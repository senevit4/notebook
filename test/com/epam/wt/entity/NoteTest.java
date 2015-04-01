package com.epam.wt.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.testng.Assert;
import org.testng.annotations.Test;

public class NoteTest {
	@Test
	public void simpleNoteTest() throws ParseException {
		Note note = new Note();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		note.setDate(sdf.parse("31-08-1982 10:20:56"));
		note.setNote("Meow");
		Assert.assertEquals(note.getNote(), "Meow");
		Assert.assertEquals(note.getDate().toString(),
				"Tue Aug 31 10:20:56 BRT 1982");
	}

	@Test
	public void equalityTest() throws ParseException {
		Note note1 = new Note();
		Note note2 = new Note();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		note1.setDate(sdf.parse("31-08-1982 10:20:56"));
		note1.setNote("Meow");
		note2.setDate(sdf.parse("31-08-1982 10:20:56"));
		note2.setNote("Meow");

	}
}
