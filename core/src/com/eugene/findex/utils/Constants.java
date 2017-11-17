package com.eugene.findex.utils;


import com.badlogic.gdx.graphics.Color;

/**
 * Created by Администратор on 19.09.2017.
 */

public class Constants {

    public static final Color DICTIONARY_BACKGROUND = Color.GRAY;

    public static final String GOOGLE_PLAY_URL = "https://play.google.com/store/apps/details?id=com.eugene.findex";

    //files
    public static final String JSON_FILE_NAME = "words.json";
    public static final String TEXTURE_ATLAS = "images/game_textures.atlas";


    //Categories
    public static final String CATEGORY_EDUCATION = "Education";
    public static final String CATEGORY_PLACES = "Places";
    public static final String CATEGORY_CLOTHES = "Clothes";
    public static final String CATEGORY_OCCUPATION ="Occupation" ;
    public static final String CATEGORY_ADJECTIVES ="Adjectives" ;
    public static final String CATEGORY_WEATHER ="Weather" ;
    public static final String CATEGORY_VERBS ="Verbs" ;
    public static final String CATEGORY_FOOD ="Food" ;
    public static final String CATEGORY_ANIMALS="Animals" ;
    public static final String CATEGORY_OUTDOORS ="Outdoors" ;

    public static final int CATEGORY_QUANTITY = 10;
    public static final int PROMPTS_COUNT = 5;
    public static final int LEVELS_IN_CATEGORY = 5;
    public static final int LEVELS_IN_GAME = CATEGORY_QUANTITY*LEVELS_IN_CATEGORY;

    //Preferences name
    public static final String GAME_PREFERENCES = "korean_game_preferences";

    public static final String PREFS_PLACES_PROGRESS = "category_places_progress";
    public static final String PREFS_CLOTHES_PROGRESS = "category_clothes_progress";
    public static final String PREFS_EDUCATION_PROGRESS = "category_education_progress";
    public static final String PREFS_OCCUPATION_PROGRESS = "category_occupation_progress";
    public static final String PREFS_ADJECTIVES_PROGRESS = "category_adjectives_progress";
    public static final String PREFS_WEATHER_PROGRESS = "category_weather_progress";
    public static final String PREFS_VERBS_PROGRESS = "category_verbs_progress";
    public static final String PREFS_FOOD_PROGRESS = "category_food_progress";
    public static final String PREFS_ANIMALS_PROGRESS = "category_animals_progress";
    public static final String PREFS_OUTDOORS_PROGRESS = "category_outdoors_progress";

    public static final String PREFS_SOUND = "sound_preference";
    public static final String PREFS_PROMPTS = "prompts_count";

    public static final String PREFS_CATEGORY_PLACES_COMPLETION = CATEGORY_PLACES;
    public static final String PREFS_CATEGORY_EDUCATION_COMPLETION = CATEGORY_EDUCATION;
    public static final String PREFS_CATEGORY_CLOTHES_COMPLETION = CATEGORY_CLOTHES;
    public static final String PREFS_CATEGORY_OCCUPATION_COMPLETION = CATEGORY_OCCUPATION;
    public static final String PREFS_CATEGORY_ADJECTIVES_COMPLETION = CATEGORY_ADJECTIVES;
    public static final String PREFS_CATEGORY_WEATHER_COMPLETION = CATEGORY_WEATHER;
    public static final String PREFS_CATEGORY_VERBS_COMPLETION = CATEGORY_VERBS;
    public static final String PREFS_CATEGORY_FOOD_COMPLETION = CATEGORY_FOOD;
    public static final String PREFS_CATEGORY_ANIMALS_COMPLETION = CATEGORY_ANIMALS;
    public static final String PREFS_CATEGORY_OUTDOORS_COMPLETION = CATEGORY_OUTDOORS;

    public static final String PREFS_IS_RATE_DIALOG_SHOWN = "is_rate_dialog_shown";


    //Sounds
    public static final String SOUND_CLICK = "sounds/click.mp3";
    public static final String SOUND_DIALOG_END_LEVEL = "sounds/level_completed_sound.mp3";
    public static final String SOUND_PROMPT = "sounds/prompt_sound.mp3";
    public static final String SOUND_WORD_FOUND = "sounds/word_found_sound.mp3";
    public static final String SOUND_LEVEL_COMPLETED = "sounds/level_complete_applause.mp3";
    public static final String SOUND_CATEGORY_COMPLETED = "sounds/category_complete_applause.mp3";
    public static final String SOUND_GONG = "sounds/gong_sound.mp3";

