package be.util;

import static jm.constants.Pitches.b2;
import static jm.constants.Pitches.c3;
import static jm.constants.Pitches.d3;
import static jm.constants.Pitches.d4;
import static jm.constants.Pitches.e4;
import static jm.constants.Pitches.f4;
import static jm.constants.Pitches.g3;
import static jm.constants.Pitches.*;

import java.util.ArrayList;
import java.util.List;

import jm.music.data.Note;
import jm.music.data.Rest;

public class TestContrapunt {

	public static void main(String[] args) {
		
	}
	private static final int DYNAMIC = Note.DEFAULT_DYNAMIC;

	public static List<Note[]> passingToneOffBeat() {
		List<Note[]> melodies = new ArrayList<Note[]>();
		Note[] d1 = { new Note(e4, 1.0, DYNAMIC), new Note(f4, 1.0, DYNAMIC), new Note(g4, 1.0, DYNAMIC)};
		Note[] d2 = { new Note(g3, 1.0, DYNAMIC), new Note(g3, 1.0, DYNAMIC), new Note(d3, 1.0, DYNAMIC)};
		Note[] d3 = {new Note(c3, 1.0, DYNAMIC), new Note(c3, 1.0, DYNAMIC), new Note(b2, 1.0, DYNAMIC)};
		melodies.add(d1);
		melodies.add(d2);
		melodies.add(d3);
		return melodies;
	}
	
	public static List<Note[]> passingToneOnBeat() {
		List<Note[]> melodies = new ArrayList<Note[]>();
		Note[] d1 = { new Note(f4, 1.0, DYNAMIC), new Note(e4, 1.0, DYNAMIC), new Note(g4, 1.0, DYNAMIC) };
		Note[] d2 = {  new Note(g3, 1.0, DYNAMIC), new Note(g3, 1.0, DYNAMIC), new Note(d3, 1.0, DYNAMIC)};
		Note[] d3 = {new Note(c3, 1.0, DYNAMIC), new Note(c3, 1.0, DYNAMIC), new Note(b2, 1.0, DYNAMIC)};
		melodies.add(d1);
		melodies.add(d2);
		melodies.add(d3);
		return melodies;
	}
	
	public static List<Note[]> passingToneOffBeatRest() {
		List<Note[]> melodies = new ArrayList<Note[]>();
		Note[] d1 = { new Note(e4, 1.0, DYNAMIC), new Note(f4, 1.0, DYNAMIC), new Note(g4, 1.0, DYNAMIC)};
		Note[] d2 = { new Note(g3, 1.0, DYNAMIC), new Rest(1.0), new Note(d3, 1.0, DYNAMIC)};
		Note[] d3 = {new Note(c3, 1.0, DYNAMIC), new Note(c3, 1.0, DYNAMIC), new Note(b2, 1.0, DYNAMIC)};
		melodies.add(d1);
		melodies.add(d2);
		melodies.add(d3);
		return melodies;
	}
	
	public static List<Note[]> passingToneWindow() {
		List<Note[]> melodies = new ArrayList<Note[]>();
		Note[] d1 = { new Note(e4, 0.5, DYNAMIC),new Note(f4, 0.5, DYNAMIC), new Note(e4, 0.5, DYNAMIC), new Note(f4, 0.5, DYNAMIC), new Note(g4, 2.0, DYNAMIC)};
		Note[] d2 = { new Note(g3, 1.0, DYNAMIC), new Note(g3, 1.0, DYNAMIC), new Note(d3, 2.0, DYNAMIC)};
		Note[] d3 = {new Note(c3, 1.0, DYNAMIC), new Note(c3, 1.0, DYNAMIC), new Note(b2, 2.0, DYNAMIC)};
		melodies.add(d1);
		melodies.add(d2);
		melodies.add(d3);
		return melodies;
	}
	
