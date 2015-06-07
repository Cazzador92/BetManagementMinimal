package de.rocks.jsdevelopment.betmanagement;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;


public class StartActivity extends ActionBarActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        FillBetList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
        ListView LVBets = (ListView) findViewById(R.id.LVBets);

        ArrayList<BetItem> Bets = new ArrayList<BetItem>();
        Bets.add(new BetItem(1,"Julian Spezial Kaffee","Langertext",new Date(2014,1,1),new Date(2014,2,1)));
        Bets.add(new BetItem(2,"Wettverwealtung wird nie fertig","Langertext2",new Date(2014,3,1),new Date(2014,4,1)));
        Bets.add(new BetItem(3,"Kuchenwettessen 3.0","Langertext3",new Date(2014,6,1),new Date(2014,6,1)));
        BetAdapter BetAdapter = new BetAdapter(this,Bets);

        LVBets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                              BetItem Bet = (BetItem)parent.getAdapter().getItem(position);

                                              Toast.makeText(getBaseContext(), Bet.toString(), Toast.LENGTH_LONG).show();

                                              OpenBetDetails(Bet);
                                          }
                                      });

        LVBets.setAdapter(BetAdapter);


    }

    private void OpenBetDetails(BetItem Bet){

        Intent intent = new Intent(StartActivity.this, BetDetailActivity.class);
        intent.putExtra("BetItem", Bet);
        startActivity(intent);
    }

/*
    @Override
    public void onFragmentInteraction(Uri uri) {
        String bla = "Localhost";
    }
    */
/*
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        menu.clear(); //Clear view of previous menu
        MenuInflater inflater = getMenuInflater();
        //if(condition_true)
          //  inflater.inflate(R.menu.menu_one, menu);
        //else
            inflater.inflate(R.menu.menu_start, menu);
        return super.onPrepareOptionsMenu(menu);
    }
    */
}