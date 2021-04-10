package com.smart.controller;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.service.EmailService;

@Controller
public class ForgotController {

	@Autowired
	BCryptPasswordEncoder pencoder;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	UserRepository userRepository;
	
	Random random =		new Random(1000);

	@RequestMapping("/forgot")
	public String openEmailForm() {
		System.out.println("////" + "prink is my nmae");
		return "forgot_email_form";
	}
	
	
	
	@PostMapping("/send-otp")
	public String sendOtp(@RequestParam("email") String email,HttpSession httpSession) throws MessagingException {

		//generating Otp of 4 digits
Integer otp =	random.nextInt(99999 );
System.out.println(otp);

//Write code to send Otp

String subject = "OTP From SCM";
String message ="<h1> OTP = " + otp + "</h1>";
String to =email;

boolean flag = this.emailService.sendEmail(subject, message, to);

if(flag) {
	System.out.println("otp by seession" + otp);
	httpSession.setAttribute("myotp", otp);
	httpSession.setAttribute("email", email);
	return "verify_otp";
}

else {
	return "forgot_email_form";
}
		
	}
	
	@PostMapping("/verify-otp")
	public String verifyOtp(@RequestParam("otp") Integer otp,HttpSession httpSession) {
		
		
		Integer myotp = (int) httpSession.getAttribute("myotp");
		String email = (String) httpSession.getAttribute("email");
		if(myotp.equals(otp) ) {
			
	User user =		this.userRepository.findByEmail(email);
	if(user == null) {
		return "forgot_email_form";

	}
	else {
		
		System.out.println("//// going to change password module");
		return "normal/password_change_form";

		
		
	}
			
			
		}
		else {
			System.out.println("Otp notmatches");

			return "verify_otp";
		}
		
	}
	
	@PostMapping("/change-password")
	public String changePassword(@RequestParam("newpassword") String newpassword,HttpSession httpSession) {

	String email = (String) httpSession.getAttribute("email");
	User user =		this.userRepository.findByEmail(email);
	user.setPassword(this.pencoder.encode(newpassword));
	this.userRepository.save(user);
	return "redirect:/signin";
	}

	
}


//change-password


