package Logic;

/**
 * Created by narey on 18.03.2017.
 */
public class Pier implements Runnable {
    private static int id = 0;

    public Thread t;

    private Ship ship;

    private Port port;
    private ShipsQueue shipsQueue;

    public Pier(Port port, ShipsQueue shipsQueue) {
        this.shipsQueue = shipsQueue;
        this.port = port;
        t = new Thread(this, "Logic.Pier(" + id + ")");
        System.out.println("Logic.Pier(" + id + ") created");
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
                Ship ship = shipsQueue.remove(0);
                System.out.println(t.getName() + ": ship <==. ID:" + ship.getId());
                Thread.sleep(2000);
                port.addToProduct(ship.getUnloading() - ship.getLoading());
                System.out.println(t.getName() + ": ship ==>. ID:" + ship.getId());
            } catch (InterruptedException e) {
                System.out.println(t.getName() + " interrupted");
            }
        }
    }
}
