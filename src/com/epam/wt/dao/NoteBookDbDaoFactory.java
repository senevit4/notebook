package com.epam.wt.dao;

import com.epam.wt.dao.impl.NoteBookDbImpl;

public class NoteBookDbDaoFactory extends NoteBookDaoFactory {
	private NoteBookDbImpl instance = new NoteBookDbImpl();

	public NoteBookDao getNoteBookDao() {
		return instance;

	}

}
