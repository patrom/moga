package be.data;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.collections.bag.TreeBag;
import org.apache.commons.lang.ArrayUtils;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultimap;

import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.View;

import be.util.Generator;
import be.util.TestMelody;
import be.util.Utilities;
import static jm.JMC.*;

public class Scale {
	
	private static final int ATOMIC_VALUE = 12;

	public static void main(String[] args) {
		
		System.out.println(transpose(0, 2, Scale.MAJOR_SCALE));
		System.out.println(transpose(7, 2, Scale.MAJOR_SCALE));
		System.out.println(transpose(9, 2, Scale.MAJOR_SCALE));
		System.out.println(transpose(7, 4, Scale.MAJOR_SCALE));
//		System.out.println(pickNextPitchFromScale(5, Scale.MAJOR_SCALE));
//		System.out.println(pickPreviousPitchFromScale(0, Scale.MAJOR_SCALE));
//		int pitchClass = 0;
//		for (int i = 0; i < 20; i++) {
//			int pc = pickPitchProbability(pitchClass, Scale.MAJOR_SCALE);
//			System.out.print(pc + ",");
//			pitchClass = pc;
//		}
		
//		for (int i = 0; i < Scale.MAJOR_SCALE.length; i++) {
//			System.out.println(pickLowerStepFromScale(i, Scale.MAJOR_SCALE, 2));
//		}
//		System.out.println(pickRandomFromScale(Scale.MAJOR_SCALE));
		
		
//		Phrase phrase = new Phrase();
//		phrase.add(new Note(c4, 1.0));
//		phrase.add(new Note(f4, 1.0));
//		phrase.add(new Note(a4, 1.0));
//		
//		int[] p = phrase.getPitchArray();
//		MusicalObject[] pitches = new MusicalObject[p.length];
//		for (int i = 0; i < p.length; i++) {
//			MusicalObject mo = new MusicalObject();
//			mo.setPitch(p[i]);
//			mo.setPitchClass(p[i] % 12);
//			mo.setOctave((int) Math.ceil(p[i]/12));
//			pitches[i] = mo;
//		}	
//		pitches = oneNoteMutation(pitches);
//		
//		
//		
//		
//		
//		List<Note[]> ch = new ArrayList<Note[]>();
//	
////		Score score = ReadMidi.readMidi();
//		Score score = Generator.getInstance().randomScore(8.0, 2, Scale.MAJOR_SCALE);
//		
////		Phrase phrase = new Phrase();
////		phrase.add(new Note(c5, 1.0));
////		phrase.add(new Note(c5, 2.0));
////		phrase.add(new Note(c5, 1.0));
////		phrase.add(new Note(c5, 1.0));
////		phrase.add(new Note(c5, 1.0));
////		
////		Phrase phrase2 = new Phrase();
////		phrase2.add(new Note(c5, 1.0));
////		phrase2.add(new Note(c5, 0.5));
////		phrase2.add(new Note(c5, 1.0));
////		phrase2.add(new Note(c5, 1.0));
////		phrase2.add(new Note(c5, 1.0));
////		Part part1 = new Part(phrase);
////		Part part2 = new Part(phrase2);
////		Score score = new Score();
////		score.add(part1);
////		score.add(part2);
//		Part[] parts = score.getPartArray();
//		for (Part part : parts) {
//			Phrase[] phrases = part.getPhraseArray();
//			for (Phrase phr : phrases) {
//				Note[] notes = phr.getNoteArray();
//				ch.add(notes);
//			}
//		}
//		View.notate(score);
//		
//		
//		List<Note[]> ch2 = new ArrayList<Note[]>();
//
////		Phrase phras = new Phrase();
////		phras.add(new Note(e4, 1.0));
////		phras.add(new Note(e4, 2.0));
////		phras.add(new Note(e4, 1.0));
////		phras.add(new Note(e4, 1.0));
////		phras.add(new Note(e4, 1.0));
////		
////		Phrase phras2 = new Phrase();
////		phras2.add(new Note(e4, 1.0));
////		phras2.add(new Note(e4, 0.5));
////		phras2.add(new Note(e4, 1.0));
////		phras2.add(new Note(e4, 1.0));
////		phras2.add(new Note(e4, 1.0));
////		Part p1 = new Part(phras);
////		Part p2 = new Part(phras2);
////		Score score2 = new Score();
////		score2.add(p1);
////		score2.add(p2);

	
	}

