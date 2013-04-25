package be.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jm.music.data.Note;

import org.apache.commons.math.random.GaussianRandomGenerator;
import org.apache.commons.math.random.JDKRandomGenerator;
import org.apache.commons.math.random.RandomData;
import org.apache.commons.math.random.RandomDataImpl;
import org.apache.commons.math.random.RandomGenerator;
import org.apache.commons.math.random.UncorrelatedRandomVectorGenerator;
import org.apache.commons.math.stat.StatUtils;
import org.apache.commons.math.stat.clustering.EuclideanIntegerPoint;
import org.apache.commons.math.stat.correlation.Covariance;
import org.apache.commons.math.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math.util.MathUtils;

import be.functions.InnerMetricWeight;

public class Correlation {

	public static void main(String[] args) {
		
		double[] vectorMajor = new double[12];
		vectorMajor[0] = 6.35;
		vectorMajor[1] = 2.23;
		vectorMajor[2] = 3.48;
		vectorMajor[3] = 2.33;
		vectorMajor[4] = 4.38;
		vectorMajor[5] = 4.09;
		vectorMajor[6] = 2.52;
		vectorMajor[7] = 5.19;
		vectorMajor[8] = 2.39;
		vectorMajor[9] = 3.66;
		vectorMajor[10] = 2.29;
		vectorMajor[11] = 2.88;
		
		double[] vectorMajorTest = new double[12];
		vectorMajorTest[0] = 6.35;
		vectorMajorTest[1] = 0.0;
		vectorMajorTest[2] = 3.48;
		vectorMajorTest[3] = 0.0;
		vectorMajorTest[4] = 4.38;
		vectorMajorTest[5] = 4.09;
		vectorMajorTest[6] = 0.0;
		vectorMajorTest[7] = 5.19;
		vectorMajorTest[8] = 0.0;
		vectorMajorTest[9] = 3.66;
		vectorMajorTest[10] = 0.0;
		vectorMajorTest[11] = 2.88;
		
		double[] vectorChromatic = new double[12];
		vectorChromatic[0] = 0.0834;
		vectorChromatic[1] = 0.0833;
		vectorChromatic[2] = 0.0833;
		vectorChromatic[3] = 0.0833;
		vectorChromatic[4] = 0.0833;
		vectorChromatic[5] = 0.0833;
		vectorChromatic[6] = 0.0833;
		vectorChromatic[7] = 0.0833;
		vectorChromatic[8] = 0.0833;
		vectorChromatic[9] = 0.0833;
		vectorChromatic[10] = 0.0833;
		vectorChromatic[11] = 0.0833;
		
		double[] vectorMinor = new double[12];
		vectorMinor[0] = 6.33;
		vectorMinor[1] = 2.68;
		vectorMinor[2] = 3.52;
		vectorMinor[3] = 5.38;
		vectorMinor[4] = 2.60;
		vectorMinor[5] = 3.53;
		vectorMinor[6] = 2.54;
		vectorMinor[7] = 4.75;
		vectorMinor[8] = 3.98;
		vectorMinor[9] = 2.69;
		vectorMinor[10] = 3.34;
		vectorMinor[11] = 3.17;

		double[] vectorWeights = new double[12];
		vectorWeights[0] = 0.3;
		vectorWeights[1] = 0.2;
		vectorWeights[2] = 0.2;
		vectorWeights[3] = 0.3;
		vectorWeights[4] = 0.2;
		vectorWeights[5] = 0.2;
		vectorWeights[6] = 0.1;
		vectorWeights[7] = 0.2;
		vectorWeights[8] = 0.2;
		vectorWeights[9] = 0.2;
		vectorWeights[10] = 0.1;
		vectorWeights[11] = 0.4;
		double[] vectorMozart = createVector(TestMelody.melodyMozart());
		System.out.println(Arrays.toString(vectorMozart));
		System.out.println("pearson correlation chromatic: "+ getPearsonCorrelation(vectorChromatic, vectorWeights));

		System.out.println("pearson correlation: "+ getPearsonCorrelation(vectorMajor, vectorWeights));
		double correlation = new PearsonsCorrelation().correlation(vectorMajor,vectorWeights);
		System.out.println("correlation " + correlation);

		System.out.println(StatUtils.mean(vectorWeights));

		System.out.println("bravais "+ pearsonBravaisCorrelation(vectorMajor, vectorWeights));
//		System.out.println(Arrays.toString(vector2));
		
		Map<Integer,Double> map =  getCorrelationMap(vectorMajor, vectorMozart);
		for (Integer key : map.keySet()) {
			Double corr= map.get(key);
			System.out.println("Key: " + key + " correlation: " + corr);
		}
		System.out.println("test");
		Map<Integer,Double> map2 =  getCorrelationMap(vectorMajorTest, vectorWeights);
		for (Integer key : map2.keySet()) {
			Double corr= map2.get(key);
			System.out.println("Key: " + key + " correlation: " + corr);
		}
		
		Double maximumCorrelationMajor = getMaximumCorrelation(vectorMajor,vectorWeights);
		System.out.println("max correlation major: " + maximumCorrelationMajor);
		
		Double maximumCorrelationMinor = getMaximumCorrelation(vectorMinor,vectorWeights);
		System.out.println("max correlation minor: " + maximumCorrelationMinor);
		
		// Get a DescriptiveStatistics instance
		DescriptiveStatistics stats = new DescriptiveStatistics();

		// Add the data from the array
		for (int i = 0; i < vectorWeights.length; i++) {
			stats.addValue(vectorWeights[i]);
		}

		// Compute some statistics
		double mean = stats.getMean();
		double std = stats.getStandardDeviation();
		double var = stats.getVariance();
		System.out.println("mean " + mean);
		System.out.println("StandardDeviation" + std);
		System.out.println("variance " + var);
		double covar = new Covariance().covariance(vectorMajor, vectorWeights);
		System.out.println("covariance " + covar);

		
		
	
//		Collection<Note> collection = new ArrayList<Note>();
//		Note note1 = new Note(71, 1.0);
//		Note note2 = new Note(72, 1.0);
//		Note note3 = new Note(73, 1.0);
//		collection.add(note1);
//		collection.add(note2);
//		collection.add(note3);
//		RandomData randomData = new RandomDataImpl();
//		for (int i = 0; i < 10; i++) {
//			int value = randomData.nextInt(1, 10);
//			System.out.println(value);
//		}
//
//		RandomData randomData2 = new RandomDataImpl();
//
//		Object[] objects = randomData.nextSample(collection, 3);
//		for (Object object : objects) {
//			System.out.println(object);
//		}
		int[] point = new int[3];
		point[0] = 5;
		point[1] = 2;
		point[2] = 3;
		EuclideanIntegerPoint euclidean = new EuclideanIntegerPoint(point);
		int[] point2 = new int[3];
		point2[0] = 2;
		point2[1] = 3;
		point2[2] = 5;
		EuclideanIntegerPoint euclidean2 = new EuclideanIntegerPoint(point2);
		double distance = euclidean.distanceFrom(euclidean2);
		System.out.println("distance " + distance);

//		// Create and seed a RandomGenerator (could use any of the generators in
//		// the random package here)
//		RandomGenerator rg = new JDKRandomGenerator();
//		// rg.setSeed(17399225432l); // Fixed seed means same results every time
//		// Create a GassianRandomGenerator using rg as its source of randomness
//		GaussianRandomGenerator rawGenerator = new GaussianRandomGenerator(rg);
//
//		// Create a CorrelatedRandomVectorGenerator using rawGenerator for the
//		// components
//		UncorrelatedRandomVectorGenerator generator = new UncorrelatedRandomVectorGenerator(
//				10, rawGenerator);
//
//		// Use the generator to generate correlated vectors
//		double[] randomVector = generator.nextVector();
//		for (int i = 0; i < randomVector.length; i++) {
//			double d = randomVector[i];
//			System.out.println(d);
//
//		}
//		double[] randomVector2 = generator.nextVector();
//		for (int i = 0; i < randomVector2.length; i++) {
//			double d = randomVector2[i];
//			System.out.println(d);
//
//		}
		
		//Calculates the L2 (Euclidean) distance between two points.
		double euclideanDistance = MathUtils.distance(vectorMajor, vectorWeights);
		System.out.println("euclideanDistance: " + euclideanDistance);
		//Smoothness: Calculates the L1 (sum of abs) distance between two points.
		double smoothness = MathUtils.distance1(vectorMajor, vectorWeights);
		System.out.println("smoothness: " + smoothness);
		//Calculates the L infinite (max of abs) distance between two points.
		double lInfDistance = MathUtils.distanceInf(vectorMajor, vectorWeights);
		System.out.println("lInfDistance: " + lInfDistance);

	}

