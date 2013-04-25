package be.moga.voiceleading;

import java.util.List;

import org.apache.commons.math.util.FastMath;

import be.data.HarmonyObject;
import be.data.MusicalObject;
import be.data.NotePos;

public class TaxiCabVoiceLeading implements VoiceLeadingStrategy {


//	public double evaluateVoiceLeading(double[] firstChord, double[] secondChord) {
//		double sum = 0;
//		for (int i = 0; i < firstChord.length; i++) {
//			if (firstChord[i] != 0 && secondChord[i] != 0) {
//				sum += FastMath.abs(firstChord[i] - secondChord[i]);
//			}
//		}
//		return sum;
//		// return MathUtils.distance1(firstChord, secondChord);
//	}

	public double evaluateVoiceLeading(MusicalObject[] firstChord, MusicalObject[] secondChord) {
		double sum = 0;
		for (int i = 0; i < firstChord.length; i++) {
			if (firstChord[i] != null && firstChord[i].getPitch() != REST 
					&& secondChord[i] != null && secondChord[i].getPitch() != REST) {
				sum += FastMath.abs(firstChord[i].getPitch() - secondChord[i].getPitch());
			}
		}
		return sum;
	}

	public double evaluateVoiceLeading(HarmonyObject[] firstChord,
			HarmonyObject[] secondChord) {
		double sum = 0;
		int difference = Math.abs(firstChord.length - secondChord.length);
		for (int i = 0; i < firstChord.length; i++) {
			if (firstChord[i] != null && firstChord[i].getPitch() != REST 
					&& secondChord[i] != null && secondChord[i].getPitch() != REST) {
				sum += FastMath.abs(firstChord[i].getPitch() - secondChord[i].getPitch());
			}
		}
		return sum;
	}

	public double evaluateVoiceLeading(List<NotePos> firstChord,
			List<NotePos> secondChord) {
		double sum = 0;
		for (NotePos firstNotePos : firstChord) {
			for (NotePos secNotePos : secondChord) {
				if (firstNotePos.getVoice() == secNotePos.getVoice()) {
					sum += FastMath.abs(firstNotePos.getPitch() - secNotePos.getPitch());
				} 
			}
		}
		return sum;
	}

}
