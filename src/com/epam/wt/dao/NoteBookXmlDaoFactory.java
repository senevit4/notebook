package com.epam.wt.dao;

import com.epam.wt.dao.impl.NoteBookXmlImpl;

public class NoteBookXmlDaoFactory extends NoteBookDaoFactory{
private final NoteBookXmlImpl instance = new NoteBookXmlImpl();
	
	public NoteBookDao getNoteBookDao(){
		return instance;
		
	}

}
