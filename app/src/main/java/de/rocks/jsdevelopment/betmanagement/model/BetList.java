package de.rocks.jsdevelopment.betmanagement.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import de.rocks.jsdevelopment.betmanagement.helper.SQLiteHelper;

/**
 * Created by Sebastian on 14.06.2015.
 * Verwaltung fuer die Wettliste
 */
public class BetList {

    final String LOG_TAG = "Wetten BetList";

    private final String TABLE_BETS = "bets";

    private final String COL_ID = "_id";
    private final String COL_TITLE = "Title";
    private final String COL_DESCRIPTION = "Description";
    private final String COL_START = "Start";
    private final String COL_END = "End";
    private Context mcontext;

    private List<BetItem> mBetList;

    public BetList(Context context){
        mBetList = new ArrayList<BetItem>();
        Load(context);
    }

    public void Load(Context context)
    {
        Log.d(LOG_TAG, "--- Load start ---");
        BetItem Bet;
        mcontext = context;

        SQLiteHelper Helper = new SQLiteHelper(context);
        SQLiteDatabase DB = Helper.getWritableDatabase();

        Cursor cursor = DB.query(TABLE_BETS, null, null, null, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Bet = new BetItem();

                    Bet.ID = cursor.getInt(cursor.getColumnIndex(COL_ID));
                    Bet.Title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
                    Bet.Description = cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION));
                    Bet.Start.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(COL_START)));
                    Bet.End.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(COL_END)));

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
        mBetList.get(position).Delete(mcontext);
        mBetList.remove(position);
    }

    public void add(BetItem Bet){
        mBetList.add(Bet);
    }

    public int Count(){
        return mBetList.size();
    }
}
