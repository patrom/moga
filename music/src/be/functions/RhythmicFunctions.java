package be.functions;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;

import jm.constants.Durations;
import jm.constants.Instruments;
import jm.constants.RhythmValues;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.View;

import org.apache.commons.lang.ArrayUtils;

import be.analyzer.ChronotonicSimilarity;

public class RhythmicFunctions {

	private static Logger LOGGER = Logger.getLogger(RhythmicFunctions.class.getName());
	
	private static final int CONCAT = 3;
	private static	Random random = new Random();
	
	public static void main(String[] args) {
		
		
//		int[] profile = {0,0,10,0, 0};
//		double[] pattern = getRhythmPatternProfile(profile, 4);
//		System.out.println(Arrays.toString(pattern));
//		playRhythm(pattern);
//		Part part1 = new Part();
//		Phrase phrase = new Phrase();
//		for (int i = 0; i < 10; i++) {
//			double rhythm = getRhythm();
//			Note note = new Note(C4, rhythm);
//			phrase.add(note);
//		}
//		part1.add(phrase);
//		Score score = new Score();
//		score.setTempo(100.0);
//		score.addPart(part1);
//		View.notate(score);
//		Write.midi(score, "test.mid");
//		Play.midi(score);
		
//		Phrase phrase = new Phrase();
//		phrase.add(new Note(72,RhythmValues.QUARTER_NOTE));
//		phrase.add(new Note(70,RhythmValues.QUARTER_NOTE));
//		phrase.add(new Note(60,RhythmValues.QUARTER_NOTE));
////		phrase.addRest(new Rest(1.0));
//		phrase.add(new Note(62,RhythmValues.HALF_NOTE));
//		phrase.add(new Note(70,RhythmValues.HALF_NOTE));
//		phrase.add(new Note(70,RhythmValues.QUARTER_NOTE));
//		phrase.add(new Note(60,RhythmValues.HALF_NOTE));
//		phrase.add(new Note(72,RhythmValues.QUARTER_NOTE));
//		
//		List<Integer> list = getRhythmValueChanges(phrase);
//		for (Integer integer : list) {
//			System.out.print(integer + ", ");
//		}
		
//		double[] pattern = createRandomRhythmPattern(8);
//		for (int i = 0; i < pattern.length; i++) {
//			System.out.print(pattern[i] + ", ");
//		}
		
//		double[] pattern = createRandomRhythmPattern2(4);
//		System.out.println(Arrays.toString(pattern));
//		RhythmicFunctions.playRhythm(pattern);

//		double[] repeatPattern = repeatPattern(pattern, 3);
//		System.out.println(Arrays.toString(repeatPattern));
//		double[] d = new double[10];
//		for (int i = 0; i < 10; i++) {
//			d[i] = getRhythmProbability();
//		}
//		System.out.println(Arrays.toString(d));
		
//		double[] pattern =  createFixedLengthRhythmPattern(4.0);
//		System.out.println(Arrays.toString(pattern));
//		double[] melody = generateSimilarRhythmWithChronotonicSimiliraty(pattern, 0.7, 4.0);
//		System.out.println(Arrays.toString(melody));
	}
	
	public static double calculateMetricCoherenceValue(Map<Integer, Double> innerMetric, int numerator){
//		double[] extendedRhythmArray = extendArray(rArray);
//		Map<Integer, Double> innerMetric = InnerMetricWeight.getNormalizedInnerMetricWeight(extendedRhythmArray, 0.25);//pulse 0.25
		double onbeatValue = 0;
		double offbeatValue = 0;
		int beat;
//		if (ArrayUtils.contains(extendedRhythmArray, 0.25)) {
//			beat = 2;
//		} else {
			
//		}
		if (numerator == 6 || numerator == 9 ) {// 6/8 or 9/8
			beat = 18;// 3 * 6
		} else if(numerator == 3){// 3/4
			beat = 36;//3 * 12
		}else {
			beat = numerator;
		}
		if (!innerMetric.isEmpty()) {
			Set<Integer> keys = innerMetric.keySet();
			for (Integer integer : keys) {
				if (integer % beat == 0) {
					onbeatValue = onbeatValue + innerMetric.get(integer);
				}else{
					offbeatValue = offbeatValue + innerMetric.get(integer);
				}
			}
		}
		LOGGER.finer("beat value: " + beat + ", inner metric map:" + innerMetric.toString());
		LOGGER.finer("on beat value: " + onbeatValue + ", off beat value: " + offbeatValue);
		if (onbeatValue != 0.0) {
			return offbeatValue/onbeatValue;
		} else {
			return Double.MAX_VALUE;
		}
	}
	
