package Interface;

import Logic.Port;
import Logic.Ship;
import Logic.ShipsQueue;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * Created by narey on 19.03.2017.
 */
public class MainWindow {

    private static final ShipsQueue shipsQueue = new ShipsQueue();

    public static void main(String[] args) {
        //Port port = new Port(shipsQueue, 10,2);

        Port port = new Port(shipsQueue, 10,6);

        Display display = new Display();
        shipsQueue.setDisplay(display);
        port.setDisplay(display);
        Shell shell = new Shell(display);

        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        shell.setLayout(gridLayout);

        GridData gridData = new GridData();

        final Button addShipBtn = new Button(shell, SWT.PUSH);
        addShipBtn.setText("Add ship");
        addShipBtn.setLayoutData(gridData);


        Label product = new Label(shell, SWT.NONE);
        product.setText("Product: ");

        String[] titlesQueue = {"Ship ID", "Priority", "Load", "Unload"};
        final Table tableQueue = new Table(shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
        shipsQueue.setTable(tableQueue);
        gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
        tableQueue.setLayoutData (gridData);
        tableQueue.setLinesVisible (true);
        tableQueue.setHeaderVisible (true);
        for (int i=0; i<titlesQueue.length; i++) {
            TableColumn column = new TableColumn (tableQueue, SWT.NONE);
            column.setText (titlesQueue [i]);
        }
        for (int i=0; i<titlesQueue.length; i++) {
            tableQueue.getColumn (i).pack ();
        }


        addShipBtn.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                shipsQueue.addShip( new Ship(0, 1, 2 ,0));
            }
        });



        String[] titlesPiers = {"Pier", "Ship ID", "Status"};
        Table tablePiers = new Table(shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
        port.setTable(tablePiers);
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
}
