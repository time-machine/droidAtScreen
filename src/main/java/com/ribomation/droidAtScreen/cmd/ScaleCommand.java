package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;

import javax.swing.*;

public class ScaleCommand extends Command {
  private static Integer[] scales = {25, 50, 75, 100, 125, 150, 175, 200, 250,
      300};

  public ScaleCommand() {
    setLabel("Projection Scale");
    setTooltip("Sets the projection scale % of the Android Device. 100% is " +
        "normal size");
  }

  @Override
  protected void doExecute(Application app) {
    Integer percentage = (Integer) JOptionPane.showInputDialog(
        app.getAppFrame(), "Choose the projection scale %", "Scale %?",
        JOptionPane.QUESTION_MESSAGE, null, scales, getPreferenceValue());
    if (percentage == null) return;

    setPreferenceValue(percentage);
    getApplication().setScale(percentage);
  }

  protected void setPreferenceValue(int value) {
    getApplication().getPreferences().putInt(getPreferencesKey(), value);
    getApplication().savePreferences();
  }

  protected String getPreferencesKey() {
    return "projection-scale";
  }

  protected int getPreferenceValue() {
    return getApplication().getPreferences().getInt(getPreferencesKey(), 100);
  }

  public int getScale() {
    return getPreferenceValue();
  }
}
