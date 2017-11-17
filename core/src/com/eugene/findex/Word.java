package com.eugene.findex;

/**
 * Created by Администратор on 22.09.2017.
 */

public class Word {

    private int id;
    private String categoryEnglish;
    private int level;
    private String englishWord;
    private String koreanWord;

    public Word(){}

    public int getId(){return id;}
    public String getCategoryEnglish() {return categoryEnglish;}
    public String getEnglishWord(){return englishWord;}
    public String getKoreanWord(){return koreanWord;}
    public int getLevel() {
        return level;
    }
}
