package com.ribomation.droidAtScreen.gui;

import com.ribomation.droidAtScreen.Application;

import javax.swing.*; // JFrame
import java.awt.*; // HeadlessException

public class ApplicationFrame extends JFrame {
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
  }
}
