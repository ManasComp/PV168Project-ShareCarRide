package cz.muni.fi.pv168.project.ui.panels.commonPanels;

import cz.muni.fi.pv168.project.business.model.Currency;
import cz.muni.fi.pv168.project.business.service.currenies.CurrencyConverter;
import cz.muni.fi.pv168.project.business.service.validation.common.ValidatorFactory;
import cz.muni.fi.pv168.project.ui.model.adapters.ComboBoxModelAdapter;
import cz.muni.fi.pv168.project.ui.validation.ValidableListener;
import cz.muni.fi.pv168.project.ui.validation.ValidatedInputField;
import cz.muni.fi.pv168.project.ui.validation.ValidatedJPanel;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.ListModel;


public class CostBar extends ValidatedJPanel {
    private final ValidatedInputField costOfFuel = new ValidatedInputField(ValidatorFactory.doubleValidator());
    private final JComboBox<Currency> currencyJComboBox;
    private final CurrencyConverter currencyConverter;
    private final ValidatedInputField rate = new ValidatedInputField(ValidatorFactory.doubleValidator(0.0000001f, Double.MAX_VALUE));

    public CostBar(ListModel<Currency> currencyModel, CurrencyConverter currencyConverter, ValidableListener validableListener) {
        super();
        setValidables(costOfFuel, rate);

        currencyJComboBox = new JComboBox<>(new ComboBoxModelAdapter<>(currencyModel));
        this.currencyConverter = currencyConverter;
        currencyJComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                var curr = (Currency) e.getItem();
                var rate = curr.getNewestRateToDollar();
                setConversionRateToDollars(rate);
                validableListener.fireChange();
            }
        });

        JLabel fuelLabel = new JLabel("Cost of Fuel:");
        this.add(fuelLabel);
        costOfFuel.setPreferredSize(new Dimension(150, 30));
        this.add(costOfFuel);

        JLabel currencyLabel = new JLabel("Currency:");
        this.add(currencyLabel);
        currencyJComboBox.setPreferredSize(new Dimension(150, 30));
        this.add(currencyJComboBox);

        JLabel rateLabel = new JLabel("Rate:");
        this.add(rateLabel);
        rate.setPreferredSize(new Dimension(150, 30));
        this.add(rate);
    }

    @Override
    public boolean evaluate() {
        return !isEmpty() && super.evaluate();
    }

    @Override
    public boolean isEmpty() {
        return currencyJComboBox.getSelectedItem() == null;
    }

    public void SetValues(double costOfFuelValue, double covertRateValue, Currency currency) {
        setCurrency(currency);
        setConversionRateToDollars(covertRateValue);
        setCostOfFuelInDollars(costOfFuelValue);
    }

    public double getCostOfFuelInDollars() {
        return currencyConverter.ConvertFromConversionRateTODollars(Double.parseDouble(rate.getText()), Double.parseDouble(costOfFuel.getText()));
    }

    public void setCostOfFuelInDollars(double costOfFuel) {
        this.costOfFuel.setText(String.valueOf(currencyConverter.convertFromDollarsToConvRate(Double.parseDouble(rate.getText()), costOfFuel)));
    }

    public double getConversionRateToDollars() {
        return Double.parseDouble(rate.getText());
    }

    public void setConversionRateToDollars(double covertRate) {
        this.rate.setText(String.valueOf(covertRate));
    }

    public Currency getCurrency() {
        return (Currency) currencyJComboBox.getSelectedItem();
    }

    public void setCurrency(Currency currency) {
        this.currencyJComboBox.setSelectedItem(currency);
    }
}

