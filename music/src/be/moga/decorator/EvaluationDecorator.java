package be.moga.decorator;

import be.moga.MusicEvaluation;
import be.moga.MusicProperties;


public abstract class EvaluationDecorator {
	
	protected MusicEvaluation impl;
	protected MusicProperties properties;
	
	public EvaluationDecorator(MusicEvaluation impl, MusicProperties props) {
		this.properties = props;
		this.impl = impl;
	}
	
}
