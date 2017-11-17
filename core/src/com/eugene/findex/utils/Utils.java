package com.eugene.findex.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Json;
import com.eugene.findex.Word;

import java.util.ArrayList;


/**
 * Created by Администратор on 26.09.2017.
 */

public final class Utils {

    public static final String TAG = Utils.class.getSimpleName();

    private Utils(){}

    public static ArrayList<Word> getAllWords(){

        Json json = new Json();

        return json.fromJson(ArrayList.class, Word.class, Gdx.files.internal(Constants.JSON_FILE_NAME));
    }

    public static ArrayList<Word> getWordsByCategoryLevel(String category, int level){

        ArrayList<Word> words = getAllWords();
        ArrayList<Word> wordsByCategory = new ArrayList<Word>();

        for(Word word: words){
            if(word.getCategoryEnglish().equals(category) && word.getLevel()==level) {
                wordsByCategory.add(word);
            }
        }

        return wordsByCategory;

    }


    public static void drawTextureRegion(SpriteBatch batch, TextureRegion region, float x, float y, float width, float height) {

        batch.draw(region.getTexture(),x,y,0,0,width,height, 1,1,0,
                region.getRegionX(),region.getRegionY(),region.getRegionWidth(),region.getRegionHeight(),false,false);

    }

}
