package br.ufal.ic.mwsn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;

public abstract class Node implements Runnable {

    private String id;
    private Date currentTime;
    private Position position;
    private String data = "";
    private double battery;

    public Node(String id, int x, int y) {
        this.id = id;
        this.currentTime = new Date();
        this.position = new Position(x, y);
        this.battery = 100f;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public String getData() {
        return data;
    }

    public String receive(InputStream dataFrame) throws IOException {
        BufferedReader input
                = new BufferedReader(new InputStreamReader(dataFrame));
        this.decrementBattery(0.2f);   
        return input.readLine();
    }

    public void send(OutputStream dataFrame, String data) throws IOException {
        this.decrementBattery(0.2f);
        PrintWriter out
                = new PrintWriter(dataFrame, true);
        out.println(data);
    }

    public void send(OutputStream dataFrame, Long data) throws IOException {
        send(dataFrame, data.toString());
    }

    public double getBattery() {
        return battery;
    }

    public void decrementBattery(float value) {
        battery -= value;
    }
}
