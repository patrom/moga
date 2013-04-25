package be.functions;



import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

import com.google.common.eventbus.AllowConcurrentEvents;

import net.sourceforge.jFuzzyLogic.fcl.FclParser.main_return;

import be.analyzer.HarmonyAnalyzer;
import be.data.Interval;
import be.data.Scale;
import be.set.ForteNumber;
import be.set.IntervalVector;
import be.set.PitchSet;
import be.util.Frequency;
import be.util.Utilities;

import jm.music.data.CPhrase;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Read;
import jm.util.View;
import static jm.constants.Pitches.*;

public class HarmonicFunctions {
	private static final int STARTING_PITCH = 60;
	
//	public static void main(String[] args) {
//		int[] testChord = new int[6];
//		testChord[0] = 60;
//		testChord[1] = 64;
//		testChord[2] = 67;
//		testChord[3] = 71;
//		testChord[4] = 74;
//		testChord[5] = 78;
//		double diss = analyzeChord(testChord);
////		double diss2 = analyzeChord2(testChord);
//		double[] stat = analyzeChordStat(testChord);
//		DescriptiveStatistics stats = new DescriptiveStatistics(stat);
//		System.out.println(diss);
////		System.out.println(diss2);
//		System.out.println(Arrays.toString(stat));
//		System.out.println("geometric: " + stats.getGeometricMean());
//	}

