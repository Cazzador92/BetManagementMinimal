package de.rocks.jsdevelopment.betmanagement;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Cazzador on 24.01.2015.
 */
public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    protected Calendar calendar;
    protected int Year;
    protected int Month;
    protected int Day;
    protected int TargetFieldID;//Zielfeld für das Datum.

    public DatePicker() {
        this.TargetFieldID = 0;
        this.calendar = Calendar.getInstance();
        this.Year = calendar.get((Calendar.YEAR));
        this.Month = calendar.get((Calendar.MONTH));
        this.Day = calendar.get((Calendar.DAY_OF_MONTH));
    }

    //TODO Bundle nutzen. -> wie geht das, ist keine activity.
    public DatePicker(Integer TargetFieldID) {
        this.TargetFieldID = TargetFieldID;
        this.calendar = Calendar.getInstance();
        this.Year = calendar.get((Calendar.YEAR));
        this.Month = calendar.get((Calendar.MONTH));
        this.Day = calendar.get((Calendar.DAY_OF_MONTH));
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //TargetFieldID = savedInstanceState.getInt("TargetFieldID");

        return new DatePickerDialog(getActivity(), this, Year, Month, Day);
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {

        this.Year = year;
        this.Month = monthOfYear;
        this.Day = dayOfMonth;

        EditText Field = (EditText) getActivity().findViewById(TargetFieldID);

        calendar.set(this.Year, this.Month, this.Day);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        Field.setText(sdf.format(calendar.getTime()));
        Field.clearFocus();
    }


    public Calendar getCalendar() {
        return this.calendar;
    }
}

