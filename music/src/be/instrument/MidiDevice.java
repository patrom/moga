package be.instrument;

public enum MidiDevice {

	KONTACT("Kontakt 5 Virtual Input");
	
	private String name;
	
	private MidiDevice(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	
}
