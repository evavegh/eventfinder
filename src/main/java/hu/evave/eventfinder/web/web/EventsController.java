package hu.evave.eventfinder.web.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EventsController {

	@RequestMapping("/")
	public String home() {
		return "index";
	}

}