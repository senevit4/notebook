package com.epam.wt.controller;

import java.util.HashMap;
import java.util.Map;

public final class Request {

	private Map<String, Object> parameters = new HashMap<String, Object>();
	private String key;

	public void setParam(String key, Object value) {

		parameters.put(key, value);
	}

	public void setParam(String key) {
		parameters.put(key, null);
	}

	public Object getParam(String key) {
		return parameters.get(key);
	}

	public String getKey() {
		return key;
	}
}
