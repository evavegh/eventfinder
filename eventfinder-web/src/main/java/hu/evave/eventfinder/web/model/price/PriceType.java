package hu.evave.eventfinder.web.model.price;

public enum PriceType {

	NORMAL("Normál"),
	STUDENT("Diák"),
	ADULT("Felnőtt"),
	RETIRED("Nyugdíjas");
	
	private String name;

	private PriceType(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
