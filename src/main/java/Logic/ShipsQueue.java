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

    public synchronized void addShip(Ship ship) {
        ships.add(ship);
        sort();
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
        return ships.remove(index);
    }

    public void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }

    public void sort() {
        ships.sort(comparator);
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }
}
