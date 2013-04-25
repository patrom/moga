package be.music.tonal.operator;

import java.util.ArrayList;
import java.util.List;

import jm.music.data.Note;
import jmetal.base.Solution;
import jmetal.base.operator.mutation.Mutation;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import jmetal.util.PseudoRandom;
import be.data.InstrumentRange;
import be.data.MelodicSentence;
import be.data.Motive;
import be.data.MusicalStructure;
import be.data.NotePos;
import be.music.MusicVariable;

public class ParallelHarmonyMutation extends Mutation {
	
	private static final double ATOMIC_VALUE = 12;
	private static final int DYNAMIC = Note.DEFAULT_DYNAMIC;
	
	private int[] scale;
	private List<InstrumentRange> ranges;
	
	public ParallelHarmonyMutation(int[] scale, List<InstrumentRange> ranges) {
		this.scale = scale;
	}

	@Override
	public Object execute(Object object) throws JMException {
		Solution solution = (Solution) object;

		Double probability = (Double) getParameter("probabilityParallel");
		if (probability == null) {
			Configuration.logger_.severe("probabilityParallel: probability not " +
			"specified");
			Class cls = java.lang.String.class;
			String name = cls.getName();
			throw new JMException("Exception in " + name + ".execute()");
		}

		doMutation(probability.doubleValue(), solution);
		return solution;
	}
	
	public void doMutation(double probability, Solution solution) throws JMException {
		if (PseudoRandom.randDouble() < probability) {
			List<MusicalStructure> melodies = ((MusicVariable)solution.getDecisionVariables()[0]).getMelodies();
			int melodyIndex = PseudoRandom.randInt(0, melodies.size() - 1);
			if (melodyIndex + 1 < melodies.size()) {
				MusicalStructure musicalStructure = melodies.get(melodyIndex);
				MusicalStructure repeatStructure = melodies.get(melodyIndex + 1);
				List<NotePos> notePositions = musicalStructure.getNotePositions();
				List<NotePos> repeatPositions = repeatStructure.getNotePositions();
				List<NotePos> newNotePositions = new ArrayList<NotePos>();
				NotePos note = repeatPositions.get(0);
				for (NotePos notePos : notePositions) {
					NotePos repeatNote = findPitch(repeatPositions, notePos);
					if (repeatNote != null) {
						NotePos notePosition = new NotePos();
						notePosition.setLength(notePos.getLength());
						notePosition.setPosition(notePos.getPosition());
						notePosition.setPitch(repeatNote.getPitch());
						notePosition.setRhythmValue(notePos.getRhythmValue());
						notePosition.setDuration(notePos.getDuration());
						notePosition.setDynamic(notePos.getDynamic());
						notePosition.setVoice(repeatNote.getVoice());
						newNotePositions.add(notePosition);
						note = repeatNote;
					}else{
						NotePos notePosition = new NotePos();
						notePosition.setLength(notePos.getLength());
						notePosition.setPosition(notePos.getPosition());
						notePosition.setPitch(note.getPitch());
						notePosition.setRhythmValue(notePos.getRhythmValue());
						notePosition.setDuration(notePos.getDuration());
						notePosition.setDynamic(notePos.getDynamic());
						notePosition.setVoice(melodyIndex + 1);
						newNotePositions.add(notePosition);
					}	
				}
				
//				MusicalStructure repeatStructure = clone(musicalStructure, melodyIndex + 1);
//				repeatStructure.transpose(2, scale);
				
				MusicalStructure newStructure = new MelodicSentence();
				Motive motive = new Motive();
				motive.setNotePositions(newNotePositions, musicalStructure.getLength());
				newStructure.setPosition(motive.getPosition());
				newStructure.setLength(motive.getLength());
				newStructure.getMotives().add(motive);
				
				melodies.remove(melodyIndex + 1);
				melodies.add(melodyIndex + 1, newStructure);
				((MusicVariable)solution.getDecisionVariables()[0]).setMelodies(melodies);
				System.out.println("parallel: " + notePositions.size() + ", " + repeatPositions.size() + ", " + newNotePositions.size());
			}
		}
	}

	private NotePos findPitch(List<NotePos> repeatPositions, NotePos notePos) {
		for (NotePos repeatNote : repeatPositions) {
			if (notePos.getPosition() == repeatNote.getPosition()) {
				return repeatNote;
			}
			if (notePos.getPosition() > repeatNote.getPosition()) {
				return null;
			}
		}
		return null;
	}
	
//	private MusicalStructure clone(MusicalStructure musicalStructure, int voice){
//		MusicalStructure newStructure = new MelodicSentence();
//		List<NotePos> notePositions = musicalStructure.getNotePositions();
//		List<NotePos> newNotePositions = new ArrayList<NotePos>();
//		Motive motive = new Motive();
//		for (NotePos notePos : notePositions) {		
//			NotePos notePosition = new NotePos();
//			notePosition.setLength(notePos.getLength());
//			notePosition.setPosition(notePos.getPosition());
//			notePosition.setPitch(notePos.getPitch());
//			notePosition.setRhythmValue(notePos.getRhythmValue());
//			notePosition.setDuration(notePos.getDuration());
//			notePosition.setDynamic(DYNAMIC);
//			notePosition.setVoice(voice);
//			newNotePositions.add(notePosition);
//		}
//		motive.setNotePositions(newNotePositions);
//		newStructure.setPosition(motive.getPosition());
//		newStructure.setLength(motive.getLength());
//		newStructure.getMotives().add(motive);
//		return newStructure;
//	}

}
