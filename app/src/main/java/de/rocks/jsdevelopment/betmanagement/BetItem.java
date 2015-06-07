package de.rocks.jsdevelopment.betmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Cazzador on 04.06.2015.
 */
public class BetItem implements Serializable{

    public Integer ID;
    public String Title;
    public String Description;
    public Calendar Start;
    public Calendar End;
    private SQLiteHelper DBHelper;
    private SQLiteDatabase DB;

    public BetItem() {
        super();
        ID = 0;
        Title = "";
        Description = "";
        Start = Calendar.getInstance();
        End = Calendar.getInstance();
    }

    public BetItem(Integer ID, String Title, String Description, Calendar Start, Calendar End) {
        super();

        this.ID = ID;
        this.Title = Title;
        this.Description = Description;
        this.Start = Start;
        this.End = End;
    }

    @Override //vllt unnötig.
    public String toString() {
        return this.ID + ". " + this.Title;
    }

    public String getPeriod()
    {
        return this.getStart() + " - " + this.getEnd();
    }

    public String getStart(){
        return getStart("dd-MM-yyyy");
    }

    public String getStart(String Format){
        SimpleDateFormat sdf = new SimpleDateFormat(Format);
        return sdf.format(Start.getTime());
    }

    public String getEnd(){
        return getEnd("dd-MM-yyyy");
    }

    public String getEnd(String Format){
        SimpleDateFormat sdf = new SimpleDateFormat(Format);
        return sdf.format(End.getTime());
    }

    public void Save(Context context)
    {
        DBHelper = new SQLiteHelper(context);
        DB = DBHelper.getWritableDatabase();

        if(ID == 0){
            Create();
        }else{
            Update();
        }
    }

    private void Create()
    {
        ContentValues content = new ContentValues();

        content.clear();

        //content.put("_ID",2);
        content.put("Title",Title);
        content.put("Description",Description);
        content.put("Start",Start.getTimeInMillis());
        content.put("End", End.getTimeInMillis());

        DB.beginTransaction();

        try {
            DB.insert("Bets",null,content);
            DB.setTransactionSuccessful();
        }finally {
            DB.endTransaction();
        }
    }

    private void Update()
    {
        ContentValues content = new ContentValues();

        content.clear();

        //content.put("_ID",2);
        content.put("Title",Title);
        content.put("Description",Description);
        content.put("Start",Start.getTimeInMillis());
        content.put("End", End.getTimeInMillis());

        DB.beginTransaction();

        try {
            DB.update("Bets", content, "_ID = ?",
                    new String[]{String.valueOf(ID)});
            DB.setTransactionSuccessful();
        }finally {
            DB.endTransaction();
        }
    }

    public void Delete(Context context)
    {
        DBHelper = new SQLiteHelper(context);
        DB = DBHelper.getWritableDatabase();

        DB.beginTransaction();
        try {
            DB.delete("Bets","_ID = ?",
                    new String[]{String.valueOf(ID)});
            DB.setTransactionSuccessful();
        }finally {
            DB.endTransaction();
        }
    }

}

