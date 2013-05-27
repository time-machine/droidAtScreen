package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Abstract command for boolean commands.
 */
public abstract class CheckBoxCommand extends Command {
  @Override
  public JMenuItem newMenuItem() {
    setSelected(getPreferenceValue());
    JCheckBoxMenuItem b = new JCheckBoxMenuItem();
    b.setSelected(getPreferenceValue());
    return b;
  }

  @Override
  public AbstractButton newButton() {
    setSelected(getPreferenceValue());
    AbstractButton b = new JCheckBox();
    b.setSelected(getPreferenceValue());
    return b;
  }

  public void setSelected(boolean selected) {
    putValue(Action.SELECTED_KEY, selected);
  }

  public boolean isSelected() {
    return (Boolean)getValue(Action.SELECTED_KEY);
  }

  @Override
  protected final void doExecute(Application app) {
    boolean selected = !getPreferenceValue();
    setSelected(selected);
    setPreferenceValue(selected);
  }

  protected abstract boolean getPreferenceValue();
  protected abstract void setPreferenceValue(boolean value);
}
