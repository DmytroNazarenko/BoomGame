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
    }

    public void playOnClick(View view){
        Intent intent = new Intent(this, PlayersActivity.class);
        startActivity(intent);
    }

}
