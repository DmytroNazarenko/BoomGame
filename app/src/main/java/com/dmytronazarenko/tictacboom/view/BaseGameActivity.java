package com.dmytronazarenko.tictacboom.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dmytronazarenko.tictacboom.R;
import com.dmytronazarenko.tictacboom.presenter.GamePresenter;


public class BaseGameActivity extends Activity {
    public TextView phrase;
    public TextView phrase_position;
    GamePresenter gp;


    @Override
    protected void onPause() {
        super.onPause();
        gp.stopPlaying();
        gp.wordsSave();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gp.wordsLoad();
        gp.newRound();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gp = new GamePresenter(this);
        phrase = (TextView) findViewById(R.id.wordTextView);
        phrase_position = (TextView) findViewById(R.id.posTextView);
    }

}
