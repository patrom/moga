package be.instrument;

import be.data.Performance;

public class KontaktLibCello extends Instrument {

	public KontaktLibCello(int voice, int channel) {
		super(voice, channel);
		setLowest(36);
		setHighest(70);
		setKeySwitch(true);
	}

	@Override
	public int getPerformanceValue(Performance performance) {
		switch (performance) {
		case LEGATO:
			return 24;
		case MARCATO:
			return 24;
		case PIZZICATO:
			return 29;
		case STACCATO:
			return 27;
		case PORTATO:
			return 24;
		default:
			return super.getPerformanceValue(performance);
		}
	}
}
