package be.functions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;

import jm.music.data.Note;
import jm.music.data.Phrase;
import jm.music.tools.NoteListException;
import jm.music.tools.QuantisationException;

import org.apache.commons.lang.ArrayUtils;

import be.core.NoteComparator;
import be.data.Interval;
import be.data.NotePos;
import be.data.RhythmInfo;
import be.data.Scale;
import be.util.Utilities;


public class MelodicFunctions {
	
	private static Logger LOGGER = Logger.getLogger(MelodicFunctions.class.getName());

	public static void main(String[] args) throws NoteListException, QuantisationException {

//		Phrase phrase = new Phrase();
//		phrase.add(new Note(60,1.0));
//		phrase.add(new Note(62,0.25));
//		phrase.add(new Note(64,1.0));
//		phrase.add(new Note(62,0.25));
//		phrase.add(new Note(60,0.25));
//		phrase.add(new Note(68,1.0));
////		phrase.add(new Note(70,0.25));
////		phrase.add(new Note(73,1.0));
////		phrase.add(new Note(70,0.25));
////		phrase.add(new Note(80,1.0));
////		phrase.addRest(new Rest(1.0));
////		phrase.add(new Note(62,1.0));
////		phrase.add(new Note(70,1.0));
////		phrase.add(new Note(70,0.75));
////		phrase.add(new Note(60,1.0));
////		phrase.add(new Note(72,1.0));
////		phrase.add(new Note(E2,1.0));
//		List<NoteInfo> noteInfoList = getNoteInfo(phrase);
//		for (NoteInfo noteInfo : noteInfoList) {
//			System.out.println(noteInfo);
//		}
//		List<RhythmInfo> list = getRhythmInfo(phrase);
//		for (RhythmInfo rhythmInfo : list) {
//			System.out.println(rhythmInfo);
//		}
//		Utilities.accents(phrase);
//		Note[] notes = phrase.getNoteArray();
//		for (Note note : notes) {
//			System.out.print(note.getNote() + ", ");
//		}
//		List<Integer> valueChanges = getDifferentValueChanges(phrase);
//		for (Integer integer : valueChanges) {
//			System.out.print(integer + ", ");
//		}
//		System.out.println();
//		double noteDensity = PhraseAnalysis.noteDensity(phrase, 0.25);
//		System.out.println("noteDensity: " + noteDensity);
//		double melodicDirectionStability = PhraseAnalysis.melodicDirectionStability(phrase);
//		System.out.println("melodicDirectionStability: " + melodicDirectionStability);
//		double pitchVariety = PhraseAnalysis.pitchVariety(phrase);
//		System.out.println("pitchVariety: " + pitchVariety);
//		double rhythmRange = PhraseAnalysis.rhythmRange(phrase);
//		System.out.println("rhythmRange: " + rhythmRange);
//		double rhythmicVariety = PhraseAnalysis.rhythmicVariety(phrase);
//		System.out.println("rhythmicVariety: " + rhythmicVariety);
//		double leapCompensation = PhraseAnalysis.leapCompensation(phrase);
//		System.out.println("leapCompensation: " + leapCompensation);
////		phrase.add(new Note(72,1.0));
//////		phrase.add(new Note(AS4,1.0));
////		phrase.add(new Note(61,1.0));
////		phrase.add(new Note(72,1.0));
//		double dev = getintervalStandardDeviation(phrase);
//		System.out.println("melodic variation = " + dev);
//		double intervalVariation = getIntervalVariation(phrase);
//		System.out.println("intervalVariation  = " + intervalVariation);
//		double val = getMelodicValue(phrase);
//		System.out.println("val :" + val);
//		double contour = getMelodicContour(phrase);
//		System.out.println("contour :" + Math.abs(contour));
//		Note tonalNote = getTonalNote(phrase);
//		System.out.println("tonal note: " + tonalNote.getNote());
		


//		Note[] melody = TestMelody.melodyWebern();
//		Note[] melody = TestMelody.melodyDiffRhythms();
	

//		System.out.println("weights:" + Arrays.toString(weights));
//		DescriptiveStatistics stats = new DescriptiveStatistics(weights);
//		System.out.println(stats.getMean());
//		Phrase phrase = new Phrase(melody);
//		List<Integer> valueCh = getValueChanges(phrase);
//		for (Integer integer : valueCh) {
//			System.out.print(integer + ",");
//		}
//		System.out.println();
//		
//	
//		
//		Integer[] pitches = new Integer[melody.length];
//		for (int i = 0; i < melody.length; i++) {
//			pitches[i] = melody[i].getPitch();
//		}
//		System.out.println("melodic val:" + getMelodicValue(phrase));
//		List<Double> v = melodicWindow(pitches, 4);
//		double[] mel = Utilities.listToArray(v);
//		System.out.println("melody: " + Arrays.toString(mel));
//		stats = new DescriptiveStatistics(mel);
//		System.out.println(1 - stats.getGeometricMean());
//		List<Integer> valueChanges = getValueChangesGeneric(pitches);
//		for (Integer integer : valueChanges) {
//			System.out.print(integer + ",");
//		}
//		System.out.println();
//		
//		double val = countNewValueChanges(pitches);
//		double zero = countZeroValueChanges(pitches);
//		System.out.println("val pitches: " + val);
//		System.out.println("zero pitches: " + zero);
//		
//		Double[] rhythms = new Double[melody.length];
//		for (int i = 0; i < melody.length; i++) {
//			rhythms[i] = melody[i].getRhythmValue();
//		}
//		List<Integer> valueChangesRhythms = getValueChangesGeneric(rhythms);
//		for (Integer integer : valueChangesRhythms) {
//			System.out.print(integer + ",");
//		}
//		System.out.println();
//		double val2 = countNewValueChanges(rhythms);
//		double zero2 = countZeroValueChanges(rhythms);
//		System.out.println("val: " + val2);
//		System.out.println("Zero: " + zero2);
//		Phrase melody = generateMelodyProbability(8, 60, 4 , Scale.MAJOR_SCALE);	
//		View.notate(phrase);	
	}
	
	
	public static List<Double> getMelodicWeights2(List<NotePos> melody, int windowSize){
		int size = melody.size();
		NotePos[] notes = new NotePos[size];
		for (int i = 0; i < size; i++) {
			notes[i] = melody.get(i);
		}
		return melodicWindow(notes, windowSize);
	}
	
