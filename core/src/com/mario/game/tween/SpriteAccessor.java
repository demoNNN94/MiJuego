package com.mario.game.tween;

import com.badlogic.gdx.graphics.g2d.Sprite;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by Mario Méndez on 26/05/2016.
 */
public class SpriteAccessor implements TweenAccessor<Sprite>{
    public static final int ALFA = 0;

    @Override
    public int getValues(Sprite target, int tweenType, float[] returnValues) {
        switch(tweenType){
            case ALFA:
                returnValues[0] = target.getColor().a;
                return 1; //Devuelve un int como returnValues se devuelvan, en este caso 1.
            default:
                assert false;
                return -1;
        }
    }

    @Override
    public void setValues(Sprite target, int tweenType, float[] newValues) {
        switch (tweenType){
            case ALFA:
                //Vamos a cambiar solo el ALFA asi que obtenemos los colores actuales.
                target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
                break;
            default:
                assert false;
        }
    }
}
