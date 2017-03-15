package br.ufal.ic.mwsn;

import java.util.Date;

public class Sensor extends Node {

    private String data;
    private int posX = 0;

    public Sensor(int x, int y) {
        super(x,y);
    }

    private void send() {

        String grid[][] = Simulation.getInstance().getEnvironment().getGrid();
        int height = Simulation.getInstance().getEnvironment().getGridHeight();

        if (this.posX < 1550) {
            for (int i = this.posX; i < this.posX + 50; i++) {
                for (int j = 0; j < height; j++) {
                    if (!grid[i][j].equals("-1")) {
                        String nodeId = grid[i][j];

                        Node recipient = Simulation.getInstance().getNodes().get(nodeId);
                        recipient.receive(this.data);
                    }
                }
            }
        }
    }

    public void collect() {
        long timeStamp = new Date().getTime();
        int currentPosition = this.posX;

        data += this.getId().toString() + ", " + timeStamp + "," + currentPosition + ";";

    }

    public void move() {
        this.posX += (int) (Math.random() * 20) + 30;

        Simulation.getInstance().getEnvironment().contendGridPosition(this.posX, this.getPosition().getY(),
                this.getId().toString());

    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public void run() {

        while (this.posX < 1600) {
            //this.move();
            this.collect();
            this.send();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            this.finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
