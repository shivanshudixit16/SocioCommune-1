package com.sociocommune.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.sociocommune.model.*;
import com.sociocommune.repository.*;
import com.sociocommune.service.EmailService;

@Controller
@SessionAttributes("user")
public class WebController {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private JobPostRepository jobpostrepo;
	
	@Autowired
	private EmailService emailService;

	//emailService.sendMail(reciever's email,Subject ,Text);
	
	@PostMapping("/signup")
	public String signup(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "password", required = false) String password,
			@RequestParam(name = "type", required = false) String type, Model model,@ModelAttribute("user") User user) {

		User newuser=new User(name, email, password, type, "", "", 0, 0);
		String attributeName,attributeValue;
		
		if (repository.fetchUserByEmail(email) == null) {
			repository.save(newuser);
			emailService.sendMail(email, "Welcome "+ name , "Welcome to SRMS Connect" );
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
			@RequestParam(name = "password", required = false) String password, Model model,@ModelAttribute("user") User user) {
		User tempuser;
		if(user == null || user.email==null)
		{
			tempuser = repository.fetchUserByEmail(username);
		}
		else
		{
			return "profile";
		}
		if (tempuser == null) {
			model.addAttribute("signin","failed");
			return "index";			
		} else {
			
			if(password.equals(tempuser.password))
			{
				user.copy(tempuser);
				System.out.println(user);
				return "profile";
			}
			else
				model.addAttribute("signin","failed");
				return "index";
		}
		
	}

	@GetMapping("/profile")
	public String profile(Model model,@ModelAttribute("user") User user) {
		if (user == null) {
			model.addAttribute("signin","failed");
			return "index";			
		} else {
			model.addAttribute("loggeduser",user);
			return "profile";
		}
	}

	@GetMapping(value="/search")
	   public String Search(@RequestParam(name="search", required=false) String str,Model model,@ModelAttribute("user") User user)
	   {
		    
		    System.out.println(user);
		    List<User> users=repository.findUsers(str,user);
		    model.addAttribute("users", users);
//			System.out.println(users);
			return "search";
	   }
	@PostMapping(value="/follow")
	public String Follow(@RequestParam(name="id", required=false) String email,Model model,@ModelAttribute("user") User user)
	{
		User user1= repository.fetchUserByEmail(email);
		System.out.println(user);
		System.out.println(user1);
		user1.followercount++;
		user.followingcount++;
		user1.followers=user1.followers +"," +user.email;
		user.following=user.following +"," +user1.email;
		repository.save(user);
		repository.save(user1);
		return "profile";
	}
	@GetMapping(value="/logout")
	public String Logout(@RequestParam(name="id", required=false) String email,Model model,@ModelAttribute("user") User user)
	{
		user = null;
		return "index";
	}
	@PostMapping("/postjob")
	public String postjob(@RequestParam(name = "title", required = false) String title,
			@RequestParam(name = "skills", required = false) String skills,
			@RequestParam(name = "salary", required = false) String salary,
			@RequestParam(name = "type", required = false) String type,
			@RequestParam(name = "desription", required = false) String desription, Model model,@ModelAttribute("user") User user) {
				
				JobPost ps= new JobPost();
				ps.title=title;
				ps.skills=skills;
				ps.salary=salary;
				ps.type=type;
				ps.description=desription;
				ps.userEmail=user.email;
				jobpostrepo.save(ps);
				return "profile";
			}
	@ModelAttribute("user")
    public User user() {
        return new User();

	}
}
