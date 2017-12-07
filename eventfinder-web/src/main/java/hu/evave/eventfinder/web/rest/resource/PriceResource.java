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

	public PriceResource() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public PriceType getType() {
		return type;
	}

	public void setType(PriceType type) {
		this.type = type;
	}

	

}