	private static double[] extendArray(double[] rhythmArray) {
		int length = rhythmArray.length * 3;
		double[] rArray = new double[length];
		for (int i = 0; i < length; i++) {
			rArray[i] = rhythmArray[i % rhythmArray.length];
		}
		return rArray;
	}

	
	public static double[] getRhythmPattern() {
		Random random = new Random();
		int i = random.nextInt(8);
		switch (i) {
		case 0:
			double[] rhythmPattern1 = {0.5, 0.5, 0.5, 0.5};
			return rhythmPattern1;
		case 1:
			double[] rhythmPattern2 = {0.5, 0.5, 1.0};
			return rhythmPattern2;
		case 2:
			double[] rhythmPattern3 = {1.0, 0.5, 0.5};
			return rhythmPattern3;
		case 3:
			double[] rhythmPattern4 = {0.5, 1.0, 0.5};
			return rhythmPattern4;
		case 4:
			double[] rhythmPattern5 = {0.5, 1.5};
			return rhythmPattern5;
		case 5:
			double[] rhythmPattern6 = {1.5, 0.5};
			return rhythmPattern6;
		case 6:
			double[] rhythmPattern7 = {1.0};
			return rhythmPattern7;
		case 7:
			double[] rhythmPattern8 = {0.5, 0.5};
			return rhythmPattern8;
		}
		return null;
	}
	
	public static double[] getRhythmPatternSixteenNote() {
		Random random = new Random();
		int i = random.nextInt(23);
		switch (i) {
		case 0:
			double[] rhythmPattern1 = {0.5, 0.5, 0.5, 0.5};
			return rhythmPattern1;
		case 1:
			double[] rhythmPattern2 = {0.5, 0.5, 1.0};
			return rhythmPattern2;
		case 2:
			double[] rhythmPattern3 = {1.0, 0.5, 0.5};
			return rhythmPattern3;
		case 3:
			double[] rhythmPattern4 = {0.5, 1.0, 0.5};
			return rhythmPattern4;
		case 4:
			double[] rhythmPattern5 = {0.5, 1.5};
			return rhythmPattern5;
		case 5:
			double[] rhythmPattern6 = {1.5, 0.5};
			return rhythmPattern6;
		case 6:
			double[] rhythmPattern7 = {Durations.HALF_NOTE};
			return rhythmPattern7;
		case 7:
			double[] rhythmPattern8 = {Durations.WHOLE_NOTE};
			return rhythmPattern8;
		case 8:
			double[] rhythmPattern9 = {Durations.DOTTED_HALF_NOTE};
			return rhythmPattern9;
		case 9:
			double[] rhythmPattern10 = {Durations.QUARTER_NOTE};
			return rhythmPattern10;
		case 10:
			double[] rhythmPattern11 = {Durations.EIGHTH_NOTE};
			return rhythmPattern11;
		case 11:
			double[] rhythmPattern12 = {Durations.SIXTEENTH_NOTE};
			return rhythmPattern12;
		case 12:
			double[] rhythmPattern13 = {0.25, 0.25, 0.25, 0.25};
			return rhythmPattern13;
		case 13:
			double[] rhythmPattern14 = {0.25, 0.25, 0.5};
			return rhythmPattern14;
		case 14:
			double[] rhythmPattern15 = {0.5, 0.25, 0.25};
			return rhythmPattern15;
		case 15:
			double[] rhythmPattern16 = {0.25, 0.5, 0.25};
			return rhythmPattern16;
		case 16:
			double[] rhythmPattern17 = {0.25, 0.75};
			return rhythmPattern17;
		case 17:
			double[] rhythmPattern18 = {0.75, 0.25};
			return rhythmPattern18;
		case 18:
			double[] rhythmPattern19 = {Durations.DOTTED_SIXTEENTH_NOTE};
			return rhythmPattern19;
		case 19:
			double[] rhythmPattern20 = {0.25, 0.25};
			return rhythmPattern20;
		case 20:
			double[] rhythmPattern21 = {0.25, 0.25, 0.25};
			return rhythmPattern21;
		case 21:
			double[] rhythmPattern22 = {0.5, 0.5};
			return rhythmPattern22;
		case 22:
			double[] rhythmPattern23 = {0.5, 0.5, 0.5};
			return rhythmPattern23;
		}
		return null;
	}
	

