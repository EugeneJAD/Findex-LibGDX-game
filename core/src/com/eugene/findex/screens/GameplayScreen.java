package com.eugene.findex.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.eugene.findex.Cell;
import com.eugene.findex.Field;
import com.eugene.findex.Word;
import com.eugene.findex.utils.Assets;
import com.eugene.findex.utils.Constants;
import com.eugene.findex.utils.ScreenManager;
import com.eugene.findex.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Администратор on 19.09.2017.
 */

public class GameplayScreen extends InputAdapter implements Screen {

    public static final String TAG = GameplayScreen.class.getSimpleName();

    public static final String GET_PROMPTS_DIALOG = "prompts_type";
    public static final String RATE_DIALOG = "rate_type";

    private Game game;
    private Stage stage;
    private Stage dialogStage;
    private ExtendViewport viewport;
    private SpriteBatch batch;

    private Preferences prefs;

    private String category;
    private int level;

    private Image searchButton;
    private int promptsCount;
    private Image backgroundTint;

    //sounds
    private boolean gongIsPlayed;

    //game objects
    private Field field;

    //searching
    private String word;
    private String displaySearchText;
    private Array<Integer> lastTrackedCellsIndex;
    private Cell lastTrackedCell;
    private boolean dragging = false;
    private float countSearchButtonsTextPosY;

    //words
    private ArrayList<Word> levelWords;
    private HashMap<String,Label> labelsEnglish;
    private ArrayList<String> guessedWords;


    //time
    private float timeOfLevel;
    private float time;
    private Image clock;

    //Game states
    private Constants.GameState gameState;

    //no interstitial ad message
    private Label noAdReadyLabel;
    private Label.LabelStyle noAdReadyLabelStyle;




    public GameplayScreen(Game aGame, String category, int level) {

        prefs = Gdx.app.getPreferences(Constants.GAME_PREFERENCES);

        init(aGame,category,level);

        //Add Screen to the ScreenManager
        ScreenManager.getInstance().addScreen(Constants.GAME_FIELD,this);

    }

