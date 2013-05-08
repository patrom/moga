package be.music.twelvetone;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jmetal.base.Variable;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import be.data.MelodicSentence;
import be.data.Motive;
import be.data.Partition;
import be.data.MusicalStructure;
import be.data.NotePos;
import be.functions.RhythmicFunctions;
import be.music.MusicVariable;

public class MusicVariableAtonal2 extends MusicVariable {

	private static Random random = new Random(System.currentTimeMillis());
	private static final int MIN_LENGTH = 24;
//	private List<MusicalStructure> melodies = new ArrayList<MusicalStructure>();
	private int size;
	
	private List<Partition> partitions = new ArrayList<Partition>();

	public List<Partition> getPartitions() {
		return partitions;
	}

	public List<MusicalStructure> getMelodies() {
		extractSentences(partitions);
		return melodies;
	}
	
	@Override
	public void setMelodies(List<MusicalStructure> melodies) {
		// TODO Auto-generated method stub
		
	}
	

	public MusicVariableAtonal2(List<Partition> partitions, int size) {
		this.partitions = partitions;
		this.size = size;
		for (int i = 0; i < size; i++) {
			melodies.add(i, new Motive());
		}
	}

	/**
	 * Offspring cloning!
	 * @param musicVariable
	 * @throws JMException
	 */
	public MusicVariableAtonal2(MusicVariableAtonal2 musicVariable) throws JMException {
		this.partitions = cloneMotives(musicVariable.getPartitions());
		this.melodies = musicVariable.getMelodies();
//		extractSentences(partitions);
		
//		this.harmonyObjects = extractHarmonyObjects(melodies);	
	}

	public void extractSentences(List<Partition> partitions) {
		for (MusicalStructure structure : melodies) {
			structure.setFirstNote(null);
			structure.setLastNote(null);
			structure.setLength(0);
			structure.getNotePositions().clear();
		}
		for (Partition motiveAtonal : partitions) {
			List<NotePos> notes = motiveAtonal.getNotes();

//			int rand = random.nextInt(notes.size() - 1);
//			
//				NotePos note1 = notes.get(rand);
//				NotePos note2 = notes.get(rand + 1);
//				int diff = note2.getPosition() - note1.getPosition();
//				if (diff % MIN_LENGTH == 0) {
//					
//					List<NotePos> newNotes = createNotes(note1, diff);
//					for (NotePos notePos : newNotes) {
//						MusicalStructure musicalStructure = melodies.get(note1.getVoice());
//						((Motive)musicalStructure.getMotives().get(0)).addNote( notePos);
//					}	
//				}
			
			for (NotePos notePos : notes) {	
				MusicalStructure musicalStructure = melodies.get(notePos.getVoice());
				if (musicalStructure == null) {
					 musicalStructure = new Motive();
					 melodies.add(notePos.getVoice(), musicalStructure);
				} 
//				musicalStructure.setLength(motiveAtonal.getLength());
//				musicalStructure.setVoice(notePos.getVoice());
				((Motive)musicalStructure.getMotives().get(0)).addNote(notePos);
				
			}
		}
	}

	private List<NotePos> createNotes(NotePos note1, int diff) {
		List<NotePos> notes = new ArrayList<NotePos>();
		int[] rhythms = RhythmicFunctions.getRhythmTemplate(diff);
		note1.setLength(rhythms[0]);
		int rhythmLength = 0;
		for (int i = 1; i < rhythms.length; i++) {//not first note
			rhythmLength = rhythmLength + rhythms[i];
			NotePos note = createNote(note1, rhythmLength);
			notes.add(note);
		}
		return notes;
	}

