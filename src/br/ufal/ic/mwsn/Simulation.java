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

    private Map<String, Temperature> map_temp;

    private static Simulation instance;

    public Simulation(Map<String, Temperature> temperatures) {
        nodes = new HashMap<>();
        map_temp = temperatures;
    }

    public static Simulation getInstance() {
        if (instance == null) {
            return null;
        }

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
        sink = new Sink("Sink", 1500, 50);
        System.out.println("Sink Created: " + sink.getId());
        nodes.put(sink.getId(), sink);
        new Thread(sink).start();

        // place sensors
        map_temp.forEach((k, t) -> {
            Sensor s = new Sensor(k, t.getPosition().getX(), t.getPosition().getY());
            System.out.println("Sensor " + s.getId() + " added on " + t.getPosition().toString());

            nodes.put(s.getId(), s);
            new Thread(s).start();

            try {
                int r = (int) (Math.random() * 1000);
                Thread.sleep(1000 + r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        /*for (int i = 0; i < numberOfNodes; i++) {

            int x = (int) (Math.random() * 100) / 10;
            int y = (int) new Date().getTime() % (30);
            if (y < 0) {
                y = y * -1;
            }

        }*/

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
        JFrame frame = new JFrame("WSN");
        frame.setSize(width, height);

        frame.setContentPane(environment);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        new Thread(environment).start();

    }

    public static void main(String[] args) {
        Map<String, Temperature> map_temp = new HashMap<>();
        map_temp.put("A", new Temperature(24f, 10, 10));
        map_temp.put("B", new Temperature(22f, 80, 10));
        map_temp.put("C", new Temperature(28f, 10, 20));
        map_temp.put("D", new Temperature(25f, 20, 10));

        Simulation simulation = new Simulation(map_temp);
        simulation.start();
    }

}
