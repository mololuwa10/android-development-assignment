package com.example.moloassignment;

import static org.junit.Assert.assertEquals;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowToast;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(RobolectricTestRunner.class)
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testLogin() {
        MainActivity mainActivity = new MainActivity();

        // Set up the views
        EditText edtUserName = new EditText(mainActivity);
        edtUserName.setId(R.id.edtUsername);
        EditText edtPassword = new EditText(mainActivity);
        edtPassword.setId(R.id.edtPassword);
        Button btnLogin = new Button(mainActivity);
        btnLogin.setId(R.id.btnLogin);

        // Add the views to the activity's layout
        LinearLayout layout = new LinearLayout(mainActivity);
        layout.addView(edtUserName);
        layout.addView(edtPassword);
        layout.addView(btnLogin);
        mainActivity.setContentView(layout);

        // Test with correct username and password
        edtUserName.setText("Mololuwa");
        edtPassword.setText("BleakMolo10");
        btnLogin.performClick();
        String expectedMessage = "Welcome Mololuwa";
        String actualMessage = ShadowToast.getTextOfLatestToast();
        assertEquals(expectedMessage, actualMessage);

        // Test with incorrect username and password
        edtUserName.setText("InvalidUsername");
        edtPassword.setText("InvalidPassword");
        btnLogin.performClick();

        String expectedErrorMessage = "Wrong Username/Password Combination";
        String actualErrorMessage = ShadowToast.getTextOfLatestToast();
        assertEquals(expectedErrorMessage, actualErrorMessage);
    }
}