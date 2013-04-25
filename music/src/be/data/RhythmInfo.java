package be.data;

public class RhythmInfo {
	
	private double position;
	private double postitionInBar;
	private boolean accent;
	private double rhythmValue;
	private boolean onBeat;
	
	public boolean isOnBeat() {
		if (postitionInBar == 0.0 || postitionInBar == 1.0 || postitionInBar == 2.0 || postitionInBar == 3.0) {
			return true;
		}
		if (rhythmValue < 1.0 && (postitionInBar == 0.5 || postitionInBar == 1.5 || postitionInBar == 2.5 || postitionInBar == 3.5)) {
			return true;
		}
		return false;
	}
	public void setOnBeat(boolean onBeat) {
		this.onBeat = onBeat;
	}
	public double getPosition() {
		return position;
	}
	public void setPosition(double position) {
		this.position = position;
	}
	public double getPostitionInBar() {
		return postitionInBar;
	}
	public void setPostitionInBar(double postitionInBar) {
		this.postitionInBar = postitionInBar;
	}
	public boolean isAccent() {
		return accent;
	}
	public void setAccent(boolean isAccent) {
		this.accent = isAccent;
	}
	public double getRhythmValue() {
		return rhythmValue;
	}
	public void setRhythmValue(double rhythmValue) {
		this.rhythmValue = rhythmValue;
	}
	
	@Override
	public String toString() {
		return "Position: " + position + ", Position in bar: " + postitionInBar + ", RhythmValue: " + rhythmValue + ", On beat: " + isOnBeat();
	}

}
