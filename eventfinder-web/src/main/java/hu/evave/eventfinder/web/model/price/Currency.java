package hu.evave.eventfinder.web.model.price;

public enum Currency {

	HUF("Ft"), USD("$"), EUR("€");

	private String symbol;

	Currency(String name) {
		this.symbol = name;
	}

	@Override
	public String toString() {
		return symbol;
	}

	public String getSymbol() {
		return symbol;
	}
}
