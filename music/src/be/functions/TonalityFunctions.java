package be.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;

import org.apache.commons.math.stat.correlation.PearsonsCorrelation;

import be.data.MusicalStructure;
import be.data.NotePos;
import be.util.Generator;
import be.util.ScoreUtilities;
import be.util.TestContrapunt;
import be.util.Utilities;

public class TonalityFunctions {
	
	private static Logger LOGGER = Logger.getLogger(TonalityFunctions.class.getName());
	public static final double[] vectorMajorTemplate;
	public static final double[] vectorMinorTemplate;
	
	static{
		vectorMajorTemplate = new double[12];
		vectorMajorTemplate[0] = 6.35;
		vectorMajorTemplate[1] = 2.23;
		vectorMajorTemplate[2] = 3.48;
		vectorMajorTemplate[3] = 2.33;
		vectorMajorTemplate[4] = 4.38;
		vectorMajorTemplate[5] = 4.09;
		vectorMajorTemplate[6] = 2.52;
		vectorMajorTemplate[7] = 5.19;
		vectorMajorTemplate[8] = 2.39;
		vectorMajorTemplate[9] = 3.66;
		vectorMajorTemplate[10] = 2.29;
		vectorMajorTemplate[11] = 2.88;
		
		vectorMinorTemplate = new double[12];
		vectorMinorTemplate[0] = 6.33;
		vectorMinorTemplate[1] = 2.68;
		vectorMinorTemplate[2] = 3.52;
		vectorMinorTemplate[3] = 5.38;
		vectorMinorTemplate[4] = 2.60;
		vectorMinorTemplate[5] = 3.53;
		vectorMinorTemplate[6] = 2.54;
		vectorMinorTemplate[7] = 4.75;
		vectorMinorTemplate[8] = 3.98;
		vectorMinorTemplate[9] = 2.69;
		vectorMinorTemplate[10] = 3.34;
		vectorMinorTemplate[11] = 3.17;
	}
	
	public static void main(String[] args) {
//		Score s = ReadMidi.readMidi();
//		View.histogram(s);
//		System.out.println("major:");
//		System.out.println(checkMajorTonality(s));
//		System.out.println("minor:");
//		System.out.println(checkMinorTonality(s));
//		double[] melody = new double[12];
//		melody[0] = 635;
//		melody[1] = 223;
//		melody[2] = 348;
//		melody[3] = 233;
//		melody[4] = 438;
//		melody[5] = 409;
//		melody[6] = 252;
//		melody[7] = 519;
//		melody[8] = 239;
//		melody[9] = 366;
//		melody[10] = 229;
//		melody[11] = 288;
//		System.out.println(getPearsonCorrelation(vectorMajorTemplate, melody));
//		
//		Note[] chord1 = new Note[3];
//		chord1[0] = new Note(60, 0.5);
//		chord1[1] = new Note(64, 0.5);
//		chord1[2] = new Note(67, 0.5);
//		double total = 0;
//		for (Note note : chord1) {
//			double percent = 100;
//			double registerValue = 1 - (note.getPitch() / percent);
//			double rhythmValue = note.getRhythmValue();
//			System.out.println(rhythmValue * registerValue);
//			total = total + (rhythmValue * registerValue);
//		}
//		System.out.println("total = " + total);
		
		List<Note[]> melodies = TestContrapunt.tonalityRegisterTest();
		List<MusicalStructure> sentences = Generator.getInstance().extractNotePositions(melodies);
		Score s = ScoreUtilities.createScore(sentences, null);
		double tonality = getMaxCorrelationTonality(sentences, vectorMajorTemplate);
		System.out.println("Tonality: " + tonality);
//		View.notate(s);
//		View.histogram(s);
//		Play.midi(s);
	}

	public static Integer checkMajorTonality(Note[] melody){
		double[] melodyVector = createVector(melody);
		return getTonality(vectorMajorTemplate, melodyVector);
	}
	
	public static Integer checkMinorTonality(Note[] melody){
		double[] melodyVector = createVector(melody);
		return getTonality(vectorMinorTemplate, melodyVector);
	}

	private static Integer getTonality(double[] template, double[] melodyVector) {
		System.out.println(Arrays.toString(melodyVector));
		Map<Integer,Double> map =  getCorrelationMap(template, melodyVector);
		Double max = getMaximumCorrelation(template, melodyVector);
		for (Integer key : map.keySet()) {
			Double corr= map.get(key);
			if (corr.equals(max)) {
				return key;
			}
		}
		return null;
	}
	
	public static Integer checkMajorTonality(Score score){
		double[] melodyVector = createVector(score);
//		System.out.println("vector: " + Arrays.toString(melodyVector));
		return getTonality(vectorMajorTemplate, melodyVector);
	}
	
	public static double getMaxCorrelationTonality(MusicalStructure structure, double[] template){
		List<NotePos> notePositions = structure.getNotePositions();
		double[] durationVector = new double[12];
		for (NotePos note : notePositions) {
			int pitchClass = note.getPitch()%12;
			durationVector[pitchClass] = durationVector[pitchClass] + note.getRhythmValue();
		}
//		System.out.println(Arrays.toString(durationVector));
		Map<Integer,Double> map =  getCorrelationMap(template, durationVector);
		Double max = getMaximumCorrelation(template, durationVector);
		return max;
		
	}
	
