package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;
import com.ribomation.droidAtScreen.Settings;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Hashtable;

public class PreferredScaleCommand extends Command {
  private static final int vMarg = 2, hMarg = 4, lblHt = 26, minScale = 0,
      maxScale = 300, tick = 25;

  protected PreferredScaleCommand() {
    updateButton(getApplication().getSettings().getPreferredScale());
    setIcon("scale");
    setTooltip("Set the preferred scale of new devices");
  }

  private void updateButton(int value) {
    setLabel(String.format("Preferred Scale (%d%%)", value));
  }

  @Override
  protected void doExecute(final Application app) {
    final JDialog dlg = createScaleDialog(app,
        app.getSettings().getPreferredScale(),
        new OnScaleUpdatedListener() {
          @Override
          public void onScaleUpdated(int value) {
            app.getSettings().setPreferredScale(value);
            getLog().info(String.format("Preferred scale: value=%d", value));
            updateButton(value);
          }
        });
    dlg.setVisible(true);
  }

  public interface OnScaleUpdatedListener {
    void onScaleUpdated(int value);
  }

  public static JDialog createScaleDialog(Application app, int currentValue,
      final OnScaleUpdatedListener action) {
    final JSlider scaleSlider = new JSlider(JSlider.VERTICAL, minScale,
        maxScale, currentValue);
    Hashtable labels = scaleSlider.createStandardLabels(tick);
    for (Object key : labels.keySet()) {
      JLabel lbl = ((JLabel)labels.get(key));
      lbl.setText(lbl.getText() + "%");
    }
    scaleSlider.setPaintTicks(true);
    scaleSlider.setSnapToTicks(true);
    scaleSlider.setMajorTickSpacing(tick);
    scaleSlider.setPaintLabels(true);
    scaleSlider.setLabelTable(labels);
    scaleSlider.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createEmptyBorder(vMarg, hMarg, vMarg, 2 * hMarg),
        BorderFactory.createTitledBorder("Scale")));

    final JDialog dlg = new JDialog(app.getAppFrame(), true);
    dlg.add(scaleSlider);
    dlg.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    dlg.setSize(scaleSlider.getSize().width, labels.size() * lblHt + 2 * vMarg);
    dlg.setLocationByPlatform(true);

    scaleSlider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        if (scaleSlider.getValueIsAdjusting()) return;
        int value = scaleSlider.getModel().getValue();
        action.onScaleUpdated(value);
        dlg.dispose();
      }
    });

    return dlg;
  }
}
