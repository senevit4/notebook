package com.epam.wt.command;

public class CommandException extends Exception {

	private static final long serialVersionUID = 1L;

	public CommandException(String message,Exception e) {
		super(message,e);
	}
}
