package be.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import be.data.Motive;
import be.instrument.Instrument;
import be.instrument.KontaktLibAltViolin;
import be.instrument.KontaktLibCello;
import be.instrument.KontaktLibViolin;
import be.util.TestPopulation;
import be.util.TwelveToneUtil;

@RunWith(JUnit4.class)
public class TwelveToneUtilTest {
	
	private List<Instrument> ranges = new ArrayList<>();
	
	@Before
	public void init(){
		ranges.add(new KontaktLibViolin(0, 1));
		ranges.add(new KontaktLibViolin(1, 2));
		ranges.add(new KontaktLibAltViolin(2, 2));
		ranges.add(new KontaktLibCello(3, 3));
	}

	@Test
	public void testTranspose() {
		fail("Not yet implemented");
	}

	@Test
	public void testInvert() {
		fail("Not yet implemented");
	}

	@Test
	public void testMultiply() {
		List<Motive> motives = TestPopulation.chromaticScale();
		TwelveToneUtil.multiply(5, motives, ranges);
		assertEquals(60, motives.get(0).getFirstNote().getPitch());
	}

	@Test
	public void testReorder() {
		fail("Not yet implemented");
	}

	@Test
	public void testRetrogradePitches() {
		List<Motive> motives = TestPopulation.melody2();
		TwelveToneUtil.retrogradePitches(motives);
		assertEquals(48, motives.get(0).getLastNote().getPitch());
		assertEquals(52, motives.get(0).getFirstNote().getPitch());
	}

}