	public static void main(String[] args) {
		
//		int[] testChord = new int[3];
//		testChord[0] = 48;
//		testChord[1] = 79;
//		testChord[2] = 64;
//		double diss = analyzeChord(testChord);
//		double diss2 = analyzeChord2(testChord);
//		
//		System.out.println(diss);
//		System.out.println(diss2);
//		
//		for (int i = 0; i < testChord.length; i++) {
////			Note note = new Note(i, 1.0);
////			System.out.print(note.getNote());
//			System.out.print(Frequency.makeNoteSymbol(testChord[i]) + " ");
//		}
//		System.out.println(diss);
		List<Note[]> all3PartChords = generate3PartChords();
		List<int[]> chordsPitches = new ArrayList<int[]>();
		for (Note[] notes : all3PartChords) {
			int[] ch = new int[notes.length];
			for (int i = 0; i < notes.length; i++) {
				ch[i] = notes[i].getPitch();
			}
			chordsPitches.add(ch);
		}
//		List<int[]> chordsMajorScale = generateAll3PartClosedChordsForScale(Scale.MAJOR_SCALE, STARTING_PITCH);
//		for (int[] pitches : chordsMajorScale) {
//			for (int pitch : pitches) {
//				System.out.print(Frequency.makeNoteSymbol(pitch) + ",");
//			}
//			System.out.println();
//		}
		
		Map<Double, List<int[]>> sonanceMap = new TreeMap<Double, List<int[]>>();
		IntervalVector intervalVector = null;
		
		HarmonyAnalyzer analyzer = new HarmonyAnalyzer();
		for (int[] notes : chordsPitches) {
//		for (int[] notes : chordsMajorScale) {
			int[] noteArray = new int[4];
			NavigableSet<Integer> notesSet = new TreeSet<Integer>();
			for (int i = 0; i < notes.length; i++) {
				int p = notes[i] % 12;
				noteArray[i] = p;
				notesSet.add(p);
			}
			PitchSet set = new PitchSet(noteArray);
			intervalVector = new IntervalVector(set);
//			double dissDegree = calculateHarmonicValue(intervalVector);
//			double dissDegree = analyzechordNotesNoDoubles(notesSet);
			double dissDegree = analyzeChord(notes);
			double[] dissDegreeArray = analyzeChordStat(notes);
//			analyzer.analyzeHarmony(notes);
//			System.out.println("sonance: " + analyzer.getSonance());
//			System.out.println("root: " + analyzer.getRoot());
//			System.out.println(analyzer);
//			double dissDegree = analyzer.getSonance();
			DescriptiveStatistics stats = new DescriptiveStatistics(dissDegreeArray);
			double dissDegreeStat = stats.getMean();
//			if (dissDegree != dissDegreeStat) {
//				System.err.println("difference!!");
//			}
//			System.out.print(Arrays.toString(notes));
//			System.out.print(Arrays.toString(dissDegreeArray));
//			System.out.print(" geo mean:" + stats.getGeometricMean());
			double deviation = stats.getGeometricMean();
//			System.out.print(" deviation: " + deviation);
//			System.out.println();
			
			deviation = Utilities.round(deviation, 3);
			if (sonanceMap.containsKey(deviation)) {
				List<int[]> chordList = sonanceMap.get(deviation);
				chordList.add(notes);
				sonanceMap.put(deviation, chordList);
			} else {
				List<int[]> chordList = new ArrayList<int[]>();
				chordList.add(notes);
				sonanceMap.put(deviation, chordList );
			}
			
//			System.out.println(set.toKeys());
//			System.out.println(intervalVector);
//			System.out.println("intervalVector value: " + val);
//			System.out.println("dissonant value: " + dissDegree);
//			ForteNumber forteNumber = new ForteNumber(set);
//			System.out.println(forteNumber);
		}
		
		Set<Double> keys = sonanceMap.keySet();
		for (Double key : keys) {
			List<int[]> chords = sonanceMap.get(key);
			for (int[] chord : chords) {
				int[] noteArray = new int[all3PartChords.size()];
				for (int i = 0; i < chord.length; i++) {
					
//					Note note = new Note(i, 1.0);
//					System.out.print(note.getNote());
					System.out.print(Frequency.makeNoteSymbol(chord[i]) + " ");
					int p = chord[i] % 12;
					noteArray[i] = p;
				}
				System.out.print(Arrays.toString(chord) + "; ");
				System.out.print("dissonantie: " + key + "; " );
				PitchSet set = new PitchSet(noteArray);
				intervalVector = new IntervalVector(set);
				System.out.print("ic vector: " + intervalVector);
				System.out.println();
			}
		}
		
//		try {
//			File file = new File("3PartChordsIntervalChromaticScale.txt");
//			PrintWriter pw = new PrintWriter(file);
//			Set<Double> keys = sonanceMap.keySet();
//			for (Double key : keys) {
//				List<int[]> chords = sonanceMap.get(key);
//				for (int[] chord : chords) {
//					int[] noteArray = new int[3];
//					for (int i = 0; i < chord.length; i++) {
//						pw.print(Frequency.makeNoteSymbol(chord[i]) + " ");
//						int p = chord[i] % 12;
//						noteArray[i] = p;
//					}
//					pw.print("; ");
//					pw.print("diss: " + key + "; " );
//					pw.print(Arrays.toString(chord) + "; ");
//					PitchSet set = new PitchSet(noteArray);
//					intervalVector = new IntervalVector(set);
//					pw.print(intervalVector);
//					pw.println();
//				}
//			}
//			pw.println();
//			pw.flush(); 
//			pw.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} 
		
//		List<Note[]> chords2 = generate2PartChords();
//		List<Note[]> chords3 = generate3PartChords();
//		List<Note[]> chords4 = generate4PartChords();
//		System.out.println("size " + chords3.size());
//		System.out.println(chords4.size());

//		TreeMap<Double, List<Note[]>> dissonanceMap = new TreeMap<Double, List<Note[]>>();
//		for (Note[] notes : chords) {	
//			
//			double dissonanceDegree = Utilities.round(analysechordNotes(notes),3);
////			double dissonanceDegree = analysechordNotes(notes);
////			double tonalDegree = getTonalValueChord(notes);
////			double dissonanceDegree = (dissDegree + tonalDegree) / 2;
//			List<Note[]> ch = dissonanceMap.get(dissonanceDegree);
//			if(ch == null){
//				dissonanceMap.put(dissonanceDegree, ch = new ArrayList<Note[]>());
//			}
//			ch.add(notes);
//		}
//		
//		Set<Double> keys = dissonanceMap.keySet();
//		int i = 1;
//		try {
//			File file = new File("4PartChords.txt");
//			PrintWriter pw = new PrintWriter(file);
//				for (Double diss : keys) {
//					List<Note[]> chds = dissonanceMap.get(diss);
//					System.out.println(i);
//					pw.println(i);
//					for (Note[] notes : chds) {
//						for (Note note: notes) {
//							System.out.print(note.getNote());
//							pw.print(note.getNote());
//						}
//						System.out.print(" diss: " + diss);
//						pw.print(" diss: " + diss);
//						Note root = getRootChord(notes);
//						System.out.print(" root: " + root.getNote());
//						pw.print(" root: " + root.getNote());
////						System.out.print(" cat: " + getChordCategory(notes));
////						pw.print(" cat: " + getChordCategory(notes));
//						if(containsInterval(notes, Interval.TRITONE)){
//							System.out.print(" tritone ");
//							pw.print(" tritone ");
//						}
//						if(containsInterval(notes, Interval.GROTE_SECONDE)){
//							System.out.print(" grote seconde ");
//							pw.print(" grote seconde ");
//						}
//						if(containsInterval(notes, Interval.KLEINE_SECONDE)){
//							System.out.print(" kleine secunde ");
//							pw.print(" kleine secunde ");
//						}
//						System.out.println();
//						pw.println();
//					}
//					i++;
//					System.out.println();
//				}
//			pw.println();
//			pw.flush(); 
//			pw.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} 
//
//		

		
//		readMidi();
//		Note[] chord = new Note[7];
//		chord[0] =	new Note(C4,1.0);
//		chord[1] =	new Note(E5,1.0);
//		chord[2] =	new Note(G5,1.0);
////		chord[3] =	new Note(C4,1.0);
//		chord[3] =	new Note(B6,1.0);
////		chord[4] =	new Note(D5,1.0);
////		chord[5] =	new Note(F5,1.0);
////		chord[6] =	new Note(FS5,1.0);
//		System.out.println(overmatigeDrieklank(chord));
//		System.out.println(kwartenAkkoord(chord));
//		System.out.print("intervallen: ");
//		double dissonanceDegree = analysechordNotesNoOcatves(chord);
////		double dissonanceDegree = getTonalValueChord(chord);
//		System.out.println(" degree = " + dissonanceDegree);
//		
//		Note root = getRootChord(chord);
//		System.out.println(root.getNote());
		
//		NavigableSet<Integer> chordNotes = new TreeSet<Integer>();
//		chordNotes.add(0);
//		chordNotes.add(4);
////		chordNotes.add(5);
//		chordNotes.add(7);
////		chordNotes.add(6);
////		chordNotes.add(11);
//		NavigableSet<Integer> chordNotes2 = new TreeSet<Integer>();
//		chordNotes2.add(0);
//		chordNotes2.add(4);
////		chordNotes2.add(5);
//		chordNotes2.add(7);
////		chordNotes2.add(6);
////		chordNotes2.add(11);
//		
//		double[] out = analyzeChord(chordNotes);
//		double dissDegree2 = analyzechordNotesNoDoubles(chordNotes2);
////		double dissonanceDegree = getTonalValueChord(chord);
//		System.out.println(" degree = " + out[0]/out[1]);
//		System.out.println(" degree2 = " + dissDegree2);
		
		
		
		
//		Map<Double, List<int[]>> sonanceMap = new TreeMap<Double, List<int[]>>();
//		IntervalVector intervalVector = null;
//		
//		HarmonyAnalyzer analyzer = new HarmonyAnalyzer();
//		for (Note[] notes : chords4) {
//			int[] noteArray = new int[4];
//			NavigableSet<Integer> notesSet = new TreeSet<Integer>();
//			for (int i = 0; i < notes.length; i++) {
//				int p = notes[i].getPitch() % 12;
//				noteArray[i] = p;
//				notesSet.add(p);
//			}
//			PitchSet set = new PitchSet(noteArray);
//			intervalVector = new IntervalVector(set);
////			double dissDegree = calculateHarmonicValue(intervalVector);
////			double dissDegree = analyzechordNotesNoDoubles(notesSet);
//			double dissDegree = analyzeChord(noteArray);
////			analyzer.analyzeHarmony(noteArray);
////			System.out.println("sonance: " + analyzer.getSonance());
////			System.out.println("root: " + analyzer.getRoot());
////			System.out.println(analyzer);
////			double dissDegree = analyzer.getSonance();
//			dissDegree = Utilities.round(dissDegree, 3);
//			if (sonanceMap.containsKey(dissDegree)) {
//				List<int[]> chordList = sonanceMap.get(dissDegree);
//				chordList.add(noteArray);
//				sonanceMap.put(dissDegree, chordList);
//			} else {
//				List<int[]> chordList = new ArrayList<int[]>();
//				chordList.add(noteArray);
//				sonanceMap.put(dissDegree, chordList );
//			}
////			System.out.println(set.toKeys());
////			System.out.println(intervalVector);
////			System.out.println("intervalVector value: " + val);
////			System.out.println("dissonant value: " + dissDegree);
////			ForteNumber forteNumber = new ForteNumber(set);
////			System.out.println(forteNumber);
//		}
//		
//		Set<Double> keys = sonanceMap.keySet();
//		for (Double key : keys) {
//			List<int[]> chords = sonanceMap.get(key);
//			for (int[] chord : chords) {
//				
//				for (int i : chord) {
//					Note note = new Note(i, 1.0);
//					System.out.print(note.getNote());
//				}
//				System.out.print("; ");
//				System.out.print("diss: " + key + "; " );
//				System.out.print(Arrays.toString(chord) + "; ");
//				System.out.println();
//			}
//		}
		
//		try {
//		File file = new File("3PartChordsInterval.txt");
//		PrintWriter pw = new PrintWriter(file);
//		Set<Double> keys = sonanceMap.keySet();
//		for (Double key : keys) {
//			List<int[]> chords = sonanceMap.get(key);
//			for (int[] chord : chords) {
//				for (int i : chord) {
//					Note note = new Note(i, 1.0);
//					pw.print(note.getNote());
//				}
//				PitchSet set = new PitchSet(chord);
//				intervalVector = new IntervalVector(set);
//				pw.print("; ");
//				pw.print("diss: " + key + "; " );
//				pw.print("< " + intervalVector + " >" + "; ");
//				pw.print(Arrays.toString(chord) + "; ");
//				pw.println();
//			}
//		}
//		pw.println();
//		pw.flush(); 
//		pw.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
	}
	
