package hu.evave.eventfinder.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.evave.eventfinder.web.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long>{

}