	public static List<Double> melodicWindow(NotePos[] notes, int windowSize){
		int length = notes.length - windowSize + 1;	
		List<Double> values = new ArrayList<Double>();
		for (int i = 0; i < length; i++) {
			NotePos[] melody = new NotePos[windowSize];
			for (int j = 0; j < windowSize; j++) {
				melody[j] = notes[i + j];
			}
			values.add(computeMelodicValueWindow(melody));
		}
		return values; 
	}

	private static double computeMelodicValueWindow(NotePos[] melody ) {
		List<Double> values = new ArrayList<Double>();
		for (int j = 0; j < melody.length - 1; j++) {
//			for (int i = j + 1; i < melody.length; i++) {
				NotePos note = melody[0];
				NotePos nextNote = melody[j + 1];

				int difference = nextNote.getPitch() - note.getPitch();
				Interval interval = Utilities.getEnumInterval(difference);
					
				double positionWeigtht = (nextNote.getPositionWeight() + note.getPositionWeight()) / 2;
				double innerMetricWeight = (nextNote.getInnerMetricWeight() + note.getInnerMetricWeight())/2;
//				double dynamic = ((note2.getDynamic() + note.getDynamic())/2) / 127d;//max midi TODO
				double rhythmicWeight = (positionWeigtht + innerMetricWeight) / 2;
				
				double intervalValue = (interval.getMelodicValue() * 0.6) + (interval.getMelodicValue() * rhythmicWeight * 0.4);
				values.add(intervalValue); 		
//			}
		}
//		double[] v = Utilities.listToArray(values);
//		LOGGER.finest(Arrays.toString(values));
//		DescriptiveStatistics statistics = new DescriptiveStatistics(v);
//		return statistics.getMean();//Geometric mean???
		return (values.isEmpty())?0.0:Collections.min(values);
	}
	
	
	
