/**
 * NSGAII_main.java
 *
 * @author Juan J. Durillo
 * @author Antonio J. Nebro
 * @version 1.0
 *   This implementation of NSGA-II makes use of a QualityIndicator object
 *   to obtained the convergence speed of the algorithm. This version is used
 *   in the paper:
 *     A.J. Nebro, J.J. Durillo, C.A. Coello Coello, F. Luna, E. Alba 
 *     "A Study of Convergence Speed in Multi-Objective Metaheuristics." 
 *     To be presented in: PPSN'08. Dortmund. September 2008.
 *     
 *   Besides the classic NSGA-II, a steady-state version (ssNSGAII) is also
 *   included (See: J.J. Durillo, A.J. Nebro, F. Luna and E. Alba 
 *                  "On the Effect of the Steady-State Selection Scheme in 
 *                  Multi-Objective Genetic Algorithms"
 *                  5th International Conference, EMO 2009, pp: 183-197. 
 *                  April 2009)
 *   
 */
package be.music.tonal;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import jm.JMC;
import jm.music.data.Score;
import jm.util.View;
import jm.util.Write;
import jmetal.base.Algorithm;
import jmetal.base.Operator;
import jmetal.base.Problem;
import jmetal.base.Solution;
import jmetal.base.SolutionSet;
import jmetal.base.operator.mutation.Mutation;
import jmetal.base.operator.selection.SelectionFactory;
import jmetal.qualityIndicator.QualityIndicator;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import jmetal.util.PseudoRandom;
import be.data.MusicalStructure;
import be.moga.MusicProperties;
import be.music.MusicSolution;
import be.music.MusicVariable;
import be.music.tonal.operator.ConcatRhythmMutation;
import be.music.tonal.operator.InsertRestMutation;
import be.music.tonal.operator.OneNoteMutation;
import be.music.tonal.operator.OnePointCrossover;
import be.music.tonal.operator.ShiftNoteMutation;
import be.music.tonal.operator.SplitRhythmMutation;
import be.util.FugaUtilities;
import be.util.ScoreUtilities;

public class NSGAII_TonalMain implements JMC{
	  public static Logger      logger_ ;      // Logger object
	  public static FileHandler fileHandler_ ; // FileHandler object
	  private static MusicProperties inputProps = new MusicProperties();

  /**
   * @param args Command line arguments.
   * @throws JMException 
   * @throws IOException 
   * @throws SecurityException 
   * Usage: three options
   *      - jmetal.metaheuristics.nsgaII.NSGAII_main
   *      - jmetal.metaheuristics.nsgaII.NSGAII_main problemName
   *      - jmetal.metaheuristics.nsgaII.NSGAII_main problemName paretoFrontFile
   */
  public static void main(String [] args) throws 
                                  JMException, 
                                  SecurityException, 
                                  IOException, 
                                  ClassNotFoundException {
    Problem   problem   ;         // The problem to solve
    Algorithm algorithm ;         // The algorithm to use
    Operator  crossover ;         // Crossover operator
    Operator  mutation  ;         // Mutation operator
    Operator  selection ;         // Selection operator
    
    QualityIndicator indicators = null; // Object to get quality indicators

    // Logger object and file to store log messages
    logger_      = Configuration.logger_ ;
    fileHandler_ = new FileHandler("NSGAII_main.log"); 
    logger_.addHandler(fileHandler_) ;
    
    
//    problem = new Kursawe("Real", 3);
    problem = new MusicTonalProblem("music", 1, inputProps);
//    indicators = new QualityIndicator(problem, "C://midi//paretoFrontFile.txt");
   
    algorithm = new NSGAII_Tonal(problem);
//    algorithm.setInputParameter("indicators", indicators);
    //algorithm = new ssNSGAII(problem);

    // Algorithm parameters
    int populationSize = 10;
    algorithm.setInputParameter("populationSize",populationSize);
    algorithm.setInputParameter("maxEvaluations",populationSize * 2000);

    // Mutation and Crossover for Real codification10
    crossover = new OnePointCrossover(inputProps.getMelodyLength());
//    crossover = new OnePointCrossover2();
    //if homophonic don't do crossover!
    if (inputProps.getPopulationStrategy().equals("homophonic")) {
    	 crossover.setParameter("probabilityCrossover",0.0);       
	} else {
		 crossover.setParameter("probabilityCrossover",0.0);       
	}      
//    crossover.setParameter("distributionIndex",20.0);

//    mutation = MutationFactory.getMutationOperator("BitFlipMutation");
    mutation = new OneNoteMutation(inputProps.getScale(), inputProps.getRanges());
    mutation.setParameter("probabilityOneNote",1.0);
//    mutation.setParameter("distributionIndex",20.0);    
    Mutation mutation2 = new ShiftNoteMutation();
    mutation2.setParameter("probabilityShiftNote",0.2);
    Mutation mutation5 = new InsertRestMutation();
    mutation5.setParameter("probabilityInsertRest",0.009);
    
    Mutation mutation3 = new SplitRhythmMutation();
    mutation3.setParameter("probabilityRhythmSplit",0.1);
    Mutation mutation4 = new ConcatRhythmMutation();
    mutation4.setParameter("probabilityRhythmConcat",0.2);
//    Mutation mutation5 = new ParallelHarmonyMutation(inputProps.getScale(), inputProps.getRanges());
//    mutation5.setParameter("probabilityParallel",0.0);
    // Selection Operator 
    selection = SelectionFactory.getSelectionOperator("BinaryTournament2") ;                           

    // Add the operators to the algorithm
    algorithm.addOperator("crossover",crossover);
    algorithm.addOperator("mutation",mutation);
    algorithm.addOperator("mutation2",mutation2);
    algorithm.addOperator("mutation3",mutation3);
    algorithm.addOperator("mutation4",mutation4);
    algorithm.addOperator("mutation5",mutation5);
    algorithm.addOperator("selection",selection);

    // Add the indicator object to the algorithm
//    indicators = new QualityIndicator(problem, "C:/space/mulitobjectivemusic/FUN") ;//C:\space\mulitobjectivemusic\FUN
//    algorithm.setInputParameter("indicators", indicators);
    
    // Execute the Algorithm
    long initTime = System.currentTimeMillis();
    SolutionSet population = algorithm.execute();
    long estimatedTime = System.currentTimeMillis() - initTime;
    
    // Result messages 
    logger_.info("Total execution time: "+estimatedTime + "ms");
    logger_.info("Variables values have been writen to file VAR");
//    population.printVariablesToFile("VAR");   
//    population.printVariablesToNotes("VAR"); 
    logger_.info("Objectives values have been writen to file FUN");
    population.printObjectivesToFile("FUN");
    printVariablesToMidi(population);
  
    if (indicators != null) {
      logger_.info("Quality indicators") ;
      logger_.info("Hypervolume: " + indicators.getHypervolume(population)) ;
      logger_.info("GD         : " + indicators.getGD(population)) ;
      logger_.info("IGD        : " + indicators.getIGD(population)) ;
      logger_.info("Spread     : " + indicators.getSpread(population)) ;
      logger_.info("Epsilon    : " + indicators.getEpsilon(population)) ;  
     
      int evaluations = ((Integer)algorithm.getOutputParameter("evaluations")).intValue();
      logger_.info("Speed      : " + evaluations + " evaluations") ;      
    } // if
  } //main
  