	public static double getMaxCorrelationTonality(List<MusicalStructure> structures, double[] template, double[] context){
		double[] durationVector = new double[12];
		for (MusicalStructure musicalStructure : structures) {
			List<NotePos> notePositions = musicalStructure.getNotePositions();
			for (NotePos note: notePositions) {
				if (!note.isRest()) {
					int pitchClass = note.getPitch()%12;
//					System.out.print("note:" + notePositions[i].getNote().getNote() + ",");
//					System.out.print("value:" + (notePositions[i].getNote().getRhythmValue() * context[notePositions[i].getPosition()]) + ", ");
//					System.out.println("context:" + context[notePositions[i].getPosition()]);
					double registerValue = Utilities.calculateRegisterValue(note.getPitch());
					double rhythmValue = note.getRhythmValue();
					durationVector[pitchClass] = durationVector[pitchClass] 
					          + (rhythmValue * context[note.getPosition()]) * registerValue;
				}
			}
		}
		LOGGER.finest("Tonality vector: " + Arrays.toString(durationVector));
//		Map<Integer,Double> map =  getCorrelationMap(template, durationVector);
		Double max = getMaximumCorrelation(template, durationVector);
		return max;
		
	}
	
	public static double getMaxCorrelationTonality(List<MusicalStructure> structures, double[] template){
		double[] durationVector = new double[12];
		for (MusicalStructure musicalStructure : structures) {
			List<NotePos> notePositions = musicalStructure.getNotePositions();
			for (int i = 0; i < notePositions.size(); i++) {
				NotePos note = notePositions.get(i);
				if (!note.isRest()) {
					int pitchClass = note.getPitch()%12;
//					System.out.print("note:" + notePositions[i].getNote().getNote() + ",");
//					System.out.print("value:" + (notePositions[i].getNote().getRhythmValue() * context[notePositions[i].getPosition()]) + ", ");
//					System.out.println("context:" + context[notePositions[i].getPosition()]);
					double percent = 100;
					double registerValue = 1 - (note.getPitch() / percent);
					double rhythmValue = note.getLength();
					double weight = note.getPositionWeight() * note.getInnerMetricWeight();
					durationVector[pitchClass] = durationVector[pitchClass] + (rhythmValue * registerValue * weight);
				}
			}
		}
		LOGGER.finer(Arrays.toString(durationVector));
//		Map<Integer,Double> map =  getCorrelationMap(template, durationVector);
		Double max = getMaximumCorrelation(template, durationVector);
		return max;
		
	}
	
	public static Integer checkMinorTonality(Score score){
		double[] melodyVector = createVector(score);
		
		return getTonality(vectorMinorTemplate, melodyVector);
	}
	
	private static double[] createVector(Note[] melody){
		double[] durationVector = new double[12];
		for (int i = 0; i < melody.length; i++) {
			int pitchClass = melody[i].getPitch()%12;
			durationVector[pitchClass] = durationVector[pitchClass] + melody[i].getRhythmValue();
		}
		return durationVector;
	}
	
	private static double[] createVector(Score score){
		double[] durationVector = new double[12];
		Part[] parts = score.getPartArray();
		for (Part part : parts) {
			Phrase[] phrases = part.getPhraseArray();
			for (int j = 0; j < phrases.length; j++) {
//				System.out.println(phrases[j]);
				Note[] melody = phrases[j].getNoteArray();
				for (int i = 0; i < melody.length; i++) {
					int pitchClass = melody[i].getPitch()%12;
					durationVector[pitchClass] = durationVector[pitchClass] + melody[i].getRhythmValue();
				}
			}
		}
		return durationVector;
	}
	
	private static Double getMaximumCorrelation(double[] vectorScale,
			double[] vectorWeights) {
		List<Double> correlations = new ArrayList<Double>();
		correlations.add(getPearsonCorrelation(vectorScale, vectorWeights));
		for (int i = 1; i < vectorWeights.length; i++) {
			Utilities.rotate(vectorWeights);
			correlations.add(getPearsonCorrelation(vectorScale, vectorWeights));
		}
//		System.out.println("correlations: " + correlations);
		Double maximumCorrelation = Collections.max(correlations);
		return maximumCorrelation;
	}
	
	private static Map<Integer,Double> getCorrelationMap(double[] vectorScale,double[] vectorWeights) {
		Map<Integer,Double> correlations = new TreeMap<Integer,Double>();
		correlations.put(60, getPearsonCorrelation(vectorScale, vectorWeights));
		for (int i = 1; i < vectorWeights.length; i++) {
			Utilities.rotate(vectorWeights);
			correlations.put(60 + i, getPearsonCorrelation(vectorScale, vectorWeights));
		}
		return correlations;
	}
	
	
	
	private static double getPearsonCorrelation(double[] vectorTemplate ,double[] melody){
		return new PearsonsCorrelation().correlation(vectorTemplate,melody);
	}
	
	public static double getCorrelation(Score score, double[] template){
		double[] melodyVector = createVector(score);
		System.out.println(Arrays.toString(melodyVector));
		Double max = getMaximumCorrelation(template, melodyVector);
		return max;
	}

}
