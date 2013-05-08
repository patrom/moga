package be.music.tonal;

import java.util.ArrayList;
import java.util.List;

import jm.music.data.Note;
import jmetal.base.Variable;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import be.data.IntervalData;
import be.data.MelodicSentence;
import be.data.Motive;
import be.data.MusicalStructure;
import be.data.NotePos;
import be.music.MusicVariable;
import be.util.Populator;

public class MusicVariableTonal extends MusicVariable{
	//TODO configuration
		private static final int DYNAMIC = Note.DEFAULT_DYNAMIC;
		
//		private List<MusicalStructure> melodies = new ArrayList<MusicalStructure>();


		public List<MusicalStructure> getMelodies() {
			return melodies;
		}

		public void setMelodies(List<MusicalStructure> melodies) {
			this.melodies = melodies;
//			this.harmonyObjects = extractHarmonyObjects(melodies);;
		}

		public MusicVariableTonal(List<MusicalStructure> melodies) {
			this.melodies = melodies;
//			this.harmonyObjects = extractHarmonyObjects(melodies);
		}

		/**
		 * Offspring cloning!
		 * @param musicVariable
		 * @throws JMException
		 */
		public MusicVariableTonal(MusicVariable musicVariable) throws JMException {
			this.melodies = cloneStructure(musicVariable.getMelodies());
//			this.harmonyObjects = extractHarmonyObjects(melodies);	
		}

		@Override
		public Variable deepCopy() {
			try {
		      return new MusicVariableTonal(this);
		    } catch (JMException e) {
		      Configuration.logger_.severe("MusicVariable.deepCopy.execute: JMException");
		      return null ;
		    }
		}
		
		/**
		 * Extracts all (harmonic) musical objects, according to an atomic constant
		 * @param sentences The (melodic) sentences 
		 * @return A list of musical objects
		 */
		public List<IntervalData> extractHarmonyObjects(List<MusicalStructure> sentences) {
			return Populator.getInstance().extractIntervals(sentences);
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
					notePosition.setDynamic(DYNAMIC);
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
