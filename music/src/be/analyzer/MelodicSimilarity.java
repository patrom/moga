package be.analyzer;

import be.util.Frequency;
import jm.music.data.Note;

public class MelodicSimilarity {

	private double similarity;
	public int i = 1, j = 1, n;
	public double sp, k1, k2, T1, T2, I3, I4;
	public double[] F1 = new double[100];
	public double[] F2 = new double[100];
	public double[] T = new double[100];
	public double[] I1 = new double[100];
	public double[] I2 = new double[100];

	
	
	public static void main(String[] args) {
		MelodicSimilarity sim = new MelodicSimilarity();
		Note[] noteArray1 = new Note[4];
		noteArray1[0] = new Note(60, 1.0);
		noteArray1[1] = new Note(68, 1.0);
		noteArray1[2] = new Note(65, 1.0);
		noteArray1[3] = new Note(63, 1.0);
		Note[] noteArray2 = new Note[4];
		noteArray2[0] = new Note(60, 1.0);
		noteArray2[1] = new Note(62, 1.0);
		noteArray2[2] = new Note(64, 1.0);
		noteArray2[3] = new Note(64, 1.0);
//		double[] melody1 = new double[noteArray1.length];
//		for (int i = 0; i < noteArray1.length; i++) {
//			melody1[i] = Frequency.getFrequency(noteArray1[i].getPitch());
//		}
//		double[] melody2 = new double[noteArray2.length];
//		for (int i = 0; i < noteArray2.length; i++) {
//			melody2[i] = Frequency.getFrequency(noteArray2[i].getPitch());
//		}
//		double[] melody1 = new double[2];
//		melody1[0] = 440.000;
//		melody1[1] = 440.000;
//		double[] melody2 = new double[2];
//		melody2[0] = 440.000;
//		melody2[1] = 440.000;
//		sim.calculateMelodicSimilarity(melody1, melody2);
//		System.out.println("similarity: " + sim.getSimilarity());
		sim.calculateMelodicSimilarity(noteArray1, noteArray2);
		System.out.println("similarity2: " + sim.getSimilarity());
	}
	
	public void calculateMelodicSimilarity(Note[] noteArray1, Note[] noteArray2) {
		double[] melody1 = new double[noteArray1.length];
		for (int i = 0; i < noteArray1.length; i++) {
			melody1[i] = Frequency.getFrequency(noteArray1[i].getPitch());
		}
		double[] melody2 = new double[noteArray2.length];
		for (int i = 0; i < noteArray2.length; i++) {
			melody2[i] = Frequency.getFrequency(noteArray2[i].getPitch());
		}
		calculateMelodicSimilarity(melody1, melody2);
	}

	public void calculateMelodicSimilarity(double[] melody1, double[] melody2) {
		F1 = melody1;
		F2 = melody2;
		i = melody1.length;
		j = melody2.length;
		if (i != j) {
			throw new IllegalArgumentException("melody lengths are not the same");
		}
		T1 = 0;
		I3 = 0;
		
		n = i - 1;
		for (int l = 1; l <= n; l++) {
			T[l] = Math.log(F1[l] / F2[l]) * 1731;
		}
		for (int l = 1; l <= n - 1; l++) {
			I1[l] = Math.log(F1[l + 1] / F1[l]) * 1731;
			I2[l] = Math.log(F2[l + 1] / F2[l]) * 1731;
		}
		k1 = 0.000019;
		k2 = 0.0000033;
		int c1 = 1;
		int c2 = -2;
		for (int l = 1; l <= n; l++) {
			T1 = T1 + Math.pow(Math.exp((-k1 / Math.pow(n, c1))* Math.pow(T[l], 2)), 2);
		}
		T2 = Math.sqrt(T1 / n);
		if (n <= 1) {
			I4 = 1;
		} else {
			for (int l = 1; l <= n - 1; l++) {
				I3 = I3 + Math.pow(Math.exp((-k2 / Math.pow(n - 1, c2))* Math.pow((I1[l] - I2[l]), 2)), 2);
			}
			I4 = Math.sqrt(I3 / (n - 1));
		}
		similarity = T2 * I4;

	}

	public double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}
	
	
}
