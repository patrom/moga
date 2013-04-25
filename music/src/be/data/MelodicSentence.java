package be.data;

import java.util.ArrayList;
import java.util.List;

public class MelodicSentence extends MusicalStructure {

	private List<MusicalStructure> motives = new ArrayList<MusicalStructure>();

	public List<MusicalStructure> getMotives() {
		return motives;
	}

	public void setMotives(List<MusicalStructure> motives) {
		int lastMotive = motives.size() - 1;
		setLength(motives.get(lastMotive).getPosition() + motives.get(lastMotive).getLength() - motives.get(0).getPosition());
		setPosition(motives.get(0).getPosition());
		this.motives = motives;
	}

	@Override
	public void transpose(int steps, int[] scale) {
		for (MusicalStructure motive: motives) {
			motive.transpose(steps, scale);
		}		
	}

	public List<NotePos> getNotePositions() {	
//		int totalLength = 0;
//		for (MusicalStructure motive : motives) {
//			totalLength = totalLength + motive.getNotePositions().size();
//		}
		List<NotePos> notePositions = new ArrayList<NotePos>();
//		int j = 0;
		for (MusicalStructure motive : motives) {
			List<NotePos> notes = motive.getNotePositions();
			if (notes != null) {
				for (NotePos notePos : notes) {
					notePositions.add(notePos);
				}
			}
		}
		return notePositions;
	}

	@Override
	public void setNotePositions(List<NotePos> notePositions, int length) {
		motives.clear();
		Motive motive = new Motive(notePositions, length);
		motives.add(motive);
	}


}
