package com.ribomation.droidAtScreen.cmd;

/**
 * A command that is associated with a target object, which is the subject for
 * the execution of the command.
 */
public abstract class CommandWithTarget<TargetType> extends Command {
  private TargetType target;

  protected CommandWithTarget(TargetType target) {
    this.target = target;
  }

  protected CommandWithTarget(String name, TargetType target) {
    super(name);
    this.target = target;
  }

  public TargetType getTarget() {
    return target;
  }
}
