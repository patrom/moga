package be.core;

import be.set.PitchSet;

public class SetTheoryTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] notes = {0,1,11};
		PitchSet pitchSet = new PitchSet(notes);
		System.out.println(pitchSet.toKeys());
		System.out.println(pitchSet.invert());
		System.out.println(pitchSet.getPrimeForm());
		System.out.println(pitchSet.getNormalForm());
	}

}