	public static double analyzechordNotesNoOcatves(Note[] chord){
		double sum = 0;
		int intervalCount = 0;
//		double weight = 1.0;
		for (int j = 0; j < chord.length - 1; j++) {
			for (int i = j + 1; i < chord.length; i++) {
				Note note = chord[j];
				Note note2 = chord[i];
				if(note2 == null){
					break;
				}			
				int difference = (note2.getPitch() - note.getPitch())%12;
				Interval interval = Utilities.getEnumInterval(difference);
//				System.out.print(interval + ": "+ (interval.getHarmonicValue() + (octavesWeight * octaves))  + "," );
				sum = sum + (interval.getHarmonicValue()); // * weight : every interval is equal in weight!
				intervalCount++;
			}
//			weight = weight - 0.2;
		}
//		System.out.print("sum :" + sum + "intervalcount: " + intervalCount);
		return sum/intervalCount; 
	}
	
	public static double analyzechordNotesOcatves(Note[] chord){
		double sum = 0;
		int intervalCount = 0;
//		double weight = 1.0;
		int octaves = 0;
		double octavesWeight = 0.1;
		for (int j = 0; j < chord.length - 1; j++) {
			for (int i = j + 1; i < chord.length; i++) {
				Note note = chord[j];
				Note note2 = chord[i];
				if(note2 == null){
					break;
				}
				int diff = note2.getPitch() - note.getPitch();
				//value of interval increases if in higher octave
				octaves = Math.round(diff/12);
				int intervalValue = diff % 12;
				Interval interval = Utilities.getEnumInterval(intervalValue);
//				System.out.print(interval + ": "+ (interval.getHarmonicValue() + (octavesWeight * octaves))  + "," );
				sum = sum + ((interval.getHarmonicValue() + (octavesWeight * octaves)) ); // * weight : every interval is equal in weight!

				intervalCount++;
			}
//			weight = weight - 0.2;
		}
//		System.out.print("sum :" + sum + "intervalcount: " + intervalCount);
		return sum/intervalCount; 
	}
	
