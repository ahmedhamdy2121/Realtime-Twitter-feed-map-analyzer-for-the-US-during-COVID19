package bdt.streamdata.model;

public class Coordinates {
	float[] coordinates;
	
	public Coordinates(float[] coordinates) {
		super();
		this.coordinates = coordinates;
	}

    @Override
    public String toString() {
    	if (coordinates == null) return "";
        return "{coordinates:[" +
                " longitude: " + coordinates[0]  +   
                ", latitude: " + coordinates[1] +
                "]}";
    }
}
