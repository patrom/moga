package be.core;


import java.util.Random;

import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Phrase;

/**
 * Generates phrase (melodies)
 * @author PRombouts
 *
 */
public class PhraseGenerator implements JMC {
	private static PhraseGenerator generator;
	
	public static PhraseGenerator getInstance(){
		if (generator == null) {
			return new PhraseGenerator();
		}
		return generator;	
	}
	
	private PhraseGenerator(){	
	}

	/**
	 * Generates a low phrase
	 * @param length the length of the phrase
	 * @param rhythmValue the rhythm value of the notes
	 * @return the generated phrase
	 */
	public Phrase generateLowPhrase(int length, double rhythmValue) {
		Random random = new Random();
		Phrase phrase = new Phrase();
		while (phrase.length() < length) {
			Note note = null;
			// generate notes from B2 till C4
			int i = random.nextInt(18);
			switch (43 + i) {
			case C3:
				note = new Note(C3, rhythmValue);
				break;
			case D3:
				note = new Note(D3, rhythmValue);
				break;
			case E3:
				note = new Note(E3, rhythmValue);
				break;
			case F3:
				note = new Note(F3, rhythmValue);
				break;
			case G3:
				note = new Note(G3, rhythmValue);
				break;
			case A3:
				note = new Note(A3, rhythmValue);
				break;
			case B3:
				note = new Note(B3, rhythmValue);
				break;
			case C4:
				note = new Note(C4, rhythmValue);
				break;		
			default:
				break;
			}
			if (note != null) {
				phrase.add(note);	
			}
		}

		return phrase;
	}
	
	/**
	 * Generates a high phrase
	 * @param length the length of the phrase
	 * @param rhythmValue the rhythm value of the notes
	 * @return the generated phrase
	 */
	public Phrase generateHighPhrase(int length, double rhythmValue) {
		Random random = new Random();
		Phrase phrase = new Phrase();
		while (phrase.length() < length) {
			Note note = null;
			// generate notes from B2 till C4
			int i = random.nextInt(13);
			switch (60 + i) {
			case C4:
				note = new Note(C4, rhythmValue);
				break;
			case D4:
				note = new Note(D4, rhythmValue);
				break;
			case E4:
				note = new Note(E4, rhythmValue);
				break;
			case F4:
				note = new Note(F4, rhythmValue);
				break;
			case G4:
				note = new Note(G4, rhythmValue);
				break;
			case A4:
				note = new Note(A4, rhythmValue);
				break;
			case B4:
				note = new Note(B4, rhythmValue);
				break;
			case C5:
				note = new Note(C5, rhythmValue);
				break;
			default:
				break;
			}
			if (note != null) {
				phrase.add(note);	
			}
		}

		return phrase;
	}
	
	
	/**
	 * Generates a random low note
	 * @param rhythmValue the rhythm value of the note
	 * @return the generated note
	 */
	public Note getLowNote(double rhythmValue) {
		Random random = new Random();
		Note note = null;
		// generate notes from B2 till C4
		int i = random.nextInt(18);
		switch (43 + i) {
		case C3:
			note = new Note(C3, rhythmValue);
			break;
		case D3:
			note = new Note(D3, rhythmValue);
			break;
		case E3:
			note = new Note(E3, rhythmValue);
			break;
		case F3:
			note = new Note(F3, rhythmValue);
			break;
		case G3:
			note = new Note(G3, rhythmValue);
			break;
		case A3:
			note = new Note(A3, rhythmValue);
			break;
		case B3:
			note = new Note(B3, rhythmValue);
			break;
		case C4:
			note = new Note(C4, rhythmValue);
			break;
		default:
			break;
		}
		if (note != null) {
			return note;
		} else {
			return getLowNote(rhythmValue);
		}
	}
	
	public Phrase generateChromaticPhrase(int length, double rhythmValue) {
		Random random = new Random();
		Phrase phrase = new Phrase();
		while (phrase.length() < length) {
			Note note = null;
			// generate notes from B2 till C4
			int i = random.nextInt(13);
			switch (60 + i) {
			case C4:
				note = new Note(C4, rhythmValue);
				break;
			case CS4:
				note = new Note(CS4, rhythmValue);
				break;
			case D4:
				note = new Note(D4, rhythmValue);
				break;
			case DS4:
				note = new Note(DS4, rhythmValue);
				break;
			case E4:
				note = new Note(E4, rhythmValue);
				break;
			case F4:
				note = new Note(F4, rhythmValue);
				break;
			case FS4:
				note = new Note(FS4, rhythmValue);
				break;
			case G4:
				note = new Note(G4, rhythmValue);
				break;
			case GS4:
				note = new Note(GS4, rhythmValue);
				break;
			case A4:
				note = new Note(A4, rhythmValue);
				break;
			case BF4:
				note = new Note(BF4, rhythmValue);
				break;
			case B4:
				note = new Note(B4, rhythmValue);
				break;
			case C5:
				note = new Note(C5, rhythmValue);
				break;
			default:
				break;
			}
			if (note != null) {
				phrase.add(note);	
			}
		}

		return phrase;
	}

}
