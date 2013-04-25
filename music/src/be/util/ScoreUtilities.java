package be.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.ArrayUtils;


import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Rest;
import jm.music.data.Score;
import be.data.MelodicSentence;
import be.data.MusicalStructure;
import be.data.NotePos;
import be.data.Scale;

public class ScoreUtilities implements JMC{

	private static final double ATOMIC_VALUE = 12;
	private static Random random = new Random();

	public static List<Note[]> convertMidiFile(Score score){
		List<Note[]> chords = new ArrayList<Note[]>();
		Part[] parts = score.getPartArray();
		for (Part part : parts) {
			Phrase[] phrases = part.getPhraseArray();
			for (Phrase phrase : phrases) {
				Note[] notes = phrase.getNoteArray();
				chords.add(notes);
				for (Note note : notes) {
					BigDecimal dec = new BigDecimal(note.getRhythmValue());
					double t = dec.round(new MathContext(2)).doubleValue()* 2;
					double tt = Math.ceil(dec.round(new MathContext(2)).doubleValue()* 2);
					double rhythmValue = dec.round(new MathContext(2)).doubleValue();
					note.setRhythmValue(rhythmValue);//Math.ceil(x * 2) / 2
					note.setDuration(rhythmValue);
					//remove if you want dynamics of midi file
					note.setDynamic(note.DEFAULT_DYNAMIC);//reset dynamics!!
				}
			}
		}
		return chords;
	}
	//retain starttime phrases!
	public static List<Phrase> convertMidiFile2(Score score){
		List<Phrase> chords = new ArrayList<Phrase>();
		Part[] parts = score.getPartArray();
		for (Part part : parts) {
			Phrase[] phrases = part.getPhraseArray();
			for (Phrase phrase : phrases) {
				Note[] notes = phrase.getNoteArray();	
				for (Note note : notes) {
					BigDecimal dec = new BigDecimal(note.getRhythmValue());
//					double t = dec.round(new MathContext(2)).doubleValue()* 2;
//					double tt = Math.ceil(dec.round(new MathContext(2)).doubleValue()* 2);
					double rhythmValue = dec.round(new MathContext(2)).doubleValue();
					note.setRhythmValue(rhythmValue);//Math.ceil(x * 2) / 2
					note.setDuration(rhythmValue);
					//remove if you want dynamics of midi file
					note.setDynamic(note.DEFAULT_DYNAMIC);//reset dynamics!!
				}
				chords.add(phrase);
			}
		}
		return chords;
	}
	
	public static List<Note[]> convertGeneratedScore(Score score){
		List<Note[]> chords = new ArrayList<Note[]>();
		Part[] parts = score.getPartArray();
		for (Part part : parts) {
			Phrase[] phrases = part.getPhraseArray();
			for (Phrase phrase : phrases) {
				Note[] notes = phrase.getNoteArray();
				for (Note note : notes) {
					note.setDynamic(note.DEFAULT_DYNAMIC);//reset dynamics!!
				}
				chords.add(notes);
			}
		}
		return chords;
	}
	
	public static Score createScore(List<MusicalStructure> sentences, int[] ensemble){
		Score score = new Score();
		int instrument = PIANO;
//		int instrument = STRING_ENSEMBLE_1;
//		int instrument = VOICE;
		for (MusicalStructure sentence : sentences) {
			List<NotePos> notePosistions = sentence.getNotePositions();
			Phrase phrase = new Phrase();
			double startTime = new Double(notePosistions.get(0).getPosition())/ATOMIC_VALUE;
			phrase.setStartTime(startTime);
			for (NotePos notePos : notePosistions) {
				Note note = new Note(notePos.getPitch(), notePos.getRhythmValue());
				phrase.add(note);
			}
			Part part = new Part(phrase);
			part.setInstrument(instrument);
			score.add(part);
		}
		Part[] parts = score.getPartArray();
		if (ensemble != null) {
			if (parts.length != ensemble.length) {
				throw new RuntimeException("ensemble of different length");
			}
			for (int i = 0; i < parts.length; i++) {
				parts[i].setInstrument(ensemble[i]);
			}
		}
		
		double r = random.nextDouble();
		if (r < 0.5) {
			r = (r * 100) + 100;
		} else {
			r = r * 100;
		}
		//tempo between 50 - 150
		score.setTempo(r);
		return score;
	}
	
	public static Score createScore2(List<MusicalStructure> sentences, int[] ensemble){
		Score score = new Score();
		int instrument = PIANO;
//		int instrument = STRING_ENSEMBLE_1;
//		int instrument = VOICE;
		Part[] scoreParts = new Part[sentences.size()];
		int voice = 0;
		for (MusicalStructure sentence : sentences) {
			List<NotePos> notePosistions = sentence.getNotePositions();
			Phrase phrase = new Phrase();
			int lastVoice = 0;
			if (!notePosistions.isEmpty()) {
				double startTime = (double)notePosistions.get(0).getPosition()/ATOMIC_VALUE;
				phrase.setStartTime(startTime);
				int length = notePosistions.size();
				Note note = null;
				for (int i = 0; i < length; i++) {
					NotePos notePos = notePosistions.get(i);
					lastVoice = notePos.getVoice();
					note = new Note(notePos.getPitch(),(double)notePos.getLength()/ATOMIC_VALUE);
					phrase.add(note);
					if ((i + 1) < length) {	
						NotePos nextNotePos = notePosistions.get(i + 1);
						int gap = (notePos.getPosition()+ notePos.getLength()) - nextNotePos.getPosition();
						if (gap < 0) {
							note = new Rest((double)-gap/ATOMIC_VALUE);
							phrase.add(note);
						}
					}	
				}
			}
			
			Part part = new Part(phrase);
			part.setInstrument(instrument);
			scoreParts[voice] = part;
			if (voice != lastVoice) {
				System.out.println("something wrong with voices");
			}
			voice++;	
		}

		for (int i = scoreParts.length - 1; i > -1; i--) {
			score.add(scoreParts[i]);
		}
		
		Part[] parts = score.getPartArray();
		if (ensemble != null) {
			if (parts.length != ensemble.length) {
				throw new IllegalArgumentException("ensemble of different length");
			}
			for (int i = 0; i < parts.length; i++) {
				parts[i].setInstrument(ensemble[i]);
			}
		}
		
		double r = random.nextDouble();
		if (r < 0.5) {
			r = (r * 100) + 100;
		} else {
			r = r * 100;
		}
		//tempo between 50 - 150
		score.setTempo(r);
		return score;
	}
}
