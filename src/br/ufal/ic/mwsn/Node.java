package br.ufal.ic.mwsn;

import java.util.Date;
import java.util.UUID;

import javax.sql.rowset.spi.SyncResolver;

public abstract class Node {
	private UUID id;
	private Date currentTime;
	private Battery battery;
	private Position position;
	private String data = "";

	public Node() {
		this.id = UUID.randomUUID();
		this.currentTime = new Date();
		this.battery = new Battery(Long.parseLong("2628000000000"));
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public UUID getId() {
		return id;
	}

	public Date getCurrentTime() {
		return currentTime;
	}

	public Battery getBattery() {
		return battery;
	}

	public void receive(String dataFrame) {
		synchronized (dataFrame) {
			data += dataFrame;
		}
	}
}
