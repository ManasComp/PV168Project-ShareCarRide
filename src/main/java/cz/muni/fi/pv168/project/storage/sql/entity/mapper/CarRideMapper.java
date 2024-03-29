package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.business.model.CarRide;
import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Currency;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.CarRideEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.CategoryEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.CurrencyEntity;
import javax.inject.Inject;

public class CarRideMapper extends Mapper<CarRideEntity, CarRide> implements EntityMapper<CarRideEntity, CarRide> {


    private final DataAccessObject<CategoryEntity> categoryDao;
    private final EntityMapper<CategoryEntity, Category> categoryMapper;
    private final DataAccessObject<CurrencyEntity> currencyDao;
    private final EntityMapper<CurrencyEntity, Currency> currencyMapper;

    @Inject
    public CarRideMapper(DataAccessObject<CategoryEntity> categoryDao, EntityMapper<CategoryEntity, Category> categoryMapper,
                         DataAccessObject<CurrencyEntity> currencyDao, EntityMapper<CurrencyEntity, Currency> currencyMapper) {

        this.categoryDao = categoryDao;
        this.categoryMapper = categoryMapper;
        this.currencyDao = currencyDao;
        this.currencyMapper = currencyMapper;
    }

    @Override
    public CarRide mapToBusiness(CarRideEntity entity) {
        var category = categoryDao
                .findById(entity.getCategoryId())
                .map(categoryMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("Entity not found, id: " +
                        entity.getCategoryId()));

        var currency = currencyDao
                .findById(entity.getCurrencyId())
                .map(currencyMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("Entity not found, id: " +
                        entity.getCurrencyId()));


        return new CarRide(
                entity.getGuid(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getDistance(),
                entity.getFuelConsumption(),
                entity.getCostOfFuelPerLitre(),
                entity.getNumberOfPassengers(),
                entity.getCommission(),
                category,
                currency,
                entity.getNewestConversionRate(),
                entity.getDate()
        );
    }

    @Override
    protected CarRideEntity getEntity(CarRide entity, Long dbID) {

        var categoryEntity = categoryDao
                .findByGuid(entity.getCategory().getGuid())
                .orElseThrow(() -> new DataStorageException("Entity not found, guid: " +
                        entity.getCategory().getGuid()));

        var currencyEntity = currencyDao
                .findByGuid(entity.getCurrency().getGuid())
                .orElseThrow(() -> new DataStorageException("Entity not found, guid: " +
                        entity.getCurrency().getGuid()));

        return new CarRideEntity(
                dbID,
                entity.getGuid(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getDistance(),
                entity.getFuelConsumption(),
                entity.getCostOfFuelPerLitreInDollars(),
                entity.getNumberOfPassengers(),
                entity.getCommission(),
                categoryEntity.getId(),
                currencyEntity.getId(),
                entity.getConversionToDollars(),
                entity.getDate());
    }
}
