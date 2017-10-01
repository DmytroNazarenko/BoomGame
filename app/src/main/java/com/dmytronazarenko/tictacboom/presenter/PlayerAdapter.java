package com.dmytronazarenko.tictacboom.presenter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dmytronazarenko.tictacboom.R;
import com.dmytronazarenko.tictacboom.model.Players;

import java.util.ArrayList;


public class PlayerAdapter extends BaseAdapter {

    private final ArrayList mData;

    public PlayerAdapter(Players players) {
        mData = new ArrayList();
        mData.addAll(players.getPlayersList());
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Players.Player getItem(int position) {
        return (Players.Player) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO implement you own logic with ID
        return 0;
    }

    public void remove(int position){
        mData.remove(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;

        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_adapter_item, parent, false);
        } else {
            result = convertView;
        }

        Players.Player item = getItem(position);

        // TODO replace findViewById by ViewHolder
        ((TextView) result.findViewById(android.R.id.text1)).setText(item.getName());
        ((TextView) result.findViewById(android.R.id.text2)).setText(item.getPoints().toString());

        return result;
    }
}
