package hu.evave.eventfinder.web.rest.resource;

import java.math.BigDecimal;

import hu.evave.eventfinder.web.model.price.Currency;
import hu.evave.eventfinder.web.model.price.Price;
import hu.evave.eventfinder.web.model.price.PriceType;

public class PriceResource {

	private Long id;

	private Currency currency;

	private BigDecimal amount;

	private PriceType type;

	public PriceResource(Price price) {
		id = price.getId();
		currency = price.getCurrency();
		amount = price.getAmount();
		type = price.getType();
	}

	public Long getId() {
		return id;
	}

	public Currency getCurrency() {
		return currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public PriceType getType() {
		return type;
	}

}
