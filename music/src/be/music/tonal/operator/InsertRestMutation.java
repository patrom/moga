package be.music.tonal.operator;

import java.util.List;
import java.util.Random;

import be.data.MusicalStructure;
import be.data.NotePos;
import be.music.MusicVariable;
import jmetal.base.Solution;
import jmetal.base.operator.mutation.Mutation;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import jmetal.util.PseudoRandom;

public class InsertRestMutation extends Mutation {
	
	private static final int MINIMUM_LENGTH = 6;
	private static Random random = new Random(System.currentTimeMillis());

	@Override
	public Object execute(Object object) throws JMException {
		Solution solution = (Solution) object;

		Double probability = (Double) getParameter("probabilityInsertRest");
		if (probability == null) {
			Configuration.logger_.severe("probabilityInsertRest: probability not " +
			"specified");
			Class cls = java.lang.String.class;
			String name = cls.getName();
			throw new JMException("Exception in " + name + ".execute()");
		}

		doMutation(probability.doubleValue(), solution);
		return solution;
	}

	private void doMutation(double probability, Solution solution) {
		if (PseudoRandom.randDouble() < probability) {
			List<MusicalStructure> melodies = ((MusicVariable)solution.getDecisionVariables()[0]).getMelodies();
			int melodyIndex = PseudoRandom.randInt(0, melodies.size() - 1);
			MusicalStructure musicalStructure = melodies.get(melodyIndex);
			List<NotePos> notePositions = musicalStructure.getNotePositions();
			int positionIndex = PseudoRandom.randInt(0, notePositions.size() - 1);
			List<NotePos> positions = musicalStructure.getNotePositions();
			NotePos note = positions.get(positionIndex);
			int length = note.getLength();	
			if (length > MINIMUM_LENGTH) {
				int newLength = length/2;
				if (random.nextBoolean()) {
					note.setPosition(note.getPosition() + newLength);
					note.setLength(newLength);
				}else{
					note.setLength(newLength);
				}
				System.out.println("Length:" + newLength);
			}	
		}
	}

}
