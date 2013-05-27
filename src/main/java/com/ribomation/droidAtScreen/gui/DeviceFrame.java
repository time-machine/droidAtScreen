package com.ribomation.droidAtScreen.gui;

import com.ribomation.droidAtScreen.Application;
import com.ribomation.droidAtScreen.Settings;
import com.ribomation.droidAtScreen.cmd.*;
import com.ribomation.droidAtScreen.dev.AndroidDevice;
import com.ribomation.droidAtScreen.dev.ScreenImage;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.TimerTask;

/**
 * Frame holder for the device image.
 */
public class DeviceFrame extends JFrame implements Comparable<DeviceFrame> {
  private final static RenderingHints HINTS = new RenderingHints(
      RenderingHints.KEY_INTERPOLATION,
      RenderingHints.VALUE_INTERPOLATION_BICUBIC);

  private final Application app;
  private final AndroidDevice device;
  private Logger log;

  private int scalePercentage = 100;
  private boolean landscapeMode = false;
  private boolean upsideDown = false;

  private ImageCanvas canvas;
  private JComponent toolBar;
  private RecordingListener recordingListener;
  private TimerTask retriever;
  private InfoPane infoPane;

  public DeviceFrame(Application app, AndroidDevice device) {
    this.app = app;
    this.device = device;
    this.log = Logger.getLogger(DeviceFrame.class.getName() + ":" +
        device.getName());
    log.debug(String.format("DeviceFrame(device=%s)", device));

    Settings cfg = app.getSettings();
    setScale(cfg.getPreferredScale());
    setLandscapeMode(cfg.isLandscape());

    setTitle(device.getName());
    setIconImage(GuiUtil.loadIcon("device").getImage());
    setResizable(false);

    add(canvas = new ImageCanvas(), BorderLayout.CENTER);
    add(toolBar = createToolBar(), BorderLayout.WEST);
    add(infoPane = new InfoPane(), BorderLayout.SOUTH);

    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    addWindowStateListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        log.debug("windowClosing");
        stopRetriever();
        DeviceFrame.this.setVisible(false);
        DeviceFrame.this.app.getDeviceTableModel().refresh();
      }
    });

    startRetriever();
    pack();
  }

  public void startRetriever() {
    retriever = new Retriever();
    app.getTimer().schedule(retriever, 0, 500);
  }

  public void stopRetriever() {
    retriever.cancel();
  }

  class Retriever extends TimerTask {
    @Override
    public void run() {
      long start = System.currentTimeMillis();
      ScreenImage image = device.getScreenImage();
      long elapsed = System.currentTimeMillis() - start;
      infoPane.setElapsed(elapsed, image);
      infoPane.setStatus(device.getState().name().toUpperCase());

      log.debug(String.format("Got screenshot %s, elapsed %d ms", image,
          elapsed));

      boolean fresh = canvas.getScreenshot() == null;
      if (image != null) {
        if (recordingListener != null) recordingListener.record(image);
        canvas.setScreenshot(image);
        infoPane.setSizeInfo(canvas);
      }

      if (fresh) {
        log = Logger.getLogger(DeviceFrame.class.getName() + ":" +
            device.getName());
        setTitle(device.getName());
        pack();
        app.getDeviceTableModel().refresh();
      }
    }
  }

  protected JComponent createToolBar() {
    JPanel buttons = new JPanel(new GridLayout(5, 1, 0, 8));
    buttons.add(new OrientationCommand(this).newButton());
    buttons.add(new UpsideDownCommand(this).newButton());
    buttons.add(new ScaleCommand(this).newButton());
    buttons.add(new ScreenshotCommand(this).newButton());
    buttons.add(new RecordingCommand(this).newButton());

    JPanel tb = new JPanel(new FlowLayout());
    tb.setBorder(BorderFactory.createEmptyBorder());
    tb.add(buttons);

    return tb;
  }

  public class InfoPane extends JPanel {
    JLabel size, status, elapsed;

    InfoPane() {
      super(new GridLayout(1, 2, 3, 0));
      setBorder(BorderFactory.createEmptyBorder());

      Font font = getFont().deriveFont(Font.PLAIN, 12.0F);
      status = new JLabel("UNKNOWN");
      status.setFont(font);
      status.setHorizontalAlignment(SwingConstants.LEADING);
      status.setToolTipText("Device status");

      size = new JLabel("? x ?");
      size.setFont(font);
      size.setHorizontalAlignment(SwingConstants.CENTER);
      size.setToolTipText("Image dimension and size");

      elapsed = new JLabel("");
      elapsed.setFont(font);
      elapsed.setHorizontalAlignment(SwingConstants.RIGHT);
      elapsed.setToolTipText("Elapsed time and rate of last screenshot");

      this.add(status);
      this.add(size);
      this.add(elapsed);
    }

    void setSizeInfo(ImageCanvas img) {
      Dimension sz = img.getPreferredSize();
      size.setText(String.format("%dx%d (%s)", sz.width, sz.height,
          new Unit(img.getScreenshot().getRawImage().size).toString()));
    }

    public void setStatus(String devStatus) {
      status.setText(devStatus);
    }

    public void setElapsed(long time, ScreenImage img) {
      int sz = (img != null ? (int)(img.getRawImage().size / (time / 1000.0)) :
          0);
      elapsed.setText(String.format("%d ms (%s/s)", time,
          new Unit(sz).toString()));
    }
  }

  class Unit {
    final int K = 1024;
    final int M = K * K;
    final int G = K * M;
    long value;

    Unit(long value) {
      this.value = value;
    }

    String unit() {
      if (value / G > 0) return "Gb";
      if (value / M > 0) return "Mb";
      if (value / K > 0) return "Kb";
      return "bytes";
    }

    float value() {
      if (value / G > 0) return (float)value / G;
      if (value / M > 0) return (float)value / M;
      if (value / K > 0) return (float)value / K;
      return value;
    }

    public String toString() {
      return String.format("%.1f %s", value(), unit());
    }
  }

  class ImageCanvas extends JComponent {
    private ScreenImage image;

    public ImageCanvas() {
      setBorder(BorderFactory.createLoweredBevelBorder());
    }

    public void setScreenshot(ScreenImage image) {
      this.image = image;
      repaint();
    }

    public ScreenImage getScreenshot() {
      return image;
    }

    @Override
    protected void paintComponent(Graphics g) {
      if (image != null && g instanceof Graphics2D) {
        Graphics2D g2 = (Graphics2D)g;
        AffineTransform TX = new AffineTransform();
        BufferedImage bufImg = image.toBufferedImage();

        if (landscapeMode) {
          bufImg = toLandscape(bufImg);
        }

        if (scalePercentage != 100) {
          double scale = scalePercentage / 100.0;
          TX.concatenate(AffineTransform.getScaleInstance(scale, scale));
        }

        if (upsideDown) {
          int w = image.getWidth();
          int h = image.getHeight();
          double x = (landscapeMode ? h : w) / 2;
          double y = (landscapeMode ? w : h) / 2;
          TX.concatenate(AffineTransform.getQuadrantRotateInstance(2, x, y));
        }

        g2.drawImage(bufImg, TX, null);
      } else {
        g.setColor(Color.RED);
        g.setFont(getFont().deriveFont(16.0F));
        g.drawString("No screenshot yet", 10, 25);
      }
    }

    BufferedImage toLandscape(BufferedImage img) {
      return rotate(3, img);
    }

    BufferedImage rotate(int quadrants, BufferedImage img) {
      int w = img.getWidth();
      int h = img.getHeight();
      int x = (quadrants == 2 || quadrants == 3) ? w : 0;
      int y = (quadrants == 1 || quadrants == 2) ? h : 0;

      Point2D origo = AffineTransform.getQuadrantRotateInstance(quadrants, 0, 0)
          .transform(new Point(x, y), null);
      BufferedImage result = new BufferedImage(h, w, img.getType());
      Graphics2D g = result.createGraphics();
      g.translate(0 - origo.getX(), 0 - origo.getY());
      g.transform(AffineTransform.getQuadrantRotateInstance(quadrants, 0, 0));
      g.drawRenderedImage(img, null);

      return result;
    }

    @Override
    public Dimension getPreferredSize() {
      if (image == null) return new Dimension(200, 300);
      if (landscapeMode) {
        return new Dimension(scale(image.getHeight()), scale(image.getWidth()));
      }
      return new Dimension(scale(image.getWidth()), scale(image.getHeight()));
    }

    @Override
    public Dimension getMinimumSize() {
      return getPreferredSize();
    }
  }

  public void setLandscapeMode(boolean landscape) {
    this.landscapeMode = landscape;
  }

  public void setScale(int scalePercentage) {
    this.scalePercentage = scalePercentage;
  }

  public void setUpsideDown(boolean upsideDown) {
    this.upsideDown = upsideDown;
  }

  public void setRecordingListener(RecordingListener recordingListener) {
    this.recordingListener = recordingListener;
  }

  public ScreenImage getLastScreenshot() {
    return canvas.getScreenshot();
  }

  public InfoPane getInfoPane() {
    return infoPane;
  }

  public AndroidDevice getDevice() {
    return device;
  }

  public String getName() {
    return device.getName();
  }

  public boolean isLandscapeMode() {
    return landscapeMode;
  }

  public int getScale() {
    return scalePercentage;
  }

  public boolean isUpsideDown() {
    return upsideDown;
  }

  private int scale(int value) {
    if (scalePercentage == 100) return value;
    return (int)Math.round(value * scalePercentage / 100.0);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    DeviceFrame that = (DeviceFrame)o;
    return this.device.getName().equals(that.device.getName());
  }

  @Override
  public int hashCode() {
    return device.getName().hashCode();
  }

  @Override
  public int compareTo(DeviceFrame that) {
    return this.getName().compareTo(that.getName());
  }
}
