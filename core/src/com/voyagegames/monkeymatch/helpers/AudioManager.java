package com.voyagegames.monkeymatch.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class AudioManager {
	
	public enum MusicSelection {
		NONE,
		JUNGLE,
		WIN
	}
	
	public enum SoundSelection {
		SUCCESS,
		FAILURE,
		APPLAUSE
	}
	
	private boolean mEnabled;
	private float mVolume;
	private MusicSelection mMusic;
	private Music mJungle;
	private Music mWin;
	private Sound mSuccess;
	private Sound mFailure;
	private Sound mApplause;
	
	public AudioManager() {
		mEnabled = true;
		mVolume = 1f;
		mMusic = MusicSelection.NONE;
	}
	
	public boolean enabled() {
		return mEnabled;
	}
	
	public void setEnabled(final boolean enabled) {
		mEnabled = enabled;
		
		if (enabled) {
			playMusic(mMusic);
		} else {
			stopMusic();
			mSuccess.stop();
			mFailure.stop();
			mApplause.stop();
		}
	}
	
	public float volume() {
		return mVolume;
	}
	
	public void setVolume(final float volume) {
		mVolume = volume;
	}
	
	public void initialize() {
		mJungle = Gdx.audio.newMusic(new FileHandle("assets/sounds/jungle.mp3"));
		mWin = Gdx.audio.newMusic(new FileHandle("assets/sounds/win.mp3"));
		mSuccess = Gdx.audio.newSound(new FileHandle("assets/sounds/success.mp3"));
		mFailure = Gdx.audio.newSound(new FileHandle("assets/sounds/fail.mp3"));
		mApplause = Gdx.audio.newSound(new FileHandle("assets/sounds/applause.mp3"));
	}
	
	public void dispose() {
		mJungle.dispose();
		mWin.dispose();
		mSuccess.dispose();
		mFailure.dispose();
		mApplause.dispose();
	}
	
	public boolean isMusicPlaying() {
		switch (mMusic) {
		case JUNGLE:
			return mJungle.isPlaying();
		case WIN:
			return mWin.isPlaying();
		default:
			return false;
		}
	}
	
	public void stopMusic() {
		switch (mMusic) {
		case JUNGLE:
			mJungle.stop();
			break;
		case WIN:
			mWin.stop();
			break;
		}
	}
	
	public void playMusic(final MusicSelection selection) {
		stopMusic();
		
		if (!mEnabled) {
			mMusic = selection;
			return;
		}
		
		switch (selection) {
		case JUNGLE:
			mJungle.setLooping(true);
			mJungle.setVolume(mVolume);
			mJungle.play();
			break;
		case WIN:
			mWin.setLooping(false);
			mWin.setVolume(mVolume);
			mWin.play();
			break;
		}
		
		mMusic = selection;
	}
	
	public void playSound(final SoundSelection selection) {
		if (!mEnabled) {
			return;
		}
		
		switch (selection) {
		case SUCCESS:
			mSuccess.play(mVolume);
			break;
		case FAILURE:
			mFailure.play(mVolume);
			break;
		case APPLAUSE:
			mApplause.play(mVolume);
			break;
		}
	}

}
