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

public class ShiftNoteMutation extends Mutation {
	
	private static Random random = new Random(System.currentTimeMillis());
	private static int OFFSET = 6;
	@Override
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

	private void doMutation(double probability, Solution solution) {
		if (PseudoRandom.randDouble() < probability) {
			List<MusicalStructure> melodies = ((MusicVariable)solution.getDecisionVariables()[0]).getMelodies();
			int melodyIndex = PseudoRandom.randInt(0, melodies.size() - 1);
			MusicalStructure musicalStructure = melodies.get(melodyIndex);
			List<NotePos> notePositions = musicalStructure.getNotePositions();
			int positionIndex = PseudoRandom.randInt(0, notePositions.size() - 1);
			List<NotePos> positions = musicalStructure.getNotePositions();
			NotePos note = positions.get(positionIndex);
			
			int oldPosition = note.getPosition();
			
			if (random.nextBoolean()) {
				if ((oldPosition - OFFSET) > 0) {
					note.setPosition(oldPosition - OFFSET);
					boolean crossing = false;
					//checkCrossing
					if(melodyIndex > 0 ){
						MusicalStructure lowerStructure = melodies.get(melodyIndex - 1);
						crossing = OperatorUtilities.checkLowerCrossing(note, note.getPitch(), lowerStructure);
						
					}
					if (melodyIndex < melodies.size() - 1) {
						MusicalStructure higherStructure = melodies.get(melodyIndex + 1);
						crossing = OperatorUtilities.checkHigherCrossing(note, note.getPitch(), higherStructure);
					} 
					note.setPosition(oldPosition);
					if (!crossing) {
						if (positionIndex > 0) {
							NotePos prevNote = positions.get(positionIndex - 1);
							if (prevNote.getLength() >= OFFSET) {
								prevNote.setLength(prevNote.getLength() - OFFSET);
								note.setPosition(oldPosition - OFFSET);
								note.setLength(note.getLength() + OFFSET);
								System.out.println("note shifted left:" + OFFSET);
							}	
						}
//						NotePos nextNote = positions.get(positionIndex + 1);	
					}
				}
			}else{
				if ((oldPosition + OFFSET) < musicalStructure.getLength()) {
					note.setPosition(oldPosition + OFFSET);
					boolean crossing = false;
					//checkCrossing
					if(melodyIndex > 0 ){
						MusicalStructure lowerStructure = melodies.get(melodyIndex - 1);
						crossing = OperatorUtilities.checkLowerCrossing(note, note.getPitch(), lowerStructure);
						
					}
					if (melodyIndex < melodies.size() - 1) {
						MusicalStructure higherStructure = melodies.get(melodyIndex + 1);
						crossing = OperatorUtilities.checkHigherCrossing(note, note.getPitch(), higherStructure);
					} 
					note.setPosition(oldPosition);
					if (!crossing) {
						if (positionIndex > 0) {
							NotePos prevNote = positions.get(positionIndex - 1);
							if (prevNote.getLength() >= OFFSET) {
								prevNote.setLength(prevNote.getLength() + OFFSET);
								note.setPosition(oldPosition + OFFSET);
								note.setLength(note.getLength() - OFFSET);
								System.out.println("note shifted right:" + OFFSET);
							}	
						}
//						NotePos nextNote = positions.get(positionIndex + 1);	
					}
				}
			}
			
			
	
		}
		
	}


}
