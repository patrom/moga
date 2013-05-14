package be.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import be.core.TwelveToneSets;
import be.data.HarmonyObject;
import be.data.InstrumentRange;
import be.data.Interval;
import be.data.IntervalData;
import be.data.MelodicSentence;
import be.data.Motive;
import be.data.Partition;
import be.data.MusicalStructure;
import be.data.NotePos;
import be.data.Scale;

public class Populator {

	private static final int START_PITCH = 48;
	private static final int START_PITCH_CLASS = 0;
	private static final int REST = Integer.MIN_VALUE;
	private static final int STARTINGPITCH = 84;
	private static final int RESOLUTION = 12;
	
	private Random random = new Random();

	private static Populator instance = null;

	public static Populator getInstance(){
		if (instance == null) {
			instance = new Populator();
		}
		return instance;
	}
	
	private Populator() {
	}

	public int[] generateChord(int[] scale, List<InstrumentRange> ranges) {
		int[] chord = new int[ranges.size()];
		int pitchClass = Scale.pickRandomFromScale(scale);
		InstrumentRange r = ranges.get(0);
		int octave = r.getLowest() / 12;
		int pitch = (octave * 12) + pitchClass;
		for (int i = 0; i < chord.length; i++) {
			chord[i] = pitch;
			InstrumentRange range = ranges.get(i);
			int pc = Scale.pickRandomFromScale(scale);
			int oct = range.getLowest() / 12;
			int higherPitch = (oct * 12 ) +  pc;
			while (higherPitch > pitch) {
				higherPitch = higherPitch - 12;
			}
			pitch = higherPitch;
		}
		return chord;
	}

	public List<Motive> generateChordsWithoutRhythm(int size, int[] scale, List<InstrumentRange> ranges, int length) {
		List<int[]> chords = new ArrayList<int[]>();
		int voices = ranges.size();
		int[] chord = new int[voices];
		List<Motive> motiveList = new ArrayList<Motive>();
		for (int i = 0; i < size; i++) {		
			chord = generateChord(scale, ranges);
			chords.add(chord);
		}	
		for (int v = 0; v < voices; v++) {
			List<NotePos> melody = new ArrayList<NotePos>();
			for (int i = 0; i < size; i++) {
				int[] pitches = chords.get(i);
				NotePos note = new NotePos(pitches[voices - 1 - v], RESOLUTION * i, RESOLUTION);
				note.setVoice(v);
				melody.add(note);
			}
			Motive motive = new Motive(melody, length);
			motive.setVoice(v);
			motiveList.add(motive);
		}
		return motiveList;
	}
	
	public List<MusicalStructure> extractSentence(List<Motive> melodies){
		List<MusicalStructure> notePositionList = new ArrayList<MusicalStructure>();
		for (Motive motive : melodies) {
			MelodicSentence sentence = new MelodicSentence();
			sentence.setPosition(motive.getPosition());
			sentence.setLength(motive.getLength());
			sentence.getMotives().add(motive);
			notePositionList.add(sentence);
		}
		return notePositionList;
	}

	public List<HarmonyObject[]> extractHarmonyObjects(List<MusicalStructure> sentences) {
		int voice = 0;	
		Set<Integer> notePositions = new TreeSet<Integer>();	
		for (MusicalStructure sentence : sentences) {
			List<NotePos> positions = sentence.getNotePositions();
			for (NotePos notePos : positions) {
				notePositions.add(notePos.getPosition());
			}
		}
		int length = notePositions.size();
		List<HarmonyObject[]> harmonyObjects = new ArrayList<HarmonyObject[]>(length);
		for (int i = 0; i < length; i++) {
			HarmonyObject[] harmonyObjectChord = new HarmonyObject[sentences.size()];
			harmonyObjects.add(harmonyObjectChord);
		}
		Integer[] allPositions = new Integer[length];
		allPositions = notePositions.toArray(allPositions);
		for (MusicalStructure sentence : sentences) {
			List<NotePos> notes = sentence.getNotePositions();
			if (notes.size() > 1) {
				for (int j = 0, i = 0; j < allPositions.length; j++) {
					int melodyPosition = 0;
					if (i < notes.size()) {
						melodyPosition = notes.get(i).getPosition();	
					} else {//last position
						melodyPosition = notes.get(i-1).getPosition();
					}	
					int allPosition = allPositions[j];
					HarmonyObject[] harmonyObjectChord = harmonyObjects.get(j);
					if (allPosition == melodyPosition) {
						createHarmonyObject(voice, notes.get(i), harmonyObjectChord);
						i++;
					}else{
						if (i != 0) {
							createHarmonyPreviousObject(voice, notes.get(i-1), harmonyObjectChord);//create harmony with previous note
						}	
					}
				}
			}
			voice++;
		}
		return harmonyObjects;
	}
	
