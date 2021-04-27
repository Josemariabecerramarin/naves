package com.jbecerra.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Alien {
    Texture texture;
    float x, y, w, h, v;

    Alien (){
        x = 100;
        y = 100;
        w = 30;
        h = 50;
        v = 2;
    }
    void draw(SpriteBatch batch){
        batch.draw();
    }
}
