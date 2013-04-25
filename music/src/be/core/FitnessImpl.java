package be.core;


import be.data.Interval;
import jm.music.data.Note;
import jm.music.data.Phrase;
import net.sourceforge.jFuzzyLogic.FIS;
//import net.sourceforge.jFuzzyLogic.rule.FuzzyRuleSet;

/**
 * Calculates fitness (harmonic and melodic)
 * @author PRombouts
 *
 */
public class FitnessImpl implements Fitness {
	
	public FitnessImpl() {
	}

	/**
	 * Gets the melodic value of the interval
	 * @param firstNote the first note of the interval
	 * @param secondNote the second note of the interval
	 * @return the value
	 */
	public double getMelodicValue(Note firstNote, Note secondNote) {
			int interval = 0;
			double melodicFitness;
			int pitch1 = firstNote.getPitch();
			int pitch2 = secondNote.getPitch();
			interval = (Math.abs(pitch1 - pitch2)) % 12;
			switch (interval) {
			case 0:
	//			gemiddelde neutrale waarde (standaardafwijking) of niet mee tellen?
//				melodicFitness = 0.75;
				melodicFitness = 0.0;
				break;
			case 1:
				melodicFitness = 0.9;
				break;
			case 2:
				melodicFitness = 1.0;
				break;
			case 3:
				melodicFitness = 0.8;
				break;
			case 4:
				melodicFitness = 0.7;
				break;
			case 5:
				melodicFitness = 0.5;
				break;
			case 6:
				melodicFitness = -1.0;
				break;
			case 7:
				melodicFitness = 0.6;
				break;
			case 8:
				melodicFitness = 0.4;
				break;
			case 9:
				melodicFitness = 0.3;
				break;
			case 10:
				melodicFitness = 0.2;
				break;
			case 11:
				melodicFitness = 0.1;
				break;
			default:
				melodicFitness = -1.0;
				break;
			}
			return melodicFitness;
		}

	/**
	 * Gets the harmonic value of the interval
	 * @param firstNote the first note of the interval
	 * @param secondNote the second note of the interval
	 * @return the value
	 */
	public double getHarmonicValue(Note firstNote, Note secondNote) {
		int interval = 0;
		double harmonicFitness;
		int pitch1 = firstNote.getPitch();
		int pitch2 = secondNote.getPitch();
		interval = (Math.abs(pitch1 - pitch2)) % 12;
		switch (interval) {
			case 0:
				harmonicFitness = 0.5;
				break;
			case 1:
				harmonicFitness = 0.2;
				break;
			case 2:
				harmonicFitness = 0.4;
				break;
			case 3:
				harmonicFitness = 0.6;
				break;
			case 4:
				harmonicFitness = 1.0;
				break;
			case 5:
				harmonicFitness = 0.7;
				break;
			case 6:
				harmonicFitness = -1.0;
				break;
			case 7:
				harmonicFitness = 0.8;
				break;
			case 8:
				harmonicFitness = 0.9;
				break;
			case 9:
				harmonicFitness = 0.5;
				break;
			case 10:
				harmonicFitness = 0.3;
				break;
			case 11:
				harmonicFitness = 0.1;
				break;
			default:
				harmonicFitness = 0.0;
				break;
			}	
		return harmonicFitness;	
	}

	/**
	 * Calculation of the melodic fitness of a phrase/melody
	 * @param phrase the melody
	 * @return the melodic fitness of the phrase
	 */
	public double calculateMelodicFitnessPhrase(Phrase phrase) {
			double total = 0;	
			Note[] notes = phrase.getNoteArray();
			int totalIntervals = 0;
			for (int i = 0; i < notes.length - 1; i++) {		
				double value = getMelodicValue(notes[i],notes[i + 1]);
					total = total + value;
					totalIntervals++;
			}
		return total/totalIntervals;
	}


	/**
	 * Calculates the harmonic fitness of two phrases/melodies 
	 * @param phrase the counter melody
	 * @param givenPhrase the given melody
	 * @param beat the beat at which the calcultion should be done
	 * @return the harmonic fitness
	 */
	public double calculateHarmonicFitnessPhrase(Phrase phrase, Phrase givenPhrase, double beat) {
			double total = 0;
			int totalIntervals = phrase.length();
			for (double i = 0.0; i < phrase.getBeatLength(); i = i + beat) {
				Note note1 = getNoteAtBeat(givenPhrase, i);	
				Note note2 = getNoteAtBeat(phrase,i);
				double value = getHarmonicValue(note1, note2);	
				total = total + value;	
			};
			return (total /totalIntervals) ;
		
		}
	
	/**
	 * Checks if two phrases contain parallelle kwinten, kwarten, octaven
	 * @param phrase the phrase
	 * @param givenPhrase the given phrase
	 * @return true if it contains parallelle intervallen
	 */
	public boolean containsParallelleIntervallen(Phrase phrase,Phrase givenPhrase) {
		int interval = 0;
		int intervalNext = 0;
		Note[] notesPhrase = phrase.getNoteArray();
		Note[] notesGivenPhrase = givenPhrase.getNoteArray();
		for (int i = 0; i < notesGivenPhrase.length - 1; i++) {
			interval = getInterval(notesGivenPhrase[i], notesPhrase[i]);
			intervalNext = getInterval(notesGivenPhrase[i + 1], notesPhrase[i + 1]);
			switch (interval) {
			case 0:
				if (intervalNext == 0) {
					return true;
				}
				break;
			case 5:
				if (intervalNext == 5) {
					return true;
				}
				break;
			case 7:
				if (intervalNext == 7) {
					return true;
				}
				break;
			}
		}		
		return false;
	}
	

