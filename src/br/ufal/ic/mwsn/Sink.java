package br.ufal.ic.mwsn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sink extends Node {

    public Sink(String id, int x, int y) {
        super(id, x, y);
    }

    public void showData() {
        String dataArray[] = formatData();
        dataArray = eliminateDuplicate(dataArray);

        for (int i = 0; i < dataArray.length; i++) {
            System.out.println(i + ":" + dataArray[i].toString());
        }
    }

    private String[] formatData() {

        String dataArray[] = this.getData().split(";");

        // System.out.println("Sink data: " + dataArray.toString());
        return dataArray;
    }

    public String[] getProcessedData() {
        String dataArray[] = formatData();
        dataArray = eliminateDuplicate(dataArray);
        return dataArray;
    }

    private String[] eliminateDuplicate(String dataArray[]) {
        for (int i = 0; i < dataArray.length; i++) {
            for (int j = 0; j < dataArray.length; j++) {

                if (dataArray[i].toString().equals(dataArray[j].toString()) && j > i) {

                    List<String> list = new ArrayList<String>(Arrays.asList(dataArray));
                    list.remove(j);
                    dataArray = list.toArray(new String[0]);
                }
            }
        }

        return dataArray;
    }

    @Override
    public void run() {
        this.placeSink();

        while (true) {
            //showData();
            if (!this.getData().equals("")) {
                System.out.println("Sink is receiving data...");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void placeSink() {
        int sinkX = this.getPosition().getX();
        int sinkY = this.getPosition().getY();
        String sinkId = this.getId();

        Simulation.getInstance().getEnvironment().contendGridPosition(sinkX, sinkY, sinkId);
    }

}
