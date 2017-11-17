package com.eugene.findex.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.eugene.findex.Level;
import com.eugene.findex.utils.Assets;
import com.eugene.findex.utils.Constants;
import com.eugene.findex.utils.ScreenManager;

import java.util.ArrayList;

/**
 * Created by Администратор on 01.10.2017.
 */

public class LevelsScreen implements Screen {

    public static final String TAG = LevelsScreen.class.getSimpleName();

    private Stage stage;
    private Game game;
    private Preferences prefs;

    public LevelsScreen(Game aGame, final String category) {

        game = aGame;
        stage = new Stage(new ExtendViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT));

        prefs = Gdx.app.getPreferences(Constants.GAME_PREFERENCES);

        Label.LabelStyle labelTitleStyle = new Label.LabelStyle();
        labelTitleStyle.font = Assets.instance.fonts.koreanUnDotum60;



        //Background
        Image background = new Image(Assets.instance.textures.backgroundLevels);
        background.setHeight(stage.getViewport().getWorldHeight());
        background.setWidth(stage.getViewport().getWorldWidth());
        background.setPosition(0,0);
        stage.addActor(background);

        //Scroll pane background
        Image backgroundList = new Image(Assets.instance.textures.platformList);
        backgroundList.setHeight(stage.getViewport().getWorldHeight());
        backgroundList.setWidth(stage.getViewport().getWorldWidth()/2.5f);
        backgroundList.setPosition(stage.getViewport().getWorldWidth()/30,-stage.getViewport().getWorldHeight()/20);
        stage.addActor(backgroundList);


        //Toolbar
        Image toolbar = new Image(Assets.instance.textures.toolbar);
        toolbar.setHeight(stage.getViewport().getWorldHeight()/7);
        toolbar.setWidth(stage.getViewport().getWorldWidth()+50);
        toolbar.setPosition(-25,stage.getViewport().getWorldHeight() - toolbar.getHeight()*0.9f);
        stage.addActor(toolbar);

        //Title
        Label categoryTitle = new Label(category, labelTitleStyle);
        categoryTitle.setPosition(stage.getViewport().getWorldWidth()/9,stage.getViewport().getWorldHeight()-categoryTitle.getHeight());
        stage.addActor(categoryTitle);

        //arrow back button
        Image arrowBack = new Image(Assets.instance.textures.arrowBack);
        arrowBack.setPosition(arrowBack.getWidth(), stage.getViewport().getWorldHeight() - arrowBack.getHeight()*1.5f);
        arrowBack.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(ScreenManager.getInstance().getScreen(Constants.CATEGORIES));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if(prefs.getBoolean(Constants.PREFS_SOUND))
                    Assets.instance.sounds.soundClick.play(0.1f);
                return true;
            }
        });

        stage.addActor(arrowBack);

        final ArrayList<Level> levels = new ArrayList<Level>();

        //get progress in category
        int progress = 0;
        if(category.equals(Constants.CATEGORY_PLACES))
            progress = prefs.getInteger(Constants.PREFS_PLACES_PROGRESS);
        else if(category.equals(Constants.CATEGORY_EDUCATION))
            progress = prefs.getInteger(Constants.PREFS_EDUCATION_PROGRESS);
        else if(category.equals(Constants.CATEGORY_CLOTHES))
            progress = prefs.getInteger(Constants.PREFS_CLOTHES_PROGRESS);
        else if(category.equals(Constants.CATEGORY_ADJECTIVES))
            progress = prefs.getInteger(Constants.PREFS_ADJECTIVES_PROGRESS);
        else if(category.equals(Constants.CATEGORY_ANIMALS))
            progress = prefs.getInteger(Constants.PREFS_ANIMALS_PROGRESS);
        else if(category.equals(Constants.CATEGORY_OCCUPATION))
            progress = prefs.getInteger(Constants.PREFS_OCCUPATION_PROGRESS);
        else if(category.equals(Constants.CATEGORY_VERBS))
            progress = prefs.getInteger(Constants.PREFS_VERBS_PROGRESS);
        else if(category.equals(Constants.CATEGORY_WEATHER))
            progress = prefs.getInteger(Constants.PREFS_WEATHER_PROGRESS);
        else if(category.equals(Constants.CATEGORY_FOOD))
            progress = prefs.getInteger(Constants.PREFS_FOOD_PROGRESS);
        else if(category.equals(Constants.CATEGORY_OUTDOORS))
            progress = prefs.getInteger(Constants.PREFS_OUTDOORS_PROGRESS);

        //Labels of levels

        Label.LabelStyle labelListStyle = new Label.LabelStyle();
        labelListStyle.font = Assets.instance.fonts.unDotumShadowTeal60;

        float posX = stage.getViewport().getWorldWidth() / 12;
        float posY = stage.getViewport().getWorldHeight() - toolbar.getHeight()*2;

        for (int i = 0; i < Constants.LEVELS_IN_CATEGORY; i++) {

            levels.add(new Level(i + 1, posX + Constants.WORLD_WIDTH/13, posY, labelListStyle));
            stage.addActor(levels.get(i).getLabel());

            if(i<=progress) {

                final int levelNumber = i + 1;
                levels.get(i).getLabel().addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if(prefs.getBoolean(Constants.PREFS_SOUND))
                            Assets.instance.sounds.soundClick.play(0.1f);
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        game.setScreen(new GameplayScreen(game, category, levelNumber));
                    }
                });

                if(progress>0 && i!=progress) {
                    //Add level completed badge
                    Image levelCompletedBadge = new Image(Assets.instance.textures.medal);
                    levelCompletedBadge.setWidth(64);
                    levelCompletedBadge.setHeight(72);
                    levelCompletedBadge.setPosition(posX - levelCompletedBadge.getWidth()/8 , posY);
                    stage.addActor(levelCompletedBadge);
                }

            } else {
                //Add padlock badge
                Image padlock = new Image(Assets.instance.textures.padlock);
                padlock.setHeight(40);
                padlock.setWidth(32);
                padlock.setPosition(posX,posY+padlock.getHeight()/4);

                stage.addActor(padlock);
            }

            posY -= Constants.WORLD_HEIGHT / 10 + levels.get(i).getLabel().getHeight()/2;
        }

    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }


}
