package be.util;

import java.util.ArrayList;
import java.util.List;

import be.data.Motive;
import be.data.NotePos;


public class TestPopulation {

	public static List<Motive> melody(){
		List<Motive> list = new ArrayList<Motive>();
		List<NotePos> melody = new ArrayList<NotePos>();
		melody.add(new NotePos(60,0, 6));
		melody.add(new NotePos(Integer.MIN_VALUE,6, 6));
		melody.add(new NotePos(62,12, 12));
		melody.add(new NotePos(64,24, 12));
		melody.add(new NotePos(60,36, 12));
		melody.add( new NotePos(62,48, 12));
		melody.add(new NotePos(64,60, 12));
		melody.add(new NotePos(65,72, 24));
		Motive motive = new Motive(melody, 96);
		list.add(motive);
		return list;	
	}
	
	public static List<Motive> melody2(){
		List<Motive> list = new ArrayList<Motive>();
		List<NotePos> melody = new ArrayList<NotePos>();
		melody.add(new NotePos(48,0, 24));
		melody.add(new NotePos(Integer.MIN_VALUE,24, 12));
		melody.add(new NotePos(50,36, 12));
		melody.add( new NotePos(52,48, 48));
		Motive motive = new Motive(melody, 96);
		list.add(motive);
		return list;	
	}
	
	public static List<Motive> passingNote(){
		List<Motive> list = new ArrayList<Motive>();
		List<NotePos> melody = new ArrayList<NotePos>();
		melody.add(new NotePos(76,0, 12));
		melody.add(new NotePos(74,12, 12));
		melody.add(new NotePos(72,24, 24));
		Motive motive = new Motive(melody, 48);
		list.add(motive);
		
		List<NotePos> melody2 = new ArrayList<NotePos>();
		melody2.add(new NotePos(60,0, 24));
		melody2.add(new NotePos(64,24, 24));
		motive = new Motive(melody2, 48);
		list.add(motive);
		return list;	
	}
	
	public static List<Motive> passingToneOffBeat() {
		List<Motive> list = new ArrayList<Motive>();
		List<NotePos> melody = new ArrayList<NotePos>();
		int octave = 0;
		melody.add(new NotePos(76 + octave,0, 12, 0));
		melody.add(new NotePos(77+ octave,12, 12, 0));
		melody.add(new NotePos(79+ octave,24, 24, 0));
		Motive motive = new Motive(melody, 48);
		list.add(motive);
		
		List<NotePos> melody2 = new ArrayList<NotePos>();
		melody2.add(new NotePos(67+ octave,0, 12, 1));
		melody2.add(new NotePos(67+ octave,12, 12, 1));
		melody2.add(new NotePos(62+ octave,24, 24, 1));
		motive = new Motive(melody2, 48);
		list.add(motive);
		
		List<NotePos> melody3 = new ArrayList<NotePos>();
		melody3.add(new NotePos(60+ octave,0, 12, 2));
		melody3.add(new NotePos(60+ octave,12, 12, 2));
		melody3.add(new NotePos(59+ octave,24, 24, 2));
		motive = new Motive(melody3, 48);
		list.add(motive);
		
		return list;
	}
	
	public static List<Motive> passingToneOnBeat() {
		List<Motive> list = new ArrayList<Motive>();
		List<NotePos> melody = new ArrayList<NotePos>();
		melody.add(new NotePos(77,0, 12, 0));
		melody.add(new NotePos(76,12, 12, 0));
		melody.add(new NotePos(79,24, 24, 0));
		Motive motive = new Motive(melody, 48);
		list.add(motive);
		
		List<NotePos> melody2 = new ArrayList<NotePos>();
		melody2.add(new NotePos(67,0, 12, 1));
		melody2.add(new NotePos(67,12, 12, 1));
		melody2.add(new NotePos(62,24, 24, 1));
		motive = new Motive(melody2, 48);
		list.add(motive);
		
		List<NotePos> melody3 = new ArrayList<NotePos>();
		melody3.add(new NotePos(60,0, 12, 2));
		melody3.add(new NotePos(60,12, 12, 2));
		melody3.add(new NotePos(59,24, 24, 2));
		motive = new Motive(melody3, 48);
		list.add(motive);
		
		return list;
	}
	
	
	public static List<Motive> melodyB(){
		List<Motive> list = new ArrayList<Motive>();
		List<NotePos> melody = new ArrayList<NotePos>();
		melody.add(new NotePos(60,0, 6));
		melody.add(new NotePos(62,18, 6));
		melody.add(new NotePos(64,24, 12));
		melody.add(new NotePos(60,36, 12));
		melody.add( new NotePos(62,48, 12));
		melody.add(new NotePos(64,60, 12));
		melody.add(new NotePos(65,72, 24));
		Motive motive = new Motive(melody, 96);
		list.add(motive);
		return list;	
	}
	
