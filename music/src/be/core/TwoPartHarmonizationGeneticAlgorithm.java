package be.core;


import java.util.Collections;
import java.util.List;
import java.util.Random;

import jm.music.data.Note;
import jm.music.data.Phrase;

/**
 * Genetic Algorithm for two part harmonisation
 * @author PRombouts
 *
 */
public class TwoPartHarmonizationGeneticAlgorithm extends GeneticAlgorithmTemplate {

	
	public TwoPartHarmonizationGeneticAlgorithm(Fitness fitness) {
		super(fitness);
	}

	/**
	 * Initialisation of the population
	 * @param population the population
	 */
	public void initPopulation(Population population) {	
		//init given phrase
		generator = PhraseGenerator.getInstance();
		Phrase givenPhrase = generator.generateHighPhrase(10, 4.0);
		double melodic = fitness.calculateMelodicFitnessPhrase(givenPhrase);
		while (melodic < 0.75) {
			givenPhrase = generator.generateHighPhrase(10, 4.0);
			melodic = fitness.calculateMelodicFitnessPhrase(givenPhrase);
		}
		population.setGivenPhrase(givenPhrase);
		//init phrases
		while (population.getSize() < 20) {
			Phrase phrase = generator.generateLowPhrase(10, 4.0);
			population.addPhrase(phrase);
		}		
	}
		
	
	/**
	 * Reproduction - selection of the best phrase and
	 * replacing the bad phrase
	 * @param population the population
	 */
	public void select(Population population) {
		List<Phrase> phrases = population.getPhrases();	
		Phrase firstPhrase = phrases.get(0);
		double fitness = getTotalFitness(firstPhrase, population.getGivenPhrase(), 4.0);
		double highestFitness = fitness;
		double lowestFitness = fitness;
		Phrase bestPhrase = firstPhrase;
		Phrase badPhrase = firstPhrase;
		for (int i = 1; i < phrases.size(); i++) {
			Phrase phrase = phrases.get(i);
			fitness = getTotalFitness(phrase,population.getGivenPhrase(), 4.0);
			if (fitness > highestFitness) {
				highestFitness = fitness;
				bestPhrase = phrase;
			}
			else if (fitness <= lowestFitness) {
				lowestFitness = fitness;
				badPhrase = phrase;
			}
		}
		double fitnessBestPhrase = 0.0;
		if (population.getBestFitnessPhrase() != null) {
			fitnessBestPhrase = getTotalFitness(population.getBestFitnessPhrase(),population.getGivenPhrase(), 4.0);
		}	
		if (fitnessBestPhrase < highestFitness) {
			//new best phrase
			population.setBestFitnessPhrase(bestPhrase);
		}
		phrases.remove(badPhrase);
		phrases.add(population.getBestFitnessPhrase());		
	}

	@Override
	public void mutate(Population population){
		Random random = new Random();
		int mutate = random.nextInt(population.getSize() - 1);
		Phrase phrase = population.getPhrase(mutate);
		Note[] notes = phrase.getNoteArray();
		//don't mutate best phrase
		if (population.getBestFitnessPhrase() != phrase) {
			int replace = random.nextInt(notes.length - 1);
			phrase.setNote(generator.getLowNote(4.0), replace);
		}
	}
	
	
	/**Crossover
	 * C
	 * @param population the population
	 */
	public void crossover(Population population) {
		List<Phrase> phrases = population.getPhrases();
		// randomize phrases
		Collections.shuffle(phrases);
		Random random = new Random();
		//even aantal melodieën!
		for (int i = 0; i < phrases.size(); i = i + 2) {
			// randomize crossover (50%)
			if (random.nextBoolean()) {
				Phrase firstPhrase = phrases.get(i);
				Phrase secondPhrase = phrases.get(i + 1);
				Note[] notesFirstPhrase = firstPhrase.getNoteArray();
				Note[] notesSecondPhrase = secondPhrase.getNoteArray();
				Note[] tempArray1 = new Note[notesFirstPhrase.length];
				Note[] tempArray2 = new Note[notesSecondPhrase.length];
				int crossoverSplit = random.nextInt(notesFirstPhrase.length - 1) + 1;
				for (int j = 0; j < notesFirstPhrase.length; j++) {
					if (j < crossoverSplit) {
						tempArray1[j] = notesFirstPhrase[j];
						tempArray2[j] = notesSecondPhrase[j];
					} else {
						tempArray1[j] = notesSecondPhrase[j];
						tempArray2[j] = notesFirstPhrase[j];
					}
				}
				population.removePhrase(firstPhrase);
				population.removePhrase(secondPhrase);
				population.addPhrase(new Phrase(tempArray1));
				population.addPhrase(new Phrase(tempArray2));
			}
		}
	}
}
