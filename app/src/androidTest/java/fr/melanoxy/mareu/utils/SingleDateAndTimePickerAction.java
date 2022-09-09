package fr.melanoxy.mareu.utils;

import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.allOf;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;

import org.hamcrest.Matcher;

import java.util.Calendar;
import java.util.Date;

public final class SingleDateAndTimePickerAction {

    private SingleDateAndTimePickerAction() {
        // no Instance
    }

    /** Returns a {@link ViewAction} that sets a date on a {@link com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker}. */
    public static ViewAction setDate(final Date date) {


        return new ViewAction() {

            @Override
            public void perform(UiController uiController, View view) {
                final SingleDateAndTimePicker allInOnePickerDate = (SingleDateAndTimePicker) view;

                allInOnePickerDate.setDefaultDate(date);
            }

            @Override
            public String getDescription() {
                return "set date";
            }

            @SuppressWarnings("unchecked")
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isAssignableFrom(SingleDateAndTimePicker.class), isDisplayed());
            }
        };
    }

}