package com.jbecerra.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {

	static Random random = new Random();
	SpriteBatch batch;
	BitmapFont bitmapFont;
	Fondo fondo;
	Nave nave;
	List<Alien> aliens = new ArrayList<>();
	List<Alien2> aliens2 = new ArrayList<>();
	List<Bala> balasAEliminar = new ArrayList<>();
	List<Alien> aliensAEliminar = new ArrayList<>();
	List<Alien2> aliensAEliminar2 = new ArrayList<>();
	Temporizador temporizadorNuevoAlien;
	Temporizador temporizadorNuevoAlien2;
	Scoreboard scoreboard;
	boolean gameover;
	List<Explosion> explosiones = new ArrayList<>();
	List<Explosion> explosionesAEliminar = new ArrayList<>();
	boolean pausa = true;
	Texture pause;
	Animacion AnimacionMenu;
    boolean Menu = true;

	Sound musica_menu;
	Sound musica_juego;




	@Override
	public void create() {
		batch = new SpriteBatch();
		bitmapFont = new BitmapFont();
		bitmapFont.setColor(Color.WHITE);
		bitmapFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		bitmapFont.getData().setScale(2f);
		AnimacionMenu = new Animacion(15f, true, "menu1.png", "menu2.png","menu3.png");
		musica_menu = Gdx.audio.newSound(Gdx.files.internal("Intro.mp3"));
		musica_menu.setLooping(musica_menu.play(0.1f),true);
		pause = new Texture("pause.png");

		inicializarJuego();




	}
	void inicializarJuego() {
		fondo = new Fondo();
		nave = new Nave();
		temporizadorNuevoAlien = new Temporizador(120);
		temporizadorNuevoAlien2 = new Temporizador(150);
		scoreboard = new Scoreboard();
		gameover = false;

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (Menu && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
			Menu = false;
			pausa = false;
			musica_menu.stop();
			musica_juego = Gdx.audio.newSound(Gdx.files.internal("Juego.mp3"));
			musica_juego.setLooping(musica_juego.play(0.1f), true);


		}


		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			pausa = !pausa;
		}

		Temporizador.tiempomenu += 1;


		if (!pausa) {

			Temporizador.tiempoJuego += 1;


			if (temporizadorNuevoAlien.suena()) aliens.add(new Alien());
			if (temporizadorNuevoAlien2.suena()) aliens2.add(new Alien2());

			if (!gameover) {
				nave.update();

				for (Alien alien : aliens) alien.update();
				for (Alien2 alien2 : aliens2) alien2.update();

			} else {

				int result = scoreboard.update(nave.puntos);
				if (result == 1) {
					inicializarJuego();
				} else if (result == 2) {
					Gdx.app.exit();
				}


			}


			balasAEliminar.clear();
			aliensAEliminar.clear();
			for (Alien alien : aliens) {
				for (Bala bala : nave.balas) {
					if (Utils.solapan(bala.x, bala.y, bala.w, bala.h, alien.x, alien.y, alien.w, alien.h)) {
						balasAEliminar.add(bala);
						aliensAEliminar.add(alien);
						explosiones.add(new Explosion(alien.x, alien.y, alien.w, alien.h));
						alien.sound.play();
						nave.puntos += 3;
						break;
					}
				}

				if (!gameover && !nave.muerta && Utils.solapan(alien.x, alien.y, alien.w, alien.h, nave.x, nave.y, nave.w, nave.h)) {
					nave.vidas--;
					nave.muerta = true;
					nave.respawn.activar();
					if (nave.vidas == 0) {
						gameover = true;
					}
				}
			}
			for (Alien2 alien2 : aliens2) {
				for (Bala bala : nave.balas) {
					if (Utils.solapan(bala.x, bala.y, bala.w, bala.h, alien2.x, alien2.y, alien2.w, alien2.h)) {
						balasAEliminar.add(bala);
						aliensAEliminar2.add(alien2);
						explosiones.add(new Explosion(alien2.x, alien2.y, alien2.w, alien2.h));
						alien2.sound.play();
						nave.puntos += 5;
						break;
					}
				}

				if (!nave.muerta && Utils.solapan(alien2.x, alien2.y, alien2.w, alien2.h, nave.x, nave.y, nave.w, nave.h)) {
					nave.vidas--;
					nave.muerta = true;
					nave.respawn.activar();
					if (nave.vidas == 0) {
						gameover = true;

					}
				}
			}
			for (Explosion explosion : explosiones) {
				if (explosion.texplosion.suena()) {
					explosionesAEliminar.add(explosion);
				}
			}

			for (Explosion explosion : explosionesAEliminar) explosiones.remove(explosion);
			for (Bala bala : balasAEliminar) nave.balas.remove(bala);
			for (Alien alien : aliensAEliminar) aliens.remove(alien);
			for (Alien2 alien2 : aliensAEliminar2) aliens2.remove(alien2);


			batch.begin();
			fondo.render(batch);
			nave.render(batch);
			for (Explosion explosion : explosiones) {
				explosion.render(batch);
			}
			for (Alien alien : aliens) alien.render(batch);
			for (Alien2 alien2 : aliens2) alien2.render(batch);
			bitmapFont.draw(batch, "VIDAS: " , 1550, 1020);
			bitmapFont.draw(batch, "PUNTOS: " + nave.puntos, 60, 1020, 250f, 0, true);

			if (gameover) {

				scoreboard.render(batch, bitmapFont);



			}


			batch.end();

		}else {
		    batch.begin();
		    batch.draw(pause, 450, 460, 950, 200);
		    batch.end();
        }
		if (Menu){
			batch.begin();
			batch.draw(AnimacionMenu.getFrame(Temporizador.tiempomenu), 0, 0, 1920, 1080);

			batch.end();

		}

	}
}





/*
create();
while(true) render();
 */