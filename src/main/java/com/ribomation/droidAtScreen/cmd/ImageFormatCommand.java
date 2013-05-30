package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;

import javax.swing.*;

/**
 * Sets the default image format, when saving screen-shots.
 */
public class ImageFormatCommand extends Command {
  public ImageFormatCommand() {
    updateView(getApplication().getSettings().getImageFormat());
    setIcon("imgfmt");
    setMnemonic('F');
    setTooltip("Set the default image-format when saving screen-shots.");
  }

  protected void updateView(String imgFmt) {
    setLabel(String.format("Image Format (%s)", imgFmt));
  }

  @Override
  protected void doExecute(Application app) {
    String[] formats = app.getSettings().getImageFormats();
    int rc = JOptionPane.showOptionDialog(app.getAppFrame(), "Image Formats",
        "Set default image format", JOptionPane.OK_CANCEL_OPTION,
        JOptionPane.QUESTION_MESSAGE, null, formats,
        app.getSettings().getImageFormat());

    if (0 <= rc && rc < formats.length) {
      app.getSettings().setImageFormat(formats[rc]);
      updateView(formats[rc]);
    }
  }
}
