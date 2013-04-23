package com.ribomation.droidAtScreen.dev;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * A facade to AndroidDebugBridge.
 */
public class AndroidDeviceManager extends Thread implements
    AndroidDebugBridge.IDebugBridgeChangeListener,
    AndroidDebugBridge.IDeviceChangeListener {
  private Logger log = Logger.getLogger(this.getClass());
  private List<AndroidDeviceListener> listeners =
      new ArrayList<AndroidDeviceListener>();

  public AndroidDeviceManager() {
    AndroidDebugBridge.init(false);
    Runtime.getRuntime().addShutdownHook(this);

    AndroidDebugBridge.addDebugBridgeChangeListener(this);
    AndroidDebugBridge.addDeviceChangeListener(this);
  }

  public void addAndroidDeviceListener(AndroidDeviceListener l) {
    listeners.add(l);
  }

  @Override
  public void bridgeChanged(AndroidDebugBridge adb) {
    this.adb = adb;
    log.debug("ADB changed");
  }

  @Override
  public void deviceChanged(IDevice dev, int changeMask) {
    log.debug("Device changed: " + dev + ", mask=" + toMaskString(changeMask));
  }

  @Override
  public void deviceDisconnected(IDevice target) {
    log.info("Device disconnected: " + target);
    //
  }

  @Override
  public void deviceConnected(IDevice target) {
    log.info("Device connected: " + target);
    // TODO: -
  }

  protected String toMaskString(int mask) {
    StringBuilder result = new StringBuilder("");
    if ((mask & IDevice.CHANGE_BUILD_INFO) != 0) {
      result.append("CHANGE_BUILD_INFO ");
    }
    if ((mask & IDevice.CHANGE_CLIENT_LIST) != 0) {
      result.append("CHANGE_CLIENT_LIST ");
    }
    if ((mask & IDevice.CHANGE_STATE) != 0) {
      result.append("CHANGE_STATE ");
    }
    return result.toString();
  }

  /**
   * The ADB object.
   */
  private AndroidDebugBridge adb;

  /**
   * Refers the ADB executable.
   */
  private File adbExecutable;

  public File getAdbExecutable() {
    return adbExecutable;
  }

  public void setAdbExecutable(File adbExecutable) {
    if (!adbExecutable.isFile()) {
      throw new RuntimeException("ADB executable '" + adbExecutable +
          "' is not a file");
    }
    if (!adbExecutable.canExecute()) {
      throw new RuntimeException("ADB executable '" + adbExecutable +
          "is not executable");
    }
    this.adbExecutable = adbExecutable;

    try {
      adb = AndroidDebugBridge.createBridge(
          getAdbExecutable().getCanonicalPath(), false);
    }
    catch (IOException e) {
      throw new RuntimeException("Failed to create the absolute path to the " +
          "ADB executable: " + getAdbExecutable());
    }
  }
}
