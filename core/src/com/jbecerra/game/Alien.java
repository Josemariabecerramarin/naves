package com.jbecerra.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public class Alien {





        Animacion alien = new Animacion(9f, true, "alienr1.png", "alienr2.png");

        float x, y, w, h, vx, vy;
        Temporizador cambioVelocidad;
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("s.mp3"));


        Alien() {
            x = MyGdxGame.random.nextInt(1920);
            y = 1000;
            w = 50;
            h = 40;
            vx = 1;
            vy = 0.7f;
            cambioVelocidad = new Temporizador(30);


        }

        void render(SpriteBatch batch) {




                batch.draw(alien.getFrame(Temporizador.tiempoJuego), x, y, w, h);
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


