package be.analyzer;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jm.music.data.Note;
import be.functions.HarmonicFunctions;

public class HarmonyAnalyzer {
	
	public static void main(String[] args) {
//		HarmonyAnalyzer analyzer = new HarmonyAnalyzer();
//		int[] pitches = new int[3];
//		pitches[0] = 0;
//		pitches[1] = 4;
//		pitches[2] = 7;
//		analyzer.analyzeHarmony(pitches);
//		System.out.println("major " + analyzer.getSonance());
//		System.out.println(analyzer.getRoot());
//
//		analyzer = new HarmonyAnalyzer();
//		pitches = new int[3];
//		pitches[0] = 0;
//		pitches[1] = 3;
//		pitches[2] = 7;
//		analyzer.analyzeHarmony(pitches);
//		System.out.println("minor " + analyzer.getSonance());
//		System.out.println(analyzer.getRoot());
		
		List<Note[]> chords3 = HarmonicFunctions.generate3PartChords();
		List<HarmonyAnalyzer> chordList = new ArrayList<HarmonyAnalyzer>();
		for (Note[] notes : chords3) {
			int[] noteArray = new int[3];
			HarmonyAnalyzer analyzer = new HarmonyAnalyzer();
			for (int i = 0; i < notes.length; i++) {
				int p = notes[i].getPitch() % 12;
				noteArray[i] = p;
			}
			analyzer.analyzeHarmony(noteArray);
			chordList.add(analyzer);
			Collections.sort(chordList, new SonanceComparator());
		}
		
		for (HarmonyAnalyzer harmonyAnalyzer : chordList) {
			int[] chord = harmonyAnalyzer.getChord();
			for (int i : chord) {
				Note note = new Note(i, 1.0);
				System.out.print(note.getNote());
			}
			System.out.print(" Sonance:  " + harmonyAnalyzer.getSonance() +  " ");
			System.out.print( Arrays.toString(harmonyAnalyzer.getChord()));
			System.out.println(" Root: " + harmonyAnalyzer.getRoot());
		}
		
		
//		try {
//		File file = new File("3PartChordsSonance.txt");
//		PrintWriter pw = new PrintWriter(file);
//		for (HarmonyAnalyzer harmonyAnalyzer : chordList) {
//			int[] chord = harmonyAnalyzer.getChord();
//			for (int i : chord) {
//				Note note = new Note(i, 1.0);
//				pw.print(note.getNote());
//			}
//			pw.print(" Sonance:  " + harmonyAnalyzer.getSonance() +  " ");
//			pw.print( Arrays.toString(harmonyAnalyzer.getChord()));
//			pw.println(" Root: " + harmonyAnalyzer.getRoot());
//		}
//		pw.println();
//		pw.flush(); 
//		pw.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
	}


//	Vector keys = new Vector();
//	Vector whiteKeys = new Vector();
	int b;
	double factor, sumf, sump, sonance, vp;
	String[] pitch = { "c", "c#", "d", "d#", "e", "f", "f#", "g", "g#", "a",
			"a#", "b", "c", "c#", "d", "d#", "e", "f", "f#", "g", "g#", "a",
			"a#", "b", "c", "c#", "d", "d#", "e", "f", "f#", "g", "g#", "a",
			"a#", "b", "c", "c#", "d", "d#", "e", "f", "f#", "g", "g#", "a",
			"a#", "b", "c", "c#", "d", "d#", "e", "f", "f#", "g", "g#", "a",
			"a#", "b", "c", "c#", "d", "d#", "e", "f", "f#", "g", "g#", "a",
			"a#", "b", "c", "c#", "d", "d#", "e", "f", "f#", "g", "g#", "a",
			"a#", "b", "c", "c#", "d", "d#", "e", "f", "f#", "g", "g#", "a",
			"a#", "b", "c", "c#", "d", "d#", "e", "f", "f#", "g", "g#", "a",
			"a#", "b", "c" };
//	int[] keyNumber = { 1, 3, 6, 8, 10, 13, 15, 18, 20, 22, 0, 2, 4, 5, 7, 9,
//			11, 12, 14, 16, 17, 19, 21, 23, 24 };
//	int[] inverseKeyNumber = { 10, 0, 11, 1, 12, 13, 2, 14, 3, 15, 4, 16, 17,
//			5, 18, 6, 19, 20, 7, 21, 8, 22, 9, 23, 24 };
	private static final int defaultValue = 100;
	String[] newPitch = new String[defaultValue];
	String[] pitchPrint = new String[defaultValue];
	double[][] virtualPitch = new double[defaultValue][6];
	double[] virtuality = new double[defaultValue];
	double[] newVirtuality = new double[defaultValue];
	double[] virtualityPrint = new double[defaultValue];
	double[] virtualityFinal = new double[defaultValue];
	private StringBuffer results = new StringBuffer();
	private int[] chord;
	
