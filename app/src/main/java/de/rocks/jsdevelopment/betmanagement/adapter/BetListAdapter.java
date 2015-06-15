package de.rocks.jsdevelopment.betmanagement.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.rocks.jsdevelopment.betmanagement.R;
import de.rocks.jsdevelopment.betmanagement.model.BetItem;

/**
 * Created by Cazzador on 06.06.2015.
 * Adapter for one Bet
 */
public class BetListAdapter extends ArrayAdapter<BetItem> {

    private final String LOG_TAG = "Wetten BetAdapter";

    public BetListAdapter(Context context, ArrayList<BetItem> bets) {
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