	public static List<Note[]> zesAcht() {
		List<Note[]> melodies = new ArrayList<Note[]>();
		Note[] d1 = { new Note(e4, 0.5, DYNAMIC),new Note(f4, 0.5, DYNAMIC), new Note(e4, 0.5, DYNAMIC), new Note(e4, 0.5, DYNAMIC),new Note(f4, 0.5, DYNAMIC), new Note(e4, 0.5, DYNAMIC)};
		Note[] d2 = { new Note(g3, 0.5, DYNAMIC), new Note(g3, 0.5, DYNAMIC), new Note(d3, 0.5, DYNAMIC),new Note(g3, 0.5, DYNAMIC), new Note(g3, 0.5, DYNAMIC), new Note(d3, 0.5, DYNAMIC)};
		Note[] d3 = {new Note(c3, 0.5, DYNAMIC), new Note(c3, 0.5, DYNAMIC), new Note(b2, 0.5, DYNAMIC),new Note(c3, 0.5, DYNAMIC), new Note(c3, 0.5, DYNAMIC), new Note(b2, 0.5, DYNAMIC)};
		melodies.add(d1);
		melodies.add(d2);
		melodies.add(d3);
		return melodies;
	}
	
	public static List<Note[]> metricTest() {
		List<Note[]> melodies = new ArrayList<Note[]>();
		Note[] d1 = { new Note(e4, 2.0, DYNAMIC), new Note(f4, 0.5, DYNAMIC), new Note(g4, 0.5, DYNAMIC), new Note(g4, 0.25, DYNAMIC),  new Note(g4, 0.25, DYNAMIC)};
		melodies.add(d1);
		return melodies;
	}
	
	public static List<Note[]> tonalityContext() {
		List<Note[]> melodies = new ArrayList<Note[]>();
		Note[] d1 = { new Note(e4, 1.0, DYNAMIC), new Note(c4, 1.0, DYNAMIC), new Note(d4, 1.0, DYNAMIC), new Note(e4, 1.0, DYNAMIC) };
		Note[] d2 = {  new Note(g3, 1.0, DYNAMIC), new Note(a3, 1.0, DYNAMIC),  new Note(b3, 1.0, DYNAMIC), new Note(g3, 1.0, DYNAMIC)};
		Note[] d3 = {new Note(c3, 1.0, DYNAMIC), new Note(f3, 1.0, DYNAMIC), new Note(g3, 1.0, DYNAMIC), new Note(c3, 1.0, DYNAMIC)};
		melodies.add(d1);
		melodies.add(d2);
		melodies.add(d3);
		return melodies;
	}
	
	public static List<Note[]> tonalityContext2() {
		List<Note[]> melodies = new ArrayList<Note[]>();
		Note[] d1 = { new Note(d4, 1.0, DYNAMIC), new Note(e4, 1.0, DYNAMIC), new Note(c4, 1.0, DYNAMIC), new Note(e4, 1.0, DYNAMIC) };
		Note[] d2 = {  new Note(b3, 1.0, DYNAMIC), new Note(g3, 1.0, DYNAMIC),  new Note(a3, 1.0, DYNAMIC), new Note(g3, 1.0, DYNAMIC)};
		Note[] d3 = {new Note(g3, 1.0, DYNAMIC), new Note(c3, 1.0, DYNAMIC), new Note(f3, 1.0, DYNAMIC), new Note(c3, 1.0, DYNAMIC)};
		melodies.add(d1);
		melodies.add(d2);
		melodies.add(d3);
		return melodies;
	}
	
	public static List<Note[]> tonalityRegisterTest() {
		List<Note[]> melodies = new ArrayList<Note[]>();
		Note[] d1 = { new Note(d4, 1.0, DYNAMIC), new Note(e4, 1.0, DYNAMIC), new Note(c4, 1.0, DYNAMIC), new Note(c4, 1.0, DYNAMIC) };
		Note[] d2 = {  new Note(g3, 1.0, DYNAMIC), new Note(g3, 1.0, DYNAMIC),  new Note(a3, 1.0, DYNAMIC), new Note(g3, 1.0, DYNAMIC)};
		Note[] d3 = {new Note(b2, 1.0, DYNAMIC), new Note(c3, 1.0, DYNAMIC), new Note(f3, 1.0, DYNAMIC), new Note(e3, 1.0, DYNAMIC)};
		melodies.add(d1);
		melodies.add(d2);
		melodies.add(d3);
		return melodies;
	}
	
