package be.core;

import java.util.ArrayList;
import java.util.List;

import be.data.InstrumentRange;
import be.data.MelodicSentence;
import be.data.Motive;
import be.data.MusicalStructure;
import be.data.NotePos;
import be.data.Scale;
import be.moga.MusicProperties;
import be.util.Populator;
import be.util.Utilities;

public class Symmetry {

	public static void main(String[] args) {
		List<Integer> pitchClasses = new ArrayList<Integer>();
		pitchClasses.add(0);
		pitchClasses.add(4);
		pitchClasses.add(5);
		pitchClasses.add(11);
		System.out.println(inversionalAxis(pitchClasses, 2));
		System.out.println(inversionalAxis(pitchClasses, 1 , 2));
		ArrayList<InstrumentRange> ranges = new ArrayList<InstrumentRange>();
		ranges.add(Utilities.getInstrument(2, 60, 74));
		ranges.add(Utilities.getInstrument(3, 65, 80));
		List<Motive> motives = Populator.getInstance().generateChordsWithoutRhythm(8, Scale.MAJOR_SCALE, ranges, 8);
		int i = ranges.size();
		for (Motive motive : motives) {
			List<NotePos> notes = motive.getNotePositions();
			List<NotePos> inverseList = inversionalAxisNotes(notes, 0);
			for (NotePos notePos : inverseList) {
				notePos.setVoice(i);
				//set range
			}
			System.out.println(notes);
			System.out.println(inverseList);
			i++;
		}
	}
	
	
	public static List<Integer> inversionalAxis(List<Integer> pitchClasses, Integer...axis) {
		List<Integer> inverseSymmetries = new ArrayList<Integer>();
		if (axis.length == 2) {
			for (Integer pc : pitchClasses) {
				inverseSymmetries.add(((axis[1] + 12) + (axis[0] - pc)) % 12);
			}
		} else {
			for (Integer pc : pitchClasses) {
				inverseSymmetries.add(((axis[0] + 12) + (axis[0] - pc)) % 12);
			}
		}
		return inverseSymmetries;
	}
	
	public static List<NotePos> inversionalAxisNotes(List<NotePos> notes, Integer...axis) {
		List<NotePos> inverseSymmetries = new ArrayList<NotePos>();
		if (axis.length == 2) {
			for (NotePos note : notes) {
				int pc = ((axis[1] + 12) + (axis[0] - note.getPitchClass())) % 12;
				addToList(inverseSymmetries, note, pc);
			}
		} else {
			for (NotePos note : notes) {
				int pc = ((axis[0] + 12) + (axis[0] - note.getPitchClass())) % 12;
				addToList(inverseSymmetries, note, pc);
			}
		}
		return inverseSymmetries;
	}


	private static void addToList(List<NotePos> inverseSymmetries, NotePos note, int pc) {
		NotePos clone = null;
		try {
			clone = (NotePos) note.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int pitch = (note.getOctave() * 12) + pc; 
		clone.setPitch(pitch);
		inverseSymmetries.add(clone);
	}

}
