package com.ribomation.droidAtScreen;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class DroidAtScreenApplicationTest {
  private DroidAtScreenApplication target;

  @Before
  public void setUp() throws Exception {
    target = new DroidAtScreenApplication();
  }

  @After
  public void tearDown() throws Exception {}

  @AfterClass
  public static void clean() throws BackingStoreException {
    new DroidAtScreenApplication().getPreferences().removeNode();
  }

  @Test
  public void testGetPreferences() throws Exception {
    Preferences prefs = target.getPreferences();
    assertThat(prefs, notNullValue());

    int n = prefs.keys().length;
    prefs.put("tst-msg", "Foobar strikes again");
    prefs.putInt("tst-rate", 16);
    prefs.putBoolean("tst-auto", true);

    for (String name : prefs.keys()) {
      System.out.printf("%s: %s%n", name, prefs.get(name, "[none]"));
    }
    assertThat(prefs.keys().length, is(n + 3));
  }
}
