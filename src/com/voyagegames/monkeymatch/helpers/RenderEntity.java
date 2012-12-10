package com.voyagegames.monkeymatch.helpers;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.math.Matrix4;

public class RenderEntity {
	
	public final Mesh mesh;
	public final int primitiveType;
	
	private Matrix4 modelMatrix;
	
	public RenderEntity(final Mesh mesh, final int primitiveType) {
		this.mesh = mesh;
		this.primitiveType = primitiveType;
		this.modelMatrix = new Matrix4();
	}
	
	public void setModelMatrix(final Matrix4 modelMatrix) {
		this.modelMatrix = modelMatrix;
	}
	
	public Matrix4 modelMatrix() {
		return modelMatrix;
	}

}
