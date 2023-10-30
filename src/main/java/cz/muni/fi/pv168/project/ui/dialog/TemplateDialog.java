package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Currency;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.ui.model.adapters.ComboBoxModelAdapter;

import javax.swing.*;

public final class TemplateDialog extends EntityDialog<Template> {

    private final JTextField titleField = new JTextField();
    private final JTextField descriptionField = new JTextField();
    private final JTextField templateField = new JTextField();
    private final ComboBoxModel<Currency> currencyModel = new DefaultComboBoxModel<>(Currency.values());
    private final JComboBox<Template> templateComboBoxModel;
    private final JComboBox<Category> categoryJComboBox;
    private final JSpinner rateField = new JSpinner(new SpinnerNumberModel());
    private final JSpinner distanceField = new JSpinner(new SpinnerNumberModel());
    private final JSpinner fuelConsiumption = new JSpinner(new SpinnerNumberModel());
    private final JSpinner costOfFuel = new JSpinner(new SpinnerNumberModel());
    private final JSpinner numberOfPassengers = new JSpinner(new SpinnerNumberModel());
    private final JSpinner commision = new JSpinner(new SpinnerNumberModel());
    private final JCheckBox isChecked = new JCheckBox();


    private final Template template;

    public TemplateDialog(Template template, ListModel<Category> categoryModel, ListModel<Template> templateModel) {
        this.template = template;

        templateComboBoxModel = new JComboBox<>(new ComboBoxModelAdapter<>(templateModel));
        categoryJComboBox = new JComboBox<>(new ComboBoxModelAdapter<>(categoryModel));
        setValues();
        addFields();
    }

    private void setValues() {
        titleField.setText(template.getTitle());
        descriptionField.setText(template.getDescription());
        distanceField.setValue(template.getDistance());
        fuelConsiumption.setValue(template.getFuelConsumption());
        costOfFuel.setValue(template.getCostOfFuelPerLitre());
        numberOfPassengers.setValue(template.getNumberOfPassengers());
        commision.setValue(template.getCommission());
        categoryJComboBox.setSelectedItem(template.getCategory());
    }

    private void addFields() {
        add("Template", templateComboBoxModel);
        add("Title", titleField);
        add("Description", descriptionField);
        add("Distance", distanceField);
        add("Average Fuel Consumption (per 100km)", fuelConsiumption);
        add("Cost of Fuel (1l)", costOfFuel);
        add("Number of Passengers", numberOfPassengers);
        add("Commision (%)", commision);
        add("Category", categoryJComboBox);
        add("Count me in the calculation of per price person", isChecked);
    }

    private <T> T getSpinnerValue(JSpinner spinner) {
        try {
            spinner.commitEdit();
        } catch (java.text.ParseException e) {
        }
        T value = (T) spinner.getValue();
        return value;
    }

    @Override
    Template getEntity() {
        template.setTitle(titleField.getText());
        template.setDescription(descriptionField.getText());
//        template.setDistance(getSpinnerValue(distanceField));
//        fuelConsiumption.setValue(getSpinnerValue(fuelConsiumption));
//        template.setCostOfFuelPerLitre(getSpinnerValue(costOfFuel));
        template.setNumberOfPassengers(getSpinnerValue(numberOfPassengers));
//        template.setCommission(getSpinnerValue(commision));
        template.setCategory((Category) categoryJComboBox.getSelectedItem());
        return template;
    }
}