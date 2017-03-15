package br.ufal.ic.mwsn;

import java.util.Date;
import java.util.UUID;

public abstract class Node implements Runnable {

    private UUID id;
    private Date currentTime;
    private Position position;
    private float temperature;
    private String data = "";

    public Node(int x, int y) {
        this.id = UUID.randomUUID();
        this.currentTime = new Date();
        position = new Position(x, y);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public UUID getId() {
        return id;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public String getData() {
        return data;
    }

    public void receive(String dataFrame) {
        synchronized (dataFrame) {
            data += dataFrame;
        }
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temp) {
        temperature = temp;
    }
}
