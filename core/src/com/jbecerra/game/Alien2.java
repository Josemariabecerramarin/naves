package com.jbecerra.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public class Alien2 {

        Animacion alienv = new Animacion(9f, true, "alienv1.png", "alienv2.png");
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("s.mp3"));
        float x, y, w, h, vx, vy;
        Temporizador cambioVelocidad;

        Alien2() {
            x = MyGdxGame.random.nextInt(1920);
            y = 1000;
            w = 50;
            h = 40;
            vx = 1;
            vy = 1.7f;
            cambioVelocidad = new Temporizador(30);

        }

        void render(SpriteBatch batch) {

            batch.draw(alienv.getFrame(Temporizador.tiempoJuego), x, y, w, h);
        }

        public void update() {
            y -= vy;
            x += vx;
            if (cambioVelocidad.suena()) {
                vy = MyGdxGame.random.nextInt(7) - 2;
                vx = MyGdxGame.random.nextInt(6) - 3;
            }
        }
    }



