package be.moga.harmony;

public class HarmonyStrategyFactory {
	
	private static HarmonyStrategyFactory instance = null;

	public static HarmonyStrategyFactory getInstance(){
		if (instance == null) {
			instance = new HarmonyStrategyFactory();
		}
		return instance;
	}
	
	public HarmonyStrategy getHarmonyStategy(String strategy){
		if (strategy.equals("PitchSet")) {
			return new PitchSetStrategy();
		}else if (strategy.equals("Interval")) {
			return new IntervalStrategy();
		}else if (strategy.equals("Sonance")) {
			return new SonanceStrategy();
		}
		return new PitchSetStrategy();
	}

	private HarmonyStrategyFactory() {
	}
	
}
