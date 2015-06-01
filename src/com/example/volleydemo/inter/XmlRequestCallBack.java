package com.example.volleydemo.inter;

import org.xmlpull.v1.XmlPullParser;

public interface XmlRequestCallBack {
	
	public abstract void getXmlResponse(XmlPullParser response);
}
