package hu.evave.eventfinder.web.web.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import hu.evave.eventfinder.web.model.user.Role;
import hu.evave.eventfinder.web.model.user.User;
import hu.evave.eventfinder.web.repository.UserRepository;
import hu.evave.eventfinder.web.service.user.UserService;
import hu.evave.eventfinder.web.service.user.UserValidator;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserValidator userValidator;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Map<String, Object> model) {
		User user = new User();
		user.setId(-1L);
		user.setPassword("password");
		user.addRole(Role.ADMIN);
		userService.save(user);
		model.put("userForm", user);
		System.out.println(user.getName() + user.getId());
		return "registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registration(@RequestBody User userForm, BindingResult bindingResult, Model model) {
		System.out.println(userForm);
		System.out.println(userForm.getId());
		System.out.println(userForm.getName());
		System.out.println(userForm.getEmail());
		userValidator.validate(userForm, bindingResult);

//		if (bindingResult.hasErrors()) {
//			System.out.println(bindingResult.getAllErrors().toString());
//			return "registration";
//		}
		
		userForm.addRole(Role.REGISTERED);

		userService.save(userForm);
		userService.sendEmail(userForm);

		return "redirect:/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(Model model) {
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/settings", method = RequestMethod.GET)
	public String edit(Map<String, Object> model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		model.put("user", userRepository.findByName(auth.getName()));
		model.put("allRoles", Role.values());
		return "settings";

	}

	@RequestMapping(value = "/settings", method = RequestMethod.POST)
	public @ResponseBody User edit(@RequestBody User user) {
		userService.save(user);
		return user;

	}

}
