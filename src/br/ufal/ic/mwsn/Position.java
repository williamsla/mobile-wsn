package br.ufal.ic.mwsn;

public class Position {

	private long latitude;
	private long longitude;

	public Position(long latitude, long longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public long getLatitude() {
		return latitude;
	}

	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}

	public long getLongitude() {
		return longitude;
	}

	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}

}
