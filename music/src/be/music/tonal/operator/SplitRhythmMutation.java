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

public class SplitRhythmMutation extends Mutation {

	private static final double ATOMIC_VALUE = 12;
	private static final int MIN_LENGTH = 6;

	@Override
	public Object execute(Object object) throws JMException {
		Solution solution = (Solution) object;

		Double probability = (Double) getParameter("probabilityRhythmSplit");
		if (probability == null) {
			Configuration.logger_.severe("probabilityRhythmSplit: probability not " +
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
			int positionIndex = PseudoRandom.randInt(0, notePositions.size() - 1);
			NotePos notePosition = notePositions.get(positionIndex);
			if (notePosition.getLength() > MIN_LENGTH && (notePosition.getLength() % 2) == 0) {//divisable by 2
				int length = notePosition.getLength() / 2;
				NotePos newNote = createNotePosition(notePosition, notePosition.getPosition() + length, length);
				notePositions.add(positionIndex + 1, newNote);
				notePosition.setLength(length);
				musicalStructure.setNotePositions(notePositions, musicalStructure.getLength());
				System.out.println("split");
			}
		} 
	}


	private NotePos createNotePosition(NotePos notePosition, int position, int length) {
		NotePos notePos = new NotePos();
		notePos.setLength(length);
		notePos.setPosition(position);
		notePos.setPitch(notePosition.getPitch());
		double value = (double)length/ATOMIC_VALUE;
		notePos.setRhythmValue(value);
		notePos.setDuration(value);
		notePos.setVoice(notePosition.getVoice());
		notePos.setDynamic(notePosition.getDynamic());
		return notePos;
	}

}
