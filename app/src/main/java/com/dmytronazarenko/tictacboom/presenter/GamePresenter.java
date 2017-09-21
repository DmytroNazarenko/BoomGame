package com.dmytronazarenko.tictacboom.presenter;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;

import com.dmytronazarenko.tictacboom.view.BaseGameActivity;
import com.dmytronazarenko.tictacboom.R;
import com.dmytronazarenko.tictacboom.view.ResultsActivity;
import com.dmytronazarenko.tictacboom.model.WordHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GamePresenter {
    private BaseGameActivity view;

    private SharedPreferences sPref;
    private WordHandler wordHandler;
    private BombTimer timer;
    private Integer timerDuration;
    private ObjectAnimator animation;
    class BombTimer extends CountDownTimer {
        MediaPlayer explosionSound = MediaPlayer.create(view.getApplicationContext(), R.raw.bomb);
        MediaPlayer bombSound = MediaPlayer.create(view.getApplicationContext(), R.raw.ticktock3);

        public BombTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            //bombSound.setLooping(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            bombSound.start();
        }

        @Override
        public void onFinish() {
            animation.cancel();
            explosionSound.start();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(view.getApplicationContext(), ResultsActivity.class);
                    intent.putExtra("GAME_STATUS", 1);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    view.getApplicationContext().startActivity(intent);
                    view.finish();
                }
            }, 2000);
        }
    }

    class PreliminateTimer extends CountDownTimer {
        private Integer leftTime = 3;
        public PreliminateTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            view.phrase_button.setText(leftTime.toString());
            leftTime--;
        }

        @Override
        public void onFinish() {
        }
    }

    public GamePresenter(BaseGameActivity activity) {
        this.view = activity;
        this.wordHandler = new WordHandler();
        this.timerDuration = (int) (Math.random() * 60 + 5) * 1000;

        this.animation = ObjectAnimator.ofPropertyValuesHolder(
                view.phrase_button,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        this.animation.setDuration(300);
        this.animation.setRepeatCount(ObjectAnimator.INFINITE);
        this.animation.setRepeatMode(ObjectAnimator.REVERSE);
    }
    
    public void wordsLoad(){
        sPref = view.getPreferences(Context.MODE_PRIVATE);
        if ( sPref.contains("key")){
            Set<String> set = sPref.getStringSet("key", null);
            ArrayList<String> al = new ArrayList<>();
            al.addAll(set);
            wordHandler.setWords(al);
            SharedPreferences.Editor editor = sPref.edit();
            editor.remove("key");
            editor.apply();
        }
    }

    public void wordsSave(){
        sPref = view.getPreferences(Context.MODE_PRIVATE);
        Set<String> set = new HashSet<>();
        set.addAll(wordHandler.getWords());
        SharedPreferences.Editor editor = sPref.edit();
        editor.putStringSet("key", set);
        editor.commit();
    }


    public void newRound(){
        final String[] mas = {"В начале","В конце", "Любое место"};

        new PreliminateTimer(3500, 1000).start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                timer = new BombTimer(timerDuration, 100);
                view.phrase_position.setText(mas[(int) (Math.random() * 3)].toUpperCase());
                view.phrase_button.setText(wordHandler.selectNewWord().toUpperCase());
                timer.start();
                animation.start();
            }
        }, 3500);


    }

    public void stopPlaying() {
        if (timer != null) {
            timer.cancel();
        }
        if (animation != null){
            animation.cancel();
        }
    }


}
