package com.sociocommune.model;

import org.springframework.stereotype.Component;

import org.springframework.data.annotation.Id;
@Component
public class Student {
	@Id
	  public String id;

	  public String name;
	  public String email;
	  public String phone;
	  public String branch;
	  public String batch;
	  public String password;
	  public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}



	
	  
	  public Student(String name, String email, String phone, String branch, String batch, String password) {
			
			
			this.name = name;
			this.email = email;
			this.phone = phone;
			this.branch = branch;
			this.batch = batch;
			this.password = password;
		}
	  public Student() {}

	  

	  @Override
	  public String toString() {
	    return String.format(
	        "Student[id=%s, name='%s', email='%s', phone='%s', branch='%s', batch='%s', password='%s']",
	        id, name, email, phone, branch, batch, password);
	  }


}
