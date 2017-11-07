package hu.evave.eventfinder.model.converter;

import org.greenrobot.greendao.converter.PropertyConverter;

import hu.evave.eventfinder.model.Currency;


public class CurrencyConverter implements PropertyConverter<Currency, String> {

    @Override
    public Currency convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return Currency.HUF;
        }

        return Currency.valueOf(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(Currency entityProperty) {
        if (entityProperty == null) {
            return Currency.HUF.name();
        }

        return entityProperty.name();
    }

}
