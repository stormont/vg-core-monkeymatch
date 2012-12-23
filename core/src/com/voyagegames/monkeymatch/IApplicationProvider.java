package com.voyagegames.monkeymatch;

import java.io.InputStream;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public interface IApplicationProvider {
	
	public InputStream openAsset(String path);
	public Music openMusic(String path);
	public Sound openSound(String path);
	public void finish();

}
