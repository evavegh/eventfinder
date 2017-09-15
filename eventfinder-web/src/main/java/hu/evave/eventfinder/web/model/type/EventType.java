package hu.evave.eventfinder.web.model.type;

public enum EventType {

	AUTOMOTIVE("Autó/motor"),
	FESTIVAL("Fesztivál"),
	GASTRO("Gasztro"),
	KIDS("Gyerekeknek"),
	TRADITION("Hagyományőrző"),
	EXHIBITION("Kiállítás"),
	CULTURE("Kultúra"),
	SIGHT("látnivalók"),
	CINEMA("Mozi"),
	SPORT("Sport"),
	OUTDOOR("Szabadtéri"),
	THEATRE("Színház"),
	MUSIC("Zene"),
	MUSEUM("Múzeum"),
	CONCERT("Koncert"),
	OTHER("Egyéb");
	
	private String name;
	
	private EventType(String name) {
		this.name= name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public String getName() {
		return name;
	}

}
