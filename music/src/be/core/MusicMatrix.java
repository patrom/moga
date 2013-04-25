package be.core;

import static jm.constants.Pitches.A4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.music.tools.Mod;

import org.apache.commons.collections.ClosureUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.ArrayUtils;

import be.data.Scale;
import be.functions.HarmonicFunctions;
import be.functions.InnerMetricWeight;
import be.functions.RhythmicFunctions;
import be.functions.VoiceLeadingFunctions;
import be.set.IntervalVector;
import be.set.PitchSet;

public class MusicMatrix  {

	private static final int INNER_METRIC_FACTOR = 2;
	private static final int RHYTHM_TEMPLATE_VALUE = 30;
	private static final int KEY = 0;
	private static final int STARTING_OCTAVE = A4;
	private static final int TIED_NOTES_FACTOR = 3;//1/3 is tied
	private static final int MAXIMUM_PITCH = 80;
	private static final int DEFAULT_DYNAMIC = 30;
	private int columns; // number of columns
	private int rows; // number of rows
	private Note[][] data; // M-by-N array
	private int lowerBound_;  //Stores the lower limit of the variable
	private int upperBound_;  //Stores the upper limit of the variable

	// create M-by-N matrix of 0's

	// create matrix based on 2d array
	public MusicMatrix(int[][] data) {
		columns = data.length;
		rows = data[0].length;
		this.data = new Note[columns][rows];
		for (int i = 0; i < columns; i++)
			for (int j = 0; j < rows; j++)
				this.data[i][j] = new Note(data[i][j], 1.0);
	}
	
	public MusicMatrix(int melodies, int voices, int[] scale, double rhythmValue) {
		columns = voices;
		rows = melodies;
		data = new Note[voices][melodies];
		Random random = new Random();
		Random randomChangePitch = new Random();
		int max = STARTING_OCTAVE;
		Note previousNote = new Note(max, rhythmValue,DEFAULT_DYNAMIC);
		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++){
				Note note = null;
				if (i == 0) {
					note = new Note(max,rhythmValue,DEFAULT_DYNAMIC);
				} else {
					note = new Note(data[i-1][j].getPitch(),rhythmValue,DEFAULT_DYNAMIC);
				}
				int change = randomChangePitch.nextInt(TIED_NOTES_FACTOR);
				if (change == 1 && previousNote.getPitch() < note.getPitch()) {
					data[i][j] = new Note(previousNote.getPitch(), rhythmValue,DEFAULT_DYNAMIC);
				} else {
					int randInt = random.nextInt(12);
					int rest = (note.getPitch() - randInt)%12;
					while (!ArrayUtils.contains(scale, rest)) {
						randInt = random.nextInt(12);
						rest = (note.getPitch() - randInt)%12;
					}	
					note = new Note((note.getPitch() - randInt) + KEY,rhythmValue,DEFAULT_DYNAMIC);
					data[i][j] = note;
					previousNote = new Note(note.getPitch(),rhythmValue,DEFAULT_DYNAMIC);	
				}	
			}		
		}
	}
	

	public MusicMatrix(Note[][] data) {
		this.data = data;
		columns = this.data.length;
		rows = this.data[0].length;
	}

	// copy constructor
	 private MusicMatrix(MusicMatrix A) {
		this(A.data);
		columns = this.data.length;
		rows = this.data[0].length;
	 }

	// swap rows i and j
	 private void swap(int i, int j) {
		 Note[] temp = data[i];
		 data[i] = data[j];
		 data[j] = temp;
	 }

	// create and return the transpose of the invoking matrix
//	public MusicMatrix transpose() {
//		MusicMatrix A = new MusicMatrix(rows, columns);
//		for (int i = 0; i < columns; i++)
//			for (int j = 0; j < rows; j++)
//				A.data[j][i] = this.data[i][j];
//		return A;
//	}

	// return C = A + B
