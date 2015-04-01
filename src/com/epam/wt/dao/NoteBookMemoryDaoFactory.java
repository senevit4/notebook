package com.epam.wt.dao;

import com.epam.wt.dao.impl.NoteBookMemoryImpl;

public class NoteBookMemoryDaoFactory extends NoteBookDaoFactory {
private final NoteBookMemoryImpl instance = new NoteBookMemoryImpl();
	
	public NoteBookDao getNoteBookDao(){
		return instance;
		
	}

}
