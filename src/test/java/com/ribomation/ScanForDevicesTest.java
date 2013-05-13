package com.ribomation;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.BeforeClass;
import org.junit.AfterClass;

public class ScanForDevicesTest {
  private static String adbCmdPath = System.getenv("ANDROID_HOME") +
      "/platform-tools/adb";
  private static AndroidDebugBridge adb;

  @BeforeClass
  public static void init() throws InterruptedException {
    AndroidDebugBridge.init(false);
    adb = AndroidDebugBridge.createBridge(adbCmdPath, false);
  }

  @AfterClass
  public static void destroy() {
    AndroidDebugBridge.disconnectBridge();
    AndroidDebugBridge.terminate();
  }

  @Test
  // @Ignore
  public void scan() {
    System.out.printf("Waiting for ADB to connect");
    while (!adb.hasInitialDeviceList()) {
      System.out.printf(".");
      sleep(100);
    }
    System.out.println();

    IDevice[] devices = adb.getDevices();
    for (IDevice dev : devices) {
      System.out.printf("DEV: %s%n", dev);
      System.out.printf("    AVD: %s%n", dev.getAvdName());
      System.out.printf("    S/N: %s%n", dev.getSerialNumber());
      System.out.printf("    State: %s%n", dev.getState());
    }
  }

  @Test
  public void useListener() {
    AndroidDebugBridge.IDeviceChangeListener devChangedListener =
        new AndroidDebugBridge.IDeviceChangeListener() {
          public void deviceConnected(IDevice device) {
            System.out.println("ScanForDevicesTest.deviceConnected: dev = " +
                device);
          }
          public void deviceDisconnected(IDevice device) {
            System.out.println("ScanForDevicesTest.deviceDisconnected: dev = " +
                device);
          }
          public void deviceChanged(IDevice device, int changeMask) {
            System.out.println("ScanForDevicesTest.deviceChanged: dev = " +
                device + ", mask = " + changeMask);
          }
        };

    AndroidDebugBridge.addDeviceChangeListener(devChangedListener);
    System.out.printf("Waiting for 10 secs...%n");
    sleep(10 * 1000);
  }

  private void sleep(long time) {
    try {
      Thread.sleep(time);
    }
    catch (InterruptedException ignored) {}
  }
}