	private static MusicalObject[] oneNoteMutation(MusicalObject[] pitches) {
		for (int i = 0; i < 10; i++) {
			MusicalObject[] pitches2 = null;
			if (random.nextBoolean()) {
				pitches2 = mutateChordPreviousPitch2(pitches, Scale.MAJOR_SCALE);
			} else {
				pitches2 = mutateChordNextPitch2(pitches, Scale.MAJOR_SCALE);
			}
			pitches = pitches2;
			for (MusicalObject m : pitches) {
				System.out.print(m.getPitch() + ",");
			}
			System.out.println();
		}
		return pitches;
	}

	private static void replaceMotive(MelodicSentence melody, MusicalStructure motive) {
		List<MusicalStructure> motives = melody.getMotives();
		int index = 0;
		for (MusicalStructure m : motives) {
			if (m.getPosition() == motive.getPosition()) {
//				m.getLastNote().getNote().getPitch()
				motives.set(index, motive);
			}
			index++;
		}
	}

	private static MusicalStructure findMotivesCrossoverpoint(List<MusicalStructure> motives, int position) {
		for (MusicalStructure motive : motives) {
			if (motive.getPosition() == position) {
				return motive;
			}
		}
		return null;	
	}

//	private static MelodicSentence concatMotives(MelodicSentence splitMelody) {
//		List<MusicalStructure> motives = splitMelody.getMotives();
//		int l = motives.size();
//		int j = 1;
//		List<MusicalStructure> motivesList = new ArrayList<MusicalStructure>();
//		for (MusicalStructure motive : motives) {
//			if (j == l) {
//				break;
//			}
//			List<NotePos> beginNotePositions = motive.getNotePositions();
//			MusicalStructure nextMotive = motives.get(j);
//			// concat matching notes
//			if (motive.getLastNote().samePitch(((Motive) nextMotive).getFirstNote())) {
//				NotePos notePos = motive.getLastNote();
//				notePos.setLength(notePos.getLength() + nextMotive.getFirstNote().getLength());
//				NotePos note = motive.getLastNote();
//				note.setRhythmValue(note.getRhythmValue() + nextMotive.getFirstNote().getRhythmValue());
//				note.setDuration(note.getDuration() + nextMotive.getFirstNote().getDuration());
//				motive.setLastNote(notePos);
//				List<NotePos> positions = nextMotive.getNotePositions();
//				positions = Arrays.copyOfRange(positions, 1, positions.length);
//				nextMotive.setNotePositions(positions);
//			}
//			List<NotePos> endNotePositions = nextMotive.getNotePositions();
//			int totalLength = beginNotePositions.length + endNotePositions.length;
//			List<NotePos> positions = new NotePos[totalLength];
//			for (int i = 0; i < totalLength; i++) {
//				if (i < beginNotePositions.length) {
//					positions[i] = beginNotePositions[i];
//				} else {
//					positions[i] = endNotePositions[i - beginNotePositions.length];
//				}
//			}
//			Motive m = new Motive();
//			m.setNotePositions(positions);
//			motivesList.add(m);
//			j++;
//			
//			
//		}
//		MelodicSentence melody = new MelodicSentence();
//		melody.setMotives(motivesList);
//		return melody;	
//	}

//	private static MelodicSentence splitMelodyInMotives(MusicalStructure melody2, int crossoverpoint) {
//		List<MusicalStructure> motives =  ((MelodicSentence) melody2).getMotives();
//		MelodicSentence melody = new MelodicSentence();
//		for (MusicalStructure motive: motives) {
//			if (motive.getPosition() < crossoverpoint && crossoverpoint < (motive.getPosition() + motive.getLength())) {
//				List<NotePos> notePositions = motive.getNotePositions();
//				List<NotePos> offspring1 = new ArrayList<NotePos>();
//				List<NotePos>  offspring2 = new ArrayList<NotePos>();
//				boolean executeFirstTime = true;
//				for (int i = 0; i < notePositions.length; i++) {
//					if (notePositions[i].getPosition() < crossoverpoint) {
//						NotePos notePos = clone(notePositions[i]);
//						if ((notePos.getPosition() + notePos.getLength()) > crossoverpoint ) {
//							notePos.setLength(crossoverpoint - notePos.getPosition());
//							double value = (double)notePos.getLength()/ATOMIC_VALUE;
//							notePos.setRhythmValue(value);
//							notePos.setDuration(value);
//						}
//						offspring1.add(notePos);
//					} else {		
//						if (executeFirstTime) {
//							NotePos notePos = clone(notePositions[i]);
//							if (notePos.getPosition() != crossoverpoint) {
////								NotePos previousNotePos = clone(notePositions[i - 1]);
//								NotePos newNotePos = new NotePos();
//								newNotePos.setPitch(notePositions[i - 1].getPitch());
//								newNotePos.setDynamic(notePositions[i - 1].getDynamic());
//								newNotePos.setPosition(crossoverpoint);
//								int length = notePos.getPosition() - crossoverpoint;
//								newNotePos.setRhythmValue((double)length/ATOMIC_VALUE);
//								newNotePos.setDuration((double)length/ATOMIC_VALUE);
//								newNotePos.setLength(length);
//								offspring2.add(newNotePos);
//							}
//							offspring2.add(notePos);
//							executeFirstTime = false;
//						} else {
//							offspring2.add(clone(notePositions[i]));
//						}	
//					}
//				}
//				Motive beginMotive = new Motive();
//				Motive endMotive = new Motive();
//				beginMotive.setNotePositions(offspring1.toArray(new NotePos[offspring1.size()]));
//				endMotive.setNotePositions(offspring2.toArray(new NotePos[offspring2.size()]));
////				multimapBegin.put(beginMotive.getLastNote().getNote().getPitch() % 12, beginMotive);
////				multimapEnd.put(endMotive.getLastNote().getNote().getPitch() % 12, endMotive);
//				melody.getMotives().add(beginMotive);
//				melody.getMotives().add(endMotive);
//				break;
//			}
//		}
//		return melody;
//	}

