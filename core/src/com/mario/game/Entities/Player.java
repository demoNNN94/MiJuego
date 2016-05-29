package com.mario.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Mario Méndez on 08/05/2016.
 */
public class Player extends Sprite implements InputProcessor{

    private Vector2 velocity = new Vector2();

    private float speed = 60 * 2, gravity = 60 * 1.0f;

    private TiledMapTileLayer collisionLayer;

    public Player(Sprite sprite, TiledMapTileLayer collisionLayer){
        super(sprite);
        this.collisionLayer = collisionLayer;
    }


    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    public void update(float delta){
        /*velocity.y -= gravity * delta;

        if(velocity.y > speed){
            velocity.y = speed;
        }else if(velocity.y < speed){
            velocity.y = -speed;
        }*/

        //Antigua pos
        float oldX = getX();
        float oldY = getY();
        float tileWidth = collisionLayer.getTileWidth();
        float tileHeight = collisionLayer.getTileHeight();

        boolean collisionX = false;
        boolean collisionY = false;

        //move on x
        setX(getX() + velocity.x * delta);

        if(velocity.x < 0){
            //top left
            collisionX = collisionLayer.getCell((int)(getX() / tileWidth), (int)((getY() + getHeight()) / tileHeight)).getTile().getProperties().containsKey("blocked");

            //middle left
            if(!collisionX)
            collisionX = collisionLayer.getCell((int)(getX() / tileWidth), (int)((getY() + getHeight() / 2) /  tileHeight)).getTile().getProperties().containsKey("blocked");

            //bottom left
            if(!collisionX)
            collisionX = collisionLayer.getCell((int)(getX() / tileWidth), (int)(getY() / tileHeight)).getTile().getProperties().containsKey("blocked");

        }else if(velocity.x > 0){
            //top right
            collisionX = collisionLayer.getCell((int)((getX() + getWidth()) / tileWidth), (int)((getY() + getHeight()) / tileHeight)).getTile().getProperties().containsKey("blocked");

            //middle right
            if(!collisionX)
            collisionX = collisionLayer.getCell((int)((getX() + getWidth()) / tileWidth), (int)((getY() + getWidth() / 2) / tileHeight)).getTile().getProperties().containsKey("blocked");

            //bottom right
            if(!collisionX)
            collisionX = collisionLayer.getCell((int)((getX() + getWidth()) / tileWidth), (int)(getY() / tileHeight)).getTile().getProperties().containsKey("blocked");

        }

        //volver a posicion antes de colision
        if(collisionX){
            setX(oldX);
            velocity.x = 0;
        }

        //move on y
        setY(getY() + velocity.y * delta);

        if(velocity.y < 0){
            //bottom left
            collisionY = collisionLayer.getCell((int)(getX() / tileWidth), (int)(getY() / tileHeight)).getTile().getProperties().containsKey("blocked");

            //bottom midle
            if(!collisionY)
            collisionY = collisionLayer.getCell((int)((getX() + getWidth() / 2) / tileWidth),(int)(getY() / tileHeight)).getTile().getProperties().containsKey("blocked");

            //bottom right
            if(!collisionY)
            collisionY = collisionLayer.getCell((int)((getX() + getWidth()) / tileWidth), (int)(getY() / tileHeight)).getTile().getProperties().containsKey("blocked");

        }else if(velocity.y > 0){
            //top left
            collisionY = collisionLayer.getCell((int)(getX() / tileWidth), (int)((getY() + getHeight()) / tileHeight)).getTile().getProperties().containsKey("blocked");

            //top middle
            if(!collisionY)
            collisionY = collisionLayer.getCell((int)((getX() + getWidth()) / tileWidth), (int)((getY() + getWidth() / 2) / tileHeight)).getTile().getProperties().containsKey("blocked");

            //top right
            if(!collisionY)
            collisionY = collisionLayer.getCell((int)((getX() + getWidth()) / tileWidth), (int)((getY() + getHeight()) / tileHeight)).getTile().getProperties().containsKey("blocked");
        }

        if(collisionY){
            setY(oldY);
            velocity.y = 0;
        }
    }

    //SETTERS Y GETTERS

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }

    public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }


    //INPUT

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.W:
                velocity.y = speed;
                break;
            case Input.Keys.S:
                velocity.y = -speed;
                break;
            case Input.Keys.A:
                velocity.x = -speed;
                break;
            case Input.Keys.D:
                velocity.x = speed;
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.D:
            case Input.Keys.W:
            case Input.Keys.S:
                velocity.x = 0;
                velocity.y = 0;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
