package com.jtc.credit.model;

public class WsResponseModel<T> {
	private String service;
	private String code;
	private String message;
	private T data;

	public WsResponseModel() {
		// TODO Auto-generated constructor stub
	}

	
	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
}
