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
 * Очередь кораблей.
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

    /**
     * Добавление корабля в очередь и сотртировка очереди по приоритету
     * @param ship корабль
     */
    public synchronized void addShip( Ship ship) {
        ships.add(ship);
        sort();
        refreshQueueTable();
        notifyAll();
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    /**
     * Функция изымания из очереди корабля
     * @return возвращает ближайший по очереди корабль, которому хватит того, что есть на складе
     */
    public synchronized Ship remove() {
        while (ships.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrupted");
            }
        }

        boolean flag = false;
        Ship ship;
        int i = 0;
        while (!flag) {
            for (i = 0; i < ships.size(); i++) {
                ship = ships.get(i);
                if (Port.getProduct() - ship.getLoading() + ship.getUnloading() >= 0) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " interrupted");
                }
            }
        }
        ship = ships.remove(i);
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

    /**
     * Обновляет таблицу очерреди кораблей
     */
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
