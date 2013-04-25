package be.functions;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math.util.FastMath;
import org.apache.commons.math.util.MathUtils;

import be.data.Interval;
import be.util.Utilities;

import jm.music.data.Note;
import jm.music.data.Phrase;


public class VoiceLeadingFunctions {
	
	public static void main(String[] args) {
		TreeMap<Double, List<int[]>> voiceLeadingMap = new TreeMap<Double, List<int[]>>();
//		List<int[]> vl = voiceLeading2Voices();
//		List<int[]> vl = voiceLeading3Voices();
//		System.out.println(vl.size());
		
//		for (int[] is : vl) {
//			System.out.print("[" );
//			for (int i : is) {
//				System.out.print(i + ",");
//			}
//			System.out.print("]" );
//			System.out.println();
//		}
		
	
//		for (int[] is : vl) {
//			double vlDegree = Utilities.round(analyseVoiceLeading(is),3);
//			List<int[]> ch = voiceLeadingMap.get(vlDegree);
//			if(ch == null){
//				voiceLeadingMap.put(vlDegree, ch = new ArrayList<int[]>());
//			}
//			ch.add(is);
//			
//		}
//		
//		Set<Double> keys = voiceLeadingMap.keySet();
//		for (Double key : keys) {
//			List<int[]> ch = voiceLeadingMap.get(key);
////			System.out.println();
//			System.out.println("size group: " + ch.size());
//			for (int[] is : ch) {	
//				System.out.print(" vl : " + key);
//				System.out.print("[" );
//				for (int i : is) {
//					System.out.print(i + ",");
//				}
//				System.out.print("]" );
////				System.out.println();
//			}
//			System.out.println();
//			
//		}
		double[] firstChord = {60.0,50.0,60.0};
		double[] secondChord = {60.0,55.0, 0.0};

		double sum = 0;
		for (int i = 0; i < firstChord.length; i++) {
			if (firstChord[i] != 0 && secondChord[i] != 0) {
				sum += FastMath.abs(firstChord[i] - secondChord[i]);
			}
		}
		
//		double smoothnes = MathUtils.distance1(vec1,vec2);
		System.out.println("smoothness: " + sum);
		 
		Note[] chord1 = new Note[4];
		chord1[0] = new Note(60, 1.0);
		chord1[1] = new Note(60, 1.0);
		chord1[2] = new Note(60, 1.0);
		chord1[3] = new Note(60, 1.0);
		Note[] chord2 = new Note[4];
		chord2[0] = new Note(60, 1.0);
		chord2[1] = new Note(70, 1.0);
		chord2[2] = new Note(70, 1.0);
		chord2[3] = new Note(60, 1.0);
		//Calculates the L2 (Euclidean) distance between two points.
		double euclideanDistance = euclideanDistance(chord1, chord2);
		System.out.println("euclideanDistance: " + euclideanDistance);
		//Smoothness: Calculates the L1 (sum of abs) distance between two points.
		double smoothness = taxiCab(chord1, chord2);
		System.out.println("smoothness: " + smoothness);
		//Calculates the L infinite (max of abs) distance between two points.
		double lInfDistance = infiniteDistance(chord1, chord2);
		System.out.println("lInfDistance: " + lInfDistance);
	

	}
	
	/**
	 * Smoothness - taxicab: Calculates the L1 (sum of abs) distance between two points.
	 * @param vector1
	 * @param vector2
	 * @return
	 */
	public static double taxiCab(double[] vector1, double[] vector2){
		return MathUtils.distance1(vector1, vector2);
	}
	
	public static double taxiCab(Note[] chord1, Note[] chord2){
		double[] vector1 = toPitchVector(chord1);
		double[] vector2 = toPitchVector(chord2);
		return taxiCab(vector1, vector2);
	}
	
	public static double[] taxicab(List<Note[]> chords){
		int length = chords.size() - 1;
		double[] voiceLeadingArray = new double[length];
		for (int i = 0; i < length; i++) {
			voiceLeadingArray[i] = taxiCab(chords.get(i), chords.get(i + 1));
		}
		return voiceLeadingArray;
	}

	private static double[] toPitchVector(Note[] melody) {
		double[] vector = new double[melody.length];
		for (int i = 0; i < melody.length; i++) {
			vector[i] = melody[i].getPitch();
		}
		return vector;
	}
	
	/**
	 * Calculates the L2 (Euclidean) distance between two points.
	 * @param vector1
	 * @param vector2
	 * @return
	 */
	public static double euclideanDistance(double[] vector1, double[] vector2){
		return MathUtils.distance(vector1, vector2);
	}
	
