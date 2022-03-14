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

public class ProfileTest {
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
    public void checkSwitchAndContent(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.txtAccount), "123");
        solo.enterText((EditText) solo.getView(R.id.txtPassword), "123");
        solo.clickOnButton("Sign In");
        solo.clickOnButton("My Profile"); //Click ADD CITY Button
        solo.assertCurrentActivity("Wrong Activity",UserCode.class);
        assertTrue(solo.searchText("Total Score"));
        assertTrue(solo.searchText("Number"));
        assertTrue(solo.searchText("e34c6437-2d11-4bd7-90ce-d36ff32a419e"));
        solo.clickOnButton("back");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

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