	/**
	 * Doesn't copy position and innermetric weight of note
	 * @param voice
	 * @param note
	 * @param harmonyObjectChord
	 */
	private void createHarmonyPreviousObject(int voice, NotePos note, HarmonyObject[] harmonyObjectChord) {
		HarmonyObject harmonyObject = new HarmonyObject();
		harmonyObject.setPosition(note.getPosition());
		harmonyObject.setLength(note.getLength());
		harmonyObject.setWeight(note.getWeight());
		if(note.isRest()){
			harmonyObject.setPitch(REST);
			harmonyObject.setVoice(voice);
			harmonyObject.setDynamic(0);
		}else{
			int pitch = note.getPitch();
			harmonyObject.setPitch(pitch);
//			harmonyObject.setPitchClass(pitch % 12);	
//			harmonyObject.setOctave((int) Math.floor(pitch/12));
			harmonyObject.setVoice(voice);
			harmonyObject.setDynamic(note.getDynamic());
		}
		harmonyObjectChord[voice] = harmonyObject;
		
	}

	private void createHarmonyObject(int voice, NotePos note, HarmonyObject[] harmonyObjectChord) {
		HarmonyObject harmonyObject = new HarmonyObject();
		harmonyObject.setPosition(note.getPosition());
		harmonyObject.setLength(note.getLength());
		harmonyObject.setWeight(note.getWeight());
		if(note.isRest()){
			harmonyObject.setPitch(REST);
			harmonyObject.setVoice(voice);
			harmonyObject.setDynamic(0);
		}else{
			int pitch = note.getPitch();
			harmonyObject.setPitch(pitch);
//			harmonyObject.setPitchClass(pitch % 12);	
//			harmonyObject.setOctave((int) Math.floor(pitch/12));
			harmonyObject.setPositionWeight(note.getPositionWeight());
			harmonyObject.setInnerMetricWeight(note.getInnerMetricWeight());
			harmonyObject.setVoice(voice);
			harmonyObject.setDynamic(note.getDynamic());
		}
		harmonyObjectChord[voice] = harmonyObject;
	}
	
	public Partition generateRow(List<Integer> row, InstrumentRange range, int offset) {
		int size = row.size();
		int length = offset;
		List<NotePos> melody = new ArrayList<NotePos>();
		for (int i = 0; i < size; i++) {
			int pitch = row.get(i);
			NotePos note = new NotePos(pitch + range.getLowest(), (RESOLUTION * i) + offset, RESOLUTION);
			note.setVoice(range.getVoice());
			melody.add(note);
			length += note.getLength();
		}
		Partition motive = new Partition(melody, length, offset);
		motive.setVoice(range.getVoice());
		return motive;
	}
	
	
	public  Set<Integer> extractNoteOnsets(List<MusicalStructure> sentences) {
		Set<Integer> noteOnsets = new TreeSet<Integer>();
		for (MusicalStructure structure : sentences) {
			List<NotePos> notePositions = structure.getNotePositions();
			for (NotePos notePos : notePositions) {
				int position = notePos.getPosition();
				noteOnsets.add(position);
			}
		}
		return noteOnsets;
	}
	
	public Map<Integer, List<HarmonyObject>> extractHarmonyObjects2(List<MusicalStructure> sentences, Set<Integer> onSets) {
		Map<Integer, List<HarmonyObject>> map = new TreeMap<Integer, List<HarmonyObject>>();
		int size = sentences.size();
		for (int i = 0; i < size; i++) {
			List<NotePos> positions = sentences.get(i).getNotePositions();
			int l = sentences.get(i).getLength();
			for (NotePos notePos : positions) {
				int position = notePos.getPosition();
				int length = notePos.getLength();
				int totalLength = position + length;	
				for (Integer onSet : onSets) {
					if (position == onSet) {
						if (map.containsKey(position)) {
							List<HarmonyObject> harmonyObjectArray = map.get(position);
							HarmonyObject harmonyObject = createHarmonyObject(i, notePos, l);
							harmonyObjectArray.add(harmonyObject);
						} else {
							List<HarmonyObject> harmonyObjectArray = new ArrayList<HarmonyObject>();
							HarmonyObject harmonyObject = createHarmonyObject(i, notePos, l);
							harmonyObjectArray.add(harmonyObject);
							map.put(notePos.getPosition(), harmonyObjectArray);
						}
					} else if(onSet > position && onSet < totalLength){
						if (map.containsKey(onSet)) {
							List<HarmonyObject> harmonyObjectArray = map.get(onSet);
							HarmonyObject harmonyObject = createHarmonyObject(i, notePos, l);
							harmonyObjectArray.add(harmonyObject);
						} else {
							List<HarmonyObject> harmonyObjectArray = new ArrayList<HarmonyObject>();
							HarmonyObject harmonyObject = createHarmonyObject(i, notePos, l);
							harmonyObjectArray.add(harmonyObject);
							map.put(onSet, harmonyObjectArray);
						}
					} else if(onSet >= totalLength){
						break;
					}
				}
			}
		}
		return map;	
	}
	
