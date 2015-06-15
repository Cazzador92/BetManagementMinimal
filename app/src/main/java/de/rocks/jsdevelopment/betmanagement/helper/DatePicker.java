package de.rocks.jsdevelopment.betmanagement.helper;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Cazzador on 24.01.2015.
 * Class for a DatePickerobject.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private final String LOG_TAG = "Wetten DatePicker";

    protected Calendar calendar;
    protected int Year;
    protected int Month;
    protected int Day;
    protected int TargetFieldID;//Zielfeld für das Datum.

    public DatePicker() {
        Log.d(LOG_TAG,"--- DatePicker start ---");
        this.TargetFieldID = 0;
        this.calendar = Calendar.getInstance();
        this.Year = calendar.get((Calendar.YEAR));
        this.Month = calendar.get((Calendar.MONTH));
        this.Day = calendar.get((Calendar.DAY_OF_MONTH));
        Log.d(LOG_TAG,"--- DatePicker end ---");
    }

    //TODO Bundle nutzen. -> wie geht das, ist keine activity.
    public DatePicker(Integer TargetFieldID) {
        Log.d(LOG_TAG,"--- DatePicker with Parameter start ---");
        this.TargetFieldID = TargetFieldID;
        this.calendar = Calendar.getInstance();
        this.Year = calendar.get((Calendar.YEAR));
        this.Month = calendar.get((Calendar.MONTH));
        this.Day = calendar.get((Calendar.DAY_OF_MONTH));
        Log.d(LOG_TAG,"--- DatePicker with Parameter end ---");
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(LOG_TAG,"--- onCreateDialog start ---");
        //TargetFieldID = savedInstanceState.getInt("TargetFieldID"); -> Bundle muss iwie übergeben werden
        Log.d(LOG_TAG,"--- onCreateDialog end ---");
        return new DatePickerDialog(getActivity(), this, Year, Month, Day);
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {
        Log.d(LOG_TAG,"--- onDateSet start ---");
        this.Year = year;
        this.Month = monthOfYear;
        this.Day = dayOfMonth;

        EditText Field = (EditText) getActivity().findViewById(TargetFieldID);

        calendar.set(this.Year, this.Month, this.Day);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        Field.setText(sdf.format(calendar.getTime()));
        Field.clearFocus();
        Log.d(LOG_TAG, "--- onDateSet end ---");
    }


    public Calendar getCalendar() {
        Log.d(LOG_TAG,"--- getCalendar start - end ---");
        return this.calendar;
    }
}

