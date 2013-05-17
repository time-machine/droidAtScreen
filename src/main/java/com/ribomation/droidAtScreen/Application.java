package com.ribomation.droidAtScreen;

import com.ribomation.droidAtScreen.dev.AndroidDevice;
import com.ribomation.droidAtScreen.dev.AndroidDeviceListener;
import com.ribomation.droidAtScreen.gui.ApplicationFrame;
import com.ribomation.droidAtScreen.gui.DeviceFrame;

import javax.swing.*;
import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.prefs.Preferences;

/**
 * Application interface that provides a set of services for disparate parts of
 * the app.
 */
public interface Application {
  ApplicationFrame getAppFrame();
  String getName();
  String getVersion();
  void addAndroidDeviceListener(AndroidDeviceListener listener);
  void setScale(int percentage);
  Preferences getPreferences();
  void setAutoShow(boolean show);
  void setSkipEmulator(boolean skip);
  void setAdbExecutablePath(File file);
  void setLandscapeMode(boolean portrait);
  void setUpsideDown(boolean upsideDown);
  void setFrameRate(int rate);
  void savePreferences();
  void destroyPreferences();
  DeviceFrame getSelectedDevice();
  Map<String, DeviceFrame> getDevices();
}