	private static Double getMaximumCorrelation(double[] vectorScale,
			double[] vectorWeights) {
		List<Double> correlations = new ArrayList<Double>();
		correlations.add(getPearsonCorrelation(vectorScale, vectorWeights));
		for (int i = 1; i < vectorWeights.length; i++) {
			rotate(vectorWeights);
			correlations.add(getPearsonCorrelation(vectorScale, vectorWeights));
//			System.out.println("pearson correlation: "+ getPearsonCorrelation(vector1, vector2));
//			System.out.println(Arrays.toString(vector2));
		}
		Double maximumCorrelation = Collections.max(correlations);
		return maximumCorrelation;
	}
	
	private static Map<Integer,Double> getCorrelationMap(double[] vectorScale,double[] vectorWeights) {
		Map<Integer,Double> correlations = new TreeMap<Integer,Double>();
		correlations.put(60, getPearsonCorrelation(vectorScale, vectorWeights));
		for (int i = 1; i < vectorWeights.length; i++) {
			rotate(vectorWeights);
			correlations.put(60 + i, getPearsonCorrelation(vectorScale, vectorWeights));
		}
		return correlations;
	}
	

	public static void rotate(double[] theArray) {
      double a = theArray[0];
      int i;
      for(i = 0; i < theArray.length-1; i++)
      theArray[i] = theArray[i+1];
      theArray[i]= a;
	}

