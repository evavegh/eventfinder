package hu.evave.eventfinder.web.web;

import javax.swing.UIManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import hu.evave.eventfinder.web.model.Event;
import hu.evave.eventfinder.web.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {

		return "/login";
	}
}
