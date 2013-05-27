package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;
import com.ribomation.droidAtScreen.gui.DeviceFrame;

import javax.swing.*;
import java.util.Set;
import java.util.TreeSet;

/**
 * Prompts the user for a Look & Feel to set for the UI.
 */
public class LookAndFeelCommand extends Command {
  public LookAndFeelCommand() {
    setLabel("Set Look & Feel");
    setTooltip("Let you choose which Look & Feel to use.");
    setIcon("lookandfeel");
  }

  @Override
  protected void doExecute(final Application app) {
    final String lafName = (String) JOptionPane.showInputDialog(app.getAppFrame(),
        "Choose a Look & Feel", "Look & Feel", JOptionPane.QUESTION_MESSAGE,
        null, toNames(UIManager.getInstalledLookAndFeels()),
        UIManager.getLookAndFeel().getName());
    if (lafName == null) return;

    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        try {
          UIManager.setLookAndFeel(findClassName(lafName));
          SwingUtilities.updateComponentTreeUI(app.getAppFrame());
          app.getAppFrame().pack();

          for (DeviceFrame frame : app.getDevices()) {
            SwingUtilities.updateComponentTreeUI(frame);
            frame.pack();
          }
        }
        catch (Exception e) {}
      }
    });
  }

  protected String[] toNames(UIManager.LookAndFeelInfo[] info) {
    Set<String> names = new TreeSet<String>();
    for (UIManager.LookAndFeelInfo i : info) {
      names.add(i.getName());
    }
    return names.toArray(new String[names.size()]);
  }

  protected String findClassName(String lafName) {
    for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
      if (info.getName().equals(lafName)) {
        return info.getClassName();
      }
    }
    return null;
  }
}
