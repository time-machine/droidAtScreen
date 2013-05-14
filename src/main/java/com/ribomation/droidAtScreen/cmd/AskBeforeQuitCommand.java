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
  protected void notifyApplication(Application app, boolean selected) {
    setPreferenceValue(selected);
  }

  @Override
  protected String getPreferencesKey() {
    return "ask-before-quit";
  }
}