	public static List<RhythmInfo> getRhythmInfo(Phrase phrase){
		Note[] notes = phrase.getNoteArray();
		List<RhythmInfo> infoList = new ArrayList<RhythmInfo>();
		int numerator = phrase.getNumerator();
		if (numerator == 0) {
			numerator = 4;
		}
		double beatCounter = 0.0;
		for (Note note : notes) {
			RhythmInfo info = new RhythmInfo();
			info.setRhythmValue(note.getRhythmValue());
			info.setPosition(beatCounter);
			double positionInBar = beatCounter%numerator;
//			if (positionInBar < 1.0) {
//				positionInBar = positionInBar + numerator;
//			}
			info.setPostitionInBar(positionInBar);
			beatCounter = beatCounter + note.getRhythmValue();
			infoList.add(info);
		}
		return infoList;
		
	}
	
	public static Note getTonalNote(Phrase phrase){
		Map<Note, Double> map = getTonalValue(phrase);
		Set<Note> keys = map.keySet();
		double highestValue = 0;
		Note tonalNote = null;
		for (Note note : keys) {
			double value = map.get(note);
			if (value > highestValue) {
				highestValue = value;
				tonalNote = note;
			}
//			System.out.println(note.getNote() + " :" + value);
		}
		return tonalNote;
	}
	
	public static double getintervalStandardDeviation(Phrase phrase){
		Note[] melody = phrase.getNoteArray();
		double[] melodicWeights = new double[melody.length - 1];
		for (int i = 0; i < melody.length - 1; i++) {
			int difference = (melody[i + 1].getPitch() - melody[i].getPitch())%12;
			Interval interval = Utilities.getEnumInterval(difference);
			melodicWeights[i] = interval.getMelodicValue();
		}
		double standardDeviation = Utilities.getStandardDeviation(melodicWeights);
		return standardDeviation;
	}
	
	public static double getIntervalVariation(Phrase phrase){
		Note[] melody = phrase.getNoteArray();
		Set<Interval> intervalSet = new HashSet<Interval>();
		for (int i = 0; i < melody.length - 1; i++) {
			int difference = (melody[i + 1].getPitch() - melody[i].getPitch())%12;
			Interval interval = Utilities.getEnumInterval(difference);
			intervalSet.add(interval);
		}
		int intervalCount = melody.length - 1;
        return (double)intervalSet.size() / (double)intervalCount;
	}

	
	public static double getMelodicValue(Phrase phrase){
		Note[] melody = phrase.getNoteArray();
		double sum = 0.0;
		int count = 0;
		for (int i = 0; i < melody.length - 1; i++) {
			int difference = (melody[i + 1].getPitch() - melody[i].getPitch())%12;
			Interval interval = Utilities.getEnumInterval(difference);
			System.out.print(interval + ": "+  interval.getMelodicValue() + "," );
			sum = sum + interval.getMelodicValue();
			count++;
		}
		System.out.println("count :" + count);
		return sum/count;
	}
	
	public static double getMelodicValue(NotePos[] melody){
		double sum = 0.0;
		int count = 0;
		for (int i = 0; i < melody.length - 1; i++) {
			int difference = (melody[i + 1].getPitch() - melody[i].getPitch())%12;
			Interval interval = Utilities.getEnumInterval(difference);
//			System.out.print(interval + ": "+  interval.getMelodicValue() + "," );
			sum = sum + interval.getMelodicValue();
			count++;
		}
//		System.out.println("count :" + count);
		return sum/count;
	}
	
//	public static double[] getMelodicWeights(NotePos[] melody){
//		double[] melodicWeights = new double[melody.length - 1];
//		for (int i = 0; i < melody.length - 1; i++) {
//			int difference = (melody[i + 1].getNote().getPitch() - melody[i].getNote().getPitch())%12;
//			Interval interval = Utilities.getEnumInterval(difference);
//			melodicWeights[i] = interval.getMelodicValue();
//		}
//		return melodicWeights;
//	}
	
