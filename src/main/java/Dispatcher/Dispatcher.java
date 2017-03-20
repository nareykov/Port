package Dispatcher;

import Logic.Port;
import Logic.Ship;
import Logic.ShipsQueue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by narey on 20.03.2017.
 */
public class Dispatcher implements Runnable {

    private Thread t;
    private Port port;
    private ShipsQueue shipsQueue;

    public Dispatcher(Port port, ShipsQueue shipsQueue) {
        this.port = port;
        this.shipsQueue = shipsQueue;
        t = new Thread(this, "Dispatcher");
        System.out.println("Dispatcher created");
        t.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println(t.getName() + " interrupted");
            }
            File file = new File("state.txt");
            PrintWriter out = null;
            try {
                out = new PrintWriter(file.getAbsoluteFile());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            out.println("Product: " + port.getProduct() + "\n");

            for (int i = 0; i < port.getPiers().size(); i++) {
                Ship ship = port.getPiers().get(i).getShip();
                if (ship != null) {
                    out.println("Pier(" + i + ")  ShipID:" + ship.getId());
                } else {
                    out.println("Pier(" + i + ")  ShipID: empty");
                }
            }

            out.println("");

            for (int i = 0; i < shipsQueue.getShips().size(); i++) {
                Ship ship = shipsQueue.getShips().get(i);
                if (ship != null) {
                    out.println("ShipsQueue(" + i + ")  ShipID:" + ship.getId());
                } else {
                    out.println("ShipsQueue(" + i + ")  ShipID: empty");
                }
            }
            out.close();
        }
    }
}
/*
* File settingsFile = new File("settings");
        try {
            settingsFile.createNewFile();
            PrintWriter out = new PrintWriter(settingsFile.getAbsoluteFile());
            out.print(dir);
            out.close();
            File typeFile = new File("type");
            typeFile.createNewFile();
            PrintWriter tout = new PrintWriter(typeFile.getAbsoluteFile());
            tout.print("Book\n.pdf\nSong\n.mp3\nFilm\n.avi\n.mkv\n.mp4");
            tout.close();
        }catch (IOException ex){
            log.error("File creating error");
            return;
        }
* */