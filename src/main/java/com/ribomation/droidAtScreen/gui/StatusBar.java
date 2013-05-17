package com.ribomation.droidAtScreen.gui;

import com.ribomation.droidAtScreen.Application;

import javax.swing.*;
import java.awt.*;

/**
 * Place for status messages.
 */
public class StatusBar extends JPanel {
  private JLabel message;

  public StatusBar(Application app) {
    setLayout(new FlowLayout(FlowLayout.LEFT));
    setBorder(BorderFactory.createLoweredBevelBorder());
    //
  }
}