//	 public MusicMatrix plus(MusicMatrix B) {
//		 MusicMatrix A = this;
//		 if (B.columns != A.columns || B.rows != A.rows)
//		 throw new RuntimeException("Illegal matrix dimensions.");
//		 MusicMatrix C = new MusicMatrix(columns, rows);
//		 for (int i = 0; i < columns; i++)
//		 for (int j = 0; j < rows; j++)
//		 C.data[i][j] = A.data[i][j] + B.data[i][j];
//		 return C;
//	 }

	// return C = A - B
	// public MusicMatrix minus(MusicMatrix B) {
	// MusicMatrix A = this;
	// if (B.M != A.M || B.N != A.N)
	// throw new RuntimeException("Illegal matrix dimensions.");
	// MusicMatrix C = new MusicMatrix(M, N);
	// for (int i = 0; i < M; i++)
	// for (int j = 0; j < N; j++)
	// C.data[i][j] = A.data[i][j] - B.data[i][j];
	// return C;
	// }

	// does A = B exactly?
	public boolean eq(MusicMatrix B) {
		MusicMatrix A = this;
		if (B.columns != A.columns || B.rows != A.rows)
			throw new RuntimeException("Illegal matrix dimensions.");
		for (int i = 0; i < columns; i++)
			for (int j = 0; j < rows; j++)
				if (A.data[i][j] != B.data[i][j])
					return false;
		return true;
	}

	// return C = A * B
	// public MusicMatrix times(MusicMatrix B) {
	// MusicMatrix A = this;
	// if (A.N != B.M)
	// throw new RuntimeException("Illegal matrix dimensions.");
	// MusicMatrix C = new MusicMatrix(A.M, B.N);
	// for (int i = 0; i < C.M; i++)
	// for (int j = 0; j < C.N; j++)
	// for (int k = 0; k < A.N; k++)
	// C.data[i][j] += (A.data[i][k] * B.data[k][j]);
	// return C;
	// }

	// return x = A^-1 b, assuming A is square and has full rank
	// public MusicMatrix solve(MusicMatrix rhs) {
	// if (M != N || rhs.M != N || rhs.N != 1)
	// throw new RuntimeException("Illegal matrix dimensions.");
	//
	// // create copies of the data
	// MusicMatrix A = new MusicMatrix(this);
	// MusicMatrix b = new MusicMatrix(rhs);
	//
	// // Gaussian elimination with partial pivoting
	// for (int i = 0; i < N; i++) {
	//
	// // find pivot row and swap
	// int max = i;
	// for (int j = i + 1; j < N; j++)
	// if (Math.abs(A.data[j][i]) > Math.abs(A.data[max][i]))
	// max = j;
	// A.swap(i, max);
	// b.swap(i, max);
	//
	// // singular
	// if (A.data[i][i] == 0.0)
	// throw new RuntimeException("Matrix is singular.");
	//
	// // pivot within b
	// for (int j = i + 1; j < N; j++)
	// b.data[j][0] -= b.data[i][0] * A.data[j][i] / A.data[i][i];
	//
	// // pivot within A
	// for (int j = i + 1; j < N; j++) {
	// double m = A.data[j][i] / A.data[i][i];
	// for (int k = i + 1; k < N; k++) {
	// A.data[j][k] -= A.data[i][k] * m;
	// }
	// A.data[j][i] = 0.0;
	// }
	// }
	//
	// // back substitution
	// MusicMatrix x = new MusicMatrix(N, 1);
	// for (int j = N - 1; j >= 0; j--) {
	// double t = 0.0;
	// for (int k = j + 1; k < N; k++)
	// t += A.data[j][k] * x.data[k][0];
	// x.data[j][0] = (b.data[j][0] - t) / A.data[j][j];
	// }
	// return x;
	//
	// }

	// print matrix to standard output
	public void show() {
		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++)
				System.out.print(data[i][j].getNote() + " ");
			System.out.println();
		}
	}

	public void showColumn(int column) {
		for (int i = 0; i < columns; i++) {
			System.out.print(data[i][column].getNote() + " ");
		}
		System.out.println();
	}

	public Note[] getColumn(int column) {
		Note[] col = new Note[columns];
		for (int i = 0; i < columns; i++) {
			col[i] = data[i][column];
		}
		return col;
	}
	
	public int[] getColumnPitches(int column) {
		int[] col = new int[columns];
		for (int i = 0; i < columns; i++) {
			col[i] = data[i][column].getPitch();
		}
		return col;
	}
	
	public double[] getColumnPitches2(int column) {
		double[] col = new double[columns];
		for (int i = 0; i < columns; i++) {
			col[i] = data[i][column].getPitch();
		}
		return col;
	}
	
	public Set<Integer> getColumnSet(int column) {	
		Set<Integer> set = new TreeSet<Integer>();
		for (int i = 0; i < columns; i++) {
			set.add(data[i][column].getPitch());
		}
		return set;
	}

	public void setRhythmColumn(int column, double rhythm) {
		for (int i = 0; i < columns; i++) {
			data[i][column].setRhythmValue(rhythm);
			data[i][column].setDuration(rhythm);
		}
	}
	
	public void setRhythmRow(int row, double rhythm) {
		for (int i = 0; i < rows; i++) {
			data[i][row].setRhythmValue(rhythm);
			data[i][row].setDuration(rhythm);
		}
	}

	public Note[] getMelody(int row) {
		Note[] r = new Note[rows];
		for (int i = 0; i < rows; i++) {
			r[i] = data[row][i];
		}
		return r;
	}

	public void applyRhythmTemplateEven(){
		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++){
				Note note = data[i][j];
				if (note != null) {
					if(j % 2 == 0){	
						note.setDynamic(note.getDynamic() + RHYTHM_TEMPLATE_VALUE);
					}
					if(j % 4 == 0){	
						note.setDynamic(note.getDynamic() + RHYTHM_TEMPLATE_VALUE);
					}
				}
			}
		}
	}
	
	public void applyRhythmTemplateUneven(){
		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++){
				Note note = data[i][j];
				if(j % 3 == 0){	
					note.setDynamic(note.getDynamic() + RHYTHM_TEMPLATE_VALUE);
				}
//				if(j % 6 == 0){	
//					note.setDynamic(note.getDynamic() + RHYTHM_TEMPLATE_VALUE);
//				}
			}
		}
	}
	
	public List<Note[]> getChords() {
		List<Note[]> chords = new ArrayList<Note[]>();
		for (int i = 0; i < rows; i++) {
			Note[] chord = getColumn(i);
			chords.add(chord);
		}
		return chords;
	}
	
	public List<Set<Integer>> getChordsPitches() {
		List<Set<Integer>> chords = new ArrayList<Set<Integer>>();
		for (int i = 0; i < rows; i++) {
			Set<Integer> set = getColumnSet(i);
			chords.add(set);
		}
		return chords;
	}
	
	public List<Set<Integer>> getRelativeChords(){
		List<Set<Integer>> chords = new ArrayList<Set<Integer>>();
		for (int i = 0; i < rows; i++) {
			int[] chord = getColumnPitches(i);
			Arrays.sort(chord);
			Set<Integer> chordSet = getRelativeChord(chord);
			chords.add(chordSet);
		}
		return chords;
	}
	
	public double[] getVoiceLeadingTaxiCab(){
		List<Double> taxiCabList = new ArrayList<Double>();
		for (int i = 0; i < rows - 1; i++) {
			double[] chord = getColumnPitches2(i);
			double[] nextChord = getColumnPitches2(i + 1);
			double taxiCab = VoiceLeadingFunctions.taxiCab(chord, nextChord);
			taxiCabList.add(taxiCab);
		}
		List<Double> copyList = new ArrayList<Double>(taxiCabList);
		for (Double voiceLeadingValue : copyList) {
			if (voiceLeadingValue.equals(0.0)) {
				taxiCabList.remove(voiceLeadingValue);
			}	
		}
		Double[] taxiCabArray = new Double[taxiCabList.size()];
		taxiCabArray = taxiCabList.toArray(taxiCabArray);
		return ArrayUtils.toPrimitive(taxiCabArray);
	}

