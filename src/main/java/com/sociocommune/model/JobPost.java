package com.sociocommune.model;
import org.springframework.stereotype.Component;

import org.springframework.data.annotation.Id;

@Component
public class JobPost {
    @Id
	public String id;
    public String title;
    public String skills;
	public String salary;
	public String description;
    public String type;
    public String userEmail;
    


    public JobPost() {}
    public JobPost(String id, String title, String skills, String salary, String description, String type, String userEmail) {
		this.id = id;
		this.title = title;
		this.skills = skills;
		this.salary = salary;
		this.description = description;
        this.type = type;
        this.userEmail=userEmail;
    }
}