	public static double analyzechordNotesNoDoubles(Set<Integer> chordNotes){
		double sum = 0;
		int intervalCount = 0;
		//add bass note 0;
		chordNotes.add(0);
		Integer[] chord = chordNotes.toArray(new Integer[chordNotes.size()]);
		for (int j = 0; j < chord.length - 1; j++) {
			for (int i = j + 1; i < chord.length; i++) {
				Integer note = chord[j];
				Integer note2 = chord[i];
				if(note2 == null){
					break;
				}			
				int difference = note2 - note;
				Interval interval = Utilities.getEnumInterval(difference);
//				System.out.println(interval);
				sum = sum + (interval.getHarmonicValue()); 
				intervalCount++;
			}
		}
		return sum/intervalCount; 
	}
	
	public static double analyzeChord(int[] chord){
		double sum = 0;
		int intervalCount = 0;
		for (int j = 0; j < chord.length - 1; j++) {
			for (int i = j + 1; i < chord.length; i++) {
				Integer note = chord[j];
				Integer note2 = chord[i];
				if(note2 == null){
					break;
				}			
				int difference = (note2 - note) % 12;
				Interval interval = Utilities.getEnumInterval(difference);
//				System.out.println(interval);
				sum = sum + (interval.getHarmonicValue()); 
				intervalCount++;
			}
		}
		return sum/intervalCount; 
	}
	
