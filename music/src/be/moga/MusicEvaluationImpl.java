package be.moga;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

import be.data.IntervalData;
import be.data.MusicalStructure;
import be.data.NotePos;
import be.functions.MelodicFunctions;
import be.functions.RhythmicFunctions;
import be.functions.TonalityFunctions;
import be.util.Frequency;
import be.util.Populator;
import be.util.Utilities;

public class MusicEvaluationImpl extends MusicEvaluation{

	private static Logger LOGGER = Logger.getLogger(MusicEvaluationImpl.class.getName());
	
//	private static final int DYNAMIC_FACTOR = 3;
//	private static final double DYNAMIC = Note.DEFAULT_DYNAMIC;
//	private static final int DEFAULT_NOTE_ON_VALUE = 10;//note on accent
//
//	private static final double DEFAULT_REGISTER_VALUE = 1.0;//lowest interval C(48)-E(52)
//
//	private static final int UPPER_LIMIT_PITCH = 84;
	private static Populator populator = Populator.getInstance();

	public MusicEvaluationImpl(MusicProperties properties) {
		super(properties);
	}
	
	public double evaluateHarmony(List<MusicalStructure> sentences) {
		List<IntervalData> intervals = populator.extractIntervals(sentences);
//		double totalIntervalsLength = 0;
//		for (IntervalData intervalData : intervals) {
//			totalIntervalsLength += intervalData.getLength();
//		}
		double value = 0;
		int count = 0;
		for (IntervalData intervalData : intervals) {
//			if (!intervalData.getInterval().equals(Interval.UNISONO)) {
				double pitchWeight = intervalData.getHarmonicWeight() * 1/Frequency.getFrequency(intervalData.getLowerNote().getPitch()) * 10;
				value += (intervalData.getHarmonicWeight() * 0.5) 
				+ (intervalData.getHarmonicWeight() * intervalData.getRhythmWeight() * 0.1) 
				- (pitchWeight * 0.4);	//* (intervalData.getLength()/totalIntervalsLength)
				count++;
//			}
		}	
		return value/count;
	}
	
	public double evaluateVL(List<MusicalStructure> sentences){
		Map<Integer, Double> map = new TreeMap<Integer, Double>();
		int count = 0;
		for (MusicalStructure musicalStructure : sentences) {
			List<NotePos> notes = musicalStructure.getNotePositions();
			int l = notes.size() - 1;
			for (int i = 0; i < l; i++) {
				NotePos note1 = notes.get(i);
				NotePos note2 = notes.get(i + 1);
				int position = note2.getPosition();
				double difference = Math.abs(note1.getPitch() - note2.getPitch());
				if (map.containsKey(position)) {
					Double value = map.get(position);
					value += difference;
					map.put(position, value);
				} else {
					map.put(position, difference);
				}
				count++;
			}
		}
//		Double[] values = new Double[map.size()];
//		values = map.values().toArray(values);
//		double[] valuesArray = ArrayUtils.toPrimitive(values);
//		DescriptiveStatistics stats = new DescriptiveStatistics(valuesArray);
//		stats.getMax();
		LOGGER.finer("VL: " + map.values().toString());
		return !map.isEmpty()?Collections.max(map.values()):Double.MAX_VALUE;
	}
	
	public double evaluateMelody(List<MusicalStructure> sentences) {
		double total = 0.0;
		int count = 0;
		for (MusicalStructure musicalStructure : sentences) {
			List<NotePos> notePositions = musicalStructure.getNotePositions();
			if (notePositions.size() > 1) {
				List<Double> listWeights;
				if (notePositions.size() > 4) {
					listWeights = MelodicFunctions.getMelodicWeights2(notePositions, 4);
				} else {
					listWeights = MelodicFunctions.getMelodicWeights2(notePositions, notePositions.size());
				}
				double[] melodicWeights = Utilities.listToArray(listWeights);
				LOGGER.fine(Arrays.toString(melodicWeights));
				DescriptiveStatistics stats = new DescriptiveStatistics(melodicWeights);
				// Compute some statistics
				double mean = stats.getGeometricMean();
//				double mean = stats.getStandardDeviation();
				LOGGER.fine("melodicValue mean: " + mean);
				LOGGER.fine("melodicValue standarddeviation: " + stats.getStandardDeviation());
				if (!Double.isNaN(mean)) {//when melody contains no intervals (note repeat, octave)
					total = total + mean;
					count++;
				}
			}
		}
		return total/count;
	}
	
	public double evaluateRhythm(List<MusicalStructure> sentences) {
		double total = 0;
		double count = 0;
		for (MusicalStructure musicalStructure : sentences) {
			List<NotePos> notes = musicalStructure.getNotePositions();
			if (!notes.isEmpty()) {
				Map<Integer, Double> map = getInnerMetricMap(musicalStructure);
				double metricCoherence = RhythmicFunctions.calculateMetricCoherenceValue(map, numerator);
				LOGGER.finer("metricCoherence: " + metricCoherence);
				if (metricCoherence != Double.MAX_VALUE) {
					total = total + metricCoherence;
					count++;
				}
			}		
		}
		return (count == 0)?0.0:total/count;
	}
	
