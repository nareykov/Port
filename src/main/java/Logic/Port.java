package Logic;

import Logic.Pier;

import java.util.ArrayList;

/**
 * Created by narey on 18.03.2017.
 */
public class Port {
    private int product;

    private ArrayList<Pier> piers;

    private ShipsQueue shipsQueue;

    public Port(ShipsQueue shipsQueue, int product, int size) {
        this.shipsQueue = shipsQueue;
        this.product = product;
        this.piers = new ArrayList<Pier>(size);
        for (int i = 0; i < size; i++) {
            piers.add(new Pier(this, shipsQueue));
        }
        System.out.println("Logic.Port created");
    }

    public int getProduct() {
        return product;
    }

    public synchronized void addToProduct(int add) {
        this.product += add;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public ShipsQueue getShipsQueue() {
        return shipsQueue;
    }

    public void setShipsQueue(ShipsQueue shipsQueue) {
        this.shipsQueue = shipsQueue;
    }

    public void wait1() {
        for (int i = 0; i < piers.size(); i++) {
            try {
                piers.get(i).t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
