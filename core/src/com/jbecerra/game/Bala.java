package com.jbecerra.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Bala {
    static Texture texture = new Texture("bala.png");
    Sound sound;

    float x, y, w, h, v;

    Bala(float xNave, float yNave){
        w = 40;
        h = 50;
        x = xNave-w/2;
        y = yNave;
        v = 25;
        sound = Gdx.audio.newSound(Gdx.files.internal("disparo.ogg"));
        sound.play();
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, w, h);
    }

    void update(){
        y += v;
    }
}