	private static NotePos clone(NotePos notePosition) {
		NotePos notePos = new NotePos();
		notePos.setLength(notePosition.getLength());
		notePos.setPosition(notePosition.getPosition());
		notePos.setPitch(notePosition.getPitch());
		notePos.setRhythmValue(notePosition.getRhythmValue());
		notePos.setDuration(notePosition.getDuration());
		notePos.setDynamic(notePosition.getDynamic());
		return notePos;
	}

	public static int[] mutateChordNextPitch(int[] chord, int[] scale) {
		int[] pitches = Arrays.copyOf(chord, chord.length);
		int chordNoteChoice = random.nextInt(pitches.length);
//		for (int i : pitches) {
//			System.out.print(i + ",");
//		}
//		System.out.println(chordNoteChoice);
		int pitchClass = pitches[chordNoteChoice] % 12;
//		int newPitchClass = pickRandomFromScale(Scale.MAJOR_SCALE);
		int newPitchClass = pickNextPitchFromScale(pitchClass, scale);
		int temp = pitches[chordNoteChoice] - pitchClass;
		int newChordNote = temp + newPitchClass;
		if (chordNoteChoice == pitches.length - 1) {//laatste
			if (newChordNote < pitches[chordNoteChoice]) {
				pitches[chordNoteChoice] = newChordNote + 12;//+ octaaf
			} else {
				pitches[chordNoteChoice] = newChordNote;
			}
		} else if(newChordNote < pitches[chordNoteChoice + 1]) {// avoid crossing
			if (newChordNote < pitches[chordNoteChoice]) {
				pitches[chordNoteChoice] = newChordNote + 12;//+ octaaf
			} else {
				pitches[chordNoteChoice] = newChordNote;
			}
		}
		return pitches;
	}
	
	public static int[] mutateChordPreviousPitch(int[] chord, int[] scale) {
		int[] pitches = Arrays.copyOf(chord, chord.length);
		int chordNoteChoice = random.nextInt(pitches.length);
//		for (int i : pitches) {
//			System.out.print(i + ",");
//		}
//		System.out.println();
		int pitchClass = pitches[chordNoteChoice] % 12;
//		int newPitchClass = pickRandomFromScale(Scale.MAJOR_SCALE);
		int newPitchClass = pickPreviousPitchFromScale(pitchClass, scale);
		int temp = pitches[chordNoteChoice] - pitchClass;
		int newChordNote = temp + newPitchClass;
		if (chordNoteChoice == 0) {//first
			if (newChordNote > pitches[chordNoteChoice]) {
				pitches[chordNoteChoice] = newChordNote - 12;//- octaaf
			} else {
				pitches[chordNoteChoice] = newChordNote;
			}
		} else if(newChordNote > pitches[chordNoteChoice - 1]) {// avoid crossing
			if (newChordNote > pitches[chordNoteChoice]) {
				pitches[chordNoteChoice] = newChordNote - 12;//- octaaf
			} else {
				pitches[chordNoteChoice] = newChordNote;
			}
		}
		return pitches;
	}
	
