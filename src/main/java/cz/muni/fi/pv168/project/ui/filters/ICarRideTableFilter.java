package cz.muni.fi.pv168.project.ui.filters;

import cz.muni.fi.pv168.project.business.model.CarRide;
import cz.muni.fi.pv168.project.business.model.Model;
import cz.muni.fi.pv168.project.ui.filters.matchers.EntityMatcher;
import cz.muni.fi.pv168.project.ui.model.TableModel;

import javax.swing.table.TableRowSorter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface ICarRideTableFilter {
    void filterByEntity(Model value, Filters filter);

    void filterByDate(Date fromDate, Date toDate);

    void filterByDistance(double fromDistance, double toDistance);

    void filterByPassengers(int passengersCount);

    void filterByName(String passengersCount);

    void removeFilter(Filters filter);

    RideCompoundMatcher getRideCompoundMatcher();

    class RideCompoundMatcher extends EntityMatcher<CarRide> {

        private final TableRowSorter<TableModel<CarRide>> rowSorter;

        private final Map<Filters, EntityMatcher<CarRide>> entityMatchers = new HashMap<>();

        public RideCompoundMatcher(TableRowSorter<TableModel<CarRide>> rowSorter) {
            this.rowSorter = rowSorter;
        }

        public void addMatcher(Filters key, EntityMatcher<CarRide> matcher) {
            entityMatchers.put(key, matcher);
            rowSorter.sort();
        }

        public void removeMatcher(Filters key) {
            entityMatchers.remove(key);
            rowSorter.sort();
        }

        public List<CarRide> getData() {
            return rowSorter.getModel().getAll().stream()
                    .filter(carRide -> entityMatchers.values().stream()
                            .allMatch(match -> match.evaluate(carRide))).toList();
        }

        @Override
        public boolean evaluate(CarRide carRide) {
            return entityMatchers.values().stream().allMatch(m -> m.evaluate(carRide));
        }
    }
}
