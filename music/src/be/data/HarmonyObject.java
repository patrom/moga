package be.data;

public class HarmonyObject implements Comparable<HarmonyObject> {
	
	private int pitch;
	private int pitchClass;
	private int voice;
	private int octave;
	private int dynamic;
	private int position;
	private int length;
	private double weight;
	private double positionWeight;
	private double innerMetricWeight = 0.01;//default calculate geometric mean!
	private int sentenceLength;

	public int getSentenceLength() {
		return sentenceLength;
	}

	public void setSentenceLength(int sentenceLength) {
		this.sentenceLength = sentenceLength;
	}

	public int getDynamic() {
		return dynamic;
	}

	public void setDynamic(int dynamic) {
		this.dynamic = dynamic;
	}

	public int getPitch() {
		return pitch;
	}

	public void setPitch(int pitch) {
		this.pitch = pitch;
		if (this.pitch != Integer.MIN_VALUE) {
			setPitchClass(this.pitch % 12);
			setOctave((int) Math.floor(pitch/12));
		}else{
			setPitchClass(0);
			setOctave(0);
		}
	}

	public int getPitchClass() {
		return pitchClass;
	}

	public void setPitchClass(int pitchClass) {
		this.pitchClass = pitchClass;
	}

	public int getVoice() {
		return voice;
	}

	public void setVoice(int voice) {
		this.voice = voice;
	}

	public int getOctave() {
		return octave;
	}

	public void setOctave(int octave) {
		this.octave = octave;
	}

	public int compareTo(HarmonyObject musicalObject) {
		if (musicalObject != null) {
			return Integer.valueOf(this.pitch).compareTo(
					musicalObject.getPitch());
		}
		return -1;
	}

	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "[p=" + ((pitch == Integer.MIN_VALUE) ? "Rest":pitch) + ", pc=" + pitchClass
				+ ", v=" + voice + ", pos=" + position +  ", l=" + length + ", pos w="
				+ positionWeight + ", i w=" + innerMetricWeight + "]";
	}

	public double getPositionWeight() {
		return positionWeight;
	}

	public void setPositionWeight(double positionWeight) {
		this.positionWeight = positionWeight;
	}

	public double getInnerMetricWeight() {
		return innerMetricWeight;
	}

	public void setInnerMetricWeight(double innerMetricWeight) {
		this.innerMetricWeight = innerMetricWeight;
	}

}
