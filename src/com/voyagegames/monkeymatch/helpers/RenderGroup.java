package com.voyagegames.monkeymatch.helpers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class RenderGroup {
	
    public final ShaderProgram shader;
    private final List<RenderEntity> mEntities;
    
    public RenderGroup(final ShaderProgram shader) {
    	this.shader = shader;
    	this.mEntities = new ArrayList<RenderEntity>();
    }
    
    public void addEntity(final RenderEntity entity) {
    	mEntities.add(entity);
    }
    
    public void removeEntity(final RenderEntity entity) {
    	mEntities.remove(entity);
    }
	
	public void render(final float delta, final Camera camera) {
		shader.begin();
		shader.setUniformMatrix("u_viewProj", camera.combined);
		
		for (final RenderEntity entity : mEntities) {
			shader.setUniformMatrix("u_modelMatrix", entity.modelMatrix());
			entity.mesh.render(shader, entity.primitiveType);
		}
		
		shader.end();
	}

}
