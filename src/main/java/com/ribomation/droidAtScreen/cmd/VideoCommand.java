package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;

import javax.swing.*;

/**
 * Records a series of screen-shots into an AVI video clip.
 */
public class VideoCommand extends Command {
  public VideoCommand() {
    setLabel("Record");
    setTooltip("Records a series of screen-shots into a video clip.");
    setIcon("movie");
    setEnabledOnlyWithDevice(true);
  }

  @Override
  protected void doExecute(Application app) {
    JOptionPane.showMessageDialog(app.getAppFrame(), "Not yet implemented", "",
        JOptionPane.WARNING_MESSAGE);
  }
}
