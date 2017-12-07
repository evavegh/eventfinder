package hu.evave.eventfinder.web.mapper;

import org.mapstruct.Mapper;

import hu.evave.eventfinder.web.model.Event;
import hu.evave.eventfinder.web.model.Location;
import hu.evave.eventfinder.web.model.price.Price;
import hu.evave.eventfinder.web.model.user.User;
import hu.evave.eventfinder.web.model.user.UserSettings;
import hu.evave.eventfinder.web.rest.resource.EventResource;
import hu.evave.eventfinder.web.rest.resource.LocationResource;
import hu.evave.eventfinder.web.rest.resource.PriceResource;
import hu.evave.eventfinder.web.rest.resource.UserResource;
import hu.evave.eventfinder.web.rest.resource.UserSettingsResource;

@Mapper(componentModel = "spring")
public interface EventFinderMapper {

	EventResource toResource(Event event);

	UserResource toResource(User user);

	LocationResource toResource(Location location);

	PriceResource toResource(Price price);

	UserSettingsResource toResource(UserSettings settings);


}
