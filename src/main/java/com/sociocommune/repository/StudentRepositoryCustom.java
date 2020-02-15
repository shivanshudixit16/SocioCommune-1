package com.sociocommune.repository;


import com.sociocommune.model.Student;

public interface StudentRepositoryCustom {
	public Student fetchStudentByEmail(String email);
}
