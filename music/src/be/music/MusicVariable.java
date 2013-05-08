package be.music;

import java.util.ArrayList;
import java.util.List;

import jm.music.data.Note;
import jmetal.base.Variable;
import be.data.MusicalStructure;

public abstract class MusicVariable extends Variable{
	//TODO configuration
	private static final int DYNAMIC = Note.DEFAULT_DYNAMIC;
	
	protected List<MusicalStructure> melodies = new ArrayList<MusicalStructure>();

	public abstract List<MusicalStructure> getMelodies() ;

	public abstract void setMelodies(List<MusicalStructure> melodies);
	
}
