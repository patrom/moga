package be.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.ArrayUtils;

public class ChordUtilities {
	
	private static final int ZERO = 0;
	private static final int REST = Integer.MIN_VALUE;

	public static int[] removeRestFromChord(int[] chord){
		int[] tempChord = chord;
		while (ArrayUtils.contains(tempChord, REST)) {
			int index = ArrayUtils.indexOf(tempChord, REST);
			tempChord  = ArrayUtils.remove(tempChord, index);
		}
		return tempChord;	
	}
	
	public static int[] removeZeroFromChord(int[] chord){
		int[] tempChord = chord;
		while (ArrayUtils.contains(tempChord, ZERO)) {
			int index = ArrayUtils.indexOf(tempChord, ZERO);
			tempChord  = ArrayUtils.remove(tempChord, index);
		}
		return tempChord;	
	}
	
	public static int[] removeDoublePitchClasses(int[] chord) {
		List<Integer> list = Arrays.asList(ArrayUtils.toObject(chord));
		Set<Integer> set = new TreeSet<Integer>(list);
		int[] temp = new int[set.size()];
		int j = 0;
		for (Integer pc : set) {
			temp[j] = pc;
			j++;
		}
		return temp;
	}
}
