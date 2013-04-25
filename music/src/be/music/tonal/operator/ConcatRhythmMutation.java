package be.music.tonal.operator;

import java.util.List;

import jmetal.base.Solution;
import jmetal.base.operator.mutation.Mutation;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import jmetal.util.PseudoRandom;
import be.data.MusicalStructure;
import be.data.NotePos;
import be.music.MusicVariable;
import be.util.MusicalStructureUtilities;

public class ConcatRhythmMutation extends Mutation {

	private static final double ATOMIC_VALUE = 12;
	private MusicalStructureUtilities m = MusicalStructureUtilities.getInstance();

	@Override
	public Object execute(Object object) throws JMException {
		Solution solution = (Solution) object;

		Double probability = (Double) getParameter("probabilityRhythmConcat");
		if (probability == null) {
			Configuration.logger_.severe("probabilityRhythmConcat: probability not " +
			"specified");
			Class cls = java.lang.String.class;
			String name = cls.getName();
			throw new JMException("Exception in " + name + ".execute()");
		}

		doMutation(probability.doubleValue(), solution);
		return solution;
	}
	
	public void doMutation(double probability, Solution solution) throws JMException {
		if (PseudoRandom.randDouble() < probability) {
			List<MusicalStructure> melodies = ((MusicVariable)solution.getDecisionVariables()[0]).getMelodies();
			int melodyIndex = PseudoRandom.randInt(0, melodies.size() - 1);
			MusicalStructure musicalStructure = melodies.get(melodyIndex);
			List<NotePos> notePositions = musicalStructure.getNotePositions();
			int length = notePositions.size();
			int positionIndex = PseudoRandom.randInt(0, length - 1);
			NotePos notePosition = notePositions.get(positionIndex);
			if (positionIndex != length - 1 && notePosition.samePitch(notePositions.get(positionIndex + 1))) {
				NotePos nextNote = notePositions.get(positionIndex + 1);	
				notePosition.setLength(notePosition.getLength() + nextNote.getLength());
				notePositions.remove(nextNote);
				System.out.println(notePosition.getPitch() + ", " + nextNote.getPitch());
				musicalStructure.setNotePositions(notePositions, musicalStructure.getLength());
//				System.out.println("concat");
			}
		} 
	}

}
