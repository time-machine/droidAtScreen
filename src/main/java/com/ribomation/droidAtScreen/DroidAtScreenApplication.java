package com.ribomation.droidAtScreen;

import org.apache.log4j.Logger;

import java.util.*;

public class DroidAtScreenApplication {
  private Logger log = Logger.getLogger(DroidAtScreenApplication.class);
  public static void main(String[] args) {
    DroidAtScreenApplication app = new DroidAtScreenApplication();
    app.parseArgs(args);
  }

  private void parseArgs(String[] args) {
    log.debug("parseArgs: " + Arrays.toString(args));
  }
}
