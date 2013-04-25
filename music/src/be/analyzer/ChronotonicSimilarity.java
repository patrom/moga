package be.analyzer;

import java.util.Arrays;

import be.functions.RhythmicFunctions;


public class ChronotonicSimilarity {

	private static final double K3 = 0.15;
	private static final double K1 = 12.8;
	private double length1, length2, TempValue,
			DistribValue, Similarity;
//	private double[] v1 = new double[100];
//	private double[] v2 = new double[100];



	public void calculateRhythmicSimilarity(double[] v1 , double[] v2) {
		int i = 0, j = 0, al1 = 0, al2 = 0;
		double  SumV1 = 0, SumV2 = 0, F3, v, L, T1 = 0, S;
		double[] a1 = new double[100000];
		double[] a2 = new double[100000];
		double[] T = new double[100000];
		double[] o = new double[100000];
		double[] p = new double[100000];
		i = v1.length;
		j = v2.length;
		for (int k = 0; k < i; k++){
			SumV1 = SumV1 + v1[k];
		}
		for (int k = 0; k < j; k++){
			SumV2 = SumV2 + v2[k];
		}
		v = K3 * Math.pow(Math.log(SumV1 / SumV2), 2);
		F3 = Math.pow(Math.E, -v);// euler? 2.71!
		//or F3 = Math.exp(-v);
		F3 = Math.round(F3 * 10000);
		F3 = F3 / 10000;
		SumV1 = Math.round(SumV1 * 1000);
		SumV1 = SumV1 / 1000;
		SumV2 = Math.round(SumV2 * 1000);
		SumV2 = SumV2 / 1000;

		length1 = SumV1;
		length2= SumV2;
		TempValue = F3;

		if (SumV2 > SumV1){
			L = SumV2 / SumV1;
			for (int m = 0; m < i; m++){
				v1[m] = L * v1[m];
			}
		}

		if (SumV1 > SumV2){
			L = SumV1 / SumV2;
			for (int m = 0; m < j; m++){
				v2[m] = L * v2[m];
			}
		}
		//atomic beat finding
		for (int m = 0; m < i; m++){
			//Note, for a more precise computation it would be sensible to set k+=0.00005
			for (double k = 0.005; k < v1[m]; k += 0.005){
				al1++;
				a1[al1] = v1[m];
			}
		}
		
		for (int m = 0; m < j; m++){
			for (double k = 0.005; k < v2[m]; k += 0.005){
				al2++;
				a2[al2] = v2[m];
			}
		}
		//The difference between the two chains is set to be the logarithmic difference (where T[m] = si)
		//log base of 2 = the natural log of the number divided by the natural log of 2.
		for (int m = 1; m < al1; m++){
			T[m] = Math.log(a1[m] / a2[m]) / Math.log(2);
		}

		for (int m = 1; m < al1; m++){
			o[m] = Math.pow(T[m], 2);
			p[m] = Math.pow(Math.E, (-K1 * o[m]));// euler? 2.71!
			T1 = T1 + Math.pow(p[m], 2);
		}

		T1 = Math.sqrt(T1 / al1);
		T1 = Math.round(T1 * 1000 + 1);
		T1 = T1 / 1000;
		S = F3 * T1;
		S = Math.round(S * 1000);
		S = S / 1000;
		DistribValue = T1;
		Similarity = S;
	}

	public double getLength1() {
		return length1;
	}

	public double getLength2() {
		return length2;
	}

	public double getTempValue() {
		return TempValue;
	}

	public double getDistribValue() {
		return DistribValue;
	}

	public double getSimilarity() {
		return Similarity;
	}
	
	public static void main(String[] args) {
		ChronotonicSimilarity sim = new ChronotonicSimilarity();
		double[] pattern1 = RhythmicFunctions.createFixedLengthRhythmPattern(4.0);
		System.out.println(Arrays.toString(pattern1));
		double[] pattern2 = RhythmicFunctions.createFixedLengthRhythmPattern(4.0);
//		double[] pattern2 = {1.5,0.5,1.0};
		System.out.println(Arrays.toString(pattern2));
		sim.calculateRhythmicSimilarity(pattern1, pattern2);
		System.out.println(sim.getLength1());
		System.out.println(sim.getLength2());
		System.out.println(sim.getTempValue());
		System.out.println(sim.getDistribValue());
		System.out.println(sim.getSimilarity());
	}
}
