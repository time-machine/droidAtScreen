package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdbExePathCommand extends Command {
  public AdbExePathCommand() {
    setLabel("ADB Executable Path");
    setTooltip("Sets the path to the Android Device Debugger (ADB) executable");
  }

  @Override
  protected void doExecute(Application app) {
    JOptionPane.showMessageDialog(app.getAppFrame(), createPane(),
        "ADB Executable", JOptionPane.QUESTION_MESSAGE);
  }

  @Override
  public JPanel createPane() {
    JPanel pane = new JPanel(new BorderLayout(0, 5));
    pane.setBorder(BorderFactory.createTitledBorder("Path to ADB Executable"));
    pane.add(createInfoPane(), BorderLayout.CENTER);
    pane.add(createPathPane(), BorderLayout.SOUTH);
    return pane;
  }

  private JTextArea createInfoPane() {
    JTextArea infoPane = new JTextArea();
    infoPane.setEditable(false);
    infoPane.setColumns(50);
    infoPane.setRows(4);
    infoPane.setLineWrap(true);
    infoPane.setText("In order to establish a connection to a device, this " +
        "application uses the Android Debug Bridge (ADB). If ADB is not " +
        "running when this program launches, it will start ADB. For this to " +
        "work, it needs the full path to the ADB executable. Please, provide " +
        "it below.");
    return infoPane;
  }

  private JPanel createPathPane() {
    final JTextField path = new JTextField(getPreferenceValue());
    JButton open = new JButton("...");
    open.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        createFileDialog(path);
      }
    });

    JPanel pathPane = new JPanel(new BorderLayout(5, 0));
    pathPane.add(path, BorderLayout.CENTER);
    pathPane.add(open, BorderLayout.EAST);
    return pathPane;
  }

  private void createFileDialog(JTextField path) {
    // File f = null;
    // if (!isNotDefined()) {
    //   f = new File(getPreferenceValue());
    // }
    // JFileChooser chooser = new JFileChooser(f != null ? f.getParentFile() :
    //     null);
    //
  }

  protected String getPreferencesKey() {
    return "adb-executable-path";
  }

  protected String getPreferenceValue() {
    return getApplication().getPreferences().get(getPreferencesKey(), "");
  }

  public boolean isNotDefined() {
    return isEmpty(getPreferenceValue());
  }

  private boolean isEmpty(String s) {
    return (s == null) || (s.trim().length() == 0);
  }
}
