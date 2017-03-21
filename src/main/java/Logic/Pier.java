package Logic;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;

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
                synchronized (this) {
                    ship = shipsQueue.remove();
                    port.addToProduct(ship.getUnloading() - ship.getLoading());
                }
                System.out.println(t.getName() + ": ship <==. ID:" + ship.getId());
                refreshTableItem(ship, "unloading");
                Thread.sleep(2000);
                refreshTableItem(ship, "loading");
                Thread.sleep(2000);
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
