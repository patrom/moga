package be.util;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
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

import jm.midi.event.EndTrack;
import jm.midi.event.Event;
import jm.music.data.Score;
import jm.util.View;
import be.core.TwelveTone;
import be.data.InstrumentRange;
import be.data.MelodicSentence;
import be.data.Motive;
import be.data.MusicalStructure;
import be.data.NotePos;


public class MidiParser {

	private static Logger LOGGER = Logger.getLogger(MidiParser.class.getName());
	public static final int TRACK_TIMESIGNATURE = 0x58;
	
	 public static final int NOTE_ON = 0x90;
	    public static final int NOTE_OFF = 0x80;
	    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
	    
	    private static InstrumentRange getInstrument(int voice, int low, int high) {
			InstrumentRange range = new InstrumentRange();
			range.setVoice(voice);
			range.setLowest(low);
			range.setHighest(high);
			return range;
		}

	    public static void main(String[] args) throws Exception {
	    	List<InstrumentRange> ranges = new ArrayList<InstrumentRange>();
	    	ranges.add(getInstrument(0, 48, 60));
	 		ranges.add(getInstrument(1, 54, 70));
	 		ranges.add(getInstrument(2, 60, 74));
	 		ranges.add(getInstrument(3, 65, 80));

	        List<Motive> motives = readMidi("/Users/parm/comp/moga/music/test2.mid");
//	 		List<Motive> motives = new ArrayList<Motive>();
//	 		Motive mot = new Motive();
//	 		NotePos note = new NotePos();
//	 		note.setChannel(0);
//	 		note.setPitch(60);
//	 		note.setPosition(12);
//	 		note.setLength(24);
//	 		note.setDynamic(100);
//	 		mot.addNote(note);
//	 		motives.add(mot);
	        Sequence seq = MidiDevicesUtil.createSequence(motives, 0);
	        MidiDevicesUtil.playOnKontakt(seq, 120f);
	        for (Motive motive : motives) {
	        	System.out.print(motive.getVoice() + ":");
				List<NotePos> notes = motive.getNotePositions();
				for (NotePos notePos : notes) {
					System.out.print(notePos.getPitch() + ",");
				}
				System.out.println();
			} 
//	        TwelveToneUtil.rotate()
//	         TwelveToneUtil.reorder(motives, ranges);
//	        TwelveToneUtil.transpose(-7, motives, ranges);
//	         TwelveToneUtil.invert(motives, ranges);
//	        TwelveToneUtil.multiply(5, motives, ranges);
//	        List<MusicalStructure> sentences = new ArrayList<MusicalStructure>(motives);
//	        Score score = ScoreUtilities.createScore2(sentences, null);
//	        View.notate(score);
	        
//	        for (Motive motive : motives) {
//	        	System.out.print(motive.getVoice() + ":");
//				List<NotePos> notes = motive.getNotePositions();
//				for (NotePos notePos : notes) {
//					System.out.print(notePos.getPitch() + ",");
//				}
//				System.out.println();
//			} 
	    }

		public static List<Motive> readMidi(String path) throws InvalidMidiDataException, IOException {	
			Sequence sequence = MidiSystem.getSequence(new File(path));
	        LOGGER.finer("Ticks: " + sequence.getResolution());
	        LOGGER.finer("PPQ: " + sequence.PPQ);
	        LOGGER.finer("DivisionType: " + sequence.getDivisionType());
	        int trackNumber = 0;
	        int voice = 0;
	        int size = sequence.getTracks().length;
	        voice = size -  1;
	        int resolution = 12;
	        Map<Integer, Motive> map = new TreeMap<Integer, Motive>();
//    		readTimeSignature(path);
             		
	        for (int j = 0; j < size; j++) {
	        	Track track = sequence.getTracks()[j];
	        	List<NotePos> notes = new ArrayList<NotePos>();
	            trackNumber++;
	            LOGGER.finer("Track " + trackNumber + ": size = " + track.size());
	            for (int i=0; i < track.size(); i++) { 
	            	MidiEvent event = track.get(i);
	                MidiMessage message = event.getMessage();
	                
	                if (message instanceof ShortMessage) {
	                	
	                	LOGGER.finer("Voice:" + voice);
		                long ticks = Math.round(((double)event.getTick()/(double)sequence.getResolution()) * resolution);
	                    ShortMessage sm = (ShortMessage) message;
	                    
//	                    Er zijn twee manieren om een note-off commando te versturen. 
////                    Er bestaat een echt note-off commando, maar de meeste apparaten versturen in plaats van een note-off commando 
////                    een note-on commando met velocitywaarde 0. Een noot die aangeschakeld wordt met velocitywaarde 0 is voor midi hetzelfde als die noot uitschakelen.
	                    if (sm.getCommand() == ShortMessage.NOTE_ON && sm.getData2() != 0) {
	                    	LOGGER.finer("on: " + ticks + " ");
	    	                LOGGER.finer("@" + event.getTick() + " ");
	                    	LOGGER.finer("Pitch: " + sm.getData1() + " ");
	                    	NotePos jNote = new NotePos();
	                        int key = sm.getData1();
	                        jNote.setPitch(key);
	                        jNote.setVoice(voice);
	                        jNote.setPosition((int)ticks);
	                        
	                        int velocity = sm.getData2();
	                        jNote.setDynamic(velocity);

	                        if (jNote != null) {
			                	notes.add(jNote);
							}   
						}
	                    if (sm.getCommand() == ShortMessage.NOTE_OFF || (sm.getCommand() == ShortMessage.NOTE_ON && sm.getData2() == 0 )) {
	                    	LOGGER.finer("off:" + ticks);
			                LOGGER.finer(" @" + event.getTick() + " ");
			                LOGGER.finer("Pitch: " + sm.getData1() + " ");
	                        int key = sm.getData1();
	                        int l = notes.size();
	                        for (int k = l - 1; k > -1; k--) {//find note on belonging to note off
	                        	NotePos noteOn = notes.get(k);
								if(noteOn.getPitch() == key){
									noteOn.setLength((int)ticks - noteOn.getPosition());
									break;
								}
							}
	                    }
	                } 
	            }
//	            LOGGER.finer();
//	            for (NotePos notePos : notes) {
//	            	LOGGER.finer(notePos);
//				}
	            if (!notes.isEmpty()) {
					NotePos firstNote = notes.get(0);
					NotePos lastNote = notes.get(notes.size() - 1);
					int length = lastNote.getPosition() + lastNote.getLength() - firstNote.getPosition();
					Motive motive = new Motive(notes, length);
					motive.setVoice(voice);
					map.put(voice, motive);
				}
	            voice--;
	        }
	        List<Motive> motives = new ArrayList<Motive>(map.values());        
			return motives;
		}
		
		public Sequence writeToSequence(List<Motive> motives) throws InvalidMidiDataException{
			Sequence sequence = new Sequence(0.0F, 12);
	        int channel = 0;
	        for (Motive motive : motives) {
	        	Track track = sequence.createTrack();
	        	System.out.print(motive.getVoice() + ":");
				List<NotePos> notes = motive.getNotePositions();
				for (NotePos notePos : notes) {
					System.out.print(notePos.getPitch() + ",");
					ShortMessage noteOn = new ShortMessage();
		        	noteOn.setMessage(ShortMessage.NOTE_ON, channel, notePos.getPitch(), notePos.getDynamic());
		        	MidiEvent event = new MidiEvent(noteOn, notePos.getPosition());
		        	track.add(event);
		        	//note off!!
				}
				System.out.println();
			}
			return sequence; 
		}

}
