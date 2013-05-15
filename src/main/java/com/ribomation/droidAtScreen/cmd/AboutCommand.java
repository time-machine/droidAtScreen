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
    JPanel content = new JPanel(new BorderLayout(5, 0));
    content.add(new JLabel(loadIcon("about")), BorderLayout.WEST);
    content.add(new JLabel("<html>" + systemInfo()), BorderLayout.CENTER);

    JOptionPane.showMessageDialog(null, content, app.getName() + " - Version " +
        app.getVersion(), JOptionPane.PLAIN_MESSAGE);
  }

  private String systemInfo() {
    return "<h2>System Information</h2>" + String.format(
        "<p style=\"text-align:left; color:lightGray;\">%s, %s<br/>" +
        "%s. Version %s</p>", System.getProperty("os.name"),
        System.getProperty("os.arch"), System.getProperty("java.vm.name"),
        System.getProperty("java.runtime.version"));
  }
}
