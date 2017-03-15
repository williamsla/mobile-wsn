package br.ufal.ic.mwsn;

import java.util.HashMap;
import java.util.Map;

public class Sensor extends Node {
    
    private String data;
    //private int posX = 0;
    private Sink sink_node;
    
    private Map<Integer, Float> temperatures;
    int time = 0;
    float rate;
    
    public Sensor(String id, int x, int y, Sink sink_node, float rate) {
        super(id, x, y);
        this.sink_node = sink_node;
        this.rate = rate;
        temperatures = new HashMap<>();
        setTemperature();
    }
    
    public float getTemperature(int hour) {
        return temperatures.get(hour);
    }
    
    public void setTimeStart(int hour_start) {
        this.time = hour_start;
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
    
    private void send() {
        this.decrementBattery(1);
        sink_node.receive(this.data);        
    }
    
    public void collect(int hour) {
        data = this.getId() + " | " + hour + " | " + getTemperature(hour) + ";\n";
        
    }
    
    public void setData(String data) {
        this.data = data;
    }
    
    @Override
    public void run() {
        
        while (time < 24) {
            this.collect(time);
            this.send();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ++time;
        }
        
        try {
            this.finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        
        System.out.println("BATTERY: " + this.getBattery());
    }
    
}
