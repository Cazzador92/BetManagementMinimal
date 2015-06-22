package de.rocks.jsdevelopment.betmanagement.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.net.URI;
import java.util.TimeZone;

import de.rocks.jsdevelopment.betmanagement.model.BetItem;
import de.rocks.jsdevelopment.betmanagement.fragment.DatePicker;
import de.rocks.jsdevelopment.betmanagement.R;
import de.rocks.jsdevelopment.betmanagement.model.ContactList;


public class BetDetailActivity extends Activity {

    private static final int PICK_CONTACT_REQUEST_FOR = 1;
    private static final int PICK_CONTACT_REQUEST_AGAINST = 2;
    private static final String LOG_TAG = "Wetten BetDetailActivit";//23 Zeichen maximal.

    private EditText etStart, etEnd, etTitle, etDescription;
    private BetItem Bet;
    private Button bSaveBet, bEditForBet, bEditAgainstBet;
    private TextView tvForBet, tvAgainstBet;


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
        bEditAgainstBet = (Button) findViewById(R.id.buttonEditAgainstBet);
        tvAgainstBet = (TextView) findViewById(R.id.textViewAgainstBet);

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

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DialogFragment datePicker = DatePicker.newInstance(R.id.txtStartDate);
                    datePicker.show(getFragmentManager(), "OpenDatePicker");//

                    Bet.Start = ((DatePicker) datePicker).getCalendar();

