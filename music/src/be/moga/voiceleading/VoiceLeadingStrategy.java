package be.moga.voiceleading;

import java.util.List;

import be.data.HarmonyObject;
import be.data.MusicalObject;
import be.data.NotePos;

public interface VoiceLeadingStrategy {

	static final int REST = Integer.MIN_VALUE;
	
	public abstract double evaluateVoiceLeading(MusicalObject[] firstChord, MusicalObject[] secondChord);
	
	public abstract double evaluateVoiceLeading(List<NotePos> firstChord, List<NotePos> secondChord);
}
