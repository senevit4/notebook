package com.epam.wt.command.impl;

import java.util.Date;

import com.epam.wt.command.Command;
import com.epam.wt.command.CommandException;
import com.epam.wt.controller.Request;
import com.epam.wt.controller.RequestParam;
import com.epam.wt.controller.Response;
import com.epam.wt.controller.ResponseParam;
import com.epam.wt.dao.DaoException;
import com.epam.wt.dao.NoteBookDao;
import com.epam.wt.dao.NoteBookDaoFactory;
import com.epam.wt.entity.Note;

public final class AddCommand implements Command {

	@Override
	public Response execute(Request request) throws CommandException {
		try {
			NoteBookDao dao = NoteBookDaoFactory.getDAO();
			Response response = new Response();
			if (request.getParam(RequestParam.NOTE.toString()) != null) {
				Note note = (Note) request.getParam(RequestParam.NOTE
						.toString());
				dao.addNote(note);
				response.setResponse(ResponseParam.LIST_OF_NOTES.toString(),
						dao.showNoteBook());
				return response;
			}
			if (request.getParam(RequestParam.TOPIC.toString()) != null) {
				dao.addTopicNote(
						request.getParam(RequestParam.RECORD.toString())
								.toString(), (Date) request
								.getParam(RequestParam.DATE.toString()),
						request.getParam(RequestParam.TOPIC.toString())
								.toString());
				response.setResponse(ResponseParam.LIST_OF_NOTES.toString(),
						dao.showNoteBook());
				return response;
			}
			if (request.getParam(RequestParam.RECORD.toString()) != null) {
				dao.addNote((String) request.getParam(RequestParam.RECORD
						.toString()), (Date) request.getParam(RequestParam.DATE
						.toString()));
				response.setResponse(ResponseParam.LIST_OF_NOTES.toString(),
						dao.showNoteBook());
				return response;
			}
		} catch (DaoException e) {
			throw new CommandException("AddCommand Error", e);
		}

		return null;
	}

}
