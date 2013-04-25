package be.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class RowMatrix {
	
	private int[][] rowMatrix;

	public static void main(String[] args) {
		
//		List<Integer> set = new ArrayList<Integer>();
//		for (int i = 0; i < 12; i++) {
//			set.add(i);
//		}
		Integer[] s = {11,0, 6, 4, 10, 9, 1, 8, 3, 7, 5, 2};
		
	
	
		List<Integer> set = Arrays.asList(TwelveToneSets.violinConcertoSchoenbergHexa1);
//		Collections.shuffle(set);
		System.out.println(set);
		System.out.println(RowMatrix.multiply(set));
		System.out.println(RowMatrix.multiplyInverse(set));
		System.out.println(RowMatrix.rotate(set));
		
		RowMatrix rowMatrix = new RowMatrix(set.size(), set);
		rowMatrix.show();
		
		System.out.println(rowMatrix.transposeSet(0));
		System.out.println(rowMatrix.retrogradeTransposeSet(9));
		System.out.println(rowMatrix.transposeInverseSet(4));
		System.out.println(rowMatrix.retrogradeTransposeInverseSet(3));

		
	}

	private static void setFirstPcToZero(List<Integer> s) {
		if (s.get(0) != 0) {
			Integer first = s.get(0);
			for (int i = 0; i < s.size(); i++) {	
				Integer value = (s.get(i) - first + 12) % 12;
				s.set(i, value);
			}
		}
	}
	
	public RowMatrix(int size, List<Integer> row) {
		super();
		List<Integer> set = new ArrayList<Integer>(row);
		setFirstPcToZero(set);
		this.rowMatrix = new int[size][size];
		List<Integer> inversionSet = TwelveTone.inversion(set);
		for (int i = 0; i < size; i++) {
			rowMatrix[0][i] = set.get(i);
			rowMatrix[i][0] = inversionSet.get(i);
		}
		for (int i = 1; i < size; i++) {
			for (int j = 1; j < size; j++) {
				rowMatrix[i][j] = (inversionSet.get(i) + set.get(j)) % 12;
			}
		}
	}

	public void show() {
		for (int i = 0; i < rowMatrix[0].length; i++) {
			for (int j = 0; j < rowMatrix.length; j++)
				System.out.print(rowMatrix[i][j] + "\t");
			System.out.println();
		}
	}
	
	/**
	 * 	The TnS rows are listed on matrix rows from left to right.
	 * @param n transpose by n
	 * @return
	 */
	public List<Integer> transposeSet(int n) {
		int row = 0;
		int t = (rowMatrix[0][0] + n) % 12;
		for (int i = 0; i < rowMatrix[0].length; i++) {
			if(rowMatrix[i][0] == t){
				row = i;
			}
		}
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < rowMatrix[0].length; i++) {
			list.add(rowMatrix[row][i]);
		}
		return list;
	}

	/**
	 * 	The RTnS rows are listed on matrix rows from right to left.
	 * @param n
	 * @return
	 */
	public List<Integer> retrogradeTransposeSet(int n) {
		int row = 0;
		int t = (rowMatrix[0][0] + n) % 12;
		for (int i = 0; i < rowMatrix[0].length; i++) {
			if(rowMatrix[i][rowMatrix[0].length - 1] == t){
				row = i;
			}
		}
		List<Integer> list = new ArrayList<Integer>();
		for (int i = rowMatrix[0].length - 1; i >= 0; i--) {
			list.add(rowMatrix[row][i]);
		}
		return list;
	}
	
	/**
	 * 	The TnIS rows are listed on matrix columns from top to bottom.
	 * @param n
	 * @return
	 */
	public List<Integer> transposeInverseSet(int n) {
		int column = 0;
		int t = (rowMatrix[0][0] + n) % 12;
		for (int i = 0; i < rowMatrix[0].length; i++) {
			if(rowMatrix[0][i] == t){
				column = i;
			}
		}
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < rowMatrix.length; i++) {
			list.add(rowMatrix[i][column]);
		}
		return list;
	}
	
	/**
	 * 	The RTnIS rows are listed on matrix columns from bottom to top.
	 * @param n
	 * @return
	 */
	public List<Integer> retrogradeTransposeInverseSet(int n) {
		int column = 0;
		int t = (rowMatrix[0][0] + n) % 12;
		for (int i = 0; i < rowMatrix.length; i++) {
			if(rowMatrix[rowMatrix.length - 1][i] == t){
				column = i;
			}
		}
		List<Integer> list = new ArrayList<Integer>();
		for (int i = rowMatrix.length - 1; i >= 0; i--) {
			list.add(rowMatrix[i][column]);
		}
		return list;
	}
	
	/**
	 * M operator
	 * @param set
	 * @return
	 */
	public static List<Integer> multiply (List<Integer> set){
		List<Integer> list = new ArrayList<Integer>();
		for (Integer integer : set) {
			list.add((integer * 5) % 12);
		}
		return list;
	}
	
	
	/**
	 * MI operator
	 * @param set
	 * @return
	 */
	public static List<Integer> multiplyInverse (List<Integer> set){
		List<Integer> list = new ArrayList<Integer>();
		for (Integer integer : set) {
			list.add((integer * 7) % 12);
		}
		return list;
	}
	
	/**
	 * Rotate set 1 position
	 * @param set
	 * @return
	 */
	public static List<Integer> rotate (List<Integer> set){
		List<Integer> list = new ArrayList<Integer>(set);
//		setFirstPcToZero(list);
		Integer first = list.get(0);
		Integer rotateValue = list.get(1);
		list.remove(0);
		list.add(first);
		
		ListIterator<Integer> it = list.listIterator();
		while (it.hasNext()) {
			Integer integer = (Integer) it.next();
			it.set((12 + integer - rotateValue) % 12);
		}
		return list;
	}
	
}
