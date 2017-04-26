package hu.evave.eventfinder.web.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
        return "registration";
    }
    
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
        	System.out.println(bindingResult.getAllErrors().toString());
            return "registration";
        }
        userService.save(userForm);

        return "redirect:/login";
    }

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "/login";
	}

	@RequestMapping(value = "/settings", method = RequestMethod.GET)
	public String edit(Map<String, Object> model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		model.put("user", userRepository.findByName(auth.getName()));
		model.put("allRoles", Role.values());
		return "settings";

	}

	@RequestMapping(value = "/settings", method = RequestMethod.POST)
	public String edit(@ModelAttribute("user") User user) {
		userRepository.save(user);
		return "redirect:/settings";

	}
}
