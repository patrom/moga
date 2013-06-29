package be.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Rest;
import jm.music.data.Score;
import be.data.Motive;
import be.data.MusicalStructure;
import be.data.NotePos;
import be.moga.MusicProperties;

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
		
		double r = randomTempo();
		score.setTempo(r);
		return score;
	}
	
	
	/**
	 * Generates random tempo between 50 - 150 bpm
	 * @return
	 */
	public static double randomTempo() {
		double r = random.nextDouble();
		if (r < 0.5) {
			r = (r * 100) + 100;
		} else {
			r = r * 100;
		}
		//tempo between 50 - 150
		return r;
	}
	
	/**
	 * Generates random tempo between 50 - 150 bpm
	 * @return
	 */
	public static float randomTempoFloat() {
		float r = random.nextFloat();
		if (r < 0.5) {
			r = (r * 100) + 100;
		} else {
			r = r * 100;
		}
		//tempo between 50 - 150
		return r;
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
		
		double r = randomTempo();
		//tempo between 50 - 150
		score.setTempo(r);
		return score;
	}
	
	public static String createVexTab(List<MusicalStructure> sentences, MusicProperties properties){
		int bar = (int) (properties.getNumerator() * ATOMIC_VALUE);
		StringBuilder builder = new StringBuilder();
		for (MusicalStructure musicalStructure : sentences) {	
			List<NotePos> notePosistions = musicalStructure.getNotePositions();
			if (!notePosistions.isEmpty()) {
				builder.append("tabstave notation=true tablature=false");
				NotePos firstNote = notePosistions.get(0);
				if (firstNote.getPitch() < 60) {
					builder.append(" clef=bass");
				} else {
					builder.append(" clef=treble");
				}
				builder.append(" key=" + properties.getKey());
				builder.append(" time=" + properties.getNumerator() + "/4");
				builder.append("\n");
				builder.append("notes "); 
				
				int noteEndPosition = (firstNote.getPosition() + firstNote.getLength()) % bar;
				int startPosition = firstNote.getPosition() % bar;
				int beforeBarDiff = bar - startPosition;
				if (firstNote.getPosition() > 0) {
					builder.append(getVexTabRest(firstNote.getPosition()));
				}
				if (firstNote.getPosition() == bar) {
					builder.append(" | ");
					builder.append(getVexTabRhythm(firstNote.getLength()));
					builder.append(Frequency.makeNoteSymbol(firstNote.getPitch()));
				}else if (beforeBarDiff < firstNote.getLength()) {
					insertBarSymbol(builder, firstNote, noteEndPosition, beforeBarDiff);
				}
				int length = notePosistions.size();
				for (int i = 0; i < length; i++) {
					NotePos notePos = notePosistions.get(i);
					noteEndPosition = (notePos.getPosition() + notePos.getLength()) % bar;
					startPosition = notePos.getPosition() % bar;
					beforeBarDiff = bar - startPosition;
					if (notePos.getPosition() == bar) {
						builder.append(" | ");
						builder.append(getVexTabRhythm(notePos.getLength()));
						builder.append(Frequency.makeNoteSymbol(notePos.getPitch()));
					} else if ((bar - startPosition) < notePos.getLength()) {
						insertBarSymbol(builder, notePos, noteEndPosition, beforeBarDiff);
					}else{
						builder.append(getVexTabRhythm(notePos.getLength()));
						builder.append(Frequency.makeNoteSymbol(notePos.getPitch()));
					}
					
					if ((i + 1) < length) {	
						NotePos nextNotePos = notePosistions.get(i + 1);
						int gap = (notePos.getPosition() + notePos.getLength()) - nextNotePos.getPosition();
						if (gap < 0) {
							int restEndPosition = (notePos.getPosition() + notePos.getLength()) + notePos.getLength();
							if (((notePos.getPosition() + notePos.getLength())/(double)bar) < 1 
									&& (restEndPosition/(double)bar) > 1) {
								int firstSymbol = bar - (notePos.getPosition() + notePos.getLength());
								builder.append(getVexTabRest(firstSymbol));
								builder.append(" | ");
								int secondSymbol = restEndPosition - bar;
								builder.append(getVexTabRest(secondSymbol));
							}else{
								builder.append(getVexTabRest(-gap));
							}
						}	
					}	
				}
			}
			builder.append("\n");
		}
		return builder.toString();
		
	}
	
	private static void insertBarSymbol(StringBuilder builder, NotePos note, int afterBarDiff, int beforeBarDiff) {
		builder.append(getVexTabRhythm(beforeBarDiff));
		String noteSymbol = Frequency.makeNoteSymbol(note.getPitch());
		builder.append(noteSymbol);
		builder.append(" | ");
		builder.append(getVexTabRhythm(afterBarDiff));
		builder.append("T");
		builder.append(noteSymbol);
	}
		
//		notes :w :h :q :8 :16 :32 :64
//		notes :hd :qd :8d :16d :32d :64d (Adding a d for dotted notes)
	private static String getVexTabRhythm(int i) {
		switch (i) {
		case 3:
			return ":16";
		case 6:
			return ":8";
		case 9:
			return "8d";
		case 12:
			return ":q";
		case 18:
			return ":qd";
		case 24:
			return ":h";
		case 32:
			return ":hd";
		case 48:
			return ":w";
		}
		throw new IllegalArgumentException("Rhythm value unknown:" + i);
	}
	
	private static String getVexTabRest(int i) {
		switch (i) {
		case 3:
			return ":16##";
		case 6:
			return ":8##";
		case 9:
			return ":8d##";
		case 12:
			return ":q##";
		case 15:
			return ":q##:16##";
		case 18:
			return ":q##:8##";
		case 21:
			return ":q##:8d##";
		case 24:
			return ":h##";
		case 27:
			return ":h##:16##";
		case 30:
			return ":h##:8##";
		case 33:
			return ":h##:8d##";
		case 36:
			return ":h##:q##";
		case 39:
			return ":h##:q##:16##";
		case 41:
			return ":h##:q##:8##";
		case 44:
			return ":h##:q##:8D##";
		case 48:
			return ":w##";
		}
		throw new IllegalArgumentException("Rest value unknown:" + i);
	}
	
	public static void main(String[] args) {
		List<Motive> motives = TestPopulation.melodyVextab(18);
		List<MusicalStructure> sentences = Populator.getInstance().extractSentence(motives);
		MusicProperties props = new MusicProperties();
		String vexTab = createVexTab(sentences, props);
		System.out.println(vexTab);
	}
}
