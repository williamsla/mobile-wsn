package br.ufal.ic.mwsn;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sink extends Node {

    private float temp;
    private ServerSocket server;

    public Sink(String id, int x, int y, float temp) {
        super(id, x, y);
        this.temp = temp;
    }

    public float getTemperature() {
        return this.temp;
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(2000);

            while (true) {
                System.out.println("listening on " + server.getInetAddress().getHostAddress() + " ...");
                Socket client = server.accept();
                try {
                    String data_receive = receive(client.getInputStream());
                    String[] data = data_receive.split(";");
                    System.out.println("Sink received '" + data[1] + "' from '" + data[0] + "' data.");
                    send(client.getOutputStream(), new Date().getTime());
                } finally {
                    client.close();
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Sink.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
