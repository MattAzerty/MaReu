package fr.melanoxy.mareu.ui;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.SeekBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.melanoxy.mareu.R;


import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

import static fr.melanoxy.mareu.utils.SeekBarProgressAction.setProgress;
import static fr.melanoxy.mareu.utils.YearSelectorAction.setYear;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import fr.melanoxy.mareu.ui.MaReuActivity;
import fr.melanoxy.mareu.ui.NewReuActivity;
import fr.melanoxy.mareu.ui.newreu.DialogFragment;
import fr.melanoxy.mareu.utils.DeleteViewAction;
import fr.melanoxy.mareu.utils.SingleDateAndTimePickerAction;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class MaReuInstrumentedTest {

    private MaReuActivity mActivity;
    private final int resId = R.id.reunions_rv;
    private Date cstDate = new Date();
    Calendar calendar = Calendar.getInstance();


    @Rule
    //Start activity
    public ActivityScenarioRule<MaReuActivity> activityScenarioRule = new ActivityScenarioRule<>(MaReuActivity.class);


    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myReunionList_shouldNotBeEmpty() {

        onView(withId(R.id.reunions_rv))
                .check(matches(hasMinimumChildCount(3)));
    }


    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myReunionList_deleteAction_shouldRemoveItem() {
        // When perform a click on a delete icon
        onView(withId(R.id.reunions_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 2
        onView(withId(R.id.reunions_rv))
                .check(matches(hasMinimumChildCount(2)));
    }

    // When we click on add a new reunion item the activity MaReuActivity is shown
    @Test
    public void myNeighboursList_NewReuActivity_Started() {
        Intents.init();
//Click on the fb
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.activity_main_fab_add_reu),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

//NewReuActivity started ?
    intended(hasComponent(NewReuActivity.class.getName()));
        Intents.release();
}

    // Add a new reu with specific infos then check if all is working
    @Test
    public void Add_a_new_reu_is_working() {

        calendar.setTime(cstDate);
        //set to next month
        calendar.add(Calendar.MONTH, 1);
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 2, 30, 00);
        cstDate = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String strDate = dateFormat.format(cstDate);

//Check ini reu
        onView(withId(R.id.reunions_rv))
                .check(matches(hasMinimumChildCount(3)));

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.activity_main_fab_add_reu),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        //Add a subject to the new reunion

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.subject),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.subjectLyt),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("TEST_NEW_REU"), closeSoftKeyboard());

