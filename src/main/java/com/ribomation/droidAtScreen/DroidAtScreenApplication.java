package com.ribomation.droidAtScreen;

import com.ribomation.droidAtScreen.cmd.*;
import com.ribomation.droidAtScreen.dev.AndroidDevice;
import com.ribomation.droidAtScreen.dev.AndroidDeviceListener;
import com.ribomation.droidAtScreen.dev.AndroidDeviceManager;
import com.ribomation.droidAtScreen.gui.ApplicationFrame;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class DroidAtScreenApplication implements Application,
    AndroidDeviceListener {
  private Logger log = Logger.getLogger(DroidAtScreenApplication.class);
  private AndroidDeviceManager deviceManager;
  private ApplicationFrame appFrame;
  private Preferences appPreferences;
  private List<AndroidDeviceListener> deviceListeners =
      new ArrayList<AndroidDeviceListener>();
  private final String appPropertiesPath = "/META-INF/maven/com.ribomation/" +
      "droidAtScreen/pom.properties";
  private String appName = "Droid@Screen";
  private String appVersion = "0.1";

  public static void main(String[] args) {
    DroidAtScreenApplication app = new DroidAtScreenApplication();
    app.parseArgs(args);
    app.initProperties();
    app.initCommands();
    app.initGUI();
    app.initAndroid();
    app.run();
    app.postStart();
  }

  private void parseArgs(String[] args) {
    log.debug("parseArgs: " + Arrays.toString(args));
  }

  private void initProperties() {
    log.debug("initProperties");
    InputStream is = this.getClass().getResourceAsStream(appPropertiesPath);
    if (is != null) {
      try {
        Properties prp = new Properties();
        prp.load(is);
        appVersion = prp.getProperty("version", appVersion);
      }
      catch (IOException e) {
        log.debug("Missing classpath resource: " + appPropertiesPath, e);
      }
    }

    try {
      log.debug("--- Preferences ---");
      Preferences prefs = getPreferences();
      for (String key : prefs.keys()) {
        log.debug(String.format("%s: %s", key, prefs.get(key, "[none]")));
      }
      log.debug("--- END ---");
    }
    catch (BackingStoreException e) {
      log.warn("Failed to list prefs", e);
    }
  }

  private void initCommands() {
    log.debug("initCommands");
    Command.setApplication(this);
  }

  private void initGUI() {
    log.debug("initGUI");
    appFrame = new ApplicationFrame(this);
    appFrame.initGUI();
  }

  private void initAndroid() {
    log.debug("initAndroid");
    deviceManager = new AndroidDeviceManager();
    deviceManager.addAndroidDeviceListener(this);
  }

  private void run() {
    log.debug("run");
    getAppFrame().placeinUpperLeftScreen();
    getAppFrame().setVisible(true);
  }

  private void postStart() {
    log.debug("postStart");
    AdbExePathCommand adbPath = Command.find(AdbExePathCommand.class);
    if (adbPath.isNotDefined()) {
      adbPath.execute();
    }
    else {
      setAdbExecutablePath(adbPath.getFile());
    }
  }

  // --------------------------------------------
  // AndroidDeviceListener
  // --------------------------------------------
  @Override
  public void connected(final AndroidDevice dev) {
    log.debug("connected: dev = " + dev);
    // TODO: -
  }

  @Override
  public void disconnected(final AndroidDevice dev) {
    log.debug("disconnected: dev = " + dev);
    //
  }

  // --------------------------------------------
  // AndroidDeviceListener
  // --------------------------------------------
  @Override
  public void addAndroidDeviceListener(AndroidDeviceListener listener) {
    deviceListeners.add(listener);
  }

  // --------------------------------------------
  // Application
  // --------------------------------------------
  @Override
  public String getName() {
    return appName;
  }

  @Override
  public String getVersion() {
    return appVersion;
  }

  @Override
  public ApplicationFrame getAppFrame() {
    return appFrame;
  }

  @Override
  public Preferences getPreferences() {
    if (appPreferences == null) {
      appPreferences = Preferences.userNodeForPackage(this.getClass());
    }
    return appPreferences;
  }

  @Override
  public void savePreferences() {
    try {
      getPreferences().flush();
    }
    catch (BackingStoreException e) {
      log.info("Failed to flush app preferences", e);
    }
  }

  @Override
  public void setAdbExecutablePath(File value) {
    log.debug("setAdbExecutablePath: " + value);
    deviceManager.setAdbExecutable(value);
  }
}
