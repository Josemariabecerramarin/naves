package com.jbecerra.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class Nave {
    Animacion nave = new Animacion(6f, true, "nave1.png", "nave2.png","nave3.png");
    float x, y, w, h, v;
    List<Bala> balas;
    int vidas = 3;
    int puntos = 0;
    boolean muerta = false;

    Temporizador fireRate;
    Temporizador respawn;

    Nave(){
        x = 100;
        y = 100;
        w = 90;
        h = 80;
        v = 10;
        balas = new ArrayList<>();
        fireRate = new Temporizador(10);
        respawn = new Temporizador(120, false);
    }

    void render(SpriteBatch batch){
        if (muerta) batch.setColor(1, 1, 1, 0.35f);
        batch.draw(nave.getFrame(Temporizador.tiempoJuego), x, y, w, h);
        if (muerta) batch.setColor(1, 1, 1, 1);



        for (Bala bala: balas) {
            bala.render(batch);
        }
    }
    void update(){

        for (Bala bala: balas) {
            bala.update();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) x += v;
        if(Gdx.input.isKeyPressed(Input.Keys.A)) x -= v;
        if(Gdx.input.isKeyPressed(Input.Keys.W)) y += v;
        if(Gdx.input.isKeyPressed(Input.Keys.S)) y -= v;

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            balas.add(new Bala(x+10, y+60));
            balas.add(new Bala(x+72, y+60));
        }
        if(x < 0) x = 1920; //para hacer los limites de pantalla
        if(y < 50) y = 50;
        if(x > 1920) x = 0;
        if(y > 1060 - h) y = 1060 - h;

        if(respawn.suena()){
            muerta = false;
        }
    }
    public void morir() {
        vidas--;
        muerta = true;
        respawn.activar();
    }

}