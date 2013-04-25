package be.set;

/************************************************************************************************************\
 *
 *   PitchSet
 *   
 *   This class represents a set of pitches from 0 through 11, 
 *   including methods for transposition, inversion, complement,
 *   normal form and prime form calculation, and interval matrix
 *   calculation. Computers are just so much better for this kind
 *   of stuff. Here's a rundown of the methods:
 *
 *   PitchSet getComplement(); returns a PitchSet consisting of all pitches not in this one
 *   PitchSet transpose(int t); moves all notes up by t mod 12 and returns the new PitchSet
 *   PitchSet invert(); returns a mirror-image-on-the-clockface PitchSet (subtracts all pitches from 12)
 *   PitchSet TnI(int t); First inverts, then transposes by t
 *   String toString; returns a comma-separated list of integers representing my pitches
 *   String toKeys(); returns a comma-separated list of notes (e.g. C#, Eb...) representing my pitches
 *   PitchSet getNormalForm(); finds the rotation which packs the set most tightly to the left
 *   PitchSet getPrimeForm(); finds the 0-based most normal form between this set and its inversion
 *
\***********************************************************************************************************/

import java.util.*;

public class PitchSet {

    // Declare class variables
    public int[] noteArray; // ordered array of integers from 0 to 11

    PitchSet() { // no-args constructor
        int[] vienna = {0, 1, 6};
        this.noteArray = vienna; // Viennese trichord by default (^_^)
    } 

    public PitchSet(int[] noteArray) { // called with an array of integer notes
        this.noteArray = noteArray;
	checkIntegrity();
    }

    PitchSet(String arg) { // called with a String of comma-separated integers
    	// first remove all whitespace
    	StringTokenizer removeSpaces = new StringTokenizer(arg);
    	String spaceFree = new String("");
    	while (removeSpaces.hasMoreTokens()) { spaceFree += removeSpaces.nextToken(); }

    	// Now split the spaceFree string into tokens
    	String delim = new String(",");
    	StringTokenizer integerTokens = new StringTokenizer(spaceFree, delim, false); 
    	int[] integersPassed = new int[integerTokens.countTokens()]; 
    	int i=0;

    	while(integerTokens.hasMoreTokens()) { 
    	    try { String item = integerTokens.nextToken().toString();
    	          try { integersPassed[i] = modTwelve(new Integer(item).intValue()); }
	    	       catch (NumberFormatException n) {;}
    	          i++; } 
    	     catch (NoSuchElementException e) {;}
    	    } // ends while()
    	this.noteArray = integersPassed;
	checkIntegrity();
    }

    public PitchSet getComplement() { // returns a set containing all notes that this one lacks
        int k = 0; // index for the complement set
        int[] complement = new int[12 - this.noteArray.length];
        iloop:
        for(int i=0; i < 12; i++) { // we'll check every note from 0 to 11 for membership
            jloop:
            for (int j=0; j < this.noteArray.length; j++) {
                if (noteArray[j] == i) continue iloop;
            } 
            complement[k] = i; //  these lines executes only when i was not in noteArray
            k++;
        }
        PitchSet complementSet = new PitchSet(complement);
        return complementSet;
    } // ends getComplement()
    
    public PitchSet transpose(int x) { // performs mod-12 addition by x on the set   
        x = modTwelve(x); // just in case we need to transpose by -239 or something weird
        // Now 0 < x < 12. Add x to all elements of noteArray
        int[] transposed = new int[this.noteArray.length];
        for (int i=0; i < this.noteArray.length; i++) {
            transposed[i] = modTwelve(noteArray[i] + x);
        }
        PitchSet Trans = new PitchSet(transposed);
        return Trans;
    } // ends transpose()

    
    public PitchSet invert() { // returns the inversion of this set about 0
        int[] inverted = new int[this.noteArray.length];
        for (int i=0; i < this.noteArray.length; i++) {
            if (this.noteArray[i] != 0) { inverted[i] = (12 - this.noteArray[i]); }
        }
        PitchSet Inv = new PitchSet(inverted);
        return Inv;
    } // ends invert()

    public String toString() { // returns a String of the (numeric) members
        String s = new String();
        for(int i=0; i < this.noteArray.length; i++) {
            if (i == (this.noteArray.length - 1)) {
                s += new Integer(noteArray[i]).toString();
            } else {
                s += new Integer(noteArray[i]).toString(); s += new String(", ");
            } // ends if
        }
        return s;
    } // ends toString()

    public String toKeys() { // returns an array of musical notes instead of numbers
        String[] keyArray = {"C", "C#", "D", "Eb", "E", "F", "F#", "G", "Ab", "A", "Bb", "B"};
        String s = new String();
        for(int i=0; i < this.noteArray.length; i++) {
            if (i == (this.noteArray.length - 1)) {
                s += keyArray[noteArray[i]];
            } else {
                s += keyArray[noteArray[i]]; s += new String(", ");
            } // ends if
        }
        return s;
    } // ends toKeys()

    public PitchSet TnI(int t) {
        PitchSet Inverted = this.invert();
        PitchSet Transposed = Inverted.transpose(t);
        return Transposed;
    } // ends TnI()
    
    public int modTwelve(int i) {
        if (i >= 0 && i < 12) { 
            return i; 
        } else {
            i = i%12; // the integer remainder after division by 12, which might be negative 
            if (i < 0) i += 12;
            return i;
        }
    } // ends modTwelve()
    
    public int sum(PitchSet PS) { // calculates the sum of all the note values
        int l = PS.noteArray.length;
        int SUM = 0;
        for( int i=0; i<l; i++) { SUM += PS.noteArray[i]; }
        return SUM;
    }

