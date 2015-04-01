package com.epam.wt.dao.impl.utility.stax;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.events.XMLEvent;

import com.epam.wt.dao.DaoException;
import com.epam.wt.entity.Note;
import com.epam.wt.entity.NoteBookComparator;
import com.epam.wt.entity.TopicNote;

public class NoteBookStaxXml {

	private XMLInputFactory inputFactory = XMLInputFactory.newInstance();
	private XMLEventReader eventReader;
	private XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
	private XMLEventWriter eventWriter;
	private XMLEventFactory eventFactory = XMLEventFactory.newInstance();
	private File readFile = new File("notebookstaxr.xml");
	private File writeFile = new File("notebookstaxw.xml");
	private XMLEvent end;

	{
		end = eventFactory.createDTD("\n");
		if (readFile.length() == 0) {
			try {
				createFile();
			} catch (DaoException e) {

			}
		}
	}

	private void createFile() throws DaoException {
		try {
			eventWriter = outputFactory.createXMLEventWriter(new FileWriter(
					readFile));
			eventWriter.add(eventFactory.createStartDocument());
			eventWriter.add(eventFactory.createStartElement("", null,
					"notebook"));
			eventWriter
					.add(eventFactory.createEndElement("", null, "notebook"));
			eventWriter.add(eventFactory.createEndDocument());
			eventWriter.flush();
			eventWriter.close();
		} catch (Exception e) {
			throw new DaoException("", e);
		}
	}

	private void rewrite() {
		try {
			readFile.delete();
			readFile.createNewFile();
			eventReader = inputFactory
					.createXMLEventReader(new FileInputStream(writeFile));
			eventWriter = outputFactory.createXMLEventWriter(new FileWriter(
					readFile));
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				eventWriter.add(event);
			}
			eventWriter.flush();
			eventWriter.close();
			writeFile.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addListOfNotes(ArrayList<Note> listOfNotes)
			throws DaoException {
		try {
			eventReader = inputFactory
					.createXMLEventReader(new FileInputStream(readFile));
			if (!writeFile.exists()) {
				writeFile.createNewFile();
			}
			eventWriter = outputFactory.createXMLEventWriter(new FileWriter(
					writeFile));
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();

				if (!(event.getEventType() == XMLEvent.END_ELEMENT && event
						.asEndElement().getName().toString().equals("notebook"))) {
					eventWriter.add(event);
				} else {
					for (Note note : listOfNotes) {
						String type = note.getClass().getSimpleName();
						switch (type) {
						case "Note":
							writeNote(note);
							break;
						case "TopicNote":
							writeTopicNote(note);
							break;
						}
					}
					eventWriter.add(event);
				}
				if (event.getEventType() == XMLEvent.END_DOCUMENT) {
					eventWriter.add(event);
				}
			}
			eventWriter.flush();
			eventWriter.close();
			rewrite();

		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		}
	}

	private void writeNote(Note note) throws DaoException {
		try {
			eventWriter.add(eventFactory.createStartElement("", null, "note"));
			eventWriter.add(end);
			eventWriter.add(eventFactory.createStartElement("", null, "date"));
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			eventWriter.add(eventFactory.createCharacters(sdf.format(note
					.getDate())));
			eventWriter.add(eventFactory.createEndElement("", null, "date"));
			eventWriter.add(end);
			eventWriter.add(eventFactory.createStartElement("", null, "record")
					.asStartElement());

			eventWriter.add(eventFactory.createCharacters(note.getNote()));
			eventWriter.add(eventFactory.createEndElement("", null, "record")
					.asEndElement());
			eventWriter.add(end);
			eventWriter.add(eventFactory.createEndElement("", null, "note"));
			eventWriter.add(end);
		} catch (Exception e) {
			throw new DaoException("", e);
		}
	}

	private void writeTopicNote(Note note) throws DaoException {
		try {
			eventWriter.add(eventFactory.createStartElement("", null,
					"topicNote"));
			eventWriter.add(end);
			eventWriter.add(eventFactory.createStartElement("", null, "date"));
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			eventWriter.add(eventFactory.createCharacters(sdf.format(note
					.getDate())));
			eventWriter.add(eventFactory.createEndElement("", null, "date"));
			eventWriter.add(end);
			eventWriter.add(eventFactory.createStartElement("", null, "record")
					.asStartElement());

			eventWriter.add(eventFactory.createCharacters(note.getNote()));
			eventWriter.add(eventFactory.createEndElement("", null, "record")
					.asEndElement());
			eventWriter.add(end);
			eventWriter.add(eventFactory.createStartElement("", null, "topic")
					.asStartElement());

			eventWriter.add(eventFactory.createCharacters(((TopicNote) note)
					.getTopic()));
			eventWriter.add(eventFactory.createEndElement("", null, "topic")
					.asEndElement());
			eventWriter.add(end);
			eventWriter.add(eventFactory
					.createEndElement("", null, "topicNote"));
			eventWriter.add(end);
		} catch (Exception e) {
			throw new DaoException("", e);
		}
	}

