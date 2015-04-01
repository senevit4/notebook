package com.epam.wt.dao;

import com.epam.wt.dao.impl.NoteBookFileImpl;

public class NoteBookFileDaoFactory extends NoteBookDaoFactory {
	private NoteBookFileImpl instance = new NoteBookFileImpl();
	
	public NoteBookDao getNoteBookDao(){
		return instance;
		
	}	

}
