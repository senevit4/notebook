package com.epam.wt.command;

import com.epam.wt.controller.Request;
import com.epam.wt.controller.Response;


public interface Command {
	public  Response execute(Request request) throws CommandException;

}
