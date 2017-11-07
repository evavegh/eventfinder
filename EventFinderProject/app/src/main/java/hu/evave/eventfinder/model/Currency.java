package hu.evave.eventfinder.model;

import java.math.BigDecimal;
import java.text.NumberFormat;


public enum Currency {
    HUF("Ft", false),
    USD("$", true),
    EUR("€", true);

    Currency(String symbol, boolean placeBefore) {
        this.symbol = symbol;
        this.placeBefore = placeBefore;
    }

    private String symbol;
    private boolean placeBefore;

    @Override
    public String toString() {
        return symbol;
    }

    public String format(BigDecimal amount) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        if (placeBefore) {
            return toString() + ' ' + numberFormat.format(amount);
        }

        return numberFormat.format(amount) + ' ' + toString();
    }
}
