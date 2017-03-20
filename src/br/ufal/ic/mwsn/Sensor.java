package br.ufal.ic.mwsn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sensor extends Node {

    private String data;
    //private int posX = 0;
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

    private void send() {
        this.decrementBattery(0.2f);
        long time_send = getTimeStart();
        long time_receive = sink_node.receive(this.data, time_send);
    }

    public void collect(int hour) {
        data = this.getId() + " | " + hour + " | " + getTemperature(hour) + ";\n";
    }

//    @Override
//    public void run() {
//        long sleep_time = 1000;
//        while (time < 24) {
//            this.collect(time);
//            Date date_send = new Date();
//            Date date_received = this.send();
//            long atraso = date_received.getTime() - date_send.getTime();            
//            System.out.println("ATRASO: " + atraso);
//            sleep_time -= atraso;
//            try {
//                Thread.sleep(sleep_time);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            ++time;
//        }
//
//        try {
//            this.finalize();
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("BATTERY: " + this.getBattery());
//    }
    @Override
    public void run() {

        this.collect(getTime());

        this.send();
        try {
            this.finalize();
        } catch (Throwable ex) {
            Logger.getLogger(Sensor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
