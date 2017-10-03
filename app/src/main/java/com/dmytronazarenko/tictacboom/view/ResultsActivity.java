package com.dmytronazarenko.tictacboom.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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
    private Integer maximumPenalty;

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

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getMaximumPenalty();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                if (SHOULD_SELECT_POINT) {
                    SHOULD_SELECT_POINT = false;
                    Players.Player player = playerAdapter.getItem(position);
                    player.addPoint();
                    if (player.getPoints() == maximumPenalty) {
                        final Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                                R.anim.slide_out);
                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                playerAdapter.remove(position);
                                players.deletePlayer(position);
                                playerAdapter.notifyDataSetChanged();
                                Log.v("Mytag", String.valueOf(playerAdapter.getCount()));
                                if (playerAdapter.getCount() == 1){
                                    showWinner(playerAdapter.getItem(0));

                                }
                            }
                        });
                        view.startAnimation(animation);
                        Toast.makeText(getApplicationContext(), player.getName() + " выбывает из игры :(", Toast.LENGTH_SHORT).show();
                    }

                    playerAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Вы уже выбрали проигравшего", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    private void showWinner(Players.Player player){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.winner_dialog, null);
        alert.setView(dialogView);
        TextView textView = (TextView) dialogView.findViewById(R.id.textView5);
        textView.setText(player.getName()+" победил!");
        AlertDialog alertToShow = alert.create();
        alertToShow.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        alertToShow.show();
    }



    private void getMaximumPenalty() {
        SharedPreferences mySharedPreferences = getSharedPreferences("boom", Context.MODE_PRIVATE);
        maximumPenalty = mySharedPreferences.getInt("maxpenalty", 1);
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