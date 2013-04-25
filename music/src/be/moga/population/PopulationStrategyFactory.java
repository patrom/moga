package be.moga.population;



public class PopulationStrategyFactory {

	public static PopulationStrategy getPopulationStrategy(String strategy){ 
		if (strategy.equals("homophonic")) {
			return new HomophonicStrategy();
		} 
		else if (strategy.equals("polyphonic")) {
			return new PolyPhonicStrategy();
		}
		else if (strategy.equals("polyphonicWithRhythmProfile")) {
			return new PolyPhonicWithRhythmProfileStrategy();
		}
		return null;
	}
}
