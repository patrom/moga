package be.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Rest;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.View;
import be.data.MelodicSentence;
import be.data.Motive;
import be.data.MusicalObject;
import be.data.MusicalStructure;
import be.data.NotePos;
import be.data.Scale;
import be.functions.RhythmicFunctions;

public class Generator {
	
	private static final int START_PITCH = 60;
	private static final int START_PITCH_CLASS = 0;
	private static final double ATOMIC_VALUE = 12;
	private static final int REST = Integer.MIN_VALUE;
	private static final int STARTINGPITCH = 48;
	private static final int RESOLUTION = 12;
	
	private Random random = new Random();

	private static Generator instance = null;

	public static Generator getInstance(){
		if (instance == null) {
			instance = new Generator();
		}
		return instance;
	}
	
	private Generator() {
	}
	
	public void main(String[] args) {
		
////		Score s = randomScore(4, 4, Scale.MAJOR_SCALE);
////		View.notate(s);
////		Play.midi(s);
//		Phrase phrase = generateMelodyOctave(8, Scale.MAJOR_SCALE, 5);
//		Phrase phrase2 = generateMelodySameRhythm(phrase.getRhythmArray(), Scale.MAJOR_SCALE, 4);
////		Phrase phrase3 = generateMelodyLowerInterval(Scale.MAJOR_SCALE, phrase2, 2);
//		Score score = new Score();
//		Part part1 = new Part(phrase);
//		Part part2 = new Part(phrase2);
////		Part part3 = new Part(phrase3);
//		score.add(part1);
//		score.add(part2);
////		score.add(part3);
//		
//		
//		View.notate(score);

		
	}
	
	public int[] generateNextChord(int[] chord, int[] scale) {
		int[] nextChord = new int[chord.length];
		if (random.nextBoolean()) {
			nextChord = Scale.mutateChordNextPitch(chord, scale);
		}else{
			nextChord = Scale.mutateChordPreviousPitch(chord, scale);
		}	
		return nextChord;
	}

	public int[] generateFirstChord(int voices, int[] scale) {
		int[] chord = new int[voices];
		int pitchClass = Scale.pickRandomFromScale(scale);
		int pitch = pitchClass + STARTINGPITCH;
		for (int i = 0; i < chord.length; i++) {
			chord[i] = pitch;
			int pc = Scale.pickRandomFromScale(scale);
			int higherPitch = STARTINGPITCH + pc;
			while (higherPitch < pitch) {
				higherPitch = higherPitch + 12;
			}
			pitch = higherPitch;
		}
		return chord;
	}

	public Score randomScore(double melodyLength, int voices, int[] scale) {
		Score score = new Score();
		Phrase upperPhrase = null;
		for (int i = 0; i < voices; i++) {
			if (upperPhrase == null) {
				upperPhrase = generateMelody(melodyLength, scale);
			} else {
				upperPhrase = generateMelody(melodyLength, scale, upperPhrase, 0.5);
			}
			Part part = new Part(upperPhrase);
			score.add(part);
		}
		return score;	
	}
	
	public Score randomScore2(double melodyLength, int voices, int[] scale, int octave) {
		Score score = new Score();
		Phrase upperPhrase = null;
		for (int i = 0; i < voices; i++) {
			upperPhrase = generateMelodyOctave(melodyLength, scale, octave);
			Part part = new Part(upperPhrase);
			score.add(part);
			octave--;
		}
		return score;	
	}
	
	public Score randomScoreSameRhythm(double melodyLength, int voices, int[] scale, int octave) {
		Score score = new Score();
		Phrase phrase = null;
		for (int i = 0; i < voices; i++) {			
			if (i == 0) {
				phrase = generateMelodyOctave(melodyLength, scale, octave);
			}else{
				phrase = generateMelodySameRhythm(phrase.getRhythmArray(), scale, octave);
			}
			Part part = new Part(phrase);
			score.add(part);
			octave--;
		}
		return score;	
	}
	
	public List<MusicalStructure> generateMelodies(double melodyLength, int voices, int[] scale) {
		List<Note[]> melodies = new ArrayList<Note[]>();
		Phrase upperPhrase = null;
		for (int i = 0; i < voices; i++) {
			if (upperPhrase == null) {
				upperPhrase = generateMelody(melodyLength, scale);
			} else {
				upperPhrase = generateMelody(melodyLength, scale, upperPhrase, 0.5);
			}
			melodies.add(upperPhrase.getNoteArray());
		}
		return extractNotePositions(melodies);	
	}
	
