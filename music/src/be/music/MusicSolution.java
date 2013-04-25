package be.music;

import jmetal.base.Problem;
import jmetal.base.Solution;

public class MusicSolution extends Solution {
	
	private double harmony;
	private double melody;
	private double voiceLeading;
	private double rhythm;
	private double tonality;
	
	private double constraintLowestInterval;
	private double constraintRhythm;
	private double constraintRepetition;
	
	public MusicSolution(Problem problem) throws ClassNotFoundException {
		super(problem);
	}
	
	public MusicSolution(Solution solution) {
		super(solution);
	}

	public double getConstraintLowestInterval() {
		return constraintLowestInterval;
	}

	public void setConstraintLowestInterval(double constraintLowestInterval) {
		this.constraintLowestInterval = constraintLowestInterval;
	}

	public double getConstraintRhythm() {
		return constraintRhythm;
	}

	public void setConstraintRhythm(double constraintRhythm) {
		this.constraintRhythm = constraintRhythm;
	}

	public double getConstraintRepetition() {
		return constraintRepetition;
	}

	public void setConstraintRepetition(double constraintRepetition) {
		this.constraintRepetition = constraintRepetition;
	}

	public double getHarmony() {
		return harmony;
	}
	public void setHarmony(double harmony) {
		this.harmony = harmony;
	}
	public double getMelody() {
		return melody;
	}
	public void setMelody(double melody) {
		this.melody = melody;
	}
	public double getVoiceLeading() {
		return voiceLeading;
	}
	public void setVoiceLeading(double voiceLeading) {
		this.voiceLeading = voiceLeading;
	}
	public double getRhythm() {
		return rhythm;
	}
	public void setRhythm(double rhythm) {
		this.rhythm = rhythm;
	}
	public double getTonality() {
		return tonality;
	}
	public void setTonality(double tonality) {
		this.tonality = tonality;
	}
	
	@Override
	public String toString() {
		return "MusicSolution [harmony=" + harmony + ", melody=" + melody
				+ ", voiceLeading=" + voiceLeading + ", rhythm=" + rhythm
				+ ", tonality=" + tonality + ", "
				+ " Constraints: LowestInterval=" + constraintLowestInterval 
				+ ", Rhythm=" + constraintRhythm 
				+ ", Repetition=" + constraintRepetition 
				+ ", overall Violation=" + getOverallConstraintViolation()
				+ ", number Violations=" + getNumberOfViolatedConstraint()+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(harmony);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(melody);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(rhythm);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(tonality);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(voiceLeading);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MusicSolution other = (MusicSolution) obj;
		if (Double.doubleToLongBits(harmony) != Double
				.doubleToLongBits(other.harmony))
			return false;
		if (Double.doubleToLongBits(melody) != Double
				.doubleToLongBits(other.melody))
			return false;
		if (Double.doubleToLongBits(rhythm) != Double
				.doubleToLongBits(other.rhythm))
			return false;
		if (Double.doubleToLongBits(tonality) != Double
				.doubleToLongBits(other.tonality))
			return false;
		if (Double.doubleToLongBits(voiceLeading) != Double
				.doubleToLongBits(other.voiceLeading))
			return false;
		return true;
	}
}
