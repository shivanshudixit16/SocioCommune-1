package com.sociocommune.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sociocommune.model.User;
import com.sociocommune.repository.UserRepository;

@Controller
public class WebController {

	@Autowired
	private UserRepository repository;

	@PostMapping("/signup")
	public String signup(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "password", required = false) String password,
			@RequestParam(name = "type", required = false) String type, Model model) {

		User user = new User(name, email, password, type);
		String attributeName,attributeValue;
		
		if (repository.fetchUserByEmail(email) == null) {
			repository.save(user);
			attributeName="signup";
			attributeValue="complete";			
		} else {
			attributeName="emailexists";
			attributeValue="true";			
		}
		model.addAttribute(attributeName, attributeValue);
		return "index";
	}
	
	@GetMapping("/signup")
	public String signup() {
		return "index";
		
	}
	
	@PostMapping("/profile")
	public String signin(@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "password", required = false) String password, Model model) {
		User user = repository.fetchUserByEmail(username);
		if (user == null) {
			model.addAttribute("signin","failed");
			return "index";			
		} else {
			if(password.equals(user.password))
				return "profile";
			else
				model.addAttribute("signin","failed");
				return "index";
		}
		
	}

	

}
