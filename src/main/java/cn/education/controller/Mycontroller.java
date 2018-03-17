package cn.education.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Mycontroller {

	@RequestMapping("/list")
	public String getStudents() {
		
		return "student/index";
	}
}
