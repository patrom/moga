package be.moga.population;

import java.util.List;

import be.data.MusicalStructure;
import be.instrument.Instrument;

public interface PopulationStrategy {

	public List<MusicalStructure> generateMelodies(int melodyLength, int voices, int[] scale, int[] profile);
	
	public List<MusicalStructure> generateMelodies(int melodyLength, List<Instrument> ranges, int[] scale, int[] profile, int length);
}
