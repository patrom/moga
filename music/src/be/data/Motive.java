package be.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class Motive extends MusicalStructure{

	public Motive(List<NotePos> notePositions, int length) {
		super();
		setNotePositions(notePositions, length);
	}
	
	public Motive() {
		notePositions = new ArrayList<NotePos>();
	}

	private List<NotePos> notePositions;
	
	public List<NotePos> getNotePositions() {
		return notePositions;
	}
	
	public List<NotePos> getNotePositionsWithoutRests() {
		List<NotePos> positions = new ArrayList<NotePos>();
		for (NotePos note : notePositions) {
			if (!note.isRest()) {
				positions.add(note);
			}
		}
//		List<NotePos> posArray = new NotePos[positions.size()];
//		posArray = positions.toArray(posArray);
		return positions;
	}
	
	public void setNotePositions(List<NotePos> notePositions, int length) {
		if (notePositions.size() > 0) {
			setFirstNote(notePositions.get(0));
			setLastNote(notePositions.get(notePositions.size() - 1));
			setLength(length);
			setPosition(firstNote.getPosition());
			this.notePositions = new ArrayList<NotePos>(notePositions);//clone list!
		}
	}
	
	@Override
	public void transpose(int steps, int[] scale) {
		int size = notePositions.size();
		for (int i = 0; i < size; i++) {
			int pitch = notePositions.get(i).getPitch();
			int pitchClass = notePositions.get(i).getPitchClass();
			int pc = Scale.transpose(pitchClass, steps, scale);
			if (pc < pitchClass) {
				pc += 12;
			}
			notePositions.get(i).setPitch(pitch - pitchClass + pc);
		}
	}

	@Override
	public List<MusicalStructure> getMotives() {
		List<MusicalStructure> list = new ArrayList<MusicalStructure>();
		list.add(this);
		return list;
	}


	public void addNote(NotePos notePos) {
//		int index = findInsertIndex(notePos);
//		notePositions.add(index, notePos);
		notePositions.add(notePos);
		Collections.sort(notePositions);
	}

	private int findInsertIndex(NotePos notePos) {
		int size = notePositions.size();
		int i = 0;
		if (size == 0) {
			return 0; //first element
		} else {
			for (; i < size; i++) {
				if (notePositions.get(i).getPosition() < notePos.getPosition() && i < size - 1) {
					continue;
				}else{
					return size;//last
				}
			}
		}
		return i;
	}	
}
