package be.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jm.music.data.Note;
import be.data.MelodicSentence;
import be.data.Motive;
import be.data.MusicalStructure;
import be.data.NotePos;

public class MusicalStructureUtilities {
	//TODO configuration!
	private static final double ATOMIC_VALUE = 12;
	private static final int DYNAMIC = Note.DEFAULT_DYNAMIC;
	
	private static MusicalStructureUtilities instance = null;

	public static MusicalStructureUtilities getInstance(){
		if (instance == null) {
			instance = new MusicalStructureUtilities();
		}
		return instance;
	}
	
	private MusicalStructureUtilities() {
	}
	
//	 public MelodicSentence splitStructures(MusicalStructure melody2, int crossoverpoint) {
//			List<MusicalStructure> motives =  ((MelodicSentence) melody2).getMotives();
//			MelodicSentence melody = new MelodicSentence();
//			for (MusicalStructure motive: motives) {
//				if (motive.getPosition() < crossoverpoint && crossoverpoint < (motive.getPosition() + motive.getLength())) {
//					NotePos[] notePositions = motive.getNotePositions();
//					List<NotePos> offspring1 = new ArrayList<NotePos>();
//					List<NotePos>  offspring2 = new ArrayList<NotePos>();
//					boolean executeFirstTime = true;
//					for (int i = 0; i < notePositions.length; i++) {
//						if (notePositions[i].getPosition() < crossoverpoint) {
//							NotePos notePos = clone(notePositions[i]);
//							if ((notePos.getPosition() + notePos.getLength()) > crossoverpoint ) {
//								notePos.setLength(crossoverpoint - notePos.getPosition());
//								double value = (double)notePos.getLength()/ATOMIC_VALUE;
//								notePos.setRhythmValue(value);
//								notePos.setDuration(value);
//								if (i == notePositions.length - 1) {//if last add remaining
//									NotePos remainPos = clone(notePositions[i]);
//									remainPos.setLength(notePositions[i].getPosition() + notePositions[i].getLength() - crossoverpoint);
//									remainPos.setPosition(crossoverpoint);
//									double remainValue = (double)remainPos.getLength()/ATOMIC_VALUE;
//									remainPos.setRhythmValue(remainValue);
//									remainPos.setDuration(remainValue);
//									offspring2.add(remainPos);
//								}
//							}
//							offspring1.add(notePos);
//						} else {		
//							if (executeFirstTime) {
//								NotePos notePos = clone(notePositions[i]);
//								if (notePos.getPosition() != crossoverpoint) {
////									NotePos previousNotePos = clone(notePositions[i - 1]);
//									NotePos newNotePos = new NotePos();
//									newNotePos.setDynamic(DYNAMIC);
//									newNotePos.setPitch(notePositions[i - 1].getPitch());
//									newNotePos.setPosition(crossoverpoint);
//									int length = notePos.getPosition() - crossoverpoint;
//									newNotePos.setRhythmValue((double)length/ATOMIC_VALUE);
//									newNotePos.setDuration((double)length/ATOMIC_VALUE);
//									newNotePos.setLength(length);
//
//									offspring2.add(newNotePos);
//								}
//								offspring2.add(notePos);
//								executeFirstTime = false;
//							} else {
//								offspring2.add(clone(notePositions[i]));
//							}	
//						}
//					}
//					Motive beginMotive = new Motive();
//					Motive endMotive = new Motive();
//					beginMotive.setNotePositions(offspring1.toArray(new NotePos[offspring1.size()]));
//					endMotive.setNotePositions(offspring2.toArray(new NotePos[offspring2.size()]));
////					multimapBegin.put(beginMotive.getLastNote().getNote().getPitch() % 12, beginMotive);
////					multimapEnd.put(endMotive.getLastNote().getNote().getPitch() % 12, endMotive);
//					melody.getMotives().add(beginMotive);
//					melody.getMotives().add(endMotive);
//					break;
//				}
//			}
//			return melody;
//		}
//
//		public NotePos clone(NotePos notePosition) {
//			NotePos notePos = new NotePos();
//			notePos.setLength(notePosition.getLength());
//			notePos.setPosition(notePosition.getPosition());
//			notePos.setPitch(notePosition.getPitch());
//			notePos.setRhythmValue(notePosition.getRhythmValue());
//			notePos.setDuration(notePosition.getDuration());
//			notePos.setDynamic(DYNAMIC);
//			return notePos;
//		}
//		
//		public void replaceStructure(MusicalStructure musicalStructure, MusicalStructure motive) {
//			List<MusicalStructure> motives = musicalStructure.getMotives();
//			int index = 0;
//			for (MusicalStructure m : motives) {
//				if (m.getPosition() == motive.getPosition()) {
////					m.getLastNote().getNote().getPitch()
//					motives.set(index, motive);
//				}
//				index++;
//			}
//		}
//
//		public MusicalStructure findStructureAtPosition(List<MusicalStructure> motives, int position) {
//			for (MusicalStructure motive : motives) {
//				if (motive.getPosition() == position) {
//					return motive;
//				}
//			}
//			return null;	
//		}
//
//		public MelodicSentence concatStructures(MusicalStructure melody2) {
//			List<MusicalStructure> motives = melody2.getMotives();
//			int l = motives.size();
//			int j = 1;
//			List<MusicalStructure> motivesList = new ArrayList<MusicalStructure>();
//			for (MusicalStructure motive : motives) {
//				if (j == l) {
//					break;
//				}
//				NotePos[] beginNotePositions = motive.getNotePositions();
//				MusicalStructure nextMotive = motives.get(j);
//				NotePos[] endNotePositions = nextMotive.getNotePositions();	
//				// concat matching notes
//				if (motive.getLastNote().samePitch(((Motive) nextMotive).getFirstNote())) {
//					NotePos notePos = motive.getLastNote();
//					notePos.setLength(notePos.getLength() + nextMotive.getFirstNote().getLength());
//					NotePos note = motive.getLastNote();
//					note.setRhythmValue(note.getRhythmValue() + nextMotive.getFirstNote().getRhythmValue());
//					note.setDuration(note.getDuration() + nextMotive.getFirstNote().getDuration());
////					motive.setLastNote(notePos);
//					endNotePositions = Arrays.copyOfRange(endNotePositions, 1, endNotePositions.length);//remove first position
//					nextMotive.setNotePositions(endNotePositions);
//				}
//				
//				int totalLength = beginNotePositions.length + endNotePositions.length;
//				NotePos[] positions = new NotePos[totalLength];
//				for (int i = 0; i < totalLength; i++) {
//					if (i < beginNotePositions.length) {
//						positions[i] = beginNotePositions[i];
//					} else {
//						positions[i] = endNotePositions[i - beginNotePositions.length];
//					}
//				}
//				Motive m = new Motive();
//				m.setNotePositions(positions);
//				motivesList.add(m);
//				j++;
//				
//				
//			}
//			MelodicSentence melody = new MelodicSentence();
//			melody.setMotives(motivesList);
//			return melody;	
//		}
//


}