	public static double[] analyzeChordStat(int[] chord){
		int amountOfIntervals = (chord.length * (chord.length - 1 ))/2;
		double[] sum = new double[amountOfIntervals];
		int intervalCount = 0;
		for (int j = 0; j < chord.length - 1; j++) {
			for (int i = j + 1; i < chord.length; i++) {
				Integer note = chord[j];
				Integer note2 = chord[i];
				if(note2 == null){
					break;
				}			
				int difference = (note2 - note) % 12;
				Interval interval = Utilities.getEnumInterval(difference);
//				System.out.println(interval);
				sum[intervalCount] = interval.getHarmonicValue(); 
				intervalCount++;
			}
		}
		return sum; 
	}
	
//	public static double analyzeChord2(int[] chord){
//		double sum = 0;
//		for (int j = 0; j < chord.length; j++) {
//			for (int i = 0; i < chord.length; i++) {
////				System.out.println(j + "," +  i);
//				Integer note = chord[j];
//				Integer note2 = chord[i];			
//				int difference = (note2 - note) % 12;
//				Interval interval = Utilities.getEnumInterval(difference);
////				System.out.println("value " + interval.getHarmonicValue());
//				sum = sum + (interval.getHarmonicValue()); 
//			}
//		}
//		return sum/(2 * chord.length);
//	}
	
	/**
	 * Analyze chord notes
	 * @param chordNotes
	 * @return output[0] = total harmony intervals; output[1] is interval count
	 */
	public static double[] analyzeChord(NavigableSet<Integer> chordNotes){
		if(chordNotes.size() == 1){
			return new double[2];
		}
		double[] output = new double[2];
		Integer rootNoteNumber = chordNotes.pollFirst();
		for (Integer noteNumber : chordNotes) {
			int difference = noteNumber - rootNoteNumber;
			Interval interval = Utilities.getEnumInterval(difference);
			System.out.println(interval);
			output[0] = output[0] + interval.getHarmonicValue(); 
			output[1]++;
		}
		//recursion	
		double[] result = analyzeChord(chordNotes);
		output[0] = output[0] + result[0]; 
		output[1] = output[1] + result[1];
		return output;
	}
	
	public static double getTonalValueChord(Note[] chord){
		double sum = 0;
		int intervalCount = 0;
		System.out.println(chord.length);
		for (int j = 1; j < chord.length ; j++) {
			Note note = chord[0];
			Note note2 = chord[j];
			if(note2 == null){
				break;
			}
			int difference = (note2.getPitch() - note.getPitch())%12;
			Interval interval = Utilities.getEnumInterval(difference);
			System.out.print(interval + ": "+  interval.getTonalValue() + "," );

			sum = sum + (interval.getTonalValue());
			intervalCount++;

		}
		System.out.print("sum :" + sum + "intervalcount: " + intervalCount);
		return sum/intervalCount; 
	}


	public static void readMidi() {
		FileDialog fd;
       	Frame f = new Frame();
		//open a MIDI file	               
		fd = new FileDialog(f,"Open MIDI file or choose cancel to finish.",
                  		    FileDialog.LOAD);
	        fd.show();
	      
		Score s = new Score();     
	        Read.midi(s, fd.getDirectory()+fd.getFile());
	        s.setTitle(fd.getFile()); 
//	        View.notate(s);
	        View.show(s);
//	        Play.midi(s);
	        Part[] parts = s.getPartArray();
	        for (Part part : parts) {
				Phrase[] phrases = part.getPhraseArray();
				for (Phrase phrase : phrases) {
					Note[] notes = phrase.getNoteArray();
					for (Note note : notes) {
						System.out.println(note.getNote());
					}
				}
			}
		
	}
	public static List<Note[]> generate3PartChords(){
		Part part1 = new Part();
		CPhrase chord = new CPhrase();
		List<Note[]> chords = new ArrayList<Note[]>();
		int topVoice3 = 11;
		int chordCount = 0;
		for (int j = 1; j < 11; j++) {
			for (int i = 1; i < topVoice3; i++) {
				Note[] noteArray = new Note[3];
				Note voice3 = new Note();
				voice3.setPitch(60 + j + i);
				if(voice3.getPitch() > 71){
					break;
				}
				noteArray[2] = voice3;
				
				
				Note voice2 = new Note();
				voice2.setPitch(60 + j);
				noteArray[1] = voice2;
				
				Note voice1 = new Note();
				voice1.setPitch(60);
				noteArray[0] = voice1;
				
				part1.addCPhrase(chord);
				chordCount++;
				chords.add(noteArray);
				
			}
		}

//		int[] noteArray = new int[2];	
//		CPhrase chord = new CPhrase();
//		
//		noteArray[0]= jm.constants.Pitches.C4;
//		noteArray[1]= jm.constants.Pitches.D4;
//		chord.addChord(noteArray, 1.0);
//		part1.addCPhrase(chord);
//		Part part2 = new Part();
//		part1.add(voice2);
		Score score = new Score();
		score.setTempo(70.0);
		score.addPart(part1);
		return chords;
	
//		View.notate(score);
//		Write.midi(score, "test.mid");
//        Play.midi(score);

	}
	
