package com.eugene.findex;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created by Администратор on 01.10.2017.
 */

public class Level {

    private Label label;

    public Level(int number, float x, float y, Label.LabelStyle labelStyle){

        label = new Label("Level "+number,labelStyle);
        label.setPosition(x,y);
    }

    public Label getLabel() {
        return label;
    }

}
