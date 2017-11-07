package hu.evave.eventfinder.model.converter;

import org.greenrobot.greendao.converter.PropertyConverter;

import hu.evave.eventfinder.model.PriceType;


public class PriceTypeConverter implements PropertyConverter<PriceType, String> {

    @Override
    public PriceType convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return null;
        }

        return PriceType.valueOf(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(PriceType entityProperty) {
        if (entityProperty == null) {
            return null;
        }

        return entityProperty.name();
    }

}
