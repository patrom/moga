package be.music.tonal;

import jmetal.base.Problem;
import jmetal.base.Solution;
import jmetal.base.Variable;
import jmetal.util.JMException;
import net.sourceforge.jFuzzyLogic.membership.MembershipFunction;
import net.sourceforge.jFuzzyLogic.membership.MembershipFunctionTriangular;
import net.sourceforge.jFuzzyLogic.membership.Value;
import be.moga.MusicEvaluationImpl;
import be.moga.MusicProperties;
import be.moga.decorator.DebussyDecorator;
import be.moga.decorator.FugaDecorator;
import be.music.MusicSolution;
import be.music.MusicVariable;

public class MusicTonalProblem extends Problem {
	
	private MusicProperties properties;

	private MembershipFunction melodyMembershipFunction;
	private MembershipFunction harmonyMembershipFunction;
	 /** 
	   * Constructor.
	   * Creates a new instance of the music problem.
	   * @param numberOfVariables Number of variables of the problem 
	   * @param solutionType The solution type. 
	 * @param innerMetricFactor 
	 * @param rhythmTemplateValue 
	 * @param dynamic 
	 * @param pulse 
	 * @param melodyLenth 
	 * @param voices 
	 * @param scale 
	   */
	  public MusicTonalProblem(String solutionType, int numberOfVariables, MusicProperties inputProps) throws ClassNotFoundException {
	    numberOfVariables_   = numberOfVariables ;
	    numberOfObjectives_  = 5                           ;
	    numberOfConstraints_ = 0                            ;
	    problemName_         = "MusicProblem"                    ;
	        
	    upperLimit_ = new double[numberOfVariables_] ;
	    lowerLimit_ = new double[numberOfVariables_] ;
	    
	    this.properties = inputProps;
	    this.melodyMembershipFunction = new MembershipFunctionTriangular(new Value(0.0), new Value(inputProps.getMelodyConsDissValue()) , new Value(1.0));
	    this.harmonyMembershipFunction = new MembershipFunctionTriangular(new Value(0.0), new Value(inputProps.getHarmonyConsDissValue()) , new Value(1.0));
	    
	    if (solutionType.equals("music"))
	    	solutionType_ = new MusicSolutionTonalType(this, inputProps.getMelodyLength(), inputProps.getScale()
	    			, inputProps.getRhythmProfile(), inputProps.getPopulationStrategy(), inputProps.getRanges(), inputProps.getMelodyLength() * 12) ;
	    else {
	    	System.out.println("Error: solution type " + solutionType + " invalid") ;
	    	System.exit(-1) ;
	    }
	  }

	@Override
	public void evaluate(Solution solution) throws JMException {
		Variable[] variables = solution.getDecisionVariables();
		MusicEvaluationImpl controller = new MusicEvaluationImpl(properties);
//		FugaDecorator decorator = new FugaDecorator(controller, 12, 48);
//		DebussyDecorator decorator = new DebussyDecorator(controller);
		double[] objectives = controller.evaluate(((MusicVariable)variables[0]).getMelodies());
		
		double harmonyObjective = 1 - harmonyMembershipFunction.membership(objectives[0]);
		solution.setObjective(0, harmonyObjective);//harmony
//		solution.setObjective(0, objectives[0]);//harmony
		solution.setObjective(1, objectives[1] - 1);//voice leading size: - 1 because 1 is the optimal value to be minimized!
		double melodyObjective = 1 - melodyMembershipFunction.membership(objectives[2]);
		solution.setObjective(2, melodyObjective);//melody
		solution.setObjective(3, objectives[3]);//rhythm
		
		solution.setObjective(4, 1 - objectives[4]);//tonality

//		solution.setObjective(4, objectives[7]);//repetition of notes (0 = repeat notes)
		
//		objectives[0] = 1 - totalHarmony;
//		objectives[1] = voiceLeadingMean;
//		objectives[2] = 1 - melodicValue;
//		objectives[3] = rhythmicValue;
//		objectives[4] = 1 - tonalityValue;
//		//constraints
//		objectives[5] = lowestIntervalRegisterValue;
//		objectives[6] = repetitionsPitchesMean;	//only for small motives (5 - 10 notes)
//		objectives[7] = repetitionsrhythmsMean;	//only for small motives (5 - 10 notes)
		
		MusicSolution musicSolution = (MusicSolution) solution;
//		musicSolution.setHarmony(objectives[0]);
		musicSolution.setHarmony(harmonyObjective);
		musicSolution.setVoiceLeading(objectives[1]);
//		musicSolution.setMelody(objectives[2]);
		musicSolution.setMelody(melodyObjective);
		musicSolution.setRhythm(objectives[3]);
		musicSolution.setTonality(objectives[4]);
//		musicSolution.setConstraintLowestInterval(objectives[5]);
//		musicSolution.setConstraintRhythm(objectives[6]);
//		musicSolution.setConstraintRepetition(objectives[7]);

	}
	

	@Override
	public void evaluateConstraints(Solution solution) throws JMException {
		 solution.setNumberOfViolatedConstraint(0);
		 solution.setOverallConstraintViolation(0);
//		MusicSolution musicSolution = (MusicSolution) solution;
//	    int violation = 0 ;
//	    double total = 0.0;
	    //harmony
//	    if (musicSolution.getObjective(0) > 0.2) {
//	       total += solution.getObjective(0);
//	       violation++ ;
//	    }
//	    //lowest interval
//	    if (musicSolution.getConstraintLowestInterval() > 0.0) {
//	       total += musicSolution.getConstraintLowestInterval();
//	       violation++ ;
//	    }
//	    //rhythm
//	    if (musicSolution.getConstraintRhythm() > 0.7) {
//	       total += musicSolution.getConstraintRhythm();
//	       violation++ ;
//	    }
//	    //repetition
//	    if (musicSolution.getConstraintRepetition() > 0.7) {
//	       total += musicSolution.getConstraintRepetition();
//	       violation++ ;
//	    }
//	    
//	    solution.setOverallConstraintViolation(total);
//	    solution.setNumberOfViolatedConstraint(violation) ;     
	}

}
