package com.eugene.findex.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Администратор on 19.09.2017.
 */

public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    public TextureAtlas atlas;
    private AssetManager assetManager;
    public TextureAssets textures;
    public SoundAssets sounds;
    public Fonts fonts;

    private Assets() {}

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        atlas = assetManager.get(Constants.TEXTURE_ATLAS);
        textures = new TextureAssets(atlas);
        sounds = new SoundAssets();
        fonts = new Fonts();


    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        atlas.dispose();
        assetManager.dispose();
        sounds.dispose();
        fonts.dispose();
    }


    public class TextureAssets {

        public TextureAtlas.AtlasRegion backgroundMain;
        public TextureAtlas.AtlasRegion backgroundCategory;
        public TextureAtlas.AtlasRegion backgroundLevels;
        public TextureAtlas.AtlasRegion backgroundGF;
        public TextureAtlas.AtlasRegion backgroundDialog;
        public TextureAtlas.AtlasRegion backgroundDialogTint;

        public TextureAtlas.AtlasRegion cellWhite;
        public TextureAtlas.AtlasRegion cellGrey;
        public TextureAtlas.AtlasRegion textBox;
        public TextureAtlas.AtlasRegion clock;
        public TextureAtlas.AtlasRegion logo;
        public TextureAtlas.AtlasRegion buttonSearch;
        public TextureAtlas.AtlasRegion iconSearch;
        public TextureAtlas.AtlasRegion padlock;
        public TextureAtlas.AtlasRegion award;
        public TextureAtlas.AtlasRegion awardPlaceholder;
        public TextureAtlas.AtlasRegion medal;

        public TextureAtlas.AtlasRegion platform;
        public TextureAtlas.AtlasRegion platform123;
        public TextureAtlas.AtlasRegion platform500;
        public TextureAtlas.AtlasRegion platformList;

        public TextureAtlas.AtlasRegion addButton;
        public TextureAtlas.AtlasRegion playButton;
        public TextureAtlas.AtlasRegion wordsButton;
        public TextureAtlas.AtlasRegion rateButton;
        public TextureAtlas.AtlasRegion soundOnButton;
        public TextureAtlas.AtlasRegion soundOffButton;
        public TextureAtlas.AtlasRegion quitButton;
        public TextureAtlas.AtlasRegion homeButton;

        public TextureAtlas.AtlasRegion toolbar;
        public TextureAtlas.AtlasRegion flag;
        public TextureAtlas.AtlasRegion arrowBack;

        TextureAssets(TextureAtlas atlas){

            backgroundMain = atlas.findRegion(Constants.BACKGROUND_MAIN);
            backgroundMain.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            backgroundCategory = atlas.findRegion(Constants.BACKGROUND_CATEGORY);
            backgroundCategory.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            backgroundLevels = atlas.findRegion(Constants.BACKGROUND_LEVELS);
            backgroundLevels.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            backgroundGF = atlas.findRegion(Constants.BACKGROUND_GAME_FIELD);
            backgroundGF.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            backgroundDialog = atlas.findRegion(Constants.BACKGROUND_DIALOG);
            backgroundDialog.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            backgroundDialogTint = atlas.findRegion(Constants.BACKGROUND_DIALOG_TINT);

            platform = atlas.findRegion(Constants.PLATFORM);
            platform.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            platform123 = atlas.findRegion(Constants.PLATFORM_123);
            platform123.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            platform500 = atlas.findRegion(Constants.PLATFORM_500);
            platform500.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            platformList = atlas.findRegion(Constants.PLATFORM_LIST);
            platformList.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            addButton = atlas.findRegion(Constants.ADD_BUTTON);
            addButton.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            playButton = atlas.findRegion(Constants.PLAY_ICON);
            playButton.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            wordsButton = atlas.findRegion(Constants.WORDS_ICON);
            wordsButton.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            rateButton = atlas.findRegion(Constants.RATE_ICON);
            rateButton.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            soundOnButton = atlas.findRegion(Constants.SOUND_ON_ICON);
            soundOnButton.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            soundOffButton = atlas.findRegion(Constants.SOUND_OFF_ICON);
            soundOffButton.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            quitButton = atlas.findRegion(Constants.QUIT_ICON);
            quitButton.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            homeButton = atlas.findRegion(Constants.HOME_BUTTON);
            homeButton.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            buttonSearch = atlas.findRegion(Constants.BUTTON_SEARCH);
            buttonSearch.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            iconSearch = atlas.findRegion(Constants.ICON_SEARCH);
            iconSearch.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            logo = atlas.findRegion(Constants.LOGO);
            logo.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            cellWhite = atlas.findRegion(Constants.CELL_WHITE);
            cellGrey = atlas.findRegion(Constants.CELL_GREY);
            textBox = atlas.findRegion(Constants.TEXT_BOX);
            textBox.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            clock = atlas.findRegion(Constants.CLOCK);
            clock.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            padlock = atlas.findRegion(Constants.PADLOCK);
            padlock.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            award = atlas.findRegion(Constants.AWARD);
            award.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            awardPlaceholder = atlas.findRegion(Constants.AWARD_PLACEHOLDER);
            awardPlaceholder.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            medal = atlas.findRegion(Constants.MEDAL);
            medal.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            toolbar = atlas.findRegion(Constants.TOOLBAR);
            toolbar.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            arrowBack = atlas.findRegion(Constants.ARROW);
            arrowBack.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            flag = atlas.findRegion(Constants.FLAG);
            flag.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        }

    }

    public class SoundAssets{

        public Sound soundGong;
        public Sound soundClick;
        public Sound soundPrompt;
        public Sound soundWordFound;
        public Sound soundDialogTitle;
        public Sound soundLevelCompleted;
        public Sound soundCategoryCompleted;

        SoundAssets(){

            soundGong = Gdx.audio.newSound(Gdx.files.internal(Constants.SOUND_GONG));
            soundClick = Gdx.audio.newSound(Gdx.files.internal(Constants.SOUND_CLICK));
            soundPrompt = Gdx.audio.newSound(Gdx.files.internal(Constants.SOUND_PROMPT));
            soundWordFound = Gdx.audio.newSound(Gdx.files.internal(Constants.SOUND_WORD_FOUND));
            soundDialogTitle = Gdx.audio.newSound(Gdx.files.internal(Constants.SOUND_DIALOG_END_LEVEL));
            soundLevelCompleted = Gdx.audio.newSound(Gdx.files.internal(Constants.SOUND_LEVEL_COMPLETED));
            soundCategoryCompleted = Gdx.audio.newSound(Gdx.files.internal(Constants.SOUND_CATEGORY_COMPLETED));

        }

        private void dispose(){

            soundClick.dispose();
            soundPrompt.dispose();
            soundWordFound.dispose();
            soundDialogTitle.dispose();
            soundLevelCompleted.dispose();
            soundCategoryCompleted.dispose();
            soundGong.dispose();
        }

    }

    public class Fonts{

        public BitmapFont chowShadow60;
        public BitmapFont unDotumShadowGold60;
        public BitmapFont unDotumShadowTeal60;
        public BitmapFont unDotum40;
        public BitmapFont koreanUnDotum55;
        public BitmapFont koreanUnDotum60;

        public Fonts(){

            //English Chow generator
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.FONT_FILE_CHOW));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

            parameter.borderWidth = 1;
            parameter.shadowOffsetX = 3;
            parameter.shadowOffsetY = 3;
            parameter.characters = Constants.FONT_CHARS_ENGLISH;

            //main menu font
            parameter.size = (int)Constants.WORLD_HEIGHT/12; //60 for 480
            parameter.color = Color.DARK_GRAY;
            parameter.shadowColor = Color.LIGHT_GRAY;

            chowShadow60 = generator.generateFont(parameter);
            chowShadow60.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            generator.dispose();

            //UnDotum generator
            FreeTypeFontGenerator generatorUnDotum = new FreeTypeFontGenerator(Gdx.files.internal(Constants.FONT_FILE));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();

            parameter2.size = (int)Constants.WORLD_HEIGHT/12;   //60 for 720;
            parameter2.borderWidth = 1;
            parameter2.characters = Constants.FONT_CHARS_ENGLISH;

            //categories and levels font
            parameter2.color = Color.GOLD;
            parameter2.shadowOffsetX = 2;
            parameter2.shadowOffsetY = 2;
            parameter2.shadowColor = Color.GRAY;
            unDotumShadowGold60 = generatorUnDotum.generateFont(parameter2);
            unDotumShadowGold60.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            parameter2.color = Color.TEAL;
            unDotumShadowTeal60 = generatorUnDotum.generateFont(parameter2);
            unDotumShadowTeal60.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            //white fonts

            parameter2.borderWidth = 0;
            parameter2.color = Color.WHITE;
            parameter2.shadowOffsetX = 0;
            parameter2.shadowOffsetY = 0;

            parameter2.size = (int)Constants.WORLD_HEIGHT/18; //40
            unDotum40 = generatorUnDotum.generateFont(parameter2);
            unDotum40.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


            //game field font
            parameter2.size = (int)Constants.WORLD_HEIGHT/13;     //55 for 720
            parameter2.borderWidth = 1;
            parameter2.color = Color.WHITE;
            parameter2.shadowOffsetX = 3;
            parameter2.shadowOffsetY = 3;
            parameter2.shadowColor = Color.LIGHT_GRAY;
            parameter2.characters = Constants.FONT_CHARS_KOREAN;

            koreanUnDotum55 = generatorUnDotum.generateFont(parameter2);
            koreanUnDotum55.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


            parameter2.size = (int)Constants.WORLD_HEIGHT/12;     //60 for 720
            parameter2.borderWidth = 0;
            parameter2.shadowOffsetX = 0;
            parameter2.shadowOffsetY = 0;

            koreanUnDotum60 = generatorUnDotum.generateFont(parameter2);
            koreanUnDotum60.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            generatorUnDotum.dispose();


        }

        private void dispose(){

            koreanUnDotum55.dispose();
            koreanUnDotum60.dispose();
            unDotum40.dispose();
            unDotumShadowGold60.dispose();
            unDotumShadowTeal60.dispose();
            chowShadow60.dispose();
        }



    }
}
