package com.ribomation.droidAtScreen;

import com.ribomation.droidAtScreen.dev.AndroidDeviceListener;

import javax.swing.*;
import java.io.File;
import java.util.prefs.Preferences;

/**
 * Application interface that provides a set of services for disparate parts of
 * the app.
 */
public interface Application {
  JFrame getAppFrame();
  String getName();
  String getVersion();
  void addAndroidDeviceListener(AndroidDeviceListener listener);
  Preferences getPreferences();
  void setSkipEmulator(boolean skip);
  void setAdbExecutablePath(File file);
  void savePreferences();
}
