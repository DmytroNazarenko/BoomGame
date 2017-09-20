package com.dmytronazarenko.tictacboom;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Дмитрий on 13.08.2016.
 */
public class BaseGameActivity extends Activity {
    TextView phrase;
    TextView phrase_position;
    protected Button btn;
    GamePresenter gp;

    public void onClick(View view) {
        gp.newRound();
    }
    @Override
    protected void onPause() {
        super.onPause();
        gp.stopPlaying();
        //gp.soundHandler.removeCallbacks(gp.stopPlaybackRun);
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
        btn = (Button) findViewById(R.id.button);
        phrase = (TextView) findViewById(R.id.wordTextView);
        phrase_position = (TextView) findViewById(R.id.posTextView);
    }

    public void preStart(){

    }
}
