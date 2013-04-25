package be.set;

/*******************************************************************************\
 *
 * This class keeps track of a row of 6 integers, representing the
 * number of times interval classes 1 through 6 appear in a given 
 * PitchSet PS. The vector is calculated by finding the interval between
 * every possible pairing of pitches in the given PitchSet. Each time an 
 * interval class is counted, its corresponding tally in the IntervalVector
 * is incremented. 
 *
\*******************************************************************************/




public class IntervalVector {
	
	// Class variable
	public int[] intervalVector;
	
	IntervalVector() {
		int iv[] = {0,0,0,0,0,0}; // zeros by default
		this.intervalVector = iv;
	} // ends no-args constructor
	
	public IntervalVector(PitchSet PS) {
		int i, j, k;
		int iv[] = {0,0,0,0,0,0};
		this.intervalVector = iv; // start with zeros
		int l = PS.noteArray.length; // l is the number of notes in the PitchSet
		for (i=0; i < l-1; i++) { // from the first to the penultimate
			for (j=i+1; j < l; j++) { // from the one in front of i to the last
				k = modTwelve(PS.noteArray[j] - PS.noteArray[i]); // calculate this interval
				if (k > 6) k = 12 - k; // modify for interval class equivalence (7=5, etc...)
				this.intervalVector[k-1]++; // note the offset--IC3's are tallied in intervalVector[2]...
			} // ends for(j)
		} // ends for(i)
	} // ends constructor

	public String toString() {
		String s = new String();
        for(int i=0; i < this.intervalVector.length; i++) {
            if (i == (this.intervalVector.length - 1)) {
                s += new Integer(this.intervalVector[i]).toString();
            } else {
                s += new Integer(this.intervalVector[i]).toString(); s += new String(", ");
            } // ends if
        } // ends for(i)
        return "<" + s + ">";
	} // ends toString()
	
    public int modTwelve(int i) {
        if (i >= 0 && i < 12) { 
            return i; 
        } else {
            i = i%12; // the integer remainder after division by 12, which might be negative 
            if (i < 0) i += 12;
            return i;
        }
    } // ends modTwelve()

} // ends class IntervalVector()