//Put Saturn room on the spinner
        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.new_reu_spinner_place),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                5)));
        appCompatSpinner.perform(scrollTo(), click());

        DataInteraction materialTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(8);
        materialTextView.perform(click());

        //Set a date on the singleDateAndTimePicker

        onView(withClassName(Matchers.equalTo(SingleDateAndTimePicker.class.getName())))
                .perform(SingleDateAndTimePickerAction.setDate(cstDate));


        ViewInteraction materialButton = onView(
                allOf(withId(R.id.new_reu_add_people_button), withText("Ajouter un participant"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                9)));
        materialButton.perform(scrollTo(), click());


        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.input),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("test@lamzone.com"), closeSoftKeyboard());

        //Check if DialogFragment visible
        onView(withId(R.id.fragment_root))
                .check(matches(isDisplayed()));

        ViewInteraction materialTextView2 = onView(
                allOf(withId(R.id.action_ok), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                3),
                        isDisplayed()));
        materialTextView2.perform(click());


        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.new_reu_add_reu_button), withText("AJOUTER"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                13)));
        materialButton2.perform(scrollTo(), click());


        onView(new RecyclerViewMatcher(this.resId)
                .atPositionOnView(3, R.id.neighbours_item_tv_fieldBottom))
                .check(matches(withText("test@lamzone.com;")));

        onView(new RecyclerViewMatcher(this.resId)
                .atPositionOnView(3, R.id.reunions_item_tv_fieldTop))
                .check(matches(withText("TEST_NEW_REU - 02h30 ["+strDate+"] - Saturn")));

    }


    @Test
    public void filter_a_reu_on_date_is_working() {

        //Set REuA Date (month is -1 on wheel picker)
        calendar.set(2022,00, 01, 2, 30, 00);
        Date dateReuA = calendar.getTime();

        //Check if default reunions are here
        onView(withId(R.id.reunions_rv))
                .check(matches(hasMinimumChildCount(3)));

        //click on filter toolbar button
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.filter), withContentDescription("Filter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        0),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());



        //Put date for REUA

        ViewInteraction linearLayout = onView(
                allOf(withId(R.id.single_day_picker),
                        withParent(allOf(withId(R.id.relativeLayout),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class)))),
                        isDisplayed()));
        linearLayout.check(matches(isDisplayed()));
        linearLayout.perform(SingleDateAndTimePickerAction.setDate(dateReuA));
        linearLayout.perform(ViewActions.swipeUp());
        linearLayout.perform(ViewActions.swipeDown());


        //Set filter type to date
        ViewInteraction seekBar = onView(
                allOf(withId(R.id.sb),
                        withParent(allOf(withId(R.id.relativeLayout),
                                withParent(IsInstanceOf.instanceOf(android.widget.ScrollView.class)))),
                        isDisplayed()));
        seekBar.check(matches(isDisplayed()));
        seekBar.perform(setProgress(1));


        //click on filter toolbar button
        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.filter), withContentDescription("Filter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        0),
                                0),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        //Check if default reunions are here
        onView(withId(R.id.reunions_rv))
                .check(matches(hasMinimumChildCount(1)));

        onView(new RecyclerViewMatcher(this.resId)
                .atPositionOnView(0, R.id.reunions_item_tv_fieldTop))
                .check(matches(withText("Réunion A - 14h00 [2022.01.01] - Soleil")));


    }


    @Test
    public void filter_a_reu_on_place_is_working() {

        //click on filter toolbar button
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.filter), withContentDescription("Filter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        0),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        //Set filter type to place
        ViewInteraction seekBar = onView(
                allOf(withId(R.id.sb),
                        withParent(allOf(withId(R.id.relativeLayout),
                                withParent(IsInstanceOf.instanceOf(android.widget.ScrollView.class)))),
                        isDisplayed()));
        seekBar.check(matches(isDisplayed()));
        seekBar.perform(setProgress(2));

        //Select room Etoile in order to filter ReuB
        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.spinner_room),
                        childAtPosition(
                                allOf(withId(R.id.relativeLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                10)));
        appCompatSpinner.perform(scrollTo(), click());

        DataInteraction materialTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        materialTextView.perform(click());


        //click on filter toolbar button
        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.filter), withContentDescription("Filter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        0),
                                0),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        //Check if there is only one reunion
        onView(withId(R.id.reunions_rv))
                .check(matches(hasMinimumChildCount(1)));

        // and if it the right one
        onView(new RecyclerViewMatcher(this.resId)
                .atPositionOnView(0, R.id.reunions_item_tv_fieldTop))
                .check(matches(withText("Réunion B - 08h00 [2022.02.02] - Etoile")));


    }

    @Test
    public void filter_a_reu_on_date_and_place_is_working() {

        //Set REuC Date (month is -1 on wheel picker)
        calendar.set(2022,02, 03, 2, 30, 00);
        Date dateReuC = calendar.getTime();


        //click on filter toolbar button
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.filter), withContentDescription("Filter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        0),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        //Set filter type to both
        ViewInteraction seekBar = onView(
                allOf(withId(R.id.sb),
                        withParent(allOf(withId(R.id.relativeLayout),
                                withParent(IsInstanceOf.instanceOf(android.widget.ScrollView.class)))),
                        isDisplayed()));
        seekBar.check(matches(isDisplayed()));
        seekBar.perform(setProgress(3));

        //Select room Etoile in order to filter ReuC (TODO)
        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.spinner_room),
                        childAtPosition(
                                allOf(withId(R.id.relativeLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                10)));
        appCompatSpinner.perform(scrollTo(), click());

        DataInteraction materialTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(2);
        materialTextView.perform(click());

        //Put date for REUC
        ViewInteraction linearLayout = onView(
                allOf(withId(R.id.single_day_picker),
                        withParent(allOf(withId(R.id.relativeLayout),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class)))),
                        isDisplayed()));
        linearLayout.check(matches(isDisplayed()));
        linearLayout.perform(SingleDateAndTimePickerAction.setDate(dateReuC));
        linearLayout.perform(ViewActions.swipeUp());
        linearLayout.perform(ViewActions.swipeDown());



        //click on filter toolbar button
        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.filter), withContentDescription("Filter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        0),
                                0),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        //Check if there is only one reunion
        onView(withId(R.id.reunions_rv))
                .check(matches(hasMinimumChildCount(1)));

        // and if it the right one
        onView(new RecyclerViewMatcher(this.resId)
                .atPositionOnView(0, R.id.reunions_item_tv_fieldTop))
                .check(matches(withText("Réunion C - 08h00 [2022.03.03] - Lune")));


    }

// END of tests


    public class RecyclerViewMatcher {

        private final int recyclerViewId;

        public RecyclerViewMatcher(int recyclerViewId) {
            this.recyclerViewId = recyclerViewId;
        }

        public Matcher<View> atPosition(final int position) {
            return atPositionOnView(position, -1);
        }

        public Matcher<View> atPositionOnView(final int position, final int targetViewId) {
            return new TypeSafeMatcher<View>() {
                Resources resources = null;
                View childView;
                public void describeTo(Description description) {
                    String idDescription = Integer.toString(recyclerViewId);
                    if(this.resources != null) {
                        try {
                            idDescription = this.resources.getResourceName(recyclerViewId);
                        } catch (Resources.NotFoundException var4) {
                            idDescription = String.format("%s (resource name not found)",
                                    Integer.valueOf(recyclerViewId));
                        }
                    }
                    description.appendText("with id: " + idDescription);
                }

                public boolean matchesSafely(View view) {
                    this.resources = view.getResources();
                    if (childView == null) {
                        RecyclerView recyclerView = view.getRootView().findViewById(recyclerViewId);
                        if (recyclerView != null && recyclerView.getId() == recyclerViewId) {
                            childView = recyclerView.findViewHolderForAdapterPosition(position).itemView;
                        } else {
                            return false;
                        }
                    }
                    if (targetViewId == -1) {
                        return view == childView;
                    } else {
                        View targetView = childView.findViewById(targetViewId);
                        return view == targetView;
                    }
                }
            };
        }
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }


} // The end