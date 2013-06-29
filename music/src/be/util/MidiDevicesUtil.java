package be.util;

import java.util.List;
import java.util.logging.Logger;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;

import jm.midi.event.EndTrack;
import jm.midi.event.KeySig;
import jm.midi.event.TempoEvent;
import jm.midi.event.TimeSig;

import be.data.InstrumentRange;
import be.data.Motive;
import be.data.MusicalStructure;
import be.data.NotePos;

public class MidiDevicesUtil {

	private static final int RESOLUTION = 12;
	private static Logger LOGGER = Logger.getLogger(MidiDevicesUtil.class.getName());

	public static void playOnKontakt(Sequence sequence, float tempo) {
		LOGGER.info("tempo:" + tempo);
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		for (int i = 0; i < infos.length; i++) {
			try {
				System.out.println(infos[i]);
//				if (infos[i].getName().equals("Kontakt 5 Virtual Input")) {
				if (infos[i].getName().equals("Kontakt 5 Virtual Input")) {
					final MidiDevice device = MidiSystem
							.getMidiDevice(infos[i]);
					device.open();

					final Sequencer sequencer = MidiSystem.getSequencer(false);

					/*
					 * There is a bug in the Sun jdk1.3/1.4. It prevents correct
					 * termination of the VM. So we have to exit ourselves. To
					 * accomplish this, we register a Listener to the Sequencer.
					 * It is called when there are "meta" events. Meta event 47
					 * is end of track.
					 * 
					 * Thanks to Espen Riskedal for finding this trick.
					 */
					sequencer.addMetaEventListener(new MetaEventListener() {
						public void meta(MetaMessage event) {
							if (event.getType() == 47) {
								sequencer.close();
								if (device != null) {
									device.close();
								}
								System.exit(0);
							}
						}
					});
					sequencer.open();
					
					sequencer.setSequence(sequence);

					Receiver receiver = device.getReceiver();
					Transmitter seqTransmitter = sequencer.getTransmitter();
					seqTransmitter.setReceiver(receiver);
					sequencer.setTempoInBPM(tempo);
					sequencer.start();
					break;
				}
			} catch (MidiUnavailableException e) {
				e.printStackTrace();
			} catch (InvalidMidiDataException e) {
				e.printStackTrace();
			}
		}
	}

	public static Sequence createSequence(List<Motive> motives, int channel)
			throws InvalidMidiDataException {
		Sequence sequence = new Sequence(Sequence.PPQ, RESOLUTION, motives.size());
		for (Motive motive : motives) {	
			List<NotePos> notes = motive.getNotePositions();
			createTrack(sequence, notes, channel);
		}
		return sequence;
	}

	private static void createTrack(Sequence sequence, List<NotePos> notes, int channel)
			throws InvalidMidiDataException {
		Track track = sequence.createTrack();
		for (NotePos notePos : notes) {
			MidiEvent eventOn = createMidiEvent(ShortMessage.NOTE_ON, notePos, notePos.getPosition(), channel);
			track.add(eventOn);
			MidiEvent eventOff = createMidiEvent(ShortMessage.NOTE_OFF, notePos, notePos.getPosition() + notePos.getLength(), channel);
			track.add(eventOff);
		}
	}

	public static Sequence createSequenceFromStructures(List<MusicalStructure> structures, List<InstrumentRange> ranges)
			throws InvalidMidiDataException {
		Sequence sequence = new Sequence(Sequence.PPQ, RESOLUTION, structures.size());
		int i = 0;
		for (MusicalStructure motive : structures) {
			List<NotePos> notes = motive.getNotePositions();
			createTrack(sequence, notes, ranges.get(i).getChannel());
			i++;
		}
		return sequence;
	}

	private static MidiEvent createMidiEvent(int cmd, NotePos notePos, int position, int channel)
			throws InvalidMidiDataException {
		ShortMessage note = new ShortMessage();
		if (notePos.isRest()) {
			note.setMessage(cmd, channel, 0, 0);
		} else {
			note.setMessage(cmd, channel,
					notePos.getPitch(), notePos.getDynamic());
		}
		
		MidiEvent event = new MidiEvent(note, position);
		return event;
	}

}
