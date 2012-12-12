package com.voyagegames.monkeymatch.helpers;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class GridBox {
	
	public final Actor target;
	public final Actor box;
	
	public GridBox(final Actor target, final Actor box) {
		this.target = target;
		this.box = box;
	}

}
