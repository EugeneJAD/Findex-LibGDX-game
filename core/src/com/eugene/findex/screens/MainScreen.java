package com.eugene.findex.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.eugene.findex.utils.Assets;
import com.eugene.findex.utils.Constants;
import com.eugene.findex.utils.ScreenManager;

import java.util.ArrayList;


/**
 * Created by Администратор on 29.09.2017.
 */

public class MainScreen implements Screen {

    public static final String TAG = MainScreen.class.getSimpleName();

    public static final String DICTIONARY_DIALOG = "dictionary_type";
    public static final String GET_PROMPTS_DIALOG = "prompts_type";
    public static final String RATE_DIALOG = "rate_type";

    private Stage stage;
    private Game game;

    private Image toolbar;
    private Preferences prefs;
    private Table gameInfoTable;
    private Image backgroundInfo;

    //no interstitial ad message
    private Label noAdReadyLabel;
    private Label.LabelStyle noAdReadyLabelStyle;


    //Buttons

    private Label resumeButton;
    private Image platformForResumeButton;

    private Label playLabel;
    private Image platformForPlayButton;
    private Image playButton;

    private Label dictionaryLabel;
    private Image platformForDictionaryLabel;
    private Image dictionaryButton;

    private Image platformForQuitLabel;
    private Image quitButton;

    private Image platformSoundButton;
    private ImageButton soundButton;

    private Image platformRateButton;
    private Image rateButton;

    private Image getPromptsButton;


    public MainScreen(Game aGame) {

        game = aGame;
        stage = new Stage(new ExtendViewport(Constants.WORLD_WIDTH,Constants.WORLD_HEIGHT));

        //Background
        Image background = new Image(Assets.instance.textures.backgroundMain);
        background.setHeight(stage.getViewport().getWorldHeight());
        background.setWidth(stage.getViewport().getWorldWidth());
        background.setPosition(0,0);
        stage.addActor(background);

        //Toolbar
        toolbar = new Image(Assets.instance.textures.toolbar);
        toolbar.setHeight(stage.getViewport().getWorldHeight()/7);
        toolbar.setWidth(stage.getViewport().getWorldWidth()+50);
        toolbar.setPosition(-25,stage.getViewport().getWorldHeight() - toolbar.getHeight()*0.9f);
        stage.addActor(toolbar);


        Image logo = new Image(Assets.instance.textures.logo);
        logo.setHeight(stage.getViewport().getWorldHeight()/11);
        logo.setWidth(stage.getViewport().getWorldWidth()/5.6f);
        logo.setPosition(stage.getViewport().getWorldWidth()/25, stage.getViewport().getWorldHeight() - logo.getHeight()*1.1f);
        stage.addActor(logo);

        Image flag = new Image(Assets.instance.textures.flag);
        flag.setWidth(stage.getViewport().getWorldWidth()/15);
        flag.setHeight(stage.getViewport().getWorldHeight()/12);
        flag.setPosition(stage.getViewport().getWorldWidth() - stage.getViewport().getWorldWidth()/50 - flag.getWidth(),
                stage.getViewport().getWorldHeight() - flag.getHeight()*1.15f);
        stage.addActor(flag);

        //define default preferences
        prefs = Gdx.app.getPreferences(Constants.GAME_PREFERENCES);
        if(!prefs.contains(Constants.PREFS_WEATHER_PROGRESS)) {
            setDefaultPreferences();
        }

        setMenuButtons();

        setGameInfo();

        //ad
        noAdReadyLabelStyle = new Label.LabelStyle();
        noAdReadyLabelStyle.font = Assets.instance.fonts.unDotum40;
        noAdReadyLabelStyle.fontColor = Color.FIREBRICK;
        noAdReadyLabel = new Label("Sorry, Ad is not loaded yet", noAdReadyLabelStyle);
        noAdReadyLabel.setPosition(Constants.CELL_SIZE/2,10);


        //Add screen to ScreenManager
        ScreenManager.getInstance().addScreen(Constants.MAIN_MENU,this);


    }

