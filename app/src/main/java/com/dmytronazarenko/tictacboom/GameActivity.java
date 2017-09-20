//package com.dmytronazarenko.tictacboom;
//
//import android.content.SharedPreferences;
//import android.media.MediaPlayer;
//import android.os.CountDownTimer;
//import android.os.Handler;
//import android.support.v4.app.TaskStackBuilder;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Set;
//
//public class GameActivity extends AppCompatActivity {
//    MediaPlayer mp;
//    Boolean isPlaying;
//    TextView word;
//    TextView pos;
//    Button btn ;
//
//    SharedPreferences sPref;
//
//    Handler h;
//    WordHandler wh;
//    Runnable stopPlaybackRun;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_game);
//        word = (TextView)findViewById(R.id.wordTextView);
//        btn = (Button)findViewById(R.id.button);
//        pos = (TextView) findViewById(R.id.posTextView);
//        wh = new WordHandler();
//        h = new Handler();
//        stopPlaybackRun = new Runnable() {
//            public void run(){
//                stopPlaying();
//
//                //mp.release();
//                mp = MediaPlayer.create(getApplicationContext(), R.raw.bomb);
//                mp.start();
//                btn.setEnabled(true);
//            }
//        };
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        sPref = getPreferences(MODE_PRIVATE);
//        if ( sPref.contains("key")){
//            Set<String> set = sPref.getStringSet("key", null);
//            ArrayList<String> al = new ArrayList<String>();
//            al.addAll(set);
//            wh.setWords(al);
//            SharedPreferences.Editor editor = sPref.edit();
//            editor.remove("key");
//            editor.apply();
//        }
//        newRound();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        stopPlaying();
//        h.removeCallbacks(stopPlaybackRun);
//
//        Set<String> set = new HashSet<String>();
//        set.addAll(wh.getWords());
//        sPref = getPreferences(MODE_PRIVATE);
//        SharedPreferences.Editor ed = sPref.edit();
//        ed.putStringSet("key",set);
//        ed.commit();
//
//        overridePendingTransition( R.anim.top_in, R.anim.bottom_out);
//    }
//
//    public void onClick(View view) {
//        //bombSound.stop();
//        newRound();
//    }
//
//    private void newRound(){
//        String[] mas = {"В начале","В конце", "Любое место"};
//        btn.setEnabled(false);
//
//        Log.v("BBB", "" + wh.getWords().size());
//        pos.setText(mas[(int) (Math.random() * 3)]);
//        word.setText(wh.selectNewWord());
//        stopPlaying();
//        mp = MediaPlayer.create(getApplicationContext(), R.raw.ticktock3);
//        mp.setLooping(true);
//        mp.start();
////        Runnable stopPlaybackRun = new Runnable() {
////            public void run(){
////                stopPlaying();
////
////                //mp.release();
////                mp = MediaPlayer.create(getApplicationContext(), R.raw.bomb);
////                mp.start();
////                btn.setEnabled(true);
////            }
////        };
//        h.postDelayed(stopPlaybackRun, (int)(Math.random()*90 + 5) * 1000);
//
//    }
//    private void stopPlaying() {
//        if (mp != null) {
//            mp.stop();
//            mp.release();
//            mp = null;
//        }
//    }
//}
