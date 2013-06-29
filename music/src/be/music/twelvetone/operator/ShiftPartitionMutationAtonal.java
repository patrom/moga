package be.music.twelvetone.operator;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import jmetal.base.Solution;
import jmetal.base.operator.mutation.Mutation;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import jmetal.util.PseudoRandom;
import be.data.NotePos;
import be.data.Partition;
import be.music.twelvetone.MusicVariableAtonal2;

public class ShiftPartitionMutationAtonal extends Mutation {

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
			List<Partition> partitions = ((MusicVariableAtonal2)solution.getDecisionVariables()[0]).getPartitions();
			
			
			if (!partitions.isEmpty()) {
				int index = PseudoRandom.randInt(0, partitions.size() - 1);
				Partition partition = partitions.get(index);
				int oldPosition = partition.getPosition();
				int rand = PseudoRandom.randInt(0, 8);
				int newPosition = rand * 6;
				partition.setPosition(newPosition);
				int diff = newPosition - oldPosition;
				if (diff != 0) {
					List<NotePos> notes = partition.getNotes();
					for (NotePos notePos : notes) {
						notePos.setPosition(diff + notePos.getPosition());
					}
					System.out.println("Partition shift:" + partition.getPosition());
				}
				
//				int index = partitions.indexOf(partition);
//					if (random.nextBoolean()) {
//						if (index >= 1) {
//							Partition prevPartition = partitions.get(index - 1);
//							int partitionPosition = partition.getPosition();
////							if (!containsNoteAtPosition(partitions, notePosition - MIN_LENGTH)
////									&& startPartition <= notePosition - MIN_LENGTH) {
////								partition.setPosition(notePosition - MIN_LENGTH);
////								System.out.println("Shifted");
////							}	
//						}
//						
//					} else {
//						if (index < partitions.size() - 1) {
//							Partition nextPartition = partitions.get(index + 1);
//							int partitionPosition = partition.getPosition();
////							if (!containsNoteAtPosition(partitions, notePosition + MIN_LENGTH)
////									&& notePosition + MIN_LENGTH <= endPartition) {
////								partition.setPosition(notePosition + MIN_LENGTH);
////								System.out.println("Shifted");
////							}	
//						}
//				}
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

		Double probability = (Double) getParameter("probabilityShiftPartition");
		if (probability == null) {
			Configuration.logger_.severe("probabilityShiftPartition: probability not " +
			"specified");
			Class cls = java.lang.String.class;
			String name = cls.getName();
			throw new JMException("Exception in " + name + ".execute()");
		}

		doMutation(probability.doubleValue(), solution);
		return solution;
	} 

}
