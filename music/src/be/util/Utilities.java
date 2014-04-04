package be.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;

import jm.music.data.Note;
import jm.music.data.Phrase;
import be.core.StandardDeviation;
import be.data.Interval;
import be.data.Profile;
import be.instrument.Instrument;

public class Utilities {
	

	public static void main(String[] args) {
//		Interval interval = getEnumInterval(-1);
//		System.out.println(interval);
//		double[] weights = new double[3];
//		weights[0] = 0.0;
//		weights[1] = 0.2;
//		weights[2] = 0.5;
//		// weights[3] = 0;
//		// weights[4] = 0;
//		System.out.println("variation: " + getStandardDeviation(weights));
//		Integer[] choices = new Integer[4];
//		choices[0] = 0;
//		choices[1] = 1;
//		choices[2] = 2;
//		choices[3] = 3;
//		Integer choice = randomChoice(choices);
//		System.out.println("choice = " + choice);
//		
//		double[] array = {1.0 , 0.4, 0.3, 0.2};
//		List<double[]> t = arrayWindowDiscrete(array, 2);
		
		int bass = 36;
		int tenor = 48;
		double t = Utilities.calculateRegisterValue(bass);
		double r = Utilities.calculateRegisterValue(tenor);
		System.out.println(t + r);
		System.out.println(r);
		
		
	}

	
	public static Instrument getInstrument(int voice, int low, int high, int channel) {
		Instrument range = new Instrument();
		range.setVoice(voice);
		range.setLowest(low);
		range.setHighest(high);
		range.setChannel(channel);
		return range;
	}


	public static Interval getEnumInterval(int difference) {
		Interval interval = null;
		switch (Math.abs(difference)) {
		case 0:
			interval = Interval.UNISONO;
			break;
		case 1:
			interval = Interval.KLEINE_SECONDE;
			break;
		case 2:
			interval = Interval.GROTE_SECONDE;
			break;
		case 3:
			interval = Interval.KLEINE_TERTS;
			break;
		case 4:
			interval = Interval.GROTE_TERTS;
			break;
		case 5:
			interval = Interval.KWART;
			break;
		case 6:
			interval = Interval.TRITONE;
			break;
		case 7:
			interval = Interval.KWINT;
			break;
		case 8:
			interval = Interval.KLEINE_SIXT;
			break;
		case 9:
			interval = Interval.GROTE_SIXT;
			break;
		case 10:
			interval = Interval.KLEIN_SEPTIEM;
			break;
		case 11:
			interval = Interval.GROOT_SEPTIEM;
			break;
		case 12:
			interval = Interval.OCTAAF;
			break;
		default:
			interval = Interval.GROTER_DAN_OCTAAF;
//			int octaves = Math.abs(difference) % 12;
//			getEnumInterval(difference);
		}
		return interval;
	}
	
	public static Profile getEnumProfile(int difference) {
		Profile profile = null;
		switch (Math.abs(difference)) {
		case 0:
			profile = Profile.UNISONO;
			break;
		case 1:
			profile = Profile.KLEINE_SECONDE;
			break;
		case 2:
			profile = Profile.GROTE_SECONDE;
			break;
		case 3:
			profile = Profile.KLEINE_TERTS;
			break;
		case 4:
			profile = Profile.GROTE_TERTS;
			break;
		case 5:
			profile = Profile.KWART;
			break;
		case 6:
			profile = Profile.TRITONE;
			break;
		case 7:
			profile = Profile.KWINT;
			break;
		case 8:
			profile = Profile.KLEINE_SIXT;
			break;
		case 9:
			profile = Profile.GROTE_SIXT;
			break;
		case 10:
			profile = Profile.KLEIN_SEPTIEM;
			break;
		case 11:
			profile = Profile.GROOT_SEPTIEM;
			break;
		case 12:
			profile = Profile.OCTAAF;
			break;
		}
		return profile;
	}

	public static double getStandardDeviation(double[] weights) {
		return StandardDeviation.sdKnuth(weights);
	}

