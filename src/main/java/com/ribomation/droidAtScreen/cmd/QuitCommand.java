package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;

import javax.swing.*;

/**
 * Quits the application, but asking first.
 */
public class QuitCommand extends Command {
  public QuitCommand() {
    setLabel("Exit");
    setTooltip("Exits the application");
    setIcon("exit");
    setMnemonic('Q');
  }

  @Override
  protected void doExecute(Application app) {
    if (!app.getSettings().isAskBeforeQuit() || askUser(app)) doQuit(app);
  }

  private void doQuit(Application app) {
    JFrame f = app.getAppFrame();
    if (f != null) f.dispose();
    System.exit(0);
  }

  private boolean askUser(Application app) {
    int rc = JOptionPane.showConfirmDialog(app.getAppFrame(),
        "Do you really want to quit?", "Quit?", JOptionPane.OK_CANCEL_OPTION);
    return rc == JOptionPane.OK_OPTION;
  }
}
