package de.rocks.jsdevelopment.betmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sebastian on 06.06.2015.
 */
public class BetAdapter extends ArrayAdapter<BetItem> {

    public BetAdapter(Context context,ArrayList<BetItem> bets ){
        super(context,0,bets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        BetItem Bet = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.betlistitem, parent, false);
        }

        // Lookup view for data population
        TextView ShortDescription = (TextView) convertView.findViewById(R.id.txtShortDescription);
        TextView Period = (TextView) convertView.findViewById(R.id.txtPeriod);
        ShortDescription.setText(Bet.ShortDescription);
        Period.setText(Bet.getPeriod());


        return convertView;
    }
}
