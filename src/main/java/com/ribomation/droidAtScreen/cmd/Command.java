package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;

import javax.swing.*;

/**
 * Base class of all app commands.
 */
public abstract class Command extends AbstractAction {
  private static Application application;

  public static void setApplication(Application application) {
    Command.application = application;
  }
}