  private static void printVariablesToMidi(SolutionSet solutionsList) throws JMException{
	  Map<Double, Solution> solutionMap = new TreeMap<Double, Solution>();
	  Iterator<Solution> iterator = solutionsList.iterator();
	  int i = 1;
	  while (iterator.hasNext()) {
		MusicSolution solution = (MusicSolution) iterator.next();
		solutionMap.put(solution.getHarmony(), solution);
	  }
	  
	  int[] ensemble = new int[4];
	  ensemble[0] = VIOLIN;
	  ensemble[1] = VIOLIN;
	  ensemble[2] = VIOLA;
	  ensemble[3] = CELLO;
	  for (Solution solution : solutionMap.values()) {
		List<MusicalStructure> sentences = ((MusicVariable)solution.getDecisionVariables()[0]).getMelodies();
//		List<MusicalStructure> structures = FugaUtilities.addTransposedVoices(sentences, inputProps.getScale(), 12, 48, inputProps.getMelodyLength() * 12);
//		sentences.addAll(structures);
//		MusicalStructure structure = FugaUtilities.harmonizeMelody(sentences, inputProps.getScale(), 2, 1);
//		sentences.add(structure);
//		MusicalStructure structure2 = FugaUtilities.harmonizeMelody(sentences, inputProps.getScale(), 2, 2);
//		sentences.add(structure2);
		Score score = ScoreUtilities.createScore2(sentences, null);
		if (i <=10) {
			score.setTitle("test " + (i));
			Write.midi(score, "test" + (i) + ".mid");	
			View.notate(score);
			i++;
		}
	  }
	  
	  int j = 1;
	  for (Solution solution : solutionMap.values()) {
			System.out.println(j + ": " + solution);
			j++;
	  }
	  
//	  for (Solution solution : solutionMap.values()) {
//		  List<MusicalStructure> structures = ((MusicVariable)solution.getDecisionVariables()[0]).getMelodies();
//		  Score score = ScoreUtilities.createScore(structures);
//		  BufferedOutputStream out = null;
//    	  FileOutputStream writer = null;
//    	  try {
//    		 writer = new FileOutputStream("C://midi//test" + (i) + ".mid");
//    		 out = new BufferedOutputStream(writer);
//    		 Write.midi(score, out);
//    	  } catch (IOException e) {
//  			e.printStackTrace();
//  		  }finally{
//	  			try {
//	  				out.close();
//	  				writer.close();
//	  			} catch (IOException e) {
//	  				e.printStackTrace();
//	  		}
//	    	i++;
//  		  }
//	  }

  }
  
} // NSGAII_main
