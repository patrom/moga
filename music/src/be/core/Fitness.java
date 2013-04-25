package be.core;


import jm.music.data.Phrase;

/**
 * Fitness calculations
 * @author PRombouts
 *
 */
public interface Fitness {

	/**
	 * Calculation of the melodic fitness of a phrase/melody
	 * @param phrase the melody
	 * @return the melodic fitness of the phrase
	 */
	public double calculateMelodicFitnessPhrase(Phrase phrase);

	/**
	 * Calculates the harmonic fitness of two phrases/melodies 
	 * @param phrase the counter melody
	 * @param givenPhrase the given melody
	 * @param beat the beat at which the calcultion should be done
	 * @return the harmonic fitness
	 */
	public double calculateHarmonicFitnessPhrase(Phrase phrase, Phrase givenPhrase, double beat);

	
	/**
	 * Gets the total fitness of a phrase against a given phrase
	 * @param phrase the phrase
	 * @param givenPhrase the given phrase
	 * @param beat
	 * @return
	 */
	public double getFitness(Phrase phrase, Phrase givenPhrase, double beat);
	

}
