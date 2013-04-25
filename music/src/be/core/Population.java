package be.core;


import java.util.ArrayList;
import java.util.List;

import jm.music.data.Phrase;

/**
 * Population
 * @author PRombouts
 *
 */
public class Population {

	private List<Phrase> phrases = new ArrayList<Phrase>();
	private Phrase bestFitnessPhrase;
	private Phrase givenPhrase;

	public Phrase getGivenPhrase() {
		return givenPhrase;
	}

	public void setGivenPhrase(Phrase givenPhrase) {
		this.givenPhrase = givenPhrase;
	}

	public Phrase getBestFitnessPhrase() {
		return bestFitnessPhrase;
	}

	public void setBestFitnessPhrase(Phrase bestFitnessPhrase) {
		this.bestFitnessPhrase = bestFitnessPhrase;
	}

	public Population() {
	}

	public void addPhrase(Phrase phrase) {
		phrases.add(phrase);
	}

	public boolean removePhrase(Phrase phrase) {
		return phrases.remove(phrase);
	}

	public Phrase getPhrase(int index) {
		return phrases.get(index);
	}

	public List<Phrase> getPhrases() {
		return phrases;
	}

	public void setPhrases(List<Phrase> phrases) {
		this.phrases = phrases;
	}
	
	public int getSize() {
		return phrases.size();
	}

}
