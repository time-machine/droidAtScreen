package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;

import javax.swing.*;

@Deprecated
public class AutoShowCommand extends CheckBoxCommand {
  public AutoShowCommand() {
    setLabel("Auto Show");
    setTooltip("Show new devices immediately");
  }

  @Override
  protected boolean getPreferenceValue() {
    return getApplication().getSettings().isAutoShow();
  }

  @Override
  protected void setPreferenceValue(boolean value) {
    getApplication().getSettings().setAutoShow(value);
  }
}
