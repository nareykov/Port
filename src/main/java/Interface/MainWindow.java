package Interface;

import Dispatcher.Dispatcher;
import Logic.Port;
import Logic.Ship;
import Logic.ShipsQueue;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;

import java.awt.*;

/**
 * Created by narey on 19.03.2017.
 */
public class MainWindow {

    private static final ShipsQueue shipsQueue = new ShipsQueue();

    public static void main(String[] args) {
        //Port port = new Port(shipsQueue, 10,2);

        Port port = new Port(shipsQueue, 10,6);

        new Dispatcher(port, shipsQueue);

        final Display display = new Display();
        shipsQueue.setDisplay(display);
        port.setDisplay(display);
        final Shell shell = new Shell(display);
        shell.setLocation(500,100);
        shell.setMinimumSize(500,500);

        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        shell.setLayout(gridLayout);

        GridData gridData = new GridData();

        final Button addShipBtn = new Button(shell, SWT.PUSH);
        addShipBtn.setText("Add ship");
        addShipBtn.setLayoutData(gridData);


        Label product = new Label(shell, SWT.NONE);
        product.setText("Product:          ");
        port.setProductLabel(product);

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

        final int[] f = {0};
        addShipBtn.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                final Shell newShip = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
                Point pt = display.getCursorLocation();
                newShip.setLocation(pt.x, pt.y);
                newShip.setText("New ship");

                GridLayout gridLayout = new GridLayout();
                gridLayout.numColumns = 2;
                gridLayout.makeColumnsEqualWidth = false;
                newShip.setLayout(gridLayout);

                GridData gridData = new GridData();
                gridData.grabExcessHorizontalSpace = true;
                gridData.grabExcessVerticalSpace = true;
                gridData.minimumWidth = 50;
                gridData.minimumHeight = 20;

                Label lbl0 = new Label(newShip, 0);
                lbl0.setText("ShipID: ");
                lbl0.setLayoutData(gridData);
                final Text textID = new Text(newShip, SWT.BORDER);
                textID.setSize(50, 50);
                textID.setLayoutData(gridData);
                Label lbl1 = new Label(newShip, 0);
                lbl1.setText("Load: ");
                lbl1.setLayoutData(gridData);
                final Text textLoad = new Text(newShip, SWT.BORDER);
                textLoad.setSize(50, 50);
                textLoad.setLayoutData(gridData);
                Label lbl2 = new Label(newShip, 0);
                lbl2.setText("Unload: ");
                lbl2.setLayoutData(gridData);
                final Text textUnload = new Text(newShip, SWT.BORDER);
                textUnload.setLayoutData(gridData);
                Label lbl3 = new Label(newShip, 0);
                lbl3.setText("Priority: ");
                lbl3.setLayoutData(gridData);
                final Text textPriority = new Text(newShip, SWT.BORDER);
                textPriority.setLayoutData(gridData);
                new Label(newShip, 0).setLayoutData(gridData);
                Button btnAdd = new Button(newShip, 0);
                btnAdd.setText("Add");
                btnAdd.setLayoutData(gridData);
                btnAdd.addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        try {
                            int id = Integer.parseInt(textID.getText());
                            int load = Integer.parseInt(textLoad.getText());
                            int unload = Integer.parseInt(textUnload.getText());
                            int priority = Integer.parseInt(textPriority.getText());
                            shipsQueue.addShip( new Ship(id, 0, load, unload, priority));
                        } catch (NumberFormatException e) {
                            return;
                        }
                    }
                });

                newShip.setSize(120, 200);
                newShip.open();
                //shipsQueue.addShip( new Ship(0, 1, 2 , f[0]++));
            }
        });

        String[] titlesPiers = {"Pier", "Ship ID", "Status        "};
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

        shipsQueue.addShip(new Ship(0, 20,0, 40));
        shipsQueue.addShip(new Ship(0, 20,0, 6770));
        shipsQueue.addShip(new Ship(0, 20,0, 450));

        shell.pack ();
        shell.open ();
        while (!shell.isDisposed ()) {
            if (!display.readAndDispatch ()) display.sleep ();
        }
        display.dispose ();
    }
}
