package be.util;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import be.data.InstrumentRange;
import be.data.Motive;
import be.data.NotePos;

public class TwelveToneUtil {

	public static void transpose(int value, List<Motive> motives, List<InstrumentRange> ranges) {
		int size = motives.size() - 1;
		for (int i = size; i > - 1; i--) {
			Motive motive = motives.get(i);
			InstrumentRange range = ranges.get(i);
			List<NotePos> notes = motive.getNotePositions();
			for (NotePos notePos : notes) {
				int newPc = (notePos.getPitchClass() + value) % 12;
		
				checkRange(range, notePos, newPc);
				
			}
		}
	}

	public static void invert(List<Motive> motives, List<InstrumentRange> ranges) {
		int size = motives.size() - 1;
		for (int i = size; i > - 1; i--) {
			Motive motive = motives.get(i);
			InstrumentRange range = ranges.get(i);
			List<NotePos> notes = motive.getNotePositions();
			for (NotePos notePos : notes) {
				int newPc = (-notePos.getPitchClass()) % 12;// - n
				
				checkRange(range, notePos, newPc);
			}
		}
	}

	private static void checkRange(InstrumentRange range, NotePos notePos, int newPc) {
		int lowPitch = range.getLowest();
		int newPitch = notePos.getPitch() + (newPc - notePos.getPitchClass());
		if (newPitch < lowPitch) {
			notePos.setPitch(newPitch + 12);
		} else {
			notePos.setPitch(newPitch);
		}
	}

	public static void multiply(int value, List<Motive> motives, List<InstrumentRange> ranges) {
		int size = motives.size() - 1;
		for (int i = size; i > - 1; i--) {
			Motive motive = motives.get(i);
			InstrumentRange range = ranges.get(i);
			List<NotePos> notes = motive.getNotePositions();
			for (NotePos notePos : notes) {
				int newPc = (notePos.getPitchClass() * value) % 12;
				
				checkRange(range, notePos, newPc);
			}
		}
	}

	public static void reorder(List<Motive> motives, List<InstrumentRange> ranges) {
		Collections.shuffle(motives);
		int size = motives.size() - 1;
		for (int i = size; i > - 1; i--) {
			Motive motive = motives.get(i);
			motive.setVoice(i);
			List<NotePos> notes = motive.getNotePositions();
			InstrumentRange range = ranges.get(i);
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


}