	public static List<Note[]> homoPhonic() {
		List<Note[]> melodies = new ArrayList<Note[]>();
		Note[] d1 = { new Note(g4, 1.0, DYNAMIC), new Note(a4, 1.0, DYNAMIC), new Note(d5, 1.0, DYNAMIC), new Note(c5, 1.0, DYNAMIC) };
		Note[] d2 = { new Note(e4, 1.0, DYNAMIC), new Note(e4, 1.0, DYNAMIC),  new Note(e4, 1.0, DYNAMIC), new Note(e4, 1.0, DYNAMIC) };
		Note[] d3 = {  new Note(g3, 1.0, DYNAMIC), new Note(g3, 1.0, DYNAMIC),     new Note(g3, 1.0, DYNAMIC), new Note(g3, 1.0, DYNAMIC)};
		Note[] d4 = {new Note(c3, 1.0, DYNAMIC), new Note(c3, 1.0, DYNAMIC), new Note(c3, 1.0, DYNAMIC), new Note(c3, 1.0, DYNAMIC)};
		melodies.add(d1);
		melodies.add(d2);
		melodies.add(d3);
		melodies.add(d4);
		return melodies;
	}
	
	public static List<Note[]> homoPhonicWithoutAccents() {
		List<Note[]> melodies = new ArrayList<Note[]>();
		Note[] d1 = { new Note(g4, 1.0, DYNAMIC), new Note(a4, 1.0, DYNAMIC), new Note(d5, 1.0, DYNAMIC), new Note(c5, 1.0, DYNAMIC) };
		Note[] d2 = { new Note(e4, 4.0, DYNAMIC) };
		Note[] d3 = {  new Note(g3, 4.0, DYNAMIC)};
		Note[] d4 = {new Note(c3, 4.0, DYNAMIC)};
		melodies.add(d1);
		melodies.add(d2);
		melodies.add(d3);
		melodies.add(d4);
		return melodies;
	}
	
	public static List<Note[]> offBeatRhythm() {
		List<Note[]> melodies = new ArrayList<Note[]>();
		Note[] d1 = { new Note(g4, 0.5, DYNAMIC), new Note(a4, 1.0, DYNAMIC), new Note(d5, 1.0, DYNAMIC), new Note(e5, 1.0, DYNAMIC), new Note(c5, 0.5, DYNAMIC) };
//		Note[] d2 = { new Note(e4, 4.0, DYNAMIC) };
//		Note[] d3 = {  new Note(g3, 4.0, DYNAMIC)};
//		Note[] d4 = {new Note(c3, 4.0, DYNAMIC)};
		melodies.add(d1);
//		melodies.add(d2);
//		melodies.add(d3);
//		melodies.add(d4);
		return melodies;
	}
	
	public static List<Note[]> lowestIntervalConstraint() {
		List<Note[]> melodies = new ArrayList<Note[]>();
		Note[] d1 = { new Note(g4, 1.0, DYNAMIC), new Note(a4, 1.0, DYNAMIC), new Note(d5, 1.0, DYNAMIC), new Note(c5, 1.0, DYNAMIC) };
		Note[] d2 = { new Note(d4, 1.0, DYNAMIC), new Note(e4, 1.0, DYNAMIC),  new Note(e4, 1.0, DYNAMIC), new Note(e4, 1.0, DYNAMIC) };
		Note[] de3 = { new Note(d3, 1.0, DYNAMIC), new Note(g3, 1.0, DYNAMIC),     new Note(g3, 1.0, DYNAMIC), new Note(g3, 1.0, DYNAMIC)};
		Note[] d4 = {new Note(b2, 1.0, DYNAMIC), new Note(c3, 1.0, DYNAMIC), new Note(c3, 1.0, DYNAMIC), new Note(c3, 1.0, DYNAMIC)};
		melodies.add(d1);
		melodies.add(d2);
		melodies.add(de3);
		melodies.add(d4);
		return melodies;
	}
	
	public static List<Note[]> rhythmConstraint() {
		List<Note[]> melodies = new ArrayList<Note[]>();
		Note[] d1 = { new Note(c5, 0.5, DYNAMIC), new Note(a4, 0.25, DYNAMIC), new Note(d5, 1.0, DYNAMIC), new Note(e5, 1.5, DYNAMIC), new Note(c5, 0.5, DYNAMIC) };
//		Note[] d2 = { new Note(e4, 4.0, DYNAMIC) };
//		Note[] d3 = {  new Note(g3, 4.0, DYNAMIC)};
//		Note[] d4 = {new Note(c3, 4.0, DYNAMIC)};
		melodies.add(d1);
//		melodies.add(d2);
//		melodies.add(d3);
//		melodies.add(d4);
		return melodies;
	}
	
}
