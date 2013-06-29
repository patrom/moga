package be.moga;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.sound.midi.InvalidMidiDataException;

import jm.music.data.Score;

import be.data.Motive;
import be.data.MusicalStructure;
import be.data.NotePos;
import be.functions.InnerMetricWeight;
import be.util.MidiParser;
import be.util.Populator;
import be.util.ScoreUtilities;

public abstract class MusicEvaluation {
	
	protected MusicProperties properties;
	protected int numerator;
	private int rhythmTemplateValue;
	private static final int DEFAULT_NOTE_ON_VALUE = 10;//note on accent
	private static Populator populator = Populator.getInstance();

	public MusicEvaluation(MusicProperties properties) {
		this.properties = properties;
		this.numerator = properties.getNumerator();
		this.rhythmTemplateValue = properties.getRhythmTemplateValue();
	}

	private static Logger LOGGER = Logger.getLogger(MusicEvaluationImpl.class.getName());
	
	public double[] evaluate(List<MusicalStructure> sentences) {
//		Set<Integer> noteOnsets = extractNoteOnsets(sentences);
		double[] objectives = new double[5];
			
		switch (numerator) {//4/4 = 4 ; 2/4 = 2 ; 3/4 = 3 ; 6/8 = 6
		case 2:
			applyDynamicTemplate(sentences, 6, true);
			break;
		case 3:
			applyDynamicTemplate(sentences, 12, false);	
			break;
		case 4:
			applyDynamicTemplate(sentences, 12, true);
			break;
		case 6:
			applyDynamicTemplate(sentences, 6, false);
			break;
		default:
			break;
		}
//		
		Map<Integer, Double> map = applyInnerMetricWeight(sentences);
		LOGGER.fine("Inner metric map: " + map.toString());
	
		//harmony
		double harmonyMean = evaluateHarmony(sentences);
		LOGGER.fine("mean harmonicValues: " + harmonyMean);

		//voice leading
		double voiceLeading = evaluateVL(sentences);
		LOGGER.fine("max voiceLeadingSize: " + voiceLeading);
		
		double melodicValue = evaluateMelody(sentences);
		if (Double.isNaN(melodicValue)) {
			melodicValue = Double.MAX_VALUE;
		}
		LOGGER.fine("melodicValue = " + melodicValue);
		
		double rhythmicValue = evaluateRhythm(sentences);
		LOGGER.fine("rhythmicValue = " + rhythmicValue);

		double tonalityValue = evaluateMajorMinorTonality(sentences);
		LOGGER.fine("tonalityValue = " + tonalityValue);
		
		objectives[0] = 1 - harmonyMean;
		objectives[1] = voiceLeading;
		objectives[2] = 1 - melodicValue;
		objectives[3] = rhythmicValue;
		objectives[4] = 1 - tonalityValue;
		//constraints
//		objectives[5] = lowestIntervalRegisterValue;
//		objectives[6] = repetitionsrhythmsMean;	//only for small motives (5 - 10 notes)
//		objectives[7] = repetitionsPitchesMean;	//only for small motives (5 - 10 notes)
		return objectives;	
	}

	protected abstract double evaluateHarmony(List<MusicalStructure> sentences);
	
	protected abstract double evaluateVL(List<MusicalStructure> sentences);
	
	protected abstract double evaluateMelody(List<MusicalStructure> sentences);
	
	protected abstract double evaluateRhythm(List<MusicalStructure> sentences);
	
	protected abstract double evaluateMajorMinorTonality(List<MusicalStructure> sentences);
	
	private void applyDynamicTemplate(List<MusicalStructure> melodies, int beat, boolean even) {
		int e = even?2:3;
		int doubleBeat = beat * e;
		int maxValue = 0;
		for (MusicalStructure structure : melodies) {
			List<NotePos> notes = structure.getNotePositions();
			for (NotePos note : notes) {
				int position = note.getPosition();
				//add to every note
				int valueToAdd = rhythmTemplateValue + DEFAULT_NOTE_ON_VALUE;
				//add to notes on the accented beats
				if(position % beat == 0){	
					valueToAdd = valueToAdd + rhythmTemplateValue;
				}
				if(position % doubleBeat == 0){	
					valueToAdd = valueToAdd + rhythmTemplateValue;
				}
				note.setPositionWeight(valueToAdd);
				if (valueToAdd > maxValue) {
					maxValue = valueToAdd;
				}
			}
		}
		//normalize values
		for (MusicalStructure structure : melodies) {
			List<NotePos> notes = structure.getNotePositions();
			for (NotePos note : notes) {
				double normalizedValue = note.getPositionWeight() / maxValue;
				note.setPositionWeight(normalizedValue);
			}
		}
	}
	
