package hu.evave.eventfinder.web.rest.resource;

import java.util.List;
import java.util.stream.Collectors;

import hu.evave.eventfinder.web.model.user.User;

public class UserResource {
	
    private long id;
    private String name;
    private String email;
    private List<String> roles;
    
    public UserResource() {
	}
    
	public UserResource(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.roles = user.getRoles().stream().map(r -> r.name()).collect(Collectors.toList());
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
    
    

}
