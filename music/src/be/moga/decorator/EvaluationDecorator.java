package be.moga.decorator;

import be.moga.MusicEvaluation;
import be.moga.MusicEvaluationImpl;


public abstract class EvaluationDecorator implements MusicEvaluation {
	
	protected MusicEvaluationImpl impl;
	
	public EvaluationDecorator(MusicEvaluationImpl impl) {
		super();
		this.impl = impl;
	}
	
}
