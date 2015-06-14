package de.rocks.jsdevelopment.betmanagement;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Sebastian on 14.06.2015.
 * Verwaltung für die Wettliste
 */
public class BetList {

    final String LOG_TAG = "Wetten BetList";

    private final String TABLE_BETS = "bets";

    private final String COL_ID = "_id";
    private final String COL_TITLE = "Title";
    private final String COL_DESCRIPTION = "Description";
    private final String COL_START = "Start";
    private final String COL_END = "End";

    private List<BetItem> mBetList;

    public BetList(Context context){
        mBetList = new ArrayList<BetItem>();
        Load(context);
    }

    public void Load(Context context)
    {
        BetItem Bet;

        Log.d(LOG_TAG, "--- Load start ---");
        SQLiteHelper Helper = new SQLiteHelper(context);
        SQLiteDatabase DB = Helper.getWritableDatabase();

        Cursor cursor = DB.query(TABLE_BETS, null, null, null, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Bet = new BetItem();

                    Calendar calStart = Calendar.getInstance();
                    Calendar calEnd = Calendar.getInstance();

                    calStart.setTimeZone(TimeZone.getDefault());
                    calEnd.setTimeZone(TimeZone.getDefault());

                    Bet.ID = cursor.getInt(cursor.getColumnIndex(COL_ID));
                    Bet.Title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
                    Bet.Description = cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION));
                    calStart.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(COL_START)));
                    calEnd.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(COL_END)));

                    mBetList.add(Bet);

                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        Log.d(LOG_TAG, "--- Load end ---");
    }

    public ArrayList<BetItem> getBetList(){
        Log.d(LOG_TAG, "--- getBetList start ---");
        ArrayList<BetItem> BetList = new ArrayList<BetItem>();
        BetList.addAll(this.mBetList);
        Log.d(LOG_TAG, "--- getBetList end ---");
        return BetList;
    }

    public BetItem getBetItem(int Position){
        Log.d(LOG_TAG, "--- getBetItem start - end ---");
        return mBetList.get(Position);
    }

    public void remove(int position){
        mBetList.remove(position);
    }
}
