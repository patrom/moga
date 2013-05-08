package be.music.twelvetone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import be.core.TwelveToneSets;
import be.data.InstrumentRange;
import be.data.Motive;
import be.data.Partition;
import be.data.MusicalStructure;
import be.util.Populator;
import jmetal.base.Problem;
import jmetal.base.SolutionType;
import jmetal.base.Variable;

public class MusicSolutionAtonalType2 extends SolutionType {

	public int[] scale;
	public List<InstrumentRange> ranges;
	public int melodyLength ;
	private int[] profile;
	private String strategy;
	private int length;
	
	public MusicSolutionAtonalType2(Problem problem, int melodyLenth, int[] scale, int[] profile, String strategy, List<InstrumentRange> ranges, int length) {
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
		
//		RowMatrix rowMatrix = new RowMatrix(hexaRow1.size(), hexaRow1);
//		rowMatrix.show();
//		List<Integer> hexaList1 = rowMatrix.transposeSet(0);
//		List<Integer> hexaList1 = rowMatrix.retrogradeTransposeSet(9);
//		List<Integer> hexaList1 = rowMatrix.transposeInverseSet(4);
//		List<Integer> hexaList1 = rowMatrix.retrogradeTransposeInverseSet(3);
//		List<Integer> hexaList1 = rowMatrix.multiply(hexaRow1);
//		List<Integer> hexaList1 = rowMatrix.multiplyInverse(hexaRow1);
		
		InstrumentRange instrumentRange = ranges.get(0);
		int position = 0;
		for (Integer integer : hexaRow1) {
			List<Integer> note = new ArrayList<Integer>();
			note.add(integer);
			Partition motiveAtonal = Populator.getInstance().generateRow(note, instrumentRange, position);
			atonalMotives.add(motiveAtonal);
			position = position + 12;
		}
		

//		List<Integer> hexaRow2 = Arrays.asList(TwelveToneSets.violinConcertoSchoenbergHexa2);
//		instrumentRange = ranges.get(2);
//		MotiveAtonal motiveAtonal2 = Populator.getInstance().generateRow(hexaRow2, instrumentRange, 0);
//		atonalMotives.add(motiveAtonal2);
		
		MusicVariableAtonal2 musicVariable = new MusicVariableAtonal2(atonalMotives, ranges.size());
		variables[0] = musicVariable;
		return variables ;
	}	


}
