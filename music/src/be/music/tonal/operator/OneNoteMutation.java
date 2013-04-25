package be.music.tonal.operator;

import java.util.List;
import java.util.Random;

import jmetal.base.Solution;
import jmetal.base.operator.mutation.Mutation;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import jmetal.util.PseudoRandom;
import be.data.InstrumentRange;
import be.data.MusicalStructure;
import be.data.NotePos;
import be.data.Scale;
import be.music.MusicVariable;

public class OneNoteMutation extends Mutation {
	
	private int[] scale;
	private static Random random = new Random(System.currentTimeMillis());
	private List<InstrumentRange> ranges;
	
	public OneNoteMutation() {	
	} 

	public OneNoteMutation(int[] scale, List<InstrumentRange> ranges) {
		this.scale = scale;
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
			List<MusicalStructure> melodies = ((MusicVariable)solution.getDecisionVariables()[0]).getMelodies();
			int melodyIndex = PseudoRandom.randInt(0, melodies.size() - 1);
			MusicalStructure musicalStructure = melodies.get(melodyIndex);
			List<NotePos> notePositions = musicalStructure.getNotePositions();
			int positionIndex = PseudoRandom.randInt(0, notePositions.size() - 1);
			List<NotePos> positions = musicalStructure.getNotePositions();
			NotePos note = positions.get(positionIndex);

			if (random.nextBoolean()) {	
				int newPitchClass = Scale.pickPreviousPitchFromScale(note.getPitchClass(), scale);
				int newChordNote = 0;
				if (newPitchClass == 11) {
					newChordNote = newPitchClass + (12 * (note.getOctave() - 1));	
				} else {
					newChordNote = newPitchClass + (12 * note.getOctave());
				}
				
				if(OperatorUtilities.checkVoiceRange(newChordNote, note.getVoice(), ranges)){
					//checkCrossing
					if(melodyIndex > 0 ){
						MusicalStructure lowerStructure = melodies.get(melodyIndex - 1);
						boolean crossing = OperatorUtilities.checkLowerCrossing(note, newChordNote, lowerStructure);
						if (!crossing) {
							note.setPitch(newChordNote);
							System.out.println("note lower");
						}
					}
				}

			}else{
				int newPitchClass = Scale.pickNextPitchFromScale(note.getPitchClass(), scale);
				int newChordNote = 0;
				if (newPitchClass == 0) {
					newChordNote = newPitchClass + (12 * (note.getOctave() + 1));	
				} else {
					newChordNote = newPitchClass + (12 * note.getOctave());
				}
				if(OperatorUtilities.checkVoiceRange(newChordNote, note.getVoice(), ranges)){
					//checkCrossing
					if (melodyIndex < melodies.size() - 1) {
						MusicalStructure higherStructure = melodies.get(melodyIndex + 1);
						boolean crossing = OperatorUtilities.checkHigherCrossing(note, newChordNote, higherStructure);
						if (!crossing) {
							note.setPitch(newChordNote);
							System.out.println("note higher");
						}
					} 
					
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

		Double probability = (Double) getParameter("probabilityOneNote");
		if (probability == null) {
			Configuration.logger_.severe("probabilityOneNote: probability not " +
			"specified");
			Class cls = java.lang.String.class;
			String name = cls.getName();
			throw new JMException("Exception in " + name + ".execute()");
		}

		doMutation(probability.doubleValue(), solution);
		return solution;
	} 

}
