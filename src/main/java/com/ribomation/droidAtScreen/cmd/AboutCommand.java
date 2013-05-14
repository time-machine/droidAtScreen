package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;

import javax.swing.*;
import java.awt.*;

public class AboutCommand extends Command {
  public AboutCommand() {
    setLabel("About...");
    setTooltip("Shows info about this application");
    setIcon("about");
    setMnemonic('A');
  }

  @Override
  protected void doExecute(Application app) {
    String icon = "Icon";
    String txt = "Details";
    JPanel content = new JPanel(new BorderLayout(5, 0));
    content.add(new JLabel(icon), BorderLayout.WEST);
    content.add(new JLabel(txt), BorderLayout.CENTER);

    JOptionPane.showMessageDialog(null, content, app.getName() + " - Version " +
        app.getVersion(), JOptionPane.PLAIN_MESSAGE);
  }
}
