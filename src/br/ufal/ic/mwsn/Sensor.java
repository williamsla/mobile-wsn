package br.ufal.ic.mwsn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Date;
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
        return this.getId() + ";" + hour + " | " + getTemperature(hour) + "\n";
    }

    @Override
    public void run() {
        boolean close = false;
        int tries = 0;
        long time_begin = new Date().getTime();
        while (close == false) {
            ++tries;
            if (tries == 5) {
                System.out.println("package was lost");
                break;
            }
            try {
                Socket sensor = new Socket("0.0.0.0", 2000);

                String data = this.collect(getTime());
                //send data to sink
                PrintWriter out
                        = new PrintWriter(sensor.getOutputStream(), true);
                out.println(data);

                //receives data from sink
                BufferedReader input
                        = new BufferedReader(new InputStreamReader(sensor.getInputStream()));
                String date_received = input.readLine();

                // counting delay time
                long delay = Long.parseLong(date_received) - time_begin;
                System.out.println("DELAY: " + getId() + " " + delay);

                //closing
                close = true;
                out.close();
                input.close();
                sensor.close();
            } catch (java.net.SocketException ex) {
                ex.printStackTrace();
                close = true;
            } catch (IOException ex) {
                Logger.getLogger(Sensor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
