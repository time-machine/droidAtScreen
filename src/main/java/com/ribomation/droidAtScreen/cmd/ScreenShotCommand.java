package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;
import com.ribomation.droidAtScreen.dev.AndroidDevice;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

/**
 * Takes a screen-shot of the current device image.
 */
public class ScreenShotCommand extends Command {
  private File lastDir = null;
  private int count = 1;

  public ScreenShotCommand() {
    setLabel("Capture");
    setIcon("camera");
    setTooltip("Takes a screen-shot of the current device and saves it as a " +
        "PNG file.");
    setEnabledOnlyWithDevice(true);
  }

  @Override
  public void setEnabled(boolean newValue) {
    getLog().info("setEnabled: " + newValue);
    super.setEnabled(newValue);
  }

  @Override
  protected void doExecute(final Application app) {
    final AndroidDevice dev = app.getSelectedDevice();
    if (dev == null) return;

    final ImageFormatCommand fmtCmd = Command.find(ImageFormatCommand.class);
    File suggestedFile = new File(String.format("droidAtScreen-%d.%s", count++,
        fmtCmd.getCurrentFormat().toLowerCase()));

    final JFileChooser chooser = new JFileChooser(lastDir);
    chooser.setCurrentDirectory(null);
    chooser.setSelectedFile(suggestedFile);
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    chooser.addChoosableFileFilter(new FileNameExtensionFilter("Image Files",
        fmtCmd.getFormats()));

    int rc = chooser.showSaveDialog(app.getAppFrame());
    if (rc == JFileChooser.APPROVE_OPTION) {
      final File imageFile = chooser.getSelectedFile();
      lastDir = imageFile.getParentFile();

      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          try {
            if (imageFile.exists()) {
              int rc = JOptionPane.showConfirmDialog(app.getAppFrame(),
                  "File '" + imageFile +
                  "' already exist. Do you want to overwrite?",
                  "Overwrite file", JOptionPane.YES_OPTION);
              if (rc != JOptionPane.YES_OPTION) return;
            }
            BufferedImage screenShot = dev.getScreenShot();
            ImageIO.write(screenShot, getFormat(imageFile), imageFile);
          }
          catch (Exception e) {
            JOptionPane.showMessageDialog(app.getAppFrame(),
                "Failed to save file " + imageFile + ". " + e.getMessage(),
                "Failure", JOptionPane.ERROR_MESSAGE);
          }
        }

        String getFormat(File f) {
          final String name = f.getName();
          final int dot = name.lastIndexOf('.');
          if (dot > 0) {
            String ext = name.substring(dot + 1).toUpperCase();
            if (Arrays.asList(fmtCmd.getFormats()).contains(ext)) {
              return ext;
            }
          }

          throw new RuntimeException("Invalid extension: " + name);
        }
      });
    }
  }
}
