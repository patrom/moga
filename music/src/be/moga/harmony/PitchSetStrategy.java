package be.moga.harmony;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.sourceforge.jFuzzyLogic.membership.MembershipFunctionGaussian;
import net.sourceforge.jFuzzyLogic.membership.Value;

import org.apache.commons.lang.ArrayUtils;

import be.data.HarmonyObject;
import be.data.Interval;
import be.data.IntervalData;
import be.data.MusicalObject;
import be.set.IntervalVector;
import be.set.PitchSet;
import be.util.Utilities;



public class PitchSetStrategy extends HarmonyStrategy {

	double percent = 100;
//	MembershipFunctionGaussian m = new MembershipFunctionGaussian(new Value(1.0), new Value(1.0));
	
	public double evaluateChord(MusicalObject[] musicalObjects, int allowChordsOfPitchesOrHigher) {
		double registerValue = 0;
		
		Set<Integer> pitchClassSet = new TreeSet<Integer>();
		for (int i = 0; i < musicalObjects.length; i++) {
			if (musicalObjects[i] != null && musicalObjects[i].getPitch() != REST) {
				pitchClassSet.add(musicalObjects[i].getPitchClass());
				registerValue = registerValue + (1 - musicalObjects[i].getPitch()/percent);
			}
		}
		Integer[] chord = pitchClassSet.toArray(new Integer[pitchClassSet.size()]);
		if (chord.length == 0) {
			return Double.NaN;
		}
		if (chord.length < allowChordsOfPitchesOrHigher) {
			return Double.MIN_VALUE;
		} else {
			PitchSet pitchSet = new PitchSet(ArrayUtils.toPrimitive(chord));
		    IntervalVector intervalVector = new IntervalVector(pitchSet);
			double sum = 0;
			int intervalCount = 0;
			int[] intervalCounts = intervalVector.intervalVector;
			for (int i = 0; i < intervalCounts.length; i++) {
				Interval interval = Utilities.getEnumInterval(i + 1);
				for (int j = 0; j < intervalCounts[i]; j++) {
					sum = sum + (interval.getHarmonicValue());
					intervalCount++;
				}
			}
//			if (chord.length == 2) {
//				return m.membership(sum/intervalCount);
////				return (sum/intervalCount); 	
//			} else {
				return (sum/intervalCount); 	
//			}
		
		}	
	}

	@Override
	public List<IntervalData> evaluateIntervals(HarmonyObject[] harmonyObjects,
			int allowChordsOfPitchesOrHigher) {
		// TODO Auto-generated method stub
		return Collections.EMPTY_LIST;
	}

}