	public Map<Integer, Double> applyInnerMetricWeight(List<MusicalStructure> sentences) {
		Map<Integer, Double> melodiesMap = new TreeMap<Integer, Double>();
		for (MusicalStructure sentence : sentences) {
			List<NotePos> notes = sentence.getNotePositions();
//			LOGGER.fine(map);
			if (!notes.isEmpty()) {
				Map<Integer, Double> map = getInnerMetricMap(sentence);
				Set<Integer> onsets = map.keySet();
				for (Integer onset : onsets) {
					for (NotePos note : notes) {
						if (note.getPosition() == onset) {
							double value = map.get(onset);
							note.setInnerMetricWeight(value);
							break;
						}
					}
					if (melodiesMap.containsKey(onset)) {
						double value = melodiesMap.get(onset);
						double newValue = value + map.get(onset);
						melodiesMap.put(onset, newValue);
					} else {
						melodiesMap.put(onset, map.get(onset));
					}
				}
			}
		}
		return melodiesMap;
	}
	
	public Map<Integer, Double> getInnerMetricMap(MusicalStructure sentence){
		int length  = sentence.getLength();
		Integer[] onSet = InnerMetricWeight.extractOnset(sentence.getNotePositions(), length);
		NavigableMap<Integer, Double> map1 = InnerMetricWeight.getNormalizedInnerMetricWeight(onSet);
		Map<Integer, Double> map = new TreeMap<Integer, Double>();
		if (!map1.isEmpty()) {
			Map<Integer, Double> m = map1.subMap(map1.firstKey() + length, map1.firstKey() + (length * 2));
			Set<Integer> keys = m.keySet();
			for (Integer key : keys) {
				map.put(key - length + sentence.getPosition(), m.get(key));
			}
		}
		return map;
	}
	
	public static void main(String[] args) throws IOException, InvalidMidiDataException {
		configureLogger(Level.INFO);
		String path = "C:/comp/test/"; 
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			List<Motive> melodies = MidiParser.readMidi(file.getAbsolutePath());
			
//			List<InstrumentRange> ranges = new ArrayList<InstrumentRange>();
//			int start = UPPER_LIMIT_PITCH;
//			for (int i = 0; i < 4; i++) {
//				InstrumentRange range = new InstrumentRange();
//				range.setVoice(i);
//				range.setLowest(start - 24);
//				range.setHighest(start);
//				ranges.add(range);
//				start = start - 12;
//			}
		
//		List<Note[]> melodies = TestContrapunt.rhythmConstraint();
//		List<Note[]> melodies = TestContrapunt.homoPhonicWithoutAccents();
//		List<NotePos> notes;
//		List<Motive> melodies = MidiParser.readMidi("mozartMK387m1- 2.mid");
//		List<Motive> melodies = TestPopulation.passingToneOffBeat();
//		List<Motive> melodies = TestPopulation.passingToneOnBeat();
//		List<Motive> melodies = TestPopulation.randomMelody();
//		List<Motive> melodies = TestPopulation.testLength();
//		melodies.addAll(melodies2);
		
//		 Motive motive = Populator.getInstance().generateRow(TwelveToneSets.twelveToneSet);
//		 List<Motive> melodies = new ArrayList<Motive>();
//		 melodies.add(motive);
			List<MusicalStructure> sentences = populator.extractSentence(melodies);
			Score s = ScoreUtilities.createScore2(sentences, null);
			s.setNumerator(2);
			s.setDenominator(4);
			s.setTitle(file.getName());
			MusicProperties props = new MusicProperties();
			props.setNumerator(2);
			props.setRhythmTemplateValue(10);
			MusicEvaluationImpl controller = new MusicEvaluationImpl(props);
//		
//		List<Motive> melodies = populator.generateChordsWithoutRhythm(8, Scale.MAJOR_SCALE, ranges);
		
//		Set<Integer> onSets = populator.extractNoteOnsets(sentences);
//		Map<Integer, HarmonyObject[]> harmonyObjects = populator.extractHarmonyObjects2(sentences, onSets);
//		for (HarmonyObject[] harmonies : harmonyObjects.values()) {
//			LOGGER.finer(Arrays.toString(harmonies));
//		}
//		controller.evaluate(sentences);
		
//		Write.midi(s);
		
		double[] objectives = controller.evaluate(sentences);
//		View.histogram(s);
//		LOGGER.info("major:" + TonalityFunctions.getCorrelation(s, TonalityFunctions.vectorMajorTemplate) + "\n" +
//					"minor:" + TonalityFunctions.getCorrelation(s, TonalityFunctions.vectorMinorTemplate));
//		
		LOGGER.info(s.getTitle() + "\n"  
				+ "Harmony: " + objectives[0] + ", " 
				+ "VoiceLeading: " + objectives[1] + ", " 
				+ "Melody: " + objectives[2] + ", "
				+ "Rhythm: " + objectives[3] + ", "
				+ "tonality: " + objectives[4] + "\n");
//				+ "Constraints: lowest interval register: " + objectives[5] + ", "
//				+ "repetitions Pitches: " + objectives[6] + ", "
//				+ "repetitions rhythms: " + objectives[7]);
//		View.notate(s);
//		Play.midi(s, false);
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
	
}
