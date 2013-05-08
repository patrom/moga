package be.core;

import java.util.ArrayList;
import java.util.List;

import be.data.NotePos;

public class Symmetry {

	public static void main(String[] args) {
		List<Integer> pitchClasses = new ArrayList<Integer>();
		pitchClasses.add(0);
		pitchClasses.add(4);
		pitchClasses.add(5);
		pitchClasses.add(11);
		System.out.println(inversionalSymmetry(pitchClasses, 2));
		System.out.println(inversionalSymmetry(pitchClasses, 1 , 2));
	}
	
	
	public static List<Integer> inversionalSymmetry(List<Integer> pitchClasses, Integer...axis) {
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
	
	public static List<NotePos> inversionalSymmetryNotes(List<NotePos> notes, Integer...axis) {
		List<NotePos> inverseSymmetries = new ArrayList<NotePos>();
		if (axis.length == 2) {
			for (NotePos note : notes) {
				int pc = ((axis[1] + 12) + (axis[0] - note.getPitchClass())) % 12;
				//TODO inverseSymmetries.add(note);
			}
		} else {
			for (NotePos note : notes) {
				int pc = ((axis[0] + 12) + (axis[0] - note.getPitchClass())) % 12;
			}
		}
		return inverseSymmetries;
	}

}
