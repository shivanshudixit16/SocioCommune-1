package com.sociocommune.controller;

import java.util.List;

import com.sociocommune.model.User;
import com.sociocommune.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("user")
public class WebController {

	@Autowired
	private UserRepository repository;

	@PostMapping("/signup")
	public String signup(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "password", required = false) String password,
			@RequestParam(name = "type", required = false) String type, Model model,@ModelAttribute("user") User user) {

		User newuser=new User(name, email, password, type, null, null, 0, 0);
		String attributeName,attributeValue;
		
		if (repository.fetchUserByEmail(email) == null) {
			repository.save(newuser);
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
			@RequestParam(name = "password", required = false) String password, Model model,@ModelAttribute("User") User user) {
		User tempuser;
		if(user == null)
		{
			tempuser = repository.fetchUserByEmail(username);
		}
		else
		{
			return "profile";
		}
		model.addAttribute("loggeduser",user);
		if (tempuser == null) {
			model.addAttribute("signin","failed");
			return "index";			
		} else {
			
			if(password.equals(tempuser.password))
			{
				user=tempuser;
				model.addAttribute("loggeduser",user);
				return "profile";
			}
			else
				model.addAttribute("signin","failed");
				return "index";
		}
		
	}

	@GetMapping("/profile")
	public String profile(Model model,@ModelAttribute("User") User user) {
		if (user == null) {
			model.addAttribute("signin","failed");
			return "index";			
		} else {
			model.addAttribute("loggeduser",user);
			return "profile";
		}
	}

	@GetMapping(value="/search")
	   public String Search(@RequestParam(name="search", required=false) String str,Model model)
	   {
		    User user=(User)model.getAttribute("loggeduser");
		    System.out.println(user);
		    List<User> users=repository.findUsers(str,user);
		    model.addAttribute("users", users);
			System.out.println(users);
			return "search";
	   }
	@PostMapping(value="/follow")
	public String Follow(@RequestParam(name="id", required=false) String email,Model model)
	{
		User currnetuser=(User)model.getAttribute("loggeduser");
		User user = repository.fetchUserByEmail(email);
		user.followercount++;
		currnetuser.followingcount++;
		return "profile";
	}
	@ModelAttribute("user")
    public User user() {
        return new User();

    }
}
