package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class AdbExePathCommand extends Command {
  public AdbExePathCommand() {
    setLabel("ADB Executable Path");
    setTooltip("Sets the path to the Android Device Debugger (ADB) executable");
    setIcon("app");
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

  private JLabel createInfoPane() {
    JLabel txt = new JLabel(loadResource("/adb-exe-info.html"));
    return txt;
  }

  private JPanel createPathPane() {
    final File adbExecutable = getApplication().getSettings().getAdbExecutable();
    final JTextField pathField = new JTextField(adbExecutable != null ?
        adbExecutable.getAbsolutePath() : "");
    final JButton open = new JButton("...");

    open.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        createFileDialog(pathField, adbExecutable);
      }
    });

    JPanel pathPane = new JPanel(new BorderLayout(5, 0));
    pathPane.add(pathField, BorderLayout.CENTER);
    pathPane.add(open, BorderLayout.EAST);
    return pathPane;
  }

  private void createFileDialog(final JTextField txtField, File exe) {
    JFileChooser chooser = new JFileChooser(exe != null ? exe.getParentFile() :
        null);
    int rc = chooser.showOpenDialog(getApplication().getAppFrame());

    if (rc == JFileChooser.APPROVE_OPTION) {
      final File file = chooser.getSelectedFile();

      if (!file.equals(exe) && file.canRead() && file.canExecute()) {
        SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
            txtField.setText(file.getAbsolutePath());
            getApplication().getSettings().setAdbExecutable(file);
            getApplication().getDeviceManager().setAdbExecutable(file);
            getApplication().getDeviceManager().createBridge();
          }
        });
        if (exe != null && exe.exists()) {
          JOptionPane.showMessageDialog(getApplication().getAppFrame(),
              "The change of ADB path will take change the next time you " +
              "start Droid@Screen", "", JOptionPane.WARNING_MESSAGE);
        }
      } else {
        JOptionPane.showMessageDialog(getApplication().getAppFrame(),
            "Cannot read/execute the file: " + file.getAbsolutePath(),
            "Not an executable", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
}
