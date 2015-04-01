package com.epam.wt.entity;

import java.util.Comparator;

public final class NoteBookComparator implements Comparator<Note> {

	@Override
	public int compare(Note note1, Note note2) {
		if (note1.getDate().compareTo(note2.getDate()) == 0) {
			return 0;
		}
		if (note1.getDate().compareTo(note2.getDate()) > 0) {
			return 1;
		}
		if (note1.getDate().compareTo(note2.getDate()) < 0) {
			return -1;
		}
		return 0;
	}

}
