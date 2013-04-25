package be.data;

public class IntervalData implements Comparable<IntervalData>{
	private double rhythmWeight;
	private double length;
	private double dynamic;
	private double harmonicWeight;
	private NotePos lowerNote;
	private NotePos higherNote;
	private Interval interval;
	public Interval getInterval() {
		return interval;
	}

	public void setInterval(Interval interval) {
		this.interval = interval;
	}

	private int position;
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public NotePos getLowerNote() {
		return lowerNote;
	}

	public void setLowerNote(NotePos firstNote) {
		this.lowerNote = firstNote;
	}

	public NotePos getHigherNote() {
		return higherNote;
	}

	public void setHigherNote(NotePos secondNote) {
		this.higherNote = secondNote;
	}

	public double getHarmonicWeight() {
		return harmonicWeight;
	}

	public void setHarmonicWeight(double harmonicWeight) {
		this.harmonicWeight = harmonicWeight;
	}

	public double getRhythmWeight() {
		return rhythmWeight;
	}

	public void setRhythmWeight(double rhythmWeight) {
		this.rhythmWeight = rhythmWeight;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getDynamic() {
		return dynamic;
	}

	public void setDynamic(double dynamic) {
		this.dynamic = dynamic;
	}

	public int compareTo(IntervalData o) {
		if (this.getPosition() < o.getPosition()) {
			return -1;
		} if (this.getPosition() > o.getPosition()) {
			return 1;
		} else {
			return 0;
		}
	}

}
