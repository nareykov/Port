package Logic;

import Interface.MainWindow;
import Logic.Ship;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import java.util.*;

/**
 * Created by narey on 18.03.2017.
 */
public class ShipsQueue {
    Comparator<Ship> comparator = new Comparator<Ship>() {

        @Override
        public int compare(Ship o1, Ship o2) {
            if( o1.getPriority() > o2.getPriority() ){
                return -1;
            }
            if( o1.getPriority() < o2.getPriority() ){
                return 1;
            }
            return 0;
        }
    };

    private ArrayList<Ship> ships = new ArrayList<Ship>();

    private Table table;
    private Display display;

    public synchronized void addShip( Ship ship) {
        ships.add(ship);
        sort();
        refreshQueueTable();
        notifyAll();
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public synchronized Ship remove(int index) {
        while (ships.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrupted");
            }
        }

        Ship ship = ships.remove(index);
        display.asyncExec (new Runnable () {
            public void run () {
                refreshQueueTable();
            }
        });
        return ship;
    }

    public void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }

    private void sort() {
        ships.sort(comparator);
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    private void refreshQueueTable() {
        table.removeAll();
        for (int i = 0; i < ships.size(); i++) {
            try {
                TableItem item = new TableItem (table, SWT.NONE);
                item.setText (0, "" + ships.get(i).getId());
                item.setText (1, "" + ships.get(i).getPriority());
                item.setText (2, "" + ships.get(i).getLoading());
                item.setText (3, "" + ships.get(i).getUnloading());
            } catch (IllegalArgumentException e) {

            }
        }
    }

    public Display getDisplay() {
        return display;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }
}