	public List<MusicalStructure> polyphonicMelodies(double melodyLength, int voices, int[] scale, int octave) {
		List<Note[]> melodies = new ArrayList<Note[]>();
		for (int i = 0; i < voices; i++) {
			Phrase phrase = generateMelodyOctave(melodyLength, scale, octave);
			melodies.add(phrase.getNoteArray());
			octave--;
		}
		return extractNotePositions(melodies);	
	}
	
	public List<Note[]> stepVoiceLeading(int size, int voices, int[] scale) {
		List<int[]> chords = new ArrayList<int[]>();
		double[] rhythm = RhythmicFunctions.createRandomRhythmPattern2(size);
		int[] chord = new int[voices];
		List<Note[]> chordList = new ArrayList<Note[]>();
		for (int i = 0; i < rhythm.length; i++) {		
			if (i == 0) {
				chord = generateFirstChord(voices, scale);
			} else {
				int[] nextChord = generateNextChord(chord, scale);
				chord = Arrays.copyOf(nextChord, nextChord.length);
			}
			chords.add(chord);
		}	
		for (int v = 0; v < voices; v++) {
			Note[] melody = new Note[rhythm.length];
			for (int i = 0; i < rhythm.length; i++) {
				double r = rhythm[i];
				int[] pitches = chords.get(i);
				melody[i] = new Note(pitches[voices - 1 - v], r);
			}
			chordList.add(melody);
		}
		return chordList;
	}
	
	public List<Note[]> generateChordsWithoutRhythm(int size, int voices, int[] scale) {
		List<int[]> chords = new ArrayList<int[]>();
		int[] chord = new int[voices];
		List<Note[]> chordList = new ArrayList<Note[]>();
		for (int i = 0; i < size; i++) {		
			chord = generateFirstChord(voices, scale);
			chords.add(chord);
		}	
		for (int v = 0; v < voices; v++) {
			Note[] melody = new Note[size];
			for (int i = 0; i < size; i++) {
				int[] pitches = chords.get(i);
				melody[i] = new Note(pitches[voices - 1 - v], 1.0);
			}
			chordList.add(melody);
		}
		return chordList;
	}
	
	public List<NotePos[]> generateChordsWithoutRhythm2(int size, int voices, int[] scale) {
		List<int[]> chords = new ArrayList<int[]>();
		int[] chord = new int[voices];
		List<NotePos[]> chordList = new ArrayList<NotePos[]>();
		for (int i = 0; i < size; i++) {		
			chord = generateFirstChord(voices, scale);
			chords.add(chord);
		}	
		for (int v = 0; v < voices; v++) {
			NotePos[] melody = new NotePos[size];
			for (int i = 0; i < size; i++) {
				int[] pitches = chords.get(i);
				melody[i] = new NotePos(pitches[voices - 1 - v], RESOLUTION * i, RESOLUTION);
			}
			chordList.add(melody);
		}
		return chordList;
	}

	
	public List<MusicalStructure> generateMelodiesSameRhythm(double melodyLength, int voices, int[] scale, int octave) {
		List<Note[]> melodies = new ArrayList<Note[]>();
		Phrase phrase = null;
		for (int i = 0; i < voices; i++) {
			if (i == 0) {
				phrase = generateMelodyOctave(melodyLength, scale, octave);
			}else{
				phrase = generateMelodySameRhythm(phrase.getRhythmArray(), scale, octave);
			}
			melodies.add(phrase.getNoteArray());
			octave--;
		}
		return extractNotePositions(melodies);	
	}
	
	public List<Note[]> homophonic(double melodyLength, int voices, int[] scale, int octave) {
		List<Note[]> melodies = new ArrayList<Note[]>();
		Phrase phrase = null;
		for (int i = 0; i < voices; i++) {
			if (i == 0) {
				phrase = generateMelodyOctave(melodyLength, scale, octave);
			}else if (i == 1) {
				phrase = generateChordMelodyOctave(melodyLength, scale, octave);
			}else{
				phrase = generateMelodySameRhythm(phrase.getRhythmArray(), scale, octave);
			}
			melodies.add(phrase.getNoteArray());
			octave--;
		}
		return melodies;	
	}
	
	
	
	
	public Phrase generateMelody(double melodyLength, int[] scale) {
		Phrase phrase = new Phrase();
		double phraseLength = 0;
		int temp = START_PITCH % 12;
		while (phraseLength < melodyLength) {
//			int pitchClass = Scale.pickRandomFromScale(scale);
			int pitchClass = Scale.pickPitchProbability(temp, scale);
			temp = pitchClass;
			double rhythm = RhythmicFunctions.getRhythmProbability();
			double tempPhraseLength = phraseLength + rhythm;
			Note note = null;
			if (tempPhraseLength > melodyLength) {
				double lastRhythm = melodyLength - phraseLength;
				note = new Note(pitchClass + START_PITCH, lastRhythm);
			} else {
				note = new Note(pitchClass + START_PITCH, rhythm);	
			}
			phrase.add(note);
			phraseLength = tempPhraseLength;
		}
		return phrase;	
	}
	