	/**
	 * Gets interval between two notes
	 * @param pitch1 the first note
	 * @param pitch2 the second note
	 * @return the interval
	 */
	private int getInterval(Note note1, Note note2){
		return Math.abs(note1.getPitch() - note2.getPitch()) % 12;	
	}

	
	public double[] getIntervallen(Phrase phrase, Phrase givenPhrase, double beat) {
		double[] weights = new double[phrase.length()];
		int j = 0;
		for (double i = 0.0; i < phrase.getBeatLength(); i = i + beat) {
			Note note1 = getNoteAtBeat(givenPhrase,i);	
			Note note2 = getNoteAtBeat(phrase, i);
			int pitch1 = note1.getPitch();
			int pitch2 = note2.getPitch();
			double interval = (Math.abs(pitch1 - pitch2)) % 12;
			weights[j] = interval;
			j++;		
		};
		return weights;	
	}

	/**
	 * Calculation of the standarddeviation
	 * @param weights the harmonic weights
	 * @return the standarddeviation
	 */
	public double getStandardDeviation(double[] weights) {
		return StandardDeviation.sdKnuth(weights);
	}


	
	public double getFitness(Phrase phrase, Phrase givenPhrase, double beat) {
		double melodic = calculateMelodicFitnessPhrase(phrase);
		System.out.println("melodische fitness tegenmelodie = " + melodic);
		double harmonic = calculateHarmonicFitnessPhrase(phrase, givenPhrase, beat);
		System.out.println("harmonische fitness = " + harmonic);
		double standarddeviation = getStandardDeviation(getIntervallen(phrase, givenPhrase, beat));
		boolean parallelInterval = containsParallelleIntervallen(phrase, givenPhrase);
		System.out.println("parallel interval: " + parallelInterval);
		System.out.println("standaardafwijking: " + standarddeviation);
		String fileName = "fitness.fcl";
		FIS fis = FIS.load(fileName,false);
		
		//add jar version 1.2.1
		// Show ruleset
//		FuzzyRuleSet fuzzyRuleSet = fis.getFuzzyRuleSet();
////		fuzzyRuleSet.chart();
//
//		// Set inputs
//		fuzzyRuleSet.setVariable("harmonie", harmonic);
//		fuzzyRuleSet.setVariable("melodie", melodic);
//		fuzzyRuleSet.setVariable("spreiding", standarddeviation);
//		if (parallelInterval) {
//			fuzzyRuleSet.setVariable("parallelinterval", 1);
//		}else{
//			fuzzyRuleSet.setVariable("parallelinterval", 0);
//		}
//
//
//		// Evaluate fuzzy set
//		fuzzyRuleSet.evaluate();
//
//		// Show output variable's chart 
////		fuzzyRuleSet.getVariable("fitness").chartDefuzzifier(true);
//		double cog = fuzzyRuleSet.getVariable("fitness").getLatestDefuzzifiedValue();
//		System.out.println("COG: " + cog);
//
//		// Print ruleSet
//		System.out.println(fuzzyRuleSet);
		double cog = 0;
		return cog;
	}
	
	/**
	 * check if phrase is within octave
	 * @param phrase the phrase
	 * @return true if within octave
	 */
	public boolean withinOctave(Phrase phrase) {
		int high = phrase.getHighestPitch();
		int low = phrase.getLowestPitch();
		if (Math.abs(high - low) > 12) {
			return false;
		}
		return true;
	}
	
	/**
	 * check if phrase contains repeated notes
	 * @param phrase the phrase
	 * @return true if phrase contains repeated notes
	 */
	public boolean containsRepeatNotes(Phrase phrase){
		Note[] notes = phrase.getNoteArray();
		for (int i = 0; i < notes.length - 1; i++) {
			if (notes[i].getPitch() == notes[i+1].getPitch()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Get the note on a particular beat (starting at 0)
	 * @param beat the beat
	 * @return the note at the beat
	 * @author PRombouts
	 */
	public Note getNoteAtBeat(Phrase phrase, double beat) {
		Note[] notes = phrase.getNoteArray();
		int i = 0;
		double sum = 0.0;
		while (sum <= beat) {
			sum = sum + notes[i].getRhythmValue();
			i++;
		}
		return notes[i - 1];		
	}
	
	public Interval getEnumInterval(Note firstNote, Note secondNote) {
		int difference = 0;
		Interval interval = null;
		int pitch1 = firstNote.getPitch();
		int pitch2 = secondNote.getPitch();
		difference = (Math.abs(pitch1 - pitch2)) % 12;
		switch (difference) {
		case 0:
			interval = Interval.UNISONO;
			break;
		case 1:
			interval = Interval.KLEINE_SECONDE;
			break;
		case 2:
			interval = Interval.GROTE_SECONDE;
			break;
		case 3:
			interval = Interval.KLEINE_TERTS;
			break;
		case 4:
			interval = Interval.GROTE_TERTS;
			break;
		case 5:
			interval = Interval.KWART;
			break;
		case 6:
			interval = Interval.TRITONE;
			break;
		case 7:
			interval = Interval.KWINT;
			break;
		case 8:
			interval = Interval.KLEINE_SIXT;
			break;
		case 9:
			interval = Interval.GROTE_SIXT;
			break;
		case 10:
			interval = Interval.KLEIN_SEPTIEM;
			break;
		case 11:
			interval = Interval.GROOT_SEPTIEM;
			break;
		case 12:
			interval = Interval.OCTAAF;
			break;
		}
		return interval;
	}

}

