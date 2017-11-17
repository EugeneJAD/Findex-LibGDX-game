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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.eugene.findex.utils.Assets;
import com.eugene.findex.utils.Constants;
import com.eugene.findex.utils.ScreenManager;


/**
 * Created by Администратор on 01.10.2017.
 */

public class CategoryScreen implements Screen {


    public static final String TAG = CategoryScreen.class.getSimpleName();

    private Stage stage;
    private Game game;

    private Preferences prefs;
    private Image toolbar;


    public CategoryScreen(Game aGame){

        game = aGame;
        stage = new Stage(new ExtendViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT));

        prefs = Gdx.app.getPreferences(Constants.GAME_PREFERENCES);

        Label.LabelStyle labelTitleStyle = new Label.LabelStyle();
        labelTitleStyle.font = Assets.instance.fonts.koreanUnDotum60;

        //Background
        Image background = new Image(Assets.instance.textures.backgroundCategory);
        background.setHeight(stage.getViewport().getWorldHeight());
        background.setWidth(stage.getViewport().getWorldWidth());
        background.setPosition(0,0);
        stage.addActor(background);

        //Scroll pane background
        Image backgroundList = new Image(Assets.instance.textures.platformList);
        backgroundList.setHeight(stage.getViewport().getWorldHeight());
        backgroundList.setWidth(stage.getViewport().getWorldWidth()/2.2f);
        backgroundList.setPosition(stage.getViewport().getWorldWidth()/30,-stage.getViewport().getWorldHeight()/20);
        stage.addActor(backgroundList);


        //Toolbar
        toolbar = new Image(Assets.instance.textures.toolbar);
        toolbar.setHeight(stage.getViewport().getWorldHeight()/7);
        toolbar.setWidth(stage.getViewport().getWorldWidth()+50);
        toolbar.setPosition(-25,stage.getViewport().getWorldHeight() - toolbar.getHeight()*0.9f);

        //Title
        Label categoryTitle = new Label("Categories", labelTitleStyle);
        categoryTitle.setPosition(stage.getViewport().getWorldWidth()/9,stage.getViewport().getWorldHeight()-categoryTitle.getHeight());

        //arrow back button
        Image arrowBack = new Image(Assets.instance.textures.arrowBack);
        arrowBack.setPosition(arrowBack.getWidth(), stage.getViewport().getWorldHeight() - arrowBack.getHeight()*1.5f);
        arrowBack.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(ScreenManager.getInstance().getScreen(Constants.MAIN_MENU));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if(prefs.getBoolean(Constants.PREFS_SOUND))
                    Assets.instance.sounds.soundClick.play(0.1f);
                return true;
            }
        });


        //Scroll pane
        //Labels of categories

        Label.LabelStyle labelListStyle = new Label.LabelStyle();
        labelListStyle.font = Assets.instance.fonts.unDotumShadowTeal60;

        Table table = new Table();
        Array<String> categories = new Array<String>();

        categories.add(Constants.CATEGORY_ADJECTIVES);
        categories.add(Constants.CATEGORY_ANIMALS);
        categories.add(Constants.CATEGORY_CLOTHES);
        categories.add(Constants.CATEGORY_EDUCATION);
        categories.add(Constants.CATEGORY_FOOD);
        categories.add(Constants.CATEGORY_OCCUPATION);
        categories.add(Constants.CATEGORY_OUTDOORS);
        categories.add(Constants.CATEGORY_PLACES);
        categories.add(Constants.CATEGORY_VERBS);
        categories.add(Constants.CATEGORY_WEATHER);


        for(final String categoryName : categories){

            Label categoryLabel = new Label(categoryName, labelListStyle);
            categoryLabel.addListener(new InputListener(){
                @Override
                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    game.setScreen(new LevelsScreen(game, categoryName));
                }
                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    if(prefs.getBoolean(Constants.PREFS_SOUND))
                        Assets.instance.sounds.soundClick.play(0.1f);
                    return true;
                }
            });

            if(Gdx.app.getPreferences(Constants.GAME_PREFERENCES).getBoolean(categoryName)) {
                Image awardImage = new Image(Assets.instance.textures.award);
                table.add(awardImage).width(80).height(72).space(10,0,50,10);
            } else {
                Image awardPlaceholder = new Image(Assets.instance.textures.awardPlaceholder);
                table.add(awardPlaceholder).width(80).height(72).space(10,0,50,10);
            }
            table.add(categoryLabel).align(Align.left).pad(10,10,50,0);
            table.row();
        }
        table.row();
        table.top().center().padTop(40);

        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setBounds(backgroundList.getX()+backgroundList.getWidth()/15,0,Constants.WORLD_WIDTH/2.5f, toolbar.getY()+30);
        scrollPane.setScrollBarPositions(false,true);
        scrollPane.setScrollbarsOnTop(true);
        stage.addActor(scrollPane);

        stage.addActor(toolbar);
        stage.addActor(categoryTitle);
        stage.addActor(arrowBack);

        ScreenManager.getInstance().addScreen(Constants.CATEGORIES,this);
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
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