	public Phrase generateMelodyOctave(double melodyLength, int[] scale, int octave) {
		Phrase phrase = new Phrase();
		double phraseLength = 0;
		int temp = START_PITCH_CLASS;
		while (phraseLength < melodyLength) {
//			int randomRest = random.nextInt(10);
//			if (randomRest == 4) {
//				double rhythm = RhythmicFunctions.getRhythmProbability();
//				double tempPhraseLength = phraseLength + rhythm;
//				Note note = null;
//				if (tempPhraseLength > melodyLength) {
//					double lastRhythm = melodyLength - phraseLength;
//					note = new Rest(lastRhythm);
//				} else {
//					note = new Rest(rhythm);	
//				}
//				phrase.add(note);
//				phraseLength = tempPhraseLength;
//			} else {
				int pitchClass = Scale.pickRandomFromScale(scale);
//				int pitchClass = Scale.pickPitchProbability(temp, scale);
				temp = pitchClass;
				double rhythm = RhythmicFunctions.getRhythmProbability();
				double tempPhraseLength = phraseLength + rhythm;
				Note note = null;
				int pitch = pitchClass + (octave * 12);
				if (tempPhraseLength > melodyLength) {
					double lastRhythm = melodyLength - phraseLength;
					note = new Note(pitch, lastRhythm);
				} else {
					note = new Note(pitch, rhythm);	
				}
				phrase.add(note);
				phraseLength = tempPhraseLength;
//			}	
		}
		return phrase;	
	}
	
	public Phrase generateChordMelodyOctave(double melodyLength, int[] scale, int octave) {
		Phrase phrase = new Phrase();
		double phraseLength = 0;
		int temp = START_PITCH_CLASS;
		while (phraseLength < melodyLength) {
//			int pitchClass = Scale.pickRandomFromScale(scale);
			int pitchClass = Scale.pickPitchProbability(temp, scale);
			temp = pitchClass;
			double rhythm = RhythmicFunctions.getChordRhythmProbability();
			double tempPhraseLength = phraseLength + rhythm;
			Note note = null;
			int pitch = pitchClass + (octave * 12);
			if (tempPhraseLength > melodyLength) {
				double lastRhythm = melodyLength - phraseLength;
				note = new Note(pitch, lastRhythm);
			} else {
				note = new Note(pitch, rhythm);	
			}
			phrase.add(note);
			phraseLength = tempPhraseLength;
		}
		return phrase;	
	}
	
	public Phrase generateMelodySameRhythm(double[] rhythm, int[] scale, int octave) {
		Phrase phrase = new Phrase();
		int temp = START_PITCH_CLASS;
		for (int i = 0; i < rhythm.length; i++) {
			int pitchClass = Scale.pickPitchProbability(temp, scale);
			temp = pitchClass;
			int pitch = pitchClass + (octave * 12);
			Note note = new Note(pitch, rhythm[i]);
			phrase.add(note);
		}
		return phrase;	
	}
	
	public int[] atomicPhrase(Phrase phrase, double atomicValue){
		Note[] melody = phrase.getNoteArray();
		int[] atomicNotes = new int[(int)(phrase.getEndTime()/atomicValue)];
		double amountOfValues = 0;
		int count = 0;
		for (int j = 0; j < melody.length; j++) {
			amountOfValues = melody[j].getRhythmValue() / atomicValue;
			for (int k = 0; k < amountOfValues; k++) {
				atomicNotes[count] = melody[j].getPitch();
				count++;
			}
		}
		return atomicNotes;	
	}
	
	private  Phrase generateMelody(double melodyLength, int[] scale, Phrase upperMelody, double atomicValue) {
		int[] pitches = atomicPhrase(upperMelody, atomicValue);
		Phrase phrase = new Phrase();
		double phraseLength = 0;
		int temp = START_PITCH % 12;
		while (phraseLength < melodyLength) {
//			int pitchClass = Scale.pickRandomFromScale(scale);
			int pitchClass = Scale.pickPitchProbability(temp, scale);
			temp = pitchClass;
			double rhythm = RhythmicFunctions.getRhythmProbability();
			double tempPhraseLength = phraseLength + rhythm;
			if (tempPhraseLength > melodyLength) {
				rhythm = melodyLength - phraseLength;
			} 
			int newPitch = pitchClass + START_PITCH;
			int upperPitch = pitches[(int) tempPhraseLength];
			int r = 12;
			while (newPitch > upperPitch) {
				newPitch = newPitch - r;//lower octave(s)
			}
			Note note = new Note(newPitch, rhythm);
			phrase.add(note);
			phraseLength = tempPhraseLength;
		}
		return phrase;	
	}
	
