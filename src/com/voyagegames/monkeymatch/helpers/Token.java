package com.voyagegames.monkeymatch.helpers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Token {
	
	public final Actor actor;
	public final int type;
	public final Vector2 initialPosition;
	public final float weight; 
	
	public Token(final Actor actor, final int type, final Vector2 initialPosition, final float weight) {
		this.actor = actor;
		this.type = type;
		this.initialPosition = initialPosition;
		this.weight = weight;
	}

}
