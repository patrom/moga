package be.util;

public class Frequency 
{

    private static final double PITCH_OF_A4 = 57D;
    private static final double FACTOR = 12D / Math.log(2D);
    public static final String NOTE_SYMBOL[] = {
        "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A",
        "A#", "B"
    };
    public static float frequencyOfA4 = 440F;
    private float frequency;

    public static final double getPitch(float f)
    {
        return getPitch(f);
    }

    public static final double getPitch(double d)
    {
        return 57D + FACTOR * Math.log(d / (double)frequencyOfA4);
    }

    public static final float getFrequency(double d)
    {
        return (float)((Math.exp((d - PITCH_OF_A4) / FACTOR) * (double)frequencyOfA4))/2;
    }

    public static final String makeNoteSymbol(double d)
    {
        int i = (int)(d + 120.5D);
        StringBuffer stringbuffer = new StringBuffer(NOTE_SYMBOL[i % 12]);
        stringbuffer.append(Integer.toString(i / 12 - 10));
        return new String(stringbuffer);
    }

    public static float valueOf(String s)
        throws IllegalArgumentException
    {
        try
        {
            return (new Float(s)).floatValue();
        }
        catch(NumberFormatException _ex) { }
        try
        {
            return getFrequency(parseNoteSymbol(s));
        }
        catch(IllegalArgumentException _ex)
        {
            throw new IllegalArgumentException("Neither a floating point number nor a valid note symbol.");
        }
    }

    public static final int parseNoteSymbol(String s)
        throws IllegalArgumentException
    {
        s = s.trim().toUpperCase();
        for(int i = NOTE_SYMBOL.length - 1; i >= 0; i--)
        {
            if(!s.startsWith(NOTE_SYMBOL[i]))
                continue;
            try
            {
                return i + 12 * Integer.parseInt(s.substring(NOTE_SYMBOL[i].length()).trim());
            }
            catch(NumberFormatException _ex) { }
            break;
        }

        throw new IllegalArgumentException("not valid note symbol.");
    }

    public Frequency(float f)
    {
        frequency = 1.0F;
        frequency = f;
    }

    public Frequency(String s)
        throws IllegalArgumentException
    {
        frequency = 1.0F;
        frequency = valueOf(s);
    }

    public byte byteValue()
    {
        return (byte)(int)(frequency + 0.5F);
    }

    public short shortValue()
    {
        return (short)(int)(frequency + 0.5F);
    }

    public long longValue()
    {
        return (long)(frequency + 0.5F);
    }

    public int intValue()
    {
        return (int)(frequency + 0.5F);
    }

    public float floatValue()
    {
        return frequency;
    }

    public double doubleValue()
    {
        return (double)frequency;
    }

    public String toString()
    {
        return Integer.toString(intValue());
    }

    public String toNoteSymbol()
    {
        return makeNoteSymbol(getPitch(frequency));
    }

    public static void main(String[] args)
    {
        System.out.println(Frequency.parseNoteSymbol("C5"));
        System.out.println(Frequency.getFrequency(72));
        System.out.println(Frequency.makeNoteSymbol(72));
        System.out.println(Math.log10(	554.365));
        System.out.println(Math.log10(	587.330));
        System.out.println(Math.log10(	659.255));
        System.out.println(Math.log10(	493.883));
        System.out.println(1/Math.pow(3, 4));
        System.out.println(Math.log(10));
        System.out.println(Math.log(126.4)/Math.log(10));
        System.out.println(Math.log10(126.4));
        System.out.println(getPitch(523.25116));
        System.out.println(linearPitchClassFormula(261.625565));
    }
    
    /**
     * Calculate base 2 logarithm
     *
     * @param x value to take log of
     *
     * @return base 2 logarithm.
     */
    public static double log2( double x )
        {
        // Math.log is base e, natural log, ln
        return Math.log( x ) / Math.log( 2 );
        }
    
    public static double linearPitchClassFormula(double frequency){
    	return Math.round(69 + 12*log2(frequency/frequencyOfA4));
    }
}
