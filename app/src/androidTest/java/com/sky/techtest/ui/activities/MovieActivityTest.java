package com.sky.techtest.ui.activities;

import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.sky.techtest.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by leslied on 11/03/2018.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MovieActivityTest {

    private String mSearchText;

    @Rule
    public ActivityTestRule<MovieActivity> mActivityRule = new ActivityTestRule<>(MovieActivity.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        mSearchText = "Test String";
    }

    @Test
    public void changeText_sameActivity() {

        onView(withId(R.id.action_search)).perform(click());
        // Type text and then press the button.

        onView(withId(R.id.edtSearch)).perform(typeText(mSearchText), closeSoftKeyboard());


        // Check that the text was changed.
        onView(withId(R.id.edtSearch)).check(matches(withText(mSearchText)));
    }

}
