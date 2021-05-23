package com.jbecerra.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Explosion {
    static Texture texture = new Texture("alienmuerto.png");
    float x, y, w, h;
    Temporizador texplosion;

    Explosion(float xAlien, float yAlien, float wAlien, float hAlien){
        texplosion = new Temporizador(20);
        x = xAlien;
        y = yAlien;
        w = wAlien;
        h = hAlien;

    }

    void render(SpriteBatch batch){

        batch.draw(texture, x, y, w, h);
    }
}
