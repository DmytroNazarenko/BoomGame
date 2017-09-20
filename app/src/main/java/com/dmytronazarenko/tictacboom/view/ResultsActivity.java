package com.dmytronazarenko.tictacboom.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dmytronazarenko.tictacboom.R;
import com.dmytronazarenko.tictacboom.model.Players;
import com.dmytronazarenko.tictacboom.presenter.PlayerAdapter;

public class ResultsActivity extends AppCompatActivity {

    private Players players = new Players();
    private boolean SHOULD_SELECT_POINT = true;
    final boolean GAME_IS_OVER = true;
    final boolean GAME_IS_NOT_OVER = false;
    private ListView lv;
    private PlayerAdapter playerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        final boolean gameStatus = getIntent().getBooleanExtra("GAME_STATUS", GAME_IS_NOT_OVER);
        if (gameStatus == GAME_IS_OVER) {
            SHOULD_SELECT_POINT = true;
        }
        players.loadResults(this);

        lv = (ListView) findViewById(R.id.listView);
        playerAdapter = new PlayerAdapter(players);
        lv.setAdapter(playerAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (SHOULD_SELECT_POINT) {
                    SHOULD_SELECT_POINT = false;
                    playerAdapter.getItem(position).addPoint();
                    playerAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Вы уже выбрали проигравшего", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    public void nextRound(View view) {
        if (SHOULD_SELECT_POINT){
            Toast.makeText(this, "Выберите проигравшего", Toast.LENGTH_SHORT).show();
            return;
        }
        players.saveResults(this);
        Intent intent = new Intent(this, BaseGameActivity.class);

        startActivity(intent);
        this.finish();
    }
}