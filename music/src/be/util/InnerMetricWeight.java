package be.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import jm.music.data.Note;
import jm.music.data.Phrase;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math.util.MathUtils;

import be.functions.MelodicFunctions;
import be.functions.RhythmicFunctions;

public class InnerMetricWeight {
	
	private static final int BEAT_FACTOR = 4;//1/8 = 2 - 1/16 = 4
	private static final int REPEAT = 3;
	private static final double PULSE = 0.25;//1/8 = 0.5 - 1/16 = 0.25
	private static final double POWER = 2.0;
	private static final int CORRELATION_LENGTH = 32; //fixed length: 4 bars of 1/8 notes!!
	private static final int MINIMUM_SIZE = 3; //size of local meter 

	public static void main(String[] args) {
//		double[] rhythmPattern = {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5 };
//		double[] rhythmPattern = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 0.5, 0.5, 0.5, 1.0, 1.0, 1.0, 1.0};
//		double[] rhythmPattern = { 0.5, 1.0, 1.0, 1.0, 0.5, 0.5, 1.0, 0.5, 0.5, 0.5, 0.5,0.5};// syncopated
//		double[] rhythmPattern = {1.0, 0.5, 1.0, 1.0, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5};
//		double[] rhythmPattern = {1.0, 1.0, 0.5,1.0, 0.5, 1.0, 1.0, 0.5,1.0, 0.5,1.0, 1.0, 0.5,1.0, 0.5, 1.0, 1.0, 0.5,1.0, 0.5};
		Note[] testMelody = TestMelody.melodyWebern();
		Integer[] pitches = new Integer[testMelody.length];
		for (int i = 0; i < testMelody.length; i++) {
			pitches[i] = testMelody[i].getPitch();
		}
		Phrase test = new Phrase(testMelody);
//		double[] rhythmPattern = RhythmicFunctions.createRandomRhythmPattern2(4);
		double[] rhythmPattern = test.getRhythmArray();
		int originalLength = rhythmPattern.length;
		System.out.println(Arrays.toString(rhythmPattern));
		List<Integer> valueChanges = RhythmicFunctions.getRhythmValueChanges(rhythmPattern);
		System.out.println(valueChanges);
		System.out.println("Count repetition: " + RhythmicFunctions.countRepetition(valueChanges));
		System.out.println("Count changes: " + RhythmicFunctions.countChanges(valueChanges));
		double valueChangesValue = MelodicFunctions.countNewValueChanges(ArrayUtils.toObject(rhythmPattern));
		System.out.println("value valueChanges ritme: " + valueChangesValue);
		double valueChangespitches = MelodicFunctions.countNewValueChanges(pitches);
		System.out.println("value valueChanges pitches: " + valueChangespitches);
//		RhythmicFunctions.playRhythm(rhythmPattern);
//		rhythmPattern = RhythmicFunctions.repeatPattern(rhythmPattern, REPEAT);
		Integer[] onSetArr = extractOnset(rhythmPattern);
		System.out.println(Arrays.toString(onSetArr));
//		List<Integer> onSet = new ArrayList<Integer>();
//		int[] onSet = new int[10];
//		Integer[] onSetArr = {0, 3, 6, 7, 8, 9, 10, 11, 12, 13, 15, 18, 19, 21, 22};
		List<List<Integer>> localMeters = getLocalMeters(onSetArr);
		System.out.println(localMeters);
		Map<Integer, Double> map = getInnerMetricWeight(localMeters, onSetArr);
//		Set<Integer> keys = map.keySet();
//		for (Integer key : keys) {
//			System.out.print(key + ":");
//			System.out.println(map.get(key));
//		}
//		double l = getLength(rhythmPattern);
//		System.out.println("length:" + l);
//		System.out.println(Math.ceil(l));
		double[] innerMetricWeightVector = createCorrelationVector(map , getLength(rhythmPattern));
		System.out.println(Arrays.toString(innerMetricWeightVector));
//		int start = (int) ((getLength(rhythmPattern)/ REPEAT) * BEAT_FACTOR);
//		int end = (start * 2) ;
//		double[] innerMetricWeightVector = Arrays.copyOfRange(tempInnerMetricWeightVector, start, end);
//		System.out.println(Arrays.toString(innerMetricWeightVector));
		System.out.println();
			
		double[] twoBarWeightVector = new double[16];
		twoBarWeightVector[0] = 100.0;
		twoBarWeightVector[1] = 25.0;
		twoBarWeightVector[2] = 50.0;
		twoBarWeightVector[3] = 25.0;
		twoBarWeightVector[4] = 100.0;
		twoBarWeightVector[5] = 25.0;
		twoBarWeightVector[6] = 50.0;
		twoBarWeightVector[7] = 25.0;
		twoBarWeightVector[8] = 100.0;
		twoBarWeightVector[9] = 25.0;
		twoBarWeightVector[10] = 50.0;
		twoBarWeightVector[11] = 25.0;
		twoBarWeightVector[12] = 100.0;
		twoBarWeightVector[13] = 25.0;
		twoBarWeightVector[14] = 50.0;
		twoBarWeightVector[15] = 25.0;
		
//		twoBarWeightVector[16] = 100.0;
//		twoBarWeightVector[17] = 25.0;
//		twoBarWeightVector[18] = 50.0;
//		twoBarWeightVector[19] = 25.0;
//		twoBarWeightVector[20] = 100.0;
//		twoBarWeightVector[21] = 25.0;
//		twoBarWeightVector[22] = 50.0;
//		twoBarWeightVector[23] = 25.0;
//		twoBarWeightVector[24] = 100.0;
//		twoBarWeightVector[25] = 25.0;
//		twoBarWeightVector[26] = 50.0;
//		twoBarWeightVector[27] = 25.0;
//		twoBarWeightVector[28] = 100.0;
//		twoBarWeightVector[29] = 25.0;
//		twoBarWeightVector[30] = 50.0;
//		twoBarWeightVector[31] = 25.0;
		
		double[] twoBarSixteenthWeightVector = new double[24];
		twoBarSixteenthWeightVector[0] = 100.0;
		twoBarSixteenthWeightVector[1] = 25.0;
		twoBarSixteenthWeightVector[2] = 50.0;
		twoBarSixteenthWeightVector[3] = 25.0;
		twoBarSixteenthWeightVector[4] = 90.0;
		twoBarSixteenthWeightVector[5] = 25.0;
		twoBarSixteenthWeightVector[6] = 50.0;
		twoBarSixteenthWeightVector[7] = 25.0;
		twoBarSixteenthWeightVector[8] = 100.0;
		twoBarSixteenthWeightVector[9] = 25.0;
		twoBarSixteenthWeightVector[10] = 50.0;
		twoBarSixteenthWeightVector[11] = 25.0;
		twoBarSixteenthWeightVector[12] = 90.0;
		twoBarSixteenthWeightVector[13] = 25.0;
		twoBarSixteenthWeightVector[14] = 50.0;
		twoBarSixteenthWeightVector[15] = 25.0;
		
		twoBarSixteenthWeightVector[16] = 100.0;
		twoBarSixteenthWeightVector[17] = 25.0;
		twoBarSixteenthWeightVector[18] = 50.0;
		twoBarSixteenthWeightVector[19] = 25.0;
		twoBarSixteenthWeightVector[20] = 90.0;
		twoBarSixteenthWeightVector[21] = 25.0;
		twoBarSixteenthWeightVector[22] = 50.0;
		twoBarSixteenthWeightVector[23] = 25.0;

		double[] corrVector = Arrays.copyOfRange(twoBarWeightVector, 0, innerMetricWeightVector.length);
		System.out.println(Arrays.toString(corrVector));
		double correlation = new PearsonsCorrelation().correlation(corrVector,innerMetricWeightVector);
		System.out.println("correlation " + correlation);
//		System.out.println("max: " + getMaxValue(innerMetricWeightVector));
		double[] normalizedVector = normalize(innerMetricWeightVector, getMaxValue(innerMetricWeightVector), originalLength);
		System.out.println(Arrays.toString(normalizedVector));
//		RhythmicFunctions.playRhythmWithDynamic(rhythmPattern, convertToDynamics(normalizedVector));
	}
	
