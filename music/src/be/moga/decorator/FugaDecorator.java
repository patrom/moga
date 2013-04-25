package be.moga.decorator;

import java.util.List;

import be.data.MusicalStructure;
import be.moga.MusicEvaluationImpl;
import be.util.FugaUtilities;

public class FugaDecorator extends EvaluationDecorator {
	
	private int transposition;
	private int offset;

	public FugaDecorator(MusicEvaluationImpl impl, int transposition, int offset) {
		super(impl);
		this.transposition = transposition;
		this.offset = offset;
	}

	public double[] evaluate(List<MusicalStructure> sentences) {
		int length = impl.getProperties().getMelodyLength() * 12;
		List<MusicalStructure> structures = FugaUtilities.addTransposedVoices(sentences, impl.getProperties().getScale(), transposition, offset, length);
		sentences.addAll(structures);
		double[] values = impl.evaluate(sentences);
		sentences.removeAll(structures);
		return values;
	}

}
