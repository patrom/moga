package be.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TwelveToneSets {

	public static Integer[] allCombinatorialHexaChordA =  {0,1,2,3,4,5}; //T0 + T6 or I11
	public static Integer[] allCombinatorialHexaChordB =  {0,2,3,4,5,7}; //T0 + T6 or I1
	public static Integer[] allCombinatorialHexaChordC =  {0,2,4,5,7,9}; //T0 + T6 or I3
	public static Integer[] allCombinatorialHexaChordD =  {0,1,2,6,7,8}; //T0 + T3, T9 or I5 ,I11
	public static Integer[] allCombinatorialHexaChordE =  {0,1,4,5,8,9}; //T0 + T2, T6, T10 or I3 ,I7 ,I11
	public static Integer[] allCombinatorialHexaChordF =  {0,2,4,6,8,10}; //T0 + T1, T3, T5, T7, T9, T11 or I1, I3, I5, I7, I9, I11
	
	public static Integer[] allIntervalHexachord = {0,1,2,4,7,8};
	public static Integer[] allIntervalRow = {0,11,7,4,2,9,3,8,10,1,5,6};
	
//	Semi-combinatorial sets are sets whose hexachords are capable of forming an aggregate with one of its basic transformations (R, I, RI) transposed. 
//	There are twelve hexachords that are semi-combinatorial by inversion only.
	public static Integer[] semiCombinatorialHexaChord0 = {0, 1, 2, 3, 4, 6};
	public static Integer[] semiCombinatorialHexaChord1 = {0, 1, 2, 3, 5, 7};
	public static Integer[] semiCombinatorialHexaChord2 = {0, 1, 2, 3, 6, 7};
	public static Integer[] semiCombinatorialHexaChord3 = {0, 1, 2, 4, 5, 8};
	public static Integer[] semiCombinatorialHexaChord4 = {0, 1, 2, 4, 6, 8};
	public static Integer[] semiCombinatorialHexaChord5 = {0, 1, 2, 5, 7, 8};
	public static Integer[] semiCombinatorialHexaChord6 = {0, 1, 3, 4, 6, 9};
	public static Integer[] semiCombinatorialHexaChord7 = {0, 1, 3, 5, 7, 9};
	public static Integer[] semiCombinatorialHexaChord8 = {0, 1, 3, 5, 8, 9};
	public static Integer[] semiCombinatorialHexaChord9 = {0, 1, 4, 5, 6, 8};
	public static Integer[] semiCombinatorialHexaChord10 = {0, 2, 3, 4, 6, 8};
	public static Integer[] semiCombinatorialHexaChord11 = {0, 2, 3, 5, 7, 9};
	
//	there is one hexachord which is combinatorial by transposition (T6):
	public static Integer[] combinatorialByT6HexaChord = {0, 2, 3, 5, 7, 9};
	
	public static Integer[] twelveToneSet = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
	
	public static Integer[]violinConcertoSchoenberg = {9,10,3,11,4,6,0,1,7,8,2,5};
	public static Integer[]violinConcertoSchoenbergHexa1 = {9,10,3,11,4,6};
	public static Integer[]violinConcertoSchoenbergHexa2 = {0,1,7,8,2,5};
	
	public static List<Integer> randomizeSet(Integer[] set){
		List<Integer> row = Arrays.asList(set);
		Collections.shuffle(row);
		return row;
	}
}
