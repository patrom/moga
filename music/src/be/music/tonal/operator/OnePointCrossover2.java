package be.music.tonal.operator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.ArrayUtils;

import be.data.MelodicSentence;
import be.data.Motive;
import be.data.MusicalStructure;
import be.data.NotePos;
import be.music.MusicVariable;
import be.music.MusicSolution;
import jmetal.base.Solution;
import jmetal.base.operator.crossover.Crossover;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import jmetal.util.PseudoRandom;

public class OnePointCrossover2 extends Crossover {

	 /**
	   * Executes the operation
	   * @param object An object containing an array of two solutions
	   * @return An object containing an array with the offSprings
	   * @throws JMException
	   */
	  public Object execute(Object object) throws JMException {
	    Solution[] parents = (Solution[]) object;

	    Double probability = (Double) getParameter("probabilityCrossover");
	    if (parents.length < 2) {
	      Configuration.logger_.severe("OnePointCrossover: operator " +
	              "needs two parents");
	      Class cls = java.lang.String.class;
	      String name = cls.getName();
	      throw new JMException("Exception in " + name + ".execute()");
	    } else if (probability == null) {
	      Configuration.logger_.severe("OnePointCrossover: probability " +
	              "not specified");
	      Class cls = java.lang.String.class;
	      String name = cls.getName();
	      throw new JMException("Exception in " + name + ".execute()");
	    }
	    Solution[] offSpring;
	    offSpring = doCrossover(probability.doubleValue(),
	            parents[0],
	            parents[1]);

	    //-> Update the offSpring solutions
	    for (int i = 0; i < offSpring.length; i++) {
	      offSpring[i].setCrowdingDistance(0.0);
	      offSpring[i].setRank(0);
	    }
	    return offSpring;//*/
	  } // execute
	  
	  
	  /**
	   * Perform the crossover operation.
	   * @param probability Crossover probability
	   * @param parent1 The first parent
	   * @param parent2 The second parent
	   * @return An array containing the two offsprings
	   * @throws JMException
	   */
	  public Solution[] doCrossover(double probability,Solution parent1,Solution parent2) throws JMException {
	    Solution[] offSpring = new Solution[2];
	    offSpring[0] = new MusicSolution(parent1);
	    offSpring[1] = new MusicSolution(parent2);
	  
	      if (PseudoRandom.randDouble() < probability) {
//	            List<MusicalStructure> melodiesParent1 = ((MusicVariable)parent1.getDecisionVariables()[0]).getMelodies();
//	            List<MusicalStructure> melodiesParent2 = ((MusicVariable)parent2.getDecisionVariables()[0]).getMelodies();
//	            //find crossover point
//	            List<Integer> onSetsParent1 = findCrossoverPoints(melodiesParent1);
//	            List<Integer> onSetsParent2 = findCrossoverPoints(melodiesParent2);
//	            Integer crossoverPoint = null;
//	            Integer[] onset1 = new Integer[onSetsParent1.size()];
//	            onset1 = onSetsParent1.toArray(onset1);
//	            int randomStart = PseudoRandom.randInt(0, onset1.length);
//	            for (int i = randomStart; i < onset1.length; i++) {
//	            	if (onSetsParent2.contains(onset1[i])) {
//						crossoverPoint = onset1[i];
//						break;
//					}
//				}
//	            if (crossoverPoint != null) {
//					System.out.println("found point:" + crossoverPoint);
//					for (MusicalStructure musicalStructure : melodiesParent1) {
//						NotePos[] positions = musicalStructure.getNotePositions();
//						List<NotePos> offspring1 = new ArrayList<NotePos>();
//						List<NotePos>  offspring2 = new ArrayList<NotePos>();
//						
//						for (int i = 0; i < positions.length; i++) {
//							if (positions[i].getPosition() < crossoverPoint) {
//								offspring1.add(positions[i]);
//							}else{
//								offspring2.add(positions[i]);
//							}
//						}
//					}
//					
//				}   
	      }
		return offSpring;
	  }


	private List<Integer> findCrossoverPoints(List<MusicalStructure> melodiesParent) {
		List<Integer> onSets = new ArrayList<Integer>();
//		boolean firstRun = true;
//		for (MusicalStructure musicalStructure : melodiesParent) {
//			if (firstRun) {
//				NotePos[] positions = musicalStructure.getNotePositions();
//		    	for (int i = 1; i < positions.length - 1; i++) {
//		    		onSets.add(positions[i].getPosition());
//				}
//			} else {
//				firstRun = false;
//				NotePos[] positions = musicalStructure.getNotePositions();
//				for (int i = 1; i < positions.length - 1; i++) {
//					if (!onSets.contains(i)) {
//						onSets.remove(i);
//					}
//				}
//			}
//		}
		return onSets;
	}
}