	public void addNote(String record, Date date) throws DaoException {
		Note note = new Note();
		note.setDate(date);
		note.setNote(record);
		try {
			eventReader = inputFactory
					.createXMLEventReader(new FileInputStream(readFile));
			eventWriter = outputFactory.createXMLEventWriter(new FileWriter(
					writeFile));
			while (eventReader.hasNext()) {
				XMLEvent event;
				event = eventReader.nextEvent();
				if (!(event.getEventType() == XMLEvent.END_ELEMENT && event
						.asEndElement().getName().toString().equals("notebook"))) {
					eventWriter.add(event);
				} else {
					writeNote(note);
					eventWriter.add(event);
				}

				if (event.getEventType() == XMLEvent.END_DOCUMENT) {
					eventWriter.add(event);
				}
			}
			eventWriter.flush();
			eventWriter.close();
			rewrite();
		} catch (Exception e) {
			throw new DaoException("", e);
		}
	}

	public void addTopicNote(String record, Date date, String topic)
			throws DaoException {
		TopicNote note = new TopicNote();
		note.setDate(date);
		note.setNote(record);
		note.setTopic(topic);
		try {
			eventReader = inputFactory
					.createXMLEventReader(new FileInputStream(readFile));
			eventWriter = outputFactory.createXMLEventWriter(new FileWriter(
					writeFile));
			while (eventReader.hasNext()) {
				XMLEvent event;
				event = eventReader.nextEvent();
				if (!(event.getEventType() == XMLEvent.END_ELEMENT && event
						.asEndElement().getName().toString().equals("notebook"))) {
					eventWriter.add(event);
				} else {
					writeNote(note);
					eventWriter.add(event);
				}

				if (event.getEventType() == XMLEvent.END_DOCUMENT) {
					eventWriter.add(event);
				}
			}
			eventWriter.flush();
			eventWriter.close();
			rewrite();
		} catch (Exception e) {
			throw new DaoException("", e);
		}
	}

	public void addNote(Note note) throws DaoException {
		try {
			eventReader = inputFactory
					.createXMLEventReader(new FileInputStream(readFile));
			eventWriter = outputFactory.createXMLEventWriter(new FileWriter(
					writeFile));
			while (eventReader.hasNext()) {
				XMLEvent event;
				event = eventReader.nextEvent();
				if (!(event.getEventType() == XMLEvent.END_ELEMENT && event
						.asEndElement().getName().toString().equals("notebook"))) {
					eventWriter.add(event);
				} else {
					writeNote(note);
					eventWriter.add(event);
				}

				if (event.getEventType() == XMLEvent.END_DOCUMENT) {
					eventWriter.add(event);
				}
			}
			eventWriter.flush();
			eventWriter.close();
			rewrite();
		} catch (Exception e) {
			throw new DaoException("", e);
		}
	}

	public ArrayList<Note> showNoteBook() throws DaoException {
		ArrayList<Note> nb = new ArrayList<Note>();
		try {
			eventReader = inputFactory
					.createXMLEventReader(new FileInputStream(readFile));
			while (eventReader.hasNext()) {
				XMLEvent event;
				event = eventReader.nextEvent();
				if (event.getEventType() == XMLEvent.START_ELEMENT) {
					String tagName = event.asStartElement().getName()
							.toString();
					switch (tagName) {
					case "note":
					case "noteWithTable":
					case "noteWithCalendar":
						nb.add(read(event));
						break;
					}
				}
			}
		} catch (Exception e) {
			throw new DaoException("", e);
		}
		return nb;
	}

	private Note read(XMLEvent event) throws DaoException {
		String tagName = event.asStartElement().getName().toString();
		switch (tagName) {
		case "note":
			return readNote(event);
		case "topicNote":
			return readTopicNote(event);
		}
		return null;
	}

