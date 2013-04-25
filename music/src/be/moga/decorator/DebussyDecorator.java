package be.moga.decorator;

import java.util.List;

import be.data.MusicalStructure;
import be.moga.MusicEvaluationImpl;
import be.util.FugaUtilities;

public class DebussyDecorator extends EvaluationDecorator {

	public DebussyDecorator(MusicEvaluationImpl impl) {
		super(impl);
	}

	public double[] evaluate(List<MusicalStructure> sentences) {
		int length = impl.getProperties().getMelodyLength() * 12;
		MusicalStructure structure1 = FugaUtilities.harmonizeMelody(sentences, impl.getProperties().getScale(), 2, 1, length);
		sentences.add(structure1);
		MusicalStructure structure2 = FugaUtilities.harmonizeMelody(sentences, impl.getProperties().getScale(), 2, 2, length);
		sentences.add(structure2);
		double[] values = impl.evaluate(sentences);
		sentences.remove(structure1);
		sentences.remove(structure2);
		return values;
	}

}
