package com.smart.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;

@Controller
public class HomeController {

	
	@Autowired
	private BCryptPasswordEncoder passwordEncode;
	
	@Autowired
	private UserRepository userepository;
	
	@GetMapping("/") 
	public String test(Model model) {
		
	model.addAttribute("title", "Home Page - Smart Contact Manager");
		
		
		return "home"; 
	}


	@GetMapping("/about") 
	public String about(Model model) {
		
	model.addAttribute("title", "About - Smart Contact Manager");
		
		
		return "about"; 
	}

	@GetMapping("/signup") 
	public String signUp(Model model) {
		
	model.addAttribute("title", "Register - Smart Contact Manager");
		
	model.addAttribute("user", new User());
		
		return "signup"; 
	}
	
	
	@PostMapping("/do_register") 
	public String registerUser(@Valid  @ModelAttribute("user") User user,@RequestParam(value = "agreement",defaultValue = "false") boolean agreement,Model model,BindingResult results,HttpSession httpSession ) {
		
		try {
			
			if(!agreement) {
				
				System.out.println("You have not agreed the terms and conditions");
				throw	new Exception("You have not agreed the terms and conditions");
				
			}
			
			if(results.hasErrors()) {
				System.out.println("error" + results.toString());
				model.addAttribute("user",user );
				
				return "signup";
			}
			
			//role is NORMAL user "ROLE_USER"
			//for admin user "ROLE_ADMIN"
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setImageUrl("defualt.png");
			user.setPassword(passwordEncode.encode(user.getPassword()));
			
			
			
			
			
			System.out.println(agreement);
			System.out.println("User" + user);
			
		User result =	this.userepository.save(user);
			
//			
		model.addAttribute("user", new User());
		httpSession.setAttribute("message", new Message("SuccesFully Registered" , "alert-success"));
		return "signup";
			
			
		}catch (Exception e) {

		e.printStackTrace();
		model.addAttribute("user",user );
		httpSession.setAttribute("message", new Message("Something Went Wrong" + e.getMessage(), "alert-danger"));
		return "signup";
		}
		
		//return "signup"; 
	}
	
	
	
	@GetMapping("/signin")
	public String customLogin(Model model) {
		
		model.addAttribute("title", "Login Page - Smart Contact Manager");

		System.out.println("/////");
		
		return "login";
	}

}
