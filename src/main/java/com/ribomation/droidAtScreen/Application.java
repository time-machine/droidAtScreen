package com.ribomation.droidAtScreen;

import com.ribomation.droidAtScreen.dev.AndroidDevice;
import com.ribomation.droidAtScreen.dev.AndroidDeviceListener;
import com.ribomation.droidAtScreen.dev.AndroidDeviceManager;
import com.ribomation.droidAtScreen.gui.ApplicationFrame;
import com.ribomation.droidAtScreen.gui.DeviceFrame;
import com.ribomation.droidAtScreen.gui.DeviceTableModel;

import javax.swing.*;
import javax.swing.table.TableModel;
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
  Settings getSettings();
  Info getInfo();
  AndroidDeviceManager getDeviceManager();
  void addAndroidDeviceListener(AndroidDeviceListener listener);
  Map<String, DeviceFrame> getDevices();

  /**
   * Invoked when a new device is detected.
   * @param dev the new device
   */
  void connected(AndroidDevice dev);

  /**
   * Invoked when a device goes offline.
   * @param dev the defunct device
   */
  void disconnected(AndroidDevice dev);

  DeviceTableModel getDeviceTableModel();
}