	public static int[] convertToDynamics(double[] vector){
		int[] dynamics = new int[vector.length];
		for (int i = 0; i < vector.length; i++) {
			dynamics[i] = (int)Math.ceil(vector[i]/10);
		}
		return dynamics;
	}
	
	public static double[] normalize(double[] vector, double maxValue, int length){
		double[] normalizedVector = new double[length];
		int j = 0 ;
		for (int i = 0; i < vector.length; i++) {
			if (vector[i] != 0.0) {
				normalizedVector[j] =  Math.round((vector[i] / maxValue) * 100);
				j++;
			} 
		}
		return normalizedVector;
	}
	
	public static double getMaxValue(double[] numbers){  
	    double maxValue = numbers[0];  
	    for(int i=1;i<numbers.length;i++){  
	        if(numbers[i] > maxValue){  
	            maxValue = numbers[i];  
	        }  
	    }  
	    return maxValue;  
	}  
	
	public static List<List<Integer>> getLocalMeters(Integer[] onSet){
		List<Integer> onSetList = Arrays.asList(onSet);
		List<List<Integer>> localMeters = new ArrayList<List<Integer>>();
		int[] distanceArr = {1,2,3,4,5,6,7,8,9,10};
//		int[] distanceArr = {1,2,3,4};
		for (int j = 0; j < distanceArr.length - 1; j++) {
			for (int start = 0; start < onSet.length; start++) {
				int i = onSet[start] + distanceArr[j];
				if (!onSetList.contains(i)) {
					continue;
				}else{
					List<Integer> sublist = new ArrayList<Integer>();
					sublist.add(onSet[start]);
					while (onSetList.contains(i)) {
						sublist.add(i);
						i = i + distanceArr[j];
					}
					if (sublist.size() >= MINIMUM_SIZE) {
						if (localMeters.isEmpty()) {//first time
							localMeters.add(sublist);
						} else {
							boolean isSubSet = false;
							for (List<Integer> localMeter : localMeters) {
								if (localMeter.containsAll(sublist)) {
									isSubSet = true;
								}
							}
							if (!isSubSet) {
								localMeters.add(sublist);
							}	
						}	
					}
				}
			}
//			start = 0;
		}
		return localMeters;
	}
	
