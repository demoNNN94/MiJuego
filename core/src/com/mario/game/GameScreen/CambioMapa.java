package com.mario.game.GameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mario.game.tween.SpriteAccessor;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by Mario Méndez on 25/05/2016.
 */
public class CambioMapa implements Screen {

    String mapa;

    private SpriteBatch batch;
    private Sprite splash;
    private TweenManager tweenManager;

    public CambioMapa(String mapa) {
        this.mapa = mapa;
    }

    public String getMapa() {
        return mapa;
    }

    public void setMapa(String mapa) {
        this.mapa = mapa;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        splash = new Sprite(new Texture("splash/fondo.jpg"));
        splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Tween.set(splash, SpriteAccessor.ALFA).target(0).start(tweenManager);
        Tween.to(splash, SpriteAccessor.ALFA, 2).target(1).start(tweenManager);
        Tween.to(splash, SpriteAccessor.ALFA, 2).target(0).delay(2).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                if(mapa.equals("casa")){
                    ((Game)Gdx.app.getApplicationListener()).setScreen(new Casa());
                }

            }
        }).start(tweenManager);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tweenManager.update(delta);

        batch.begin();
        splash.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        splash.getTexture().dispose();
    }
}