	public static List<Note[]> generate4PartChords(){
		List<Note[]> chords = new ArrayList<Note[]>();
		int topVoice4 = 11;
		int topVoice3 = 11;
		int topVoice2 = 11;
		for (int j = 1; j < topVoice2; j++) {
			for (int i = 1; i < topVoice3; i++) {
				for (int k = 1; k < topVoice4; k++) {
					Note[] noteArray = new Note[4];
					Note voice4 = new Note();
					voice4.setPitch(60 + j + i + k);
					if(voice4.getPitch() > 71){
						break;
					}
					noteArray[3] = voice4;
					
					Note voice3 = new Note();
					voice3.setPitch(60 + j + i);
					if(voice3.getPitch() > 71){
						break;
					}
					noteArray[2] = voice3;
					
					Note voice2 = new Note();
					voice2.setPitch(60 + j);
					noteArray[1] = voice2;
					
					Note voice1 = new Note();
					voice1.setPitch(60);
					noteArray[0] = voice1;

					chords.add(noteArray);		
				}
			}		
		}
		return chords;
	}

	
	public static Note getRootChord(Note[] chord) {
		Interval bestInterval = Interval.UNISONO;
		Note rootChord = null;
		for (int j = 0; j < chord.length - 1; j++) {
			for (int i = j + 1; i < chord.length; i++) {
				Note firstNote = chord[j];
				Note secondNote = chord[i];
				if(secondNote == null){
					break;
				}
				int difference = (secondNote.getPitch() - firstNote.getPitch())%12;
				Interval interval = Utilities.getEnumInterval(difference);
				if(interval.equals(Interval.KWINT)){
					if (difference < 0) {
						return secondNote;
					} else {
						return firstNote;
					}
				}else{
					if(bestInterval.getSeries2() < interval.getSeries2()){
						bestInterval = interval;
						switch (interval.getInterval()) {
						case 3:
						case 4:
						case 10:
						case 11:
							if (difference < 0) {
								rootChord = secondNote;
							} else {
								rootChord = firstNote;
							}
							break;
						case 5:
						case 8:
						case 9:
						case 1:
						case 2:	
							if (difference < 0) {
								rootChord = firstNote;
							} else {
								rootChord = secondNote;
							}
							break;
						}
					}	
				}
			}
		}
		return rootChord;	
	}
	
	public static Note getRootChord(Integer[] chord) {
		Interval bestInterval = Interval.UNISONO;
		Note rootChord = null;
		for (int j = 0; j < chord.length - 1; j++) {
			for (int i = j + 1; i < chord.length; i++) {
				Integer firstNote = chord[j];
				Integer secondNote = chord[i];
				if(secondNote == null){
					break;
				}
				int difference = (secondNote - firstNote)%12;
				Interval interval = Utilities.getEnumInterval(difference);
				if(interval.equals(Interval.KWINT)){
					if (difference < 0) {
						return new Note(secondNote, 1.0);
					} else {
						return new Note(firstNote, 1.0);
					}
				}else{
					if(bestInterval.getSeries2() < interval.getSeries2()){
						bestInterval = interval;
						switch (interval.getInterval()) {
						case 3:
						case 4:
						case 10:
						case 11:
							if (difference < 0) {
								rootChord =  new Note(secondNote, 1.0);
							} else {
								rootChord = new Note(firstNote, 1.0);
							}
							break;
						case 5:
						case 8:
						case 9:
						case 1:
						case 2:	
							if (difference < 0) {
								rootChord = new Note(firstNote, 1.0);
							} else {
								rootChord = new Note(secondNote, 1.0);
							}
							break;
						}
					}	
				}
			}
		}
		return rootChord;	
	}
	
