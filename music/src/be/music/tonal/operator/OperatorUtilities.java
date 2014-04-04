package be.music.tonal.operator;

import java.util.List;

import be.data.MusicalStructure;
import be.data.NotePos;
import be.instrument.Instrument;

public class OperatorUtilities {

	private static final int UPPER_LIMIT_PITCH = 84;
	private static final int LOWER_LIMIT_PITCH = 40;

	public static boolean checkHigherCrossing(NotePos note, int newChordNote, MusicalStructure higherStructure) {
		List<NotePos> higherPositions = higherStructure.getNotePositions();
		for (NotePos notePos : higherPositions) {
			int totalLength = note.getPosition() + note.getLength();
			if(notePos.getPosition() >= note.getPosition()&& notePos.getPosition() < totalLength ){
				if (notePos.getPitch() < newChordNote) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean checkLowerCrossing(NotePos note, int newChordNote, MusicalStructure lowerStructure) {
		List<NotePos> lowerPositions = lowerStructure.getNotePositions();
		for (NotePos notePos : lowerPositions) {
			int totalLength = note.getPosition() + note.getLength();
			if(notePos.getPosition() >= note.getPosition()&& notePos.getPosition() < totalLength ){
				if (notePos.getPitch() > newChordNote) {
					return true;
				}
			}
		}
		return false;
	} 

	public static  boolean checkVoiceRange(int newChordNote, int voice, List<Instrument> ranges) {
		if (newChordNote > UPPER_LIMIT_PITCH || newChordNote < LOWER_LIMIT_PITCH) {
			return false;
		}
		Instrument range = ranges.get(voice);
		if (range.getVoice() == voice && (range.getLowest() > newChordNote || range.getHighest() < newChordNote)) {
			return false;
		}
		return true;
	}

}
