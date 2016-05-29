package com.mario.game.GameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.mario.game.Entities.Player;

import java.util.Iterator;

/**
 * Created by Mario Méndez on 08/05/2016.
 */
public class Play implements Screen{
    private String STR_CASA = "casa";

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private Player player;

    private int[] background = new int[]{0}, foreground = new int[]{1};

    @Override
    public void show() {
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("maps/mapa.tmx");


        renderer = new OrthogonalTiledMapRenderer(map);

        camera = new OrthographicCamera();

        player = new Player(new Sprite(new Texture("entities/still1.png")), (TiledMapTileLayer)map.getLayers().get(0));
        player.setPosition(12 * player.getCollisionLayer().getTileWidth(), 8 * player.getCollisionLayer().getTileHeight());

        Gdx.input.setInputProcessor(player);

        //======ANIMACION FLORES=======

        //frames
        Array<StaticTiledMapTile> frameTiles = new Array<StaticTiledMapTile>(2);

        //cogemos los frame tiles
        Iterator<TiledMapTile> tiles = map.getTileSets().getTileSet("tiles").iterator();
        while(tiles.hasNext()){
            TiledMapTile tile = tiles.next();
            if(tile.getProperties().containsKey("animacion") && tile.getProperties().get("animacion", String.class).equals("flor")){
                frameTiles.add((StaticTiledMapTile) tile);
            }
        }

        //creamos la animacion
        AnimatedTiledMapTile animatedTile = new AnimatedTiledMapTile(1/3f, frameTiles);

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("background");

        for(int x=0; x<layer.getWidth(); x++){
            for(int y=0; y<layer.getHeight(); y++){
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if(cell.getTile().getProperties().containsKey("animacion") && cell.getTile().getProperties().get("animacion", String.class).equals("flor")){
                    cell.setTile(animatedTile);
                }
            }
        }

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
        camera.update();

        renderer.setView(camera);

        renderer.render(background);
        renderer.getBatch().begin();
        player.draw(renderer.getBatch());
        renderer.getBatch().end();
        renderer.render(foreground);

        if(player.getX() > player.getCollisionLayer().getTileWidth()*16
            && player.getX() < player.getCollisionLayer().getTileWidth()*18
            && player.getY() > player.getCollisionLayer().getTileHeight()*3
            && player.getY() < player.getCollisionLayer().getTileHeight()*4){
            ((Game)Gdx.app.getApplicationListener()).setScreen(new CambioMapa(STR_CASA));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width / 4.2f;
        camera.viewportHeight = height / 4.2f;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {

    }
}
