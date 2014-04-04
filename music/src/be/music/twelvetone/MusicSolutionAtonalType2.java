package be.music.twelvetone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import be.core.TwelveToneSets;
import be.data.Motive;
import be.data.Partition;
import be.data.MusicalStructure;
import be.instrument.Instrument;
import be.util.Populator;
import jmetal.base.Problem;
import jmetal.base.SolutionType;
import jmetal.base.Variable;

public class MusicSolutionAtonalType2 extends SolutionType {

	public int[] scale;
	public List<Instrument> ranges;
	public int melodyLength ;
	private int[] profile;
	private String strategy;
	private int length;
	
	public MusicSolutionAtonalType2(Problem problem, int melodyLenth, int[] scale, int[] profile, String strategy, List<Instrument> ranges, int length) {
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
		
		List<Partition> atonalMotives = new ArrayList<Partition>();
//		List<Integer> row = TwelveToneSets.randomizeSet(TwelveToneSets.twelveToneSet);
		List<Integer> hexaRow1 = Arrays.asList(TwelveToneSets.violinConcertoSchoenbergHexa1);
		
		List<Integer> hexaRow2 = Arrays.asList(TwelveToneSets.violinConcertoSchoenbergHexa2);
		
//		RowMatrix rowMatrix = new RowMatrix(hexaRow1.size(), hexaRow1);
//		rowMatrix.show();
//		List<Integer> hexaList1 = rowMatrix.transposeSet(0);
//		List<Integer> hexaList1 = rowMatrix.retrogradeTransposeSet(9);
//		List<Integer> hexaList1 = rowMatrix.transposeInverseSet(4);
//		List<Integer> hexaList1 = rowMatrix.retrogradeTransposeInverseSet(3);
//		List<Integer> hexaList1 = rowMatrix.multiply(hexaRow1);
//		List<Integer> hexaList1 = rowMatrix.multiplyInverse(hexaRow1);
		
		Instrument instrumentRange = ranges.get(0);
		Partition motiveAtonal = Populator.getInstance().generateRow(hexaRow1, instrumentRange, 0);
		atonalMotives.add(motiveAtonal);
		
		Instrument instrumentRange2 = ranges.get(1);
		Partition motiveAtonal2 = Populator.getInstance().generateRow(hexaRow2, instrumentRange2,0);
		atonalMotives.add(motiveAtonal2);

//		List<Integer> hexaRow2 = Arrays.asList(TwelveToneSets.violinConcertoSchoenbergHexa2);
//		instrumentRange = ranges.get(2);
//		MotiveAtonal motiveAtonal2 = Populator.getInstance().generateRow(hexaRow2, instrumentRange, 0);
//		atonalMotives.add(motiveAtonal2);
		
		MusicVariableAtonal2 musicVariable = new MusicVariableAtonal2(atonalMotives, ranges.size());
		variables[0] = musicVariable;
		return variables ;
	}	


}
