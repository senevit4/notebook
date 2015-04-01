package com.epam.wt.controller;

import java.util.HashMap;
import java.util.Map;

public final class Response {
	private Map<String, Object> responses = new HashMap<String, Object>();

	public void setResponse(String key, Object obj) {
		responses.put(key, obj);
	}

	public Object getParam(String key) {
		return responses.get(key);

	}

}
