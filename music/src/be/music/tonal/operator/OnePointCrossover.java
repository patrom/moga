package be.music.tonal.operator;

import java.util.ArrayList;
import java.util.List;

import jmetal.base.Solution;
import jmetal.base.operator.crossover.Crossover;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import jmetal.util.PseudoRandom;
import be.data.MelodicSentence;
import be.data.MusicalStructure;
import be.data.NotePos;
import be.music.MusicVariable;
import be.music.MusicSolution;
import be.util.MusicalStructureUtilities;

public class OnePointCrossover extends Crossover {

	//TODO configuration data!!
	  private final double ATOMIC_VALUE = 12;
	  private int length;

	public OnePointCrossover(int melodyLenth) { 
		this.length = melodyLenth;
	} 

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
//	          
//	            MusicalStructureUtilities util = MusicalStructureUtilities.getInstance();
////	            int parent1Length = ((MusicVariable)parent1.getDecisionVariables()[0]).getMusicalObjects().size();
////	            int parent2Length = ((MusicVariable)parent2.getDecisionVariables()[0]).getMusicalObjects().size();
////	            System.out.println("parent1Length: " + parent1Length);
////	            System.out.println("parent2Length: " + parent2Length);
//		        int crossoverPoint = (int) (PseudoRandom.randInt(2, length - 2) * ATOMIC_VALUE);
////		        int crossoverPoint = 48;
////		        System.out.println("crossoverPoint: " + crossoverPoint);
//	            //split at crossoverpoint
//	    		List<MusicalStructure> splitMelodiesParent1 = new ArrayList<MusicalStructure>();
//	    		for (MusicalStructure melody : melodiesParent1) {
//	    			MelodicSentence splitMelody = util.splitStructures(melody, crossoverPoint);
//	    			splitMelodiesParent1.add(splitMelody);
//	    		}
//	    		
//	    		List<MusicalStructure> splitMelodiesParent2 = new ArrayList<MusicalStructure>();
//	    		for (MusicalStructure melody : melodiesParent2) {
//	    			MelodicSentence splitMelody = util.splitStructures(melody, crossoverPoint);
//	    			splitMelodiesParent2.add(splitMelody);
//	    		}
//	    		
//	    		
//	    		//switch the split structures
//	    		int size = splitMelodiesParent2.size();
//	    		for (int i = 0; i < size; i++) {
//	    			List<MusicalStructure> motives = splitMelodiesParent2.get(i).getMotives();
//	    			List<MusicalStructure> motives2 = splitMelodiesParent1.get(i).getMotives();
//	    			MusicalStructure motive = util.findStructureAtPosition(motives, crossoverPoint);
//	    			MusicalStructure motive2 = util.findStructureAtPosition(motives2, crossoverPoint);
//	    			util.replaceStructure(splitMelodiesParent1.get(i), motive);
//	    			util.replaceStructure(splitMelodiesParent2.get(i), motive2);
//	    		}
//	    		
//	    		//check
////	    		for (MusicalStructure musicalStructure : splitMelodiesParent1) {
////	    			NotePos[] pos = musicalStructure.getNotePositions();
////	    			int total = 0;
////	    			for (int i = 0; i < pos.length; i++) {
////						 total = total + pos[i].getLength();
////					}
////	    			if(total != musicalStructure.getLength()){
////	    				System.out.println("total split1: " + total);
////	    			}
////				}
////	    		
////	    		for (MusicalStructure musicalStructure : splitMelodiesParent2) {
////	    			NotePos[] pos = musicalStructure.getNotePositions();
////	    			int total = 0;
////	    			for (int i = 0; i < pos.length; i++) {
////						 total = total + pos[i].getLength();
////					}
////	    			if(total != musicalStructure.getLength()){
////	    				System.out.println("total split2: " + total);
////	    			}
////				}
////	    		
//	    		//concat the switched structures
//	    		List<MusicalStructure> concatMelodies = new ArrayList<MusicalStructure>();
//	    		for (MusicalStructure melody : splitMelodiesParent1) {
//	    			MelodicSentence m = util.concatStructures(melody);
//	    			concatMelodies.add(m);
//	    		}
//	    		
//	    		List<MusicalStructure> concatMelodies2 = new ArrayList<MusicalStructure>();
//	    		for (MusicalStructure melody : splitMelodiesParent2) {
//	    			MelodicSentence m = util.concatStructures(melody);
//	    			concatMelodies2.add(m);
//	    		}
//	    		
//	    		
////	    		for (MusicalStructure musicalStructure : concatMelodies) {
////	    			NotePos[] pos = musicalStructure.getNotePositions();
////	    			int total = 0;
////	    			for (int i = 0; i < pos.length; i++) {
////						 total = total + pos[i].getLength();
////					}
////	    			if(total != musicalStructure.getLength()){
////	    				System.out.println("total: " + total);
////	    			}
////				}
//    		  ((MusicVariable)offSpring[0].getDecisionVariables()[0]).setMelodies(concatMelodies);
//    	      ((MusicVariable)offSpring[1].getDecisionVariables()[0]).setMelodies(concatMelodies2);
	       } 
	    return offSpring;
	  } // doCrossover

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
	  
	  
	 
}