    //Textures
    public static final String BACKGROUND_MAIN = "main_bg";
    public static final String BACKGROUND_CATEGORY = "categories_bg";
    public static final String BACKGROUND_LEVELS = "levels_bg";
    public static final String BACKGROUND_GAME_FIELD = "game_bg";
    public static final String CELL_WHITE = "cell_white";
    public static final String CELL_GREY = "cell_grey";
    public static final String TEXT_BOX = "text_field";
    public static final String CLOCK = "ic_timer";
    public static final String LOGO = "logo";
    public static final String BUTTON_SEARCH = "ic_search_button";
    public static final String ICON_SEARCH = "ic_search";
    public static final String PADLOCK = "padlock";
    public static final String AWARD = "award";
    public static final String AWARD_PLACEHOLDER = "award_placeholder";
    public static final String MEDAL = "level_completed";
    public static final String BACKGROUND_DIALOG = "dialog_background";
    public static final String BACKGROUND_DIALOG_TINT = "dialog_stage_background";
    public static final String PLATFORM = "platform";
    public static final String PLATFORM_123 = "platform_box123";
    public static final String PLATFORM_500 = "platform_box500";
    public static final String PLATFORM_LIST = "platform_list";
    public static final String ADD_BUTTON = "add_button";
    public static final String PLAY_ICON = "ic_play_arrow";
    public static final String WORDS_ICON = "ic_school_yellow";
    public static final String QUIT_ICON = "ic_power_off";
    public static final String SOUND_ON_ICON = "ic_volume_up";
    public static final String SOUND_OFF_ICON = "ic_volume_off";
    public static final String RATE_ICON = "ic_rate";
    public static final String HOME_BUTTON = "ic_home";
    public static final String ARROW = "ic_arrow_back";
    public static final String TOOLBAR = "toolbar";
    public static final String FLAG = "korean_flag";



    //world size
    public static final float WORLD_WIDTH = 1200;
    public static final float WORLD_HEIGHT = 720;

    //Field parameters
    public static final float CELL_SIZE = WORLD_HEIGHT/9.0f + 1;
    public static final int CELLS_IN_ROW = 8;
    public static final int CELLS_IN_COLUMN = 7;
    public static final int NUMBER_OF_CELLS = CELLS_IN_ROW*CELLS_IN_COLUMN;

    //font
    public static final float SPACE_BETWEEN_WORDS= WORLD_HEIGHT/12;
    public static final String FONT_CHARS_KOREAN = "정장소카페은행병원수영가게시학교대강의실도서관식당영화" +
            "극공사무기자역항회점관미용스테이트약국마백여문구고속버스널교회선생님칠판숙제책옷셔츠티바지반바" +
            "지치피두신발드레운동하힐모청양말코재킷안경갑양복파브라팬한웨터우비조끼깃매단추전과방년유업목험졸" +
            "성적급출석졸간호요리델체부진디너엔니어농변인둑배술만배번다난싸짜싱겁좋나쁘길짧오래된새로득빈" +
            "아둔크작달쓰건프넓좁습깨끗더럽해눈날씨태개온람위후송보폭설얼음풍허케홍뭄천일예산둥박덥늘읽먹입듣찾" +
            "앉닫쉬르노쇼핑야주받믿묻쥐물늑거랑토슴곰연염숭돼암펭귄채랜애플감참외몬포딸감귤근찌키금별꽃풀잔숲잎변못돌굴름집" +
            "-ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz:1234567890?./";
    public static final String FONT_CHARS_ENGLISH = "-ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz:1234567890/!?.,";
    public static final String FONT_FILE = "2593-UnDotum.ttf";
    public static final String FONT_FILE_CHOW = "CHOWFUN_.TTF";

    //Screens
    public static final String MAIN_MENU = "MainScreen";
    public static final String CATEGORIES = "CategoryScreen";
    public static final String GAME_FIELD = "GameplayScreen";
    public static final String DICTIONARY = "DictionaryScreen";

    //Time measures
    public static final float TIME_STARTING = 3;
    public static final float TIME_LEVEL = 210;  // 3.5 minutes


    //enums

    public enum AppState {
        INITIAL,
        PLAYING
    }
    public enum CellState {
        INITIAL,
        TRACKED,
        ACTIVATED
    }

    public enum GameState {
        PLAYING,
        PAUSE,
        LEVEL_COMPLETED,
        CATEGORY_COMPLETED,
        GAME_OVER
    }

}
