package com.ribomation.droidAtScreen.gui;

import com.ribomation.droidAtScreen.cmd.Command;

import javax.swing.*;
import java.awt.*;

/**
 * Util class with a collection of GUI helper methods.
 */
public class GuiUtil {
  public static void placeInCenterScreen(Window win) {
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frame = win.getSize();
    win.setLocation((screen.width - frame.width) / 2,
        (screen.height - frame.height) / 2);
  }

  public static void placeInUpperLeftScreen(Window win) {
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frame = win.getSize();
    win.setLocation(screen.width / 4 - frame.width / 2,
        screen.height / 4 - frame.height / 2);
  }

  public static JMenu createMenu(String name, char mnemonicChar,
      String... commandNames) {
    JMenu m = new JMenu(name);
    m.setMnemonic(mnemonicChar);

    for (String cmdName : commandNames) {
      if (cmdName.equals("-")) {
        m.addSeparator();
      } else {
        m.add(Command.get(cmdName).createMenuItem());
      }
    }

    return m;
  }

  public static JToolBar createToolbar(String... commandNames) {
    JToolBar tb = new JToolBar();
    for (String cmdName : commandNames) {
      if (cmdName.equals("-")) {
        tb.addSeparator();
      }
      else {
        tb.add(Command.get(cmdName).createButton());
      }
    }
    return tb;
  }
}