//	private Set<Integer> getRelativeChord(Note[] chord) {
//		Set<Integer> chordSet = new TreeSet<Integer>();
//		if (chord.length > 1) {
//			Note bassNote = chord[0];
//			for (int j = 1; j < chord.length; j++) {
//				Note note = chord[j];
//				int n = (note.getPitch() - bassNote.getPitch()) % 12;
////				if(n != 0){
//					chordSet.add(n);
////				}
//			}
//		}
//		return chordSet;
//	}
	
	private Set<Integer> getRelativeChord(int[] chord) {
		Set<Integer> chordSet = new TreeSet<Integer>();
		chordSet.add(0);
		if (chord.length > 1) {
			int bassNote = chord[0];
			for (int j = 1; j < chord.length; j++) {
				int note = chord[j];
				int n = (note - bassNote) % 12;
//				if(n != 0){
					chordSet.add(n);
//				}
			}
		}
		return chordSet;
	}
	
	public List<PitchSet> getPitchSets(){
		List<PitchSet> pitchSets = new ArrayList<PitchSet>();
		for (int i = 0; i < rows; i++) {
			int[] col = new int[columns];
			for (int j = 0; j < columns; j++) {
				col[j] = data[j][i].getPitch() % 12;	
			}
			PitchSet pitchSet = new PitchSet(col);
			pitchSets.add(pitchSet);
		}
		return pitchSets;
	}
	
	public List<IntervalVector> getIntervalVectors(){
		List<PitchSet> pitchSets =  getPitchSets();
		List<IntervalVector> intervalVectors = new ArrayList<IntervalVector>();
		for (PitchSet pitchSet : pitchSets) {
			 IntervalVector internalVector = new IntervalVector(pitchSet);
			 intervalVectors.add(internalVector);
		}
		return intervalVectors;
	}
	
	public List<Phrase> getMelodies() {
		List<Phrase> melodies = new ArrayList<Phrase>();
		for (int i = 0; i < columns; i++) {
			Note[] melody = getMelody(i);
			Phrase phrase = new Phrase(melody);
			melodies.add(phrase);
		}
		return melodies;
	}
	
	public void setInnerMetricDynamics(){
		for (int i = 0; i < columns; i++) {
			Note[] melody = getMelody(i);
			//create copy - notes refer to objects!!
			Note[] copyMelody = new Note[melody.length - 1];
			for (int j = 0; j < melody.length - 1; j++) {
				Note note = new Note(melody[j].getPitch(),melody[j].getRhythmValue());
				copyMelody[j] = note;
			}
			Phrase phrase = new Phrase(copyMelody);
			Mod.tiePitches(phrase);
			double[] pattern = phrase.getRhythmArray();
			Map<Integer, Double> map = InnerMetricWeight.getNormalizedInnerMetricWeight(pattern, 0.5);
			if (!map.isEmpty()) {
				for (int j = 0; j < melody.length - 1; j++) {
					if (map.containsKey(j)) {
						Double value = map.get(j);
						melody[j].setDynamic((int) (melody[j].getDynamic() + Math.round(value/INNER_METRIC_FACTOR)));
					}
				}
			}
		}
	}
	
	public double[] getContext(){
		double[] dynamics = getDynamics();
		double totalDynamic = 0;
		for (double dyn : dynamics) {
			totalDynamic = totalDynamic + dyn;
		}
		double[] context = new double[dynamics.length];
		for (int j = 0; j < dynamics.length; j++) {
			context[j] = dynamics[j] / totalDynamic;
		}
		return context;
	}
	
	public double[] getPattern(int row) {
		double[] r = new double[rows];
		for (int i = 0; i < rows; i++) {
			r[i] = data[row][i].getRhythmValue();
		}
		return r;
	}
	
	public double[] getDynamics() {
		double[] context = new double[rows];
		for (int i = 0; i < rows; i++) {
			double c = getColumnDynamics(i);
			context[i] = c;
		}
		return context;
	}
	
	public double getColumnDynamics(int column) {
		int sumDynamics = 0;
		for (int i = 0; i < columns; i++) {
			sumDynamics += data[i][column].getDynamic();
		}
		return sumDynamics / columns;
	}
	
	public Score musicMatrixScore(){
		Score score = new Score();
		for (int i = 0; i < columns; i++) {
			Note[] melody = getMelody(i);
			Phrase phrase = new Phrase(melody);
			Mod.tiePitches(phrase);
			Part part = new Part();
			part.add(phrase);
			score.add(part);
		}
		return score;
	}
	
	public static MusicMatrix createMusicMatric(Score score, double atomicValue, double length){	
		Part[] parts = score.getPartArray();
		if (score.getShortestRhythmValue() < atomicValue) {
			throw new IllegalArgumentException("Melody contains smaller value than atomic value: " + score.getShortestRhythmValue());
		}
		Note[][] matrix = new Note[parts.length][(int)(length/atomicValue)];
		for (int i = 0; i < parts.length; i++) {
			Phrase[] phrases = parts[i].getPhraseArray();
//			for (int i = 0; i < phrases.length; i++) {	
			Note[] melody = phrases[0].getNoteArray();
			double amountOfValues = 0;
			int count = 0;
			for (int j = 0; j < melody.length; j++) {
				amountOfValues = melody[j].getRhythmValue() / atomicValue;
				melody[j].setRhythmValue(atomicValue);
				melody[j].setDuration(atomicValue);
				matrix[i][count] = melody[j];
				count++;
				for (int k = 1; k < amountOfValues; k++) {
					Note note = new Note(melody[j].getPitch(),atomicValue, DEFAULT_DYNAMIC);//only the first note gets the dynamics
					matrix[i][count] = note;
					count++;
				}
			}		
		}
		return new MusicMatrix(matrix);
	}
	
	public static MusicMatrix createMusicMatric(Score score, double atomicValue){	
		Part[] parts = score.getPartArray();
		if (score.getShortestRhythmValue() < atomicValue) {
			throw new IllegalArgumentException("Melody contains smaller value than atomic value: " + score.getShortestRhythmValue());
		}
		double endTime = score.getEndTime();
		Note[][] matrix = new Note[parts.length][(int)(endTime/atomicValue)];
		for (int i = 0; i < parts.length; i++) {
			Phrase[] phrases = parts[i].getPhraseArray();
//			for (int i = 0; i < phrases.length; i++) {	
			Note[] melody = phrases[0].getNoteArray();
			double amountOfValues = 0;
			int count = 0;
			for (int j = 0; j < melody.length; j++) {
				amountOfValues = melody[j].getRhythmValue() / atomicValue;
				melody[j].setRhythmValue(atomicValue);
				melody[j].setDuration(atomicValue);
				matrix[i][count] = melody[j];
				count++;
				for (int k = 1; k < amountOfValues; k++) {
					Note note = new Note(melody[j].getPitch(),atomicValue, DEFAULT_DYNAMIC);//only the first note gets the dynamics
					matrix[i][count] = note;
					count++;
				}
			}		
		}
		return new MusicMatrix(matrix);
	}
		
	
	public static Note[] concatMelodyNotes(Note[] melody){
//		Note[] newMelody = new Note[melody.length];
//		int j = 0;
//		for (int i = 0; i < melody.length; i++) {
//			Note note = melody[i];
//			int nextIndex = i + 1;
//			while (nextIndex < melody.length && note.getPitch() == melody[nextIndex].getPitch()) {
//				note.setRhythmValue(note.getRhythmValue() + melody[nextIndex].getRhythmValue());
//				note.setDuration(note.getRhythmValue());
//				nextIndex++;
//				i++;
//			}
//			newMelody[j] = note;
//			j++;
//		}
//		return newMelody;
		Phrase phrase = new Phrase(melody);
		Mod.tiePitches(phrase);
		return phrase.getNoteArray();
	}
	
	public void setRhythmMatrix(double[] pattern){
		if (pattern.length == rows) {
			for (int j = 0; j < columns; j++) {
				for (int i = 0; i < rows; i++) {
					Note note = data[j][i];
					note.setRhythmValue(pattern[i]);
					note.setDuration(pattern[i]);
					data[j][i] = note;
				}	
			}
		}else{
			System.out.println("Pattern size is different from row count");
		}	
	}
	
	public void setDynamicMatrix(int[] dynamics){
		if (dynamics.length == rows) {
			for (int j = 0; j < columns; j++) {
				for (int i = 0; i < rows; i++) {
					Note note = data[j][i];
					note.setDynamic(dynamics[i]);
					data[j][i] = note;
				}	
			}
		}else{
			System.out.println("Pattern size is different from row count");
		}	
	}
	
	public List<Double> getHarmonicValues(List<Set<Integer>> ch){
//		List<Set<Integer>> ch = this.getRelativeChords();
		List<Double> harmonicValues = new ArrayList<Double>();
		for (Set<Integer> set : ch) {
			double harmonicValue = HarmonicFunctions.analyzechordNotesNoDoubles(set);
			harmonicValues.add(harmonicValue);
		}
		return harmonicValues;
	}
	
	public static int[][] generateMatrix(int melodies, int voices){
		int[][] matrix = new int[voices][melodies];
		Random random = new Random();
		Random randomChangePitch = new Random();
		int previousPitch = MAXIMUM_PITCH;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++){
				int pitch = 0;
				if (i == 0) {
					pitch= MAXIMUM_PITCH;
				} else {
					pitch = matrix[i-1][j];
				}
				int change = randomChangePitch.nextInt(TIED_NOTES_FACTOR);
				if (change == 1 && previousPitch < pitch) {
					matrix[i][j] = previousPitch;
				} else {
					int randInt = random.nextInt(12);
					pitch = pitch - randInt;
					matrix[i][j] = pitch;
					previousPitch = pitch;	
				}	
			}		
		}
		return matrix;
	}
	

	// test client
	public static void main(String[] args) {
//		int[][] matrix = generateMatrix(5, 4);
//		MusicMatrix D0 = new MusicMatrix(matrix);
		MusicMatrix D0 = new MusicMatrix(13, 4, Scale.MAJOR_SCALE, 1.0);
		D0.showColumn(1);
		System.out.println();
		D0.show();
		System.out.println();
		double[] pattern = {1.0,0.5,0.5,2.0,0.5};
		double[] rPattern = RhythmicFunctions.createRandomRhythmPattern(13);
		System.out.println("pattern " + Arrays.toString(rPattern));
		D0.setRhythmMatrix(rPattern);
		
		Score score = D0.musicMatrixScore();
		score.setTempo(60);
//		View.notate(score);
//		Play.midi(score);
		
//		int[][] d = { { 60, 62, 64, 62 }, { 65, 50, 61, 58 }, { 58, 50, 72 ,54},{ 64, 64, 68 ,67} };
//		int[][] d = { { 60, 62, 64, 62 }, { 56, 57, 50, 58 } };
//		MusicMatrix D = new MusicMatrix(d);
//		Note[] col = D.getColumn(2);
//
//		D.showColumn(1);
//		System.out.println();
//		D.show();
//		System.out.println();
//		D.setRhythmColumn(1, 2.0);
		Note[] melody = D0.getMelody(0);
		List<Note[]> chords = D0.getChords();
		
//		List<Set<Integer>> ch = D.getRelativeChords();
//		List<Double> harmonicValues = new ArrayList<Double>();
//		for (Set<Integer> set : ch) {
//			double harmonicValue = HarmonicFunctions.analyzechordNotesNoDoubles(set);
//			harmonicValues.add(harmonicValue);
//		}

		List<Double> harmonicValues = D0.getHarmonicValues(D0.getRelativeChords());
		System.out.println(harmonicValues);
		List<Double> harmonicFluctuationDelta = HarmonicFunctions.harmonicFluctuationDelta(harmonicValues);
		List<Integer> harmonicFluctuationContour = HarmonicFunctions.harmonicFluctuationContour(harmonicValues);
		System.out.println(harmonicFluctuationDelta);
		System.out.println(harmonicFluctuationContour);
		List<Phrase> melodies = D0.getMelodies();
		
//		double[] rhythmPattern = {1.0,1.0,1.0,1.0};
//		double[] rhythmPattern2 = {1.0,0.5,0.5,2.0};
//		
//		D.setRhythmMatrix(rhythmPattern2);
		
//		Score score = D.musicMatrixScore();
//		score.setTempo(60);
//		View.notate(score);
//		Play.midi(score);
		
//		Note[] notes = { new Note(49,1.0),new Note(50,1.0), new Note(50,2.0), new Note(51,1.0), new Note(51,1.0), new Note(51,1.5), new Note(49,1.0)};
//		Note[] newMelody = concatMelodyNotes(notes);
//		Phrase phrase = new Phrase(newMelody);
////		Mod.tiePitches(phrase);
//		System.out.println(phrase);
		
		List<Set<Integer>> relativeChords2 = D0.getRelativeChords();
		for (Set<Integer> set : relativeChords2) {
			System.out.println(set);
			Iterator<Integer> it = set.iterator();
			int[] notesSet = new int[set.size()];
			int i = 0;
			while (it.hasNext()) {
				Integer integer = (Integer) it.next();
				notesSet[i] = integer;
				i++;
			}
			PitchSet pitchSet = new PitchSet(notesSet);
			System.out.println("pitch set: " + pitchSet.toKeys());
		    IntervalVector internalVector = new IntervalVector(pitchSet);
		    System.out.println("interval vector: " +internalVector.toString());
		    double val = HarmonicFunctions.calculateHarmonicValue(internalVector);
			System.out.println("intervalVector value: " + val);
		}

	}
	

}
