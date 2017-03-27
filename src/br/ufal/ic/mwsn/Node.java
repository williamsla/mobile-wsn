package br.ufal.ic.mwsn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Node implements Runnable {

    private String id;
    private Date currentTime;
    private Position position;
    private String data = "";
    private float battery;
    private List<Float> batteries;

    public Node(String id, int x, int y) {
        this.id = id;
        this.currentTime = new Date();
        this.position = new Position(x, y);
        this.battery = 100f;
        this.batteries = new ArrayList<>();
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
        return input.readLine();
    }

    public void send(OutputStream dataFrame, String data) throws IOException {
        PrintWriter out
                = new PrintWriter(dataFrame, true);
        out.println(data);
    }

    public void send(OutputStream dataFrame, Long data) throws IOException {
        send(dataFrame, data.toString());
    }

    public float getBattery() {
        return battery;
    }

    public List<Float> getBatteries() {
        return batteries;
    }

    public void decrementBattery(float value) {
        battery -= value;
        batteries.add(value);
    }
}
