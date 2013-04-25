package be.util;

import java.util.ArrayList;
import java.util.List;

import be.data.MelodicSentence;
import be.data.MusicalStructure;
import be.data.NotePos;
import be.data.Scale;

public class FugaUtilities {

	
	public static List<MusicalStructure> addTransposedVoices(List<MusicalStructure> sentences, int[] scale, int transposition, int incrementPosition, int length) {
		int size = sentences.size();
		int voice = size;
		List<MusicalStructure> structures = new ArrayList<MusicalStructure>();
		for (int j = 0; j < size; j++) {
			List<NotePos> notePositions = sentences.get(j).getNotePositions();
			List<NotePos> newNotePositions = new ArrayList<NotePos>();
			for (int i = 0; i < notePositions.size(); i++) {
				NotePos notePos = notePositions.get(i);
				int pitch = notePos.getPitch();
				int pitchClass = notePos.getPitchClass();
				int pc = Scale.transpose(pitchClass, transposition, scale);
				if (pc < pitchClass) {
					pc += 12;
				}
				if (transposition - scale.length > 0) {
					int octave = (transposition / scale.length) * 12;
					pc += octave;
				}
				NotePos note = new NotePos();
				note.setPitch(pitch - pitchClass + pc);
				note.setPosition(notePos.getPosition() + incrementPosition);
				note.setLength(notePos.getLength());
				note.setDynamic(notePos.getDynamic());
				note.setVoice(voice);
				newNotePositions.add(note);
			}
			MusicalStructure structure = new MelodicSentence();
			structure.setNotePositions(newNotePositions, length);
			structures.add(structure);
			voice++;
		}
		return structures;
	}
	
	public static MusicalStructure harmonizeMelody(List<MusicalStructure> sentences, int[] scale, int transposition, int voice, int length) {
		MusicalStructure sentence = sentences.get(voice);
		List<NotePos> notePositions = sentence.getNotePositions();
		List<NotePos> newNotePositions = new ArrayList<NotePos>();
		for (int i = 0; i < notePositions.size(); i++) {
			NotePos notePos = notePositions.get(i);
			int pitch = notePos.getPitch();
			int pitchClass = notePos.getPitchClass();
			int pc = Scale.transpose(pitchClass, transposition, scale);
			if (pc < pitchClass) {
				pc += 12;
			}
			if (transposition - scale.length > 0) {
				int octave = (transposition / scale.length) * 12;
				pc += octave;
			}
			NotePos note = new NotePos();
			note.setPitch(pitch - pitchClass + pc);
			note.setPosition(notePos.getPosition());
			note.setLength(notePos.getLength());
			note.setDynamic(notePos.getDynamic());
			note.setVoice(notePos.getVoice() + 1 + sentences.size());
			newNotePositions.add(note);
		}
		MusicalStructure structure = new MelodicSentence();
		structure.setNotePositions(newNotePositions, length);
		return structure;
	}

}
