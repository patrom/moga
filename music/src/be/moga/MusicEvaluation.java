package be.moga;

import java.util.List;

import be.data.MusicalStructure;

public interface MusicEvaluation {

	public double[] evaluate(List<MusicalStructure> structures);
	
}
