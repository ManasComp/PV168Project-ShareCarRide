package cz.muni.fi.pv168.project.ui.panels.CarRide;


import cz.muni.fi.pv168.project.business.model.CarRide;
import cz.muni.fi.pv168.project.business.service.statistics.ICarRideStatistics;
import cz.muni.fi.pv168.project.ui.filters.ICarRideTableFilter;
import cz.muni.fi.pv168.project.ui.model.TableModel;
import cz.muni.fi.pv168.project.ui.panels.commonPanels.NameValuePanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CarRideStatisticsPanel extends JPanel {
    private final TableModel<CarRide> model;
    private final NameValuePanel filteredDistance = new NameValuePanel("Filtered distance:");
    private final NameValuePanel filteredExpenses = new NameValuePanel("Filtered expenses:");
    private final NameValuePanel todayExpenses = new NameValuePanel("Today expenses:");
    private final NameValuePanel totalDistance = new NameValuePanel("Total distance:");
    private final NameValuePanel totalExpenses = new NameValuePanel("Total expenses:");
    private final NameValuePanel totalRevenues = new NameValuePanel("Total revenues:");
    private final NameValuePanel totalRides = new NameValuePanel("Total rides:");
    private final ICarRideStatistics ICarRideStatistics;
    private final ICarRideTableFilter carRideTableFilter;

    public CarRideStatisticsPanel(TableModel<CarRide> model, ICarRideTableFilter carRideTableFilter, ICarRideStatistics ICarRideStatistics1) {
        super(new FlowLayout(FlowLayout.LEFT));
        this.model = model;
        this.ICarRideStatistics = ICarRideStatistics1;
        this.carRideTableFilter = carRideTableFilter;

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JPanel filteredRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(filteredRow, BorderLayout.PAGE_START);
        filteredRow.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 0));
        filteredRow.add(filteredDistance);
        filteredRow.add(filteredExpenses);
        filteredRow.add(todayExpenses);

        JPanel totalRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(totalRow, BorderLayout.CENTER);
        totalRow.setBorder(BorderFactory.createEmptyBorder(0, 3, 3, 0));
        totalRow.add(totalDistance);
        totalRow.add(totalRides);
        totalRow.add(totalRevenues);
        totalRow.add(totalExpenses);

        updateFilteredStats();
        updateTotalStats();
    }

    public void updateFilteredStats() {
        var entities = carRideTableFilter.getRideCompoundMatcher().getData();
        filteredDistance.setValue(String.format("%.2f", ICarRideStatistics.getTotalDistance(entities)));
        filteredExpenses.setValue(String.format("%.2f", ICarRideStatistics.getTotalExpenses(entities)));
    }

    public void updateTotalStats() {
        var entities = model.getAllEntities();
        totalDistance.setValue(String.format("%.2f", ICarRideStatistics.getTotalDistance(entities)));
        totalRides.setValue(ICarRideStatistics.getTotalRides(entities).toString());
        totalRevenues.setValue(String.format("%.2f", ICarRideStatistics.getTotalRevenues(entities)));
        totalExpenses.setValue(String.format("%.2f", ICarRideStatistics.getTotalExpenses(entities)));
        todayExpenses.setValue(String.format("%.2f", ICarRideStatistics.getTodayExpenses(entities)));
    }
}
