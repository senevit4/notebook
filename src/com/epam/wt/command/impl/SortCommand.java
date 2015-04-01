package com.epam.wt.command.impl;

import com.epam.wt.command.Command;
import com.epam.wt.command.CommandException;
import com.epam.wt.controller.Request;
import com.epam.wt.controller.Response;
import com.epam.wt.controller.ResponseParam;
import com.epam.wt.dao.DaoException;
import com.epam.wt.dao.NoteBookDao;
import com.epam.wt.dao.NoteBookDaoFactory;

public final class SortCommand implements Command {

	@Override
	public Response execute(Request request) throws CommandException {
		try {
			NoteBookDao dao = NoteBookDaoFactory.getDAO();
			dao.sortNoteBook();
			Response response = new Response();
			response.setResponse(ResponseParam.LIST_OF_NOTES.toString(),
					dao.showNoteBook());
			return response;
		} catch (DaoException e) {
			throw new CommandException("SortCommand Error", e);
		}
	}

}
