package com.voyagegames.weatherroute;

import com.voyagegames.weatherroute.core.IXmlHandler;

public interface IUrlXmlHandler <T> extends IXmlHandler<T> {
	
	public String url();
	public T handler();

}
