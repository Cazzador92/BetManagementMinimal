package de.rocks.jsdevelopment.betmanagement;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class StartActivity extends ActionBarActivity implements AdapterView.OnItemLongClickListener,AdapterView.OnItemClickListener{

    final String LOG_TAG = "Wetten StartActivity";

    private ListView mLVBets;
    private BetList mBetList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Log.d(LOG_TAG, "--- onCreate start ---");
        FillBetList();
       Log.d(LOG_TAG, "--- onCreate end ---");
    }

    @Override
    public void onResume() {
        super.onResume();
        FillBetList();
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
            OpenBetDetails(new BetItem()); //TODO Details für neue Wette anzeigen
            return true;
        }

        Log.d(LOG_TAG, "--- onOptionsItemSelected end ---");

        return super.onOptionsItemSelected(item);
    }

    private void FillBetList() {
        Log.d(LOG_TAG, "--- FillBetList start ---");

        mBetList = new BetList(this);
        mLVBets = (ListView) findViewById(R.id.LVBets);

        //Klick zum bearbeiten.

        mLVBets.setOnItemClickListener(this);
        mLVBets.setOnItemLongClickListener(this);
        //Adapter setzen und laden.
        mLVBets.setAdapter(new BetListAdapter(this,mBetList.getBetList()));

        Log.d(LOG_TAG, "--- FillBetList end ---");
    }

    private void OpenBetDetails(BetItem Bet) {
        Log.d(LOG_TAG, "--- OpenBetDetails start ---");
        Intent intent = new Intent(StartActivity.this, BetDetailActivity.class);
        intent.putExtra("BetItem", Bet);
        startActivity(intent);
        Log.d(LOG_TAG, "--- OpenBetDetails end ---");
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(LOG_TAG,"--- onItemLongClick start ---");
        mBetList.remove(position);
        mLVBets.setAdapter(new BetListAdapter(this, mBetList.getBetList()));
        Log.d(LOG_TAG, "--- onItemLongClick end ---");
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(LOG_TAG, "--- onItemClick start ---");
        BetItem Bet = (BetItem) parent.getAdapter().getItem(position);
        Toast.makeText(getBaseContext(), Bet.toString(), Toast.LENGTH_LONG).show();
        OpenBetDetails(Bet);
        Log.d(LOG_TAG, "--- onItemClick end ---");
    }
}

//TODO 3: Hinzufuegen von "Dafuer" und "Dagegen" um leute aufzuschreiben die mitmachen.
//TODO 4: restlichen TODO's pruefen.