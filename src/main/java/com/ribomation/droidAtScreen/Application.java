package com.ribomation.droidAtScreen;

//import com.ribomation.droidAtScreen.dev.AndroidDevice;

import java.util.prefs.Preferences;

/**
 * Application interface that provides a set of services for disparate parts of
 * the app.
 */
public interface Application {
  Preferences getPreferences();
}
