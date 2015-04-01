package com.epam.wt.entity;

import java.util.ArrayList;

public final class NoteBook {
	private ArrayList<Note> notebook = new ArrayList<Note>();

	public ArrayList<Note> getNoteBook() {
		return notebook;
	}

	public void setNoteBook(ArrayList<Note> notes) {
		this.notebook = notes;
	}

	public int deleteNotebook() {
		int tmp=notebook.size();
		notebook.clear();
		return tmp;
	}

	public void add(Note note) {
		notebook.add(note);
	}

	public Note getNote(int index) {
		return notebook.get(index);
	}

	public void deleteNote(int index) {
		notebook.remove(index);
	}

}
