package be.test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import jm.music.data.Score;
import jm.util.Play;
import jm.util.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import be.data.IntervalData;
import be.data.Motive;
import be.data.MusicalStructure;
import be.data.Scale;
import be.moga.MusicEvaluation;
import be.moga.MusicEvaluationImpl;
import be.moga.MusicProperties;
import be.util.Populator;
import be.util.ScoreUtilities;
import be.util.TestPopulation;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class TestPopulator {
	
	private static Logger LOGGER = Logger.getLogger(TestPopulator.class.getName());
	private MusicEvaluation impl;
	double[] objectives;
	
	public TestPopulator() throws IOException{
	}
	
	@Before
	public void init() throws IOException {
//		configureLogger(Level.FINER);
		MusicProperties props = new MusicProperties();
		props.setNumerator(4);
		props.setRhythmTemplateValue(10);
		impl = new MusicEvaluationImpl(props);
	}
	
	@After
	public void loggerInfo() {
		if (objectives != null) {
			LOGGER.info( "Harmony: " + objectives[0] + ", " 
					+ "VoiceLeading: " + objectives[1] + ", " 
					+ "Melody: " + objectives[2] + ", "
					+ "Rhythm: " + objectives[3] + ", "
					+ "tonality: " + objectives[4] + "\n");
//					+ "Constraints: lowest interval register: " + objectives[5] + ", "
//					+ "repetitions Pitches: " + objectives[6] + ", "
//					+ "repetitions rhythms: " + objectives[7]);
		}
		
//		Play.midi(s, false);
	}

	@Test
	public void testEvaluate() {
		LOGGER.info("testEvaluate");
		List<Motive> motives = TestPopulation.cChord3();	
		List<MusicalStructure> sentences = Populator.getInstance().extractSentence(motives);
		objectives = impl.evaluate(sentences);
		assertEquals(0.175, objectives[0], 0.0001);		
	}
	
	@Test
	public void testPassingToneOnBeat(){
		LOGGER.info("testPassingToneOnBeat");
		List<Motive> motives = TestPopulation.passingToneOnBeat();	
		List<MusicalStructure> sentences = Populator.getInstance().extractSentence(motives);
		objectives = impl.evaluate(sentences);
		Score s = ScoreUtilities.createScore2(sentences, null);
		View.notate(s);
//		Play.midi(s, false);
	}
	
	@Test
	public void testPassingToneOffBeat(){
		LOGGER.info("testPassingToneOffBeat");
		List<Motive> motives = TestPopulation.passingToneOffBeat();
		List<MusicalStructure> sentences = Populator.getInstance().extractSentence(motives);
		objectives = impl.evaluate(sentences);
		Score s = ScoreUtilities.createScore2(sentences, null);
		View.notate(s);
	}
	
	@Test
	public void testSuspension(){
		LOGGER.info("testSuspension");
		List<Motive> motives = TestPopulation.suspension();
		List<MusicalStructure> sentences = Populator.getInstance().extractSentence(motives);
		objectives = impl.evaluate(sentences);
//		Score s = ScoreUtilities.createScore2(sentences, null);
//		View.notate(s);
	}
	
	@Test
	public void testNoSuspension(){
		LOGGER.info("testNoSuspension");
		List<Motive> motives = TestPopulation.noSuspension();
		List<MusicalStructure> sentences = Populator.getInstance().extractSentence(motives);
		objectives = impl.evaluate(sentences);
		Score s = ScoreUtilities.createScore2(sentences, null);
		View.notate(s);
	}
	
	@Test
	public void testSuspensionAccent(){
		LOGGER.info("testSuspensionAccent");
		List<Motive> motives = TestPopulation.suspensionAccent();
		List<MusicalStructure> sentences = Populator.getInstance().extractSentence(motives);
		objectives = impl.evaluate(sentences);
//		Score s = ScoreUtilities.createScore2(sentences, null);
//		View.notate(s);
	}
	
//	@Test
//	public void testTranspose(){
//		LOGGER.info("testTranspose");
//		List<Motive> motives = TestPopulation.passingToneOnBeat();	
//		for (Motive motive : motives) {
//			motive.transpose(2, Scale.MAJOR_SCALE);
//		}
//		List<MusicalStructure> sentences = Populator.getInstance().extractSentence(motives);
////		Score s = ScoreUtilities.createScore2(sentences, null);
////		View.notate(s);
////		Play.midi(s, false);
//	}
	
	@Test
	public void testPedaalToon(){
		LOGGER.info("testPedaalToon");
		List<Motive> motives = TestPopulation.pedaalToon();	
		List<MusicalStructure> sentences = Populator.getInstance().extractSentence(motives);
		objectives = impl.evaluate(sentences);
//		Score s = ScoreUtilities.createScore2(sentences, null);
//		View.notate(s);
//		Play.midi(s, false);
	}
	
	@Test
	public void testInnermetric(){
		LOGGER.info("testInnermetric");
		List<Motive> motives = TestPopulation.melodyDisplacement(6);
		List<MusicalStructure> sentences = Populator.getInstance().extractSentence(motives);
		Map<Integer, Double> map = impl.applyInnerMetricWeight(sentences);
//		Score s = ScoreUtilities.createScore2(sentences, null);
//		View.notate(s);
//		Play.midi(s, false);
	}
	
	private  void configureLogger(Level level) throws IOException {
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
