package be.moga.decorator;

import java.util.List;

import be.data.MusicalStructure;
import be.moga.MusicEvaluation;
import be.moga.MusicProperties;
import be.util.FugaUtilities;

public class FugaDecorator extends EvaluationDecorator {
	
	private int transposition;
	private int offset;

	public FugaDecorator(MusicEvaluation impl, int transposition, int offset, MusicProperties props) {
		super(impl, props);
		this.transposition = transposition;
		this.offset = offset;
	}

	public double[] evaluate(List<MusicalStructure> sentences) {
		int length = properties.getMelodyLength() * 12;
		List<MusicalStructure> structures = FugaUtilities.addTransposedVoices(sentences, properties.getScale(), transposition, offset, length);
		sentences.addAll(structures);
		double[] values = impl.evaluate(sentences);
		sentences.removeAll(structures);
		return values;
	}

}
