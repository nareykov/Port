package Interface;

import Logic.Port;
import Logic.Ship;
import Logic.ShipsQueue;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;

/**
 * Created by narey on 19.03.2017.
 */
public class MainWindow {

    private static Display display;
    private static Shell shell;
    private static Table tableQueue;
    private static Table tablePiers;
    private static final ShipsQueue shipsQueue = new ShipsQueue();

    public static void main(String[] args) {
        //Port port = new Port(shipsQueue, 10,2);

        shipsQueue.addShip(new Ship(1,1,3,4));

        Port port = new Port(shipsQueue, 10,6);

        display = new Display ();
        shell = new Shell (display);

        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        shell.setLayout(gridLayout);

        GridData gridData = new GridData();

        final Button addShipBtn = new Button(shell, SWT.PUSH);
        addShipBtn.setText("Add ship");
        addShipBtn.setLayoutData(gridData);
        addShipBtn.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                shipsQueue.addShip(new Ship(0, 1, 2 ,0));
            }
        });

        Label product = new Label(shell, SWT.NONE);
        product.setText("Product: ");

        String[] titlesQueue = {"Ship ID", "Priority", "Load", "Unload"};

        tableQueue = new Table(shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
        shipsQueue.setTable(tableQueue);
        gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
        tableQueue.setLayoutData (gridData);
        tableQueue.setLinesVisible (true);
        tableQueue.setHeaderVisible (true);
        for (int i=0; i<titlesQueue.length; i++) {
            TableColumn column = new TableColumn (tableQueue, SWT.NONE);
            column.setText (titlesQueue [i]);
        }
        refreshTable();
        for (int i=0; i<titlesQueue.length; i++) {
            tableQueue.getColumn (i).pack ();
        }

        String[] titlesPiers = {"Pier", "Ship ID", "Status"};

        tablePiers = new Table(shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
        gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
        tablePiers.setLayoutData (gridData);
        tablePiers.setLinesVisible (true);
        tablePiers.setHeaderVisible (true);
        for (int i=0; i<titlesPiers.length; i++) {
            TableColumn column = new TableColumn (tablePiers, SWT.NONE);
            column.setText (titlesPiers [i]);
        }
        for (int i=0; i<titlesPiers.length; i++) {
            tablePiers.getColumn (i).pack ();
        }

        shell.pack ();
        shell.open ();
        while (!shell.isDisposed ()) {
            if (!display.readAndDispatch ()) display.sleep ();
        }
        display.dispose ();
    }

    public static void refreshTable() {
        ArrayList<Ship> ships = shipsQueue.getShips();
        for (int i = 0; i < ships.size(); i++) {
            try {
                TableItem item = new TableItem (tableQueue, SWT.NONE);
                item.setText (0, "" + ships.get(i).getId());
                item.setText (1, "" + ships.get(i).getPriority());
                item.setText (2, "" + ships.get(i).getLoading());
                item.setText (3, "" + ships.get(i).getUnloading());
            } catch (IllegalArgumentException e) {

            }
        }
    }
}
