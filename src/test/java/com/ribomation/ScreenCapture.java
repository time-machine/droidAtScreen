package com.ribomation;

import com.ribomation.droidAtScreen.dev.AndroidDevice;
import com.ribomation.droidAtScreen.dev.AndroidDeviceManager;
import com.ribomation.droidAtScreen.img.OrientationImageTransformer;
import com.ribomation.droidAtScreen.img.ScaleImageTransformer;

import java.io.File;

public class ScreenCapture {
  private String adbCmdPath = "/usr/lib/android-sdk-linux/platform-tools/adb";
  private AndroidDeviceManager mgr;
  public static void main(String[] args) throws InterruptedException {
    ScreenCapture app = new ScreenCapture();
    app.landscapeAndDoubleSize();
    // TODO: -
  }

  public ScreenCapture() {
    mgr = new AndroidDeviceManager();
    mgr.setAdbExecutable(new File(adbCmdPath));
  }

  public void landscapeAndDoubleSize() {
    mgr.addAndroidDeviceListener(new DeviceListener() {
      @Override
      protected AndroidDevice wrap(AndroidDevice dev) {
        return new TransformingAndroidDevice(
            new OrientationImageTransformer(false),
            new TransformingAndroidDevice(new ScaleImageTransformer(2.0), dev));
      }
    });
  }
}
