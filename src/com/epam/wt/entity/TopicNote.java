package com.epam.wt.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class TopicNote extends Note {
	private String topic;

	public TopicNote() {

	}

	public TopicNote(Date date, String note, String topic) {
		super(date,note);
		this.topic = topic;

	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Override
	public int hashCode() {
		return 2*topic.hashCode() + 18*getNote().hashCode() + 155*getDate().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		TopicNote x = (TopicNote) obj;
		return super.equals(obj) && x.topic.equals(topic);
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy");
		return "TopicNote[date=" + sdf.format(super.getDate()) + ", note=" + super.getNote() + ", topic="+topic+"]";
	}

}
