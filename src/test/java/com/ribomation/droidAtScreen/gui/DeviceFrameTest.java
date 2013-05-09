package com.ribomation.droidAtScreen.gui;

import com.ribomation.droidAtScreen.dev.AndroidDevice;
import com.ribomation.droidAtScreen.dev.AndroidDeviceListener;
import com.ribomation.droidAtScreen.dev.AndroidDeviceManager;

import javax.swing.*;
import java.io.File;

import org.junit.Test;

public class DeviceFrameTest implements AndroidDeviceListener {
  private String adbCmdPath = "/usr/lib/android-sdk-linux/platform-tools/adb";
  private AndroidDeviceManager mgr;
  private JFrame win;

  public static void main(String[] args) throws Exception {
    DeviceFrameTest app = new DeviceFrameTest();
    app.initAndroid();
    app.initGUI();
  }

  public void initAndroid() throws Exception {
    mgr = new AndroidDeviceManager();
    mgr.setAdbExecutable(new File(adbCmdPath));
    mgr.addAndroidDeviceListener(this);
  }

  public void initGUI() {
    win = new JFrame("DeviceFrameTest");
    win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    win.setSize(200, 300);
    win.setLocation(300, 200);
    win.setVisible(true);
  }

  @Override
  public void connected(AndroidDevice dev) {
    System.out.println("DeviceFrameTest.connected");
    if (dev.isEmulator()) return;
    DeviceFrame devGUI = new DeviceFrame(null, dev, false, 75, 10);
    devGUI.setVisible(true);
  }

  @Override
  public void disconnected(AndroidDevice dev) {
    System.out.println("DeviceFrameTest.disconnected");
  }

  @Test
  public void testStub() throws Exception {
  }
}
