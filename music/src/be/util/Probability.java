package be.util;

import java.util.Random;

public class Probability {

	public static void main(String[] args) {
		char[] Select = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' };
		int[] Weight = { 100, 30, 25, 60, 20, 70, 10, 180, 20, 30 };
		int[] WeightSum = new int[Weight.length];

		int i, j, k;
		Random Rnd = new Random();

		WeightSum[0] = Weight[0];

		for (i = 1; i < Weight.length; i++)
			WeightSum[i] = WeightSum[i - 1] + Weight[i];

		for (j = 0; j < 70; j++) {
			k = Rnd.nextInt(WeightSum[Weight.length - 1]);

			for (i = 0; k > WeightSum[i]; i++)
				;
			System.out.print(Select[i]);
		}
	}

}
