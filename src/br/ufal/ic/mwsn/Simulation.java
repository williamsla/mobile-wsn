package br.ufal.ic.mwsn;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import br.ufal.ic.mwsn.gui.Environment;

public class Simulation {

    private long duration;
    private long numberOfNodes;
    private Environment environment;
    private static Map<String, Node> nodes;
    private Sink sink;

    private Map<String, Position> map_temp;

    private static Simulation instance;

    public int hour;

    public Simulation() {
        nodes = new HashMap<>();
        map_temp = new HashMap<>();
        map_temp.put("A", new Position(100, 100));
        map_temp.put("B", new Position(100, 350));
        map_temp.put("C", new Position(350, 100));
        map_temp.put("D", new Position(350, 350));

    }

    public static Simulation getInstance() {
        if (instance == null) {
            instance = new Simulation();
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
        System.exit(0);

    }

    public void initNetwork() {

        // place sink
        sink = new Sink("Sink", 500, 100, 24f);
        nodes.put(sink.getId(), sink);
        System.out.println("Sink " + sink.getId() + " added on " + sink.getPosition().toString());

        new Thread(sink).start();
        int hour_start = 0;

        // place sensors
        for (Map.Entry<String, Position> entry : map_temp.entrySet()) {
            String k = entry.getKey();
            Position pos = entry.getValue();
            float rate;
            switch (k) {
                case "A":
                    rate = 0.3f;
                    break;
                case "B":
                    rate = 0.2f;
                    break;
                case "C":
                    rate = 0f;
                    break;
                case "D":
                    rate = 0.1f;
                    break;
                default:
                    rate = 0f;
            }

            Sensor s = new Sensor(k, pos.getX(), pos.getY(), sink, rate);
            System.out.println("Sensor " + s.getId() + " added on " + pos.toString());

            s.setTimeStart(hour_start);
            hour_start = hour_start + 1;

            nodes.put(s.getId(), s);
            new Thread(s).start();

            try {
                int r = (int) (Math.random() * 1000);
                Thread.sleep(1000 + r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

        Simulation simulation = new Simulation();
        simulation.start();
    }

}
