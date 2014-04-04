package be.moga.population;

import java.util.List;

import jm.music.data.Note;

import be.data.MusicalStructure;
import be.instrument.Instrument;
import be.util.Generator;

public class HomophonicStrategy implements PopulationStrategy {

	public List<MusicalStructure> generateMelodies(int melodyLength, int voices, int[] scale, int[] profile){
		Generator generator = Generator.getInstance();
		List<Note[]> structures = generator.homophonic(melodyLength, voices, scale, 6);
		return generator.extractNotePositions(structures);
	}

	public List<MusicalStructure> generateMelodies(int melodyLength,
			List<Instrument> ranges, int[] scale, int[] profile, int length) {
		// TODO Auto-generated method stub
		return null;
	}

}