	public static List<Motive> melodyB2(){
		List<Motive> list = new ArrayList<Motive>();
		List<NotePos> melody = new ArrayList<NotePos>();
		melody.add(new NotePos(48,0, 12));
		melody.add(new NotePos(49,12, 12));
		melody.add(new NotePos(50,36, 12));
		melody.add( new NotePos(52,48, 48));
		Motive motive = new Motive(melody, 96);
		list.add(motive);
		return list;	
	}
	
	public static List<Motive> cChord3(){
		List<Motive> list = new ArrayList<Motive>();
		List<NotePos> melody = new ArrayList<NotePos>();
		melody.add(new NotePos(60,0, 12, 0));
		melody.add(new NotePos(60,12, 12, 0));
//		melody.add(new NotePos(60,24, 12));
		Motive motive = new Motive(melody, 24);
		list.add(motive);
		
		List<NotePos> melody2 = new ArrayList<NotePos>();
		melody2.add(new NotePos(64,0, 18, 1));
		melody2.add(new NotePos(64,18, 6, 1));
//		melody2.add(new NotePos(64,24, 12));
		Motive motive2 = new Motive(melody2, 24);
		list.add(motive2);
		
		List<NotePos> melody3 = new ArrayList<NotePos>();
		melody3.add(new NotePos(67,0, 6, 2));
		melody3.add(new NotePos(67,6, 18, 2));
//		melody3.add(new NotePos(67,24, 12));
		Motive motive3 = new Motive(melody3, 24);
		list.add(motive3);
		return list;	
	}
	
	public static List<Motive> suspension() {
		List<Motive> list = new ArrayList<Motive>();
		List<NotePos> melody = new ArrayList<NotePos>();
		melody.add(new NotePos(72,0, 36, 0));
		melody.add(new NotePos(71,36, 12, 0));
		Motive motive = new Motive(melody , 48);
		list.add(motive);
		
		List<NotePos> melody2 = new ArrayList<NotePos>();
		melody2.add(new NotePos(67,0, 24, 1));
		melody2.add(new NotePos(62,24, 24, 1));
		motive = new Motive(melody2, 48);
		list.add(motive);
		
		List<NotePos> melody3 = new ArrayList<NotePos>();
		melody3.add(new NotePos(52,0, 24, 2));
		melody3.add(new NotePos(55,24, 24, 2));
		motive = new Motive(melody3, 48);
		list.add(motive);
		
		return list;
	}
	
	public static List<Motive> noSuspension() {
		List<Motive> list = new ArrayList<Motive>();
		List<NotePos> melody = new ArrayList<NotePos>();
		melody.add(new NotePos(72,0, 24, 0));
		melody.add(new NotePos(71,24, 24, 0));
		Motive motive = new Motive(melody, 48);
		list.add(motive);
		
		List<NotePos> melody2 = new ArrayList<NotePos>();
		melody2.add(new NotePos(67,0, 24, 1));
		melody2.add(new NotePos(62,24, 24, 1));
		motive = new Motive(melody2, 48);
		list.add(motive);
		
		List<NotePos> melody3 = new ArrayList<NotePos>();
		melody3.add(new NotePos(52,0, 24, 2));
		melody3.add(new NotePos(55,24, 24, 2));
		motive = new Motive(melody3, 48);
		list.add(motive);
		
		return list;
	}
	
