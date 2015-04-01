package com.epam.wt.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Note {
	private Date date;
	private String note;

	public Note() {
	}

	public Note(Date date, String note) {
		this.date = date;
		this.note = note;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public int hashCode() {

		return 7 * date.hashCode() + 65 * note.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Note x = (Note) obj;
		return x.date.equals(date) && x.note.equals(note);
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy");
		return "Note[date=" + sdf.format(date) + ", note=" + note + "]";
	}

}
