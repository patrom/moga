package be.core;


import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.View;
import jm.util.Write;

/**
 * Template for the genetic algorithm
 * @author PRombouts
 *
 */
public abstract class GeneticAlgorithmTemplate {

	protected PhraseGenerator generator;

	protected Fitness fitness;
	
	private Population population;

	/**
	 * Constructor
	 */
	public GeneticAlgorithmTemplate(Fitness fitness) {
		this.fitness = fitness;
		generator = PhraseGenerator.getInstance();
	}
	

	/**
	 * The genitic algorithm
	 */
	public void geneticAlgorithm(){
		population = new Population();
		initPopulation(population);
		for (int i = 0; i < 100; i++) {		
			select(population);
			crossover(population);
			mutate(population);		
		}
	}

	public void viewScore(Population population) {
		Part part1 = new Part();
		Part part2 = new Part();
		part1.add(population.getGivenPhrase());
		part2.add(population.getBestFitnessPhrase());
		Score score = new Score();
		score.setTempo(250.0);
		score.addPart(part1);
		score.addPart(part2);
		View.notate(score);
		Write.midi(score, "twopartharmonisation5.mid");
        Play.midi(score);
	}

	
	protected abstract void mutate(Population population);

	protected abstract void crossover(Population population);

	protected abstract void select(Population population);

	protected abstract void initPopulation(Population population);
	


	/**
	 * Gets the fitness for a phrase
	 * @param phrase the phrase
	 * @param beat the beat
	 * @return the fitness
	 */
	public double getTotalFitness(Phrase phrase, Phrase givenPhrase, double beat) {
		return fitness.getFitness(phrase, givenPhrase, beat);
	}
	
	public Population getPopulation() {
		return population;
	}

	public void setPopulation(Population population) {
		this.population = population;
	}

	
	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GeneticAlgorithmTemplate ga = new TwoPartHarmonizationGeneticAlgorithm(new FitnessImpl());
		ga.geneticAlgorithm();
		ga.viewScore(ga.getPopulation());
		ga.getPopulation().getBestFitnessPhrase();
	}
}