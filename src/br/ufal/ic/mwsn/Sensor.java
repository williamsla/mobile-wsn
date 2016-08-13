package br.ufal.ic.mwsn;

import java.util.Date;

public class Sensor extends Node implements Runnable {

	private String data;
	private int posX = 0;

	public Sensor() {
		super();

		int x = (int) (Math.random() * 100) / 10;
		int y = (int) new Date().getTime() % (30);
		this.setPosition(new Position(x, 50 + y));

	}

	@Override
	public void receive() {
		// TODO Auto-generated method stub
	}

	public void send() {

	}

	public void collect() {

	}

	public void move() {
		// int incrementX = (int) (Math.random() * 100) % 50;

		this.posX += 20;

		Simulation.getInstance().getEnvironment().contendGridPosition(this.posX, this.getPosition().getY(),
				this.getId().toString());

	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public void run() {

		while (this.posX < 1600) {
			this.move();

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		try {
			this.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