	public static MusicalObject[] mutateChordNextPitch2(MusicalObject[] musicalObject, int[] scale) {
		int chordNoteChoice = random.nextInt(musicalObject.length);	
		MusicalObject mo = musicalObject[chordNoteChoice];
//		int newPitchClass = pickRandomFromScale(Scale.MAJOR_SCALE);
		int newPitchClass = pickNextPitchFromScale(mo.getPitchClass(), scale);
		int newChordNote = newPitchClass + (12* mo.getOctave());
		if (chordNoteChoice == musicalObject.length - 1) {//laatste
			if (newChordNote < mo.getPitch()) {
				mo.setPitch(newChordNote + 12);
				mo.setPitchClass(newPitchClass);
				mo.setOctave(mo.getOctave() + 1);//+ octaaf
			} else {
				mo.setPitch(newChordNote);
				mo.setPitchClass(newPitchClass);
			}
		} else if(newChordNote < musicalObject[chordNoteChoice + 1].getPitch()) {// avoid crossing
			if (newChordNote < mo.getPitch()) {
				mo.setPitch(newChordNote + 12);
				mo.setPitchClass(newPitchClass);
				mo.setOctave(mo.getOctave() + 1);//+ octaaf
			} else {
				mo.setPitch(newChordNote);
				mo.setPitchClass(newPitchClass);
			}
		}
		return musicalObject;
	}
	
	
	
	public static MusicalObject[] mutateChordPreviousPitch2(MusicalObject[] musicalObject, int[] scale) {
		int chordNoteChoice = random.nextInt(musicalObject.length);	
		MusicalObject mo = musicalObject[chordNoteChoice];
//		int newPitchClass = pickRandomFromScale(Scale.MAJOR_SCALE);
		int newPitchClass = pickPreviousPitchFromScale(mo.getPitchClass(), scale);
		int newChordNote = newPitchClass + (12* mo.getOctave());
		if (chordNoteChoice == 0) {//first
			if (newChordNote > mo.getPitch()) {
				mo.setPitch(newChordNote - 12);
				mo.setPitchClass(newPitchClass);
				mo.setOctave(mo.getOctave() - 1);//+ octaaf
			} else {
				mo.setPitch(newChordNote);
				mo.setPitchClass(newPitchClass);
			}
		} else if(newChordNote > musicalObject[chordNoteChoice - 1].getPitch()) {// avoid crossing
			if (newChordNote > mo.getPitch()) {
				mo.setPitch(newChordNote - 12);
				mo.setPitchClass(newPitchClass);
				mo.setOctave(mo.getOctave() - 1);//+ octaaf
			} else {
				mo.setPitch(newChordNote);
				mo.setPitchClass(newPitchClass);
			}
		}
		return musicalObject;
	}
	
