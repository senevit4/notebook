package com.epam.wt.dao;

import com.epam.wt.entity.NoteBook;

public class NoteBookAdapter {

	private static final NoteBookAdapter instance = new NoteBookAdapter();

	private NoteBook noteBook = new NoteBook();

	public static NoteBookAdapter getInstance() {
		return instance;
	}

	public NoteBook getNoteBook() {
		return noteBook;
	}

}
