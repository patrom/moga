package be.music.tonal.operator;

import java.util.List;
import java.util.Map;

import jmetal.base.Solution;
import jmetal.base.operator.mutation.Mutation;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import jmetal.util.PseudoRandom;
import be.data.MusicalStructure;
import be.data.NotePos;
import be.music.MusicVariable;
import be.util.Populator;

public class SwitchVoicesMutation extends Mutation {
	
	private static final int PITCH_BASE = 48;

	public SwitchVoicesMutation() {	
	} 

	/**
	 * Perform the mutation operation
	 * @param probability Mutation probability
	 * @param solution The solution to mutate
	 * @throws JMException
	 */
	public void doMutation(double probability, Solution solution) throws JMException {
		if (PseudoRandom.randDouble() < probability) {
			List<MusicalStructure> melodies = ((MusicVariable)solution.getDecisionVariables()[0]).getMelodies();
			Map<Integer, List<NotePos>> map = Populator.getInstance().extractHarmonyObjects3(melodies);
			int melodyIndex = PseudoRandom.randInt(0, melodies.size() - 1);
			MusicalStructure musicalStructure = melodies.get(melodyIndex);
			List<NotePos> notePositions = musicalStructure.getNotePositions();
			int position = PseudoRandom.randInt(0, notePositions.size() - 1);
			List<NotePos> harmony = map.get((notePositions.get(position)).getPosition());
			int first = PseudoRandom.randInt(0, harmony.size() - 1);
			int second = PseudoRandom.randInt(0, harmony.size() - 1);
			
			if (first != second) {	
				NotePos firstNote = harmony.get(first);
				NotePos secondNote = harmony.get(second);
				int oldFirstPitch = firstNote.getPitch();
				int oldSecondPitch = secondNote.getPitch();
				
				int pc = firstNote.getPitchClass();
				int octave = firstNote.getOctave();	
				int pitch = pc + (12 * secondNote.getOctave());
	
				int secPc = secondNote.getPitchClass();
				int secPitch = secPc + (12 * octave);
				firstNote.setPitch(secPitch);
				secondNote.setPitch(pitch);
				
				boolean crossing = false;
				int l = harmony.size() - 1;
				for (int i = 0; i < l; i++) {
					NotePos note1 = harmony.get(i);
					NotePos note2 = harmony.get(i+1);
					if (note1.getVoice() < note2.getVoice() && note1.getPitch() > note2.getPitch()) {
						crossing = true;
						break;
					}else if (note1.getVoice() > note2.getVoice() && note1.getPitch() < note2.getPitch()){
						crossing = true;
						break;
					}
				}
				if (crossing) {
					firstNote.setPitch(oldFirstPitch);
					secondNote.setPitch(oldSecondPitch);
				}else{
					System.out.println("Switch pitch");
				}
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

		Double probability = (Double) getParameter("probabilitySwitch");
		if (probability == null) {
			Configuration.logger_.severe("probabilitySwitch: probability not " +
			"specified");
			Class cls = java.lang.String.class;
			String name = cls.getName();
			throw new JMException("Exception in " + name + ".execute()");
		}

		doMutation(probability.doubleValue(), solution);
		return solution;
	} 

}
