package hello;

public class CurrentReading {

	// create a class that has a Hashtable that stores the temp as key-value - eg
	// key = bldg +zone, value = temp

	private float setTemperatureMin;
	private float setTemperatureMax;
	private String zoneId;
	private double setTemperature;
	private String zoneName;
	private String buildingId;
	private String twCustomHeader1;
	private String response = "Ok!";

	public CurrentReading(float setTemperatureMin, float setTemperatureMax, String zoneId, double setTemperature,
			String zoneName, String buildingId, String twCustomHeader1) {
		super();
		this.setTemperatureMin = setTemperatureMin;
		this.setTemperatureMax = setTemperatureMax;
		this.zoneId = zoneId;
		this.setTemperature = setTemperature;
		this.zoneName = zoneName;
		this.buildingId = buildingId;
		this.twCustomHeader1 = twCustomHeader1;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return response;
	}

	public void setSetTemperature(double setTemperature) {
		this.setTemperature = setTemperature;
	}

	public double getSetTemperature() {
		return setTemperature;
	}

	public String getResponse() {
		// new HashStorage( zoneId, buildingId, "setTemperature", setTemperature);
		return response;
	}

}
