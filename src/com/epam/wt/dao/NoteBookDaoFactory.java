package com.epam.wt.dao;

import java.util.ResourceBundle;

public abstract class NoteBookDaoFactory {
	private static DaoEnum type = DaoEnum.getType("memory");

	static {
		ResourceBundle bundle = ResourceBundle.getBundle("properties.DataDao");
		type = DaoEnum.getType((String) bundle.getObject("DataDao"));
	}

	public static NoteBookDao getDAO() {
		switch (type) {
		case USING_MEMORY:
			return new NoteBookMemoryDaoFactory().getNoteBookDao();
		case USING_FILE:
			return new NoteBookFileDaoFactory().getNoteBookDao();
		case USING_XML:
			return new NoteBookXmlDaoFactory().getNoteBookDao();
		case USING_DB:
			return new NoteBookDbDaoFactory().getNoteBookDao();
		default:
			break;
		}
		return null;
	}

}