	private Note readNote(XMLEvent event) throws DaoException {
		Note note = new Note();
		try {
			eventReader.nextEvent();
			XMLEvent dateStartEvent = eventReader.nextEvent();
			if (dateStartEvent.getEventType() == XMLEvent.START_ELEMENT) {
				if (dateStartEvent.asStartElement().getName().toString()
						.equals("date")) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
					note.setDate(sdf.parse(eventReader.nextEvent()
							.asCharacters().getData()));
				}
			}
			eventReader.nextEvent();
			eventReader.nextEvent();
			XMLEvent recordStartEvent = eventReader.nextEvent();
			if (recordStartEvent.getEventType() == XMLEvent.START_ELEMENT) {
				if (recordStartEvent.asStartElement().getName().toString()
						.equals("record")) {
					note.setNote(eventReader.nextEvent().asCharacters()
							.getData());
				}
			}
			eventReader.nextEvent();
		} catch (Exception e) {
			throw new DaoException("", e);
		}
		return note;
	}

	private Note readTopicNote(XMLEvent event) throws DaoException {
		Note note = new TopicNote();
		try {
			eventReader.nextEvent();
			XMLEvent dateStartEvent = eventReader.nextEvent();
			if (dateStartEvent.getEventType() == XMLEvent.START_ELEMENT) {
				if (dateStartEvent.asStartElement().getName().toString()
						.equals("date")) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
					note.setDate(sdf.parse(eventReader.nextEvent()
							.asCharacters().getData()));
				}
			}
			eventReader.nextEvent();
			eventReader.nextEvent();
			XMLEvent recordStartEvent = eventReader.nextEvent();
			if (recordStartEvent.getEventType() == XMLEvent.START_ELEMENT) {
				if (recordStartEvent.asStartElement().getName().toString()
						.equals("record")) {
					note.setNote(eventReader.nextEvent().asCharacters()
							.getData());
				}
			}
			eventReader.nextEvent();
			eventReader.nextEvent();
			XMLEvent topicStartEvent = eventReader.nextEvent();
			if (topicStartEvent.getEventType() == XMLEvent.START_ELEMENT) {
				if (topicStartEvent.asStartElement().getName().toString()
						.equals("topic")) {
					((TopicNote) note).setTopic(eventReader.nextEvent()
							.asCharacters().getData());
				}
			}
			eventReader.nextEvent();
		} catch (Exception e) {
			throw new DaoException("", e);
		}
		return note;
	}

	public Note findById(int id) throws DaoException {
		try {
			eventReader = inputFactory
					.createXMLEventReader(new FileInputStream(readFile));
			int i = -1;
			while (eventReader.hasNext()) {
				XMLEvent event;
				event = eventReader.nextEvent();
				i++;
				if (event.getEventType() == XMLEvent.START_ELEMENT) {
					String tagName = event.asStartElement().getName()
							.toString();
					switch (tagName) {
					case "note":
					case "topicNote":
						if (id == i) {
							return read(event);
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			throw new DaoException("", e);
		}
		return null;
	}

	public void deleteNote(int id) throws DaoException {
		try {
			eventReader = inputFactory
					.createXMLEventReader(new FileInputStream(readFile));
			if (!writeFile.exists()) {
				writeFile.createNewFile();
			}
			eventWriter = outputFactory.createXMLEventWriter(new FileWriter(
					writeFile));
			XMLEvent event;
			int i = -1;
			while (eventReader.hasNext()) {
				event = eventReader.nextEvent();
				i++;
				if (event.getEventType() == XMLEvent.START_ELEMENT) {
					String type = event.asStartElement().getName().toString();
					switch (type) {
					case "note":
					case "topicNote":
						if (id == i) {
							while (!(event.getEventType() == XMLEvent.END_ELEMENT && event
									.asEndElement().getName().toString()
									.equals(type))) {
								event = eventReader.nextEvent();
							}
							event = eventReader.nextEvent();
						} else {
							eventWriter.add(event);
						}

						break;
					default:
						eventWriter.add(event);
						break;
					}

				} else {
					eventWriter.add(event);
				}

			}
			eventWriter.flush();
			eventWriter.close();
			rewrite();

		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		}
	}

	private int numberOfNotes() throws DaoException {
		int number = 0;
		try {
			eventReader = inputFactory
					.createXMLEventReader(new FileInputStream(readFile));
			while (eventReader.hasNext()) {
				XMLEvent event;
				event = eventReader.nextEvent();
				if (event.getEventType() == XMLEvent.START_ELEMENT) {
					String tagName = event.asStartElement().getName()
							.toString();
					switch (tagName) {
					case "note":
					case "topicNote":
						number++;
						break;
					}
				}
			}
		} catch (Exception e) {
			throw new DaoException("", e);
		}
		return number;
	}

	public int deleteNoteBook() throws DaoException {
		int number = numberOfNotes();
		try {
			eventWriter = outputFactory.createXMLEventWriter(new FileWriter(
					readFile));
			eventWriter.add(eventFactory.createStartDocument());
			eventWriter.add(eventFactory.createStartElement("", null,
					"notebook"));
			eventWriter
					.add(eventFactory.createEndElement("", null, "notebook"));
			eventWriter.add(eventFactory.createEndDocument());
			eventWriter.flush();
			eventWriter.close();
			rewrite();
		} catch (Exception e) {
			throw new DaoException("", e);
		}
		return number;
	}

	public void sortNoteBook() throws DaoException {
		ArrayList<Note> nb = this.showNoteBook();
		NoteBookComparator comparator = new NoteBookComparator();
		nb.sort(comparator);
		this.deleteNoteBook();
		addListOfNotes(nb);
	}

}
