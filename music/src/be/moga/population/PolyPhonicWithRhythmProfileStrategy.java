package be.moga.population;

import java.util.List;

import be.data.MusicalStructure;
import be.instrument.Instrument;
import be.util.Generator;

public class PolyPhonicWithRhythmProfileStrategy implements PopulationStrategy{

	public List<MusicalStructure> generateMelodies(int melodyLength,
			int voices, int[] scale, int[] profile) {
		return Generator.getInstance().polyphonicMelodiesWithRhythmProfile(melodyLength, voices, scale, 6, profile);
	}

	public List<MusicalStructure> generateMelodies(int melodyLength,
			List<Instrument> ranges, int[] scale, int[] profile, int length) {
		// TODO Auto-generated method stub
		return null;
	}

}