	private static double pearsonBravaisCorrelation(double[] vector1,
			double[] vector2) {
		double meanX = StatUtils.mean(vector1);
		double meanY = StatUtils.mean(vector2);
		double sumNominator = 0;
		for (int i = 0; i < 12; i++) {
			sumNominator = sumNominator
					+ (vector1[i] * (vector2[i] - meanY) - meanX
							* (vector2[i] - meanY));
		}
		double sumFirst = 0;
		for (int i = 0; i < 12; i++) {
			sumFirst = sumFirst + Math.pow(vector1[i] - meanX, 2.0);
		}
		double sumSecond = 0;
		for (int i = 0; i < 12; i++) {
			sumSecond = sumSecond + Math.pow(vector2[i] - meanY, 2.0);
		}
		double denominator = Math.sqrt(sumFirst * sumSecond);
		double result = sumNominator / denominator;
		return result;
	}

	public static double getPearsonCorrelation(double[] scores1,
			double[] scores2) {
		double result = 0;
		double sum_sq_x = 0;
		double sum_sq_y = 0;
		double sum_coproduct = 0;
		double mean_x = scores1[0];
		double mean_y = scores2[0];
		for (int i = 2; i < scores1.length + 1; i += 1) {
			double sweep = Double.valueOf(i - 1) / i;
			double delta_x = scores1[i - 1] - mean_x;
			double delta_y = scores2[i - 1] - mean_y;
			sum_sq_x += delta_x * delta_x * sweep;
			sum_sq_y += delta_y * delta_y * sweep;
			sum_coproduct += delta_x * delta_y * sweep;
			mean_x += delta_x / i;
			mean_y += delta_y / i;
		}
		double pop_sd_x = (double) Math.sqrt(sum_sq_x / scores1.length);
		double pop_sd_y = (double) Math.sqrt(sum_sq_y / scores1.length);
		double cov_x_y = sum_coproduct / scores1.length;
		result = cov_x_y / (pop_sd_x * pop_sd_y);
		return result;
	}
	
	public static double[] createVector(Note[] melody){
		double[] durationVector = new double[12];
		for (int i = 0; i < melody.length; i++) {
			int pitchClass = melody[i].getPitch()%12;
			durationVector[pitchClass] = durationVector[pitchClass] + melody[i].getRhythmValue();
		}
		return durationVector;
	}

}
