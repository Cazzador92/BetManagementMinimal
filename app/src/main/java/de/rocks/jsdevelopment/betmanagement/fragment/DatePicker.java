package de.rocks.jsdevelopment.betmanagement.fragment;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Cazzador on 24.01.2015.
 * Class for a DatePickerobject.
 */
public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private static final String ARG_YEAR = "Year";
    private static final String ARG_MONTH = "Month";
    private static final String ARG_DAY = "Day";
    private static final String ARG_HOUR = "Hour";
    private static final String ARG_MINUTE = "Minute";
    private static final String ARG_TARGET_FIELD = "TargetFieldID";
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private final String LOG_TAG = "Wetten DatePicker";
    protected Calendar calendar;
    protected int mYear, mMonth, mDay;
    protected int mHour, mMinute;
    protected int TargetFieldID;//Zielfeld für das Datum.

    //TODO user should input time later


    //empty public constructor
    public DatePicker() {
        // Required empty public constructor
    }

    //static function to create new instance of date-picker
    public static DatePicker newInstance() {
        DatePicker fragment = new DatePicker();
        Bundle args = new Bundle();

        Calendar calendar = Calendar.getInstance();

        args.putInt(ARG_YEAR, calendar.get((Calendar.YEAR)));
        args.putInt(ARG_MONTH, calendar.get((Calendar.MONTH)));
        args.putInt(ARG_DAY, calendar.get((Calendar.DAY_OF_MONTH)));
        args.putInt(ARG_HOUR, 12);
        args.putInt(ARG_MINUTE, 0);
        args.putInt(ARG_TARGET_FIELD, 0);

        fragment.setArguments(args);

        return fragment;
    }

    //static function to create new instance of date-picker
    public static DatePicker newInstance(Integer TargetFieldID) {
        DatePicker fragment = new DatePicker();
        Bundle args = new Bundle();

        args.putInt(ARG_YEAR, Calendar.YEAR);
        args.putInt(ARG_MONTH, Calendar.MONTH);
        args.putInt(ARG_DAY, Calendar.DAY_OF_MONTH);
        args.putInt(ARG_HOUR, 12);
        args.putInt(ARG_MINUTE, 0);
        args.putInt(ARG_TARGET_FIELD, TargetFieldID);

        fragment.setArguments(args);

        return fragment;
    }

    // get args from bundle
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        Log.d(LOG_TAG,"--- onCreateDialog start ---");

        EditText dateField = null;
        calendar = Calendar.getInstance();

        if (getArguments() != null) {

            this.TargetFieldID = getArguments().getInt(ARG_TARGET_FIELD);

            if (TargetFieldID != 0) {

                DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                dateField = (EditText) getActivity().findViewById(TargetFieldID);

                String dateString = dateField.getText().toString();
                Date date = null;

                try {
                    //TODO search another way (depricated)
                    date = dateFormat.parse(dateString);
                    calendar.setTime(date);

                } catch (ParseException e) {
                    e.printStackTrace();

                } catch (Exception exception) {
                    exception.printStackTrace();
                    // Generic catch.
                }

            }

            //TODO later with user input (or settings)
            calendar.set(Calendar.HOUR, 12);
            calendar.set(Calendar.MINUTE, 0);

            this.mYear = calendar.get((Calendar.YEAR));
            this.mMonth = calendar.get((Calendar.MONTH));
            this.mDay = calendar.get((Calendar.DAY_OF_MONTH));
            this.mHour = calendar.get((Calendar.HOUR));
            this.mMinute = calendar.get((Calendar.MINUTE));

        } else {

            Log.d(LOG_TAG, "!!! should never be called !!!");

            this.calendar = Calendar.getInstance();
            this.mYear = calendar.get((Calendar.YEAR));
            this.mMonth = calendar.get((Calendar.MONTH));
            this.mDay = calendar.get((Calendar.DAY_OF_MONTH));
            this.mHour = 12;
            this.mMinute = 0;
            this.TargetFieldID = 0;

        }

        Log.d(LOG_TAG,"--- onCreateDialog end ---");
        if (dateField != null) {
            dateField.clearFocus();
        }
        return new DatePickerDialog(getActivity(), this, mYear, mMonth, mDay);
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {
        Log.d(LOG_TAG, "--- onDateSet start ---");
        this.mYear = year;
        this.mMonth = monthOfYear;
        this.mDay = dayOfMonth;

        EditText Field = (EditText) getActivity().findViewById(TargetFieldID);

        calendar.set(this.mYear, this.mMonth, this.mDay);

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

        Field.setText(sdf.format(calendar.getTime()));
        Field.clearFocus();
        Log.d(LOG_TAG, "--- onDateSet end ---");
    }


    public Calendar getCalendar() {
        Log.d(LOG_TAG,"--- getCalendar start - end ---");
        return this.calendar;
    }
}