    public void init(Game aGame, String category, int level) {

        this.game = aGame;
        this.category = category;
        this.level = level;

        //Set time depends on level
        timeOfLevel = Constants.TIME_LEVEL - 30*level ;
        time = Constants.TIME_LEVEL + Constants.TIME_STARTING - 30*level;

        gongIsPlayed = false;

        viewport = new ExtendViewport(Constants.WORLD_WIDTH,Constants.WORLD_HEIGHT);

        batch = new SpriteBatch();

        //get All words
        levelWords = Utils.getWordsByCategoryLevel(category, level);
        guessedWords = new ArrayList<String>();

        field = new Field(levelWords);

        word = "";
        displaySearchText = "";
        lastTrackedCellsIndex = new Array<Integer>();


        //initialise game state
        gameState = Constants.GameState.PLAYING;

        //UI Stage
        stage = new Stage(viewport);

        //Background
        Image background = new Image(Assets.instance.textures.backgroundGF);
        background.setHeight(stage.getViewport().getWorldHeight());
        background.setWidth(stage.getViewport().getWorldWidth());
        background.setPosition(0,0);
        stage.addActor(background);

        //Dialog screen background
        backgroundTint = new Image(Assets.instance.textures.backgroundDialogTint);
        backgroundTint.setWidth(stage.getViewport().getWorldWidth());
        backgroundTint.setHeight(stage.getViewport().getWorldHeight());
        backgroundTint.setPosition(0,0);

        //List of english words
        labelsEnglish = new HashMap<String, Label>();


        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Assets.instance.fonts.unDotumShadowGold60;

        float posX = (Constants.CELL_SIZE * (Constants.CELLS_IN_ROW+1));
        float posY =  stage.getViewport().getWorldHeight() - Constants.SPACE_BETWEEN_WORDS*6 + Constants.SPACE_BETWEEN_WORDS;

        float delayTime = 0;

        for (int i =0; i<levelWords.size();i++) {
            labelsEnglish.put(levelWords.get(i).getEnglishWord(), new Label(levelWords.get(i).getEnglishWord(), labelStyle));
            labelsEnglish.get(levelWords.get(i).getEnglishWord()).setPosition(posX,-labelsEnglish.get(levelWords.get(i).getEnglishWord()).getHeight()*2);
            labelsEnglish.get(levelWords.get(i).getEnglishWord()).addAction(Actions.sequence(Actions.delay(delayTime),Actions.moveTo(posX,posY,1,Interpolation.exp5)));
            stage.addActor(labelsEnglish.get(levelWords.get(i).getEnglishWord()));
            posY -= Constants.SPACE_BETWEEN_WORDS;
            delayTime+=0.2f;
        }



        Label.LabelStyle levelLabelStyle = new Label.LabelStyle();
        levelLabelStyle.font = Assets.instance.fonts.koreanUnDotum60;

        // level label
        Label levelNumberLabel = new Label("Level "+level,levelLabelStyle);
        levelNumberLabel.setPosition( stage.getViewport().getWorldWidth()-levelNumberLabel.getWidth()-Constants.CELL_SIZE/2,
                stage.getViewport().getWorldHeight()+ levelNumberLabel.getHeight());
        levelNumberLabel.setAlignment(Align.right);
        levelNumberLabel.addAction(Actions.moveTo( stage.getViewport().getWorldWidth()-levelNumberLabel.getWidth()-Constants.CELL_SIZE/2,
                stage.getViewport().getWorldHeight() - levelNumberLabel.getHeight()*1.1f, 1, Interpolation.exp5));
        stage.addActor(levelNumberLabel);

        //Buttons
        searchButton = new Image(Assets.instance.textures.buttonSearch);
        searchButton.setHeight(140);
        searchButton.setWidth(140);
        searchButton.setPosition(stage.getViewport().getWorldWidth()-searchButton.getWidth()-Constants.CELL_SIZE/6,
                stage.getViewport().getWorldHeight()/2);
        searchButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                searchButton.setY(searchButton.getY()+5);

                if(promptsCount>0) {
                    for (int j : field.getFirstLetterIndexes()) {

                        if (field.getFieldCells().get(j).getCellState() == Constants.CellState.INITIAL) {

                            field.getFieldCells().get(j).setCellState(Constants.CellState.ACTIVATED);

                            if (prefs.getBoolean(Constants.PREFS_SOUND))
                                Assets.instance.sounds.soundPrompt.play(0.2f);

                            promptsCount -= 1;
                            prefs.putInteger(Constants.PREFS_PROMPTS, promptsCount).flush();

                            break;
                        }
                    }
                } else {
                    if(!prefs.getBoolean(Constants.PREFS_IS_RATE_DIALOG_SHOWN))
                        showDialog(RATE_DIALOG);
                    else
                        showDialog(GET_PROMPTS_DIALOG);
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if(prefs.getBoolean(Constants.PREFS_SOUND))
                    Assets.instance.sounds.soundClick.play(0.1f);

                searchButton.setY(searchButton.getY()-5);

                return true;
            }
        });
        stage.addActor(searchButton);

        countSearchButtonsTextPosY = searchButton.getY();

        final Image homeIconButton = new Image(Assets.instance.textures.homeButton);
        homeIconButton.setPosition(stage.getViewport().getWorldWidth()-homeIconButton.getWidth()-Constants.CELL_SIZE/4,
                field.getFieldEndBound().y - homeIconButton.getHeight()/5);
        homeIconButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                homeIconButton.setY(homeIconButton.getY()+5);
                game.setScreen(new MainScreen(game));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if(prefs.getBoolean(Constants.PREFS_SOUND))
                    Assets.instance.sounds.soundClick.play(0.1f);

                homeIconButton.setY(homeIconButton.getY()-5);

                return true;
            }
        });
        stage.addActor(homeIconButton);


        //clock
        clock = new Image(Assets.instance.textures.clock);
        clock.setPosition(Constants.CELL_SIZE*(Constants.CELLS_IN_ROW+1)-clock.getWidth()/3,
                stage.getViewport().getWorldHeight() - clock.getHeight()*1.2f);
        stage.addActor(clock);

        //Dialog Stage
        dialogStage = new Stage(viewport);


        Label.LabelStyle goLabelStyle = new Label.LabelStyle();
        goLabelStyle.font = Assets.instance.fonts.chowShadow60;

        // "GO!" Label
        Label goLabel = new Label("GO!", goLabelStyle);
        goLabel.setPosition(Constants.CELL_SIZE*(Constants.CELLS_IN_ROW+2) + goLabel.getWidth()/2,
                stage.getViewport().getWorldHeight() - Constants.CELL_SIZE*2 -  goLabel.getWidth()/4);
        dialogStage.addActor(goLabel);
        goLabel.addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.fadeIn(0.5f),Actions.fadeOut(0.5f),Actions.fadeIn(0.5f),Actions.fadeOut(0.5f)));


        //ad
        noAdReadyLabelStyle = new Label.LabelStyle();
        noAdReadyLabelStyle.font = Assets.instance.fonts.unDotum40;
        noAdReadyLabelStyle.fontColor = Color.FIREBRICK;
        noAdReadyLabel = new Label("Sorry, Ad is not loaded yet", noAdReadyLabelStyle);
        noAdReadyLabel.setPosition(Constants.CELL_SIZE/2,stage.getViewport().getWorldHeight()-noAdReadyLabel.getHeight());

    }


    @Override
    public void show() {
        promptsCount = prefs.getInteger(Constants.PREFS_PROMPTS);
        Gdx.input.setInputProcessor(new InputMultiplexer(stage,this));
        gameState = Constants.GameState.PLAYING;
        ScreenManager.getInstance().getHandler().showBannerAds(true);
    }

    @Override
    public void render(float delta) {

        viewport.apply();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        //render game field
        field.render(batch, Assets.instance.fonts.koreanUnDotum55);


        Assets.instance.fonts.koreanUnDotum55.getData().setScale(1);

        //render time
        if(gameState== Constants.GameState.PLAYING)
            time-=delta;

        if((int)time==(int)timeOfLevel) {
            if(!gongIsPlayed) {
                gongIsPlayed = true;
                if(prefs.getBoolean(Constants.PREFS_SOUND))
                    Assets.instance.sounds.soundGong.play(0.1f);
            }
        }

        Assets.instance.fonts.koreanUnDotum55.setColor(Color.LIGHT_GRAY);

        String timeToString;
        if(time>0 && time<=timeOfLevel) {
            if ((int) time % 60 < 10) {
                if((int)time/60==0)
                    Assets.instance.fonts.koreanUnDotum55.setColor(Color.RED);
                timeToString = "0" + (int) time / 60 + ":0" + (int) time % 60;
            } else
                timeToString = "0" + (int) time / 60 + ":" + (int) time % 60;
        } else if(time<=0) {
            timeToString = "00:00";
        } else {
            if(timeOfLevel%60 == 0)
                timeToString = "0" + (int) timeOfLevel / 60 + ":0" + (int) timeOfLevel % 60;
            else
                timeToString = "0" + (int) timeOfLevel / 60 + ":" + (int) timeOfLevel % 60;
        }

        Assets.instance.fonts.koreanUnDotum55.draw(batch, timeToString,
                Constants.CELL_SIZE * (Constants.CELLS_IN_ROW + 1) + clock.getWidth(),
                stage.getViewport().getWorldHeight() - Assets.instance.fonts.koreanUnDotum55.getLineHeight() / 3,
                0, Align.left, false);

        //render text box for display searching text
        Utils.drawTextureRegion(batch,Assets.instance.textures.textBox,Constants.CELL_SIZE*(Constants.CELLS_IN_ROW+1),
                stage.getViewport().getWorldHeight() - Constants.CELL_SIZE*1.7f - Assets.instance.fonts.koreanUnDotum55.getLineHeight(),
                stage.getViewport().getWorldWidth()-(Constants.CELL_SIZE*(Constants.CELLS_IN_ROW+1.3f)),
                Assets.instance.fonts.koreanUnDotum55.getLineHeight()+Constants.CELL_SIZE/2
        );

        //render searching text
        Assets.instance.fonts.koreanUnDotum55.setColor(Color.VIOLET);
        Assets.instance.fonts.koreanUnDotum55.draw(batch,displaySearchText,
                stage.getViewport().getWorldWidth() - (stage.getViewport().getWorldWidth()-(Constants.CELL_SIZE*(Constants.CELLS_IN_ROW+1.3f)))/2
                        -Assets.instance.fonts.koreanUnDotum55.getCapHeight()/2,
                stage.getViewport().getWorldHeight() - Constants.CELL_SIZE*1.7f + 5 ,
                0, Align.center,false);

        //render prompts count
        Assets.instance.fonts.unDotumShadowGold60.setColor(Color.FOREST);
        Assets.instance.fonts.unDotumShadowGold60.getData().setScale(0.5f);
        Assets.instance.fonts.unDotumShadowGold60.draw(batch,"x"+promptsCount,
                searchButton.getX()+searchButton.getWidth()/3,
                countSearchButtonsTextPosY+Assets.instance.fonts.unDotumShadowGold60.getCapHeight()/4);
        Assets.instance.fonts.unDotumShadowGold60.getData().setScale(1);
        Assets.instance.fonts.unDotumShadowGold60.setColor(Color.GOLD);

        batch.end();

        dialogStage.act();
        dialogStage.draw();

        if(time<=0 && gameState== Constants.GameState.PLAYING){
            gameState= Constants.GameState.GAME_OVER;
            showDialogStage();
        }


    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);
        dialogStage.getViewport().update(width,height,true);
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
        batch.dispose();
        stage.dispose();
        dialogStage.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        // Unproject the touch from the screen to the world
        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        if (isTouchOnField(worldTouch) && !dragging ) {

            word = "";
            dragging = true;

            for (int i=0;i<field.getFieldCells().size;i++) {

                Cell cell = field.getFieldCells().get(i);

                if(isTouchOnCell(worldTouch,cell)){
                    word+=cell.getCellCharWhileSearching();
                    setDisplaySearchText(word);
                    lastTrackedCellsIndex.add(i);
                    lastTrackedCell = cell;
                }
            }
        }

        return true;

    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));


        if(isTouchOnField(worldTouch)){

            if (!dragging) return false;

            for (int i=0;i<field.getFieldCells().size;i++) {

                Cell cell = field.getFieldCells().get(i);

                if(isTouchOnCell(worldTouch,cell)) {

                    if(lastTrackedCell!=cell){

                        if(word.length()<7) {
                            int wordLength = word.length();
                            word += cell.getCellCharWhileSearching();
                            if (word.length() > wordLength)
                                lastTrackedCellsIndex.add(i);
                        } else {
                            untrackCells();
                            lastTrackedCellsIndex.add(i);
                            word = "";
                            cell.setCellState(Constants.CellState.INITIAL);
                            word += cell.getCellCharWhileSearching();
                        }

                        lastTrackedCell=cell;
                        setDisplaySearchText(word);

                    }


                }
            }
        } else {
            if(checkCoincidence())
                activateFoundCells();
            else
                untrackCells();
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        setDisplaySearchText(word);

        if(checkCoincidence())
            activateFoundCells();
        else
            untrackCells();

        dragging = false;

        return true;
    }

    private void activateFoundCells() {

        for (int i = 0; i < field.getFieldCells().size; i++) {
            for (int x : lastTrackedCellsIndex) {
                if (i == x)
                    field.getFieldCells().get(i).setCellState(Constants.CellState.ACTIVATED);

            }
        }

        setDisplaySearchText(word);

        //Add word to guessed words array
        if(!guessedWords.isEmpty()){
            int coincidence =0;

            for(String s:guessedWords){
                if(s.equals(word)){
                    coincidence++;
                }
            }
            //if new guessed word
            if(coincidence==0) {
                guessedWords.add(word);
                if(prefs.getBoolean(Constants.PREFS_SOUND))
                    Assets.instance.sounds.soundWordFound.play(0.1f);
            }
        } else {
            guessedWords.add(word);
            if(prefs.getBoolean(Constants.PREFS_SOUND))
                Assets.instance.sounds.soundWordFound.play(0.1f);
        }

        //reset tracked word and cells
        word = "";
        lastTrackedCellsIndex.clear();

        //check if the level completed
        if(guessedWords.size()==levelWords.size()){
            gameState= Constants.GameState.LEVEL_COMPLETED;
            showDialogStage();
        }

    }

    private boolean isTouchOnField(Vector2 worldTouch) {

        return worldTouch.x >= field.getFieldStartBound().x && worldTouch.x <= field.getFieldEndBound().x
                && worldTouch.y <= field.getFieldStartBound().y && worldTouch.y >= field.getFieldEndBound().y;
    }

    private boolean isTouchOnCell(Vector2 worldTouch, Cell cell) {

        return worldTouch.x >= cell.getCellPosition().x && worldTouch.x <= (cell.getCellPosition().x+Constants.CELL_SIZE)
                && worldTouch.y >= cell.getCellPosition().y && worldTouch.y <= (cell.getCellPosition().y+Constants.CELL_SIZE);
    }

    private void untrackCells () {

        for (int i = 0; i < field.getFieldCells().size; i++) {

            for (int x : lastTrackedCellsIndex) {
                if (i == x) {
                    if(field.getFieldCells().get(i).getCellState()!= Constants.CellState.ACTIVATED)
                        field.getFieldCells().get(i).setCellState(Constants.CellState.INITIAL);
                }
            }
        }

        lastTrackedCellsIndex.clear();

    }

    public void setDisplaySearchText(String displaySearchText) {
        this.displaySearchText = displaySearchText;
    }

    public boolean checkCoincidence(){

        for(Word w: levelWords){
            if(w.getKoreanWord().equals(word)) {
                word = w.getEnglishWord();
                labelsEnglish.get(word).setColor(Color.GRAY);
                return true;
            }
        }

        return false;
    }


    private void showDialogStage(){


        if(gameState == Constants.GameState.LEVEL_COMPLETED){

            //Update game progress for category
            if (category.equals(Constants.CATEGORY_PLACES)) {
                int progress =  prefs.getInteger(Constants.PREFS_PLACES_PROGRESS);
                if(level>progress)
                    prefs.putInteger(Constants.PREFS_PLACES_PROGRESS, progress + 1).flush();
            } else if(category.equals(Constants.CATEGORY_CLOTHES)) {
                int progress =  prefs.getInteger(Constants.PREFS_CLOTHES_PROGRESS);
                if(level>progress)
                    prefs.putInteger(Constants.PREFS_CLOTHES_PROGRESS, progress + 1).flush();
            } else if(category.equals(Constants.CATEGORY_EDUCATION)) {
                int progress =  prefs.getInteger(Constants.PREFS_EDUCATION_PROGRESS);
                if(level>progress)
                    prefs.putInteger(Constants.PREFS_EDUCATION_PROGRESS, progress + 1).flush();
            } else if(category.equals(Constants.CATEGORY_VERBS)) {
                int progress =  prefs.getInteger(Constants.PREFS_VERBS_PROGRESS);
                if(level>progress)
                    prefs.putInteger(Constants.PREFS_VERBS_PROGRESS, progress + 1).flush();
            } else if(category.equals(Constants.CATEGORY_WEATHER)) {
                int progress =  prefs.getInteger(Constants.PREFS_WEATHER_PROGRESS);
                if(level>progress)
                    prefs.putInteger(Constants.PREFS_WEATHER_PROGRESS, progress + 1).flush();
            } else if(category.equals(Constants.CATEGORY_VERBS)) {
                int progress =  prefs.getInteger(Constants.PREFS_VERBS_PROGRESS);
                if(level>progress)
                    prefs.putInteger(Constants.PREFS_VERBS_PROGRESS, progress + 1).flush();
            } else if(category.equals(Constants.CATEGORY_FOOD)) {
                int progress =  prefs.getInteger(Constants.PREFS_FOOD_PROGRESS);
                if(level>progress)
                    prefs.putInteger(Constants.PREFS_FOOD_PROGRESS, progress + 1).flush();
            } else if(category.equals(Constants.CATEGORY_ADJECTIVES)) {
                int progress =  prefs.getInteger(Constants.PREFS_ADJECTIVES_PROGRESS);
                if(level>progress)
                    prefs.putInteger(Constants.PREFS_ADJECTIVES_PROGRESS, progress + 1).flush();
            } else if(category.equals(Constants.CATEGORY_ANIMALS)) {
                int progress =  prefs.getInteger(Constants.PREFS_ANIMALS_PROGRESS);
                if(level>progress)
                    prefs.putInteger(Constants.PREFS_ANIMALS_PROGRESS, progress + 1).flush();
            } else if(category.equals(Constants.CATEGORY_OUTDOORS)) {
                int progress =  prefs.getInteger(Constants.PREFS_OUTDOORS_PROGRESS);
                if(level>progress)
                    prefs.putInteger(Constants.PREFS_OUTDOORS_PROGRESS, progress + 1).flush();
            }

            //All levels completed?
            if(level==Constants.LEVELS_IN_CATEGORY) {
                gameState = Constants.GameState.CATEGORY_COMPLETED;
                prefs.putBoolean(category,true).flush();
            }

        }

        dialogStage.addActor(backgroundTint);

        //Dialog background
        Image dialogBackground = new Image(Assets.instance.textures.backgroundDialog);
        dialogBackground.setPosition(viewport.getWorldWidth()/2 - dialogBackground.getWidth()/2, viewport.getWorldHeight()/2 - dialogBackground.getHeight()/2);
        dialogStage.addActor(dialogBackground);


        Label.LabelStyle dialogTextStyle = new Label.LabelStyle();
        dialogTextStyle.font = Assets.instance.fonts.koreanUnDotum60;



        Image awardImage = null;

        //Define labels and button next of the dialog
        String bigLabel1 = "";
        String bigLabel2 = "";
        String labelStyle = "";
        String buttonLabel = "";

        if(gameState == Constants.GameState.LEVEL_COMPLETED){

            bigLabel1 = "Level";
            bigLabel2 = "Complete";
            buttonLabel = "Next";

            if(prefs.getBoolean(Constants.PREFS_SOUND))
                Assets.instance.sounds.soundLevelCompleted.play(0.3f);

            dialogTextStyle.fontColor = Color.CYAN;
            awardImage = new Image(Assets.instance.textures.medal);
            awardImage.setWidth(94);
            awardImage.setHeight(107);

        } else if(gameState == Constants.GameState.CATEGORY_COMPLETED){
            bigLabel1 = "Category";
            bigLabel2 = "Complete";

            if(prefs.getBoolean(Constants.PREFS_SOUND))
                Assets.instance.sounds.soundCategoryCompleted.play(0.3f);

            dialogTextStyle.fontColor = Color.GREEN;
            awardImage = new Image(Assets.instance.textures.award);
            awardImage.setWidth(110);
            awardImage.setHeight(98);

        } else if(gameState == Constants.GameState.GAME_OVER){
            bigLabel1 = "Game";
            bigLabel2 = "Over";
            buttonLabel = "Try again";
            dialogTextStyle.fontColor = Color.RED;
        }

        if(awardImage!=null){
            awardImage.setPosition(dialogBackground.getX() + dialogBackground.getWidth() - awardImage.getWidth() - dialogBackground.getWidth()/14,
                    dialogBackground.getY() + dialogBackground.getHeight() -  awardImage.getHeight() - dialogBackground.getWidth()/18);
            dialogStage.addActor(awardImage);
        }

        Label title1 = new Label(bigLabel1, dialogTextStyle);
        Label title2 = new Label(bigLabel2, dialogTextStyle);

        title1.setPosition(-title1.getWidth()*2, stage.getViewport().getWorldHeight()/2);
        title2.setPosition(viewport.getWorldWidth()+title2.getWidth()*2, viewport.getWorldHeight()/2 - title2.getHeight()/2);

        dialogStage.addActor(title1);
        dialogStage.addActor(title2);


        title1.addAction(Actions.moveTo(viewport.getWorldWidth() / 2 - title1.getWidth() / 2,
                viewport.getWorldHeight() / 2 + title1.getHeight()/3, 0.4f, Interpolation.elastic));
        title2.addAction(Actions.moveTo(viewport.getWorldWidth() / 2 - title2.getWidth() / 2,
                viewport.getWorldHeight() / 2 - title2.getHeight() / 2, 0.4f, Interpolation.elastic));


        Label.LabelStyle dialogButtonsStyle = new Label.LabelStyle();
        dialogButtonsStyle.font = Assets.instance.fonts.unDotum40;

        //Home button
        Label homeButton = new Label("Home", dialogButtonsStyle);
        homeButton.setPosition(dialogBackground.getX()+dialogBackground.getWidth()/10,
                dialogBackground.getY() + homeButton.getHeight());
        homeButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                ScreenManager.getInstance().setAppState(Constants.AppState.INITIAL);
                game.setScreen(new MainScreen(game));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if(prefs.getBoolean(Constants.PREFS_SOUND))
                    Assets.instance.sounds.soundClick.play(0.1f);
                return true;
            }
        });
        dialogStage.addActor(homeButton);

        //Button next
        if(gameState!= Constants.GameState.CATEGORY_COMPLETED) {
            Label nextButton = new Label(buttonLabel, dialogButtonsStyle);
            nextButton.setPosition(dialogBackground.getX() + dialogBackground.getWidth() - nextButton.getWidth() - dialogBackground.getWidth() / 10,
                    dialogBackground.getY() + nextButton.getHeight());
            nextButton.addListener(new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                    dispose();
                    if(gameState == Constants.GameState.GAME_OVER)
                        init(game, category, level);
                    else
                        init(game, category, ++level);
                    show();
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(prefs.getBoolean(Constants.PREFS_SOUND))
                        Assets.instance.sounds.soundClick.play(0.1f);
                    return true;
                }
            });
            dialogStage.addActor(nextButton);
        }

        if(prefs.getBoolean(Constants.PREFS_SOUND)) {
            Assets.instance.sounds.soundWordFound.stop();
            Assets.instance.sounds.soundDialogTitle.play(0.3f);
        }

        Gdx.input.setInputProcessor(dialogStage);

        if(level%2==0){
            ScreenManager.getInstance().getHandler().showInterstitialAds(true);
        }

    }

    private void showDialog(final String dialogType){

        gameState = Constants.GameState.PAUSE;

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

        Gdx.input.setInputProcessor(dialogStage);

        String dialogMessage;
        if(dialogType.equals(RATE_DIALOG)) {
            dialogMessage = "You got 5 additional prompts. Rate the game please.";
            prefs.putInteger(Constants.PREFS_PROMPTS, prefs.getInteger(Constants.PREFS_PROMPTS)+5);
            prefs.putBoolean(Constants.PREFS_IS_RATE_DIALOG_SHOWN, true);
            prefs.flush();
        } else {
            dialogMessage = "Watch Ad and get 3 extra Prompts?";
        }


        Label dialogLabel = new Label(dialogMessage, dialogTextStyle);
        dialogLabel.setAlignment(Align.center);
        dialogLabel.setWrap(true);

        dialog.getContentTable().add(dialogLabel).pad(0,
                dialog.getBackground().getMinHeight()/10,
                dialog.getBackground().getMinHeight()/25,
                dialog.getBackground().getMinHeight()/10)
                .width(dialog.getBackground().getMinWidth());


        if(dialogType.equals(GET_PROMPTS_DIALOG)) {

            Assets.instance.fonts.koreanUnDotum60.getData().setScale(0.6f);

            Label promptsCountLabel = new Label ("x3", dialogButtonsStyle);

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

                if(dialogType.equals(RATE_DIALOG)) {
                    //open URL
                    Gdx.net.openURI(Constants.GOOGLE_PLAY_URL);
                    show();
                } else {
                    //show Interstitial Ad
                    if(ScreenManager.getInstance().isInterstitialAdReady()) {
                        ScreenManager.getInstance().getHandler().showInterstitialAds(true);
                        prefs.putInteger(Constants.PREFS_PROMPTS, prefs.getInteger(Constants.PREFS_PROMPTS) + 3).flush();
                        promptsCount = prefs.getInteger(Constants.PREFS_PROMPTS);
                    } else {
                        stage.addActor(noAdReadyLabel);
                        noAdReadyLabel.addAction(Actions.sequence(Actions.fadeIn(0.5f), Actions.delay(0.5f), Actions.fadeOut(1)));
                    }
                }

                dialog.hide();
                show();
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
                show();
                return true;
            }
        });
        buttonCancel.setAlignment(Align.center);

        dialog.getButtonTable().padBottom(dialog.getBackground().getMinHeight()/5);
        dialog.getButtonTable().add(buttonOk).width(dialog.getBackground().getMinWidth()/2);
        dialog.getButtonTable().add(buttonCancel).width(dialog.getBackground().getMinWidth()/2).align(Align.center);

        dialog.show(dialogStage);


    }


}

