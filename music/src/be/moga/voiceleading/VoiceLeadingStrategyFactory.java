package be.moga.voiceleading;

public class VoiceLeadingStrategyFactory {

	private static VoiceLeadingStrategyFactory instance = null;

	public static VoiceLeadingStrategyFactory getInstance(){
		if (instance == null) {
			instance = new VoiceLeadingStrategyFactory();
		}
		return instance;
	}
	
	private VoiceLeadingStrategyFactory() {
	}
	
	public VoiceLeadingStrategy getVoiceLeadingStategy(){
		return new TaxiCabVoiceLeading();
	}
}