	private Phrase generateMelodySameRhythm(double[] rhythm, int[] scale, Phrase upperMelody, double atomicValue) {
		int[] pitches = atomicPhrase(upperMelody, atomicValue);
		Phrase phrase = new Phrase();
		int temp = START_PITCH % 12;
		for (int i = 0; i < rhythm.length; i++) {
			int pitchClass = Scale.pickPitchProbability(temp, scale);
			temp = pitchClass;
			int newPitch = pitchClass + START_PITCH;
			int upperPitch = pitches[(int) rhythm[i]];
			int r = 12;
			while (newPitch > upperPitch) {
				newPitch = newPitch - r;//lower octave(s)
			}
			Note note = new Note(newPitch, rhythm[i]);
			phrase.add(note);
		}
		return phrase;	
	}
	
	private Phrase generateMelodyLowerInterval(int[] scale, Phrase upperMelody, int lowerStep) {
		Phrase phrase = new Phrase();
		Note[] notes = upperMelody.getNoteArray();
		for (Note note : notes) {
			int pc = Scale.pickLowerStepFromScale(note.getPitch()%12, scale, lowerStep);
			int newPitch = pc + START_PITCH;
			int r = 12;
			while (newPitch > note.getPitch()) {
				newPitch = newPitch - r;//lower octave(s)
			}
			Note n = new Note(newPitch, note.getRhythmValue());
			phrase.add(n);
		}
		return phrase;	
	}
	
	/**
	 * Extract the exact positions of all notes in a musical piece according to an atomic constant
	 * @param sentences the musical sentences
	 * @return A list of the musical structures (sentence has one motive)
	 */
	public List<MusicalStructure> extractNotePositions(List<Note[]> melodies){
		List<MusicalStructure> notePositionList = new ArrayList<MusicalStructure>();
//		for (Note[] notes : melodies) {
//			int position = 0;
//			NotePos[] notePositions = new NotePos[notes.length];
//			Motive motive = new Motive();
//			for (int i = 0; i < notes.length; i++) {
//				NotePos notePos = new NotePos();
//				notePos.setDuration(notes[i].getDuration());
//				notePos.setDynamic(notes[i].getDynamic());
//				notePos.setRhythmValue(notes[i].getRhythmValue());
//				notePos.setPitch(notes[i].getPitch());
//				notePos.setPosition(position);
//				int length = (int) Math.ceil(ATOMIC_VALUE/(1/notes[i].getRhythmValue()));
//				notePos.setLength(length);
//				position = position + length;
//				notePositions[i] = notePos;
//			}
//			motive.setNotePositions(notePositions);
//			MelodicSentence sentence = new MelodicSentence();
//			sentence.setPosition(motive.getPosition());
//			sentence.setLength(motive.getLength());
//			sentence.getMotives().add(motive);
//			notePositionList.add(sentence);
//		}
		return notePositionList;
	}
	
	public List<MusicalStructure> extractNotePositions3(List<NotePos[]> melodies){
		List<MusicalStructure> notePositionList = new ArrayList<MusicalStructure>();
//		for (NotePos[] notes : melodies) {
//			Motive motive = new Motive();
//			motive.setNotePositions(notes);
//			MelodicSentence sentence = new MelodicSentence();
//			sentence.setPosition(motive.getPosition());
//			sentence.setLength(motive.getLength());
//			sentence.getMotives().add(motive);
//			notePositionList.add(sentence);
//		}
		return notePositionList;
	}
	
	public List<MusicalStructure> extractNotePositions2(List<Phrase> melodies){
		List<MusicalStructure> notePositionList = new ArrayList<MusicalStructure>();
//		for (Phrase phrase : melodies) {
//			Note[] notes = phrase.getNoteArray();
//			int position = (int) Math.ceil(ATOMIC_VALUE/(1/phrase.getStartTime()));
//			NotePos[] notePositions = new NotePos[notes.length];
//			Motive motive = new Motive();
//			for (int i = 0; i < notes.length; i++) {
//				NotePos notePos = new NotePos();
//				notePos.setDuration(notes[i].getDuration());
//				notePos.setDynamic(notes[i].getDynamic());
//				notePos.setRhythmValue(notes[i].getRhythmValue());
//				notePos.setPitch(notes[i].getPitch());
//				notePos.setPosition(position);
//				int length = (int) Math.ceil(ATOMIC_VALUE/(1/notes[i].getRhythmValue()));
//				notePos.setLength(length);
//				position = position + length;
//				notePositions[i] = notePos;
//			}
//			motive.setNotePositions(notePositions);
//			MelodicSentence sentence = new MelodicSentence();
//			sentence.setPosition(motive.getPosition());
//			sentence.setLength(motive.getLength());
//			sentence.getMotives().add(motive);
//			notePositionList.add(sentence);
//		}
		return notePositionList;
	}
	
