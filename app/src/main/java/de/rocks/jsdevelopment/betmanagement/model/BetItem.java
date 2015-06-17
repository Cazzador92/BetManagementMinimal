package de.rocks.jsdevelopment.betmanagement.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.rocks.jsdevelopment.betmanagement.R;
import de.rocks.jsdevelopment.betmanagement.helper.SQLiteHelper;

/**
 * Created by Cazzador on 04.06.2015.
 * Model for a Bet
 */
public class BetItem implements Serializable {

    private final String LOG_TAG = "Wetten BetItem";

    //TODO protected + setter
    public Integer ID;
    public String Title;
    public String Description;
    public Calendar Start;
    public Calendar End;
    protected ArrayList<Contact> mPeoplesFor, mPeoplesAgainst;

    //TODO Auslagern!!!
    private SQLiteHelper DBHelper;
    private SQLiteDatabase DB;

    public BetItem() {
        super();
        Log.d(LOG_TAG, "--- BetItem start ---");
        ID = 0;
        Title = "";
        Description = "";
        Start = Calendar.getInstance();
        End = Calendar.getInstance();
        Log.d(LOG_TAG, "--- BetItem end ---");
    }

    public BetItem(Integer ID, String Title, String Description, Calendar Start, Calendar End) {
        super();
        Log.d(LOG_TAG, "--- BetItem mit Parametern start ---");
        this.ID = ID;
        this.Title = Title;
        this.Description = Description;
        this.Start = Start;
        this.End = End;

        Log.d(LOG_TAG, "--- BetItem mit Parametern end ---");
    }

    public String getTitle() {
        Log.d(LOG_TAG, "--- getTitle ---");
        return Title;
    }

    public String getDescription() {
        Log.d(LOG_TAG, "--- getDescription ---");
        return Description;
    }

    public String getPeriod() {
        Log.d(LOG_TAG, "--- getPeriod ---");
        return this.getStart() + " - " + this.getEnd();
    }

    public Calendar getCalendarStart() {
        Log.d(LOG_TAG, "--- getCalendarStart start - end ---");
        return Start;
    }

    public Calendar getCalendarEnd() {
        Log.d(LOG_TAG, "--- getCalendarEnd start - end ---");
        return End;
    }

    public String getStart() {
        Log.d(LOG_TAG, "--- getStart start - end ---");
        return getStart("dd-MM-yyyy");
    }

    public String getStart(String Format) {
        Log.d(LOG_TAG, "--- getStart mit Parametern ---");
        SimpleDateFormat sdf = new SimpleDateFormat(Format);
        return sdf.format(Start.getTime());
    }

    public String getEnd() {
        Log.d(LOG_TAG, "--- getEnd ---");
        return getEnd("dd-MM-yyyy");
    }

    public String getEnd(String Format) {
        Log.d(LOG_TAG, "--- getEnd mit Parametern ---");
        SimpleDateFormat sdf = new SimpleDateFormat(Format);
        return sdf.format(End.getTime());
    }

    public void Save(Context context) {
        Log.d(LOG_TAG, "--- Save start ---");

        DBHelper = new SQLiteHelper(context);
        DB = DBHelper.getWritableDatabase();

        if (ID == 0) {
            Create(context);
        } else {
            Update(context);
        }

        Log.d(LOG_TAG, "--- Save end ---");
    }

    private void Create(Context context) {

        Log.d(LOG_TAG,"--- Create start ---");

        ContentValues content = new ContentValues();

        content.clear();

        content.put("Title", Title);
        content.put("Description", Description);
        content.put("Start", Start.getTimeInMillis());
        content.put("End", End.getTimeInMillis());//End.getTimeInMillis()

        DB.beginTransaction();

        try {

            DB.insert("Bets", null, content);

            DB.setTransactionSuccessful();
            Toast.makeText(context, R.string.value_create, Toast.LENGTH_LONG).show();
        } finally {
            DB.endTransaction();
        }
        Log.d(LOG_TAG,"--- Create end ---");
    }

    private void Update(Context context) {
        Log.d(LOG_TAG,"--- Update start ---");

        ContentValues content = new ContentValues();

        content.clear();

        content.put("Title", Title);
        content.put("Description", Description);
        content.put("Start", Start.getTimeInMillis());
        content.put("End", End.getTimeInMillis());

        DB.beginTransaction();

        try {
            DB.update("Bets", content, "_ID = ?",
                    new String[]{String.valueOf(ID)});
            DB.setTransactionSuccessful();
            Toast.makeText(context,R.string.value_update, Toast.LENGTH_LONG).show();
        } finally {
            DB.endTransaction();
        }
        Log.d(LOG_TAG,"--- Update end ---");
    }

    public void Delete(Context context) {
        Log.d(LOG_TAG,"--- Delete start ---");
        DBHelper = new SQLiteHelper(context);
        DB = DBHelper.getWritableDatabase();

        DB.beginTransaction();
        try {
            DB.delete("Bets", "_ID = ?",
                    new String[]{String.valueOf(ID)});
            DB.setTransactionSuccessful();
            Toast.makeText(context,R.string.value_delete, Toast.LENGTH_LONG).show();
        } finally {
            DB.endTransaction();
        }
        Log.d(LOG_TAG,"--- Delete end ---");
    }
}

