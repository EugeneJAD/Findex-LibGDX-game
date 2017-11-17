package com.eugene.findex;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.eugene.findex.utils.Assets;
import com.eugene.findex.utils.Constants;
import com.eugene.findex.utils.Utils;


/**
 * Created by Администратор on 19.09.2017.
 */

public class Cell {

    private Vector2 position;
    private String cellChar;
    private Constants.CellState cellState;

    public Cell(float x, float y, String cellChar) {

        position = new Vector2(x,y);
        this.cellChar = cellChar;
        cellState = Constants.CellState.INITIAL;

    }


    public void render(SpriteBatch batch, BitmapFont font) {


        if(cellState== Constants.CellState.ACTIVATED)
            Utils.drawTextureRegion(batch, Assets.instance.textures.cellGrey,position.x, position.y, Constants.CELL_SIZE,Constants.CELL_SIZE);
        else
            Utils.drawTextureRegion(batch,Assets.instance.textures.cellWhite,position.x, position.y, Constants.CELL_SIZE,Constants.CELL_SIZE);


        if(cellState==Constants.CellState.TRACKED){
            font.setColor(Color.VIOLET);
            font.getData().setScale(1.1f);
        } else if (cellState==Constants.CellState.ACTIVATED){
            font.getData().setScale(1);
            font.setColor(Color.FOREST);
        } else {
            font.setColor(Color.WHITE);
            font.getData().setScale(1);
        }

        font.draw(batch, cellChar,position.x+Constants.CELL_SIZE/2,position.y+Constants.CELL_SIZE/2+font.getCapHeight()/2,0, Align.center,false);

    }

    public String getCellCharWhileSearching(){

        if(cellState==Constants.CellState.INITIAL){
            cellState = Constants.CellState.TRACKED;
            return cellChar;
        } else if (cellState==Constants.CellState.ACTIVATED){
            return cellChar;
        } else
            return "";

    }

    public String getCellChar(){return cellChar;}

    public Vector2 getCellPosition(){
        return position;
    }
    public Constants.CellState getCellState() {return cellState;}

    public void setCellState(Constants.CellState state){cellState = state;}

    public void setCellChar(char cellChar) {
        this.cellChar = String.valueOf(cellChar);
    }

}
