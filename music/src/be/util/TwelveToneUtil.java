package be.util;

import java.util.Collections;
import java.util.List;

import be.data.Motive;
import be.data.NotePos;
import be.instrument.Instrument;

public class TwelveToneUtil {

	public static void transpose(int value, List<Motive> motives, List<Instrument> ranges) {
		int size = motives.size() - 1;
		for (int i = size; i > - 1; i--) {
			Motive motive = motives.get(i);
			Instrument range = ranges.get(i);
			List<NotePos> notes = motive.getNotePositions();
			for (NotePos notePos : notes) {
				int newPc = (notePos.getPitchClass() + value) % 12;	
				checkRange(range, notePos, newPc);				
			}
		}
	}

	public static void invert(List<Motive> motives, List<Instrument> ranges) {
		int size = motives.size() - 1;
		for (int i = size; i > - 1; i--) {
			Motive motive = motives.get(i);
			Instrument range = ranges.get(i);
			List<NotePos> notes = motive.getNotePositions();
			for (NotePos notePos : notes) {
				int newPc = (-notePos.getPitchClass()) % 12;// - n	
				checkRange(range, notePos, newPc);
			}
		}
	}

	private static void checkRange(Instrument range, NotePos notePos, int newPc) {
		int lowPitch = range.getLowest();
		int newPitch = notePos.getPitch() + (newPc - notePos.getPitchClass());
		if (newPitch < lowPitch) {
			while (newPitch < lowPitch) {
				newPitch = newPitch + 12;
				notePos.setPitch(newPitch);
			}		
		} else {
			notePos.setPitch(newPitch);
		}
	}

	public static void multiply(int value, List<Motive> motives, List<Instrument> ranges) {
		int size = motives.size() - 1;
		for (int i = size; i > - 1; i--) {
			Motive motive = motives.get(i);
			Instrument range = ranges.get(i);
			List<NotePos> notes = motive.getNotePositions();
			for (NotePos notePos : notes) {
				int newPc = (notePos.getPitchClass() * value) % 12;			
				checkRange(range, notePos, newPc);
			}
		}
	}

	public static void reorderMotives(List<Motive> motives, List<Instrument> ranges) {
		Collections.shuffle(motives);
		int size = motives.size() - 1;
		for (int i = size; i > - 1; i--) {
			Motive motive = motives.get(i);
			motive.setVoice(i);
			List<NotePos> notes = motive.getNotePositions();
			Instrument range = ranges.get(i);
			int octave = range.getLowest() / 12;
			for (NotePos notePos : notes) {
				notePos.setVoice(i);
				int pitch = notePos.getPitchClass() + (octave * 12);
				if (pitch < range.getLowest()) {
					notePos.setPitch(pitch + 12);
				} else {
					notePos.setPitch(pitch);
				}
			}
		}
	}
	
	public static void retrogradePitches(List<Motive> motives){
		for (Motive motive : motives) {
			List<NotePos> notes = motive.getNotePositions();
			int size = notes.size();
			int[] pitches = new int[size];		
			for (int i = 0; i < size; i++) {
				pitches[i] = notes.get(i).getPitch();			
			}
			for (int i = 0; i < size; i++) {
				notes.get(i).setPitch(pitches[(size - 1) - i]);
			}
		}
	}


}
