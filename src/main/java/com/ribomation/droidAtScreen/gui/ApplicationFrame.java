package com.ribomation.droidAtScreen.gui;

import com.ribomation.droidAtScreen.Application;
import com.ribomation.droidAtScreen.cmd.Command;
import com.ribomation.droidAtScreen.cmd.QuitCommand;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*; // HeadlessException
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ApplicationFrame extends JFrame {
  private Logger log = Logger.getLogger(ApplicationFrame.class);
  private Application application;

  public ApplicationFrame(Application application) throws HeadlessException {
    this.application = application;
  }

  public Application getApplication() {
    if (application == null) {
      throw new IllegalStateException("Missing application ref. Must invoke" +
          "setApplication(...) before use.");
    }
    return application;
  }

  public void initGUI() {
    setTitle(getApplication().getName() + ", Version " +
        getApplication().getVersion());
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    this.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        Command.find(QuitCommand.class).execute();
      }
    });
    setJMenuBar(createMenubar());
    add(createDeviceControlPane());
    pack();
  }

  private JPanel createDeviceControlPane() {
    JPanel p = new JPanel(new GridLayout(1, 1, 0, 5));
    p.add(createDevicesList());
    return p;
  }

  private JPanel createDevicesList() {
    //
    JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
    //
    return p;
  }

  protected JMenuBar createMenubar() {
    JMenuBar mb = new JMenuBar();
    mb.add(createFileMenu());
    mb.add(createViewMenu());
    //
    return mb;
  }

  protected JMenu createFileMenu() {
    return createMenu("File", 'F', "ScreenShot", "-", "Quit");
  }

  protected JMenu createViewMenu() {
    return createMenu("View", 'V', "Portrait", "Scale");
  }

  public JMenu createMenu(String name, char mnemonicChar,
      String... commandNames) {
    JMenu m = new JMenu(name);
    m.setMnemonic(mnemonicChar);

    for (String cmdName : commandNames) {
      if (cmdName.equals("-")) {
        m.addSeparator();
      }
      else {
        m.add(Command.get(cmdName).createMenuItem());
      }
    }
    return m;
  }
}
