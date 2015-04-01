package com.epam.wt.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.epam.wt.dao.DaoException;
import com.epam.wt.dao.NoteBookAdapter;
import com.epam.wt.dao.NoteBookDao;
import com.epam.wt.entity.Note;
import com.epam.wt.entity.NoteBook;
import com.epam.wt.entity.NoteBookComparator;
import com.epam.wt.entity.TopicNote;

public final class NoteBookMemoryImpl implements NoteBookDao {
	NoteBook noteBook = NoteBookAdapter.getInstance().getNoteBook();

	public void addNote(String name, Date date) throws DaoException {
		try {
			Note note = new Note();
			note.setDate(date);
			note.setNote(name);
			noteBook.add(note);
		} catch (Exception e) {
			throw new DaoException("Add Error", e);
		}
	}

	public void addNote(Note note) throws DaoException {
		try {
			noteBook.add(note);
		} catch (Exception e) {
			throw new DaoException("Add Error", e);
		}
	}

	public Note findNote(int id) throws DaoException {
		try {
			return noteBook.getNoteBook().get(id);
		} catch (Exception e) {
			throw new DaoException("Find error", e);
		}
	}

	@Override
	public void deleteNote(int id) throws DaoException {
		try {
			noteBook.deleteNote(id);
		} catch (Exception e) {
			throw new DaoException("Delete Error", e);
		}
	}

	@Override
	public void addTopicNote(String record, Date date, String topic)
			throws DaoException {
		try {
			TopicNote note = new TopicNote();
			note.setDate(date);
			note.setNote(record);
			note.setTopic(topic);
			noteBook.add(note);
		} catch (Exception e) {
			throw new DaoException("AddTopicNote Error", e);
		}

	}

	@Override
	public int deleteNoteBook() throws DaoException {
		try {

			return noteBook.deleteNotebook();
		} catch (Exception e) {
			throw new DaoException("DeleteAll Error", e);
		}

	}

	@Override
	public ArrayList<Note> showNoteBook() throws DaoException {
		try {
			return noteBook.getNoteBook();
		} catch (Exception e) {
			throw new DaoException("Show Error", e);
		}

	}

	@Override
	public void sortNoteBook() throws DaoException {
		try {
			NoteBookComparator comparator = new NoteBookComparator();
			Collections.sort(noteBook.getNoteBook(), comparator);
		} catch (Exception e) {
			throw new DaoException("Sort Error", e);
		}
	}

}
