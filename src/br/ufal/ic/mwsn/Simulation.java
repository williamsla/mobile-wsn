package br.ufal.ic.mwsn;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import br.ufal.ic.mwsn.gui.Environment;

public class Simulation {
	private long duration;
	private long numberOfNodes;
	private Environment environment;
	private Map<String, Node> nodes;
	private Sink sink;

	private static Simulation instance;

	private Simulation() {
		super();
		nodes = new HashMap<>();
	}

	public static Simulation getInstance() {
		if (instance == null)
			instance = new Simulation();

		return instance;
	}

	private void start() {
		initGraphics();
		initNetwork();

		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		this.generateStats();

		System.exit(0);

	}

	public void initNetwork() {

		// place sink
		sink = new Sink();
		sink.setPosition(new Position(1500, 50));
		System.out.println("Sink Created: " + sink.getId().toString());
		nodes.put(sink.getId().toString(), sink);
		new Thread(sink).start();

		// place sensors
		for (int i = 0; i < numberOfNodes; i++) {
			Sensor s = new Sensor();
			System.out.println("Sensor Created: " + s.getId().toString());
			nodes.put(s.getId().toString(), s);
			new Thread(s).start();

			try {
				int r = (int) (Math.random() * 1000);
				Thread.sleep(1000 + r);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void generateStats() {
		// System.out.println("speed: " +
		// Statistics.getInstance().getSpeed(sink.getProcessedData()));
		//

		for (String str : sink.getProcessedData()) {
			System.out.println("Data Received: " + str);
		}

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

	public Environment getEnvironment() {
		return environment;
	}

	public Map<String, Node> getNodes() {
		return nodes;
	}

	private void initGraphics() {
		int width = 1600;
		int height = 600;

		environment = new Environment();

		// creates a new frame
		JFrame frame = new JFrame("Mobile WSN");
		frame.setSize(width, height);

		frame.setContentPane(environment);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		new Thread(environment).start();

	}

	public static void main(String[] args) {
		Simulation simulation = Simulation.getInstance();
		simulation.setNumberOfNodes(20);
		simulation.start();
	}

}