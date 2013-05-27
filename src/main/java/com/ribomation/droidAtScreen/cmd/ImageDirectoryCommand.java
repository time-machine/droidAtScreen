package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;

import javax.swing.*;
import java.io.File;

/**
 * Sets the default image dir, when saving screen-shots.
 */
public class ImageDirectoryCommand extends Command {
  public ImageDirectoryCommand() {
    setLabel("Image Dir");
    setIcon("imgfolder");
    updateView(getApplication().getSettings().getImageDirectory());
  }

  protected void updateView(File imageDirectory) {
    setTooltip(String.format("Directory when saving screen-shots (%s)",
        imageDirectory.getName()));
  }

  @Override
  protected void doExecute(Application app) {
    File imageDirectory = app.getSettings().getImageDirectory();

    final JFileChooser chooser = new JFileChooser(imageDirectory);
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int rc = chooser.showSaveDialog(app.getAppFrame());
    if (rc != JFileChooser.APPROVE_OPTION) return;

    imageDirectory = chooser.getSelectedFile();
    if (!(imageDirectory.isAbsolute() && imageDirectory.canWrite())) {
      JOptionPane.showMessageDialog(app.getAppFrame(),
          "Not a writable directory '" + imageDirectory + "'",
          "Invalid directory", JOptionPane.ERROR_MESSAGE);
      return;
    }

    app.getSettings().setImageDirectory(imageDirectory);
    updateView(imageDirectory);
  }
}