	public static double[] getMelodicWeights(NotePos[] melody, int allowIntervalsBelowValue){
		List<Double> list = new ArrayList<Double>();
		int count = 0;
		for (int i = 0; i < melody.length - 1; i++) {
			if (!melody[i + 1].isRest()) {
				int difference = (melody[i + 1].getPitch() - melody[i].getPitch());
				if (difference > allowIntervalsBelowValue) {
					count++;
					list.add(-1.0);
				} else {
					Interval interval = Utilities.getEnumInterval(difference % 12);
					switch (interval.getInterval()) {//don't count note repetitions and octaves
					case 0:
					case 12:
						break;
					default:
						count++;
						list.add(interval.getMelodicValue());
						break;
					}
				}	
			}
		}
		Double[] melodicWeights = new Double[count];
		melodicWeights = list.toArray(melodicWeights);
		return ArrayUtils.toPrimitive(melodicWeights);
	}
	
	public static double[] getMelodicWeights2(Note[] melody, int allowIntervalsBelowValue){
		List<Double> list = new ArrayList<Double>();
		int count = 0;
		for (int i = 0; i < melody.length - 1; i++) {
			int difference = (melody[i + 1].getPitch() - melody[i].getPitch());
			if (difference > allowIntervalsBelowValue) {
				count++;
				list.add(-1.0);
			} else {
				Interval interval = Utilities.getEnumInterval(difference % 12);
				switch (interval.getInterval()) {//don't count note repetitions and octaves
				case 0:
				case 12:
					break;
				default:
					count++;
					list.add(interval.getMelodicValue());
					break;
				}
			}	
		}
		Double[] melodicWeights = new Double[count];
		melodicWeights = list.toArray(melodicWeights);
		return ArrayUtils.toPrimitive(melodicWeights);
	}
	
	public static double getMelodicContour(Phrase phrase){
		Note[] melody = phrase.getNoteArray();
		double sum = 0.0;
		int count = 0;
		for (int i = 0; i < melody.length - 1; i++) {
			int difference = (melody[i + 1].getPitch() - melody[i].getPitch())%12;
			System.out.print("difference : "+  difference + "," );
			sum = sum + difference;
			count++;
		}
//		System.out.println("count :" + count);
		return sum;
	}
	
	public static Map<Note, Double> getTonalValue(Phrase phrase){
		Note[] melody = phrase.getNoteArray();
		Map<Note, Double> tonalMap = new TreeMap<Note,Double>(new NoteComparator());
		double totalValue = 0;
		for (Note note : melody) {
			double value = note.getRhythmValue()/melody.length;
			int pitch = note.getPitch()%12;
			Note n = new Note(pitch, value);
			if(tonalMap.containsKey(n)){
				totalValue = tonalMap.get(n);
				tonalMap.put(n, totalValue + value);
			}else{
				tonalMap.put(n, value);
			}		
		}
		return tonalMap;
	}
	
	public static List<Integer> getValueChanges(Phrase phrase){
		Note[] melody = phrase.getNoteArray();
		Map<Integer, Integer> map = new TreeMap<Integer,Integer>();
		List<Integer> valueChanges = new ArrayList<Integer>();
		for (Note note : melody) {
			int pitch = note.getPitch();// mod 12 octaaf equivalentie?
			if (map.containsKey(pitch)) {
				int count = map.get(pitch);
				valueChanges.add(count);//notes between events
				addOnePositionToMapValues(map);
				map.put(pitch, 0);
			}else{
				valueChanges.add(-1);//-1 = *
				addOnePositionToMapValues(map);
				map.put(pitch, 0);
			}
		}
		return valueChanges;
	}
	
