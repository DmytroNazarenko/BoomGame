package com.dmytronazarenko.tictacboom.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dmytronazarenko.tictacboom.R;

public class MainActivity extends AppCompatActivity {
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        img = (ImageView) findViewById(R.id.imageView);
        img.setImageResource(R.drawable.bomb);


        //intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    public void playOnClick(View view){
        //Intent intent = new Intent(this, BaseGameActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        Intent intent = new Intent(this, PlayersActivity.class);
        startActivity(intent);
        //overridePendingTransition(R.anim.top_out,R.anim.bottom_in);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
       // overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
    }
}
