package com.voyagegames.monkeymatch.helpers;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class GridBox {
	
	public final int offset;
	public final Actor target;
	public final Actor box;
	
	public GridBox(final int offset, final Actor target, final Actor box) {
		this.offset = offset;
		this.target = target;
		this.box = box;
	}

}