	public static double getRhythm() {
		Random random = new Random();
		int i = random.nextInt(16);
		switch (i) {
		case 0:
			return Durations.WHOLE_NOTE;
		case 1:
			return Durations.DOTTED_HALF_NOTE;
		case 2:
			return Durations.HALF_NOTE;
		case 3:
			return Durations.DOTTED_QUARTER_NOTE;
		case 4:
			return Durations.QUARTER_NOTE;
		case 5:
			return Durations.EIGHTH_NOTE;
		case 6:
			return Durations.SIXTEENTH_NOTE;
		case 7:
			return Durations.DOTTED_EIGHTH_NOTE;
		case 8:
			return Durations.CDD;
		case 9:
			return Durations.QUARTER_NOTE;
		case 10:
			return Durations.QD;
		case 11:
			return Durations.QT;
		case 12:
			return Durations.SQ;
		case 13:
			return Durations.SQD;
		case 14:
			return Durations.SQT;
		case 15:
			return Durations.DSQ;
		case 16:
			return Durations.DSQT;
		}
		return i;
	}
	
	public static double getRhythm2() {
		Random random = new Random();
		int i = random.nextInt(5);
		switch (i) {
		case 0:
			return Durations.QUARTER_NOTE;
		case 1:
			return Durations.EIGHTH_NOTE;
		case 2:
			return Durations.HALF_NOTE;
		case 3:
			return Durations.DOTTED_QUARTER_NOTE;
		case 4:
			return Durations.QUARTER_NOTE;
		case 5:
			return Durations.EIGHTH_NOTE;
		}
		return i;
	}
	
	public static double getRhythmProbability(){
		double[] rhythmSelections = {Durations.QUARTER_NOTE, Durations.EIGHTH_NOTE, Durations.DOTTED_QUARTER_NOTE, Durations.HALF_NOTE};
		int[] profile = {100,100,10,40};
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
		return rhythmSelections[i];
	}
	
	public static double getChordRhythmProbability(){
		double[] rhythmSelections = {Durations.WHOLE_NOTE, Durations.HALF_NOTE, Durations.QUARTER_NOTE, Durations.DOTTED_HALF_NOTE};
		int[] profile = {100,100,20,40};
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
		return rhythmSelections[i];
	}

	
	public static List<Integer> getRhythmValueChanges(Phrase phrase){
		double[] rhythmArray = phrase.getRhythmArray();
		Map<Double, Integer> map = new TreeMap<Double,Integer>();
		List<Integer> valueChanges = new ArrayList<Integer>();
		for (double rhythm : rhythmArray) {
			if (map.containsKey(rhythm)) {
				int count = map.get(rhythm);
				valueChanges.add(count);//rhythm between events
				addOnePositionToMapValues(map);
				map.put(rhythm, 0);
			}else{
				valueChanges.add(-1);//-1 = *
				addOnePositionToMapValues(map);
				map.put(rhythm, 0);
			}
		}
		return valueChanges;
	}
	
