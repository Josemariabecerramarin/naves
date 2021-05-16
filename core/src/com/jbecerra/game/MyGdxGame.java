package com.jbecerra.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

	@Override
	public void create () {
		batch = new SpriteBatch();
		bitmapFont = new BitmapFont();
		bitmapFont.setColor(Color.WHITE);
		bitmapFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		bitmapFont.getData().setScale(2f);
		fondo = new Fondo();
		nave = new Nave();
		scoreboard = new Scoreboard();
		gameover = false;


		temporizadorNuevoAlien = new Temporizador(120);
		temporizadorNuevoAlien2 = new Temporizador(150);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0,0,0,0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Temporizador.tiempoJuego += 1;


		if (temporizadorNuevoAlien.suena()) aliens.add(new Alien());
		if (temporizadorNuevoAlien2.suena()) aliens2.add(new Alien2());

		if(!gameover){
		nave.update();

		for (Alien alien:aliens) alien.update();
		for (Alien2 alien2:aliens2) alien2.update();

		}else{
			scoreboard.update(nave.puntos);
		}

		balasAEliminar.clear();
		aliensAEliminar.clear();
		for (Alien alien: aliens) {
			for (Bala bala: nave.balas) {
				if (solapan(bala.x, bala.y, bala.w, bala.h, alien.x, alien.y, alien.w, alien.h)) {
					balasAEliminar.add(bala);
					aliensAEliminar.add(alien);
					nave.puntos+=3;
					break;
				}
			}

			if (!gameover && !nave.muerta && solapan(alien.x, alien.y, alien.w, alien.h, nave.x, nave.y, nave.w, nave.h)) {
				nave.vidas--;
				nave.muerta = true;
				nave.respawn.activar();
				if (nave.vidas == 0){
					gameover = true;
				}
			}
		}
		for (Alien2 alien2: aliens2) {
			for (Bala bala: nave.balas) {
				if (solapan(bala.x, bala.y, bala.w, bala.h, alien2.x, alien2.y, alien2.w, alien2.h)) {
					balasAEliminar.add(bala);
					aliensAEliminar2.add(alien2);
					nave.puntos+=5;
					break;
				}
			}

			if (!nave.muerta && solapan(alien2.x, alien2.y, alien2.w, alien2.h, nave.x, nave.y, nave.w, nave.h)) {
				nave.vidas--;
				nave.muerta = true;
				nave.respawn.activar();
				if (nave.vidas == 0){
					gameover = true;
				}
			}
		}

		for (Bala bala:balasAEliminar) nave.balas.remove(bala);
		for (Alien alien:aliensAEliminar) aliens.remove(alien);
		for (Alien2 alien2:aliensAEliminar2) aliens2.remove(alien2);


		batch.begin();
		fondo.render(batch);
		nave.render(batch);
		for(Alien alien:aliens) alien.render(batch);
		for(Alien2 alien2:aliens2) alien2.render(batch);
		bitmapFont.draw(batch, "VIDAS: "+nave.vidas, 1650, 1020);
		bitmapFont.draw(batch, "PUNTOS: "+nave.puntos, 60, 1020, 250f, 0, true);

		if (gameover) {
			scoreboard.render(batch, bitmapFont);
		}


		batch.end();
	}

	boolean solapan(float x, float y, float w, float h, float x2, float y2, float w2, float h2){
		return !(x > x2 + w2) && !(x + w < x2) && !(y > y2 + h2) && !(y + h < y2);
	}
}





/*
create();
while(true) render();
 */