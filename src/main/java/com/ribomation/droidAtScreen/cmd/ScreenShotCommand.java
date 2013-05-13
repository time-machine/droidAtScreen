package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;
import com.ribomation.droidAtScreen.dev.AndroidDevice;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenShotCommand extends Command {
  private static final String PNG = "png";
  private File lastFile = null;

  public ScreenShotCommand() {
    setLabel("Save ScreenShot as PNG");
    setTooltip("Takes a screen-shot of the current device and saves it as " +
        "PNG file.");
    setEnabled(false);
  }

  @Override
  protected void doExecute(final Application app) {
    AndroidDevice dev = app.getSelectedDevice();
    if (dev == null) return;

    final BufferedImage screenShot = dev.getScreenShot();
    JFileChooser chooser = new JFileChooser(lastFile);
    chooser.setSelectedFile(lastFile != null? lastFile :
        new File("droidAtScreen.png"));
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    chooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG Files",
        "png"));

    int rc = chooser.showSaveDialog(app.getAppFrame());
    if (rc == JFileChooser.APPROVE_OPTION) {
      lastFile = chooser.getSelectedFile();
      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          try {
            if (lastFile.exists()) {
              int rc = JOptionPane.showConfirmDialog(app.getAppFrame(),
                  "File '" + lastFile +
                  "' already exist. Do you want to overwrite?",
                  "Overwrite file", JOptionPane.YES_OPTION);
              if (rc != JOptionPane.YES_OPTION) return;
            }
            ImageIO.write(screenShot, PNG, lastFile);
          }
          catch (IOException e) {
            JOptionPane.showMessageDialog(app.getAppFrame(),
                "Failed to save screen-shot file " + lastFile + ". " + e,
                "Failed to save file", JOptionPane.ERROR_MESSAGE);
          }
        }
      });
    }
  }
}
