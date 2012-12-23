package com.voyagegames.monkeymatch.helpers;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.voyagegames.monkeymatch.IApplicationProvider;

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
	
	private final IApplicationProvider mApp;
	
	private boolean mEnabled;
	private float mVolume;
	private MusicSelection mMusic;
	private Music mJungle;
	private Music mWin;
	private Sound mSuccess;
	private Sound mFailure;
	private Sound mApplause;
	
	public AudioManager(final IApplicationProvider app) {
		mApp = app;
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
		if (mJungle == null) {
			mJungle = mApp.openMusic("sounds/jungle.mp3");
		}
		
		if (mWin == null) {
			mWin = mApp.openMusic("sounds/win.mp3");
		}
		
		if (mSuccess == null) {
			mSuccess = mApp.openSound("sounds/success.mp3");
		}
		
		if (mFailure == null) {
			mFailure = mApp.openSound("sounds/fail.mp3");
		}
		
		if (mApplause == null) {
			mApplause = mApp.openSound("sounds/applause.mp3");
		}
	}
	
	public void dispose() {
		if (mJungle != null) {
			mJungle.dispose();
			mJungle = null;
		}
		
		if (mWin != null) {
			mWin.dispose();
			mWin = null;
		}
		
		if (mSuccess != null) {
			mSuccess.dispose();
			mSuccess = null;
		}
		
		if (mFailure != null) {
			mFailure.dispose();
			mFailure = null;
		}
		
		if (mApplause != null) {
			mApplause.dispose();
			mApplause = null;
		}
	}
	
	public boolean isMusicPlaying() {
		switch (mMusic) {
		case JUNGLE:
			if (mJungle != null) {
				return mJungle.isPlaying();
			}
			
			return false;
		case WIN:
			if (mWin != null) {
				return mWin.isPlaying();
			}
			
			return false;
		default:
			return false;
		}
	}
	
	public void stopMusic() {
		switch (mMusic) {
		case JUNGLE:
			if (mJungle != null) {
				mJungle.stop();
			}
			break;
		case WIN:
			if (mWin != null) {
				mWin.stop();
			}
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
			if (mJungle != null) {
				mJungle.setLooping(true);
				mJungle.setVolume(mVolume);
				mJungle.play();
			}
			break;
		case WIN:
			if (mWin != null) {
				mWin.setLooping(false);
				mWin.setVolume(mVolume);
				mWin.play();
			}
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
			if (mSuccess != null) {
				mSuccess.play(mVolume);
			}
			break;
		case FAILURE:
			if (mFailure != null) {
				mFailure.play(mVolume);
			}
			break;
		case APPLAUSE:
			if (mApplause != null) {
				mApplause.play(mVolume);
			}
			break;
		}
	}

}
