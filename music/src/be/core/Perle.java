package be.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.View;

public class Perle {

	public static void main(String[] args) {
		List<Integer> pCycle = pCycle(7, 0);
		List<Integer> piCycle = piCycle(7, 7, 0);
//		List<Integer> ipCycle = ipCycle(7, 7, 0);
//		Collections.rotate(ipCycle, 1);
		System.out.println(piCycle);
//		System.out.println(ipCycle);
		
		Score score = new Score();
		Part piPart = addListToPart(piCycle, 60);
		score.add(piPart);
//		Part ipPart = addListToPart(ipCycle, 72);
//		score.add(ipPart);
		View.notate(score);
		Play.midi(score, false);
		
	}

	private static Part addListToPart(List<Integer> piCycle, int pitch) {
		Phrase phrase = new Phrase();
		for (int pc : piCycle) {		
			Note note = new Note(pc + pitch,1.0);
			phrase.add(note);		
		}
		Part part = new Part(phrase);
		return part;
	}
	
	public static List<Integer> pCycle(int cycle, int start){
		int pc = start;
		List<Integer> list = new ArrayList<Integer>();
		while (!list.contains(pc)){
			list.add(pc);
			pc = (pc  + cycle) % 12;
		}
		return list;	
	}
	
	public static List<Integer> iCycle(int cycle, int start){
		int pc = start;
		List<Integer> list = new ArrayList<Integer>();
		while (!list.contains(pc)){
			list.add(pc);
			pc = (pc - cycle + 12 )% 12;
		}
		return list;	
	}
	
	public static List<Integer> piCycle(int pCycleInt, int iCycleInt, int start){
		List<Integer> piCycle = new ArrayList<Integer>();
		List<Integer> pCycle = pCycle(pCycleInt, start);
		List<Integer> iCycle = iCycle(iCycleInt, start);
		int l = pCycle.size();
		for (int i = 0; i < l; i++) {
			piCycle.add(pCycle.get(i));
			piCycle.add(iCycle.get(i));
		}
		return piCycle;	
	}
	
	public static List<Integer> ipCycle(int pCycleInt, int iCycleInt, int start){
		List<Integer> piCycle = new ArrayList<Integer>();
		List<Integer> pCycle = pCycle(pCycleInt, start);
		List<Integer> iCycle = iCycle(iCycleInt, start);
		int l = pCycle.size();
		for (int i = 0; i < l; i++) {
			piCycle.add(iCycle.get(i));
			piCycle.add(pCycle.get(i));
		}
		return piCycle;	
	}
	
//	public static List<Integer>[] alignPCycleICycle(int cycle, int start, int shift){
//		List<Integer>[] alignedCycle = new int[2][12];
//		List<Integer> pCycle = pCycle(cycle, start);
//		List<Integer> iCycle = iCycle(cycle, start + shift);
//		for (int i = 0; i < pCycle.length; i++) {
//			alignedCycle[0][i] = pCycle[i];
//			alignedCycle[1][i] = iCycle[i];
//		}
//		return alignedCycle;
//	}
//	
//	public static List<Integer>[] alignPiCycleIpCycle(int cycle, int start, int shift){
//		List<Integer>[] alignedCycle = new int[2][24];
//		List<Integer> piCycle = piCycle(cycle, start);
//		List<Integer> ipCycle = ipCycle(cycle, start + shift);
//		rotateRight(ipCycle);
//		for (int i = 0; i < piCycle.length; i++) {
//			alignedCycle[0][i] = piCycle[i];
//			alignedCycle[1][i] = ipCycle[i];
//		}
//		return alignedCycle;
//	}
//	
//	public static void rotateRight(List<Integer> theArray) {
//      int a = theArray[theArray.length - 1];
//      int i;
//      for(i = theArray.length - 1; i > 0; i--)
//      theArray[i] = theArray[i-1];
//      theArray[i]= a;
//	}
//	
//	public static void rotateLeft(double[] theArray) {
//      double a = theArray[0];
//      int i;
//      for(i = 0; i < theArray.length-1; i++)
//      theArray[i] = theArray[i+1];
//      theArray[i]= a;
//	}
}
