package com.dmytronazarenko.tictacboom;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ExtendedGameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        final MediaPlayer mp = MediaPlayer.create(this, R.raw.ticktock3);


        class MyCountDownTimer extends CountDownTimer {

            public MyCountDownTimer(long millisInFuture, long countDownInterval) {
                super(millisInFuture, countDownInterval);
            }

            @Override
            public void onTick(long millisUntilFinished) {
                mp.start();
            }

            @Override
            public void onFinish() {
                finish();
            }
        }

        new MyCountDownTimer(10000, 300).start();
    }
}