                    etStart.selectAll();
                }
            }
        });

        etEnd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DialogFragment datePicker = DatePicker.newInstance(R.id.txtEndDate);
                    datePicker.show(getFragmentManager(), "OpenDatePicker");

                    //TODO unschoen besser ist es mit getter setter
                    Bet.End = ((DatePicker) datePicker).getCalendar();

                    etEnd.selectAll();
                }
            }
        });

        bSaveBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bet.Title = etTitle.getText().toString();
                Bet.Description = etDescription.getText().toString();

                Bet.Save(v.getContext());

                //TODO save people
                //TODO save calendar-entry data (id), we need this to update later
                //TODO delete update calendar entries if changed

                //TODO am anfang Kalender-Daten in der DB speichern
                //TODO als locale Variable setzen, damit nicht jedes mal abgefragt wird

                //TODO spaeter ueber Einstellungen Kallender auswaehlen koennen

                //load Calendar ID + Name
                Uri eventsUri, remainderUri;
                Cursor cursor;

                eventsUri = Uri.parse("content://com.android.calendar/events");
                remainderUri = Uri
                        .parse("content://com.android.calendar/reminders");

                Context mContext = getBaseContext();
                ContentResolver mContentResolver = mContext.getContentResolver();

                cursor = mContentResolver.query(
                        Uri.parse("content://com.android.calendar/calendars"),
                        new String[]{"_id", "calendar_displayName"}, null, null,
                        null);

                //store calendar id + name in variables
                Log.e("count", "" + cursor.getColumnName(0));
                String[] calendarNames = new String[cursor.getCount()];
                String[] calendarId = new String[cursor.getCount()];
                cursor.moveToFirst();
                for (int i = 0; i < calendarNames.length; i++) {
                    calendarId[i] = cursor.getString(0);
                    calendarNames[i] = cursor.getString(1);
                    cursor.moveToNext();

                }

                //create event
                long startCalTime;
                long endCalTime;

                TimeZone timeZone = TimeZone.getDefault();

                startCalTime = Bet.getCalendarEnd().getTimeInMillis();
                endCalTime = Bet.getCalendarEnd().getTimeInMillis();

                ContentValues event = new ContentValues();
                event.put(CalendarContract.Events.CALENDAR_ID, calendarId[0]);
                event.put(CalendarContract.Events.TITLE, Bet.getTitle());
                event.put(CalendarContract.Events.DESCRIPTION, Bet.getDescription());
                event.put(CalendarContract.Events.EVENT_LOCATION, "");
                event.put(CalendarContract.Events.DTSTART, startCalTime);
                event.put(CalendarContract.Events.DTEND, endCalTime);
                event.put(CalendarContract.Events.STATUS, 1);                       // 0 for tentative, 1 for confirmed, 2 for canceled
                event.put(CalendarContract.Events.HAS_ALARM, 1);
                event.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());

                //TODO Die timeZone pruefen
                //TODO alle Wetten mit 12:00 H abspeichern
                //TODO spaeter zusaetzlich moeglichkeit hinzufuegen die Uhrzeit zu setzen

                //save event
                Uri insertEventUri = mContentResolver.insert(eventsUri, event);

                //create reminder
                ContentValues reminders = new ContentValues();
                reminders.put(CalendarContract.Reminders.EVENT_ID,
                        Long.parseLong(insertEventUri.getLastPathSegment()));
                reminders.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
                reminders.put(CalendarContract.Reminders.MINUTES, 10);

                //save reminder
                mContentResolver.insert(remainderUri, reminders);

                finish();//Returns back to main activity.
            }
        });

        bEditForBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Intent pickContactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

               // startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST_FOR);
                ContactList Contacts = new ContactList(v.getContext());

                //TODO Besprechen wie man hier am besten vorgeht -> Activity die ausgewählte zurückgibt?
                Toast.makeText(getBaseContext(), "Kontakte sind geladen werden aber noch nicht angezeigt.", Toast.LENGTH_LONG).show();
            }
        });

        bEditAgainstBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent pickContactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

                startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST_AGAINST);

            }
        });

        Log.d(LOG_TAG, "--- AddHandler end ---");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(LOG_TAG, "--- onActivityResult start ---");

        if (requestCode == PICK_CONTACT_REQUEST_FOR || requestCode == PICK_CONTACT_REQUEST_AGAINST) {
            if (resultCode == RESULT_OK) {

                //TODO UI (add, edit, ...) fuer Kontakte
                //TODO Die ausgewaehlte Kontakte auch speichern

                //TODO Auslagern

                //TODO evtl. die sachen lokal speichern

//
//                String[] projection = new String[]{
//                        ContactsContract.Contacts._ID,
//                        ContactsContract.Contacts.DISPLAY_NAME,
////                        ContactsContract.CommonDataKinds.Email.ADDRESS,
////                        ContactsContract.CommonDataKinds.Phone.NUMBER,
////                        ContactsContract.CommonDataKinds.Website.URL,
////                        ContactsContract.CommonDataKinds.Identity.IDENTITY,
////                        ContactsContract.CommonDataKinds.Organization.COMPANY
//                };
//
//                Cursor cursor = null;
//                String email = "";
//                try {
//                    Uri result = data.getData();
//                    Log.v(LOG_TAG, "Got a contact result: "
//                            + result.toString());
//
//                    // get the contact id from the Uri
//                    String id = result.getLastPathSegment();
//
//                    // query fuer alle Kontakte
////                    cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
////                            projection, null, null, null);
//
//                    cursor = getContentResolver().query(result,
//                            projection, null, null, null);
//
//                    String columns[] = cursor.getColumnNames();
//
//                    int indexId = cursor.getColumnIndex(ContactsContract.Contacts._ID);
//                    int indexName = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
//
////                    if (cursor.moveToFirst()) {
////                        while (cursor.isAfterLast() == false) {
////
////                            for (String column : columns) {
////                                try {
////
////                                    int index = cursor.getColumnIndex(column);
////                                    Log.v(LOG_TAG, "Column: " + column + " == ["
////                                            + cursor.getString(index) + "]");
////
////                                } catch (Exception e) {
////                                    Log.e(LOG_TAG, "Failed to get " + column, e);
////                                }
////                            }
////
//////                            if (id == cursor.getString(indexId)){
//////                                tvForBet.setText(cursor.getString(indexName));
//////                            }
////
////                            cursor.moveToNext();
////                        }
////                    } else {
////                        // no results actions
////                }
////
//                    if (cursor.moveToFirst()) {
//                        switch (requestCode) {
//                            case PICK_CONTACT_REQUEST_FOR:
//                                tvForBet.setText(cursor.getString(indexName));
//                                break;
//                            case PICK_CONTACT_REQUEST_AGAINST:
//                                tvAgainstBet.setText(cursor.getString(indexName));
//                                break;
//                        }
//                    }
//
//                } catch (Exception e) {
//                    Log.e(LOG_TAG, "Failed to get contact data", e);
//
//                } finally {
//                    if (cursor != null) {
//                        cursor.close();
//                    }
//                }

                String id = "", name, phone, email, hasPhone;
//                int idx;
//
//                Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
//                if (cursor.moveToFirst()) {
//                    idx = cursor.getColumnIndex(ContactsContract.Contacts._ID);
//                    id = cursor.getString(idx);
//
//                    idx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
//                    name = cursor.getString(idx);

//                }
//
//                Uri.Builder b = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, id).buildUpon();
//                b.appendPath(ContactsContract.Contacts.Entity.CONTENT_DIRECTORY);
//                Uri contactUri = b.build();


                Cursor cursor = null;

                    Uri result = data.getData();

                    // get the contact id from the Uri
                    id = result.getLastPathSegment();

                // Build the Entity URI.
                Uri.Builder b = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, id).buildUpon();
                b.appendPath(ContactsContract.Contacts.Entity.CONTENT_DIRECTORY);
                Uri contactUri = b.build();

// Create the projection (SQL fields) and sort order.
                String[] projection = {
                        ContactsContract.Contacts.Entity.RAW_CONTACT_ID,
                        ContactsContract.Contacts.Entity._ID,
                        ContactsContract.Contacts.Entity.DISPLAY_NAME,
                        ContactsContract.Contacts.Entity.DATA1,
                        ContactsContract.Contacts.Entity.MIMETYPE,
                };
                String sortOrder = ContactsContract.Contacts.Entity.DISPLAY_NAME + " ASC";
                cursor = getContentResolver().query(contactUri, projection, null, null, null);

                String mime;
                int mimeIdx = cursor.getColumnIndex(ContactsContract.Contacts.Entity.MIMETYPE);
                int dataIdx = cursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA1);
                if (cursor.moveToFirst()) {
                    do {
                        mime = cursor.getString(mimeIdx);
                        if (mime.equalsIgnoreCase(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {
                            email = cursor.getString(dataIdx);
                        }

                        // ...etc.
                    } while (cursor.moveToNext());
                }
            }
        }
        Log.d(LOG_TAG, "--- onActivityResult end ---");
    }
}
