package com.example.qrhunter;

import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class SignInOutUpTest {
    /**
     * Test class for RankActivity. All the UI tests are written here. Robotium test framework is
     used
     */
    private Solo solo;
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }

    /**
     * Gets the Activity
     * @throws Exception
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    /**
     * Check whether activity correctly switched
     * 包括back 的button
     */

    @Test
    public void checkSignUp(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", SignupActivity.class);
        solo.enterText((EditText) solo.getView(R.id.txtUserSignup),  "new");
        //solo.enterText((EditText) solo.getView(R.id.txtPasswordSignup), "new01");
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", SigninActivity.class);
    }

    @Test
    public void checkSignInAndOut(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.txtAccount), "1234");
        //solo.enterText((EditText) solo.getView(R.id.txtPassword), "1234");
        solo.clickOnButton("Sign In"); //Click ADD CITY Button
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Sign Out");
        solo.clickOnText("Confirm");
        solo.assertCurrentActivity("Wrong Activity", SigninActivity.class);
    }







    /**
     * Closes the activity after each test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
