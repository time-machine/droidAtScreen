package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;

/**
 * Controls if a prompt should be shown before taking a screenshot.
 */
public class AskBeforeScreenshotCommand extends CheckBoxCommand {
  public AskBeforeScreenshotCommand() {
    setLabel("Ask Before Screenshot");
    setTooltip("If you want to specify the filename before a screenshot, " +
        "else it's generated.");
  }

  @Override
  protected boolean getPreferenceValue() {
    return getApplication().getSettings().isAskBeforeScreenshot();
  }

  @Override
  protected void setPreferenceValue(boolean value) {
    getApplication().getSettings().setAskBeforeScreenshot(value);
  }
}