	public double evaluateMajorMinorTonality(List<MusicalStructure> sentences) {
		double major = TonalityFunctions.getMaxCorrelationTonality(sentences, TonalityFunctions.vectorMajorTemplate);
		double minor = TonalityFunctions.getMaxCorrelationTonality(sentences, TonalityFunctions.vectorMinorTemplate);
		if (major > minor) {
			return major;
		} else {
			return minor;
		}
	}
	

	private static void configureLogger(Level level) throws IOException {
		Logger topLogger = Logger.getLogger("");
		ConsoleHandler ch = new ConsoleHandler();
		ch.setLevel(level);
		topLogger.addHandler(ch);
		topLogger.setLevel(level);
		FileHandler fileTxt = new FileHandler("Logging.txt");
		SimpleFormatter formatterTxt = new SimpleFormatter();
		fileTxt.setFormatter(formatterTxt);
		topLogger.addHandler(fileTxt);
	}
	
//	public static void main(String[] args) throws IOException, InvalidMidiDataException {
//	configureLogger(Level.INFO);
//	String path = "C:/comp/test/"; 
//	File folder = new File(path);
//	File[] listOfFiles = folder.listFiles();
//	for (File file : listOfFiles) {
//		List<Motive> melodies = MidiParser.readMidi(file.getAbsolutePath());
//		
////		List<InstrumentRange> ranges = new ArrayList<InstrumentRange>();
////		int start = UPPER_LIMIT_PITCH;
////		for (int i = 0; i < 4; i++) {
////			InstrumentRange range = new InstrumentRange();
////			range.setVoice(i);
////			range.setLowest(start - 24);
////			range.setHighest(start);
////			ranges.add(range);
////			start = start - 12;
////		}
//	
////	List<Note[]> melodies = TestContrapunt.rhythmConstraint();
////	List<Note[]> melodies = TestContrapunt.homoPhonicWithoutAccents();
////	List<NotePos> notes;
////	List<Motive> melodies = MidiParser.readMidi("mozartMK387m1- 2.mid");
////	List<Motive> melodies = TestPopulation.passingToneOffBeat();
////	List<Motive> melodies = TestPopulation.passingToneOnBeat();
////	List<Motive> melodies = TestPopulation.randomMelody();
////	List<Motive> melodies = TestPopulation.testLength();
////	melodies.addAll(melodies2);
//	
////	 Motive motive = Populator.getInstance().generateRow(TwelveToneSets.twelveToneSet);
////	 List<Motive> melodies = new ArrayList<Motive>();
////	 melodies.add(motive);
//		List<MusicalStructure> sentences = populator.extractSentence(melodies);
//		Score s = ScoreUtilities.createScore2(sentences, null);
//		s.setNumerator(2);
//		s.setDenominator(4);
//		s.setTitle(file.getName());
//		MusicProperties props = new MusicProperties();
//		props.setNumerator(2);
//		props.setRhythmTemplateValue(10);
//		MusicEvaluationImpl controller = new MusicEvaluationImpl(props);
////	
////	List<Motive> melodies = populator.generateChordsWithoutRhythm(8, Scale.MAJOR_SCALE, ranges);
//	
////	Set<Integer> onSets = populator.extractNoteOnsets(sentences);
////	Map<Integer, HarmonyObject[]> harmonyObjects = populator.extractHarmonyObjects2(sentences, onSets);
////	for (HarmonyObject[] harmonies : harmonyObjects.values()) {
////		LOGGER.finer(Arrays.toString(harmonies));
////	}
////	controller.evaluate(sentences);
//	
////	Write.midi(s);
//	
//	double[] objectives = controller.evaluate(sentences);
////	View.histogram(s);
////	LOGGER.info("major:" + TonalityFunctions.getCorrelation(s, TonalityFunctions.vectorMajorTemplate) + "\n" +
////				"minor:" + TonalityFunctions.getCorrelation(s, TonalityFunctions.vectorMinorTemplate));
////	
//	LOGGER.info(s.getTitle() + "\n"  
//			+ "Harmony: " + objectives[0] + ", " 
//			+ "VoiceLeading: " + objectives[1] + ", " 
//			+ "Melody: " + objectives[2] + ", "
//			+ "Rhythm: " + objectives[3] + ", "
//			+ "tonality: " + objectives[4] + "\n");
////			+ "Constraints: lowest interval register: " + objectives[5] + ", "
////			+ "repetitions Pitches: " + objectives[6] + ", "
////			+ "repetitions rhythms: " + objectives[7]);
////	View.notate(s);
////	Play.midi(s, false);
//	}
//}

//public double[] evaluate(List<MusicalStructure> sentences) {
////	Set<Integer> noteOnsets = extractNoteOnsets(sentences);
//	double[] objectives = new double[5];
//		
//	switch (numerator) {//4/4 = 4 ; 2/4 = 2 ; 3/4 = 3 ; 6/8 = 6
//	case 2:
//		applyDynamicTemplate(sentences, 6, true);
//		break;
//	case 3:
//		applyDynamicTemplate(sentences, 12, false);	
//		break;
//	case 4:
//		applyDynamicTemplate(sentences, 12, true);
//		break;
//	case 6:
//		applyDynamicTemplate(sentences, 6, false);
//		break;
//	default:
//		break;
//	}
////	
//	Map<Integer, Double> map = applyInnerMetricWeight(sentences);
//	LOGGER.fine("Inner metric map: " + map.toString());
//
//	//harmony
//	
//	double harmonyMean = evaluateHarmony(sentences);
//	LOGGER.fine("mean harmonicValues: " + harmonyMean);
//
//	//voice leading
//	double voiceLeading = evaluateVL(sentences);
//	LOGGER.fine("max voiceLeadingSize: " + voiceLeading);
//	
//	double melodicValue = evaluateMelody(sentences);
//	if (Double.isNaN(melodicValue)) {
//		melodicValue = Double.MAX_VALUE;
//	}
//	LOGGER.fine("melodicValue = " + melodicValue);
//	
//	double rhythmicValue = evaluateRhythm(sentences, numerator);
//	LOGGER.fine("rhythmicValue = " + rhythmicValue);
//
//	double tonalityValue = evaluateMajorMinorTonality(sentences);
//	LOGGER.fine("tonalityValue = " + tonalityValue);
//	
//	objectives[0] = 1 - harmonyMean;
//	objectives[1] = voiceLeading;
//	objectives[2] = 1 - melodicValue;
//	objectives[3] = rhythmicValue;
//	objectives[4] = 1 - tonalityValue;
//	//constraints
////	objectives[5] = lowestIntervalRegisterValue;
////	objectives[6] = repetitionsrhythmsMean;	//only for small motives (5 - 10 notes)
////	objectives[7] = repetitionsPitchesMean;	//only for small motives (5 - 10 notes)
//	return objectives;	
//}


//private void applyDynamicTemplate(List<MusicalStructure> melodies, int beat, boolean even) {
//	int e = even?2:3;
//	int doubleBeat = beat * e;
//	int maxValue = 0;
//	for (MusicalStructure structure : melodies) {
//		List<NotePos> notes = structure.getNotePositions();
//		for (NotePos note : notes) {
//			int position = note.getPosition();
//			//add to every note
//			int valueToAdd = rhythmTemplateValue + DEFAULT_NOTE_ON_VALUE;
//			//add to notes on the accented beats
//			if(position % beat == 0){	
//				valueToAdd = valueToAdd + rhythmTemplateValue;
//			}
//			if(position % doubleBeat == 0){	
//				valueToAdd = valueToAdd + rhythmTemplateValue;
//			}
//			note.setPositionWeight(valueToAdd);
//			if (valueToAdd > maxValue) {
//				maxValue = valueToAdd;
//			}
//		}
//	}
//	//normalize values
//	for (MusicalStructure structure : melodies) {
//		List<NotePos> notes = structure.getNotePositions();
//		for (NotePos note : notes) {
//			double normalizedValue = note.getPositionWeight() / maxValue;
//			note.setPositionWeight(normalizedValue);
//		}
//	}
//}
//
//public Map<Integer, Double> applyInnerMetricWeight(List<MusicalStructure> sentences) {
//	Map<Integer, Double> melodiesMap = new TreeMap<Integer, Double>();
//	for (MusicalStructure sentence : sentences) {
//		List<NotePos> notes = sentence.getNotePositions();
////		LOGGER.fine(map);
//		if (!notes.isEmpty()) {
//			Map<Integer, Double> map = getInnerMetricMap(sentence);
//			Set<Integer> onsets = map.keySet();
//			for (Integer onset : onsets) {
//				for (NotePos note : notes) {
//					if (note.getPosition() == onset) {
//						double value = map.get(onset);
//						note.setInnerMetricWeight(value);
//						break;
//					}
//				}
//				if (melodiesMap.containsKey(onset)) {
//					double value = melodiesMap.get(onset);
//					double newValue = value + map.get(onset);
//					melodiesMap.put(onset, newValue);
//				} else {
//					melodiesMap.put(onset, map.get(onset));
//				}
//			}
//		}
//	}
//	return melodiesMap;
//}
//
//public Map<Integer, Double> getInnerMetricMap(MusicalStructure sentence){
//	int length  = sentence.getLength();
//	Integer[] onSet = InnerMetricWeight.extractOnset(sentence.getNotePositions(), length);
//	NavigableMap<Integer, Double> map1 = InnerMetricWeight.getNormalizedInnerMetricWeight(onSet);
//	Map<Integer, Double> map = new TreeMap<Integer, Double>();
//	if (!map1.isEmpty()) {
//		Map<Integer, Double> m = map1.subMap(map1.firstKey() + length, map1.firstKey() + (length * 2));
//		Set<Integer> keys = m.keySet();
//		for (Integer key : keys) {
//			map.put(key - length + sentence.getPosition(), m.get(key));
//		}
//	}
//	return map;
//}
//

}