    public PitchSet getNormalForm() {
        // I'm calculating the normal form by first sorting & transposing the set to start on zero,
        // and then looking at the last note. The smaller that note is, the more "packed
        // to the left" the set is. I'll get such a note for every possible rotation, and the
        // smallest note wins. If there's a tie, such as for sets like (0,3,6,9), we'll just
        // start with whichever set member was originally closest to 0.

        int l = this.noteArray.length;
        int check, sigma, rightside, pitchvalue, transum, tau;
        pitchvalue = 0; rightside=0;
        PitchSet Transposed;
	PitchSet sorted = this.sort();
        sigma = 1000; // maintained as the smallest noteArray[l-1] of any rotation.
	tau = 1000; // maintained as the smallest sum of Transposed 
                         
        for (int i = 0; i < l; i++) {
            Transposed = sorted.transpose(-sorted.noteArray[i]);
	    Transposed = Transposed.rotate(i);
            check = Transposed.noteArray[l-1];
	    transum = sum(Transposed);
            if (check < sigma) { 
		sigma = check; rightside = i; 
		pitchvalue = sorted.noteArray[i]; tau = transum; 
	    }
            else if (check == sigma && transum < tau) { 
		rightside = i; pitchvalue = sorted.noteArray[i];
		tau = transum; 
	    }
            else if (check == sigma && transum == tau && pitchvalue > sorted.noteArray[i]) {
		rightside = i; pitchvalue = sorted.noteArray[i];
	    }
        }
        PitchSet Normalized = sorted.rotate(rightside); 
        return Normalized;
    } // ends getNormalForm()
    
    public PitchSet getPrimeForm() {
        PitchSet Normalized, invNormalized, AnswerSet;
        Normalized = this.getNormalForm();
        invNormalized = this.invert().getNormalForm();
        Normalized = Normalized.transpose(-Normalized.noteArray[0]);
        invNormalized = invNormalized.transpose(-invNormalized.noteArray[0]);
        if ( sum(Normalized) < sum(invNormalized)) {
            AnswerSet = Normalized.sort();
        } else {
            AnswerSet = invNormalized.sort();
        }
	// Special set classes:
	// Forte  Prime form packed    Prime form packed
	// name   from the right       to the left     
	// ----------------------------------------------
	// 5-20      (01568)           (01378) 
	// 6-Z29     (023679)          (013689) 
	// 6-31      (014579)          (013589) 
	// 7-20      (0125679)         (0124789) 
	// 8-26      (0134578T)        (0124579T) 

	// 5-20
	if (AnswerSet.toString().equals("0, 1, 3, 7, 8")) {
		AnswerSet = new PitchSet("0,1,5,6,8");
	}
	// 6-Z29
	if (AnswerSet.toString().equals("0, 1, 3, 6, 8, 9")) {
		AnswerSet = new PitchSet("0,2,3,6,7,9");
	}
	// 6-31
	if (AnswerSet.toString().equals("0, 1, 3, 5, 8, 9")) {
		AnswerSet = new PitchSet("0,1,4,5,7,9");
	}
	// 7-20
	if (AnswerSet.toString().equals("0, 1, 2, 4, 7, 8, 9")) {
		AnswerSet = new PitchSet("0,1,2,5,6,7,9");
	}
	// 8-26
	if (AnswerSet.toString().equals("0, 1, 2, 4, 5, 7, 9, 10")) {
		AnswerSet = new PitchSet("0,1,3,4,5,7,8,10");
	}
	

	return AnswerSet;
    } // ends getPrimeForm()
    
    public PitchSet rotate(int x) { 
        // rearrange the order of the set by shifting all pitches x places to the left
        int l = this.noteArray.length; // the real number of pitches
        // adjust x so that 0 <= x < l
        if (x < 0 || x >= l) {
            x = x % l;
            if ( x < 0 ) x += l;
        }
        // create a new array of integers to populate
        int[] rotated = new int[l];
        for (int i=0; i<l; i++) {
            if ((i+x) >= l) x -= l;
            rotated[i] = noteArray[i+x];
        }
        PitchSet Rotated = new PitchSet(rotated);
        return Rotated;
    } // ends rotate()
    
    public PitchSet sort() {
        int l = this.noteArray.length;
        int[] sorted = new int[l];
        int k=0;
        for (int i=0; i < 12; i++) {
                for (int j=0; j < l; j++) {
                        if (this.noteArray[j] == i) {
                                sorted[k] = i;
                                k++;
                        }
                }
        }
        PitchSet Sorted = new PitchSet(sorted);
        return Sorted;
    } // ends sort()

    void checkIntegrity() { 				// this routine eliminates any double entries 
	int l = this.noteArray.length;			// that may have been entered by the user, so
	int[] Twelve = {0,0,0,0,0,0,0,0,0,0,0,0};	// we don't allow sets like 0,2,3,2
	int[] buffer = new int[12]; // a placeholder for the "clean" set, sans duplicates
	int count = 0;	// keeps track of the number of unique members
	for (int i=0; i < l; i++) {				// for each note in the dirty set
		if (Twelve[this.noteArray[i]] == 0) { 	// if this is the first occurence
			Twelve[this.noteArray[i]] = 1;	// turn its flag on in Twelve[]
			buffer[count] = this.noteArray[i];
			count++; 
		} // ends if
	} // ends for
	int[] scrubbed = new int[count];
	for (int j=0; j<count; j++) { scrubbed[j] = buffer[j]; }
	this.noteArray = scrubbed;
    }

    public boolean contains(int c) { // check to see if c is in noteArray[]
	int l = this.noteArray.length;
	boolean answer = false;
	for(int i = 0; i < l; i++) {
	    if (this.noteArray[i] == c) { answer = true; }
	}
	return answer;
    }


} /////////////////////////////////////////////// ends class PitchSet

