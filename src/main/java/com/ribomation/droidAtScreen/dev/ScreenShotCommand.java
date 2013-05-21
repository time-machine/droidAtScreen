package com.ribomation.droidAtScreen.dev;

import com.ribomation.droidAtScreen.Application;
import com.ribomation.droidAtScreen.cmd.CommandWithTarget;
import com.ribomation.droidAtScreen.gui.DeviceFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Takes a screen-shot of the current device image.
 */
public class ScreenShotCommand extends CommandWithTarget<DeviceFrame> {
  public ScreenShotCommand(DeviceFrame target) {
    super(target);
    setIcon("camera");
    setTooltip("Takes a screen-shot of the current device");
  }

  @Override
  protected void doExecute(Application app, DeviceFrame device) {
    JFileChooser chooser = createChooser(app.getSettings().getImageDirectory(),
        suggestFile(app), app.getSettings().getImageFormats());

    if (chooser.showSaveDialog(app.getAppFrame()) ==
        JFileChooser.APPROVE_OPTION) {
      File file = chooser.getSelectedFile();
      if (!file.exists() || askOverwrite(app, file)) {
        SwingUtilities.invokeLater(new ImageSaver(app, file, device));
      }
    }
  }

  private class ImageSaver implements Runnable {
    private Application app;
    private File file;
    private DeviceFrame device;

    private ImageSaver(Application app, File file, DeviceFrame device) {
      this.app = app;
      this.file = file;
      this.device = device;
    }

    @Override
    public void run() {
      try {
        ImageIO.write(device.getLastScreenshot().toBufferedImage(),
            extractFormat(app, file), file);
        app.getAppFrame().getStatusBar().message("Written",
            file.getAbsolutePath());
      } catch (Exception e) {
        JOptionPane.showMessageDialog(app.getAppFrame(),
            String.format("Failed to save file '%s': %s", file, e.getMessage()),
            "Failure", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  @Override
  protected void updateButton(DeviceFrame target) {}

  private JFileChooser createChooser(File dir, File file, String[] exts) {
    JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(dir);
    chooser.setSelectedFile(file);
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    chooser.addChoosableFileFilter(new FileNameExtensionFilter("Image Files",
        exts));
    return chooser;
  }

  private File suggestFile(Application app) {
    return new File(String.format("%s-%d.%s",
        app.getInfo().getName().toLowerCase(), app.getSettings().nextInt(),
        app.getSettings().getImageFormat()));
  }

  private boolean askOverwrite(Application app, File f) {
    return JOptionPane.showConfirmDialog(app.getAppFrame(), String.format(
        "File '%s' already exist. Do you want to overwrite it?", f),
        "Overwrite?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
  }

  private String extractExt(File f) {
    String n = f.getName();
    int dot = n.lastIndexOf('.');
    if (dot > 0) return n.substring(dot + 1);
    return n;
  }

  private String extractFormat(Application app, File f) {
    String[] formats = app.getSettings().getImageFormats();
    String ext = extractExt(f).toUpperCase();
    if (Arrays.asList(formats).contains(ext)) return ext;
    throw new RuntimeException("Invalid extension: " + f);
  }
}