	public static List<List<Double>> splitRhythm(double[] rhythmArray) {
		List<List<Double>> rhythmList = new ArrayList<List<Double>>();
		double total = 0;
		List<Double> list = null;
		for (int i = 0; i < rhythmArray.length; i++) {
			if (total == 0) {
				list = new ArrayList<Double>();
			}
			total = total + rhythmArray[i];
			list.add(rhythmArray[i]);
			if (total == 1 || total == 2 || total == 4) {
				total = 0;
				if (list.size() > 1) {
					rhythmList.add(list);
				}
			}
			
		}
		return rhythmList;
	}

	
	public static List<Integer> getRhythmValueChanges(double[] rhythmArray){
		Map<Double, Integer> map = new TreeMap<Double,Integer>();
		List<Integer> valueChanges = new ArrayList<Integer>();
		for (double rhythm : rhythmArray) {
			if (map.containsKey(rhythm)) {
				int count = map.get(rhythm);
				valueChanges.add(count);//rhythm between events
				addOnePositionToMapValues(map);
				map.put(rhythm, 0);
			}else{
				valueChanges.add(-1);//-1 = *
				addOnePositionToMapValues(map);
				map.put(rhythm, 0);
			}
		}
		return valueChanges;
	}
	
	private static void addOnePositionToMapValues(Map<Double, Integer> map) {
		for (Double key : map.keySet()) {
			int value = map.get(key) + 1;
			map.put(key, value);
		}
	}
	
	public static void playRhythm(double[] pattern){
		Score score = new Score("JMDemo - Random Patterns");
		Part inst = new Part("Snare", 0, 9);
		Phrase phr = new Phrase(0.0); 
		// create notes for the chosen pattern to the phrase
		for (int j=0; j<pattern.length; j++) {
			Note note = new Note(60, pattern[j]);
			phr.addNote(note);
		}		
		// add the phrase to an instrument and that to a score
		inst.addPhrase(phr);
		score.addPart(inst);
		
		
		// create a MIDI file of the score
//		Write.midi(score, "RhythmPattern.mid");
		View.notate(score);
		Play.midi(score);
	}
	
	public static void playRhythmWithDynamic(double[] pattern, int[] dynamics){
		
		Score score = new Score("Random Patterns with dynamics");
		Part inst = new Part("Piano", Instruments.PIANO, 9);
		Phrase phr = new Phrase(0.0); 
		// create notes for the chosen pattern to the phrase
		for (int j=0; j<pattern.length; j++) {
			Note note = new Note(60, pattern[j], Note.DEFAULT_DYNAMIC + dynamics[j]);
			phr.addNote(note);
		}		
		// add the phrase to an instrument and that to a score
		inst.addPhrase(phr);
		score.addPart(inst);
		
//		Play.midi(score);
		// create a MIDI file of the score
//		Write.midi(score, "RhythmPattern.mid");
		View.notate(score);
	}
	
	public static double[] createFixedLengthRhythmPattern(double length){
		double[] pattern = new double[100];
		double lengthPattern = 0;
		int i = 0;
		while (lengthPattern < length) {
			pattern[i] = getRhythm2();
			lengthPattern = lengthPattern + pattern[i];
			i++;
		}
		if (lengthPattern > length) {
			lengthPattern = lengthPattern - pattern[i - 1];
			double diff = length - lengthPattern;
			pattern[i - 1] = diff;
		}
		double[] newPattern = null;
		for (int j = 0; j < pattern.length; j++) {
			if (pattern[j] == 0) {
				newPattern = new double[j];
				System.arraycopy(pattern, 0, newPattern, 0, j);
				break;
			}
		}
		return newPattern;	
	}
	
	public static double[] createRandomRhythmPattern(int size){
		double[] pattern = new double[size];
		for (int i = 0; i < size; i++) {
			pattern[i] = getRhythm2();
		}
		return pattern;	
	}
	
	public static double[] createRandomRhythmPattern2(int size){
		double[] pattern = new double[size];
		for (int i = 0; i < size; i++) {
			double[] rhythmPattern = getRhythmPattern();
			if (i==0) {
				pattern = rhythmPattern;
			} else {
				pattern = concatArrays(pattern, rhythmPattern);
			}
		}
		return pattern;	
	}
	
