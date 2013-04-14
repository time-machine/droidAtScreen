package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Base class of all app commands.
 */
public abstract class Command extends AbstractAction {
  private Logger log;
  private static Application application;
  private static Map<String, Command> cmds = new HashMap<String, Command>();
  private String name;

  {
    log = Logger.getLogger(this.getClass());
  }

  protected Command() {
    this.name = extractName();
    cmds.put(this.getName(), this);
  }

  protected Command(String name) {
    this.name = name;
    cmds.put(this.getName(), this);
  }

  protected abstract void doExecute(Application app);

  public void execute() {
    try {
      doExecute(getApplication());
    }
    catch (Exception e) {
      getLog().error("Failed to execute command " + getName(), e);
      JOptionPane.showMessageDialog(null,
          "Failed to execute command " + getName() + ": " + e,
          "Application Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    //
  }

  public static <CmdType extends Command> CmdType find(
      Class<? extends Command> cmdCls) {
    String name = extractName(cmdCls);
    return (CmdType) get(name);
  }

  public static Command get(String name) {
    Command c = cmds.get(name.toLowerCase());
    if (c != null) return c;
    return loadCommand(name);
  }

  protected Logger getLog() {
    return log;
  }

  public String getName() {
    return name;
  }

  public String getLabel() {
    return (String) getValue(Action.NAME);
  }

  protected void setLabel(String label) {
    putValue(Action.NAME, label);
  }

  protected void setTooltip(String label) {
    putValue(Action.SHORT_DESCRIPTION, label);
  }

  protected void setMnemonic(char ch) {
    putValue(Action.MNEMONIC_KEY, new Integer(ch));
  }

  public JMenuItem createMenuItem() {
    JMenuItem mi = new JMenuItem();
    mi.setAction(this);
    return mi;
  }

  protected Application getApplication() {
    if (application == null) {
      throw new IllegalStateException("Missing application ref. Must invoke " +
          "Command.setApplication(...) before use.");
    }
    return application;
  }
  public static void setApplication(Application application) {
    Command.application = application;
  }

  protected static Command loadCommand(String name) {
    String clsName = Command.class.getPackage().getName() + "." +
        toTitleCase(name) + "Command";
    try {
      Class cls = Class.forName(clsName);
      return (Command) cls.newInstance();
    }
    catch (ClassNotFoundException e) {
      return new DummyCommand(name);
    }
    catch (InstantiationException e) {
      throw new RuntimeException("Failed to create command " + name +
          ". ErrMsg = " + e);
    }
    catch (IllegalAccessException e) {
      throw new RuntimeException("Not permitted to create command " + name +
          ". ErrMsg = " + e);
    }
  }

  private String extractName() {
    return extractName(this.getClass());
  }

  private static String extractName(Class cls) {
    return extractName(cls.getSimpleName());
  }

  private static String extractName(String clsName) {
    if (isBlank(clsName)) return "[NONE]";
    return clsName.substring(0, clsName.lastIndexOf("Command")).toLowerCase();
  }

  private static String toTitleCase(String s) {
    if (isBlank(s) || s.length() == 1) return s;
    return s.substring(0, 1).toUpperCase() + s.substring(1);
  }

  private static boolean isBlank(String s) {
    return s == null || s.trim().length() == 0;
  }

  private static class DummyCommand extends Command {
    private DummyCommand(String name) {
      super("dummy-" + name);
      setLabel(name);
    }

    @Override
    protected void doExecute(Application app) {
      JOptionPane.showMessageDialog(null, "Command not yet implemented: " +
        getLabel(), "Undefined command", JOptionPane.WARNING_MESSAGE);
    }
  }
}
