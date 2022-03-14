package com.example.qrhunter;

import static org.junit.Assert.assertTrue;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ManageActivityTest {
    /**
     * Test class for RankActivity. All the UI tests are written here. Robotium test framework is
     used
     */
    private Solo solo;
    @Rule
    public ActivityTestRule<ManageActivity> rule =
            new ActivityTestRule<>(ManageActivity.class, true, true);

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
    public void checkSwitch(){
        solo.assertCurrentActivity("Wrong Activity", ManageActivity.class);
        solo.clickOnButton("Manage User"); //Click ADD CITY Button
        solo.assertCurrentActivity("Wrong Activity", DeleteUserActivity.class);
        solo.clickOnButton("Back");
        solo.assertCurrentActivity("Wrong Activity", ManageActivity.class);

        solo.clickOnButton("Manage Code"); //Click ADD CITY Button
        solo.assertCurrentActivity("Wrong Activity", DeleteCodesActivity.class);
        solo.clickOnButton("Back");
        solo.assertCurrentActivity("Wrong Activity", ManageActivity.class);
        solo.clickOnButton("Back");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    /**
     * test list UI
     */

    @Test
    public void checkList(){
        solo.assertCurrentActivity("Wrong Activity", ManageActivity.class);
        solo.clickOnButton("Manage User");
        solo.assertCurrentActivity("Wrong Activity", DeleteUserActivity.class);
        assertTrue(solo.searchText("1234"));
        solo.clickOnButton("Back");

        solo.clickOnButton("Manage Code");
        solo.assertCurrentActivity("Wrong Activity", DeleteCodesActivity.class);
        assertTrue(solo.searchText("1234"));
        solo.clickOnButton("Back");
        solo.assertCurrentActivity("Wrong Activity", ManageActivity.class);




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