	public Map<Integer, List<NotePos>> extractHarmonyObjects3(List<MusicalStructure> sentences) {
		Map<Integer, List<NotePos>> map = new TreeMap<Integer, List<NotePos>>();
		int size = sentences.size();
		for (int i = 0; i < size; i++) {
			List<NotePos> positions = sentences.get(i).getNotePositions();
			int l = sentences.get(i).getLength();
			for (NotePos notePos : positions) {
				int position = notePos.getPosition();	
				if (map.containsKey(position)) {
					List<NotePos> harmonyObjectArray = map.get(position);
					harmonyObjectArray.add(notePos);
				} else {
					List<NotePos> harmonyObjectArray = new ArrayList<NotePos>();
					harmonyObjectArray.add(notePos);
					map.put(notePos.getPosition(), harmonyObjectArray);
				}	
			}
		}
		return map;	
	}
	
	public List<IntervalData> extractIntervals(List<MusicalStructure> sentences){
		List<IntervalData> intervals = new ArrayList<IntervalData>();
		int size = sentences.size();
		for (int i = 0; i < size - 1; i++) {
			List<NotePos> basePositions = sentences.get(i).getNotePositions();
			for (int j = i + 1; j < size; j++) {
				List<NotePos> position = sentences.get(j).getNotePositions();
				int bs = basePositions.size();
				for (int m = 0; m < bs; m++) {
					NotePos baseNotePos = basePositions.get(m);	
					int s = position.size();
					for (int k = 0; k < s; k++) {
						NotePos notePos = position.get(k);
						//start gelijk aan base noot
						if (notePos.getPosition() == baseNotePos.getPosition()) {
							int length = getMinLength(notePos.getLength(), baseNotePos.getLength());
							IntervalData interval = extractInterval(baseNotePos, notePos, length);
							intervals.add(interval);
						}
						//suspension - start voor en einde volgende noot na begin base noot (rust verlengt noot tot aan volgende noot!)
						else if (notePos.getPosition() < baseNotePos.getPosition()){ 
							int overlap = (notePos.getPosition() + notePos.getLength()) - baseNotePos.getPosition();
							if (overlap > baseNotePos.getLength()) {
								overlap = baseNotePos.getLength();//TODO bereken max rust interval
							}
							if((k+1 < s) && (position.get(k + 1).getPosition() > baseNotePos.getPosition())) {
								IntervalData interval = extractInterval(baseNotePos, notePos, overlap);
								intervals.add(interval);
							}
							if (k+1 == s){//last note
								IntervalData interval = extractInterval(baseNotePos, notePos, overlap);
								intervals.add(interval);
							}	
						}
						//start na begin base noot maar voor volgende base noot (rust verlengt noot tot aan volgende noot!)
						else if (notePos.getPosition() > baseNotePos.getPosition()){
							int overlap = (baseNotePos.getPosition() + baseNotePos.getLength()) - notePos.getPosition();
							if((m+1 < bs) && (basePositions.get(m + 1).getPosition() > notePos.getPosition())){
								IntervalData interval = extractInterval(baseNotePos, notePos, overlap);
								intervals.add(interval);//TODO bereken max rust interval
							}
							if (m+1 == bs) {
								IntervalData interval = extractInterval(baseNotePos, notePos, overlap);
								intervals.add(interval);//TODO bereken max rust interval
							}
						}
					}
				}
			}
		}
		Collections.sort(intervals);
		return intervals;
	}

	private IntervalData extractInterval(NotePos baseNotePos, NotePos notePos, int length) {		
		int difference = (baseNotePos.getPitch() - notePos.getPitch()) % 12;
		double positionWeigtht = (baseNotePos.getPositionWeight() + notePos.getPositionWeight()) / 2;
		double innerMetricWeight = (baseNotePos.getInnerMetricWeight() + notePos.getInnerMetricWeight())/2;
		double dynamic = ((baseNotePos.getDynamic() + notePos.getDynamic())/2) / 127d;//max midi TODO
		Interval interval = Utilities.getEnumInterval(difference);
		double rhythmicWeight = (positionWeigtht + innerMetricWeight) / 2;
		IntervalData intervalData = new IntervalData();
		intervalData.setInterval(interval);
		intervalData.setHarmonicWeight(interval.getHarmonicValue());
		intervalData.setLength(length);
		intervalData.setRhythmWeight(rhythmicWeight);
		intervalData.setDynamic(dynamic);
		intervalData.setLowerNote(notePos);
		intervalData.setHigherNote(baseNotePos);
		intervalData.setPosition(baseNotePos.getPosition());
		return intervalData;
	}

	private int getMinLength(int length1, int length2){
		if (length1 <= length2) {
			return length1;
		} else {
			return length2;
		}
	}

	private HarmonyObject createHarmonyObject(int i, NotePos notePos, int sentenceLength) {
		HarmonyObject harmonyObject = new HarmonyObject();
		harmonyObject.setPitch(notePos.getPitch());
		harmonyObject.setPosition(notePos.getPosition());
		harmonyObject.setLength(notePos.getLength());
		harmonyObject.setVoice(i);
		harmonyObject.setPositionWeight(notePos.getPositionWeight());
		harmonyObject.setInnerMetricWeight(notePos.getInnerMetricWeight());
		harmonyObject.setDynamic(notePos.getDynamic());
		harmonyObject.setSentenceLength(sentenceLength);
		return harmonyObject;
	}

}
