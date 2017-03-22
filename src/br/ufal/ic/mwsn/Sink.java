package br.ufal.ic.mwsn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
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
        try {
            server = new ServerSocket(2000);

            while (true) {
                System.out.println("listening on " + server.getInetAddress().getHostAddress() + " ...");
                Socket client = server.accept();

                BufferedReader input
                        = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String[] data = input.readLine().split(";");
                System.out.println("Sink received '" + data[1] + "' from '" + data[0] + "' data.");
                try {
                    PrintWriter out
                            = new PrintWriter(client.getOutputStream(), true);
                    out.println(new Date().getTime());
                } finally {
                    client.close();
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Sink.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
