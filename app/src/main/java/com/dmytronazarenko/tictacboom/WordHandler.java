package com.dmytronazarenko.tictacboom;

 import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Дмитрий on 27.07.2016.
 */
public class WordHandler {

    final String[] DICTIONARY = {
            "пер", "об","пт","сот", "вн", "мо" , "ист",
            "ник", "аст", "но","ко","пит","ак","акт","инт","ход",
            "кол","ок","ки","док","ка","ло","ча","мет","ран","рез","ант",
            "ру","век","лос","лог","вик","суд","ла","тик","вод","лю","ме",
            "со","те","ад","уз","во","хо","на","вар","рог","ти","га","уб",
            "лов","ле","да","ик","за","от","тор","не"
    };
    ArrayList<String> words = new ArrayList<String>();

    public ArrayList<String> getWords() {
        return words;
    }

    public void setWords(ArrayList<String> words) {
        this.words = words;
    }

    private void wordLoad()
    {
        words.addAll(Arrays.asList(DICTIONARY));
    }

    public  String selectNewWord (){
        int position = (int) (Math.random() * (words.size() - 1));
        String s = words.get(position);
        words.remove(position);
        if (words.size() == 0) wordLoad();
        Log.v("AAA", "" + words.size());
        return s;
    }
//    List<String> RemainingWords ;
//
    public WordHandler() {
        wordLoad();
    }
    public WordHandler(ArrayList<String> words) {

        this.words = words;
    }
//
//    public  String selectNewWord (){
//        int position = (int) (Math.random() * (RemainingWords.size() - 1));
//        String s = RemainingWords.get(position);
//        RemainingWords.remove(position);
//        if (RemainingWords.size() == 0){
//            RemainingWords = Arrays.asList(DICTIONARY.clone());
//        }
//        return s;
//    }

//    private static void deleteWord(int position){
//        RemainingWords.remove(position);
//        if (RemainingWords.size() == 0){
//            RemainingWords = Arrays.asList(DICTIONARY.clone());
//        }
//    }
}
