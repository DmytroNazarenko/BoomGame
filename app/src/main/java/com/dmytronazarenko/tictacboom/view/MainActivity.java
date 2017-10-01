package com.dmytronazarenko.tictacboom.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.dmytronazarenko.tictacboom.R;

public class MainActivity extends AppCompatActivity {

    private final String textToShow[] = {"БЫСТРАЯ ИГРА", "ПОЛНАЯ ИГРА"};
    private final int messageCount = textToShow.length;
    private Animation inLeft;
    private Animation outRight;
    private Animation inRight;
    private Animation outLeft;
    private int currentIndex = 0;
    private TextSwitcher mSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher);
        mSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                TextView myText = new TextView(MainActivity.this);
                myText.setTypeface(null, Typeface.BOLD);
                myText.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                myText.setTextSize(25);
                myText.setTextColor(Color.parseColor("#FF7043"));
                return myText;
            }
        });
        inLeft = AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left);
        outRight = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        inRight = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        outLeft = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);

        mSwitcher.setInAnimation(inLeft);
        mSwitcher.setOutAnimation(outRight);

        mSwitcher.setText(textToShow[currentIndex]);
    }

    public void playOnClick(View view){
        SharedPreferences preferences = getSharedPreferences("boom", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (currentIndex == 0)
            editor.putInt("maxpenalty", 5);
        else
            editor.putInt("maxpenalty", 10);
        editor.apply();

        Intent intent = new Intent(this, PlayersActivity.class);
        startActivity(intent);
    }

    public void nextOnClick(View view){
        mSwitcher.setInAnimation(inRight);
        mSwitcher.setOutAnimation(outLeft);
        if(currentIndex != messageCount - 1) {
            currentIndex++;
            mSwitcher.setText(textToShow[currentIndex]);
        }
    }

    public void previousOnClick(View view){
        mSwitcher.setInAnimation(inLeft);
        mSwitcher.setOutAnimation(outRight);
        if(currentIndex != 0) {
            currentIndex--;
            mSwitcher.setText(textToShow[currentIndex]);
        }
    }
}
