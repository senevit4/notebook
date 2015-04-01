package com.epam.wt.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.epam.wt.logic.NoteBookLogic;

public class DaoTest {
	@Test
	public void daoTest() throws ParseException {
		Note note = new Note();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		note.setDate(sdf.parse("31-08-1982 10:20:56"));
		note.setNote("Meow");
		Note note1 = new Note();
		note1.setDate(sdf.parse("31-08-1982 10:20:56"));
		note1.setNote("wooff");
		NoteBookLogic nbl = new NoteBookLogic();
//		nbl.addNote(note);
//		nbl.showNoteBook();
		//nbl.addNoteFile(note1);//nbl.deleteNoteBook();
//		Assert.assertEquals(nbl.findNote(0), note);
	}
}
