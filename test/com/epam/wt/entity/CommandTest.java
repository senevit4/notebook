package com.epam.wt.entity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import com.epam.wt.command.CommandName;
import com.epam.wt.controller.Controller;
import com.epam.wt.controller.Request;
import com.epam.wt.controller.RequestParam;
import com.epam.wt.controller.Response;
import com.epam.wt.controller.ResponseParam;
import com.epam.wt.dao.DaoException;
import com.epam.wt.dao.impl.NoteBookFileImpl;
import com.epam.wt.dao.impl.utility.sax.NoteBookSaxParser;

public class CommandTest {
	@Test
	public void commandTest() throws ParseException, DaoException, ParserConfigurationException, SAXException, IOException {
//		Controller con = new Controller();
//		Request r = new Request();
//		Note note = new Note();
//		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
//		note.setDate(sdf.parse("31-08-1982 10:20:56"));
//		note.setNote("Meow");
//		r.setParam(RequestParam.NOTE.toString(), note);
//		con.doRequest(CommandName.ADD, r);
//		Response resp = new Response();
//		resp.setResponse(RequestParam.NOTE.toString(), note);
//		NoteBookFileImpl nfi=new NoteBookFileImpl();
//		nfi.addNote(note);
//		nfi.addNote(note);
//		nfi.addNote(note);
////		nfi.addTopicNote("noterecord", new Date(), "topic1");
//		//System.out.println(nfi.findNote(4));
//		//System.out.println(nfi.deleteNoteBook());
//		nfi.deleteNote(2);
//		Assert.assertEquals(
//				con.doRequest(CommandName.ADD, r)
//						.getParam(RequestParam.NOTE.toString()),
//				resp.getParam(ResponseParam.LIST_OF_NOTES.toString()));
		NoteBookSaxParser saxpn=new NoteBookSaxParser();
		saxpn.parse();
		System.out.println((saxpn.getListOfNotes()));

	}
}
