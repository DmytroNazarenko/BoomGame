package com.dmytronazarenko.tictacboom.view;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dmytronazarenko.tictacboom.R;
import com.dmytronazarenko.tictacboom.presenter.GamePresenter;


public class BaseGameActivity extends Activity {
    public TextView phrase;
    public TextView phrase_position;
    public Button phrase_button;
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
        phrase_button = (Button) findViewById(R.id.button);
        //phrase_button.setHeight(phrase_button.getWidth());
        //phrase = (TextView) findViewById(R.id.wordTextView);
        phrase_position = (TextView) findViewById(R.id.posTextView);
        gp = new GamePresenter(this);
        //ImageView iv = (ImageView) findViewById(R.id.my_imageview);

//        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
//                phrase_button,
//                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
//                PropertyValuesHolder.ofFloat("scaleY", 1.2f));
//        scaleDown.setDuration(170);
//
//        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
//        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
//
//        scaleDown.start();
    }

}
