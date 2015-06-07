package de.rocks.jsdevelopment.betmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

/**
 * Created by Cazzador on 07.06.2015.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(Context context) {
        super(context, "BetManagementDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {

        ContentValues content = new ContentValues();

        DB.execSQL("DROP TABLE IF EXISTS Bets");//TODO Später rausnehmen wenn Werte nicht fest über den code eingegeben werden.

        DB.execSQL("CREATE TABLE IF NOT EXISTS Bets" +
                "  (_ID int AUTO_INCREMENT PRIMARY KEY," +
                "   Title Text NULL," +
                "   Description Text NULL," +
                "   Start Long NULL," + //Datum in Millisekunden (Long) speichern -> Einfacher + von Date & Calendar unterstützt.
                "   End Long NULL" +
                "    );");

        content.clear();
        content.put("_ID",1);
        content.put("Title","ErsterTitel");
        content.put("Description","ErsteBeschreibung");
        content.put("Start",System.currentTimeMillis());
        content.put("End",System.currentTimeMillis() + (86400000 * 10));// jetzt + 3 tage   86400000 -> 1 Tag in Millisekunden.

        DB.insert("Bets",null,content);

        content.clear();
        content.put("_ID",2);
        content.put("Title","ZweiterTitel");
        content.put("Description","ZweitBeschreibung");
        content.put("Start",System.currentTimeMillis() + (86400000 * 15));
        content.put("End",System.currentTimeMillis() + (86400000 * 30));
        DB.insert("Bets",null,content);
        //Connect schließen?
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Todo später nicht vergessen.
    }
}

