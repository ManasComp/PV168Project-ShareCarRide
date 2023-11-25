package cz.muni.fi.pv168.project.ui.action.CarRide;


import cz.muni.fi.pv168.project.business.model.CarRide;
import cz.muni.fi.pv168.project.business.model.Currency;
import cz.muni.fi.pv168.project.data.EntityProvider;
import cz.muni.fi.pv168.project.ui.dialog.DialogFactory;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.CarRide.CarRideTableModel;
import cz.muni.fi.pv168.project.ui.model.adapters.EntityListModelAdapter;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

final class AddCarRideAction extends AbstractAction {
    private final JTable carRidesTable;
    private final EntityListModelAdapter<Currency> currencyListModel;
    private final EntityProvider entityProvider;
    private final DialogFactory modalDialogFactory;

    AddCarRideAction(JTable carRidesTable, DialogFactory modalDialogFactory, EntityProvider entityProvider, EntityListModelAdapter<Currency> currencyListModel, Icon icon) {
        super("Add");
        this.modalDialogFactory = modalDialogFactory;
        this.currencyListModel = currencyListModel;
        this.entityProvider = entityProvider;
        this.carRidesTable = carRidesTable;
        putValue(SMALL_ICON, icon);
        putValue(SHORT_DESCRIPTION, "Adds new Ride");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
    }

    private void updateFields(CarRide carRide) {
        var carRidesTableModel = (CarRideTableModel) carRidesTable.getModel();
        carRidesTableModel.addRow(carRide);
        carRide.getCurrency().setNewestRateToDollar(carRide.getConversionToDollars());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        EntityDialog<CarRide> dialog = modalDialogFactory.getAddCarRideDialog(entityProvider.getCarRide());
        dialog.show(carRidesTable, "Add Car ride", "Add")
                .ifPresent(this::updateFields);
    }
}
