package de.rocks.jsdevelopment.betmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Cazzador on 04.06.2015.
 * Model for a Bet
 */
public class BetItem implements Serializable {

    private final String LOG_TAG = "Wetten BetItem";

    public Integer ID;
    public String Title;
    public String Description;
    public Calendar Start;
    public Calendar End;
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

    //@Override //vllt unnötig.
    /*public String toString() {
        return this.ID + ". " + this.Title;
    }*/

    public String getPeriod() {
        Log.d(LOG_TAG, "--- getPeriod start - end ---");
        return this.getStart() + " - " + this.getEnd();
    }

    public String getStart()
    {
        Log.d(LOG_TAG, "--- getStart start - end ---");
        return getStart("dd-MM-yyyy");
    }

    public String getStart(String Format) {
        Log.d(LOG_TAG, "--- getStart mit Parametern start ---");
        SimpleDateFormat sdf = new SimpleDateFormat(Format);
        Log.d(LOG_TAG, "--- getStart mit Parametern start ---");
        return sdf.format(Start.getTime());
    }

    public String getEnd() {
        Log.d(LOG_TAG, "--- getEnd start - end ---");
        return getEnd("dd-MM-yyyy");
    }

    public String getEnd(String Format) {
        Log.d(LOG_TAG, "--- getEnd mit Parametern start ---");
        SimpleDateFormat sdf = new SimpleDateFormat(Format);
        Log.d(LOG_TAG, "--- getEnd mit Parametern start ---");
        return sdf.format(End.getTime());
    }

    public void Save(Context context) {
        Log.d(LOG_TAG, "--- Save start ---");

        DBHelper = new SQLiteHelper(context);
        DB = DBHelper.getWritableDatabase();

        if (ID == 0) {
            Create();
        } else {
            Update();
        }

        Log.d(LOG_TAG, "--- Save end ---");
    }

    private void Create() {

        Log.d(LOG_TAG,"--- Create start ---");

        ContentValues content = new ContentValues();

        content.clear();

        //content.put("_ID",2);
        content.put("Title", Title);
        content.put("Description", Description);
        content.put("Start", Start.getTimeInMillis());
        content.put("End", End.getTimeInMillis());

        DB.beginTransaction();

        try {

            DB.insert("Bets", null, content);

            DB.setTransactionSuccessful();
        } finally {
            DB.endTransaction();
        }
        Log.d(LOG_TAG,"--- Create end ---");
    }

    private void Update() {
        Log.d(LOG_TAG,"--- Update start ---");

        ContentValues content = new ContentValues();

        content.clear();

        //content.put("_ID",2);
        content.put("Title", Title);
        content.put("Description", Description);
        content.put("Start", Start.getTimeInMillis());
        content.put("End", End.getTimeInMillis());

        DB.beginTransaction();

        try {
            DB.update("Bets", content, "_ID = ?",
                    new String[]{String.valueOf(ID)});
            DB.setTransactionSuccessful();
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
        } finally {
            DB.endTransaction();
        }
        Log.d(LOG_TAG,"--- Delete end ---");
    }
}

