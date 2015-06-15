package de.rocks.jsdevelopment.betmanagement.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import de.rocks.jsdevelopment.betmanagement.model.BetItem;
import de.rocks.jsdevelopment.betmanagement.helper.DatePicker;
import de.rocks.jsdevelopment.betmanagement.R;


public class BetDetailActivity extends Activity {

    static final int PICK_CONTACT_REQUEST = 1;
    final String LOG_TAG = "Wetten BetDetailActivit";//23 Zeichen maximal.
    private EditText etStart;
    private EditText etEnd;
    private EditText etTitle;
    private EditText etDescription;
    private BetItem Bet;
    private Button bSaveBet, bEditForBet;
    private TextView tvForBet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "--- onCreate start ---");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betdetail);

        Bundle bundle = getIntent().getExtras();
        Bet = (BetItem) bundle.get("BetItem");

        //Objekte initialisieren.
        SetBetDetails();

        //Datepicker für Datumsfelder setzen.
        AddHandler();

        Log.d(LOG_TAG, "--- onCreate end ---");
    }

    private void SetBetDetails() {
        Log.d(LOG_TAG, "--- SetBetDetails start ---");
        etTitle = (EditText) findViewById(R.id.txtTitle);
        etStart = (EditText) findViewById(R.id.txtStartDate);
        etEnd = (EditText) findViewById(R.id.txtEndDate);
        etDescription = (EditText) findViewById(R.id.txtDescription);
        bSaveBet = (Button) findViewById(R.id.txtSave);
        bEditForBet = (Button) findViewById(R.id.buttonEditForBet);
        tvForBet = (TextView) findViewById(R.id.textViewForBet);

        etTitle.setText(Bet.Title);
        etStart.setText(Bet.getStart());
        etEnd.setText(Bet.getEnd());
        etDescription.setText(Bet.Description);

        Log.d(LOG_TAG, "--- SetBetDetails end ---");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(LOG_TAG, "--- onCreateOptionsMenu start ---");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bet, menu);
        Log.d(LOG_TAG, "--- onCreateOptionsMenu end ---");
        return true;
    }

    public void AddHandler() {
        Log.d(LOG_TAG, "--- AddHandler start ---");
        etStart.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DialogFragment DF = new DatePicker(R.id.txtStartDate);
                    DF.show(getFragmentManager(), "OpenDatePicker");//
                    Bet.Start = ((DatePicker) DF).getCalendar();

                    etStart.setText("");
                }
            }
        });

        etEnd.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DialogFragment DF = new DatePicker(R.id.txtEndDate);
                    DF.show(getFragmentManager(), "OpenDatePicker");
                    Bet.End = ((DatePicker) DF).getCalendar();

                    etEnd.setText("");
                }
            }
        });

        bSaveBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bet.Title = etTitle.getText().toString();
                Bet.Description = etDescription.getText().toString();

                Bet.Save(v.getContext());
                finish();//Returns back to main activity.
            }
        });

        bEditForBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent pickContactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

                startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);

            }
        });

        Log.d(LOG_TAG, "--- AddHandler end ---");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(LOG_TAG, "--- onActivityResult start ---");

        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) {

//                Uri personUri = ContentUris.withAppendedId(Contacts.People.CONTENT_URI, personId);
//                Uri phonesUri = Uri.withAppendedPath(personUri, Contacts.People.Phones.CONTENT_DIRECTORY);
//                String[] proj = new String[] {Contacts.Phones._ID, Contacts.Phones.TYPE, Contacts.Phones.NUMBER, Contacts.Phones.LABEL}
//                Cursor cursor = contentResolver.query(phonesUri, proj, null, null, null);


                //TODO UI (add, edit, ...) fuer Kontakte
                //TODO Die ausgewaehlte Kontakte auch speichern
                //TODO genauso für die die dagegen sind
                //TODO Auslagern
                //TODO Pruefen wie wir mehr Informationen bekommen
                //TODO evtl. die sachen lokal speichern
                String[] projection = new String[]{
                        ContactsContract.Contacts._ID,
                        ContactsContract.Contacts.DISPLAY_NAME,
//                        ContactsContract.CommonDataKinds.Email.ADDRESS,
//                        ContactsContract.CommonDataKinds.Phone.NUMBER,
//                        ContactsContract.CommonDataKinds.Website.URL,
//                        ContactsContract.CommonDataKinds.Identity.IDENTITY,
//                        ContactsContract.CommonDataKinds.Organization.COMPANY
                };

                Cursor cursor = null;
                String email = "";
                try {
                    Uri result = data.getData();
                    Log.v(LOG_TAG, "Got a contact result: "
                            + result.toString());

                    // get the contact id from the Uri
                    String id = result.getLastPathSegment();

                    // query fuer alle Kontakte
//                    cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
//                            projection, null, null, null);

                    cursor = getContentResolver().query(result,
                            projection, null, null, null);

                    String columns[] = cursor.getColumnNames();

                    int indexId = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                    int indexName = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

//                    if (cursor.moveToFirst()) {
//                        while (cursor.isAfterLast() == false) {
//
//                            for (String column : columns) {
//                                try {
//
//                                    int index = cursor.getColumnIndex(column);
//                                    Log.v(LOG_TAG, "Column: " + column + " == ["
//                                            + cursor.getString(index) + "]");
//
//                                } catch (Exception e) {
//                                    Log.e(LOG_TAG, "Failed to get " + column, e);
//                                }
//                            }
//
////                            if (id == cursor.getString(indexId)){
////                                tvForBet.setText(cursor.getString(indexName));
////                            }
//
//                            cursor.moveToNext();
//                        }
//                    } else {
//                        // no results actions
//                }
//
                    if (cursor.moveToFirst()) {
                        tvForBet.setText(cursor.getString(indexName));
                    }

                } catch (Exception e) {
                    Log.e(LOG_TAG, "Failed to get contact data", e);

                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }

            }
        }
        Log.d(LOG_TAG, "--- onActivityResult end ---");
    }
}
