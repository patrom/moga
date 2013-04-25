package be.data;

import java.util.List;

public abstract class MusicalStructure {

	protected int length;
	protected int position;
	protected NotePos firstNote;
	protected NotePos lastNote;
	protected int highestRange;
	protected int lowestRange;
	protected int voice;
	protected String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVoice() {
		return voice;
	}

	public void setVoice(int voice) {
		this.voice = voice;
	}

	public int getHighestRange() {
		return highestRange;
	}

	public void setHighestRange(int highestRange) {
		this.highestRange = highestRange;
	}

	public int getLowestRange() {
		return lowestRange;
	}

	public void setLowestRange(int lowestRange) {
		this.lowestRange = lowestRange;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public NotePos getFirstNote() {
		return firstNote;
	}

	public void setFirstNote(NotePos firstNote) {
		this.firstNote = firstNote;
	}

	public NotePos getLastNote() {
		return lastNote;
	}

	public void setLastNote(NotePos lastNote) {
		this.lastNote = lastNote;
	}

	public abstract void transpose(int steps, int[] scale);

	public abstract List<NotePos> getNotePositions();
	
	public abstract void setNotePositions(List<NotePos> notePositions, int length);
	
	public abstract List<MusicalStructure> getMotives();


}
