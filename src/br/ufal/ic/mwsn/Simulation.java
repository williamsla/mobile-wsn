package br.ufal.ic.mwsn;

public class Simulation {
	private long duration;
	private long numberOfNodes;

	public Simulation(long duration, long numberOfNodes) {
		super();
		this.duration = duration;
		this.numberOfNodes = numberOfNodes;
	}

	public void initNetwork() {

	}

	public void stopNetwork() {

	}

	public void generateStats() {

	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public long getNumberOfNodes() {
		return numberOfNodes;
	}

	public void setNumberOfNodes(long numberOfNodes) {
		this.numberOfNodes = numberOfNodes;
	}

}