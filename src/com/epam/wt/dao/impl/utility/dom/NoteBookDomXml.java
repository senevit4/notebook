package com.epam.wt.dao.impl.utility.dom;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.epam.wt.dao.DaoException;
import com.epam.wt.entity.Note;
import com.epam.wt.entity.NoteBookComparator;
import com.epam.wt.entity.TopicNote;

public class NoteBookDomXml {
	private DocumentBuilderFactory factory = DocumentBuilderFactory
			.newInstance();
	private DocumentBuilder builder;
	private Document document;
	private File file;
	{
		file = new File("notebookdom.xml");
		try {
			builder = factory.newDocumentBuilder();
			if (file.length() == 0) {
				document = builder.newDocument();
				Element rootElement = document.createElement("notebook");
				document.appendChild(rootElement);
				transform();
			}
		} catch (ParserConfigurationException e) {
			// e.printStackTrace();
		}
	}

	private void transform() {
		try {
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new FileWriter(file));
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Element writeNote(Note note) {
		Element noteElement = document.createElement("note");

		Element dateElement = document.createElement("date");
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		dateElement.setTextContent(sdf.format(note.getDate()));

		noteElement.appendChild(dateElement);

		Element recordElement = document.createElement("record");
		recordElement.setTextContent(note.getNote());

		noteElement.appendChild(recordElement);
		return noteElement;
	}

	private Element writeTopicNote(Note note) {
		Element noteElement = document.createElement("topicNote");

		Element dateElement = document.createElement("date");
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		dateElement.setTextContent(sdf.format(note.getDate()));

		noteElement.appendChild(dateElement);

		Element recordElement = document.createElement("record");
		recordElement.setTextContent(note.getNote());

		noteElement.appendChild(recordElement);

		Element topicElement = document.createElement("topic");
		topicElement.setTextContent(((TopicNote) note).getTopic());

		noteElement.appendChild(topicElement);
		return noteElement;
	}

	private Note read(Node node) throws DaoException {
		String type = node.getNodeName();
		switch (type) {
		case "note":
			return readNote(node);
		case "topicNote":
			return readTopicNote(node);
		}
		return null;
	}

	private Note readNote(Node node) throws DaoException {
		Note note = new Note();
		NodeList childNodes = node.getChildNodes();
		for (int j = 0; j < childNodes.getLength(); j++) {
			Node cNode = childNodes.item(j);
			if (cNode instanceof Element) {
				String field = cNode.getNodeName();
				String string = cNode.getLastChild().getTextContent().trim();
				switch (field) {
				case "date":
					SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
					try {
						note.setDate(sdf.parse(string));
					} catch (ParseException e) {
						throw new DaoException("parse error", e);
					}
					break;
				case "record":
					note.setNote(string);
					break;
				}
			}
		}
		return note;
	}

	private Note readTopicNote(Node node) throws DaoException {
		Note note = new TopicNote();
		NodeList childNodes = node.getChildNodes();
		for (int j = 0; j < childNodes.getLength(); j++) {
			Node cNode = childNodes.item(j);
			if (cNode instanceof Element) {
				String field = cNode.getNodeName();
				String string = cNode.getLastChild().getTextContent().trim();
				switch (field) {
				case "date":
					SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
					try {
						note.setDate(sdf.parse(string));
					} catch (ParseException e) {
						throw new DaoException("parse error", e);
					}
					break;
				case "record":
					note.setNote(string);
					break;
				case "topic":
					((TopicNote) note).setTopic(string);
					break;
				}
			}
		}
		return note;
	}

	public void addNote(Note note) throws DaoException {
		try {
			document = builder.parse(new FileInputStream(file));
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		Element rootElement = document.getDocumentElement();
		rootElement.appendChild(writeNote(note));
		transform();
	}

	public void addNote(String record, Date date) throws DaoException {
		try {
			document = builder.parse(new FileInputStream(file));
		} catch (Exception e) {
			throw new DaoException("", e);
		}
		Note note = new Note();
		note.setNote(record);
		note.setDate(date);
		Element rootElement = document.getDocumentElement();
		rootElement.appendChild(writeNote(note));
		transform();

	}

	public void addTopicNote(String record, Date date, String topic)
			throws DaoException {
		try {
			document = builder.parse(new FileInputStream(file));
		} catch (Exception e) {
			throw new DaoException("", e);
		}
		TopicNote note = new TopicNote();
		note.setNote(record);
		note.setDate(date);
		note.setTopic(topic);
		Element rootElement = document.getDocumentElement();
		rootElement.appendChild(writeTopicNote(note));
		transform();

	}

	public Note findNote(int id) throws DaoException {
		try {
			document = builder.parse(new FileInputStream(file));
		} catch (Exception e) {
			throw new DaoException("", e);
		}
		NodeList nodeList = document.getDocumentElement().getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node instanceof Element) {
				if (id == i) {
					return read(node);
				}
			}
		}
		return null;
	}

	public void deleteNote(int id) throws DaoException {
		try {
			document = builder.parse(new FileInputStream(file));
		} catch (Exception e) {
			throw new DaoException("", e);
		}
		NodeList nodeList = document.getDocumentElement().getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node instanceof Element) {
				if (id == i) {
					node.getParentNode().removeChild(node);
				}
			}
		}
		transform();

	}

	public int deleteNoteBook() throws DaoException {
		try {
			document = builder.parse(new FileInputStream(file));
		} catch (Exception e) {
			throw new DaoException("", e);
		}
		NodeList nodeList = document.getDocumentElement().getChildNodes();
		int res = nodeList.getLength();
		while (nodeList.getLength() > 0) {
			Node node = nodeList.item(0);
			node.getParentNode().removeChild(node);
		}
		transform();
		return res;
	}

	public void sortNoteBook() throws DaoException {
		ArrayList<Note> nb = this.showNoteBook();
		NoteBookComparator comparator = new NoteBookComparator();
		nb.sort(comparator);
		this.deleteNoteBook();
		transform();
		addListOfNotes(nb);
		transform();

	}

	private void addListOfNotes(ArrayList<Note> listOfNotes)
			throws DaoException {
		try {
			document = builder.parse(new FileInputStream(file));
		} catch (Exception e) {
			throw new DaoException("", e);
		}
		Element rootElement = document.getDocumentElement();
		for (Note note : listOfNotes) {
			String type = note.getClass().getSimpleName();
			Element noteElement = null;
			switch (type) {
			case "Note":
				noteElement = writeNote(note);
				break;
			case "TopicNote":
				noteElement = writeTopicNote((TopicNote) note);
				break;
			}
			rootElement.appendChild(noteElement);
		}
		transform();
	}

	public ArrayList<Note> showNoteBook() throws DaoException {
		try {
			document = builder.parse(new FileInputStream(file));
		} catch (Exception e) {
			throw new DaoException("", e);
		}
		Note note;
		ArrayList<Note> noteBook = new ArrayList<Note>();
		NodeList nodeList = document.getDocumentElement().getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node instanceof Element) {
				note = read(node);
				noteBook.add(note);
			}
		}
		return noteBook;
	}

}
