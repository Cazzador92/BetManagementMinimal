package de.rocks.jsdevelopment.betmanagement.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

/**
 * Created by Cazzador on 07.06.2015.
 * Basic Database Class
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    final String LOG_TAG = "Wetten SQLiteHelper";

    private final String DB_NAME = "BetManagementDB";

    private final String TABLE_BETS = "bets";

    private final String COL_ID = "_id";
    private final String COL_TITLE = "Title";
    private final String COL_DESCRIPTION = "Description";
    private final String COL_START = "Start";
    private final String COL_END = "End";

    public SQLiteHelper(Context context) {
        super(context, "BetManagementDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {

        Log.d(LOG_TAG, "--- onCreate start ---");

        ContentValues content = new ContentValues();

        //TODO Spaeter rausnehmen wenn Werte nicht fest über den code eingegeben werden.
        DB.execSQL("DROP TABLE IF EXISTS Bets");

        DB.execSQL("CREATE TABLE " + TABLE_BETS + " ( " +
                COL_ID + " INTEGER PRIMARY KEY, " +
                COL_TITLE + " Text NULL, " +
                COL_DESCRIPTION + " Text NULL, " +
                COL_START + " UNSIGNED bigint NULL, " + //Datum in Millisekunden (Long) speichern -> Einfacher + von Date & Calendar unterstützt.
                COL_END + " UNSIGNED bigint NULL " +
                " );");

        content.clear();
        content.put(COL_ID, 1);
        content.put(COL_TITLE, "ErsterTitel");
        content.put(COL_DESCRIPTION, "ErsteBeschreibung");
        content.put(COL_START, System.currentTimeMillis());
        content.put(COL_END, System.currentTimeMillis() + (86400000 * 10));//86400000 -> 1 Tag in Millisekunden.

        DB.insert(TABLE_BETS, null, content);

        content.clear();
        content.put(COL_ID, 2);
        content.put(COL_TITLE, "ZweiterTitel");
        content.put(COL_DESCRIPTION, "ZweitBeschreibung");
        content.put(COL_START, System.currentTimeMillis() + (86400000 * 15));
        content.put(COL_END, System.currentTimeMillis() + (86400000 * 30));

        DB.insert(TABLE_BETS, null, content);

        Log.d(LOG_TAG, "--- onCreate ende ---");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(LOG_TAG, "--- onUpgrade start ---");
        //TODO onUpgrade Methode ergaenzen sobald gebraucht wird
        Log.d(LOG_TAG, "--- onUpgrade ende ---");
    }
}

