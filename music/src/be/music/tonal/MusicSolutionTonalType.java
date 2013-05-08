package be.music.tonal;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.IntStream;

import jmetal.base.Problem;
import jmetal.base.SolutionType;
import jmetal.base.Variable;
import be.core.TwelveToneSets;
import be.data.InstrumentRange;
import be.data.Motive;
import be.data.MusicalStructure;
import be.moga.population.PopulationStrategy;
import be.moga.population.PopulationStrategyFactory;
import be.music.MusicVariable;
import be.util.Populator;

public class MusicSolutionTonalType extends SolutionType {

	public int[] scale;
	public List<InstrumentRange> ranges;
	public int melodyLength ;
	private int[] profile;
	private String strategy;
	private int length;
	
	public MusicSolutionTonalType(Problem problem, int melodyLenth, int[] scale, int[] profile, String strategy, List<InstrumentRange> ranges, int length) {
		super(problem);
		problem.variableType_ = new Class[problem.getNumberOfVariables()];
		problem.setSolutionType(this);
		this.scale = scale;
		this.melodyLength = melodyLenth;
		this.ranges = ranges;
		this.profile = profile;
		this.strategy = strategy;
		this.length = length;
	}

//	@Override
//	public Variable[] createVariables() throws ClassNotFoundException {
//		Variable[] variables = new Variable[problem_.getNumberOfVariables()];
//		Motive motive = Populator.getInstance().generateRow(TwelveToneSets.twelveToneSet);
//		Motive motive2 = new Motive();
//		Motive motive3 = new Motive();
//		List<MusicalStructure> melodies = new ArrayList<MusicalStructure>();
//		melodies.add(motive);
//		melodies.add(motive2);
//		melodies.add(motive3);
//		MusicVariable musicVariable = new MusicVariable(melodies);
//		variables[0] = musicVariable;
//		return variables ;
//	}

	
	
	@Override
	public Variable[] createVariables() throws ClassNotFoundException {
		Variable[] variables = new Variable[problem_.getNumberOfVariables()];
		PopulationStrategy strategy = PopulationStrategyFactory.getPopulationStrategy(this.strategy);
		List<MusicalStructure> melodies = strategy.generateMelodies(melodyLength, ranges, scale, profile, length);
		MusicVariable musicVariable = new MusicVariableTonal(melodies);
		variables[0] = musicVariable;
		return variables ;
	}
	
	

}
