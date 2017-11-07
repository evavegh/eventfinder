package hu.evave.eventfinder.model;


import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

import java.math.BigDecimal;

import hu.evave.eventfinder.R;
import hu.evave.eventfinder.app.App;
import hu.evave.eventfinder.model.converter.CurrencyConverter;
import hu.evave.eventfinder.model.converter.PriceConverter;
import hu.evave.eventfinder.model.converter.PriceTypeConverter;


@Entity(nameInDb = "price")
public class Price {

    @Id(autoincrement = true)
    @Property(nameInDb = "id")
    private Long id;

    @Property(nameInDb = "event_id")
    private String eventId;

    @Property(nameInDb = "currency")
    @Convert(columnType = String.class, converter = CurrencyConverter.class)
    @NotNull
    private Currency currency;

    @Property(nameInDb = "amount")
    @Convert(columnType = Long.class, converter = PriceConverter.class)
    @NotNull
    private BigDecimal amount;

    @Property(nameInDb = "type")
    @Convert(columnType = String.class, converter = PriceTypeConverter.class)
    private PriceType type;


    @Generated(hash = 585659531)
    public Price(Long id, String eventId, @NotNull Currency currency,
                 @NotNull BigDecimal amount, PriceType type) {
        this.id = id;
        this.eventId = eventId;
        this.currency = currency;
        this.amount = amount;
        this.type = type;
    }

    @Generated(hash = 812905808)
    public Price() {
    }


    @Override
    @Keep
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price = (Price) o;

        return id != null ? id.equals(price.id) : price.id == null;

    }

    @Override
    @Keep
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    @Keep
    public String toString() {
        StringBuilder result = new StringBuilder();

        if (type != null) {
            result.append(type);
            result.append(": ");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) == 0) {
            result.append(App.getContext().getString(R.string.free));
        } else {
            result.append(currency.format(amount));
        }

        return result.toString();
    }

    public PriceType getType() {
        return this.type;
    }

    public void setType(PriceType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getEventId() {
        return this.eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
