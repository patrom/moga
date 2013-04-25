package be.util;

import java.util.Random;

import be.data.HarmonyObject;
import be.data.MusicalObject;
import be.data.Scale;

/**
 * @author PaRm
 *
 */
public class MusicalObjectUtilities {

	//TODO configuration!
	private static final double ATOMIC_VALUE = 12;
	private static final int UPPER_LIMIT_PITCH = 84;
	private static final int LOWER_LIMIT_PITCH = 40;
	private static Random random = new Random(System.currentTimeMillis());
	
	public static void main(String[] args) {
		HarmonyObject[] object = new HarmonyObject[3];
		HarmonyObject o1 = new HarmonyObject();
		o1.setPitch(69);
		o1.setPitchClass(9);
		o1.setOctave(5);
		object[0] = o1;
		HarmonyObject o2 = new HarmonyObject();
		o2.setPitch(65);
		o2.setPitchClass(5);
		o2.setOctave(5);
		object[1] = o2;
		
		HarmonyObject o3 = new HarmonyObject();
		o3.setPitch(60);
		o3.setPitchClass(0);
		o3.setOctave(5);
		object[2] = o3;
		for (int i = 0; i < 1000; i++) {
			new MusicalObjectUtilities().oneNoteMutation(object,random.nextInt(object.length), Scale.MAJOR_SCALE);
			for (int j = 0; j < object.length; j++) {
				System.out.print(object[j].getPitch() + ",");
			}
			System.out.println();
		}
	
	}
	
//	private static MusicalObjectUtilities instance = null;
//
//	public static MusicalObjectUtilities getInstance(){
//		if (instance == null) {
//			instance = new MusicalObjectUtilities();
//		}
//		return instance;
//	}
	
	public MusicalObjectUtilities() {
	}
	
	/**
	 * Moves one note of a chord (musical object) up or down by one step, avoiding crossing
	 * @param musicalObject The chord (pitches has to sorted descending)
	 * @param melodyIndex 
	 * @param scale The next or previous step in the given scale
	 * @return 
	 */
	public void oneNoteMutation(HarmonyObject[] musicalObject, int melodyIndex, int[] scale) {
		if (random.nextBoolean()) {
			mutateChordPreviousPitch(musicalObject, melodyIndex, scale);
		} else {
			mutateChordNextPitch(musicalObject, melodyIndex, scale);
		}
	}
	
	
	public void mutateChordPreviousPitch(HarmonyObject[] musicalObject, int melodyIndex, int[] scale) {
		HarmonyObject mo = musicalObject[melodyIndex];
		int newPitchClass = Scale.pickPreviousPitchFromScale(mo.getPitchClass(), scale);
		int newChordNote = newPitchClass + (12* mo.getOctave());
		if (newChordNote > LOWER_LIMIT_PITCH) {
			if (melodyIndex == musicalObject.length - 1) {//last
				if (newChordNote > mo.getPitch()) {
					mo.setPitch(newChordNote - 12);
					mo.setPitchClass(newPitchClass);
					mo.setOctave(mo.getOctave() - 1);//+ octaaf
				} else {
					mo.setPitch(newChordNote);
					mo.setPitchClass(newPitchClass);
				}
			} else if(newChordNote > musicalObject[melodyIndex + 1].getPitch()) {// avoid crossing
				if (newChordNote > mo.getPitch()) {
					mo.setPitch(newChordNote - 12);
					mo.setPitchClass(newPitchClass);
					mo.setOctave(mo.getOctave() - 1);//+ octaaf
				} else {
					mo.setPitch(newChordNote);
					mo.setPitchClass(newPitchClass);
				}
			}
		}
	}
	
	public void mutateChordNextPitch(HarmonyObject[] musicalObject, int melodyIndex, int[] scale) {
		HarmonyObject mo = musicalObject[melodyIndex];
		int newPitchClass = Scale.pickNextPitchFromScale(mo.getPitchClass(), scale);
		int newChordNote = newPitchClass + (12* mo.getOctave());
		if (newChordNote < UPPER_LIMIT_PITCH) {
			if (melodyIndex == 0) {//first
				if (newChordNote < mo.getPitch()) {
					mo.setPitch(newChordNote + 12);
					mo.setPitchClass(newPitchClass);
					mo.setOctave(mo.getOctave() + 1);//+ octaaf
				} else {
					mo.setPitch(newChordNote);
					mo.setPitchClass(newPitchClass);
				}
			} else if(newChordNote < musicalObject[melodyIndex - 1].getPitch()) {// avoid crossing
				if (newChordNote < mo.getPitch()) {
					mo.setPitch(newChordNote + 12);
					mo.setPitchClass(newPitchClass);
					mo.setOctave(mo.getOctave() + 1);//+ octaaf
				} else {
					mo.setPitch(newChordNote);
					mo.setPitchClass(newPitchClass);
				}
			}
		}
		
	}
}
