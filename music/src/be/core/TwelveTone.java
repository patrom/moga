package be.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.imageio.spi.RegisterableService;

import org.apache.commons.collections.ListUtils;

import com.google.common.collect.Lists;

import jm.gui.cpn.PianoStave;
import jm.music.data.CPhrase;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.View;
import jm.util.Write;
import be.data.Motive;
import be.data.NotePos;
import be.data.Scale;
import be.functions.RhythmicFunctions;

public class TwelveTone {

	private static final int START_PITCH_CLASS = 60;
	private static final int RESOLUTION = 12;

	public static void main(String[] args) {
//		randomMelody();
		
//		Integer[] s = {0, 1, 6, 4, 10, 9, 11, 8, 3, 7, 5, 2};
//		List<Integer> set = Arrays.asList(s);
////		Collections.shuffle(set);
//		System.out.println(set);
//		RowMatrix rowMatrix = new RowMatrix(12, set);
//		rowMatrix.show();
//		List<Integer> TnSet = rowMatrix.transposeSet(2);
//		List<Integer> RTnSet = rowMatrix.retrogradeTransposeSet(3);
//		List<Integer> TnISet = rowMatrix.transposeInverseSet(4);
//		List<Integer> RTnISet = rowMatrix.retrogradeTransposeInverseSet(3);
//		System.out.println(TnSet);
//		System.out.println(RTnSet);
//		System.out.println(TnISet);
//		System.out.println(RTnISet);
//		List<List<Integer>> lists = new ArrayList<List<Integer>>();
//		lists.add(TnSet);
//		lists.add(set);
//		Score score = new Score();
//		int[] register1 = {48,48};
//		for (List<Integer> list : lists) {
////			System.out.println(list);		
//			Phrase phrase1 = generateSetMelody(list, register1, RhythmicFunctions.getRhythm2());
//			Part part1 = new Part();
//			part1.add(phrase1);
//			score.addPart(part1);
//			for (int i = 0; i < register1.length; i++) {
//				register1[i] = register1[i] + 12;
//			}
//		}
//		View.notate(score);
////	Write.midi(score, "twelveTone.mid");
//		Play.midi(score);
		
//		List<Integer> hexaSet1 = new ArrayList<Integer>();
//		hexaSet1.add(0);
//		hexaSet1.add(1);
//		hexaSet1.add(2);
//		hexaSet1.add(3);
//		hexaSet1.add(4);
//		hexaSet1.add(5);
//		List<Integer> hexaSet2 = new ArrayList<Integer>();
//		hexaSet2.add(6);
//		hexaSet2.add(7);
//		hexaSet2.add(8);
//		hexaSet2.add(9);
//		hexaSet2.add(10);
//		hexaSet2.add(11);
//		Collections.shuffle(hexaSet1);
//		Collections.shuffle(hexaSet2);
//		
//		
//		List<Integer> set = createRandomTwelveToneSet();
		List<Integer> hexaChordsetA = new ArrayList<Integer>();
		hexaChordsetA.add(60);
		hexaChordsetA.add(62);
		hexaChordsetA.add(64);
		hexaChordsetA.add(60);
		hexaChordsetA.add(67);
		hexaChordsetA.add(66);
		
//		List<Integer> hexaChordsetA = Arrays.asList(TwelveToneSets.allCombinatorialHexaChordB);
		List<Integer> inversedHex = inversion(hexaChordsetA);
		System.out.println(inversedHex);
		List<Integer> hexaChordsetB = transpose(inversedHex, 1);
		System.out.println(hexaChordsetB);
		List<Integer> set = ListUtils.union(hexaChordsetA, hexaChordsetB);
		
		RowMatrix rowMatrix = new RowMatrix(12, set);
		rowMatrix.show();
		System.out.println(rowMatrix.transposeSet(6));
//		System.out.println(rowMatrix.retrogradeTransposeSet(3));
		System.out.println(rowMatrix.transposeInverseSet(1));
//		System.out.println(rowMatrix.retrogradeTransposeInverseSet(3));
		
		System.out.println(set);
//		System.out.println(inversion(set));
//		System.out.println(retrograde(set));
//		List<List<Integer>> lists = Lists.partition(set, 6);
		Score score = new Score();
		score.setTempo(100.0);
		int[] register1 = {60,72};
//		for (List<Integer> list : lists) {
//			System.out.println(list);		
//			Phrase phrase1 = generateSetMelody(list, register1, RhythmicFunctions.getChordRhythmProbability());
//			Part part1 = new Part();
//			part1.add(phrase1);
//			score.addPart(part1);
//			for (int i = 0; i < register1.length; i++) {
//				register1[i] = register1[i] + 12;
//			}
//		}

		
		score = generateSetChords(hexaChordsetB, register1, 1.0, 3);
		Phrase phrase1 = generateSetMelody(hexaChordsetA, register1, 0);
		Part part1 = new Part();
		part1.add(phrase1);
		score.addPart(part1);
		
//		View.notate(score);
//		Write.midi(score, "twelveTone.mid");
//		Play.midi(score);
		
//		int[] register1 = {48,60};
//		int[] register2 = {60,72};
//		Phrase phrase1 = generateSetMelody(hexaSet1, register1, 1.0);
//		Phrase phrase2 = generateSetMelody(hexaSet2, register2, 4.0);
//		Part part1 = new Part();
//		part1.add(phrase1);
//		Part part2 = new Part();
//		part2.add(phrase2);
//		Score score = new Score();
//		score.setTempo(100.0);
//		score.addPart(part1);
//		score.addPart(part2);
//		View.notate(score);
////		Write.midi(score, "twelveTone.mid");
//		Play.midi(score);
	}

