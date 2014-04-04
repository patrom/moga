package be.music.twelvetone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.antlr.runtime.IntStream;

import jmetal.base.Problem;
import jmetal.base.SolutionType;
import jmetal.base.Variable;
import be.core.RowMatrix;
import be.core.TwelveToneSets;
import be.data.Motive;
import be.data.Partition;
import be.data.MusicalStructure;
import be.instrument.Instrument;
import be.moga.population.PopulationStrategy;
import be.moga.population.PopulationStrategyFactory;
import be.music.MusicVariable;
import be.util.Populator;

public class MusicSolutionAtonalType extends SolutionType {

	public int[] scale;
	public List<Instrument> ranges;
	public int melodyLength ;
	private int[] profile;
	private String strategy;
	private int length;
	
	public MusicSolutionAtonalType(Problem problem, int melodyLenth, int[] scale, int[] profile, String strategy, List<Instrument> ranges, int length) {
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

	@Override
	public Variable[] createVariables() throws ClassNotFoundException {
		Variable[] variables = new Variable[problem_.getNumberOfVariables()];
		
		List<MusicalStructure> melodies = new ArrayList<MusicalStructure>();
		List<Partition> atonalMotives = new ArrayList<Partition>();
//		List<Integer> row = TwelveToneSets.randomizeSet(TwelveToneSets.twelveToneSet);
		List<Integer> hexaRow1 = Arrays.asList(TwelveToneSets.violinConcertoSchoenbergHexa1);
		
//		RowMatrix rowMatrix = new RowMatrix(hexaRow1.size(), hexaRow1);
//		rowMatrix.show();
//		List<Integer> hexaList1 = rowMatrix.transposeSet(0);
//		List<Integer> hexaList1 = rowMatrix.retrogradeTransposeSet(9);
//		List<Integer> hexaList1 = rowMatrix.transposeInverseSet(4);
//		List<Integer> hexaList1 = rowMatrix.retrogradeTransposeInverseSet(3);
//		List<Integer> hexaList1 = rowMatrix.multiply(hexaRow1);
//		List<Integer> hexaList1 = rowMatrix.multiplyInverse(hexaRow1);
		
		Instrument instrumentRange = ranges.get(0);
		Partition motiveAtonal = Populator.getInstance().generateRow(hexaRow1, instrumentRange, 12);
		atonalMotives.add(motiveAtonal);
		
		Motive motive = new Motive(motiveAtonal.getNotes(), motiveAtonal.getLength());
		motive.setLowestRange(instrumentRange.getLowest());
		motive.setHighestRange(instrumentRange.getHighest());
		motive.setVoice(instrumentRange.getVoice());
		melodies.add(0,motive);
		
		List<Integer> hexaRow2 = Arrays.asList(TwelveToneSets.violinConcertoSchoenbergHexa2);
		instrumentRange = ranges.get(2);
		Partition motiveAtonal2 = Populator.getInstance().generateRow(hexaRow2, instrumentRange, 0);
		atonalMotives.add(motiveAtonal2);
		
		motive = new Motive(motiveAtonal2.getNotes(), motiveAtonal2.getLength());
		motive.setLowestRange(instrumentRange.getLowest());
		motive.setHighestRange(instrumentRange.getHighest());
		motive.setVoice(instrumentRange.getVoice());
		melodies.add(1,motive);
		
		Instrument range = ranges.get(1);
		Motive mot = new Motive();
		mot.setLowestRange(range.getLowest());
		mot.setHighestRange(range.getHighest());
		mot.setVoice(range.getVoice());
		melodies.add(1,mot);
		
		range = ranges.get(3);
		mot = new Motive();
		mot.setLowestRange(range.getLowest());
		mot.setHighestRange(range.getHighest());
		mot.setVoice(range.getVoice());
		melodies.add(3,mot);
		
		MusicVariableAtonal musicVariable = new MusicVariableAtonal(atonalMotives, melodies);
		variables[0] = musicVariable;
		return variables ;
	}	


}
