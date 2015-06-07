package de.rocks.jsdevelopment.betmanagement;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class BetDetailActivity extends Activity {

    BetItem Bet;
    TextView tBetNr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betdetail);

        Bundle bundle = getIntent().getExtras();

        Bet = (BetItem) bundle.get("BetItem");
        /*
        tBetNr = (TextView)findViewById(R.id.txtPeriod);

        if (Bet.Nr == 0){
            tBetNr.setText("NEUE WETTE");
        }else
        {
            tBetNr.setText(Bet.getPeriod());
        }
*/


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bet, menu);
        return true;
    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
}
