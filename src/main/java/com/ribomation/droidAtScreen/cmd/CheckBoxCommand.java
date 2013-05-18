package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;

import javax.swing.*;

public abstract class CheckBoxCommand extends Command {
  @Override
  public JMenuItem newMenuItem() {
    JCheckBoxMenuItem b = new JCheckBoxMenuItem();
    b.setSelected(getPreferenceValue());
    return b;
  }

  @Override
  public AbstractButton newButton() {
    AbstractButton b = new JCheckBox();
    b.setSelected(getPreferenceValue());
    return b;
  }

  @Override
  protected final void doExecute(Application app) {
    boolean selected = !getPreferenceValue();
    setPreferenceValue(selected);
    notifyApplication(app, selected);
  }

  public boolean isSelected() {
    return getPreferenceValue();
  }

  protected abstract void notifyApplication(Application app, boolean selected);
  protected abstract boolean getPreferenceValue();
  protected abstract void setPreferenceValue(boolean value);
}
