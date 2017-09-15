package hu.evave.eventfinder.web.web.client;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController2 {
	@RequestMapping("/api/hi")
	public String hi() {
		return "Hello World from Restful API";
	}
}
