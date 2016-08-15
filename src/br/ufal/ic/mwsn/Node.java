package br.ufal.ic.mwsn;

import java.util.Date;
import java.util.UUID;

public abstract class Node implements Runnable {
	private UUID id;
	private Date currentTime;
	private Position position;
	private String data = "";

	public Node() {
		this.id = UUID.randomUUID();
		this.currentTime = new Date();
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

	public String getData() {
		return data;
	}

	public void receive(String dataFrame) {
		synchronized (dataFrame) {
			data += dataFrame;
		}
	}
}
