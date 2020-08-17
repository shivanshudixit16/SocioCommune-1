package com.sociocommune.controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.sociocommune.model.Chat;
import com.sociocommune.model.JobPost;
import com.sociocommune.model.User;
import com.sociocommune.repository.ChatRepository;
import com.sociocommune.repository.JobPostRepository;
import com.sociocommune.repository.UserRepository;
import com.sociocommune.service.EmailService;

@Controller
@SessionAttributes("user")
public class WebController {

	@Autowired
	private UserRepository repository;

	@Autowired
	private JobPostRepository jobpostrepo;

	@Autowired
	private ChatRepository chatrepo;

	@Autowired
	private EmailService emailService;

	// emailService.sendMail(reciever's email,Subject ,Text);
	
	
	@GetMapping("/fpass")
	public String fpass()
	{
		return "fpass";
	}

	@PostMapping("/signup")
	public String signup(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "password", required = false) String password,
			@RequestParam(name = "type", required = false) String type, Model model,
			@ModelAttribute("user") User user) {
		
		String bpassword = Base64.getEncoder().encodeToString(password.getBytes());	

		User newuser = new User(name, email, bpassword, type, "", "", 0, 0);
		String attributeName, attributeValue;

		if (repository.fetchUserByEmail(email) == null) {
			repository.save(newuser);
			emailService.sendMail(email, "Welcome " + name, "Welcome to SRMS Connect");
			attributeName = "signup";
			attributeValue = "complete";
		} else {
			attributeName = "emailexists";
			attributeValue = "true";
		}
		model.addAttribute(attributeName, attributeValue);
		return "index";
	}

	@GetMapping("/signup")
	public String signup() {
		return "index";

	}

	@GetMapping("/chat")
	public String chat(@RequestParam(name = "rec", required = false) String rec,Model model,@ModelAttribute("user") User user) {
		model.addAttribute("rec_email", rec);
		model.addAttribute("sen_email", user.email);
		User tempuser = repository.fetchUserByEmail(rec);
		System.out.println(tempuser.name);
		model.addAttribute("rec_name", tempuser.name);
		return "chat";
	}

	@GetMapping("/get-message")
	@ResponseBody
	public List<Chat> getMessage(@RequestParam(name = "sen", required = false) String sender,
	@RequestParam(name = "rec", required = false) String receiver)
	{
		List <Chat> chats1=chatrepo.findChats(sender,receiver);
		List <Chat> chats2=chatrepo.findChats(receiver,sender);
		List<Chat> allChats = new ArrayList<Chat>();
		allChats.addAll(chats1);
		allChats.addAll(chats2);
		return allChats;
	}

	@PostMapping("/save-message")
	@ResponseBody
	public String saveMessage(@RequestParam(name = "sen", required = false) String sender,
	@RequestParam(name = "rec", required = false) String receiver,
	@RequestParam(name = "msg", required = false) String msg)
	{
		System.out.println(sender);
		System.out.println(receiver);
		System.out.println(msg);
		Chat ps= new Chat(sender,receiver,msg);
		chatrepo.save(ps);
		return "done";
	}
	
	@GetMapping("/update-like")
	@ResponseBody
	public String updateLike(@RequestParam(name = "id", required = false) String id)
	{

		Optional<JobPost> ps=jobpostrepo.findById(id);
		if(ps.isPresent()) {
			JobPost jb = ps.get();
			jb.likes++;
			jobpostrepo.save(jb);
			return  ""+jb.likes;
		}
		return "";
	}


	
	@PostMapping("/profile")
	public String signin(@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "password", required = false) String password, Model model,@ModelAttribute("user") User user) {
		User tempuser;
		String bpassword = Base64.getEncoder().encodeToString(password.getBytes());	
		if(user == null || user.email==null)
		{
			tempuser = repository.fetchUserByEmail(username);
		}
		else
		{
			return "redirect:profile";
		}
		if (tempuser == null) {
			model.addAttribute("signin","failed");
			return "index";			
		} else {
			
			if(bpassword.equals(tempuser.password))
			{
				user.copy(tempuser);
				System.out.println(user);
				String [] fol=user.following.split(",");
				List <JobPost> ls= new ArrayList<JobPost>();
				for(int i=1;i<fol.length;i++)
				{
					ls.addAll(jobpostrepo.fetchPostByEmail(fol[i]));
				}
				model.addAttribute("posts",ls);
				
				return "redirect:profile";
			}
			else
				model.addAttribute("signin","failed");
				return "index";
		}
		
	}

	@GetMapping("/profile")
	public String profile(Model model,@ModelAttribute("user") User user) {
		if (user == null || user.email==null) {
			model.addAttribute("signin","failed");
			return "index";			
		} else {
			String [] fol=user.following.split(",");
			List <JobPost> ls= new ArrayList<JobPost>();
			for(int i=1;i<fol.length;i++)
			{
				ls.addAll(jobpostrepo.fetchPostByEmail(fol[i]));
			}
			model.addAttribute("posts",ls);
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
	   @GetMapping(value="/following")
	   public String following(Model model,@ModelAttribute("user") User user)
	   {
		    
			System.out.println(user);
			List<User> followingUsers = new ArrayList<User>();
			String [] following=user.following.split(",");			
			for(int i=1;i<following.length;i++)
			{
				followingUsers.add(repository.fetchUserByEmail(following[i]));
			}
			model.addAttribute("folusers", followingUsers);
			
			model.addAttribute("type", "Following");
//			System.out.println(users);
			return "followers";
	   }
	   @GetMapping(value="/followers")
	   public String followers(Model model,@ModelAttribute("user") User user)
	   {
		    
			System.out.println(user);
			List<User> followers = new ArrayList<User>();
			String [] folow=user.followers.split(",");			
			for(int i=1;i<folow.length;i++)
			{
				followers.add(repository.fetchUserByEmail(folow[i]));
			}
			model.addAttribute("folusers", followers);
			model.addAttribute("type", "Followers");
//			System.out.println(users);
			return "followers";
	   }
	@PostMapping(value="/follow")
	public String Follow(@RequestParam(name="id", required=false) String email,Model model,@ModelAttribute("user") User user)
	{
		String [] following=user.following.split(",");
		if(Arrays.asList(following).contains(email))
		{
			return "profile";
		}
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
			@RequestParam(name = "job_type", required = false) String type,
			@RequestParam(name = "description", required = false) String description, Model model,@ModelAttribute("user") User user) {
				
				JobPost ps= new JobPost();
				ps.title=title;
				ps.skills=skills;
				ps.salary=salary;
				ps.type=type;
				ps.description=description;
				ps.userEmail=user.email;
				ps.userName=user.name;
				ps.likes=0;
				jobpostrepo.save(ps);
				return "redirect:profile";
			}
	
	@PostMapping("/fpassword")
	public String ForgotPassword(@RequestParam(name = "email", required = true) String email,Model model)
	{
		User euser= repository.fetchUserByEmail(email);
		System.out.println(euser.password);
		if(euser!=null)
		{
			String dpassword=new String(Base64.getDecoder().decode(euser.password));
			System.out.println(euser.password);
			emailService.sendMail(email, "Password","Dear "+euser.getName()+" , the Password for your account is "+dpassword);
		}
		model.addAttribute("msg", "Password Sent To Your Mail Address");
		return "fpass";
	}
	
	@ModelAttribute("user")
    public User user() {
        return new User();

	}
}