	public static double euclideanDistance(Note[] chord1, Note[] chord2){
		double[] vector1 = toPitchVector(chord1);
		double[] vector2 = toPitchVector(chord2);
		return euclideanDistance(vector1, vector2);
	}
	
	/**
	 * Calculates the L infinite (max of abs) distance between two points.
	 * @param vector1
	 * @param vector2
	 * @return
	 */
	public static double infiniteDistance(double[] vector1, double[] vector2){
		return MathUtils.distanceInf(vector1, vector2);
	}
	
	public static double infiniteDistance(Note[] chord1, Note[] chord2){
		double[] vector1 = toPitchVector(chord1);
		double[] vector2 = toPitchVector(chord2);
		return infiniteDistance(vector1, vector2);
	}
	
	public static double analyseVoiceLeading(int[] vl){
		double sum = 0.0;
		for (int i : vl) {
			Interval interval = Utilities.getEnumInterval(i);
//			System.out.print(" " + interval + " : " + interval.getMelodicValue());
			sum = sum + interval.getMelodicValue();
		}
		return sum/vl.length;
	}
	
	public static List<int[]> voiceLeading2Voices(){
		List<int[]> list = new ArrayList<int[]>();	
		for (int j = 0; j < 12; j++) {
			for (int i = 0; i < 12; i++) {
				int[] intervals = new int[2];
				intervals[0] = j;
				intervals[1] = i;
				list.add(intervals);
			}
		}
		for (int j = 0; j < 12; j++) {
			for (int i = 0; i > -12; i--) {
				int[] intervals = new int[2];
				intervals[0] = j;
				intervals[1] = i;
				list.add(intervals);
			}
		}
		for (int j = 0; j > -12; j--) {
			for (int i = 0; i < 12; i++) {
				int[] intervals = new int[2];
				intervals[0] = j;
				intervals[1] = i;
				list.add(intervals);
			}
		}
		for (int j = 0; j > -12; j--) {
			for (int i = 0; i > -12; i--) {
				int[] intervals = new int[2];
				intervals[0] = j;
				intervals[1] = i;
				list.add(intervals);
			}
		}
		return list;
	}
	
	
	public static List<int[]> voiceLeading3Voices(){
		List<int[]> list = new ArrayList<int[]>();	
		for (int j = 0; j < 12; j++) {
			for (int i = 0; i < 12; i++) {
				for (int k = 0; k < 12; k++) {
					int[] intervals = new int[3];
					intervals[0] = j;
					intervals[1] = i;
					intervals[2] = k;
					list.add(intervals);
				}
			}
		}
		for (int j = 0; j > -12; j--) {
			for (int i = 0; i < 12; i++) {
				for (int k = 0; k < 12; k++) {
					int[] intervals = new int[3];
					intervals[0] = j;
					intervals[1] = i;
					intervals[2] = k;
					list.add(intervals);
				}
			}
		}
		
		for (int j = 0; j < 12; j++) {
			for (int i = 0; i > -12; i--) {
				for (int k = 0; k < 12; k++) {
					int[] intervals = new int[3];
					intervals[0] = j;
					intervals[1] = i;
					intervals[2] = k;
					list.add(intervals);
				}
			}
		}
		for (int j = 0; j > -12; j--) {
			for (int i = 0; i > -12; i--) {
				for (int k = 0; k < 12; k++) {
					int[] intervals = new int[3];
					intervals[0] = j;
					intervals[1] = i;
					intervals[2] = k;
					list.add(intervals);
				}
			}
		}
		
		for (int j = 0; j < 12; j++) {
			for (int i = 0; i < 12; i++) {
				for (int k = 0; k > -12; k--) {
					int[] intervals = new int[3];
					intervals[0] = j;
					intervals[1] = i;
					intervals[2] = k;
					list.add(intervals);
				}
			}
		}
		for (int j = 0; j > -12; j--) {
			for (int i = 0; i < 12; i++) {
				for (int k = 0; k > -12; k--) {
					int[] intervals = new int[3];
					intervals[0] = j;
					intervals[1] = i;
					intervals[2] = k;
					list.add(intervals);
				}
			}
		}
		
		for (int j = 0; j < 12; j++) {
			for (int i = 0; i > -12; i--) {
				for (int k = 0; k > -12; k--) {
					int[] intervals = new int[3];
					intervals[0] = j;
					intervals[1] = i;
					intervals[2] = k;
					list.add(intervals);
				}
			}
		}
		for (int j = 0; j > -12; j--) {
			for (int i = 0; i > -12; i--) {
				for (int k = 0; k > -12; k--) {
					int[] intervals = new int[3];
					intervals[0] = j;
					intervals[1] = i;
					intervals[2] = k;
					list.add(intervals);
				}
			}
		}
		
		return list;
	}
}
