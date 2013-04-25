package be.util;

import java.util.Arrays;

import javax.xml.datatype.Duration;

import jm.JMC;
import jm.constants.RhythmValues;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.View;
import be.data.Scale;
import be.functions.RhythmicFunctions;

public class TestMelody implements JMC{

	private static final int START_PITCH = 60;

	public static void main(String[] args) {
//		
//		int[] pitches = atomicPhrase(phrase, 0.5);
//		System.out.println(Arrays.toString(pitches));
//		Phrase np = generateMelody(8, Scale.MAJOR_SCALE, phrase, 0.5);
//		pitches = atomicPhrase(np, 0.5);
//		System.out.println(Arrays.toString(pitches));
//		Phrase p = generateMelody(8, Scale.MAJOR_SCALE, np, 0.5);
//		pitches = atomicPhrase(p, 0.5);
//		System.out.println(Arrays.toString(pitches));
////		Part part = new Part(phrase);
////		Score score = new Score(part);
//		View.notate(phrase);
//		List<Integer> rhythmValueChanges = RhythmicFunctions.getRhythmValueChanges(phrase); 
//		List<Integer> melodicValueChanges = MelodicFunctions.getValueChanges(phrase);
//		
//		System.out.println(rhythmValueChanges);
//		System.out.println(melodicValueChanges);
	}
	
	public static Note[] melodyBroederJacob(){
		Note[] melody = new Note[32];
		melody[0] = new Note(c4,1.0);
		melody[1] = new Note(d4,1.0);
		melody[2] = new Note(e4,1.0);
		melody[3] = new Note(c4,1.0);
		melody[4] = new Note(c4,1.0);
		melody[5] = new Note(d4,1.0);
		melody[6] = new Note(e4,1.0);
		melody[7] = new Note(c4,1.0);
		
		melody[8] = new Note(e4,1.0);
		melody[9] = new Note(f4,1.0);
		melody[10] = new Note(g4,2.0);
		melody[11] = new Note(e4,1.0);
		melody[12] = new Note(f4,1.0);
		melody[13] = new Note(g4,2.0);
		
		melody[14] = new Note(g4,0.5);
		melody[15] = new Note(a4,0.5);
		melody[16] = new Note(g4,0.5);
		melody[17] = new Note(f4,0.5);
		melody[18] = new Note(e4,1.0);
		melody[19] = new Note(c4,1.0);
		
		melody[20] = new Note(g4,0.5);
		melody[21] = new Note(a4,0.5);
		melody[22] = new Note(g4,0.5);
		melody[23] = new Note(f4,0.5);
		melody[24] = new Note(e4,1.0);
		melody[25] = new Note(c4,1.0);
		
		melody[26] = new Note(c4,1.0);
		melody[27] = new Note(g3,1.0);
		melody[28] = new Note(c4,2.0);
		melody[29] = new Note(c4,1.0);
		melody[30] = new Note(g3,1.0);
		melody[31] = new Note(c4,2.0);
		return melody;
	}
	
	public static Note[] melodyInnerMetric(){
		Note[] melody = new Note[7];
		melody[0] = new Note(c5,0.5);
		melody[1] = new Note(g4,0.25);
		melody[2] = new Note(g4,0.25);
		melody[3] = new Note(af4,0.5);
		melody[4] = new Note(g4,1.0);
//		melody[5] = new Rest(0.5);
		melody[5] = new Note(g4,0.5);
		melody[6] = new Note(c5,1.0);
		return melody;		
	}
	
	public static Note[] melodyMozart(){
		Note[] melody = new Note[12];
		melody[0] = new Note(g4,0.5);
		melody[1] = new Note(d5,1.0);
		melody[2] = new Note(g5,1.0);
		melody[3] = new Note(g5,1.0);
		melody[4] = new Note(fs5,0.5);
		melody[5] = new Note(e5,0.5);
		melody[6] = new Note(d5,0.5);
		melody[7] = new Note(ds5,0.5);
		melody[8] = new Note(e5,0.5);
		melody[9] = new Note(b4,0.5);
		melody[10] = new Note(d5,1.0);
		melody[11] = new Note(c5,1.0);
		return melody;	
	}
	
	public static Note[] repeatingMelody(){
		Note[] melody = new Note[12];
		melody[0] = new Note(g4,0.5);
		melody[1] = new Note(g5,1.0);
		melody[2] = new Note(g5,1.0);
		melody[3] = new Note(g5,1.0);
		melody[4] = new Note(es5,0.5);
		melody[5] = new Note(e5,0.5);
		melody[6] = new Note(e5,0.5);
		melody[7] = new Note(e5,0.5);
		melody[8] = new Note(e5,0.5);
		melody[9] = new Note(b4,0.5);
		melody[10] = new Note(b5,1.0);
		melody[11] = new Note(b5,1.0);
		return melody;	
	}
	
	public static Note[] melodyWebern(){
		Note[] melody = new Note[12];
		melody[0] = new Note(g4,1.0);
		melody[1] = new Note(e4,1.0);
		melody[2] = new Note(ds5,0.5);
		melody[3] = new Note(fs5,1.0);
		melody[4] = new Note(cs5,RhythmValues.QUARTER_NOTE_TRIPLET);
		melody[5] = new Note(f4,RhythmValues.HALF_NOTE_TRIPLET);
		melody[6] = new Note(d4,1.0);
		melody[7] = new Note(b3,0.5);
		melody[8] = new Note(bf4,0.5);
		melody[9] = new Note(c5,1.5);
		melody[10] = new Note(a4,0.5);
		melody[11] = new Note(gs5,1.0);
		return melody;	
	}
	
	public static Note[] melodyMaxInnerMetric(){
		Note[] melody = new Note[8];
		melody[0] = new Note(c4,1.0);
		melody[1] = new Note(c4,1.0);
		melody[2] = new Note(c4,1.0);
		melody[3] = new Note(c4,1.0);
		melody[4] = new Note(c4,1.0);
		melody[5] = new Note(c4,1.0);
		melody[6] = new Note(c4,1.0);
		melody[7] = new Note(c4,1.0);
		return melody;	
	}
	
	public static Note[] melodyMinSyncopeInnerMetric(){
		Note[] melody = new Note[8];
		melody[0] = new Note(c4,0.5);
		melody[1] = new Note(c4,1.0);
		melody[2] = new Note(c4,1.0);
		melody[3] = new Note(c4,1.0);
		melody[4] = new Note(c4,1.0);
		melody[5] = new Note(c4,1.0);
		melody[6] = new Note(c4,1.0);
		melody[7] = new Note(c4,0.5);
		return melody;	
	}
	
	public static Note[] melodyDiffRhythms(){
		Note[] melody = new Note[8];
		melody[0] = new Note(c4,0.5);
		melody[1] = new Note(c4,1.0);
		melody[2] = new Note(c4,1.5);
		melody[3] = new Note(c4,0.5);
		melody[4] = new Note(c4,2.0);
		melody[5] = new Note(c4,0.25);
		melody[6] = new Note(c4,0.75);
		melody[7] = new Note(c4,1.0);
		return melody;	
	}
	
	public static Note[] melodyTest(){
		Note[] melody = new Note[8];
		melody[0] = new Note(c4,1.0);
		melody[1] = new Note(d4,1.0);
		melody[2] = new Note(e4,1.0);
		melody[3] = new Note(c4,1.0);
		melody[4] = new Note(c4,1.0);
		melody[5] = new Note(d4,1.0);
		melody[6] = new Note(A5,1.0);
		melody[7] = new Note(c4,1.0);
		return melody;
	}
	

}
