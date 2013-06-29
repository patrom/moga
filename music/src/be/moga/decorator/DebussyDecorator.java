package be.moga.decorator;

import java.util.List;

import be.data.MusicalStructure;
import be.moga.MusicEvaluation;
import be.moga.MusicProperties;
import be.util.FugaUtilities;

public class DebussyDecorator extends EvaluationDecorator {

	public DebussyDecorator(MusicEvaluation impl, MusicProperties props) {
		super(impl, props);
	}

	public double[] evaluate(List<MusicalStructure> sentences) {
		int length = properties.getMelodyLength() * 12;
		MusicalStructure structure1 = FugaUtilities.harmonizeMelody(sentences, properties.getScale(), 2, 1, length);
		sentences.add(structure1);
		MusicalStructure structure2 = FugaUtilities.harmonizeMelody(sentences, properties.getScale(), 2, 2, length);
		sentences.add(structure2);
		double[] values = impl.evaluate(sentences);
		sentences.remove(structure1);
		sentences.remove(structure2);
		return values;
	}

}
