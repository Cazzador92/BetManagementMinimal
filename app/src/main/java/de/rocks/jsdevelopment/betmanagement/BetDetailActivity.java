package de.rocks.jsdevelopment.betmanagement;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class BetDetailActivity extends Activity {

    private EditText Start;
    private EditText End;
    private EditText Title;
    private EditText Description;
    private BetItem Bet;
    private Button SaveBet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betdetail);

        Bundle bundle = getIntent().getExtras();
        Bet = (BetItem) bundle.get("BetItem");

        //Objekte initialisieren.
        SetBetDetails();

        //Datepicker für Datumsfelder setzen.
        AddHandler();
    }

    private void SetBetDetails(){
        Title = (EditText) findViewById(R.id.txtTitle);
        Start = (EditText) findViewById(R.id.txtStartDate);
        End = (EditText) findViewById(R.id.txtEndDate);
        Description = (EditText) findViewById(R.id.txtDescription);
        SaveBet = (Button) findViewById(R.id.txtSave);

        Title.setText(Bet.Title);
        Start.setText(Bet.getStart());
        End.setText(Bet.getEnd());
        Description.setText(Bet.Description);
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

    public void AddHandler()
    {

        Start.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DialogFragment DF = new DatePicker(R.id.txtStartDate);
                    DF.show(getFragmentManager(), "OpenDatePicker");//
                    Bet.Start = ((DatePicker)DF).getCalendar();

                    Start.setText("");
                }
            }
        });

        End.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DialogFragment DF = new DatePicker(R.id.txtEndDate);
                    DF.show(getFragmentManager(), "OpenDatePicker");
                    Bet.Start = ((DatePicker)DF).getCalendar();

                    End.setText("");
                }
            }
        });

        SaveBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bet.Title = Title.getText().toString();
                Bet.Description = Description.getText().toString();

                Bet.Save(v.getContext());
            }
        });
    }
}
