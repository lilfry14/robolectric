package org.robolectric.shadows;

import static android.os.Build.VERSION_CODES.P;
import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

import android.accessibilityservice.AccessibilityButtonController;
import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

/** Unit tests for {@link ShadowAccessibilityButtonController}. */
@RunWith(AndroidJUnit4.class)
@Config(minSdk = P)
public class ShadowAccessibilityButtonControllerTest {

  private AccessibilityButtonController accessibilityButtonController;
  private AccessibilityButtonController.AccessibilityButtonCallback accessibilityButtonCallback;

  private MyService service;

  @Before
  public void setUp() {
    service = new MyService();
    accessibilityButtonController = service.getAccessibilityButtonController();
  }

  @Test
  public void accessibilityButtonCallback_callbackIsCached_registerAccessibilityButtonCallback() {
    createAndRegisterAccessibilityButtonCallback();

    assertThat(shadowOf(accessibilityButtonController).getCallback()).isNotNull();
    assertEquals(
        shadowOf(accessibilityButtonController).getCallback(), accessibilityButtonCallback);
  }

  private void createAndRegisterAccessibilityButtonCallback() {
    accessibilityButtonCallback =
        new AccessibilityButtonController.AccessibilityButtonCallback() {
          @Override
          public void onClicked(AccessibilityButtonController controller) {}
        };
    accessibilityButtonController.registerAccessibilityButtonCallback(accessibilityButtonCallback);
  }

  /** AccessibilityService for {@link ShadowAccessibilityButtonControllerTest} */
  public static class MyService extends AccessibilityService {
    @Override
    public void onDestroy() {
      super.onDestroy();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent arg0) {
      // Do nothing
    }

    @Override
    public void onInterrupt() {
      // Do nothing
    }
  }
}
