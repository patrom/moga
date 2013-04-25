package be.analyzer;

import java.util.Comparator;

public class SonanceComparator implements Comparator<HarmonyAnalyzer>{

	public int compare(HarmonyAnalyzer analyzer1, HarmonyAnalyzer analyzer2) {
		if (analyzer1.getSonance() == analyzer2.getSonance()) {
			return 0;
		}else if (analyzer1.getSonance() < analyzer2.getSonance()) {
			return -1;
		}else if (analyzer1.getSonance() > analyzer2.getSonance()) {
			return 1;
		}
		return 0;
	}

}
