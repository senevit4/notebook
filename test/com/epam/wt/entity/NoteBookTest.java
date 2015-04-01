package com.epam.wt.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

public class NoteBookTest {
	@Test
	public void simpleNoteBookTest() throws ParseException {
		Note note = new Note();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		note.setDate(sdf.parse("31-08-1982 10:20:56"));
		note.setNote("Meow");
		NoteBook nb = new NoteBook();
		nb.add(note);
		List<Note> testnotes = new ArrayList<Note>();
		testnotes.add(note);
		Assert.assertEquals(nb.getNoteBook(), testnotes);
	}

}
