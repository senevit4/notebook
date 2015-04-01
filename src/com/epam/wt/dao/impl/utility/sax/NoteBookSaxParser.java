package com.epam.wt.dao.impl.utility.sax;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.epam.wt.entity.Note;

public class NoteBookSaxParser {
	private SAXParserFactory factory;
	private SAXParser parser;
	private NoteBookSaxHandler saxp;

	public void parse() throws ParserConfigurationException, SAXException,
			IOException {
		factory = SAXParserFactory.newInstance();
		parser = factory.newSAXParser();
		saxp = new NoteBookSaxHandler();
		parser.parse(new File("notebooksax.xml"), saxp);
	}

	public ArrayList<Note> getListOfNotes() {
		return saxp.getListOfNotes();
	}

}
