package com.eugene.findex;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.eugene.findex.utils.Constants;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Администратор on 19.09.2017.
 */

public class Field {

    public static final String TAG = Field.class.getSimpleName();

    Array<Cell> cellArray;
    private int cellsInRow = 0;
    private int cellsInColumn;
    private Vector2 fieldStartBound;
    private Vector2 fieldEndBound;

    private ArrayList<Word> words;
    private Array<Integer> filledCellsIndexes;
    private ArrayList<Integer> firstLetterIndexes;

    public Field(ArrayList<Word> allWords) {
        init(allWords);
    }

    public void init(ArrayList<Word> allWords) {

        cellArray = new Array<Cell>();
        filledCellsIndexes = new Array<Integer>();
        firstLetterIndexes = new ArrayList<Integer>();

        words = allWords;

        String fakesChars = "졸간요델체부진디너엔니어농변인둑술만번난싸짜싱겁좋쁘짧오래된로득아장소카페은" +
                "행병원수영가게시학교대강의실도서관식당영화극공사무기자역항회점관미용스테이트약국마백여문" +
                "구고속버스널교회선생님칠판숙제셔츠티바지반바지치피두신발드레운동하힐모청양말코재킷안경갑" +
                "양복파브라팬한웨터우비조끼깃매단추전과방년유업목험성적급출석졸";

        char[] fakes = fakesChars.toCharArray();

        float posX = Constants.CELL_SIZE / 2;
        float posY = Constants.WORLD_HEIGHT - Constants.CELL_SIZE * 1.75f;

        fieldStartBound = new Vector2(posX, posY+Constants.CELL_SIZE);

        cellsInColumn = 1;

        //set positions for each cell
        for (int i = 0; i < Constants.NUMBER_OF_CELLS; i++) {

            if (cellsInRow == Constants.CELLS_IN_ROW) {
                posX = Constants.CELL_SIZE / 2;
                posY -= Constants.CELL_SIZE-1;
                cellsInRow = 0;
                cellsInColumn++;
            }

            cellArray.add(new Cell(posX, posY, "0"));

            posX += Constants.CELL_SIZE-1;
            cellsInRow++;

        }

        float posXend = Constants.CELL_SIZE / 2 + (Constants.CELL_SIZE * Constants.CELLS_IN_ROW);
        fieldEndBound = new Vector2(posXend, posY);

        writeWordsOnField();

        writeFakeSymbols(fakes);

    }


    public void render(SpriteBatch batch, BitmapFont font) {

        for (Cell cell : cellArray) {
            cell.render(batch, font);
        }

    }

    public Array<Cell> getFieldCells(){
        return cellArray;
    }

    public Vector2 getFieldStartBound(){return fieldStartBound;}

    public Vector2 getFieldEndBound() {return fieldEndBound;}

    private void writeWordsOnField (){

        Random random = new Random();

        for(int i = 0; i <words.size();  i++) {

            boolean insertionCompleted = false;

            while (!insertionCompleted) {
                //Horizontal Word Orientation
                if (random.nextInt(2) == 0)
                    insertionCompleted = insertInRow(words.get(i).getKoreanWord());
                //Vertical Word Orientation
                else
                    insertionCompleted = insertInColumn(words.get(i).getKoreanWord());
            }
        }

    }

    private boolean insertInRow(String word){

        char[] wordChars = word.toCharArray();
        boolean insertionCompleted = false;
        boolean rowIsOk = true;
        Random random = new Random();

        int row = random.nextInt(cellsInColumn);

        //define indexes of word placement in row
        int startBound = row * Constants.CELLS_IN_ROW; // 0,7,14,...
        int endBound = startBound + (Constants.CELLS_IN_ROW); // 7,14,...
        int startIndex = startBound + random.nextInt(Constants.CELLS_IN_ROW - Constants.CELLS_IN_ROW / 3);

        //check, if word fits in row
        if(wordChars.length<=endBound-startIndex) {
            //check, if row consists letters of other words
            for (int i = startIndex; i < startIndex+wordChars.length; i++) {
                if (!cellArray.get(i).getCellChar().equals("0")) {
                    rowIsOk = false;
                    break;
                }
            }
        } else {
            rowIsOk = false;
        }

        //write word to row
        if(rowIsOk){
            int x = 0;
            firstLetterIndexes.add(startIndex);
            for (int i = startIndex; i < startIndex+wordChars.length; i++) {
                cellArray.get(i).setCellChar(wordChars[x]);
                filledCellsIndexes.add(i);
                x++;
            }
            insertionCompleted = true;
        }

        return insertionCompleted;

    }

    private boolean insertInColumn(String word){

        char[] wordChars = word.toCharArray();
        boolean insertionCompleted = false;
        boolean columnIsOk = true;
        Random random = new Random();

        //define indexes of word placement in column
        int startBound = random.nextInt(Constants.CELLS_IN_ROW); // 0,1,2,...
        int endBound = startBound + (Constants.CELLS_IN_ROW*(cellsInColumn-1)); // 42,43,44...
        int startIndex = startBound + (Constants.CELLS_IN_ROW*(random.nextInt(cellsInColumn-2)));
        int endIndex = startIndex+((wordChars.length-1)*Constants.CELLS_IN_ROW);

        //check, if word fits in column
        if(wordChars.length<=(((endBound-startIndex)/Constants.CELLS_IN_ROW)+1)) {
            //check, if column consists letters of other words
            for (int i = startIndex; i < endIndex+1; i+=Constants.CELLS_IN_ROW) {

                if (!cellArray.get(i).getCellChar().equals("0")) {
                    columnIsOk = false;
                    break;
                }
            }
        } else {
            columnIsOk = false;
        }

        //write word to column
        if(columnIsOk){
            int x = startIndex;
            firstLetterIndexes.add(startIndex);
            for (int i = 0; i < wordChars.length; i++) {
                cellArray.get(x).setCellChar(wordChars[i]);
                filledCellsIndexes.add(x);
                x+=Constants.CELLS_IN_ROW;
            }
            insertionCompleted = true;
        }

        return insertionCompleted;
    }

    private void writeFakeSymbols(char[] fakes){

        Random random = new Random();

        for(int i = 0; i<cellArray.size; i++){

            boolean isFreeIndex = true;

            for(int x = 0; x<filledCellsIndexes.size; x++){
                if(i==filledCellsIndexes.get(x))
                    isFreeIndex = false;
            }
            if(isFreeIndex)
                cellArray.get(i).setCellChar(fakes[random.nextInt(fakes.length)]);
        }
    }

    public ArrayList<Integer> getFirstLetterIndexes() {
        return firstLetterIndexes;
    }

}


