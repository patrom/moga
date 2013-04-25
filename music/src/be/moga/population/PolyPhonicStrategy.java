package be.moga.population;

import java.util.List;

import jm.music.data.Note;

import be.data.InstrumentRange;
import be.data.Motive;
import be.data.MusicalStructure;
import be.util.Generator;
import be.util.Populator;

public class PolyPhonicStrategy implements PopulationStrategy {

//	public List<MusicalStructure> generateMelodies(int melodyLength,
//			int voices, int[] scale, int[] profile) {
////		return Generator.getInstance().polyphonicMelodies(melodyLength, voices, scale,6);
//		List<Note[]> melodies = Generator.getInstance().generateChordsWithoutRhythm(melodyLength, voices, scale);
//		return Generator.getInstance().extractNotePositions(melodies);
//	}
	
	
	
	public List<MusicalStructure> generateMelodies(int melodyLength, List<InstrumentRange> ranges, int[] scale, int[] profile, int length) {
		Populator populator = Populator.getInstance();
		List<Motive> melodies = populator.generateChordsWithoutRhythm(melodyLength,scale, ranges, length);
		return populator.extractSentence(melodies);
	}

	public List<MusicalStructure> generateMelodies(int melodyLength,
			int voices, int[] scale, int[] profile) {
		// TODO Auto-generated method stub
		return null;
	}

}