	public static double round(double Rval, int Rpl) {
		double p = (float) Math.pow(10, Rpl);
		Rval = Rval * p;
		double tmp = Math.round(Rval);
		return (double) tmp / p;
	}

	public static void accents(Phrase phrase) {
		double beatCounter = phrase.getStartTime();
		Vector v = phrase.getNoteList();
		int numerator = phrase.getNumerator();
		switch (numerator) {
		case 2:
			for (int i = 0; i < v.size(); i++) {
				Note n = (Note) v.elementAt(i);
				if (beatCounter % numerator == 0.0 || beatCounter % numerator == 1.0) {
					n.setDynamic(127);
				}
				beatCounter += n.getRhythmValue();
			}
			break;
		case 3:
			for (int i = 0; i < v.size(); i++) {
				Note n = (Note) v.elementAt(i);
				if (beatCounter % numerator == 0.0 || beatCounter % numerator == 1.0 || beatCounter % numerator == 2.0 ) {
					n.setDynamic(127);
				}
				beatCounter += n.getRhythmValue();
			}
			break;
		case 4:
			for (int i = 0; i < v.size(); i++) {
				Note n = (Note) v.elementAt(i);
				if (beatCounter % numerator == 0.0 || beatCounter % numerator == 1.0
						|| beatCounter % numerator == 2.0 || beatCounter % numerator == 3.0) {
					n.setDynamic(127);
				}
				beatCounter += n.getRhythmValue();
			}
			break;

		default:
			for (int i = 0; i < v.size(); i++) {
				Note n = (Note) v.elementAt(i);
				if (beatCounter % numerator == 0.0 || beatCounter % numerator == 1.0
						|| beatCounter % numerator == 2.0 || beatCounter % numerator == 3.0) {
					n.setDynamic(127);
				}
				beatCounter += n.getRhythmValue();
			}
			break;
		}
		
	}
	
	private static Random random = new Random(System.currentTimeMillis());
	public static <T> T randomChoice(T[] choices){
	    int index = random.nextInt(choices.length);
	    return choices[index];
	}

	public static List<double[]> arrayWindowDiscrete(double[] array, int windowSize){
		List<double[]> windowArray = new ArrayList<double[]>();
		for (int i = 0; i < array.length; i = i + windowSize) {
			double[] temp = new double[windowSize];
			for (int j = 0; j < windowSize; j++) {
				double value = array[i + j];
				temp[j] = value;
			}
			windowArray.add(temp);
		}
		return windowArray;
	}
	
	public static List<double[]> arrayWindow(double[] array, int windowSize){
		List<double[]> windowArray = new ArrayList<double[]>();
		for (int i = 0; i < array.length - windowSize + 1; i++) {
			double[] temp = new double[windowSize];
			for (int j = 0; j < windowSize; j++) {
				double value = array[i + j];
				temp[j] = value;
			}
			windowArray.add(temp);
		}
		return windowArray;
	}
	
	public static List<double[]> arrayWindowBeat(double[] array, int beatValue){
		List<double[]> windowArray = new ArrayList<double[]>();
		for (int i = 0; i < array.length; i = i + beatValue) {
			double[] temp = new double[beatValue];
			for (int j = 0; j < beatValue; j++) {
				double value = array[i + j];
				temp[j] = value;
			}
			windowArray.add(temp);
		}
		return windowArray;
	}
	
	public static double calculateRegisterValue(double pitch){
		return 1 - (pitch / 100);
	}
	
	public static double[] listToArray(List<Double> values) {
		Double[] valuesArray = new Double[values.size()];
		valuesArray = values.toArray(valuesArray);
		double[] v = ArrayUtils.toPrimitive(valuesArray);
		return v;
	}
	
	public static void rotate(double[] theArray) {
      double a = theArray[0];
      int i;
      for(i = 0; i < theArray.length-1; i++)
      theArray[i] = theArray[i+1];
      theArray[i]= a;
	}

}
