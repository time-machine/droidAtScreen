package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * A command that is associated with a target object, which is the subject for
 * the execution of the command.
 */
public abstract class CommandWithTarget<TargetType> extends Command {
  private TargetType target;

  protected CommandWithTarget(TargetType target) {
    this.target = target;
  }

  public TargetType getTarget() {
    return target;
  }

  @Override
  public AbstractButton newButton() {
    //JToggleButton b = new JToggleButton(this);
    //b.setVerticalTextPosition(AbstractButton.BOTTOM);
    //b.setHorizontalTextPosition(AbstractButton.CENTER);
    final JButton b = new JButton(this);
    b.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    b.setRolloverEnabled(true);
    b.setContentAreaFilled(false);
    b.setFocusPainted(false);
    b.addMouseListener(new MouseListener() {
      public void mouseClicked(MouseEvent e) {}
      public void mousePressed(MouseEvent e) {}
      public void mouseReleased(MouseEvent e) {}
      public void mouseEntered(MouseEvent e) {
        b.setBorder(BorderFactory.createEtchedBorder());
      }
      public void mouseExited(MouseEvent e) {
        b.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
      }
    });
    return b;
  }

  protected abstract void updateButton(TargetType target);

  protected abstract void doExecute(Application app, TargetType target);

  @Override
  final protected void doExecute(Application app) {
    doExecute(app, getTarget());
  }
}
