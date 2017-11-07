package hu.evave.eventfinder.model.converter;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.math.BigDecimal;


public abstract class BigDecimalConverter implements PropertyConverter<BigDecimal, Long> {

    private final BigDecimal scale;

    BigDecimalConverter() {
        this(BigDecimal.valueOf(100L));
    }

    BigDecimalConverter(BigDecimal scale) {
        this.scale = scale;
    }

    @Override
    public BigDecimal convertToEntityProperty(Long databaseValue) {
        if (databaseValue == null) {
            return BigDecimal.ZERO;
        }

        return BigDecimal.valueOf(databaseValue).divide(scale);
    }

    @Override
    public Long convertToDatabaseValue(BigDecimal entityProperty) {
        if (entityProperty == null) {
            return 0L;
        }

        return entityProperty.multiply(scale).longValue();
    }

}
