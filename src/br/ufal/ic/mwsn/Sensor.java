package br.ufal.ic.mwsn;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sensor extends Node {

    private final Sink sink_node;

    private Map<Integer, Float> temperatures;
    int time = 0;
    float rate;
    private long date_send;
    private List<Long> battery;

    public Sensor(String id, int x, int y, Sink sink_node, float rate) {
        super(id, x, y);
        this.sink_node = sink_node;
        this.rate = rate;
        temperatures = new HashMap<>();
        setTemperature();
        battery = new ArrayList<>();
    }

    public float getTemperature(int hour) {
        return temperatures.get(hour);
    }

    public void setTime(int hour) {
        this.time = hour;
    }

    public int getTime() {
        return this.time;
    }

    public void setTemperature() {
        for (int h = 0; h < 24; h++) {
            float grau;
            if (h >= 22 || h <= 6) {
                grau = 0;
            } else if (h <= 10) {
                grau = 1;
            } else if (h <= 14) {
                grau = 3;
            } else {
                grau = 2;
            }
            temperatures.put(h, grau + sink_node.getTemperature() + rate);
        }
    }

    public void setTimeStart(long date_send) {
        this.date_send = date_send;
    }

    public long getTimeStart() {
        return this.date_send;
    }

    private void send(String data) {
        this.decrementBattery(0.2f);
        long time_send = getTimeStart();
        long time_receive = sink_node.receive(data, time_send);
    }

    public String collect(int hour) {
        return this.getId() + " | " + hour + " | " + getTemperature(hour) + ";\n";
    }

    @Override
    public void run() {
        try {
            Socket sensor = new Socket("127.0.0.1", 1000);
            ObjectOutputStream output = new ObjectOutputStream(sensor.getOutputStream());
            String data = this.collect(getTime());
            output.writeChars(data);
            output.close();
            sensor.close();
            try {
                this.finalize();
            } catch (Throwable ex) {
                Logger.getLogger(Sensor.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(Sensor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
