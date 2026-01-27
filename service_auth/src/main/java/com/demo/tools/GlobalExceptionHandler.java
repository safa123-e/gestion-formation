package com.demo.tools;

public class GlobalExceptionHandler extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public GlobalExceptionHandler(String message, Throwable cause) {
		super(message, cause);
	}

}