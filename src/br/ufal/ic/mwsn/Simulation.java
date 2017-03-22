package br.ufal.ic.mwsn;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import br.ufal.ic.mwsn.gui.Environment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Simulation {

    private Environment environment;
    private List<Sensor> sensors;
    private Sink sink;

    private Map<String, Position> map_temp;

    private static Simulation instance;

    public Simulation() {
        sensors = new ArrayList<>();
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

    public double avg(List<Long> list) {
        double avg = 0;
        for (Long long1 : list) {
            avg += long1.doubleValue() / list.size();
        }
        return avg;
    }

    private void start() {
        initGraphics();
        initNetwork();

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(sink.delays.size());
        System.out.println(sink.delays);
        System.out.println("DELAY AVG: " + avg(sink.delays) + "\n");
        for (Sensor s : sensors) {
            System.out.println("BATTERY: " + s.getId() + " " + s.getBattery());
        }
        System.out.println("BATTERY: " + sink.getId() + " " + sink.getBattery());
        System.exit(0);

    }

    public void initNetwork() {

        // place sink
        sink = new Sink("Sink", 500, 100, 24f);
        System.out.println("Sink " + sink.getId() + " added on " + sink.getPosition().toString());

        new Thread(sink).start();

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
            sensors.add(s);
        }
        int time = 0;

        while (time < 24) {
            long date_send = System.currentTimeMillis();
            Collections.shuffle(sensors);
            for (Sensor s : sensors) {
                s.setTime(time);
                s.setTimeStart(date_send);
                new Thread(s).start();
            }
            try {
                Thread.sleep(1000);
                ++time;
                System.out.println("\n");
            } catch (InterruptedException ex) {
                Logger.getLogger(Sensor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
