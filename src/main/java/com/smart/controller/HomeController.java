package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.dao.UserRepository;
import com.smart.entities.User;

@Controller
public class HomeController {

	
	@Autowired
	private UserRepository userrepository;
	
	@GetMapping("/home") 
	public String test(Model model) {
		
	model.addAttribute("title", "Home Page - Smart Contact Manager");
		
		
		return "home"; 
	}


	@GetMapping("/about") 
	public String about(Model model) {
		
	model.addAttribute("title", "About - Smart Contact Manager");
		
		
		return "about"; 
	}



}