	public void analyzeHarmony(Set<Integer> pitches) {
		int[] p = new int[pitches.size()];
		Iterator<Integer> it = pitches.iterator();
		int i = 0;
		while (it.hasNext()) {
			Integer integer = (Integer) it.next();
			p[i] = integer;
			i++;
		}
		analyzeHarmony(p);
	}

	public void analyzeHarmony(int[] pitches) {
		chord = pitches;
		int totalChordNotes = pitches.length;
		int n = 0;
		sumf = 0;
		sump = 0;
		double m = 0;

		for (int i = 0; i < defaultValue; i++) {

			// i to how many notes there are

			virtuality[i] = 0;
			newVirtuality[i] = 0;
			virtualityPrint[i] = 0;
			newPitch[i] = "";
			pitchPrint[i] = "";
			for (int k = 0; k < 6; k++)
				virtualPitch[i][k] = 0;
		}

		results.append("\n");
		results.append("Data for the chord: ");
		for (int i = 0; i < totalChordNotes; i++) {
			results.append(pitch[pitches[i]] + " ");	
		}
		results.append("\n");
		int p = 0;
		int c = 0;
		 for (int i = 0; i < defaultValue; i++) {
             
             //i to how many notes there are
            
             if(c < totalChordNotes && pitches[c] == i){
//             int k = inverseKeyNumber[i];
//             if (((Key)(keys.get(k))).isNoteOn()) {
                 virtualPitch[p][0] = Math.IEEEremainder(i, 12);//candidate 1 
                 if (virtualPitch[p][0] < 0) {
                     virtualPitch[p][0] = 12 + virtualPitch[p][0];
                 }
//                 System.out.println("virtualPitch[" + p + ",0] = " + virtualPitch[p][0]);
                 virtualPitch[p][1] = Math.IEEEremainder(i + 5, 12);
                 if (virtualPitch[p][1] < 0) {
                     virtualPitch[p][1] = 12 + virtualPitch[p][1];
                 }
//                 System.out.println("virtualPitch[" + p + ",1] = " + virtualPitch[p][1]);
                 virtualPitch[p][2] = Math.IEEEremainder(i + 8, 12);
                 if (virtualPitch[p][2] < 0) {
                     virtualPitch[p][2] = 12 + virtualPitch[p][2];
                 }
//                 System.out.println("virtualPitch[" + p + ",2] = " + virtualPitch[p][2]);
                 virtualPitch[p][3] = Math.IEEEremainder(i + 2, 12);
                 if (virtualPitch[p][3] < 0) {
                     virtualPitch[p][3] = 12 + virtualPitch[p][3];
                 }
//                 System.out.println("virtualPitch[" + p + ",3] = " + virtualPitch[p][3]);
                 virtualPitch[p][4] = Math.IEEEremainder(i + 10, 12);
                 if (virtualPitch[p][4] < 0) {
                     virtualPitch[p][4] = 12 + virtualPitch[p][4];
                 }
//                 System.out.println("virtualPitch[" + p + ",4] = " + virtualPitch[p][4]);
                 virtualPitch[p][5] = Math.IEEEremainder(i + 1, 12);
                 if (virtualPitch[p][5] < 0) {
                     virtualPitch[p][5] = 12 + virtualPitch[p][5];
                 }
//                 System.out.println("virtualPitch[" + p + ",5] = " + virtualPitch[p][5]);
                 p++;
                 c++;
             }
         }
         for (int i = 0; i < 12; i++) {// loop over 12 notes
             m = 0;
             for (int k = 0; k < totalChordNotes; k++) {// j = tones in chord
                 m++;//position of tone in chord
                 for (int l = 0; l < 6; l++) {// l = value b at place i
                     if (i == virtualPitch[k][l]) {
                         virtuality[i] = virtuality[i] + ((36 - Math.pow(l, 2)) * Math.sqrt(1.0 / m));// sum
//                         System.out.println("* k = " + k + ", l = " + l +
//                              ", virtuality[" + i + "] = " + virtuality[i]);
                      }
                 }
             }
             virtuality[i] = virtuality[i] / (totalChordNotes * 6);// /n*g
//             System.out.println("virtuality[" + i + "] = " + virtuality[i]);
         }
         for (int i = 0; i < 12; i++) {
             if (virtuality[i] != 0) {
                 newVirtuality[n] = virtuality[i];
                 newPitch[n] = pitch[i];
//                 System.out.println("newPitch[" + n + "] = " + newPitch[n]);
                 n++;// amount of virtual pitches
             }
         }
         for (int i = 0; i < n; i++) {
             int o = 0;
             for (int k = 0; k < n; k++) {
                 if (newVirtuality[i] < newVirtuality[k]) {
                     o++;
                 }
             }
             virtualityFinal[o] = newVirtuality[i];
             virtualityPrint[o] = Math.round(100 * newVirtuality[i]);
             pitchPrint[o] = newPitch[i];
         }
         for (int i = 0; i < n; i++) {
             results.append("Virtuality of: " + pitchPrint[i] + " = " +
                            Double.toString(virtualityPrint[i]/100) + " Hh" + "\n");
         }
         //sonance
         m = 0;
         for (int i = 0; i < totalChordNotes; i++) {
             m++;
             sumf = sumf + Math.sqrt(1 / m);
         }
//         System.out.println("sumf = " + sumf);
         factor = totalChordNotes / sumf;
         for (int i = 0; i < n; i++) {
             sump = sump + virtualityFinal[i];
         }
         vp = virtualityFinal[0] / sump;//max virtual pitch / sum virtual pitch
         sonance = factor * (virtualityFinal[0] / (6 + Math.pow(n, 2)
            / 6 * Math.sqrt(Math.abs(1 - Math.pow(vp / 0.2236024844720497, 2)))));
         sonance = Math.round(1000*sonance);
         results.append("Sonance Factor is: " + Double.toString(sonance/1000) + " Sh" + "\n" + "\n");
//         System.out.println(results);
         for (int i = 0; i <= n; i++) {
             newVirtuality[i] = 0;
         }
     }
	


	public int[] getChord() {
		return chord;
	}



	public void setChord(int[] chord) {
		this.chord = chord;
	}



	public double getSonance() {
		return sonance;
	}

	public void setSonance(double sonance) {
		this.sonance = sonance;
	}

	@Override
	public String toString() {
		return "HarmonyAnalyzer [results=" + results + "]";
	}

	public String getRoot() {
		return pitchPrint[0];
	}
	
    private static int[] toPrimitive(Integer[] array) {  
    	int[] result = new int[array.length];  
        for (int i = 0; i < array.length; i++) {  
            result[i] = array[i].intValue();  
        }  
        return result;  
    }  

}