	private static List<Integer> createRandomTwelveToneSet() {
		List<Integer> set = new ArrayList<Integer>();
		for (int i = 0; i < 12; i++) {
			set.add(i);
		}

		Collections.shuffle(set);
		return set;
	}
	
	private static List<Integer> createRandomRow() {
		List<Integer> row = Arrays.asList(TwelveToneSets.twelveToneSet);
		Collections.shuffle(row);
		return row;
	}
	
	
	public static Phrase generateSetMelody(List<Integer> set, int[] register, double startTime) {
		Random random = new Random();
		Phrase phrase = new Phrase();
		for (Integer i : set) {
			int r = random.nextInt(register.length);
			int pitch = i + register[r];
			double rhythm = RhythmicFunctions.getRhythmProbability();
			Note note = new Note(pitch, rhythm);	
			phrase.add(note);
		}
		phrase.setStartTime(startTime);
		return phrase;	
	}
	
	public static List<Integer> inversion(List<Integer> set){
		List<Integer> inversion = new ArrayList<Integer>();
		Integer[] arr = new Integer[set.size()];
		arr = set.toArray(arr);
		Integer pc = set.get(0) % 12;
		inversion.add(pc);
		for (int i = 0; i < arr.length - 1; i++) {
			Integer interval = (arr[i+1] - arr[i]);
			pc = (pc - interval) % 12;
			if (pc < 0) {
				pc = 12 + pc;
			}
			inversion.add(pc);
		}
		return inversion;	
	}
	
	public static List<Integer> retrograde(List<Integer> set){
		List<Integer> retrograde = new ArrayList<Integer>(set);
		Collections.reverse(retrograde);
		return retrograde;
	}
	
	public static void randomMelody(){
		Random random = new Random();
		Score score = new Score();
		for (int i = 0; i < 4; i++) {
			Phrase phrase = new Phrase();
			Part part = new Part();
			double melodyLength = 8.0;
			double phraseLength = 0;
			while (phraseLength < melodyLength) {
				int r = random.nextInt(36);
				int pitch = 48 + r;
				double rhythm = RhythmicFunctions.getRhythm();
				double tempPhraseLength = phraseLength + rhythm;
				Note note = null;
				if (tempPhraseLength > melodyLength) {
					double lastRhythm = melodyLength - phraseLength;
					note = new Note(pitch, lastRhythm);
				} else {
					note = new Note(pitch, rhythm);
				}
				phrase.add(note);
				phraseLength = tempPhraseLength;
			}
			part.add(phrase);
			score.add(part);
		}
		View.notate(score);
		Write.midi(score, "random.mid");
		Play.midi(score);
	}
	
	public static List<Integer> transpose(List<Integer> set, int transpose){
		List<Integer> transposedList = new ArrayList<Integer>();
		for (Integer integer : set) {
			transposedList.add((integer + transpose) % 12);
		}
		return transposedList;
	}
	
	public static Score generateSetChords(List<Integer> set, int[] register, double startTime, int size) {
		Random random = new Random();
		Score score = new Score();
		List<List<Integer>> partitions = Lists.partition(set, size);

		List<Integer> firstList = partitions.get(0);
		double[] rhythms = new double[firstList.size()];
		int[] registers = new int[firstList.size()];
		int i = 0;
		Part part = new Part();
		Phrase phrase = new Phrase();
 		for (Integer integer : firstList) {
			int r = random.nextInt(register.length);
			double rhythm = RhythmicFunctions.getRhythm2();
			rhythms[i] = rhythm;
			registers[i] = register[r];
			i++;
			int pitch = integer + register[r];
			Note note = new Note(pitch, rhythm);
			phrase.add(note);
		}
 		phrase.setStartTime(startTime);
		part.add(phrase);
		score.add(part);

		int length = partitions.size();
		for (int j = 0; j < length - 1; j++) {
			List<Integer> list = partitions.get(j + 1);
			part = new Part();
			phrase = new Phrase();
			int f = 0;
			for (Integer integer : list) {
				int pitch = integer + registers[f];
				Note note = new Note(pitch, rhythms[f]);
				phrase.add(note);
				phrase.setStartTime(startTime);
				f++;
			}
			part.add(phrase);
			score.add(part);
		}
		return score;	
	}

}
