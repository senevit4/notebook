package com.epam.wt.dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

import com.epam.wt.dao.DaoException;
import com.epam.wt.dao.NoteBookDao;
import com.epam.wt.entity.Note;
import com.epam.wt.entity.NoteBook;
import com.epam.wt.entity.NoteBookComparator;
import com.epam.wt.entity.TopicNote;

public final class NoteBookFileImpl implements NoteBookDao {
	public static final String FILENAME = "notebook.txt";

	// NoteBook noteBookFile = NoteBookAdapter.getInstance().getNoteBook();

	@Override
	public void addNote(Note note) throws DaoException {
		FileWriter writeFile = null;
		try {
			writeFile = new FileWriter(FILENAME, true);
			writeFile.write(note.toString() + "\n");
			writeFile.close();
		} catch (IOException e) {
			throw new DaoException("Add Error", e);
		}
	}

	@Override
	public void addNote(String record, Date date) throws DaoException {
		Note note = new Note();
		note.setDate(date);
		note.setNote(record);
		try {
			FileWriter writeFile = new FileWriter(FILENAME, true);
			writeFile.write(note.toString() + "\n");
			writeFile.close();
		} catch (IOException e) {
			throw new DaoException("Add Error", e);
		}

	}

	@Override
	public void addTopicNote(String record, Date date, String topic)
			throws DaoException {
		TopicNote note = new TopicNote();
		note.setDate(date);
		note.setNote(record);
		note.setTopic(topic);
		try {
			FileWriter writeFile = new FileWriter(FILENAME, true);
			writeFile.write(note.toString() + "\n");
			writeFile.close();
		} catch (IOException e) {
			throw new DaoException("AddTopicNote Error", e);
		}

	}

	@Override
	public Note findNote(int id) throws DaoException {
		Scanner in = null;
		try {
			in = new Scanner(new File(FILENAME));
			String tmp = null;
			;
			int i = 0;
			while (in.hasNextLine()) {
				if (id == i) {
					tmp = in.nextLine();
					break;
				}
				in.nextLine();
				i++;
			}
			in.close();
			return getNoteFromString(tmp);
		} catch (FileNotFoundException e) {

			throw new DaoException("Find Error", e);
		} finally {
			in.close();
		}
	}

	@Override
	public void deleteNote(int id) throws DaoException {
		NoteBook nb = new NoteBook();
		Scanner in;
		try {
			in = new Scanner(new File(FILENAME));
			while (in.hasNextLine()) {
				nb.add(getNoteFromString(in.nextLine()));
			}
			in.close();
		} catch (FileNotFoundException e) {
			throw new DaoException("Delete Error", e);
		}
		nb.deleteNote(id);
		try {
			FileWriter writeFile = new FileWriter(FILENAME);
			for (int i = 0; i < nb.getNoteBook().size(); i++) {
				writeFile.write(nb.getNote(i).toString() + "\n");
			}
			writeFile.close();
		} catch (IOException e) {
			throw new DaoException("Delete Error", e);
		}

	}

	@Override
	public int deleteNoteBook() throws DaoException {
		int i = 0;
		try {

			FileReader fileReader = new FileReader(FILENAME);
			BufferedReader br = new BufferedReader(fileReader);
			while (br.readLine() != null)
				i++;
			FileWriter writeFile = new FileWriter(FILENAME);
			writeFile.write("");
			writeFile.close();
			br.close();
		} catch (IOException e) {
			throw new DaoException("DeleteAll Error", e);
		}
		return i;
	}

	@Override
	public ArrayList<Note> showNoteBook() throws DaoException {
		NoteBook nb = new NoteBook();
		Scanner in = null;
		try {
			in = new Scanner(new File(FILENAME));
			while (in.hasNextLine()) {
				nb.add(getNoteFromString(in.nextLine()));
			}
			in.close();
		} catch (FileNotFoundException e) {
			throw new DaoException("Show Error", e);
		} finally {
			in.close();
		}

		return nb.getNoteBook();

	}

	@Override
	public void sortNoteBook() throws DaoException {
		NoteBook nb = new NoteBook();
		Scanner in;
		try {
			in = new Scanner(new File(FILENAME));
			while (in.hasNextLine()) {
				nb.add(getNoteFromString(in.nextLine()));
			}
			in.close();
		} catch (FileNotFoundException e) {
			throw new DaoException("Sort Error", e);
		}
		NoteBookComparator comparator = new NoteBookComparator();
		Collections.sort(nb.getNoteBook(), comparator);
		try {
			FileWriter writeFile = new FileWriter(FILENAME);
			for (int i = 0; i < nb.getNoteBook().size(); i++) {
				writeFile.write(nb.getNote(i).toString() + "\n");
			}
			writeFile.close();
		} catch (IOException e) {
			throw new DaoException("Sort Error", e);
		}

	}

	public static Note getNoteFromString(String str) throws DaoException {
		String date = str.substring(str.indexOf("date=") + 5, str.indexOf(","));
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		if (str.startsWith("TopicNote")) {
			String record = str.substring(str.indexOf("note=") + 5,
					str.indexOf(", topic="));
			String topic = str.substring(str.indexOf("topic=") + 6,
					str.indexOf("]"));
			try {
				return new TopicNote(sdf.parse(date), record, topic);
			} catch (ParseException e) {
				throw new DaoException("Parse Error", e);
			}
		}
		String record = str.substring(str.indexOf("note=") + 5,
				str.indexOf("]"));

		try {
			return new Note(sdf.parse(date), record);
		} catch (ParseException e) {
			throw new DaoException("Parse Error", e);
		}
	}

}
