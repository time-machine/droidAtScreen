package com.ribomation.droidAtScreen.dev;

import com.ribomation.droidAtScreen.Application;
import com.ribomation.droidAtScreen.cmd.CommandWithTarget;
import com.ribomation.droidAtScreen.gui.DeviceFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Set the device frame projection scale, as a percentage.
 */
public class ScaleCommand extends CommandWithTarget<DeviceFrame> {
  public ScaleCommand(DeviceFrame deviceFrame) {
    super(deviceFrame);
    setIcon("zoom");
    updateButton(deviceFrame);
  }

  @Override
  protected void doExecute(Application app, final DeviceFrame deviceFrame) {
    final JDialog dialog = new JDialog(app.getAppFrame(),
        "Set the Device Frame Scale", true);

    ActionListener action = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dialog.dispose();
        int percentage = Integer.parseInt(e.getActionCommand());
        deviceFrame.setScale(percentage);
        updateButton(deviceFrame);
        deviceFrame.validate();
      }
    };

    JPanel scalePane = createScalePane(action);
    dialog.setContentPane(new JOptionPane(scalePane,
        JOptionPane.QUESTION_MESSAGE));
    dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    dialog.pack();
    dialog.setLocationByPlatform(true);
    dialog.setVisible(true);
  }

  @Override
  protected void updateButton(DeviceFrame deviceFrame) {
    setTooltip(String.format("Current scale (%d%%)", deviceFrame.getScale()));
  }

  private JPanel createScalePane(ActionListener action) {
    JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
    p.setBorder(BorderFactory.createTitledBorder("Projection Scale"));
    ButtonGroup scale = new ButtonGroup();
    for (int s : getApplication().getSettings().getScales()) {
      JRadioButton rb = createScaleRadioButton(s, action);
      scale.add(rb);
      p.add(rb);
    }
    return p;
  }

  private JRadioButton createScaleRadioButton(int percentage,
      ActionListener action) {
    JRadioButton r = new JRadioButton(percentage + "%");
    r.setActionCommand(Integer.toString(percentage));
    r.addActionListener(action);
    r.setSelected(percentage == getTarget().getScale());
    return r;
  }
}