	private NotePos createNote(NotePos note, int length) {
		NotePos notePosition = new NotePos();
		notePosition.setLength(length);
		notePosition.setPosition(note.getPosition() + length);
		notePosition.setPitch(note.getPitch());
//		notePosition.setRhythmValue(note.getRhythmValue());
//		notePosition.setDuration(note.getDuration());
		notePosition.setVoice(note.getVoice());
//		notePosition.setInnerMetricWeight(note.getInnerMetricWeight());
//		notePosition.setPositionWeight(note.getPositionWeight());
//		notePosition.setWeight(note.getWeight());
//		notePosition.setDynamic(note.getDynamic());
		return notePosition;
	}

	private List<Partition> cloneMotives(List<Partition> partitions) {
		List<Partition> motives = new ArrayList<Partition>();
		for (Partition motiveAtonal : partitions) {
			List<NotePos> notePositions = motiveAtonal.getNotes();
			List<NotePos> newNotePositions = new ArrayList<NotePos>();
			int l = notePositions.size();
			for (int i = 0; i < l; i++) {		
				NotePos notePosition = new NotePos();
				notePosition.setLength(notePositions.get(i).getLength());
				notePosition.setPosition(notePositions.get(i).getPosition());
				notePosition.setPitch(notePositions.get(i).getPitch());
				notePosition.setDuration(notePositions.get(i).getDuration());
				notePosition.setVoice(notePositions.get(i).getVoice());
				notePosition.setInnerMetricWeight(notePositions.get(i).getInnerMetricWeight());
				notePosition.setPositionWeight(notePositions.get(i).getPositionWeight());
				notePosition.setRhythmValue(notePositions.get(i).getRhythmValue());
				notePosition.setWeight(notePositions.get(i).getWeight());
				notePosition.setDynamic(notePositions.get(i).getDynamic());
				newNotePositions.add(notePosition);
			}
			Partition motive = new Partition(newNotePositions, motiveAtonal.getLength(), motiveAtonal.getPosition());
			motives.add(motive);
		}
		return motives;
	}

	@Override
	public Variable deepCopy() {
		try {
	      return new MusicVariableAtonal2(this);
	    } catch (JMException e) {
	      Configuration.logger_.severe("MusicVariable.deepCopy.execute: JMException");
	      return null ;
	    }
	}
	
	public List<MusicalStructure> cloneStructure(List<MusicalStructure> structures) {
		List<MusicalStructure> newStructures = new ArrayList<MusicalStructure>();
		for (MusicalStructure musicalStructure : structures) {
			MusicalStructure newStructure = new MelodicSentence();
			List<NotePos> notePositions = musicalStructure.getNotePositions();
			List<NotePos> newNotePositions = new ArrayList<NotePos>();
			Motive motive = new Motive();
			int l = notePositions.size();
			for (int i = 0; i < l; i++) {		
				NotePos notePosition = new NotePos();
				notePosition.setLength(notePositions.get(i).getLength());
				notePosition.setPosition(notePositions.get(i).getPosition());
				notePosition.setPitch(notePositions.get(i).getPitch());
				notePosition.setRhythmValue(notePositions.get(i).getRhythmValue());
				notePosition.setDuration(notePositions.get(i).getDuration());
				notePosition.setVoice(notePositions.get(i).getVoice());
				notePosition.setInnerMetricWeight(notePositions.get(i).getInnerMetricWeight());
				notePosition.setPositionWeight(notePositions.get(i).getPositionWeight());
				notePosition.setRhythmValue(notePositions.get(i).getRhythmValue());
				notePosition.setWeight(notePositions.get(i).getWeight());
				notePosition.setDynamic(notePositions.get(i).getDynamic());
				newNotePositions.add(notePosition);
			}
			motive.setNotePositions(newNotePositions, musicalStructure.getLength());
			newStructure.setPosition(motive.getPosition());
			newStructure.setLength(motive.getLength());
			newStructure.getMotives().add(motive);
			newStructure.setLowestRange(musicalStructure.getLowestRange());
			newStructure.setHighestRange(musicalStructure.getHighestRange());
			newStructure.setVoice(musicalStructure.getVoice());
			newStructures.add(newStructure);
			}
		return newStructures;
	}


}