	public static double[] concatArrays(double[] a, double[] b){
        double[] ab = new double[a.length + b.length];    
        System.arraycopy(a, 0, ab, 0, a.length);  
        System.arraycopy(b, 0, ab, a.length, b.length);   
//        System.out.println(Arrays.toString(ab));
		return ab;  
	}
	
	public static double[] repeatPattern(double[] pattern, int repeat){
		double[] repeatPattern = new double[pattern.length * repeat];    
		for (int i = 0; i < repeat; i++) {
			if (i==0) {
				repeatPattern = pattern;
			} else {
				repeatPattern = concatArrays(repeatPattern, pattern);
			}	
		}
		return repeatPattern;	
	}
	
	public static BigDecimal countChanges(List<Integer> valueChanges){
		int count = 0;
		for (Integer integer : valueChanges) {
			if (integer == -1) {
				count++;
			}
		}
		BigDecimal size = new BigDecimal(valueChanges.size());
		BigDecimal countChanges = new BigDecimal(count);
//		BigDecimal percent = countChanges.divide(size, 2 , BigDecimal.ROUND_FLOOR);
		return countChanges;	
	}
	
	public static int countRepetition(List<Integer> valueChanges){
		int count = 0;
		for (Integer integer : valueChanges) {
			if (integer != -1) {
				count = count + integer;
			}
		}
		return count;	
	}
	
	public static double[] generateSimilarRhythmWithChronotonicSimiliraty(double[] melody , double similarity, double beatLength) {
		ChronotonicSimilarity sim = new ChronotonicSimilarity();
		double[] pattern = null;
		double generatedSimilarity = 0;
		int i = 0;
		while (generatedSimilarity < similarity && i < 100) {//i to prevent endless loop
			pattern = RhythmicFunctions.createFixedLengthRhythmPattern(beatLength);
			sim.calculateRhythmicSimilarity(melody, pattern);
			generatedSimilarity = sim.getSimilarity();
//			System.out.println(sim.getSimilarity());
			i++;
		}
		return pattern;
	}
	
	public static double[] getEvenRhythmPattern() {
		int i = random.nextInt(8);
		switch (i) {
		case 0:
			double[] rhythmPattern1 = {RhythmValues.WHOLE_NOTE};
			return rhythmPattern1;
		case 1:
			double[] rhythmPattern2 = {RhythmValues.HALF_NOTE,RhythmValues.HALF_NOTE};
			return rhythmPattern2;
		case 2:
			double[] rhythmPattern3 = {RhythmValues.QUARTER_NOTE,RhythmValues.QUARTER_NOTE,RhythmValues.HALF_NOTE};
			return rhythmPattern3;
		case 3:
			double[] rhythmPattern4 = {RhythmValues.HALF_NOTE,RhythmValues.QUARTER_NOTE,RhythmValues.QUARTER_NOTE};
			return rhythmPattern4;
		case 4:
			double[] rhythmPattern5 = {RhythmValues.QUARTER_NOTE,RhythmValues.HALF_NOTE,RhythmValues.QUARTER_NOTE};
			return rhythmPattern5;
		case 5:
			double[] rhythmPattern6 = {RhythmValues.QUARTER_NOTE,RhythmValues.DOTTED_HALF_NOTE};
			return rhythmPattern6;
		case 6:
			double[] rhythmPattern7 = {RhythmValues.DOTTED_HALF_NOTE,RhythmValues.QUARTER_NOTE};
			return rhythmPattern7;
		case 7:
			double[] rhythmPattern8 = {RhythmValues.QUARTER_NOTE,RhythmValues.QUARTER_NOTE,RhythmValues.QUARTER_NOTE, RhythmValues.QUARTER_NOTE};
			return rhythmPattern8;
		}
		return null;
	}
	
