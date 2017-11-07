package hu.evave.eventfinder.model.converter;

import java.math.BigDecimal;


public class CoordinateConverter extends BigDecimalConverter {

    public CoordinateConverter() {
        super(BigDecimal.valueOf(100000000L));
    }
}
