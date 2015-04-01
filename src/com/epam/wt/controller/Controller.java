 package com.epam.wt.controller;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.epam.wt.command.Command;
import com.epam.wt.command.CommandException;
import com.epam.wt.command.CommandHelper;
import com.epam.wt.command.CommandName;

public final class Controller
{
	private final CommandHelper helper = new CommandHelper();

	private final static Logger log = Logger.getLogger(CommandException.class
			.getName());

	public Response doRequest(CommandName cn, Request request)
	{
		try
		{
			Command command = helper.getCommand(cn);
			Response response = command.execute(request);
			return response;
		} catch (CommandException e)
		{

			//System.out.println("CRITICAL ERROR!!!");
			PropertyConfigurator.configure("log4j.properties");
			log.error(e.getStackTrace(), e);
			//Response response = new Response();
			//response.setResponse(ResponseParam.RESPONSE.toString(), "CRITICAL ERROR!!!");
			return null;
			
		}
	}
}

