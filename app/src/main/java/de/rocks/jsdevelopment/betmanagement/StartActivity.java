package de.rocks.jsdevelopment.betmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

public class StartActivity extends ActionBarActivity{

    private ListView LVBets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        FillBetList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_bar_bet_add){
            Toast.makeText(getBaseContext(), "Neue Wette erstellen", Toast.LENGTH_LONG).show();
            OpenBetDetails(new BetItem());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void FillBetList()
    {
        LVBets = (ListView) findViewById(R.id.LVBets);

        //ArrayList<BetItem> Bets = getBetList();
        //BetAdapter BetAdapter = new BetAdapter(this,getBetList());

        //Kurzer Klick zum bearbeiten.
        LVBets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                              BetItem Bet = (BetItem)parent.getAdapter().getItem(position);
                                              Toast.makeText(getBaseContext(), Bet.toString(), Toast.LENGTH_LONG).show();
                                              OpenBetDetails(Bet);
                                          }
                                      });

        //Lange Klicken = Löschen einer Wette.
        LVBets.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                BetItem Bet = (BetItem)parent.getAdapter().getItem(position);
                Bet.Delete(view.getContext());
                return true;
            }
        });

        //Adapter setzen und laden.
        LVBets.setAdapter(new BetAdapter(this,getBetList()));
    }

    private void OpenBetDetails(BetItem Bet){
        Intent intent = new Intent(StartActivity.this, BetDetailActivity.class);
        intent.putExtra("BetItem", Bet);
        startActivity(intent);
    }

    /**
     * Returns the BetItems out of the Database.
     * @return ArrayList<BetItem> BetList
     */
    private ArrayList<BetItem> getBetList(){
        SQLiteHelper Helper = new SQLiteHelper(this);
        SQLiteDatabase DB = Helper.getWritableDatabase();
        ArrayList<BetItem> BetList = new ArrayList<BetItem>();//BetItem muss da stehen kommt sonst zur NullPointerException.
        BetItem Bet;

        Cursor cursor = DB.query("Bets",null,null,null,null,null,null,null);

        if (cursor != null){
            if (cursor.moveToFirst()){
                do {
                    Bet = new BetItem();

                    Calendar calStart = Calendar.getInstance();
                    Calendar calEnd = Calendar.getInstance();

                    calStart.setTimeZone(TimeZone.getDefault());
                    calEnd.setTimeZone(TimeZone.getDefault());

                    Bet.ID = cursor.getInt(cursor.getColumnIndex("_ID"));
                    Bet.Title = cursor.getString(cursor.getColumnIndex("Title"));
                    Bet.Description = cursor.getString(cursor.getColumnIndex("Description"));
                    calStart.setTimeInMillis(cursor.getLong(cursor.getColumnIndex("Start")));
                    calEnd.setTimeInMillis(cursor.getLong(cursor.getColumnIndex("End")));

                    BetList.add(Bet);
                }while(cursor.moveToNext());

            }
            cursor.close();
        }

        return BetList;
    }
}

//TODO 1: Hinzufügen und löschen testen.
//TODO 2: Beim lange klicken fragen ob die wette gelöscht werden soll.
//TODO 3: Hinzufügen von "Dafür" und "Dagegen" um leute aufzuschreiben die mitmachen.
//TODO 4: restlichen TOdos prüfen.