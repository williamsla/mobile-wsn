package br.ufal.ic.mwsn;

import java.util.Date;

public abstract class Node implements Runnable {

    private String id;
    private Date currentTime;
    private Position position;
    private String data = "";
    private int battery;

    public Node(String id, int x, int y) {
        this.id = id;
        this.currentTime = new Date();
        this.position = new Position(x, y);
        this.battery = 100;
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

    public void receive(String dataFrame) {
        synchronized (dataFrame) {
            data += dataFrame;
        }
        System.out.println(dataFrame);
    }

    public int getBattery() {
        return battery;
    }

    public void decrementBattery(int value) {
        battery -= value;
    }
}
