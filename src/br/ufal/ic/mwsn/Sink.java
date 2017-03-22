package br.ufal.ic.mwsn;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sink extends Node {

    private float temp;
    public List<Long> delays;
    private ServerSocket server;

    public Sink(String id, int x, int y, float temp) {
        super(id, x, y);
        this.temp = temp;
        delays = new ArrayList<>();
    }

    public float getTemperature() {
        return this.temp;
    }

    public long receive(String dataFrame, long date_send) {
        long date_receive = this.receive(dataFrame);

        delays.add(date_receive - date_send);
        this.decrementBattery(0.2f);
        return date_receive;
    }

    @Override
    public void run() {
        while (true) {

            try {
                server = new ServerSocket(1000);
                
                Socket client = server.accept();
//                ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
//                System.out.println("Sink is receiving data.");
                OutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
                outputStream.flush();
                outputStream.write("received".getBytes());
                outputStream.close();
                client.close();
            } catch (IOException ex) {
                Logger.getLogger(Sink.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
