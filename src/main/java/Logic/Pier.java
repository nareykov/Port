package Logic;

import DataBase.DataBase;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;

import java.util.Random;

/**
 * Created by narey on 18.03.2017.
 */
public class Pier implements Runnable {
    private static int id = 0;

    public Thread t;

    int ID = id;

    private Ship ship;

    private Port port;
    private ShipsQueue shipsQueue;

    private static DataBase db = new DataBase();

    public Pier(Port port, ShipsQueue shipsQueue) {
        this.shipsQueue = shipsQueue;
        this.port = port;
        t = new Thread(this, "Pier(" + id + ")");
        System.out.println("Pier(" + id + ") created");
        t.start();
        id++;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public int getId() {
        return id;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int number;
                synchronized (this) {
                    ship = shipsQueue.remove();
                    port.addToProduct(ship.getUnloading() - ship.getLoading());

                    Random rnd = new Random(System.currentTimeMillis());
                    // Получаем случайное число в диапазоне от min до max (включительно)
                    //int number = min + rnd.nextInt(max - min + 1);
                    number = 6 + rnd.nextInt(15 - 6 + 1);
                    db.connectToDataBase();
                    if (number > ship.getDuration()) {
                        db.updateShipInfo(ship.getId(), ship.getLoading(), ship.getUnloading(), 1);
                    } else {
                        db.updateShipInfo(ship.getId(), ship.getLoading(), ship.getUnloading(), 0);
                    }
                    db.closeDataBase();
                }
                System.out.println(t.getName() + ": ship <==. ID:" + ship.getId());
                refreshTableItem(ship, "unloading");
                Thread.sleep(ship.getDuration() * 1000 / 2);
                refreshTableItem(ship, "loading");
                Thread.sleep(ship.getDuration() * 1000 / 2);
                if (number > ship.getDuration()) {
                    refreshTableItem(ship, "LAGGGG");
                    Thread.sleep((number - ship.getDuration()) * 1000);
                }
                System.out.println(t.getName() + ": ship ==>. ID:" + ship.getId());
                refreshTableItem(null, "empty");
            } catch (InterruptedException e) {
                System.out.println(t.getName() + " interrupted");
            }
        }
    }

    private synchronized void refreshTableItem(final Ship ship, final String status) {
        port.getDisplay().asyncExec(new Runnable() {
            @Override
            public void run() {
                if (ship != null) {
                    TableItem tableItem = port.getTable().getItem(ID);
                    tableItem.setText(0, t.getName());
                    tableItem.setText(1, "" + ship.getId());
                    tableItem.setText(2, status);
                } else {
                    TableItem tableItem = port.getTable().getItem(ID);
                    tableItem.setText(0, t.getName());
                    tableItem.setText(1, "");
                    tableItem.setText(2, status);
                }
            }
        });
    }
}