    private void setGameInfo() {

        Label.LabelStyle gameInfoLabelStyle = new Label.LabelStyle();
        gameInfoLabelStyle.font = Assets.instance.fonts.unDotum40;
        gameInfoLabelStyle.fontColor = Color.GRAY;

        Label gameInfo = new Label("Game progress", gameInfoLabelStyle);
        gameInfo.setColor(Color.DARK_GRAY);
        Label inventory = new Label("Inventory", gameInfoLabelStyle);
        inventory.setColor(Color.DARK_GRAY);

        ArrayList<String> categoriesProgress = new ArrayList<String>();
        categoriesProgress.add(Constants.PREFS_CLOTHES_PROGRESS);
        categoriesProgress.add(Constants.PREFS_EDUCATION_PROGRESS);
        categoriesProgress.add(Constants.PREFS_PLACES_PROGRESS);
        categoriesProgress.add(Constants.PREFS_OCCUPATION_PROGRESS);
        categoriesProgress.add(Constants.PREFS_ADJECTIVES_PROGRESS);
        categoriesProgress.add(Constants.PREFS_ANIMALS_PROGRESS);
        categoriesProgress.add(Constants.PREFS_FOOD_PROGRESS);
        categoriesProgress.add(Constants.PREFS_OUTDOORS_PROGRESS);
        categoriesProgress.add(Constants.PREFS_VERBS_PROGRESS);
        categoriesProgress.add(Constants.PREFS_WEATHER_PROGRESS);

        int levelsCompleted = 0;
        for(String category : categoriesProgress){
            levelsCompleted+=prefs.getInteger(category);
        }

        ArrayList<String> categoriesCompletions = new ArrayList<String>();
        categoriesCompletions.add(Constants.PREFS_CATEGORY_CLOTHES_COMPLETION);
        categoriesCompletions.add(Constants.PREFS_CATEGORY_EDUCATION_COMPLETION);
        categoriesCompletions.add(Constants.PREFS_CATEGORY_PLACES_COMPLETION);
        categoriesCompletions.add(Constants.PREFS_CATEGORY_ADJECTIVES_COMPLETION);
        categoriesCompletions.add(Constants.PREFS_CATEGORY_ANIMALS_COMPLETION);
        categoriesCompletions.add(Constants.PREFS_CATEGORY_FOOD_COMPLETION);
        categoriesCompletions.add(Constants.PREFS_CATEGORY_OCCUPATION_COMPLETION);
        categoriesCompletions.add(Constants.PREFS_CATEGORY_OUTDOORS_COMPLETION);
        categoriesCompletions.add(Constants.PREFS_CATEGORY_VERBS_COMPLETION);
        categoriesCompletions.add(Constants.PREFS_CATEGORY_WEATHER_COMPLETION);


        int categoriesCompleted = 0;
        for(String category : categoriesCompletions){
            if(prefs.getBoolean(category))
                categoriesCompleted++;
        }


        Label medalsCount =  new Label(levelsCompleted+"/"+Constants.LEVELS_IN_GAME, gameInfoLabelStyle);
        Label awardsCount =  new Label(categoriesCompleted+"/"+Constants.CATEGORY_QUANTITY, gameInfoLabelStyle);
        Label promptsCount =  new Label("x"+prefs.getInteger(Constants.PREFS_PROMPTS), gameInfoLabelStyle);

        Image medalImage = new Image(Assets.instance.textures.medal);
        Image awardImage = new Image(Assets.instance.textures.award);
        Image promptImage = new Image(Assets.instance.textures.iconSearch);

        gameInfoTable = new Table();
        gameInfoTable.add(gameInfo).align(Align.left).padBottom(20).colspan(2);
        gameInfoTable.row();
        gameInfoTable.add(medalImage).align(Align.center).width(74).height(84).padBottom(20).padRight(10);
        gameInfoTable.add(medalsCount).align(Align.left);
        gameInfoTable.row();
        gameInfoTable.add(awardImage).align(Align.center).width(100).height(90).padBottom(20).padRight(10);
        gameInfoTable.add(awardsCount).align(Align.left);
        gameInfoTable.row();
        gameInfoTable.add(inventory).padBottom(20).colspan(2).align(Align.center);
        gameInfoTable.row();
        gameInfoTable.add(promptImage).width(70).height(70).align(Align.right).padRight(10);
        gameInfoTable.add(promptsCount).align(Align.left);

        gameInfoTable.setPosition(stage.getViewport().getWorldWidth()/4,
                stage.getViewport().getWorldHeight()- gameInfoTable.getMinHeight()*0.90f);

        //background for game info table
        backgroundInfo = new Image(Assets.instance.textures.platform500);
        backgroundInfo.setWidth(backgroundInfo.getWidth()/1.3f);
        backgroundInfo.setPosition(gameInfoTable.getX() - backgroundInfo.getWidth()/2,gameInfoTable.getY()-backgroundInfo.getHeight()/2);

        stage.addActor(backgroundInfo);
        stage.addActor(gameInfoTable);

        //show add prompts button if count<2
        if(prefs.getInteger(Constants.PREFS_PROMPTS)<2){

            getPromptsButton = new Image(Assets.instance.textures.addButton);
            getPromptsButton.setWidth(90);
            getPromptsButton.setHeight(90);
            getPromptsButton.setPosition(backgroundInfo.getX() + backgroundInfo.getWidth() - getPromptsButton.getWidth() - backgroundInfo.getWidth()/20,
                    backgroundInfo.getY() + backgroundInfo.getHeight()/20);

            getPromptsButton.addListener(new InputListener(){
                @Override
                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    if(!prefs.getBoolean(Constants.PREFS_IS_RATE_DIALOG_SHOWN)){
                        showDialog(RATE_DIALOG);
                        prefs.putBoolean(Constants.PREFS_IS_RATE_DIALOG_SHOWN, true).flush();
                    } else
                        showDialog(GET_PROMPTS_DIALOG);

                    getPromptsButton.setY(getPromptsButton.getY()+5);
                }
                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    if(prefs.getBoolean(Constants.PREFS_SOUND))
                        Assets.instance.sounds.soundClick.play(0.1f);
                    getPromptsButton.setY(getPromptsButton.getY()-5);


                    return true;
                }
            });