	public static double[] getTripletRhythmPattern() {
		int i = random.nextInt(3);
		switch (i) {
		case 0:
			double[] rhythmPattern1 = {RhythmValues.HALF_NOTE_TRIPLET, RhythmValues.QUARTER_NOTE_TRIPLET};
			return rhythmPattern1;
		case 1:
			double[] rhythmPattern2 = {RhythmValues.HALF_NOTE_TRIPLET,RhythmValues.QUARTER_NOTE_TRIPLET};
			return rhythmPattern2;
		case 2:
			double[] rhythmPattern3 = {RhythmValues.QUARTER_NOTE_TRIPLET,RhythmValues.QUARTER_NOTE_TRIPLET,RhythmValues.QUARTER_NOTE_TRIPLET};
			return rhythmPattern3;
		}
		return null;
	}
	
	public static double[] getRhythmPatternProfile(int[] profile, double melodyLength){
		int[] rhythmSelections = {1,2,3,4,5};
		int[] weightSum = new int[profile.length];
		Random Rnd = new Random();
		weightSum[0] = profile[0];
		for (int i = 1; i < profile.length; i++){
			weightSum[i] = weightSum[i - 1] + profile[i];
		}
		double[] pattern = null;	
		double phraseLength = 0;
		boolean firstTime = true;
		while (phraseLength < melodyLength) {
			int k = Rnd.nextInt(weightSum[profile.length - 1]);
			int i;
			for (i = 0; k > weightSum[i]; i++)
				;
			double[] tempPattern = null;
			double rhythm = 0.0;
			switch (rhythmSelections[i]) {
			case 1:
				tempPattern = getEvenRhythmPattern();
				rhythm = 4.0;
				break;
			case 2:
				tempPattern = getEvenRhythmPattern();
				dividePattern(tempPattern, 2);
				rhythm = 2.0;
				break;
			case 3:
				tempPattern = getEvenRhythmPattern();
				dividePattern(tempPattern, 4);
				rhythm = 1.0;
				break;
			case 4:
				tempPattern = getTripletRhythmPattern();
				rhythm = 2.0;
				break;
			case 5:
				tempPattern = getTripletRhythmPattern();
				dividePattern(tempPattern, 2);
				rhythm = 1.0;
				break;
			default:
				break;
			}
			double tempPhraseLength = phraseLength + rhythm;
			if (tempPhraseLength > melodyLength) {
				double lastRhythm = melodyLength - phraseLength;
				double[] lastPattern = {lastRhythm};
				pattern = concatArrays(pattern, lastPattern);
			} else {
				if (firstTime) {
					firstTime = false;
					pattern = tempPattern;
				}else{
					pattern = concatArrays(tempPattern, pattern);
				}
			}
			phraseLength = tempPhraseLength;
		}
		for (int i = 0; i < CONCAT; i++) {
			if (pattern.length > 2) {
				pattern = randomConcat(pattern);
			}else{
				break;
			}
		}
		return pattern;
	}

	private static double[] randomConcat(double[] pattern) {
		int index = random.nextInt(pattern.length - 2);
		double firstValue =	pattern[index];
		double secondValue = pattern[index + 1];
		double total = firstValue + secondValue;
		double[] concatPattern = ArrayUtils.remove(pattern, index + 1);
		concatPattern[index ] = total;
		return concatPattern;
	}

	private static void dividePattern(double[] pattern, int divide) {
		for (int j = 0; j < pattern.length; j++) {
			pattern[j] = pattern[j] / divide;
		}
	}
	
	public static int[] getRhythmTemplate(int length) {
		Random random = new Random();
		int i = random.nextInt(6);
		int half = length/2;
		int fourth = length/4;
		switch (i) {
		case 0:
			int[] pattern1 = {fourth, fourth, fourth, fourth};
			return pattern1; 
		case 1:
			int[] pattern2 = {fourth, fourth, half};
			return pattern2; 
		case 2:
			int[] pattern3 = {half, fourth, fourth};
			return pattern3; 
		case 3:
			int[] rhythmPattern4 = {half, half};
			return rhythmPattern4;
		case 4:
			int[] rhythmPattern5 = {length};
			return rhythmPattern5;
		case 5:
			int[] rhythmPattern6 = {fourth, half , fourth};
			return rhythmPattern6;
		}
		return null;
	}
	
	
	
	

}
