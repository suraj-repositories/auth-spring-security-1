package com.on14june.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.on14june.entity.User;
import com.on14june.service.UserService;


@Controller()
public class AuthController {

	@Autowired
	private UserService service;
	
	@GetMapping("/")
	public String home(Model model, Authentication authentication) {
		if(authentication != null) {
			User user = service.getUserByEmail(authentication.getName());
			model.addAttribute("user", user);
		}
		return "welcome";
	}
	
	@GetMapping("/login")
	public String loginPage(Authentication authentication) {
		if(authentication != null) {
			return "redirect:/";
		}
		return "login";
	}
	
	@GetMapping("/signup")
	public String signupPage(Model model, Authentication authentication) {
		if(authentication != null) {
			return "redirect:/";
		}
		model.addAttribute("user", new User());
		return "signup";
	}
	
	@PostMapping("/signup")
	public String signup(User user, Authentication authentication) {
		if(user!=null) {
			if(service.getUserByEmail(user.getEmail()) != null) {
				return "redirect:/signup";
			}
			user.setRole("USER");
			service.saveUser(user);
			return "redirect:/login";
		}
		
		return "redirect:/signup";
	}

	

	@GetMapping("/welcome")
	public String welcom() {
		return "redirect:/";
	}
	
	
}
