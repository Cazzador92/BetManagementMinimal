package de.rocks.jsdevelopment.betmanagement;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Cazzador on 06.06.2015.
 * Adapter for one Bet
 */
public class BetAdapter extends ArrayAdapter<BetItem> {

    final String LOG_TAG = "Wetten BetAdapter";

    public BetAdapter(Context context, ArrayList<BetItem> bets) {
        super(context, 0, bets);
        Log.d(LOG_TAG, "--- BetAdapter start - end ---");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d(LOG_TAG, "--- getView start ---");

        BetItem Bet = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.betlistitem, parent, false);
        }

        TextView Title = (TextView) convertView.findViewById(R.id.txtListTitle);
        TextView Period = (TextView) convertView.findViewById(R.id.txtListPeriod);

        Title.setText(Bet.Title);
        Period.setText(Bet.getPeriod());

        Log.d(LOG_TAG, "--- getView end ---");

        return convertView;
    }
}
