package be.music.twelvetone.operator;

import java.util.List;

import jmetal.base.Solution;
import jmetal.base.operator.mutation.Mutation;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import jmetal.util.PseudoRandom;
import be.data.Partition;
import be.data.NotePos;
import be.instrument.Instrument;
import be.music.twelvetone.MusicVariableAtonal;

public class SwitchVoiceMutationAtonal extends Mutation {

	private List<Instrument> ranges;
	
	public SwitchVoiceMutationAtonal(List<Instrument> ranges) {
		super();
		this.ranges = ranges;
	}

	/**
	 * Perform the mutation operation
	 * @param probability Mutation probability
	 * @param solution The solution to mutate
	 * @throws JMException
	 */
	public void doMutation(double probability, Solution solution) throws JMException {
		if (PseudoRandom.randDouble() < probability) {
			List<Partition> motives = ((MusicVariableAtonal)solution.getDecisionVariables()[0]).getPartitions();
//			int s = motives.size() - 1;
//			int melodyIndex = PseudoRandom.randInt(0, s);	
			Partition motive = motives.get(0);
			Partition otherMotive = motives.get(1);
			int newMelodyIndex = PseudoRandom.randInt(0, 1);
			shiftNote(motive, otherMotive, newMelodyIndex);
			
			newMelodyIndex = PseudoRandom.randInt(2, 3);
			shiftNote(otherMotive, motive, newMelodyIndex);
		} 
	}

	private void shiftNote(Partition motive, Partition otherMotive,
			int newMelodyIndex) {
		List<NotePos> notePositions = motive.getNotes();
		if (!notePositions.isEmpty()) {	
			int position = PseudoRandom.randInt(0, notePositions.size() - 1);
			NotePos note = notePositions.get(position);
			if (note.getVoice() != newMelodyIndex && !containsNoteAtPosition(otherMotive, position)) {
				note.setVoice(newMelodyIndex);
				int lowPitch = ranges.get(newMelodyIndex).getLowest();
				int pc = lowPitch % 12;
				if (pc < note.getPitchClass()) {
					note.setPitch(lowPitch + (note.getPitchClass() - pc));
				} else {
					note.setPitch(lowPitch + (note.getPitchClass() - pc) + 12);
				}
				System.out.println("switch");
			}
		}
	}


	private boolean containsNoteAtPosition(Partition moveToMotive, int position) {
		List<NotePos> notes = moveToMotive.getNotes();
		for (NotePos notePos : notes) {
			if (notePos.getPosition() == position) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Executes the operation
	 * @param object An object containing a solution to mutate
	 * @return An object containing the mutated solution
	 * @throws JMException 
	 */
	public Object execute(Object object) throws JMException {
		Solution solution = (Solution) object;

		Double probability = (Double) getParameter("probabilitySwitchVoice");
		if (probability == null) {
			Configuration.logger_.severe("probabilitySwitchVoice: probability not " +
			"specified");
			Class cls = java.lang.String.class;
			String name = cls.getName();
			throw new JMException("Exception in " + name + ".execute()");
		}

		doMutation(probability.doubleValue(), solution);
		return solution;
	} 


}
