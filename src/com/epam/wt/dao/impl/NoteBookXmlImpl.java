package com.epam.wt.dao.impl;

import java.util.ArrayList;
import java.util.Date;

import com.epam.wt.dao.DaoException;
import com.epam.wt.dao.NoteBookDao;
import com.epam.wt.dao.impl.utility.dom.NoteBookDomXml;
import com.epam.wt.entity.Note;

public class NoteBookXmlImpl implements NoteBookDao {
    private NoteBookDomXml xml=new NoteBookDomXml();
	@Override
	public void addNote(Note note) throws DaoException {
		xml.addNote(note);
		
	}

	@Override
	public void addNote(String record, Date date) throws DaoException {
		xml.addNote(record, date);
		
	}

	@Override
	public void addTopicNote(String record, Date date, String topic)
			throws DaoException {
		xml.addTopicNote(record, date, topic);
		
	}

	@Override
	public Note findNote(int id) throws DaoException {
		return xml.findNote(id);
	}

	@Override
	public void deleteNote(int id) throws DaoException {
		xml.deleteNote(id);
		
	}

	@Override
	public int deleteNoteBook() throws DaoException {
		return xml.deleteNoteBook();
	}

	@Override
	public void sortNoteBook() throws DaoException {
		xml.sortNoteBook();
		
	}

	@Override
	public ArrayList<Note> showNoteBook() throws DaoException {
		return xml.showNoteBook();
	}

}
