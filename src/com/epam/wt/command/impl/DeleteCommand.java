package com.epam.wt.command.impl;

import com.epam.wt.command.Command;
import com.epam.wt.command.CommandException;
import com.epam.wt.controller.Request;
import com.epam.wt.controller.RequestParam;
import com.epam.wt.controller.Response;
import com.epam.wt.controller.ResponseParam;
import com.epam.wt.dao.DaoException;
import com.epam.wt.dao.NoteBookDao;
import com.epam.wt.dao.NoteBookDaoFactory;

public final class DeleteCommand implements Command {

	@Override
	public Response execute(Request request) throws CommandException {
		try {
			NoteBookDao dao = NoteBookDaoFactory.getDAO();
			Response response = new Response();
			dao.deleteNote(Integer.parseInt((String) request
					.getParam(RequestParam.ID.toString())));
			response.setResponse(ResponseParam.LIST_OF_NOTES.toString(),
					dao.showNoteBook());
			return response;
		} catch (DaoException e) {
			throw new CommandException("DeleteCommand Error", e);
		}
	}

}
