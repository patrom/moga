package be.util;

import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;

public class MidiDevicesUtil {

	public static void main(String[] args) {
		Info[] midiDevices = MidiSystem.getMidiDeviceInfo();
		MidiDevice device = null;
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();

		try {
			Sequencer sequencer = MidiSystem.getSequencer();
			// sequencer.start();

		} catch (MidiUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 0; i < infos.length; i++) {
			try {
				System.out.println(infos[i]);
				if (infos[i].getName().equals("Kontakt 5 Virtual Input")) {
					device = MidiSystem.getMidiDevice(infos[i]);
					device.open();
					Receiver receiver = device.getReceiver();
//					Transmitter transmitter = device.getTransmitter();
//					transmitter.setReceiver(receiver);
					ShortMessage myMsg = new ShortMessage();
					// Start playing the note Middle C (60),
					// moderately loud (velocity = 93).
					myMsg.setMessage(ShortMessage.NOTE_ON, 1, 60, 93);
					long timeStamp = -1;
					
					receiver.send(myMsg, timeStamp);
					

					// Transmitter transmitter = device.getTransmitter();
					System.out.println();
					// device.open();
					// List<Receiver> receivers = device.getReceivers();
					// for (Receiver receiver : receivers) {
					// System.out.println("receiver");
					// // System.out.println(receiver.send(arg0, arg1));
					// }
					 device.close();

					if (device instanceof Receiver) {
						System.out.println("synth");
					}
					if (device instanceof Transmitter) {
						System.out.println("Sequencer");
					}
				}

			} catch (MidiUnavailableException e) {
				// Handle or throw exception...
			} catch (InvalidMidiDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