	public static final int[] 
	                        CHROMATIC_SCALE = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11},
	                        MAJOR_SCALE = {0, 2, 4, 5, 7, 9, 11},
	                        MINOR_SCALE = {0, 2, 3, 5, 7, 8, 10},
	                        HARMONIC_MINOR_SCALE = {0, 2, 3, 5, 7, 8, 11},
	                        MELODIC_MINOR_SCALE = {0, 2, 3, 5, 7, 8, 9, 10, 11}, // mix of ascend and descend
	                        NATURAL_MINOR_SCALE = {0, 2, 3, 5, 7, 8, 10},
	                        DIATONIC_MINOR_SCALE = {0, 2, 3, 5, 7, 8, 10},
	                        AEOLIAN_SCALE = {0, 2, 3, 5, 7, 8, 10},
	                        DORIAN_SCALE = {0, 2, 3, 5, 7, 9, 10},	
	                        LYDIAN_SCALE = {0, 2, 4, 6, 7, 9, 11},
	                        MIXOLYDIAN_SCALE = {0, 2, 4, 5, 7, 9, 10},
	                        PENTATONIC_SCALE = {0, 2, 4, 7, 9},
	                        BLUES_SCALE = {0, 2, 3, 4, 5, 7, 9, 10, 11},
	                        TURKISH_SCALE = {0, 1, 3, 5, 7, 10, 11},
	                        INDIAN_SCALE = {0, 1, 1, 4, 5, 8, 10},
	                        WHOLE_TONE_SCALE = {0, 2, 4, 6, 8, 10},
							PITCH_SET_0134 = {0, 1, 3, 4},
							PITCH_SET_FOURTH = {0, 5, 7, 10},
							MODULATION_DOM = {0, 2, 4, 5, 6, 7, 9, 11},
							ALL_INTERVAL_TRETRACHORD1 = {0, 1, 4, 6},
							ALL_INTERVAL_TRETRACHORD2 = {0, 1, 3, 7},
							ALL_INTERVAL_HEXACHORD = {0, 1, 2, 4, 7, 8};

	private static Random random = new Random(System.currentTimeMillis());
	
	public static int pickRandomFromScale(int[] choices){
	    int index = random.nextInt(choices.length);
	    return choices[index];
	}
	
	public static int transpose(int pitchClass, int steps, int[] scale){
		for (int i = 0; i < scale.length; i++) {
			if (pitchClass  == scale[i]) {
				int index = (i + steps)% scale.length;
				return scale[index];
			}
		}
		return pitchClass;
	}
	
	
	public static int pickNextPitchFromScale(int pitchClass, int[] scale){
		for (int i = 0; i < scale.length; i++) {
			if (pitchClass == scale[i]) {
				if (i == scale.length - 1) {
					return scale[0];//end of array, pick first
				} else {
					return scale[i + 1];
				}
			}
		}
		return pitchClass;
	}
	
	public static int pickPreviousPitchFromScale(int pitchClass, int[] scale){
		for (int i = 0; i < scale.length; i++) {
			if (pitchClass == scale[i]) {
				if (i == 0) {
					return scale[scale.length - 1];//begin of array, pick last
				} else {
					return scale[i - 1];
				}
			}
		}
		return pitchClass;
	}
	
	public static int pickLowerStepFromScale(int pitchClass, int[] scale, int lowerStep){
		for (int i = 0; i < scale.length; i++) {
			if (pitchClass == scale[i]) {
				int step = i - lowerStep;
				if (step < 0) {
					step = step + scale.length;
					return scale[step];
				} else {
					return scale[step];
				}
			}
		}
		return pitchClass;
	}
	
	public static int pickPitchProbability(int pitchClass, int[] scale){
		int[] profile = new int[scale.length];
		int count = 0;
		for (int j = 0; j < scale.length; j++) {
			if (scale[j] < pitchClass) {
				int difference = 12 + scale[j] - pitchClass;
				Profile p = Utilities.getEnumProfile(difference);
				profile[count] = (int) (p.getpitchProfile() * 100);
				count++;
			} else {
				int difference = scale[j] - pitchClass;
//				Interval interval = Utilities.getEnumInterval(difference);
//				profile[count] = (int) (interval.getMelodicValue() * 100);
				Profile p = Utilities.getEnumProfile(difference);
				profile[count] = (int) (p.getpitchProfile() * 100);
				count++;
			}
		}
//		for (int h = scale.length -1; h > 0; h--) {
//			if (scale[h] > pitchClass) {
//				int difference = 12 - pitchClass - scale[h];
//				Interval interval = Utilities.getEnumInterval(difference);
//				profile[count] = (int) (interval.getMelodicValue() * 100);
//				count++;
//			} else {
//				int difference = pitchClass - scale[h];
//				Interval interval = Utilities.getEnumInterval(difference);
//				profile[count] = (int) (interval.getMelodicValue() * 100);
//				count++;
//			}
//		}
		int[] weightSum = new int[profile.length];
		int i, k;
		Random Rnd = new Random();
		weightSum[0] = profile[0];
		for (i = 1; i < profile.length; i++){
			weightSum[i] = weightSum[i - 1] + profile[i];
		}
		k = Rnd.nextInt(weightSum[profile.length - 1]);
		for (i = 0; k > weightSum[i]; i++)
			;
		return scale[i];	
	}
	
	
}
