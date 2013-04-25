package be.moga.harmony;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.ArrayUtils;

import be.analyzer.HarmonyAnalyzer;
import be.data.HarmonyObject;
import be.data.IntervalData;
import be.data.MusicalObject;


public class SonanceStrategy extends HarmonyStrategy {

	public double evaluateChord(MusicalObject[] musicalObjects, int allowChordsOfPitchesOrHigher) {
		List<Integer> pitchClassList = new ArrayList<Integer>();
		Set<Integer> pitchClassSet = new TreeSet<Integer>();
		for (int i = 0; i < musicalObjects.length; i++) {
			if (musicalObjects[i] != null && musicalObjects[i].getPitch() != REST) {
				pitchClassSet.add(musicalObjects[i].getPitchClass());
				pitchClassList.add(musicalObjects[i].getPitchClass());
			}
		}
		if (pitchClassSet.size() == 0){
			return Double.NaN;
		}
		if (pitchClassSet.size() < allowChordsOfPitchesOrHigher) {//check on set - no duplicated pitches
			return Double.MIN_VALUE;
		}else{
			Integer[] chord = pitchClassList.toArray(new Integer[pitchClassList.size()]);
			HarmonyAnalyzer analyzer = new HarmonyAnalyzer();
			analyzer.analyzeHarmony(ArrayUtils.toPrimitive(chord));
			return analyzer.getSonance();
		}
	}

	@Override
	public List<IntervalData> evaluateIntervals(HarmonyObject[] harmonyObjects,
			int allowChordsOfPitchesOrHigher) {
		// TODO Auto-generated method stub
		return Collections.EMPTY_LIST;
	}

}
