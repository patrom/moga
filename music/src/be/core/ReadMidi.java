package be.core;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import be.functions.HarmonicFunctions;
import be.functions.RhythmicFunctions;
import be.util.Utilities;

import jm.JMC;
import jm.music.data.Anchoring;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.music.tools.Prob;
import jm.util.Play;
import jm.util.Read;
import jm.util.View;

public class ReadMidi {
	
	public static void main(String[] args) {
		Score s = readMidi();
//		s.setTempo(60);
		View.notate(s);
		Play.midi(s, false);
	}

	public static void main2(String[] args) {
		// for (int i = 0; i < 100; i++) {
		// double rv = Prob.gaussianRhythmValue(1, 1, JMC.DOTTED_CROTCHET);
		// System.out.println(rv);
		// }

		Score s = readMidi();
		MusicMatrix test = MusicMatrix.createMusicMatric(s, 0.25);
		test.show();
		Part[] parts = s.getPartArray();
		for (Part part : parts) {
			Phrase[] phrases = part.getPhraseArray();
			for (int i = 0; i < phrases.length; i++) {
				System.out.println(phrases[i]);
				List<Integer> valueChanges = RhythmicFunctions
						.getRhythmValueChanges(phrases[i]);
				System.out.print(valueChanges);
				System.out.println();
			}
		}
		// View.notate(s);
		// View.show(s);
		// Play.midi(s);
		// Map<Double, List<Integer>> chordMap = createMapInteger(s);
		// Set<Double> keys = chordMap.keySet();
		// for (Double key : keys) {
		// System.out.println(key + ":" );
		// List<Integer> list = chordMap.get(key);
		// for (Integer note : list) {
		// System.out.print(note + ",");
		// }
		// System.out.println();
		// }

		// Map<Double, Set<Integer>> chordMap = createMapNoDoubles(s);
		// Set<Double> keys = chordMap.keySet();
		// for (Double key : keys) {
		// System.out.print(key + " : " );
		// Set<Integer> list = chordMap.get(key);
		// for (Integer note : list) {
		// System.out.print(note + ",");
		// }
		// double dissonanceDegree =
		// HarmonicFunctions.analyseChordIntervalsNoDoubles(list);
		// System.out.print(" dissonance: " +
		// Utilities.round(dissonanceDegree,3));
		// System.out.println();
		// }
		//
		//
		// NavigableMap<Double, List<Integer>> chords = createMapInteger(s);
		// Set<Double> beats = chords.keySet();
		// for (Double key : beats) {
		// System.out.print(key + " : " );
		// List<Integer> list = chords.get(key);
		// for (Integer note : list) {
		// System.out.print(note + ",");
		// }
		// if (list.size() > 1) {
		// Integer[] notes = list.toArray(new Integer[list.size()]);
		// Note root = HarmonicFunctions.getRootChord(notes);
		// System.out.print(" root: " + root.getNote());
		// System.out.println();
		// }
		// }

		// NavigableMap<Double, Set<Integer>> navigableChords =
		// createMapNoDoubles(s);
		// NavigableMap<Double, Double> map =
		// calculateHarmonicDegrees(navigableChords);

		// NavigableMap<Double, Set<Integer>> navigableChords =
		// createMapNoDoubles(s);
		// for (double i = 0.0; i < navigableChords.size(); i++) {
		// NavigableMap<Double, Set<Integer>> subMap = navigableChords.subMap(i,
		// true, i + 1, false);
		// Set<Double> keysBeat = subMap.keySet();
		// if (keysBeat.size() > 1) {
		// double bestBeatDegree = 0;
		// System.out.println("beat > 1");
		// for (Double keyBeat : keysBeat) {
		// System.out.print(keyBeat + " : " );
		// Set<Integer> set = navigableChords.get(keyBeat);
		// for (Integer note : set) {
		// System.out.print(note + ",");
		// }
		// if (set.size() > 0) {
		// double dissonanceDegree =
		// HarmonicFunctions.analyseChordIntervalsNoDoubles(set);
		// if (dissonanceDegree > bestBeatDegree) {
		// bestBeatDegree = dissonanceDegree;
		// }
		// System.out.print(" dissonance: " +
		// Utilities.round(dissonanceDegree,3));
		// }
		// System.out.println();
		// }
		// System.out.print("best: " + Utilities.round(bestBeatDegree,3));
		// System.out.println();
		// }
		// }

		// Double[] k = keys.toArray(new Double[keys.size()]);
		// Arrays.sort(k);

		// Collection<List<Note>> chords = chordMap.values();
		// for (List<Note> list : chords) {
		// for (Note note : list) {
		// System.out.print(note.getNote() + ",");
		// }
		// System.out.println();
		// }

	}

