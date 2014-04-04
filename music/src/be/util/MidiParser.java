package be.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import jm.music.data.Score;
import jm.util.View;

import be.data.Motive;
import be.data.NotePos;
import be.instrument.Instrument;
import be.instrument.KontaktLibAltViolin;
import be.instrument.KontaktLibCello;
import be.instrument.KontaktLibViolin;
import be.instrument.MidiDevice;

public class MidiParser {

	private static Logger LOGGER = Logger.getLogger(MidiParser.class.getName());
	public static final int TRACK_TIMESIGNATURE = 0x58;

	public static final int NOTE_ON = 0x90;
	public static final int NOTE_OFF = 0x80;
	public static final String[] NOTE_NAMES = { "C", "C#", "D", "D#", "E", "F",
			"F#", "G", "G#", "A", "A#", "B" };

	public static void main(String[] args) throws Exception {
		List<Instrument> ranges = new ArrayList<Instrument>();
//		ranges.add(Utilities.getInstrument(0, 48, 60, 0));
		ranges.add(new KontaktLibViolin(0, 1));
		ranges.add(new KontaktLibViolin(1, 2));
		ranges.add(new KontaktLibAltViolin(2, 2));
		ranges.add(new KontaktLibCello(3, 3));

		List<Motive> motives = readMidi("/Users/parm/comp/moga/music/test3.mid");
		
//		TwelveToneUtil.reorder(motives, ranges);
		Score score = ScoreUtilities.createScoreMotives(motives);
		View.notate(score);
//		TwelveToneUtil.multiply(7, motives, ranges);
		TwelveToneUtil.retrogradePitches(motives);
		score = ScoreUtilities.createScoreMotives(motives);
		View.notate(score);
//		Sequence seq = MidiDevicesUtil.createSequence(motives, ranges);
//		float tempo = ScoreUtilities.randomTempoFloat();
//		System.out.println("Tempo: " + tempo);
//		MidiDevicesUtil.playOnDevice(seq, tempo, MidiDevice.KONTACT);
		for (Motive motive : motives) {
			System.out.print(motive.getVoice() + ":");
			List<NotePos> notes = motive.getNotePositions();
			for (NotePos notePos : notes) {
				System.out.print(notePos.getPitch() + ",");
			}
			System.out.println();
		}
		// TwelveToneUtil.rotate()
	
		// TwelveToneUtil.transpose(-7, motives, ranges);
		// TwelveToneUtil.invert(motives, ranges);
		// TwelveToneUtil.multiply(5, motives, ranges);
		// List<MusicalStructure> sentences = new
		// ArrayList<MusicalStructure>(motives);
		// Score score = ScoreUtilities.createScore2(sentences, null);
		// View.notate(score);

		// for (Motive motive : motives) {
		// System.out.print(motive.getVoice() + ":");
		// List<NotePos> notes = motive.getNotePositions();
		// for (NotePos notePos : notes) {
		// System.out.print(notePos.getPitch() + ",");
		// }
		// System.out.println();
		// }
	}

	public static List<Motive> readMidi(String path)
			throws InvalidMidiDataException, IOException {
		Sequence sequence = MidiSystem.getSequence(new File(path));
		LOGGER.finer("Ticks: " + sequence.getResolution());
		LOGGER.finer("PPQ: " + sequence.PPQ);
		LOGGER.finer("DivisionType: " + sequence.getDivisionType());
		int trackNumber = 0;
		int voice = 0;
		int size = sequence.getTracks().length;
		voice = size - 1;
		int resolution = 12;
		Map<Integer, Motive> map = new TreeMap<Integer, Motive>();
		// readTimeSignature(path);

		for (int j = 0; j < size; j++) {
			Track track = sequence.getTracks()[j];
			List<NotePos> notes = new ArrayList<NotePos>();
			trackNumber++;
			LOGGER.finer("Track " + trackNumber + ": size = " + track.size());
			for (int i = 0; i < track.size(); i++) {
				MidiEvent event = track.get(i);
				MidiMessage message = event.getMessage();

				if (message instanceof ShortMessage) {

					LOGGER.finer("Voice:" + voice);
					long ticks = Math
							.round(((double) event.getTick() / (double) sequence
									.getResolution()) * resolution);
					ShortMessage sm = (ShortMessage) message;

					// Er zijn twee manieren om een note-off commando te
					// versturen.
					// // Er bestaat een echt note-off commando, maar de meeste
					// apparaten versturen in plaats van een note-off commando
					// // een note-on commando met velocitywaarde 0. Een noot
					// die aangeschakeld wordt met velocitywaarde 0 is voor midi
					// hetzelfde als die noot uitschakelen.
					if (sm.getCommand() == ShortMessage.NOTE_ON
							&& sm.getData2() != 0) {
						LOGGER.finer("on: " + ticks + " ");
						LOGGER.finer("@" + event.getTick() + " ");
						LOGGER.finer("Pitch: " + sm.getData1() + " ");
						NotePos jNote = new NotePos();
						int key = sm.getData1();
						jNote.setPitch(key);
						jNote.setVoice(voice);
						jNote.setPosition((int) ticks);

						int velocity = sm.getData2();
						jNote.setDynamic(velocity);

						if (jNote != null) {
							notes.add(jNote);
						}
					}
					if (sm.getCommand() == ShortMessage.NOTE_OFF
							|| (sm.getCommand() == ShortMessage.NOTE_ON && sm
									.getData2() == 0)) {
						LOGGER.finer("off:" + ticks);
						LOGGER.finer(" @" + event.getTick() + " ");
						LOGGER.finer("Pitch: " + sm.getData1() + " ");
						int key = sm.getData1();
						int l = notes.size();
						for (int k = l - 1; k > -1; k--) {// find note on
															// belonging to note
															// off
							NotePos noteOn = notes.get(k);
							if (noteOn.getPitch() == key) {
								noteOn.setLength((int) ticks
										- noteOn.getPosition());
								break;
							}
						}
					}
				}
			}

			if (!notes.isEmpty()) {
				NotePos firstNote = notes.get(0);
				NotePos lastNote = notes.get(notes.size() - 1);
				int length = lastNote.getPosition() + lastNote.getLength()
						- firstNote.getPosition();
				Motive motive = new Motive(notes, length);
				motive.setVoice(voice);
				map.put(voice, motive);
			}
			voice--;
		}
		List<Motive> motives = new ArrayList<Motive>(map.values());
		return motives;
	}

//	public Sequence writeToSequence(List<Motive> motives)
//			throws InvalidMidiDataException {
//		Sequence sequence = new Sequence(0.0F, 12);
//		int channel = 0;
//		for (Motive motive : motives) {
//			Track track = sequence.createTrack();
//			System.out.print(motive.getVoice() + ":");
//			List<NotePos> notes = motive.getNotePositions();
//			for (NotePos notePos : notes) {
//				System.out.print(notePos.getPitch() + ",");
//				ShortMessage noteOn = new ShortMessage();
//				noteOn.setMessage(ShortMessage.NOTE_ON, channel,
//						notePos.getPitch(), notePos.getDynamic());
//				MidiEvent event = new MidiEvent(noteOn, notePos.getPosition());
//				track.add(event);
//				// note off!!
//			}
//			System.out.println();
//		}
//		return sequence;
//	}

}
