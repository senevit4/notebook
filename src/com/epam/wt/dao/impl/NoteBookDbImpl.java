package com.epam.wt.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.epam.wt.dao.DaoException;
import com.epam.wt.dao.NoteBookDao;
import com.epam.wt.entity.Note;
import com.epam.wt.entity.NoteBook;
import com.epam.wt.entity.NoteBookComparator;
import com.epam.wt.entity.TopicNote;

public class NoteBookDbImpl implements NoteBookDao {

	static final String JDBC_DRIVER = "org.sqlite.JDBC";
	static final String DB_LOCATION = "jdbc:sqlite:notebook.db";
	private static Connection con = null;
	private static Statement st = null;

	private void setupConnection() throws DaoException {
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_LOCATION);
		} catch (SQLException | ClassNotFoundException e) {
			throw new DaoException("SQLException | ClassNotFoundException", e);
		}

	}

	private void closeConnection() throws DaoException {
		try {
			Class.forName(JDBC_DRIVER);
			con.close();
		} catch (SQLException | ClassNotFoundException e) {
			throw new DaoException("SQLException | ClassNotFoundException", e);
		}

	}

	@Override
	public void addNote(Note note) throws DaoException {
		try {
			setupConnection();
			st = con.createStatement();
			String sql = "INSERT INTO notebook (note) VALUES('"
					+ note.toString() + "')";
			st.executeUpdate(sql);
			closeConnection();
		} catch (Exception e) {
			throw new DaoException("Add Error", e);
		}

	}

	@Override
	public void addNote(String record, Date date) throws DaoException {
		try {
			setupConnection();
			st = con.createStatement();
			Note note = new Note();
			note.setDate(date);
			note.setNote(record);
			String sql = "INSERT INTO notebook (note) VALUES('"
					+ note.toString() + "')";
			st.executeUpdate(sql);
		} catch (Exception e) {
			throw new DaoException("Add Error", e);
		} finally {
			closeConnection();
		}

	}

	@Override
	public void addTopicNote(String record, Date date, String topic)
			throws DaoException {
		try {
			setupConnection();
			st = con.createStatement();
			TopicNote note = new TopicNote();
			note.setDate(date);
			note.setNote(record);
			note.setTopic(topic);
			String sql = "INSERT INTO notebook (note) VALUES('"
					+ note.toString() + "')";
			st.executeUpdate(sql);
		} catch (Exception e) {
			throw new DaoException("Add Error", e);
		} finally {
			closeConnection();
		}

	}

	@Override
	public Note findNote(int id) throws DaoException {
		try {
			setupConnection();
			st = con.createStatement();
			String sql = "SELECT * FROM notebook WHERE ROWID =" + (id + 1);

			return getNoteFromString(st.executeQuery(sql).getString(1));

		} catch (Exception e) {
			throw new DaoException("Find Error", e);
		} finally {
			closeConnection();
		}
	}

	@Override
	public void deleteNote(int id) throws DaoException {
		try {
			setupConnection();
			st = con.createStatement();
			String sql = "DELETE  FROM notebook WHERE ROWID =" + (id + 1);
			st.executeUpdate(sql);
		} catch (Exception e) {
			throw new DaoException("Delete Error", e);
		} finally {
			closeConnection();
		}

	}

	@Override
	public int deleteNoteBook() throws DaoException {
		try {
			setupConnection();
			st = con.createStatement();
			return st.executeUpdate("DELETE FROM notebook");

		} catch (Exception e) {
			throw new DaoException("DeleteAll Error", e);
		} finally {
			closeConnection();
		}
	}

	@Override
	public void sortNoteBook() throws DaoException {
		try {
			NoteBook nb = new NoteBook();
			setupConnection();
			st = con.createStatement();
			String sql = "SELECT * FROM notebook";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				nb.add(getNoteFromString(rs.getString(1)));
			}
			NoteBookComparator comparator = new NoteBookComparator();
			Collections.sort(nb.getNoteBook(), comparator);
			st.executeUpdate("DELETE FROM notebook");
			for (int i = 0; i < nb.getNoteBook().size(); i++) {
				st.executeUpdate("INSERT INTO notebook (note) VALUES('"
						+ nb.getNote(i).toString() + "')");
			}
		} catch (Exception e) {
			throw new DaoException("Show Error", e);
		} finally {
			closeConnection();
		}

	}

	@Override
	public ArrayList<Note> showNoteBook() throws DaoException {
		try {
			NoteBook nb = new NoteBook();
			setupConnection();
			st = con.createStatement();
			String sql = "SELECT * FROM notebook";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				nb.add(getNoteFromString(rs.getString(1)));
			}
			return nb.getNoteBook();
		} catch (Exception e) {
			throw new DaoException("Show Error", e);
		} finally {
			closeConnection();
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
