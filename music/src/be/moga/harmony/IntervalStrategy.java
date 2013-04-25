package be.moga.harmony;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

import be.data.HarmonyObject;
import be.data.Interval;
import be.data.IntervalData;
import be.data.MusicalObject;
import be.util.Utilities;

public class IntervalStrategy extends HarmonyStrategy {
	
	private static Logger LOGGER = Logger.getLogger(IntervalStrategy.class.getName());
	
	private DescriptiveStatistics statistics ;

	public double evaluateChord(MusicalObject[] musicalObjects,int allowChordsOfPitchesOrHigher) {
//		List<Integer> pitchClassList = new ArrayList<Integer>();
		Set<Integer> pitchClassSet = new TreeSet<Integer>();
		
		for (int i = 0; i < musicalObjects.length; i++) {
			if (musicalObjects[i] != null && musicalObjects[i].getPitch() != REST) {
				pitchClassSet.add(musicalObjects[i].getPitchClass());
//				pitchClassList.add(musicalObjects[i].getPitchClass());//TODO pitch
			}
		}
		if (pitchClassSet.size() == 0 || pitchClassSet.size() == 1) {
			return Double.NaN;
		}
		if (pitchClassSet.size() < allowChordsOfPitchesOrHigher) {//check on set - no duplicated pitches
			return Double.MIN_VALUE;//change to max for atonal harmony!!!!
		}else{
			Integer[] chord = pitchClassSet.toArray(new Integer[pitchClassSet.size()]);
			
			int amountOfIntervals = (chord.length * (chord.length - 1 ))/2;
			double[] values = new double[amountOfIntervals];
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
					values[intervalCount] = interval.getHarmonicValue(); 
					intervalCount++;
				}
			}
//			System.out.println(Arrays.toString(values));
//			LOGGER.finest(Arrays.toString(values));
			statistics = new DescriptiveStatistics(values);
			return statistics.getGeometricMean(); 
			
			
//			double sum = 0;
//			int intervalCount = 0;
//			for (int j = 0; j < chord.length - 1; j++) {
//				for (int i = j + 1; i < chord.length; i++) {
//					Integer note = chord[j];
//					Integer note2 = chord[i];
//					if(note2 == null){
//						break;
//					}			
//					int difference = note2 - note;
//					Interval interval = Utilities.getEnumInterval(difference);
////					System.out.println(interval);
//					sum = sum + (interval.getHarmonicValue()); 
//					intervalCount++;
//				}
//			}
////			if (pitchClassList.size() == 2) {
////				return sum/intervalCount;	
////			} else {
////				return sum/intervalCount;	
////			}
//			return sum/intervalCount;
			
		}
	}

	@Override
	public List<IntervalData> evaluateIntervals(HarmonyObject[] harmonyObjects,
			int allowChordsOfPitchesOrHigher) {
//		List<Integer> pitchClassList = new ArrayList<Integer>();
//		Set<Integer> pitchClassSet = new TreeSet<Integer>();
		//remove double notes (octaves)
//		for (int i = 0; i < harmonyObjects.length; i++) {
//			if (harmonyObjects[i] != null && harmonyObjects[i].getPitch() != REST) {
//				pitchClassSet.add(harmonyObjects[i].getPitchClass());
////				pitchClassList.add(musicalObjects[i].getPitchClass());//TODO pitch
//			}
//		}
		if (harmonyObjects.length == 0 || harmonyObjects.length == 1) {
			return Collections.EMPTY_LIST;
		}
//		if (pitchClassSet.size() < allowChordsOfPitchesOrHigher) {//check on set - no duplicated pitches
//			return Double.MIN_VALUE;//change to max for atonal harmony!!!!
//		}else{
//			Integer[] chord = pitchClassSet.toArray(new Integer[pitchClassSet.size()]);
			
			int amountOfIntervals = (harmonyObjects.length * (harmonyObjects.length - 1 ))/2;
			double[] values = new double[amountOfIntervals];
			List<IntervalData> intervals = new ArrayList<IntervalData>();

//			int intervalCount = 0;
			for (int j = 0; j < harmonyObjects.length - 1; j++) {
				for (int i = j + 1; i < harmonyObjects.length; i++) {
					int note = harmonyObjects[j].getPitch();
					int note2 = harmonyObjects[i].getPitch();
//					if(note2 == 0){
//						break;
//					}			
					int difference = (note2 - note) % 12;
					int length = getMinLength(harmonyObjects[j].getLength(),harmonyObjects[i].getLength());
					double positionWeigtht = (harmonyObjects[j].getPositionWeight() + harmonyObjects[i].getPositionWeight()) / 2;
					double innerMetricWeight = (harmonyObjects[j].getInnerMetricWeight() + harmonyObjects[i].getInnerMetricWeight())/2;
					double dynamic = ((harmonyObjects[j].getDynamic() + harmonyObjects[i].getDynamic())/2) / 127d;//max midi TODO
					Interval interval = Utilities.getEnumInterval(difference);
					double rhythmicWeight = (positionWeigtht + innerMetricWeight) / 2;
					IntervalData intervalData = new IntervalData();
					intervalData.setHarmonicWeight(interval.getHarmonicValue());
					intervalData.setLength(length);
					intervalData.setRhythmWeight(rhythmicWeight);
					intervalData.setDynamic(dynamic);
					intervals.add(intervalData);
//					values[intervalCount] = value; 
//					intervalCount++;
				}
			}
//			System.out.println(Arrays.toString(values));
//			LOGGER.finest(Arrays.toString(values));
//			statistics = new DescriptiveStatistics(values);
			return intervals; 
//		}
	}
	
	private int getMinLength(int length1, int length2){
		if (length1 <= length2) {
			return length1;
		} else {
			return length2;
		}
	}
}
