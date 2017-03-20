package Logic;

import Logic.Pier;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import java.util.ArrayList;

/**
 * Created by narey on 18.03.2017.
 */
public class Port {
    private int product;

    private ArrayList<Pier> piers;

    private ShipsQueue shipsQueue;

    private Table table;
    private Display display;

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

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
        for (int i = 0; i < piers.size(); i++) {
            new TableItem(this.table, SWT.NONE);
        }
    }

    public Display getDisplay() {
        return display;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }
}
