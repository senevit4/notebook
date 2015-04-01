package com.epam.wt.dao;

import java.util.ArrayList;
import java.util.Date;

import com.epam.wt.entity.Note;

public interface NoteBookDao {

	void addNote(Note note) throws DaoException;

	void addNote(String record, Date date) throws DaoException;

	void addTopicNote(String record, Date date, String topic) throws DaoException;

	Note findNote(int id) throws DaoException;

	void deleteNote(int id) throws DaoException;

	int deleteNoteBook() throws DaoException;

	void sortNoteBook() throws DaoException;

	ArrayList<Note> showNoteBook() throws DaoException;

}
