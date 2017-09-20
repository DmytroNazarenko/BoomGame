package com.dmytronazarenko.tictacboom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Дмитрий on 13.08.2016.
 */
public class GamePresenter {
    private BaseGameActivity view;

    private SharedPreferences sPref;
    //Handler soundHandler;
    private WordHandler wordHandler;
    //Runnable stopPlaybackRun;
    private BombTimer timer;
    private Integer timerDuration;
    private boolean initialGame = true;

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
            explosionSound.start();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //if (initialGame){
                        Intent intent = new Intent(view.getApplicationContext(), ResultsActivity.class);
                        intent.putExtra("GAME_STATUS", 1);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        view.getApplicationContext().startActivity(intent);
                        view.finish();
//                    } else {
//                        view.setResult(Activity.RESULT_OK);
//                        view.finish();
//                    }
                }
            }, 2000);
        }
    }

    class PreliminateTimer extends CountDownTimer {
        private Integer leftTime = 3;
        public PreliminateTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            //bombSound.setLooping(true);
        }


        @Override
        public void onTick(long millisUntilFinished) {
            view.phrase.setText(leftTime.toString());
            leftTime--;
        }

        @Override
        public void onFinish() {
        }
    }

    class MainTask extends AsyncTask <String, String, String>{


        @Override
        protected String doInBackground(String... strings) {
            new PreliminateTimer(3000, 1000).start();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            new BombTimer(timerDuration, 100).start();
        }
    }
    public GamePresenter(BaseGameActivity activity) {
        this.view = activity;
        this.wordHandler = new WordHandler();
        this.timerDuration = (int) (Math.random() * 60 + 5) * 1000;
        //soundHandler = new Handler();
//        stopPlaybackRun = new Runnable() {
//
//            public void run(){
//                stopPlaying();
//
//                mp = MediaPlayer.create(view.getApplicationContext(), R.raw.bomb);
//                mp.start();
//
//                Intent intent = new Intent(activity, ResultsActivity.class);
//                intent.putExtra("GAME_STATUS", 1);
//                activity.startActivity(intent);
//                //view.btn.setEnabled(true);
//            }
//        };

    }
    
    public void wordsLoad(){
        sPref = view.getPreferences(Context.MODE_PRIVATE);
        if ( sPref.contains("key")){
            Set<String> set = sPref.getStringSet("key", null);
            ArrayList<String> al = new ArrayList<String>();
            al.addAll(set);
            wordHandler.setWords(al);
            SharedPreferences.Editor editor = sPref.edit();
            editor.remove("key");
            editor.apply();
        }
    }

    public void wordsSave(){
        sPref = view.getPreferences(Context.MODE_PRIVATE);
        Set<String> set = new HashSet<String>();
        set.addAll(wordHandler.getWords());
        SharedPreferences.Editor editor = sPref.edit();
        editor.putStringSet("key", set);
        editor.commit();
    }


    public void newRound(){
        final String[] mas = {"В начале","В конце", "Любое место"};

        timer = new BombTimer(timerDuration, 100);

        Log.v("BBB", "" + wordHandler.getWords().size());
        // new MainTask().execute();
        view.phrase_position.setText(mas[(int) (Math.random() * 3)].toUpperCase());
        view.phrase.setText(wordHandler.selectNewWord().toUpperCase());

        synchronized (timer){
            timer.start();
        }

    }

    public void stopPlaying() {
        if (timer != null) {
            timer.cancel();
        }
    }


}
