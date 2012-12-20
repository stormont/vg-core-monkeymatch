package com.voyagegames.monkeymatch;

import java.io.InputStream;

public interface IApplicationProvider {
	
	public InputStream openAsset(String path);
	public void finish();

}
