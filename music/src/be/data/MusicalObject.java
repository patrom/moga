package be.data;

public class MusicalObject implements Comparable<MusicalObject>{

	private int pitch;
	private int pitchClass;
	private int voice;
	private int octave;
	private int dynamic;
	
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
	public int compareTo(MusicalObject musicalObject) {
		if (musicalObject != null) {
			return Integer.valueOf(this.pitch).compareTo(musicalObject.getPitch());
		}
		return -1;
	}
	@Override
	public String toString() {
		return "MusicalObject [pitch=" + pitch + "]";
	}
	
}
