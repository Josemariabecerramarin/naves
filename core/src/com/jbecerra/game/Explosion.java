package com.jbecerra.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Explosion {
    static Texture texture = new Texture("alienmuerto.png");
    float x, y, w, h;
    // Sound explosion;
    Temporizador texplosion;

    Explosion(float xAlien, float yAlien, float wAlien, float hAlien){
        texplosion = new Temporizador(20);
        x = xAlien;
        y = yAlien;
        w = wAlien;
        h = hAlien;
        // explosion = Gdx.audio.newSound(Gdx.files.internal("Sound/explosion.mp3"));
        // explosion.play(0.2f);
    }

    void render(SpriteBatch batch){
        batch.draw(texture, x, y, w, h);
    }
}
