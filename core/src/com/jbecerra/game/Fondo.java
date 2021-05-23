package com.jbecerra.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Fondo {
    Texture texture;
    Texture hud;
    Fondo(){
        texture = new Texture("fondo.jpg");
        hud = new Texture("hud.png");
    }

    public void render(SpriteBatch batch) {

        batch.draw(texture, 0,0);


        batch.draw(hud, 1600, 250, 300, 600);
    }
}
