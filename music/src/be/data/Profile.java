package be.data;

public enum Profile {
	
	UNISONO(1.0),
	KLEINE_SECONDE(1.0),
	GROTE_SECONDE(1.0),
	KLEINE_TERTS(0.8),
	GROTE_TERTS(0.8),
	KWART(0.7),
	TRITONE(0.1),
	KWINT(0.5),
	KLEINE_SIXT(0.3),
	GROTE_SIXT(0.3),
	KLEIN_SEPTIEM(0.2),
	GROOT_SEPTIEM(0.1),
	OCTAAF(0.3);
	
	public double getpitchProfile() {
		return pitch;
	}
	
	Profile(double pitch){
		this.pitch = pitch;
	}
	
	private double pitch;

}
