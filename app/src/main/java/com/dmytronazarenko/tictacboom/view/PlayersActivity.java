package com.dmytronazarenko.tictacboom.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.dmytronazarenko.tictacboom.R;
import com.dmytronazarenko.tictacboom.model.Players;

import java.util.ArrayList;


public class PlayersActivity extends AppCompatActivity {

    // LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems=new ArrayList<>();

    // DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

    Players players = new Players();

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_players);
        adapter=new ArrayAdapter<>(this,
                R.layout.players_adapter,
                listItems);
        ListView lv = (ListView) findViewById(R.id.listView2);
        lv.setAdapter(adapter);
    }

    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    public void addItems(View v) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.custom_alert_dialog, null);
        alert.setView(dialogView);
        alert.setTitle("Введите имя");
        final EditText input = (EditText) dialogView.findViewById(R.id.edit1);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                String result = input.getText().toString().trim();
                if (!result.equals("")) {
                    listItems.add(result);
                    players.addPlayer(result, 0);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        AlertDialog alertToShow = alert.create();
        alertToShow.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        alertToShow.show();
    }


    public void startGame(View v){
        Intent intent = new Intent(this, BaseGameActivity.class);
        players.saveResults(this);
        startActivity(intent);
    }
}