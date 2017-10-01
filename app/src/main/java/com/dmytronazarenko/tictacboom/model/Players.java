package com.dmytronazarenko.tictacboom.model;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class Players {

    private List<Player> players;

    public Players() {
        this.players = new ArrayList<>();
    }

    public List<Player> getPlayersList(){
        return players;
    }

    public void addPlayer(String name, int points){
        players.add(new Player(name, points));
    }

    public void deletePlayer(int position){
        players.remove(position);
    }

    // Loading results from players.txt
    public void loadResults(Context context){

        try {
            InputStream inputStream = context.openFileInput("players.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    int count = Integer.parseInt(bufferedReader.readLine());
                    this.players.add(new Player(receiveString, count));
                }

                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    // Save results to players.txt
    public void saveResults(Context context){

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("players.txt", Context.MODE_PRIVATE));
            for (Player e : players) {
                outputStreamWriter.write(e.name + '\n' + e.points.toString() + '\n');
            }
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public class Player{
        private String name;
        private Integer points;

        private Player(String name, int points) {
            this.name = name;
            this.points = points;
        }

        public String getName() {
            return name;
        }

        public Integer getPoints() {
            return points;
        }

        private void setPoints(int points) {
            this.points = points;
        }

        public void addPoint(){
            this.points++;
        }
    }

}