	public static boolean containsInterval(Note[] chord, Interval containsInterval){
		for (int j = 0; j < chord.length - 1; j++) {
			for (int i = j + 1; i < chord.length; i++) {
				Note firstNote = chord[j];
				Note secondNote = chord[i];
				if(secondNote == null){
					break;
				}
				int difference = (secondNote.getPitch() - firstNote.getPitch())%12;
				Interval interval = Utilities.getEnumInterval(difference);
				if(interval.equals(containsInterval)){
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean containsMoreThanOneOfInterval(Note[] chord, Interval containsInterval){
		int count = 0;
		for (int j = 0; j < chord.length - 1; j++) {
			for (int i = j + 1; i < chord.length; i++) {
				Note firstNote = chord[j];
				Note secondNote = chord[i];
				if(secondNote == null){
					break;
				}
				int difference = (secondNote.getPitch() - firstNote.getPitch())%12;
				Interval interval = Utilities.getEnumInterval(difference);
				if(interval.equals(containsInterval)){
					count++;
					if(count > 1 ){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static boolean containsOneOrMoreIntervals(Note[] chord, List<Interval> containsInterval){
		for (int j = 0; j < chord.length - 1; j++) {
			for (int i = j + 1; i < chord.length; i++) {
				Note firstNote = chord[j];
				Note secondNote = chord[i];
				if(secondNote == null){
					break;
				}
				int difference = (secondNote.getPitch() - firstNote.getPitch())%12;
				Interval interval = Utilities.getEnumInterval(difference);
				for (Interval interv : containsInterval) {
					if(interval.equals(interv)){
						return true;
					}
				}
				
			}
		}
		return false;
	}
	
	public static String getChordCategory(Note[] chord) {
		if(overmatigeDrieklank(chord) || kwartenAkkoord(chord)){
			return "V";
		}
		Note bass = chord[0];
		//B
		if(containsInterval(chord, Interval.TRITONE)){
			List<Interval> intervallen = new ArrayList<Interval>();
			intervallen.add(Interval.GROOT_SEPTIEM);
			intervallen.add(Interval.KLEINE_SECONDE);
			if(!containsOneOrMoreIntervals(chord, intervallen)){
				if(!containsInterval(chord, Interval.GROTE_SECONDE)){
					return "AII.a";
				}else{
					if(containsMoreThanOneOfInterval(chord,Interval.TRITONE)){
						return "AII.b3";
					}else{
						if(bass.equals(getRootChord(chord))){
							return "AII.b1";
						}else{
							return "AII.b2";
						}
					}		
				}
			}else{
				if(bass.equals(getRootChord(chord))){
					return "AIV.1";
				}else{
					return "AIV.2";
				}
			}
		}
		//A
		else{
			//III
			List<Interval> intervallen = new ArrayList<Interval>();
			intervallen.add(Interval.GROOT_SEPTIEM);
			intervallen.add(Interval.GROTE_SECONDE);
			intervallen.add(Interval.KLEIN_SEPTIEM);
			intervallen.add(Interval.KLEINE_SECONDE);
			if(containsOneOrMoreIntervals(chord, intervallen)){
				if(bass.equals(getRootChord(chord))){
					return "AIII.1";
				}else{
					return "AIII.2";
				}
			}
			//I
			else{
				if(bass.equals(getRootChord(chord))){
					return "AI.1";
				}else{
					return "AI.2";
				}
			}
		}
	}
	
	public static boolean overmatigeDrieklank(Note[] chord){
		for (int j = 0; j < chord.length - 1 ; j++) {
			Note firstNote = chord[j];
			Note secondNote = chord[j + 1];
			if(secondNote == null){
				break;
			}
			int difference = (secondNote.getPitch() - firstNote.getPitch())%12;
			Interval interval = Utilities.getEnumInterval(difference);
			if(!interval.equals(Interval.GROTE_TERTS)){
				return false;
			}
		}
		return true;
	}
	
	public static boolean kwartenAkkoord(Note[] chord){
		for (int j = 0; j < chord.length - 1; j++) {
			Note firstNote = chord[j];
			Note secondNote = chord[j + 1];
			if(secondNote == null){
				break;
			}
			int difference = (secondNote.getPitch() - firstNote.getPitch())%12;
			Interval interval = Utilities.getEnumInterval(difference);
			if(!interval.equals(Interval.KWART)){
				return false;
			}
		}
		return true;
	}
	
	public static List<Double> harmonicFluctuationDelta(List<Double> harmonicValues){
		List<Double> harmonicFluctuationDelta = new ArrayList<Double>();
		int len = harmonicValues.size();
		for (int i = 0; i < len - 1; i++) {
			Double first = harmonicValues.get(i);
			Double sec = harmonicValues.get(i + 1);
			double diff = sec - first;
			harmonicFluctuationDelta.add(diff);
		}
		return harmonicFluctuationDelta;
	}
	
	public static List<Integer> harmonicFluctuationContour(List<Double> harmonicValues){
		List<Integer> harmonicFluctuationContour = new ArrayList<Integer>();
		int len = harmonicValues.size();
		for (int i = 0; i < len - 1; i++) {
			Double first = harmonicValues.get(i);
			Double sec = harmonicValues.get(i + 1);
			double diff = sec - first;
			if(diff < 0){
				harmonicFluctuationContour.add(-1);
			}else if (diff == 0) {
				harmonicFluctuationContour.add(0);
			} else {
				harmonicFluctuationContour.add(1);
			}
		}
		return harmonicFluctuationContour;
	}
	
	public static double calculateHarmonicValue(IntervalVector intervalVector){
		double sum = 0;
		int intervalCount = 0;
		int[] intervalCounts = intervalVector.intervalVector;
		for (int i = 0; i < intervalCounts.length; i++) {
			Interval interval = Utilities.getEnumInterval(i + 1);
			for (int j = 0; j < intervalCounts[i]; j++) {
				sum = sum + (interval.getHarmonicValue());
				intervalCount++;
			}
		}
		return sum/intervalCount; 	
	}
	
	public static List<Note[]> generate2PartChords(){
		List<Note[]> chords = new ArrayList<Note[]>();
		for (int j = 1; j < 12; j++) {
			Note[] noteArray = new Note[2];
			Note voice2 = new Note();
			voice2.setPitch(60 + j);
			noteArray[1] = voice2;
			
			Note voice1 = new Note();
			voice1.setPitch(60);
			noteArray[0] = voice1;
			chords.add(noteArray);
		}
		return chords;
	}
	
	public static List<int[]> generate3PartChordsForScale(int[] scale , int firstPitch, int index){
		List<int[]> chords = new ArrayList<int[]>();
		int m = 0;
		int j = 1;
		for (; j < scale.length;j++) {
			m = j + 1;
			for (; m < scale.length;m++) {
				int[] chord = new int[3];
				chord[0] = firstPitch + scale[index];
				chord[1] = firstPitch + scale[(j + index) % scale.length];
				if (chord[1] < chord[0]) {
					chord[1] = chord[1] + 12;
				}
				chord[2] = firstPitch + scale[(m + index) % scale.length];
				if (chord[2] < chord[1]) {
					chord[2] = chord[2] + 12;
				}
				chords.add(chord);
			}
		}
		return chords;
	}
	
	public static List<int[]> generateAll3PartClosedChordsForScale(int[] scale, int startingPitch){
		List<int[]> chords = new ArrayList<int[]>();
		for (int i = 0; i < scale.length; i++) {
			List<int[]> chordsForPitchClass = generate3PartChordsForScale(scale ,startingPitch, i);
			chords.addAll(chordsForPitchClass);
		}
		return chords;
	}
	
	public static List<int[]> generate4PartChordsForScale(int[] scale , int firstPitch, int index){
		List<int[]> chords = new ArrayList<int[]>();
		int m = 0;
		int k = 1;
		int j = 2;
		for (; k < scale.length;k++) {
			j = k + 1;
			m = j + 1;
			for (; j < scale.length;j++) {
				m = j + 1;
				for (; m < scale.length;m++) {
					int[] chord = new int[4];
					chord[0] = firstPitch + scale[index];
					chord[1] = firstPitch + scale[(k + index) % scale.length];
					if (chord[1] < chord[0]) {
						chord[1] = chord[1] + 12;
					}
					chord[2] = firstPitch + scale[(j + index) % scale.length];
					if (chord[2] < chord[1]) {
						chord[2] = chord[2] + 12;
					}
					chord[3] = firstPitch + scale[(m + index) % scale.length];
					if (chord[3] < chord[2]) {
						chord[3] = chord[3] + 12;
					}
					chords.add(chord);
				}
			}
		}
		return chords;
	}
	
	public static List<int[]> generateAll4PartClosedChordsForScale(int[] scale, int startingPitch){
		List<int[]> chords = new ArrayList<int[]>();
		for (int i = 0; i < scale.length; i++) {
			List<int[]> chordsForPitchClass = generate4PartChordsForScale(scale ,startingPitch, i);
			chords.addAll(chordsForPitchClass);
		}
		return chords;
	}
}
