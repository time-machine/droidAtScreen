package com.ribomation;

import com.ribomation.droidAtScreen.dev.AndroidDevice;
import com.ribomation.droidAtScreen.dev.AndroidDeviceListener;
import com.ribomation.droidAtScreen.dev.AndroidDeviceManager;
import com.ribomation.droidAtScreen.img.ImageTransformer;
import com.ribomation.droidAtScreen.img.OrientationImageTransformer;
import com.ribomation.droidAtScreen.img.ScaleImageTransformer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import org.junit.Test;

public class ScreenCapture {
  // private String adbCmdPath = "/usr/lib/android-sdk-linux/platform-tools/adb";
  private String adbCmdPath = "/Applications/android-sdk-macosx/platform-tools/adb";
  private AndroidDeviceManager mgr;
  public static void main(String[] args) throws InterruptedException {
    ScreenCapture app = new ScreenCapture();
    app.landscapeAndDoubleSize();

    System.out.println("Sleeping for a while...");
    Thread.sleep(15 * 1000);
  }

  public ScreenCapture() {
    mgr = new AndroidDeviceManager();
    mgr.setAdbExecutable(new File(adbCmdPath));
  }

  public void landscapeAndDoubleSize() {
    mgr.addAndroidDeviceListener(new DeviceListener() {
      @Override
      protected AndroidDevice wrap(AndroidDevice dev) {
        return new TransformingAndroidDevice(
            new OrientationImageTransformer(false),
            new TransformingAndroidDevice(new ScaleImageTransformer(2.0), dev));
      }
    });
  }

  class TransformingAndroidDevice implements AndroidDevice {
    private ImageTransformer transformer;
    private AndroidDevice delegate;

    TransformingAndroidDevice(ImageTransformer transformer,
        AndroidDevice delegate) {
      this.transformer = transformer;
      this.delegate = delegate;
    }

    @Override
    public BufferedImage getScreenShot() {
      return transformer.transform(delegate.getScreenShot());
    }

    @Override
    public BufferedImage getScreenShot(boolean landscapeMode) {
      return getScreenShot();
    }

    @Override
    public boolean isEmulator() {
      return delegate.isEmulator();
    }

    @Override
    public String getName() {
      return delegate.getName();
    }

    @Override
    public ConnectionState getState() {
      return delegate.getState();
    }
  }

  class DeviceListener implements AndroidDeviceListener {
    protected AndroidDevice wrap(AndroidDevice dev) {
      return dev;
    }

    @Override
    public void connected(AndroidDevice dev) {
      System.out.println("ScreenCapture.connected: dev = " + dev);
      if (dev.isEmulator()) return;
      createFrame(wrap(dev));
    }

    @Override
    public void disconnected(AndroidDevice dev) {
    }
  }

  private void createFrame(AndroidDevice dev) {
    DevicePane devPane = new DevicePane(dev);
    JFrame f = new JFrame("Android Screen Capture");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JScrollPane scrollPane = new JScrollPane(devPane);
    scrollPane.setSize(devPane.getPreferredSize());
    f.add(scrollPane, BorderLayout.CENTER);
    f.pack();

    f.setLocation(200, 200);
    f.setVisible(true);
  }

  class DevicePane extends JPanel {
    private AndroidDevice dev;

    DevicePane(AndroidDevice dev) {
      this.dev = dev;
      updateSize(dev.getScreenShot());
    }

    protected void updateSize(BufferedImage img) {
      setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
    }
  }

  @Test
  public void testStub() throws Exception {
  }
}
