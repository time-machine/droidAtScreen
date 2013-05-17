package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;

import javax.swing.*;

/**
 * Flips the device frame orientation.
 */
public class OrientationCommand extends CheckBoxCommand {
  public OrientationCommand() {
    updateView(getPreferenceValue());
    setTooltip("Flip the orientation (portrait | landscape)");
    setEnabledOnlyWithDevice(true);
  }

  @Override
  protected boolean getDefaultValue() {
    return false;
  }

  @Override
  protected void notifyApplication(Application app, boolean landscape) {
    app.setLandscapeMode(landscape);
    updateView(landscape);
    app.getAppFrame().validate();
  }

  private void updateView(boolean landscape) {
    setLabel(landscape ? "Landscape" : "Portrait");
    setIcon("orientation-" + getLabel().toLowerCase().trim());
  }

  public boolean isLandscape() {
    return getPreferenceValue();
  }

  @Override
  protected String getPreferencesKey() {
    return "landscape-mode";
  }

  @Override
  public AbstractButton newButton() {
    JToggleButton b = new JToggleButton(this);
    b.setVerticalTextPosition(AbstractButton.BOTTOM);
    b.setHorizontalTextPosition(AbstractButton.CENTER);
    b.setSelected(getPreferenceValue());
    return b;
  }
}