	public static List<Motive> suspensionAccent() {
		List<Motive> list = new ArrayList<Motive>();
		List<NotePos> melody = new ArrayList<NotePos>();
		melody.add(new NotePos(72,0, 24, 0));
		melody.add(new NotePos(72,24, 12, 0));
		melody.add(new NotePos(71,36, 12, 0));
		Motive motive = new Motive(melody, 48);
		list.add(motive);
		
		List<NotePos> melody2 = new ArrayList<NotePos>();
		melody2.add(new NotePos(67,0, 24, 1));
		melody2.add(new NotePos(62,24, 24, 1));
		motive = new Motive(melody2, 48);
		list.add(motive);
		
		List<NotePos> melody3 = new ArrayList<NotePos>();
		melody3.add(new NotePos(52,0, 24, 2));
		melody3.add(new NotePos(55,24, 24, 2));
		motive = new Motive(melody3, 48);
		list.add(motive);
		
		return list;
	}
	
	public static List<Motive> pedaalToon() {
		List<Motive> list = new ArrayList<Motive>();
		List<NotePos> melody = new ArrayList<NotePos>();
		melody.add(new NotePos(76,0, 12, 0));
		melody.add(new NotePos(74,12, 12, 0));
		melody.add(new NotePos(76,24, 24, 0));
		Motive motive = new Motive(melody, 48);
		list.add(motive);
		
		List<NotePos> melody2 = new ArrayList<NotePos>();
		melody2.add(new NotePos(67,0, 12, 1));
		melody2.add(new NotePos(71,12, 12, 1));
		melody2.add(new NotePos(67,24, 24, 1));
		motive = new Motive(melody2, 48);
		list.add(motive);
		
		List<NotePos> melody3 = new ArrayList<NotePos>();
		melody3.add(new NotePos(60,0, 48, 2));
		motive = new Motive(melody3, 48);
		list.add(motive);
		
		return list;
	}
	
	public static List<Motive> randomMelody() {
		List<Motive> list = new ArrayList<Motive>();
		List<NotePos> melody = new ArrayList<NotePos>();
		melody.add(new NotePos(76,0, 6, 0));
		melody.add(new NotePos(74,6, 6, 0));
		melody.add(new NotePos(72,12, 6, 0));
		melody.add(new NotePos(72,18, 6, 0));
		melody.add(new NotePos(74,24, 12, 0));
		melody.add(new NotePos(77,36, 12, 0));
		melody.add(new NotePos(69,48, 12, 0));
		melody.add(new NotePos(72,60, 12, 0));
		melody.add(new NotePos(76,72, 24, 0));
		Motive motive = new Motive(melody, 96);
		list.add(motive);
		return list;
	}
	
	public static List<Motive> singleNoteMel() {
		List<Motive> list = new ArrayList<Motive>();
		List<NotePos> melody = new ArrayList<NotePos>();
		melody.add(new NotePos(76,0, 12, 0));
		melody.add(new NotePos(74,12, 12, 0));
		Motive motive = new Motive(melody, 24);
		list.add(motive);
		
		List<NotePos> melody2 = new ArrayList<NotePos>();
		melody2.add(new NotePos(67,12, 12, 1));
		motive = new Motive(melody2, 12);
		list.add(motive);
		return list;
	}
	
	public static List<Motive> testLength() {
		List<Motive> list = new ArrayList<Motive>();
		List<NotePos> melody = new ArrayList<NotePos>();
		melody.add(new NotePos(74,0, 24, 0));
		melody.add(new NotePos(72,24, 12, 0));
		melody.add(new NotePos(71,36, 12, 0));
		Motive motive = new Motive(melody, 48);
		list.add(motive);
		
		List<NotePos> melody2 = new ArrayList<NotePos>();
		melody2.add(new NotePos(67,0, 12, 1));
		melody2.add(new NotePos(62,24, 6, 1));
		motive = new Motive(melody2, 48);
		list.add(motive);
		
		List<NotePos> melody3 = new ArrayList<NotePos>();
		melody3.add(new NotePos(52,0, 6, 2));
		melody3.add(new NotePos(55,24, 12, 2));
		motive = new Motive(melody3, 48);
		list.add(motive);
		
		return list;
	}
	
	
}
