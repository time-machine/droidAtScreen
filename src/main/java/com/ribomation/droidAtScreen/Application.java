package com.ribomation.droidAtScreen;

import java.util.prefs.Preferences;

/**
 * Application interface that provides a set of services for disparate parts of
 * the app.
 */
public interface Application {
  String getName();
  String getVersion();
  Preferences getPreferences();
}
