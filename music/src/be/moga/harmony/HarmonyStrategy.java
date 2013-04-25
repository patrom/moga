package be.moga.harmony;

import java.util.List;

import be.data.HarmonyObject;
import be.data.IntervalData;
import be.data.MusicalObject;



public abstract class HarmonyStrategy {
	
	protected static final int REST = Integer.MIN_VALUE;

	public abstract double evaluateChord(MusicalObject[] musicalObjects, int allowChordsOfPitchesOrHigher);
	
	public abstract List<IntervalData> evaluateIntervals(HarmonyObject[] harmonyObjects,int allowChordsOfPitchesOrHigher);
	
	protected double amountOfIntervals(int n){
		return (n * (n - 1))/ 2;
	}
}
