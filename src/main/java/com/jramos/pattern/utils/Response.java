package com.jramos.pattern.utils;

public class Response<T> {
	
	private T body;
	private Status status;
		
	public Response(T body) {
		this.body = body;
	}
	
	public Response() {
		this(null);
	}
	
	public T getBody() {
		return body;
	}
	public void setBody(T body) {
		this.body = body;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
}
