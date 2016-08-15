package br.ufal.ic.mwsn;

public class Sink extends Node {

	public void showData() {
		System.out.println("Sink data: " + this.getData());
	}

	@Override
	public void run() {
		this.placeSink();
		while (true) {
			showData();

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private void placeSink() {
		int sinkX = this.getPosition().getX();
		int sinkY = this.getPosition().getY();
		String sinkId = this.getId().toString();

		Simulation.getInstance().getEnvironment().contendGridPosition(sinkX, sinkY, sinkId);
	}

}
