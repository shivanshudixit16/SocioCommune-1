package com.sociocommune.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sociocommune.model.Student;
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
		if (repository.fetchUserByEmail(email) == null) {
			repository.save(user);
			model.addAttribute("signup", "complete");
			return "index";
		} else {
			model.addAttribute("emailexists", "true");
			return "index";
		}
	}

	@ResponseBody
	@PostMapping("/signin")
	public String signin(@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "password", required = false) String password,
			Model model) {
		
		
		return username+password;

	}

}
