package be.set;

import java.util.*;

public class ForteNumber {

    private String primeString;

    public ForteNumber(PitchSet PS) {
		PitchSet prime = PS.getPrimeForm();
		String s = new String();
		int l = prime.noteArray.length;
		String comma = new String(",");
	
		// We need a list of numbers separated by commas with no spaces
		for (int i = 0; i < l; i++) {
			if (i == (l-1)) { 
			    s += new Integer(prime.noteArray[i]).toString();
			} else {
			    s += new Integer(prime.noteArray[i]).toString();
			    s += comma;
			} // ends if
		}
	
		if (PS.noteArray.length > 2 && PS.noteArray.length < 10) {
			primeString = s;
		} else { primeString = new String("None"); }
    } 

    public String toString() {
		Hashtable<String, String> fortehash = new Hashtable<String, String>(250);
	
		fortehash.put(new String("0,1,2"), new String("3-1"));
		fortehash.put(new String("0,1,3"), new String("3-2"));
		fortehash.put(new String("0,1,4"), new String("3-3"));
		fortehash.put(new String("0,1,5"), new String("3-4"));
		fortehash.put(new String("0,1,6"), new String("3-5"));
		fortehash.put(new String("0,2,4"), new String("3-6"));
		fortehash.put(new String("0,2,5"), new String("3-7"));
		fortehash.put(new String("0,2,6"), new String("3-8"));
		fortehash.put(new String("0,2,7"), new String("3-9"));
		fortehash.put(new String("0,3,6"), new String("3-10"));
		fortehash.put(new String("0,3,7"), new String("3-11"));
		fortehash.put(new String("0,4,8"), new String("3-12"));
		fortehash.put(new String("0,1,2,3"), new String("4-1"));
		fortehash.put(new String("0,1,2,4"), new String("4-2"));
		fortehash.put(new String("0,1,2,5"), new String("4-4"));
		fortehash.put(new String("0,1,2,6"), new String("4-5"));
		fortehash.put(new String("0,1,2,7"), new String("4-6"));
		fortehash.put(new String("0,1,3,4"), new String("4-3"));
		fortehash.put(new String("0,1,3,5"), new String("4-11"));
		fortehash.put(new String("0,1,3,6"), new String("4-13"));
		fortehash.put(new String("0,1,3,7"), new String("4-Z29"));
		fortehash.put(new String("0,1,4,5"), new String("4-7"));
		fortehash.put(new String("0,1,4,6"), new String("4-Z15"));
		fortehash.put(new String("0,1,4,7"), new String("4-18"));
		fortehash.put(new String("0,1,4,8"), new String("4-19"));
		fortehash.put(new String("0,1,5,6"), new String("4-8"));
		fortehash.put(new String("0,1,5,7"), new String("4-16"));
		fortehash.put(new String("0,1,5,8"), new String("4-20"));
		fortehash.put(new String("0,1,6,7"), new String("4-9"));
		fortehash.put(new String("0,2,3,5"), new String("4-10"));
		fortehash.put(new String("0,2,3,6"), new String("4-12"));
		fortehash.put(new String("0,2,3,7"), new String("4-14"));
		fortehash.put(new String("0,2,4,6"), new String("4-21"));
		fortehash.put(new String("0,2,4,7"), new String("4-22"));
		fortehash.put(new String("0,2,4,8"), new String("4-24"));
		fortehash.put(new String("0,2,5,7"), new String("4-23"));
		fortehash.put(new String("0,2,5,8"), new String("4-27"));
		fortehash.put(new String("0,2,6,8"), new String("4-25"));
		fortehash.put(new String("0,3,4,7"), new String("4-17"));
		fortehash.put(new String("0,3,5,8"), new String("4-26"));
		fortehash.put(new String("0,3,6,9"), new String("4-28"));
		fortehash.put(new String("0,1,2,3,4"), new String("5-1"));
		fortehash.put(new String("0,1,2,3,5"), new String("5-2"));
		fortehash.put(new String("0,1,2,3,6"), new String("5-4"));
		fortehash.put(new String("0,1,2,3,7"), new String("5-5"));
		fortehash.put(new String("0,1,2,4,5"), new String("5-5"));
		fortehash.put(new String("0,1,2,4,5"), new String("5-3"));
		fortehash.put(new String("0,1,2,4,6"), new String("5-9"));
		fortehash.put(new String("0,1,2,4,7"), new String("5-Z36"));
		fortehash.put(new String("0,1,2,4,8"), new String("5-13"));
		fortehash.put(new String("0,1,2,5,6"), new String("5-6"));
		fortehash.put(new String("0,1,2,5,7"), new String("5-14"));
		fortehash.put(new String("0,1,2,5,8"), new String("5-Z38"));
		fortehash.put(new String("0,1,2,6,7"), new String("5-7"));
		fortehash.put(new String("0,1,2,6,8"), new String("5-15"));
		fortehash.put(new String("0,1,3,4,6"), new String("5-10"));
		fortehash.put(new String("0,1,3,4,7"), new String("5-16"));
		fortehash.put(new String("0,1,3,4,8"), new String("5-Z17"));
		fortehash.put(new String("0,1,3,5,6"), new String("5-Z12"));
		fortehash.put(new String("0,1,3,5,7"), new String("5-24"));
		fortehash.put(new String("0,1,3,5,8"), new String("5-27"));
		fortehash.put(new String("0,1,3,6,7"), new String("5-19"));
		fortehash.put(new String("0,1,3,6,8"), new String("5-29"));
		fortehash.put(new String("0,1,3,6,9"), new String("5-31"));
		fortehash.put(new String("0,1,4,5,7"), new String("5-Z18"));
		fortehash.put(new String("0,1,4,5,8"), new String("5-21"));
		fortehash.put(new String("0,1,4,6,8"), new String("5-30"));
		fortehash.put(new String("0,1,4,6,9"), new String("5-32"));
		fortehash.put(new String("0,1,4,7,8"), new String("5-22"));
		fortehash.put(new String("0,1,5,6,8"), new String("5-20"));
		fortehash.put(new String("0,2,3,4,6"), new String("5-8"));
		fortehash.put(new String("0,2,3,4,7"), new String("5-11"));
		fortehash.put(new String("0,2,3,5,7"), new String("5-23"));
		fortehash.put(new String("0,2,3,5,8"), new String("5-25"));
		fortehash.put(new String("0,2,3,6,8"), new String("5-28"));
		fortehash.put(new String("0,2,4,5,8"), new String("5-26"));
		fortehash.put(new String("0,2,4,6,8"), new String("5-33"));
		fortehash.put(new String("0,2,4,6,9"), new String("5-34"));
		fortehash.put(new String("0,2,4,7,9"), new String("5-35"));
		fortehash.put(new String("0,3,4,5,8"), new String("5-Z37"));
		fortehash.put(new String("0,1,2,3,4,5"), new String("6-1"));
		fortehash.put(new String("0,1,2,3,4,6"), new String("6-2"));
		fortehash.put(new String("0,1,2,3,4,7"), new String("6-Z36"));
		fortehash.put(new String("0,1,2,3,4,8"), new String("6-Z37"));
		fortehash.put(new String("0,1,2,3,5,7"), new String("6-9"));
		fortehash.put(new String("0,1,2,3,5,8"), new String("6-Z40"));
		fortehash.put(new String("0,1,2,3,6,7"), new String("6-5"));
		fortehash.put(new String("0,1,2,3,6,8"), new String("6-Z41"));
		fortehash.put(new String("0,1,2,3,6,9"), new String("6-Z42"));
		fortehash.put(new String("0,1,2,3,7,8"), new String("6-Z38"));
		fortehash.put(new String("0,1,2,4,5,8"), new String("6-15"));
		fortehash.put(new String("0,1,2,4,6,8"), new String("6-22"));
		fortehash.put(new String("0,1,2,4,6,9"), new String("6-Z46"));
		fortehash.put(new String("0,1,2,4,7,8"), new String("6-Z17"));
		fortehash.put(new String("0,1,2,4,7,9"), new String("6-Z47"));
		fortehash.put(new String("0,1,2,5,6,9"), new String("6-Z44"));
		fortehash.put(new String("0,1,2,5,7,8"), new String("6-18"));
		fortehash.put(new String("0,1,2,5,7,9"), new String("6-Z48"));
		fortehash.put(new String("0,1,2,6,7,8"), new String("6-7"));
		fortehash.put(new String("0,1,3,4,5,7"), new String("6-Z10"));
		fortehash.put(new String("0,1,3,4,5,8"), new String("6-14"));
		fortehash.put(new String("0,1,3,4,6,9"), new String("6-27"));
		fortehash.put(new String("0,1,3,4,7,9"), new String("6-Z49"));
		fortehash.put(new String("0,1,3,5,7,9"), new String("6-34"));
		fortehash.put(new String("0,1,4,5,7,9"), new String("6-31"));
		fortehash.put(new String("0,1,3,6,7,9"), new String("6-30"));
		fortehash.put(new String("0,2,3,6,7,9"), new String("6-Z29"));
		fortehash.put(new String("0,1,4,5,6,8"), new String("6-16"));
		fortehash.put(new String("0,1,4,5,8,9"), new String("6-20"));
		fortehash.put(new String("0,2,3,4,5,7"), new String("6-8"));
		fortehash.put(new String("0,2,3,4,6,8"), new String("6-21"));
		fortehash.put(new String("0,2,3,4,6,9"), new String("6-Z45"));
		fortehash.put(new String("0,2,3,5,7,9"), new String("6-33"));
		fortehash.put(new String("0,2,4,5,7,9"), new String("6-32"));
		fortehash.put(new String("0,2,4,6,8,10"), new String("6-35"));
		fortehash.put(new String("0,1,2,3,5,6"), new String("6-Z3"));
		fortehash.put(new String("0,1,2,4,5,6"), new String("6-Z4"));
		fortehash.put(new String("0,1,2,4,5,7"), new String("6-Z11"));
		fortehash.put(new String("0,1,2,4,6,7"), new String("6-Z12"));
		fortehash.put(new String("0,1,3,4,6,7"), new String("6-Z13"));
		fortehash.put(new String("0,1,2,5,6,7"), new String("6-Z6"));
		fortehash.put(new String("0,1,3,4,6,8"), new String("6-Z24"));
		fortehash.put(new String("0,1,2,5,6,8"), new String("6-Z43"));
		fortehash.put(new String("0,1,3,5,6,8"), new String("6-Z25"));
		fortehash.put(new String("0,1,3,4,7,8"), new String("6-Z19"));
		fortehash.put(new String("0,1,3,5,7,8"), new String("6-Z26"));
		fortehash.put(new String("0,2,3,4,5,8"), new String("6-Z39"));
		fortehash.put(new String("0,1,3,5,6,9"), new String("6-Z28"));
		fortehash.put(new String("0,1,4,6,7,9"), new String("6-Z50"));
		fortehash.put(new String("0,2,3,5,6,8"), new String("6-Z23"));
		fortehash.put(new String("0,1,2,3,4,5,6,7,8"), new String("9-1"));
		fortehash.put(new String("0,1,2,3,4,5,6,7,9"), new String("9-2"));
		fortehash.put(new String("0,1,2,3,4,5,6,8,9"), new String("9-3"));
		fortehash.put(new String("0,1,2,3,4,5,7,8,9"), new String("9-4"));
		fortehash.put(new String("0,1,2,3,4,6,7,8,9"), new String("9-5"));
		fortehash.put(new String("0,1,2,3,4,5,6,8,10"), new String("9-6"));
		fortehash.put(new String("0,1,2,3,4,5,7,8,10"), new String("9-7"));
		fortehash.put(new String("0,1,2,3,4,6,7,8,10"), new String("9-8"));
		fortehash.put(new String("0,1,2,3,5,6,7,8,10"), new String("9-9"));
		fortehash.put(new String("0,1,2,3,4,6,7,9,10"), new String("9-10"));
		fortehash.put(new String("0,1,2,3,5,6,7,9,10"), new String("9-11"));
		fortehash.put(new String("0,1,2,4,5,6,8,9,10"), new String("9-12"));
		fortehash.put(new String("0,1,2,3,4,5,6,7"), new String("8-1"));
		fortehash.put(new String("0,1,2,3,4,5,6,8"), new String("8-2"));
		fortehash.put(new String("0,1,2,3,4,5,7,8"), new String("8-4"));
		fortehash.put(new String("0,1,2,3,4,6,7,8"), new String("8-5"));
		fortehash.put(new String("0,1,2,3,5,6,7,8"), new String("8-6"));
		fortehash.put(new String("0,1,2,3,4,5,6,9"), new String("8-3"));
		fortehash.put(new String("0,1,2,3,4,5,7,9"), new String("8-11"));
		fortehash.put(new String("0,1,2,3,4,6,7,9"), new String("8-13"));
		fortehash.put(new String("0,1,2,3,5,6,7,9"), new String("8-Z29"));
		fortehash.put(new String("0,1,2,3,4,5,8,9"), new String("8-7"));
		fortehash.put(new String("0,1,2,3,4,6,8,9"), new String("8-Z15"));
		fortehash.put(new String("0,1,2,3,5,6,8,9"), new String("8-18"));
		fortehash.put(new String("0,1,2,4,5,6,8,9"), new String("8-19"));
		fortehash.put(new String("0,1,2,3,4,7,8,9"), new String("8-8"));
		fortehash.put(new String("0,1,2,3,5,7,8,9"), new String("8-16"));
		fortehash.put(new String("0,1,2,4,5,7,8,9"), new String("8-20"));
		fortehash.put(new String("0,1,2,3,6,7,8,9"), new String("8-9"));
		fortehash.put(new String("0,2,3,4,5,6,7,9"), new String("8-10"));
		fortehash.put(new String("0,1,3,4,5,6,7,9"), new String("8-12"));
		fortehash.put(new String("0,1,2,4,5,6,7,9"), new String("8-14"));
		fortehash.put(new String("0,1,2,3,4,6,8,10"), new String("8-21"));
		fortehash.put(new String("0,1,2,3,5,6,8,10"), new String("8-22"));
		fortehash.put(new String("0,1,2,4,5,6,8,10"), new String("8-24"));
		fortehash.put(new String("0,1,2,3,5,7,8,10"), new String("8-23"));
		fortehash.put(new String("0,1,2,4,5,7,8,10"), new String("8-27"));
		fortehash.put(new String("0,1,2,4,6,7,8,10"), new String("8-25"));
		fortehash.put(new String("0,1,3,4,5,6,8,9"), new String("8-17"));
		fortehash.put(new String("0,1,3,4,5,7,8,10"), new String("8-26"));
		fortehash.put(new String("0,1,3,4,6,7,9,10"), new String("8-28"));
		fortehash.put(new String("0,1,2,3,4,5,6"), new String("7-1"));
		fortehash.put(new String("0,1,2,3,4,5,7"), new String("7-2"));
		fortehash.put(new String("0,1,2,3,4,6,7"), new String("7-4"));
		fortehash.put(new String("0,1,2,3,5,6,7"), new String("7-5"));
		fortehash.put(new String("0,1,2,3,4,5,8"), new String("7-5"));
		fortehash.put(new String("0,1,2,3,4,5,8"), new String("7-3"));
		fortehash.put(new String("0,1,2,3,4,6,8"), new String("7-9"));
		fortehash.put(new String("0,1,2,3,5,6,8"), new String("7-Z36"));
		fortehash.put(new String("0,1,2,4,5,6,8"), new String("7-13"));
		fortehash.put(new String("0,1,2,3,4,7,8"), new String("7-6"));
		fortehash.put(new String("0,1,2,3,5,7,8"), new String("7-14"));
		fortehash.put(new String("0,1,2,4,5,7,8"), new String("7-Z38"));
		fortehash.put(new String("0,1,2,3,6,7,8"), new String("7-7"));
		fortehash.put(new String("0,1,2,4,6,7,8"), new String("7-15"));
		fortehash.put(new String("0,1,2,3,4,6,9"), new String("7-10"));
		fortehash.put(new String("0,1,2,3,5,6,9"), new String("7-16"));
		fortehash.put(new String("0,1,2,4,5,6,9"), new String("7-Z17"));
		fortehash.put(new String("0,1,2,3,4,7,9"), new String("7-Z12"));
		fortehash.put(new String("0,1,2,3,5,7,9"), new String("7-24"));
		fortehash.put(new String("0,1,2,4,5,7,9"), new String("7-27"));
		fortehash.put(new String("0,1,2,3,6,7,9"), new String("7-19"));
		fortehash.put(new String("0,1,2,4,6,7,9"), new String("7-29"));
		fortehash.put(new String("0,1,3,4,6,7,9"), new String("7-31"));
		fortehash.put(new String("0,1,2,3,6,7,10"), new String("7-Z18"));
		fortehash.put(new String("0,1,2,4,5,8,9"), new String("7-21"));
		fortehash.put(new String("0,1,2,4,6,8,9"), new String("7-30"));
		fortehash.put(new String("0,1,3,4,6,8,9"), new String("7-32"));
		fortehash.put(new String("0,1,3,4,7,8,9"), new String("7-22"));
		fortehash.put(new String("0,1,2,5,6,7,9"), new String("7-20"));
		fortehash.put(new String("0,2,3,4,5,6,8"), new String("7-8"));
		fortehash.put(new String("0,1,3,4,5,6,8"), new String("7-11"));
		fortehash.put(new String("0,2,3,4,5,7,9"), new String("7-23"));
		fortehash.put(new String("0,2,3,4,6,7,9"), new String("7-25"));
		fortehash.put(new String("0,1,3,5,6,7,9"), new String("7-28"));
		fortehash.put(new String("0,1,3,4,5,7,9"), new String("7-26"));
		fortehash.put(new String("0,1,3,5,7,9,11"), new String("7-33"));
		fortehash.put(new String("0,1,3,5,7,9,10"), new String("7-34"));
		fortehash.put(new String("0,1,3,5,6,8,10"), new String("7-35"));
		fortehash.put(new String("0,1,3,4,5,7,8"), new String("7-Z37"));
		fortehash.put(new String("None"), new String("N/A"));

		String f = (String)fortehash.get(primeString);
		return f;
    } 

} 