	/**
	 * Extracts all (harmonic) musical objects, according to an atomic constant
	 * @param sentences The (melodic) sentences 
	 * @return A list of musical objects
	 */
	public List<MusicalObject[]> extractMusicalObjects(List<MusicalStructure> sentences) {
		int voice = 0;
		int length = 0;
		for (MusicalStructure sentence : sentences) {
			int totalLength = sentence.getLength() + sentence.getPosition();
			if (length < totalLength) {
				length = totalLength;
			}	
		}
		List<MusicalObject[]> musicalObjects = new ArrayList<MusicalObject[]>(length);
//		for (int i = 0; i < length; i++) {
//			MusicalObject[] musicalObjectChord = new MusicalObject[sentences.size()];
//			musicalObjects.add(musicalObjectChord);
//		}
//		for (MusicalStructure sentence : sentences) {
////			if (sentence.getPosition() > 0) {
////				int count = 0;
////				while (sentence.getPosition() >= count) {
////					MusicalObject[] musicalObjectChord = new MusicalObject[sentences.size()];
////					musicalObjects.add(count, musicalObjectChord);
////					count++;
////				}
////			}
//			NotePos[] notePositions = sentence.getNotePositions();
//			int nextPosition = sentence.getPosition();
//			for (int j = 0; j < notePositions.length ; j++) {
//				int startPosition = notePositions[j].getPosition();
//				nextPosition = nextPosition + notePositions[j].getLength();
//				for (; startPosition < nextPosition; startPosition++) {
////					if (voice == 0) {
////						MusicalObject[] musicalObjectChord = new MusicalObject[sentences.size()];
////						createMusicalObject(voice, notePositions[j].getNote(), musicalObjectChord);
////						musicalObjects.add(startPosition, musicalObjectChord);
////					} else {
//						MusicalObject[] musicalObjectChord = musicalObjects.get(startPosition);
//						createMusicalObject(voice, notePositions[j],musicalObjectChord);
////					}	
//				}
//			}
//			voice++;
//		}
		return musicalObjects;
	}
	
	private void createMusicalObject(int voice, NotePos note, MusicalObject[] musicalObjectChord) {
		MusicalObject musicalObject = new MusicalObject();
		if(note.isRest()){
			musicalObject.setPitch(REST);
			musicalObject.setVoice(voice);
			musicalObject.setDynamic(0);
		}else{
			int pitch = note.getPitch();
			musicalObject.setPitch(pitch);
			musicalObject.setPitchClass(pitch % 12);	
			musicalObject.setOctave((int) Math.floor(pitch/12));
			musicalObject.setVoice(voice);
			musicalObject.setDynamic(note.getDynamic());
		}
		musicalObjectChord[voice] = musicalObject;
	}
	
	public Phrase generateMelodyOctave2(double melodyLength, int[] scale, int octave, int[] profile) {
		double[] pattern = RhythmicFunctions.getRhythmPatternProfile(profile, melodyLength);
		Phrase phrase = new Phrase();
		int temp = START_PITCH_CLASS;
		for (int i = 0; i < pattern.length; i++) {
			int pitchClass = Scale.pickRandomFromScale(scale);
//			int pitchClass = Scale.pickPitchProbability(temp, scale);
			temp = pitchClass;
			int pitch = pitchClass + (octave * 12);
			Note note = new Note(pitch, pattern[i]);	
			phrase.add(note);
		}
		return phrase;	
	}
	
	public List<MusicalStructure> polyphonicMelodiesWithRhythmProfile(double melodyLength, int voices, int[] scale, int octave, int[] profile) {
		List<Note[]> melodies = new ArrayList<Note[]>();
		for (int i = 0; i < voices; i++) {
			Phrase phrase = generateMelodyOctave2(melodyLength, scale, octave, profile);
			melodies.add(phrase.getNoteArray());
			octave--;
		}
		return extractNotePositions(melodies);	
	}
	

	
}
