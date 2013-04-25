package be.moga.population;

import java.util.List;

import be.data.InstrumentRange;
import be.data.MusicalStructure;

public interface PopulationStrategy {

	public List<MusicalStructure> generateMelodies(int melodyLength, int voices, int[] scale, int[] profile);
	
	public List<MusicalStructure> generateMelodies(int melodyLength, List<InstrumentRange> ranges, int[] scale, int[] profile, int length);
}
