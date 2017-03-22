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

    public Simulation(Map<String, Position> map) {
        sensors = new ArrayList<>();
        map_temp = map;
    }

    public double avg_long(List<Long> list) {
        double avg = 0;
        for (Long long1 : list) {
            avg += long1.doubleValue() / list.size();
        }
        return avg;
    }

    public double avg_double(List<Double> list) {
        double avg = 0;
        for (Double number : list) {
            avg += number / list.size();
        }
        return avg;
    }

    private void start() {
        initGraphics();
        initNetwork();

        List<Long> delays = new ArrayList<>();
        List<Double> battery = new ArrayList<>();
        for (Sensor s : sensors) {
            delays.addAll(s.delays);
            battery.add(s.getBattery());
            System.out.println("BATTERY: " + s.getId() + " " + s.getBattery());
        }

        System.out.println("BATTERY AVG: " + avg_double(battery));
        System.out.println("BATTERY: " + sink.getId() + " " + sink.getBattery());
        System.out.println("\n");
        System.out.println("DELAYS: " + delays);
        System.out.println("DELAY AVG: " + avg_long(delays));
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
        Map<String, Position> map = new HashMap<>();
        map.put("A", new Position(100, 100));
        map.put("B", new Position(100, 350));
        map.put("C", new Position(350, 100));
        map.put("D", new Position(350, 350));
        map.put("E", new Position(250, 100));
        map.put("F", new Position(150, 200));

        Simulation simulation = new Simulation(map);
        simulation.start();

    }

}
