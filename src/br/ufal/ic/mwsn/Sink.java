package br.ufal.ic.mwsn;

import java.util.ArrayList;
import java.util.List;

public class Sink extends Node {

    private float temp;
    public List<Long> delays;

    public Sink(String id, int x, int y, float temp) {
        super(id, x, y);
        this.temp = temp;
        delays = new ArrayList<>();
    }

    public float getTemperature() {
        return this.temp;
    }

    public void showData() {
        String dataArray[] = formatData();
        dataArray = dataArray;

        for (int i = 0; i < dataArray.length; i++) {
            System.out.println(i + ":" + dataArray[i].toString());
        }
    }

    public long receive(String dataFrame, long date_send) {
        long date_receive = this.receive(dataFrame);

        delays.add(date_receive - date_send);
        this.decrementBattery(0.2f);
        return date_receive;
    }

    private String[] formatData() {

        String dataArray[] = this.getData().split(";");

        // System.out.println("Sink data: " + dataArray.toString());
        return dataArray;
    }

    @Override
    public void run() {
        while (true) {

            if (!this.getData().equals("")) {
                System.out.println("Sink is receiving data...");
                //        System.out.println(this.getData() + "\n--");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
