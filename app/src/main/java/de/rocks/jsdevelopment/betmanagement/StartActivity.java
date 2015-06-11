package de.rocks.jsdevelopment.betmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Debug;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class StartActivity extends ActionBarActivity {

    final String LOG_TAG = "Wetten StartActivity";

    private final String TABLE_BETS = "bets";

    private final String COL_ID = "_id";
    private final String COL_TITLE = "Title";
    private final String COL_DESCRIPTION = "Description";
    private final String COL_START = "Start";
    private final String COL_END = "End";

    private ListView LVBets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Log.d(LOG_TAG, "--- onCreate start ---");

        FillBetList();

        Log.d(LOG_TAG, "--- onCreate end ---");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(LOG_TAG, "--- onCreateOptionsMenu start ---");

        getMenuInflater().inflate(R.menu.menu_start, menu);

        Log.d(LOG_TAG, "--- onCreateOptionsMenu end ---");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "--- onOptionsItemSelected start ---");

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_bar_bet_add) {
            Toast.makeText(getBaseContext(), "Neue Wette erstellen", Toast.LENGTH_LONG).show();
            OpenBetDetails(new BetItem());
            return true;
        }

        Log.d(LOG_TAG, "--- onOptionsItemSelected end ---");

        return super.onOptionsItemSelected(item);
    }

    private void FillBetList() {
        Log.d(LOG_TAG, "--- FillBetList start ---");

        LVBets = (ListView) findViewById(R.id.LVBets);

        //ArrayList<BetItem> Bets = getBetList();
        //BetAdapter BetAdapter = new BetAdapter(this,getBetList());

        //Kurzer Klick zum bearbeiten.
        LVBets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BetItem Bet = (BetItem) parent.getAdapter().getItem(position);
                Toast.makeText(getBaseContext(), Bet.toString(), Toast.LENGTH_LONG).show();
                OpenBetDetails(Bet);
            }
        });

        //Lange Klicken = Loeschen einer Wette.
        LVBets.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                BetItem Bet = (BetItem) parent.getAdapter().getItem(position);
                Bet.Delete(view.getContext());
                return true;
            }
        });

        //Adapter setzen und laden.
        LVBets.setAdapter(new BetAdapter(this, getBetList()));

        Log.d(LOG_TAG, "--- FillBetList end ---");
    }

    private void OpenBetDetails(BetItem Bet) {
        Log.d(LOG_TAG, "--- OpenBetDetails start ---");

        Intent intent = new Intent(StartActivity.this, BetDetailActivity.class);
        intent.putExtra("BetItem", Bet);
        startActivity(intent);

        Log.d(LOG_TAG, "--- OpenBetDetails end ---");
    }


    //TODO Auslagern!!!

    /**
     * Returns the BetItems out of the Database.
     *
     * @return ArrayList<BetItem> BetList
     */
    private ArrayList<BetItem> getBetList() {
        Log.d(LOG_TAG, "--- getBetList start ---");

        SQLiteHelper Helper = new SQLiteHelper(this);
        SQLiteDatabase DB = Helper.getWritableDatabase();

        ArrayList<BetItem> BetList = new ArrayList<BetItem>();
        //BetItem muss da stehen kommt sonst zur NullPointerException.

        BetItem Bet;

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

                    BetList.add(Bet);
                } while (cursor.moveToNext());

            }
            cursor.close();
        }

        Log.d(LOG_TAG, "--- getBetList end ---");

        return BetList;
    }
}

//TODO 1: Hinzufuegen und loeschen testen.
//TODO 2: Beim lange klicken fragen ob die wette geloescht werden soll.
//TODO 3: Hinzufuegen von "Dafuer" und "Dagegen" um leute aufzuschreiben die mitmachen.
//TODO 4: restlichen TODO's pruefen.