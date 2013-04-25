package be.core;

import java.util.Random;

import be.data.Scale;

public class TestCrossover {

	public static void main(String[] args) {
		int[][] d1 = { { 60, 62, 64, 62 }, { 65, 50, 61, 58 }, { 58, 50, 72 ,54},{ 64, 64, 68 ,67} };
		int[][] d2 = { { 64, 61, 63, 63 }, { 68, 58, 64, 54 }, { 57, 54, 74 ,51},{ 61, 68, 65 ,64} };
		double[] pattern1 = {1.0,0.5,0.5,2.0,0.5};
		double[] pattern2 = {1.5,1.0,1.0,1.0,0.5};
		double[] offspringPattern = doRhythmCrossover(pattern1,pattern2);
		int[][] offspringMutated = doMutation(d1);
		int[][] offspring = doHarmonyCrossover(d1, d2);
		System.out.println(offspring);
	}
	
	private static double[] doRhythmCrossover(double[] pattern, double[] offspringPattern) {
		int splitPoint = 2;
		int length = pattern.length;
		double [] temp = new double[length];
//		int [][] B = new int[data1.length - splitPoint][length];
		System.arraycopy(pattern,0,temp,0,splitPoint);
		System.arraycopy(temp,0,offspringPattern,0,splitPoint);
		System.arraycopy(offspringPattern,splitPoint,pattern,0,pattern.length - splitPoint);
		return offspringPattern;
	}

	public static int[][] doHarmonyCrossover(int[][] parent, int[][] offspring){
		int length = parent[0].length;
		int splitPoint = 2;
		int [][] temp = new int[length][splitPoint];
		for (int j = 0; j < splitPoint; j++) {
			for (int i = 0; i < parent.length; i++) {
				temp[i][j] = parent[i][j];
			}
		}
		for (int j = 0; j < splitPoint; j++) {
			for (int i = 0; i < parent.length; i++) {
				offspring[i][j] = temp[i][j];
			}
		}
		return offspring;

	}
	
	public static int[][] doMelodyCrossover(int[][] parent, int[][] offspring){
		int splitPoint = 2;
		int length = parent[0].length;
		int [][] temp = new int[splitPoint][length];
//		int [][] B = new int[data1.length - splitPoint][length];
		System.arraycopy(parent,0,temp,0,splitPoint);
		System.arraycopy(temp,0,offspring,0,splitPoint);
		System.arraycopy(offspring,splitPoint,parent,0,parent.length - splitPoint);
		return offspring;

	}
	
	public static int[][] doMutation(int[][] parent){
		Random random = new Random();
		System.out.println(parent.length * parent[0].length);
		int randNumber = random.nextInt(parent.length * parent[0].length);
		System.out.println(randNumber);
		int count = 0;
		for (int i = 0; i < parent.length; i++) {
			for (int j = 0; j < parent[i].length; j++) {
				if (count == randNumber) {
					int pitch = parent[i][j];
					int relativePitch = pitch % 12;
					int newRelativePitch = Scale.pickNextPitchFromScale(relativePitch, Scale.MAJOR_SCALE);
					int newPitch = pitch - relativePitch + newRelativePitch;
					parent[i][j] = newPitch;
					return parent;
				}
				count++;
			}
		}
		return parent;
	}
}
