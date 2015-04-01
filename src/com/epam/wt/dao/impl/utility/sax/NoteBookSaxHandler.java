package com.epam.wt.dao.impl.utility.sax;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.epam.wt.entity.Note;
import com.epam.wt.entity.TopicNote;

public class NoteBookSaxHandler extends DefaultHandler{
	private ArrayList<Note> listOfNotes;
	private Note note;
	private String currentElement;

	public void startDocument() throws SAXException {
		listOfNotes = new ArrayList<Note>();
		System.out.println("parsing started");
	}

	public void endDocument() throws SAXException {
		System.out.println("parsing finished");
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		//System.out.println("start");
		currentElement = qName;
		switch (currentElement) {
		case "note":
			note = new Note();
			break;
		case "topicNote":
			note = new TopicNote();
			break;
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		//System.out.println("end");
		currentElement = "";
		switch (qName) {
		case "note":
		case "topicNote":
			listOfNotes.add(note);
			break;
		}
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		 currentElement = currentElement.toString();
		switch (currentElement) {
		case "date":
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			Date date = null;
			try {
				date = sdf.parse(new String(ch, start, length));
			} catch (ParseException e) {

			}
			note.setDate(date);
			break;
		case "record":
			note.setNote(new String(ch, start, length));
			break;
		case "topic":
			((TopicNote)note).setTopic(new String(ch, start, length));
			break;
		}
	}

	public ArrayList<Note> getListOfNotes() {
		return listOfNotes;
	}

}
