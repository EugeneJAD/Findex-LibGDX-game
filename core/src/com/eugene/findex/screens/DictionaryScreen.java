package com.eugene.findex.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.eugene.findex.Word;
import com.eugene.findex.utils.Assets;
import com.eugene.findex.utils.Constants;
import com.eugene.findex.utils.ScreenManager;
import com.eugene.findex.utils.Utils;
import java.util.ArrayList;

/**
 * Created by Администратор on 24.10.2017.
 */

public class DictionaryScreen implements Screen {

    public static final String TAG = DictionaryScreen.class.getSimpleName();

    private Game game;
    private Stage stage;


    public DictionaryScreen(Game aGame){

        game = aGame;
        stage = new Stage(new ExtendViewport(Constants.WORLD_WIDTH,Constants.WORLD_HEIGHT));

        final Preferences prefs = Gdx.app.getPreferences(Constants.GAME_PREFERENCES);

        Label.LabelStyle labelTitleStyle = new Label.LabelStyle();
        labelTitleStyle.font = Assets.instance.fonts.koreanUnDotum60;

        //Toolbar
        Image toolbar = new Image(Assets.instance.textures.toolbar);
        toolbar.setHeight(stage.getViewport().getWorldHeight()/7);
        toolbar.setWidth(stage.getViewport().getWorldWidth()+50);
        toolbar.setPosition(-25,stage.getViewport().getWorldHeight() - toolbar.getHeight()*0.9f);

        //Title
        Label categoryTitle = new Label("Dictionary", labelTitleStyle);
        categoryTitle.setPosition(stage.getViewport().getWorldWidth()/9,stage.getViewport().getWorldHeight()-categoryTitle.getHeight());

        //arrow back button
        Image arrowBack = new Image(Assets.instance.textures.arrowBack);
        arrowBack.setPosition(arrowBack.getWidth(), stage.getViewport().getWorldHeight() - arrowBack.getHeight()*1.5f);
        arrowBack.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainScreen(game));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if(prefs.getBoolean(Constants.PREFS_SOUND))
                    Assets.instance.sounds.soundClick.play(0.1f);
                return true;
            }
        });



        //Scroll pane

        ArrayList<Word> words = Utils.getAllWords();

        Label.LabelStyle categoryLabelStyle = new Label.LabelStyle();
        categoryLabelStyle.font = Assets.instance.fonts.koreanUnDotum60;
        categoryLabelStyle.fontColor = Color.GOLD;

        String category = words.get(0).getCategoryEnglish();
        Label categoryLabel = new Label(category, categoryLabelStyle);

        Table table = new Table();
        table.add(categoryLabel).colspan(2).align(Align.center).padTop(20).padBottom(20).row();


        for(Word word : words){

            if(!word.getCategoryEnglish().equals(category)) {
                category = word.getCategoryEnglish();
                table.add(new Label(category, categoryLabelStyle)).colspan(2).align(Align.center).padTop(50).padBottom(20).row();
            }

            table.add(new Label(word.getEnglishWord(), labelTitleStyle)).align(Align.center);
            table.add(new Label(word.getKoreanWord(), labelTitleStyle)).align(Align.center);
            table.row();
        }


        table.top().padBottom(80);

        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setBounds(0, 0, stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight()-toolbar.getHeight()*0.7f);

        stage.addActor(scrollPane);
        stage.addActor(toolbar);
        stage.addActor(categoryTitle);
        stage.addActor(arrowBack);

        ScreenManager.getInstance().addScreen(Constants.DICTIONARY, this);
    }

    @Override
    public void show() {
        ScreenManager.getInstance().setAppState(Constants.AppState.INITIAL);
        ScreenManager.getInstance().getHandler().showBannerAds(true);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(
                Constants.DICTIONARY_BACKGROUND.r,
                Constants.DICTIONARY_BACKGROUND.g,
                Constants.DICTIONARY_BACKGROUND.b,
                Constants.DICTIONARY_BACKGROUND.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
