package be.core;
import java.util.Comparator;

import jm.music.data.Note;


public class NoteComparator implements Comparator<Note>{

	public int compare(Note note1, Note note2) {
		int pitch1 = note1.getPitch();
		int pitch2 = note2.getPitch();
		int difference = Math.abs(pitch1 - pitch2);
		if(difference % 12 == 0){
			return 0;
		}
		if(note1.getPitch() < note2.getPitch()){
			return -1;
		}
		if(note1.getPitch() > note2.getPitch()){
			return 1;
		}
		return 0;
	}

}
