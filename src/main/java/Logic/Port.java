package Logic;

import Logic.Pier;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import java.util.ArrayList;

/**
 * Created by narey on 18.03.2017.
 */
public class Port {
    private static int product;

    private ArrayList<Pier> piers;

    private ShipsQueue shipsQueue;

    private Label productLabel;
    private Table table;
    private Display display;

    public Port(ShipsQueue shipsQueue, int product, int size) {
        this.shipsQueue = shipsQueue;
        this.product = product;
        this.piers = new ArrayList<Pier>(size);
        for (int i = 0; i < size; i++) {
            piers.add(new Pier(this, shipsQueue));
        }
        System.out.println("Port created");
    }

    public static int getProduct() {
        return product;
    }

    public synchronized void addToProduct(int add) {
        this.product += add;
        display.asyncExec(new Runnable() {
            @Override
            public void run() {
                productLabel.setText("Product: " + product);
            }
        });
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
            TableItem tableItem = new TableItem(this.table, SWT.NONE);
            tableItem.setText(0, "Pier(" + i + ")");
        }
    }

    public Display getDisplay() {
        return display;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public ArrayList<Pier> getPiers() {
        return piers;
    }

    public void setProductLabel(Label productLabel) {
        this.productLabel = productLabel;
    }
}
