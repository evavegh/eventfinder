package hu.evave.eventfinder.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.evave.eventfinder.web.model.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByEmail(String email);

	public User findByName(String username);
	
}