	public static List<Score> getScoresTestFiles(String path) {
		List<Score> scores = new ArrayList<Score>();
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				Score s = new Score();
				Read.midi(s, listOfFiles[i].getAbsolutePath());
				s.setTitle(listOfFiles[i].getName());
				scores.add(s);
			}
		}
		return scores;
	}

	public static NavigableMap<Double, Double> calculateHarmonicDegrees(
			NavigableMap<Double, Set<Integer>> navigableChords) {
		NavigableMap<Double, Double> degrees = new TreeMap<Double, Double>();
		Set<Double> keysBeat = navigableChords.keySet();
		for (Double keyBeat : keysBeat) {
			System.out.print(keyBeat + " : ");
			Set<Integer> set = navigableChords.get(keyBeat);
			if (set.size() > 0) {
				double dissonanceDegree = HarmonicFunctions
						.analyzechordNotesNoDoubles(set);
				System.out.print(" dissonance: "
						+ Utilities.round(dissonanceDegree, 3));
				degrees.put(keyBeat, Utilities.round(dissonanceDegree, 3));
			}
			System.out.println();
		}
		System.out.println();
		return degrees;
	}

	public static Score readMidi() {
		FileDialog fd;
		Frame f = new Frame();
		// open a MIDI file
		fd = new FileDialog(f, "Open MIDI file or choose cancel to finish.",
				FileDialog.LOAD);
		fd.show();
		Score s = new Score();
		Read.midi(s, fd.getDirectory() + fd.getFile());
		s.setTitle(fd.getFile());
		return s;
	}

	public static List<int[]> getChords(Score s) {
		int[] chord = new int[4];
		Map<Double, int[]> chordMap = new HashMap<Double, int[]>();
		Part[] parts = s.getPartArray();
		for (Part part : parts) {
			Phrase[] phrases = part.getPhraseArray();
			System.out.println(part.getTitle());
			for (int i = 0; i < phrases.length; i++) {
				Note[] notes = phrases[i].getNoteArray();
				phrases[i].getNote(6);
			}
		}
		return null;
	}

	public static Map<Double, List<Note>> createMap(Score s) {
		Map<Double, List<Note>> chordMap = createBeatMap(s);
		Set<Double> keys = chordMap.keySet();
		Part[] parts = s.getPartArray();
		for (int k = 0; k < parts.length; k++) {
			Phrase[] phrases = parts[k].getPhraseArray();
			for (int i = 0; i < phrases.length; i++) {
				for (Double key : keys) {
					Note note = getNoteAtBeat(phrases[i], key);
					if (note != null && !note.isRest()) {
						List<Note> chord = chordMap.get(key);
						chord.add(note);
						chordMap.put(key, chord);
					}
				}
			}
		}
		return chordMap;
	}

	public static NavigableMap<Double, List<Integer>> createMapInteger(Score s) {
		NavigableMap<Double, List<Integer>> chordMap = createBeatMapInteger(s);
		Set<Double> keys = chordMap.keySet();
		Part[] parts = s.getPartArray();
		for (int k = 0; k < parts.length; k++) {
			Phrase[] phrases = parts[k].getPhraseArray();
			for (int i = 0; i < phrases.length; i++) {
				for (Double key : keys) {
					Note note = getNoteAtBeat(phrases[i], key);
					if (note != null && !note.isRest()) {
						List<Integer> chord = chordMap.get(key);
						chord.add(note.getPitch());
						Collections.sort(chord);
						chordMap.put(key, chord);
					}
				}
			}
		}
		return chordMap;
	}

	public static NavigableMap<Double, Set<Integer>> createMapNoDoubles(Score s) {
		NavigableMap<Double, List<Integer>> map = createMapInteger(s);
		NavigableMap<Double, Set<Integer>> chordMap = new TreeMap<Double, Set<Integer>>();
		Set<Double> keys = map.keySet();
		for (Double key : keys) {
			List<Integer> list = map.get(key);
			Integer[] chord = list.toArray(new Integer[list.size()]);
			if (chord.length > 1) {
				Integer bassNote = chord[0];
				Set<Integer> chordSet = new TreeSet<Integer>();
				for (int i = 1; i < chord.length; i++) {
					Integer note = chord[i];
					Integer n = (note - bassNote) % 12;
					if (!n.equals(0)) {
						chordSet.add(n);
					}
				}
				chordMap.put(key, chordSet);
			}
		}
		return chordMap;
	}

	public static NavigableMap<Double, List<Note>> createBeatMap(Score s) {
		NavigableMap<Double, List<Note>> beatMap = new TreeMap<Double, List<Note>>();
		Part[] parts = s.getPartArray();
		for (Part part : parts) {
			Phrase[] phrases = part.getPhraseArray();
			for (int i = 0; i < phrases.length; i++) {
				int[] notes = phrases[i].getPitchArray();
				for (int j = 0; j < notes.length; j++) {
					double startTime = phrases[i].getNoteStartTime(j);
					beatMap.put(startTime, new ArrayList<Note>());
				}
			}
		}
		return beatMap;
	}

	public static NavigableMap<Double, List<Integer>> createBeatMapInteger(
			Score s) {
		NavigableMap<Double, List<Integer>> beatMap = new TreeMap<Double, List<Integer>>();
		Part[] parts = s.getPartArray();
		for (Part part : parts) {
			Phrase[] phrases = part.getPhraseArray();
			for (int i = 0; i < phrases.length; i++) {
				int[] notes = phrases[i].getPitchArray();
				for (int j = 0; j < notes.length; j++) {
					double startTime = phrases[i].getNoteStartTime(j);
					beatMap.put(startTime, new ArrayList<Integer>());
				}
			}
		}
		return beatMap;
	}

	public static Note getNoteAtBeat(Phrase phrase, double beat) {
		Note[] notes = phrase.getNoteArray();
		int i = 0;
		double sum = phrase.getStartTime();
		if (phrase.getStartTime() > beat) {
			return null;
		}
		while (sum <= beat && i < notes.length) {
			sum = sum + notes[i].getRhythmValue();
			i++;
		}
		return notes[i - 1];
	}

}
