package com.sociocommune.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sociocommune.entity.User;
import com.sociocommune.repository.UserRepository;


@Controller
public class WebController {
	
	@Autowired
	  private UserRepository repository;
	
	@PostMapping("/signup")
	public String signup(
			@RequestParam(name="fname", required=false)String FName,
			@RequestParam(name="lname", required=false)String LName,
			Model model) {
		model.addAttribute("FName",FName);
		model.addAttribute("LName",LName);
	    repository.deleteAll();
	    repository.save(new User(FName, LName));
		return "results";
	}
}