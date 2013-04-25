package be.data;

public enum IntervalEnum {
	
	UNISONO(0, 1.0, 0.0, 0.0, 0),
	KLEINE_SECONDE(1, 0.9, -0.3, 0.2, 2),
	GROTE_SECONDE(2, 1.0, 0.15, 0.4 , 4),
	KLEINE_TERTS(3, 0.8, 0.8, 0.6, 6),
	GROTE_TERTS(4, 0.7, 1.0, 0.7, 8),
	KWART(5, 0.5, 0.85, 0.9, 9),
	TRITONE(6, 0.0, 0.4, 0.0, 0),
	KWINT(7, 0.6, 0.9, 1.0, 10),
	KLEINE_SIXT(8, 0.4, 0.95, 0.5, 5),
	GROTE_SIXT(9, 0.3, 0.75, 0.8, 7),
	KLEIN_SEPTIEM(10, 0.2, 0.3, 0.3, 3),
	GROOT_SEPTIEM(11, 0.1, 0.2, 0.1, 1),
	OCTAAF(12, 0.0, 0.0, 0.0 , 0);
	
	public int getInterval() {
		return interval;
	}
	public double getMelodicValue() {
		return melodicValue;
	}
	public double getHarmonicValue() {
		return harmonicValue;
	}
	
	public double getTonalValue() {
		return tonalValue;
	}
	public int getSeries2() {
		return series2;
	}
	
	IntervalEnum(int interval, double melodicValue, double harmonicValue, double tonalValue, int series2){
		this.interval = interval;
		this.melodicValue = melodicValue;
		this.harmonicValue = harmonicValue;
		this.tonalValue = tonalValue;
		this.series2 = series2;
	}
	
	private int interval;
	private double melodicValue;
	private double harmonicValue;
	private double tonalValue;
	private int series2;
}