	public static Map<Integer, Double> getInnerMetricWeight(List<List<Integer>> localMeters, Integer[] onSet){
		Map<Integer, Double> map = new TreeMap<Integer, Double>();
		for (int i = 0; i < onSet.length; i++) {
			for (List<Integer> localMeter : localMeters) {
				if (!localMeter.contains(onSet[i])) {
					continue;
				} else {
					Integer key = onSet[i];
					double value = Math.pow(localMeter.size()-1, POWER);
					if (map.containsKey(key)) {
						double oldValue = map.get(key);
						map.put(key, value + oldValue);
					} else {
						map.put(key, value);
					}
				}
			}
		}
		return map;
	}
	
	public static Integer[] extractOnset(double[] rhythmPattern){
		Integer[] arr = new Integer[rhythmPattern.length];
		arr[0] = 0;
		double onSet = 0;
		for (int i = 0; i < rhythmPattern.length; i++) {
			double rhythm = rhythmPattern[i];
			onSet = onSet + rhythm / PULSE;
			if (i + 1 != rhythmPattern.length) {//don't add last
				arr[i + 1] = (int) onSet;
			}
		}
		return arr;
	}
	
	public static double[] createCorrelationVector(Map<Integer, Double> map, double length){
		double[] innerMetricWeightVector = new double[(int) (length * BEAT_FACTOR)];//1/8 = 2 - 1/16 = 4
		Set<Integer> keys = map.keySet();
		for (Integer key : keys) {
			innerMetricWeightVector[key] = map.get(key);
		}
		return innerMetricWeightVector;
	}
	
	public static double getLength(double[] rhythmPattern){
		double total = 0;
		for (double d : rhythmPattern) {
			total = total + d;
		}
		return total;
	}
	
}
