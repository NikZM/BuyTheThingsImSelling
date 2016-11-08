package com.fdmgroup.buythethingsisell.springcontrollers;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fdmgroup.buythethingsisell.entities.User;
import com.fdmgroup.buythethingsisell.jdbc.userfunctions.RegisterNewUser;

@Controller
public class LoginPage {
	
	@Resource
	private RegisterNewUser regNewUser;
	@Resource
	SecurityContextUserName securityContextUserName;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView model, @RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout) {
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}
		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");
		return model;
	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied(ModelAndView model) {
		String email = securityContextUserName.getUserName();
		if (email != null ) {
			model.addObject("username", email);
		}
		model.setViewName("403");
		return model;
	}
	
	@RequestMapping(value="/register", method = RequestMethod.GET)
	public String getRegistrationPage(Model model){
		model.addAttribute("user", new User());
		return "register";
	}
	
	@RequestMapping(value="/register/submit", method = RequestMethod.POST)
	public String registrationSubmit(@ModelAttribute User user, Model model){
		String passUnHashed = user.getPasswordHashed();
		user.setPasswordUnHashed(passUnHashed);
		boolean b = regNewUser.registerNewUser(user);
		if (b){
			return "index";
		} else {
			model.addAttribute(b);
			return "register";
		}
	}
}