package be.music.twelvetone.operator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import be.data.MotiveAtonal;
import be.data.MusicalStructure;
import be.data.NotePos;
import be.music.MusicVariable;
import be.music.twelvetone.MusicVariableAtonal;
import jmetal.base.Solution;
import jmetal.base.operator.mutation.Mutation;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import jmetal.util.PseudoRandom;

public class ShiftNoteMutationAtonal extends Mutation{

	private static final int MIN_LENGTH = 6;
	private static Random random = new Random(System.currentTimeMillis());
	/**
	 * Perform the mutation operation
	 * @param probability Mutation probability
	 * @param solution The solution to mutate
	 * @throws JMException
	 */
	public void doMutation(double probability, Solution solution) throws JMException {
		if (PseudoRandom.randDouble() < probability) {
			List<MotiveAtonal> motives = ((MusicVariableAtonal)solution.getDecisionVariables()[0]).getAtonalMotives();
			int s = motives.size() - 1;
			int melodyIndex = PseudoRandom.randInt(0, s);	
			MotiveAtonal motive = motives.get(melodyIndex);
			List<NotePos> notePositions = motive.getNotes();
			int startMotive = motive.getPosition();
			int endMotive = startMotive + motive.getLength();
			if (!notePositions.isEmpty()) {
				int position = PseudoRandom.randInt(0, notePositions.size() - 1);
				NotePos note = notePositions.get(position);
				int index = notePositions.indexOf(note);
					if (random.nextBoolean()) {
						if (index >= 1) {
							NotePos prevNote = notePositions.get(index - 1);
							if (prevNote.getVoice() != note.getVoice()) {
//								int prevPosition = prevNote.getPosition();
								int notePosition = note.getPosition();
								if (!containsNoteAtPosition(notePositions, notePosition - MIN_LENGTH)
										&& startMotive <= notePosition - MIN_LENGTH) {
									note.setPosition(notePosition - MIN_LENGTH);
									System.out.println("Shifted");
								}
							}
						}
						
					} else {
						if (index < notePositions.size() - 1) {
							NotePos nextNote = notePositions.get(index + 1);
							if (nextNote.getVoice() != note.getVoice()) {
//								int nextPosition = nextNote.getPosition();
								int notePosition = note.getPosition();
								if (!containsNoteAtPosition(notePositions, notePosition + MIN_LENGTH)
										&& notePosition + MIN_LENGTH <= endMotive) {
									note.setPosition(notePosition + MIN_LENGTH);
									System.out.println("Shifted");
								}
							}
						}
				}
			}
		} 
	}


	private boolean containsNoteAtPosition(List<NotePos> notePositions, int notePosition) {
		for (NotePos notePos : notePositions) {
			if (notePos.getPosition() == notePosition) {
				return true;
			}
		}
		return false;
	}


	private void shiftNote(NotePos note, List<NotePos> mergedNotePositions, int index) {
		NotePos prevOrNextNote = mergedNotePositions.get(index);
		if (prevOrNextNote.getVoice() != note.getVoice()) {
			int prevOrNextPosition = prevOrNextNote.getPosition();
			int notePosition = note.getPosition();
			int length = Math.abs(notePosition - prevOrNextPosition);
			if (length > MIN_LENGTH && (length % 2) == 0) {//divisable by 2
				note.setPosition(notePosition + (length / 2));
				System.out.println("shift");
			}else{
				note.setPosition(prevOrNextPosition);
			}
		}
	}



	/**
	 * Executes the operation
	 * @param object An object containing a solution to mutate
	 * @return An object containing the mutated solution
	 * @throws JMException 
	 */
	public Object execute(Object object) throws JMException {
		Solution solution = (Solution) object;

		Double probability = (Double) getParameter("probabilityShiftNote");
		if (probability == null) {
			Configuration.logger_.severe("probabilityShiftNote: probability not " +
			"specified");
			Class cls = java.lang.String.class;
			String name = cls.getName();
			throw new JMException("Exception in " + name + ".execute()");
		}

		doMutation(probability.doubleValue(), solution);
		return solution;
	} 
}
