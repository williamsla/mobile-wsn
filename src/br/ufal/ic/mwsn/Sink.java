package br.ufal.ic.mwsn;

public class Sink extends Node {

    private float temp;

    public Sink(String id, int x, int y, float temp) {
        super(id, x, y);
        this.temp = temp;
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
