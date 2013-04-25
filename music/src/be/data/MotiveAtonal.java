package be.data;

import java.util.ArrayList;
import java.util.List;

public class MotiveAtonal {
	
	private List<NotePos> notes = new ArrayList<NotePos>();
	private int length;
	private int position;
	private int voice;
	
	public MotiveAtonal(List<NotePos> notes, int length, int position) {
		super();
		this.notes = notes;
		this.length = length;
		this.position = position;
	}

	public int getVoice() {
		return voice;
	}

	public void setVoice(int voice) {
		this.voice = voice;
	}

	public int getLength() {
		return length;
	}

	public List<NotePos> getNotes() {
		return notes;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	

}
