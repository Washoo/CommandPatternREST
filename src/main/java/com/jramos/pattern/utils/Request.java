package com.jramos.pattern.utils;

import org.springframework.lang.Nullable;

public class Request<T> {

	@Nullable
	private T body;
	
	public final static Request<?> EMPTY = new Request<Object>();
	
	public Request(@Nullable T body) {
		this.body = body;
	}
	
	public Request() {
		this(null);
	}

	@Nullable
	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}
}