	public static  <T> List<Integer> getValueChangesGeneric(T[] array){
		Map<T, Integer> map = new TreeMap<T,Integer>();
		List<Integer> valueChanges = new ArrayList<Integer>();
		for (T value : array) {
			if (map.containsKey(value)) {
				Integer count = map.get(value);
				valueChanges.add(count);//notes between events
				addOnePositionToMapValuesGeneric(map);
				map.put(value, 0);
			}else{
				valueChanges.add(-1);//-1 = *
				addOnePositionToMapValuesGeneric(map);
				map.put(value, 0);
			}
		}
		return valueChanges;
	}
	
	private static <T> void addOnePositionToMapValuesGeneric(Map<T, Integer> map) {
		for (T key : map.keySet()) {
			int value = map.get(key) + 1;
			map.put(key, value);
		}
	}
	
	public static List<Integer> getValueChangesMax(Phrase phrase){
		List<Integer> valueChanges = getValueChanges(phrase);
		List<Integer> newValueChanges = new ArrayList<Integer>();
		Integer max = Collections.max(valueChanges);
		for (Integer integer : valueChanges) {
			if (integer.equals(-1)) {
				newValueChanges.add(max + 1);
			} else {
				newValueChanges.add(integer);
			}
		}
		return newValueChanges;
	}
	
	public static List<Integer> getDifferentValueChanges(Phrase phrase){
		Note[] melody = phrase.getNoteArray();
		Map<Integer, Set<Integer>> map = new TreeMap<Integer,Set<Integer>>();
		List<Integer> valueChanges = new ArrayList<Integer>();
		for (Note note : melody) {
			int pitch = note.getPitch();
			if (map.containsKey(pitch)) {
				Set<Integer> set = map.get(pitch);
				valueChanges.add(set.size());//different notes between events
				addPitchToMap(map, pitch);
				set = new HashSet<Integer>();
				map.put(pitch, set);
			}else{
				valueChanges.add(-1);//-1 = *
				addPitchToMap(map, pitch);
				Set<Integer> set = new HashSet<Integer>();
				map.put(pitch, set);
			}
		}
		return valueChanges;
	}

	private static void addOnePositionToMapValues(Map<Integer, Integer> map) {
		for (Integer key : map.keySet()) {
			int value = map.get(key) + 1;
			map.put(key, value);
		}
	}
	
	private static void addPitchToMap(Map<Integer, Set<Integer>> map, int pitch){
		for (Integer key : map.keySet()) {
			Set<Integer> set = map.get(key);
			set.add(pitch);
			map.put(key, set);
		}
	}
	
	public static Phrase generateMelodyProbability(int length, int offset, int firstPitchClass, int[] scale){
		Note[] notes = new Note[length];
		Note note = new Note(firstPitchClass + offset, 1.0);
		notes[0] = note;
		int temp = firstPitchClass;
		for (int i = 1; i < notes.length; i++) {
			int pc2 = Scale.pickPitchProbability(temp, scale);
			double r = RhythmicFunctions.getRhythmProbability();
			Note note2 = new Note(pc2 + offset, r);
			notes[i] = note2;
			temp = pc2;
		}
		Phrase phrase = new Phrase();
		phrase.addNoteList(notes);
		return phrase;
	}
	
	public static <T> double countZeroValueChanges(T[] array){
		List<Integer> valueChanges = getValueChangesGeneric(array);
		double newValues = 0;
		for (Integer integer : valueChanges) {
			if (integer == 0) {
				newValues++;
			}
		}
//		System.out.println(newValues + "/" + array.length);
		return newValues/array.length;
		
	}
	
	public static <T> double countNewValueChanges(T[] array){
		List<Integer> valueChanges = getValueChangesGeneric(array);
		double newValues = 0;
		for (Integer integer : valueChanges) {
			if (integer == -1) {
				newValues++;
			}
		}
//		System.out.println(newValues + "/" + array.length);
		return newValues/array.length;
		
	}


}
