package com.sociocommune.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sociocommune.model.Student;
import com.sociocommune.model.User;
import com.sociocommune.repository.UserRepository;

import com.sociocommune.repository.StudentRepository;

@Controller
public class WebController {
	
	@Autowired
	  private StudentRepository repository;
	
	@PostMapping("/signup")
	public String signup(
			@RequestParam(name="fname", required=false)String FName,
			@RequestParam(name="lname", required=false)String LName,
			Model model) {
		model.addAttribute("FName",FName);
		model.addAttribute("LName",LName);
	    repository.deleteAll();
	    //repository.save(new User(FName, LName));
	    
	    
	    
		return "results";
	}
	
	@GetMapping("/signin")
	public String singin() {
		return "sign-in";
	}
	@PostMapping("/profile")
	public String profile(
			@RequestParam(name="name", required=false)String name,
			@RequestParam(name="email", required=false)String email,
			@RequestParam(name="phone", required=false)String phone,
			@RequestParam(name="branch", required=false)String branch,
			@RequestParam(name="batch", required=false)String batch,
			@RequestParam(name="password", required=false)String password,
			Model model){
		Student student = new Student(name, email, phone, branch, batch, password);
		repository.deleteAll();
		repository.save(student);
		
		Student S = repository.fetchStudentByEmail(email);
		model.addAttribute("student", student);
		System.out.println(S.getName());
		return "profile";
	}
	@GetMapping("/test")
	public String test(
			@RequestParam(name="name", required=false)String name,
			@RequestParam(name="email", required=false)String email,
			@RequestParam(name="phone", required=false)String phone,
			@RequestParam(name="branch", required=false)String branch,
			@RequestParam(name="batch", required=false)String batch,
			@RequestParam(name="password", required=false)String password,
			Model model) {
		model.addAttribute("name", name);
		model.addAttribute("email", email);
		model.addAttribute("phone", phone);
		model.addAttribute("branch", branch);
		model.addAttribute("batch", batch);
		model.addAttribute("password", password);
		Student student = new Student(name, email, phone, branch, batch, password);
		repository.deleteAll();
		repository.save(student);
		/*User user = new User("d4rk","r0n1n");
		repository.deleteAll();
		repository.save(user);
		User S=repository.findByFirstName("d4rk");
		System.out.println(S);
		
		System.out.println(repository.fetchPersonByFNameAndLName("d4rk", "r0n1n"));
		*/
		return "test";
	}
}