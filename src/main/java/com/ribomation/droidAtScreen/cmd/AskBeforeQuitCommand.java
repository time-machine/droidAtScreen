package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;

/**
 * Controls if a prompt should be shown before quitting the application.
 */
public class AskBeforeQuitCommand extends CheckBoxCommand {
  public AskBeforeQuitCommand() {
    setLabel("Ask Before Quit");
    setTooltip("If you want a prompt before quitting.");
  }

  @Override
  protected boolean getPreferenceValue() {
    return getApplication().getSettings().isAskBeforeQuit();
  }

  @Override
  protected void setPreferenceValue(boolean value) {
    getApplication().getSettings().setAskBeforeQuit(value);
  }
}
