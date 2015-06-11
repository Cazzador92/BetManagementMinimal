package de.rocks.jsdevelopment.betmanagement;

//TODO Logs hinzufuegen

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Cazzador on 06.06.2015.
 */
public class BetAdapter extends ArrayAdapter<BetItem> {

    public BetAdapter(Context context, ArrayList<BetItem> bets) {
        super(context, 0, bets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BetItem Bet = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.betlistitem, parent, false);
        }

        TextView Title = (TextView) convertView.findViewById(R.id.txtListTitle);
        TextView Period = (TextView) convertView.findViewById(R.id.txtListPeriod);

        Title.setText(Bet.Title);
        Period.setText(Bet.getPeriod());

        return convertView;
    }
}
