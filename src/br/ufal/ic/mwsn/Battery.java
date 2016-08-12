package br.ufal.ic.mwsn;

public class Battery {
	private int level;
	private long lifetime;

	public Battery(long lifetime) {
		super();
		this.lifetime = lifetime;
		this.level = 100;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public long getLifetime() {
		return this.lifetime;
	}

}