            stage.addActor(getPromptsButton);

            getPromptsButton.addAction(Actions.sequence(Actions.fadeOut(0.7f), Actions.fadeIn(0.7f),Actions.fadeOut(0.7f),Actions.fadeIn(0.7f)));
        }


    }

    private void setDefaultPreferences() {

        ////levels completed in categories
        prefs.putInteger(Constants.PREFS_PLACES_PROGRESS, 0);
        prefs.putInteger(Constants.PREFS_CLOTHES_PROGRESS, 0);
        prefs.putInteger(Constants.PREFS_EDUCATION_PROGRESS, 0);
        prefs.putInteger(Constants.PREFS_OCCUPATION_PROGRESS, 0);
        prefs.putInteger(Constants.PREFS_ADJECTIVES_PROGRESS, 0);
        prefs.putInteger(Constants.PREFS_FOOD_PROGRESS, 0);
        prefs.putInteger(Constants.PREFS_ANIMALS_PROGRESS, 0);
        prefs.putInteger(Constants.PREFS_OUTDOORS_PROGRESS, 0);
        prefs.putInteger(Constants.PREFS_VERBS_PROGRESS, 0);
        prefs.putInteger(Constants.PREFS_WEATHER_PROGRESS, 0);

        //prompts count
        prefs.putInteger(Constants.PREFS_PROMPTS, Constants.PROMPTS_COUNT);

        //sound
        prefs.putBoolean(Constants.PREFS_SOUND, true);

        //categories completion
        prefs.putBoolean(Constants.PREFS_CATEGORY_CLOTHES_COMPLETION, false);
        prefs.putBoolean(Constants.PREFS_CATEGORY_EDUCATION_COMPLETION, false);
        prefs.putBoolean(Constants.PREFS_CATEGORY_PLACES_COMPLETION, false);
        prefs.putBoolean(Constants.PREFS_CATEGORY_ADJECTIVES_COMPLETION, false);
        prefs.putBoolean(Constants.PREFS_CATEGORY_OCCUPATION_COMPLETION, false);
        prefs.putBoolean(Constants.PREFS_CATEGORY_FOOD_COMPLETION, false);
        prefs.putBoolean(Constants.PREFS_CATEGORY_OUTDOORS_COMPLETION, false);
        prefs.putBoolean(Constants.PREFS_CATEGORY_ANIMALS_COMPLETION, false);
        prefs.putBoolean(Constants.PREFS_CATEGORY_VERBS_COMPLETION, false);
        prefs.putBoolean(Constants.PREFS_CATEGORY_WEATHER_COMPLETION, false);

        //rate dialog
        prefs.putBoolean(Constants.PREFS_IS_RATE_DIALOG_SHOWN, false);

        prefs.flush();
    }

    private void setMenuButtons() {

        float posYstep = 20;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Assets.instance.fonts.chowShadow60;

        //Display resume button if user is playing
        if(ScreenManager.getInstance().getAppState()== Constants.AppState.PLAYING) {


            resumeButton = new Label("Resume", labelStyle);
            resumeButton.setPosition(
                    stage.getViewport().getWorldWidth(),
                    stage.getViewport().getWorldHeight()- toolbar.getHeight() - resumeButton.getHeight() - posYstep);
            resumeButton.addListener(new InputListener(){
                @Override
                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                    resumeButton.setY(resumeButton.getY()+5);
                    platformForResumeButton.setY(platformForResumeButton.getY()+5);

                    game.setScreen(ScreenManager.getInstance().getScreen(Constants.GAME_FIELD));
                }
                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    if(prefs.getBoolean(Constants.PREFS_SOUND))
                        Assets.instance.sounds.soundClick.play(0.1f);
                    resumeButton.setY(resumeButton.getY()-5);
                    platformForResumeButton.setY(platformForResumeButton.getY()-5);

                    return true;
                }
            });

            platformForResumeButton = new Image(Assets.instance.textures.platform);
            platformForResumeButton.setHeight(resumeButton.getHeight()*1.2f);
            platformForResumeButton.setWidth(Constants.WORLD_WIDTH/3);
            platformForResumeButton.setPosition(stage.getViewport().getWorldWidth() - stage.getViewport().getWorldWidth()/12 - platformForResumeButton.getWidth(),
                    stage.getViewport().getWorldHeight()- toolbar.getHeight() - platformForResumeButton.getHeight() - posYstep);

            stage.addActor(platformForResumeButton);

            stage.addActor(resumeButton);

            //Animation
            resumeButton.addAction(Actions.moveTo(stage.getViewport().getWorldWidth() - stage.getViewport().getWorldWidth()/7 - resumeButton.getWidth(),
                    stage.getViewport().getWorldHeight()- toolbar.getHeight() - resumeButton.getHeight() - posYstep,
                    1,Interpolation.swing));

            posYstep = platformForResumeButton.getHeight()*1.2f;
        }

        //Play button

        InputListener playClickListener = new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                playLabel.setY(playLabel.getY()+5);
                playButton.setY(playButton.getY()+5);
                platformForPlayButton.setY(platformForPlayButton.getY()+5);

                game.setScreen(new CategoryScreen(game));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if(prefs.getBoolean(Constants.PREFS_SOUND))
                    Assets.instance.sounds.soundClick.play(0.1f);

                playLabel.setY(playLabel.getY()-5);
                playButton.setY(playButton.getY()-5);
                platformForPlayButton.setY(platformForPlayButton.getY()-5);

                return true;
            }
        };


        playLabel = new Label("Play", labelStyle);
        playLabel.setPosition(
                stage.getViewport().getWorldWidth(),
                stage.getViewport().getWorldHeight()- toolbar.getHeight() - playLabel.getHeight() - posYstep );
        playLabel.addListener(playClickListener);


        platformForPlayButton = new Image(Assets.instance.textures.platform);
        platformForPlayButton.setHeight(playLabel.getHeight()*1.2f);
        platformForPlayButton.setWidth(Constants.WORLD_WIDTH/3);
        platformForPlayButton.setPosition(stage.getViewport().getWorldWidth() - stage.getViewport().getWorldWidth()/12 - platformForPlayButton.getWidth(),
                stage.getViewport().getWorldHeight()- toolbar.getHeight() - platformForPlayButton.getHeight() - posYstep);

        playButton = new Image(Assets.instance.textures.playButton);
        playButton.setPosition(stage.getViewport().getWorldWidth() - stage.getViewport().getWorldWidth()/12 - platformForPlayButton.getWidth()+playButton.getWidth(),
                stage.getViewport().getWorldHeight()- toolbar.getHeight()- platformForPlayButton.getHeight()/2 - playButton.getHeight()/2 + 5 - posYstep);
        playButton.addListener(playClickListener);


        stage.addActor(platformForPlayButton);
        stage.addActor(playButton);
        stage.addActor(playLabel);

        //Animation
        playLabel.addAction(Actions.moveTo(stage.getViewport().getWorldWidth() - stage.getViewport().getWorldWidth()/12 - platformForPlayButton.getWidth()*0.6f,
                stage.getViewport().getWorldHeight()- toolbar.getHeight() - playLabel.getHeight() - posYstep,
                1,Interpolation.swing));

        posYstep+=platformForPlayButton.getHeight()*1.2f;


        //Dictionary button

        InputListener dictionaryClickListener = new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(ScreenManager.getInstance().getAppState()== Constants.AppState.INITIAL)
                    game.setScreen(new DictionaryScreen(game));
                else
                    showDialog(DICTIONARY_DIALOG);

                dictionaryLabel.setY(dictionaryLabel.getY()+5);
                dictionaryButton.setY(dictionaryButton.getY()+5);
                platformForDictionaryLabel.setY(platformForDictionaryLabel.getY()+5);

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if(prefs.getBoolean(Constants.PREFS_SOUND))
                    Assets.instance.sounds.soundClick.play(0.1f);

                dictionaryLabel.setY(dictionaryLabel.getY()-5);
                dictionaryButton.setY(dictionaryButton.getY()-5);
                platformForDictionaryLabel.setY(platformForDictionaryLabel.getY()-5);


                return true;
            }
        };

        dictionaryLabel = new Label("Words", labelStyle);
        dictionaryLabel.setPosition(
                stage.getViewport().getWorldWidth(),
                stage.getViewport().getWorldHeight()- toolbar.getHeight() - dictionaryLabel.getHeight() - posYstep );
        dictionaryLabel.addListener(dictionaryClickListener);


        platformForDictionaryLabel = new Image(Assets.instance.textures.platform);
        platformForDictionaryLabel.setHeight(dictionaryLabel.getHeight()*1.2f);
        platformForDictionaryLabel.setWidth(Constants.WORLD_WIDTH/3);
        platformForDictionaryLabel.setPosition(stage.getViewport().getWorldWidth() - stage.getViewport().getWorldWidth()/12 - platformForDictionaryLabel.getWidth(),
                stage.getViewport().getWorldHeight()- toolbar.getHeight() - platformForDictionaryLabel.getHeight() - posYstep);

        dictionaryButton = new Image(Assets.instance.textures.wordsButton);
        dictionaryButton.setPosition(stage.getViewport().getWorldWidth() - stage.getViewport().getWorldWidth()/12 - platformForDictionaryLabel.getWidth()+dictionaryButton.getWidth()/2,
                stage.getViewport().getWorldHeight()- toolbar.getHeight() - platformForDictionaryLabel.getHeight()/2 - dictionaryButton.getHeight()/2 + 5 - posYstep);
        dictionaryButton.addListener(dictionaryClickListener);

        stage.addActor(platformForDictionaryLabel);
        stage.addActor(dictionaryButton);
        stage.addActor(dictionaryLabel);

        //Animation
        dictionaryLabel.addAction(Actions.moveTo(stage.getViewport().getWorldWidth() - stage.getViewport().getWorldWidth()/12 -platformForDictionaryLabel.getWidth()*0.6f,
                stage.getViewport().getWorldHeight()- toolbar.getHeight() - dictionaryLabel.getHeight() - posYstep,
                1,Interpolation.swing));

        posYstep+=platformForPlayButton.getHeight()*1.2f;


        //Quit, Sound, Rate buttons

        float space_between_buttons = 20;

        platformForQuitLabel = new Image(Assets.instance.textures.platform123);
        platformForQuitLabel.setPosition(stage.getViewport().getWorldWidth() - stage.getViewport().getWorldWidth()/12-platformForQuitLabel.getWidth()-4,
                stage.getViewport().getWorldHeight()- toolbar.getHeight() - platformForQuitLabel.getHeight() - posYstep);

        quitButton = new Image(Assets.instance.textures.quitButton);
        quitButton.setPosition(stage.getViewport().getWorldWidth() - stage.getViewport().getWorldWidth()/12 - platformForQuitLabel.getWidth() -4 +quitButton.getWidth()/2,
                stage.getViewport().getWorldHeight()- toolbar.getHeight() - platformForQuitLabel.getHeight()/2 - quitButton.getHeight()/2 + 5 - posYstep);
        quitButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                if(prefs.getBoolean(Constants.PREFS_SOUND))
                    Assets.instance.sounds.soundClick.play(0.1f);

                quitButton.setY(quitButton.getY()-5);
                platformForQuitLabel.setY(platformForQuitLabel.getY()-5);

                return true;
            }
        });

        stage.addActor(platformForQuitLabel);
        stage.addActor(quitButton);


        ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.up = new Image(Assets.instance.textures.soundOnButton).getDrawable();
        imageButtonStyle.down = new Image(Assets.instance.textures.soundOnButton).getDrawable();
        imageButtonStyle.checked = new Image(Assets.instance.textures.soundOffButton).getDrawable();

        platformSoundButton = new Image(Assets.instance.textures.platform123);
        platformSoundButton.setPosition(stage.getViewport().getWorldWidth() - stage.getViewport().getWorldWidth()/12-platformForQuitLabel.getWidth()*2 -4 - space_between_buttons/2,
                stage.getViewport().getWorldHeight()- toolbar.getHeight() - platformSoundButton.getHeight() - posYstep);

        soundButton = new ImageButton(imageButtonStyle);
        soundButton.setPosition(stage.getViewport().getWorldWidth() - stage.getViewport().getWorldWidth()/12
                        - platformSoundButton.getWidth()*2 +soundButton.getWidth()/2
                        - space_between_buttons-4,
                stage.getViewport().getWorldHeight()- toolbar.getHeight() - platformSoundButton.getHeight()/2 - soundButton.getHeight()/2 + 5 - posYstep);
        soundButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(Gdx.app.getPreferences(Constants.GAME_PREFERENCES).getBoolean(Constants.PREFS_SOUND)) {
                    Gdx.app.getPreferences(Constants.GAME_PREFERENCES).putBoolean(Constants.PREFS_SOUND, false).flush();
                } else {
                    Gdx.app.getPreferences(Constants.GAME_PREFERENCES).putBoolean(Constants.PREFS_SOUND, true).flush();
                }
                soundButton.setY(soundButton.getY()+5);
                platformSoundButton.setY(platformSoundButton.getY()+5);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Assets.instance.sounds.soundClick.play(0.1f);

                soundButton.setY(soundButton.getY()-5);
                platformSoundButton.setY(platformSoundButton.getY()-5);


                return true;
            }
        });

        if(!prefs.getBoolean(Constants.PREFS_SOUND))
            soundButton.setChecked(true);

        stage.addActor(platformSoundButton);
        stage.addActor(soundButton);


        platformRateButton = new Image(Assets.instance.textures.platform123);
        platformRateButton.setPosition(stage.getViewport().getWorldWidth() - stage.getViewport().getWorldWidth()/12-platformForQuitLabel.getWidth()*3 -4- space_between_buttons,
                stage.getViewport().getWorldHeight()- toolbar.getHeight() - platformRateButton.getHeight() - posYstep);

        rateButton = new Image(Assets.instance.textures.rateButton);
        rateButton.setPosition(stage.getViewport().getWorldWidth() - stage.getViewport().getWorldWidth()/12
                        - platformRateButton.getWidth()*3 +rateButton.getWidth()/2
                        - space_between_buttons - 12,
                stage.getViewport().getWorldHeight()- toolbar.getHeight() - platformRateButton.getHeight()/2 - rateButton.getHeight()/2 + 5 - posYstep);
        rateButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                rateButton.setY(rateButton.getY()+5);
                platformRateButton.setY(platformRateButton.getY()+5);

                Gdx.net.openURI(Constants.GOOGLE_PLAY_URL);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if(prefs.getBoolean(Constants.PREFS_SOUND))
                    Assets.instance.sounds.soundClick.play(0.1f);

                rateButton.setY(rateButton.getY()-5);
                platformRateButton.setY(platformRateButton.getY()-5);

                return true;
            }
        });

        stage.addActor(platformRateButton);
        stage.addActor(rateButton);


    }

    @Override
    public void show() {
        ScreenManager.getInstance().getHandler().showBannerAds(false);
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
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void showDialog(final String dialogType){

        //Dialog screen background
        Image backgroundTint = new Image(Assets.instance.textures.backgroundDialogTint);
        backgroundTint.setWidth(stage.getViewport().getWorldWidth());
        backgroundTint.setHeight(stage.getViewport().getWorldHeight());
        backgroundTint.setPosition(0,0);

        Window.WindowStyle dialogWindowStyle = new Window.WindowStyle();
        dialogWindowStyle.background = new Image(Assets.instance.textures.backgroundDialog).getDrawable();
        dialogWindowStyle.titleFont = Assets.instance.fonts.unDotum40;
        dialogWindowStyle.stageBackground = backgroundTint.getDrawable();

        Label.LabelStyle dialogTextStyle = new Label.LabelStyle();
        dialogTextStyle.font = Assets.instance.fonts.unDotum40;
        dialogTextStyle.fontColor = Color.GOLD;

        Label.LabelStyle dialogButtonsStyle = new Label.LabelStyle();
        dialogButtonsStyle.font = Assets.instance.fonts.unDotum40;

        //Show dialog
        final Dialog dialog = new Dialog("", dialogWindowStyle);

        int addPromptsQuantity = 0;
        String dialogMessage;
        if(dialogType.equals(DICTIONARY_DIALOG)) {
            dialogMessage = "Reset current game and open Dictionary?";
        } else if(dialogType.equals(RATE_DIALOG)){
            addPromptsQuantity = 5;
            dialogMessage = "You got "+addPromptsQuantity + " additional Prompts. Rate the game please.";
            prefs.putInteger(Constants.PREFS_PROMPTS, prefs.getInteger(Constants.PREFS_PROMPTS)+addPromptsQuantity);
            prefs.putBoolean(Constants.PREFS_IS_RATE_DIALOG_SHOWN, true);
            prefs.flush();
        } else {
            addPromptsQuantity = 3;
            dialogMessage = "Watch Ad and get " + addPromptsQuantity + " extra Prompts?";
        }


        Label dialogLabel = new Label(dialogMessage, dialogTextStyle);
        dialogLabel.setAlignment(Align.center);
        dialogLabel.setWrap(true);

        dialog.getContentTable().add(dialogLabel).pad(0,
                dialog.getBackground().getMinHeight()/10,
                dialog.getBackground().getMinHeight()/25,
                dialog.getBackground().getMinHeight()/10)
                .width(dialog.getBackground().getMinWidth());


        if(dialogType.equals(RATE_DIALOG) || dialogType.equals(GET_PROMPTS_DIALOG) ) {

            Assets.instance.fonts.koreanUnDotum60.getData().setScale(0.6f);

            Label promptsCountLabel = new Label("x" + addPromptsQuantity, dialogButtonsStyle);

            Image buttonSearchImage = new Image(Assets.instance.textures.iconSearch);

            Table table = new Table();
            table.add(buttonSearchImage).width(70).height(70);
            table.add(promptsCountLabel).padLeft(10);
            dialog.getContentTable().row();
            dialog.getContentTable().add(table);

            Assets.instance.fonts.koreanUnDotum60.getData().setScale(1);
        }


        Label buttonOk = new Label("Ok",dialogButtonsStyle);
        buttonOk.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                if(dialogType.equals(DICTIONARY_DIALOG)) {
                    if (ScreenManager.getInstance().getScreen(Constants.DICTIONARY) == null) {
                        game.setScreen(new DictionaryScreen(game));
                    } else{
                        game.setScreen(ScreenManager.getInstance().getScreen(Constants.DICTIONARY));}

                    dialog.hide();

                } else if(dialogType.equals(RATE_DIALOG)){
                    Gdx.net.openURI(Constants.GOOGLE_PLAY_URL);
                    stage.getActors().removeValue(gameInfoTable,true);
                    stage.getActors().removeValue(backgroundInfo,true);
                    dialog.hide();
                    setGameInfo();
                }  else {
                    if(ScreenManager.getInstance().isInterstitialAdReady()) {
                        ScreenManager.getInstance().getHandler().showInterstitialAds(true);
                        prefs.putInteger(Constants.PREFS_PROMPTS, prefs.getInteger(Constants.PREFS_PROMPTS) + 3).flush();
                        stage.getActors().removeValue(gameInfoTable,true);
                        stage.getActors().removeValue(backgroundInfo,true);
                        dialog.hide();
                        setGameInfo();
                    } else {
                        dialog.hide();
                        stage.addActor(noAdReadyLabel);
                        noAdReadyLabel.addAction(Actions.sequence(Actions.fadeIn(1), Actions.fadeOut(1)));
                    }
                }

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if(prefs.getBoolean(Constants.PREFS_SOUND))
                    Assets.instance.sounds.soundClick.play(0.1f);

                return true;
            }
        });
        buttonOk.setAlignment(Align.center);

        Label buttonCancel = new Label("Cancel", dialogButtonsStyle);
        buttonCancel.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(prefs.getBoolean(Constants.PREFS_SOUND))
                    Assets.instance.sounds.soundClick.play(0.1f);
                dialog.hide();
                return true;
            }
        });
        buttonCancel.setAlignment(Align.center);



        dialog.getButtonTable().padBottom(dialog.getBackground().getMinHeight()/5);
        dialog.getButtonTable().add(buttonOk).width(dialog.getBackground().getMinWidth()/2);
        dialog.getButtonTable().add(buttonCancel).width(dialog.getBackground().getMinWidth()/2).align(Align.center);

        dialog.show(stage);


    }

}
