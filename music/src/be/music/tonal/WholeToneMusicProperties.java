package be.music.tonal;

import be.data.Scale;

public class WholeToneMusicProperties {
	
	//harmony
	private double harmonyConsDissValue = 0.2;
	private int allowChordsOfPitchesOrHigher = 3;
	private String harmonyStrategy;
	
	//melody
	private double melodyConsDissValue = 0.2;//hoe lager, hoe cons - stapsgewijs
	private int allowIntervalsBelowValue = 11;//niet gebruikt - melody nu met window size!!
	
	//voice leading
	private double voiceLeadingConsDissValue;
	private String voiceLeadingStrategy;
	
	//rhythm - context
	private int rhythmTemplateValue = 5;//add to every note + template accents
	private int innerMetricFactor = 10;// add 100/innerMetricFactor
	private int[] rhythmProfile = {0,0,0,10,10};//whole note (with divisions), half note, quarter note, triplet half note, triplet quarter note.
	
	//tonality
	private int[] scale = Scale.WHOLE_TONE_SCALE;
	
	//generation
	private int numerator = 4;//2/4,4/4,3/4 - 6/8
	private int voices = 3;
	private int melodyLength = 8;
	private String populationStrategy = "polyphonic";//homophonic or polyphonic
	
	//MOGA
	private int populationSize;
	private int maxEvaluations;
	private double crossoverProbability;
	private double mutationProbability;
	
	public String getHarmonyStrategy() {
		return harmonyStrategy;
	}
	public void setHarmonyStrategy(String harmonyStrategy) {
		this.harmonyStrategy = harmonyStrategy;
	}
	public String getVoiceLeadingStrategy() {
		return voiceLeadingStrategy;
	}
	public void setVoiceLeadingStrategy(String voiceLeadingStrategy) {
		this.voiceLeadingStrategy = voiceLeadingStrategy;
	}
	
	public int getAllowIntervalsBelowValue() {
		return allowIntervalsBelowValue;
	}
	public void setAllowIntervalsBelowValue(int allowIntervalsBelowValue) {
		this.allowIntervalsBelowValue = allowIntervalsBelowValue;
	}
	
	public String getPopulationStrategy() {
		return populationStrategy;
	}
	public void setPopulationStrategy(String populationStrategy) {
		this.populationStrategy = populationStrategy;
	}
	public double getHarmonyConsDissValue() {
		return harmonyConsDissValue;
	}
	public void setHarmonyConsDissValue(double harmonyConsDissValue) {
		this.harmonyConsDissValue = harmonyConsDissValue;
	}
	public int getAllowChordsOfPitchesOrHigher() {
		return allowChordsOfPitchesOrHigher;
	}
	public void setAllowChordsOfPitchesOrHigher(int allowChordsOfPitchesOrHigher) {
		this.allowChordsOfPitchesOrHigher = allowChordsOfPitchesOrHigher;
	}
	public double getMelodyConsDissValue() {
		return melodyConsDissValue;
	}
	public void setMelodyConsDissValue(double melodyConsDissValue) {
		this.melodyConsDissValue = melodyConsDissValue;
	}
	public double getVoiceLeadingConsDissValue() {
		return voiceLeadingConsDissValue;
	}
	public void setVoiceLeadingConsDissValue(double voiceLeadingConsDissValue) {
		this.voiceLeadingConsDissValue = voiceLeadingConsDissValue;
	}
	public int getRhythmTemplateValue() {
		return rhythmTemplateValue;
	}
	public void setRhythmTemplateValue(int rhythmTemplateValue) {
		this.rhythmTemplateValue = rhythmTemplateValue;
	}
	public int getInnerMetricFactor() {
		return innerMetricFactor;
	}
	public void setInnerMetricFactor(int innerMetricFactor) {
		this.innerMetricFactor = innerMetricFactor;
	}
	public int[] getScale() {
		return scale;
	}
	public void setScale(int[] scale) {
		this.scale = scale;
	}
	public int getNumerator() {
		return numerator;
	}
	public void setNumerator(int numerator) {
		this.numerator = numerator;
	}
	public int getVoices() {
		return voices;
	}
	public void setVoices(int voices) {
		this.voices = voices;
	}
	public int getMelodyLength() {
		return melodyLength;
	}
	public void setMelodyLength(int melodyLength) {
		this.melodyLength = melodyLength;
	}
	public int getPopulationSize() {
		return populationSize;
	}
	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}
	public int getMaxEvaluations() {
		return maxEvaluations;
	}
	public void setMaxEvaluations(int maxEvaluations) {
		this.maxEvaluations = maxEvaluations;
	}
	public double getCrossoverProbability() {
		return crossoverProbability;
	}
	public void setCrossoverProbability(double crossoverProbability) {
		this.crossoverProbability = crossoverProbability;
	}
	public double getMutationProbability() {
		return mutationProbability;
	}
	public void setMutationProbability(double mutationProbability) {
		this.mutationProbability = mutationProbability;
	}
	public int[] getRhythmProfile() {
		return rhythmProfile;
	}
	public void setRhythmProfile(int[] rhythmProfile) {
		this.rhythmProfile = rhythmProfile;
	}
}
