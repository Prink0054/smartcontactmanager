package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ContactRepository contactRepository;
	
	// Adding common data to response
	@ModelAttribute
	public void addCommonData(Model model,Principal principal) {
		
		String userName = principal.getName();

		User user =		this.userRepository.findByEmail(userName);
				

		model.addAttribute("user", user);
		
	}
	
	
	//Dashboardhome
	@GetMapping("/index")
	public String dashboard(Model model,Principal principal) {

		model.addAttribute("title", "User Dashboard");

	
		return "normal/user_dashboard";
	}
	
	//
	@GetMapping("/add-contact")
	public String openAddContactForm(Model model) {
		
		model.addAttribute("title", "Add Contacts");

		model.addAttribute("contact", new Contact());
		
		return "normal/add_contact_form";
	}
	
	
	
	@PostMapping("/process")
	public String processContact(@ModelAttribute("contact") Contact contact,@RequestParam("profileImage")MultipartFile file,Principal principal) {
		
		
		try {
			String name= principal.getName();
			User user = this.userRepository.findByEmail(name);
			
			
			//processing and uploading file
			
			if(file.isEmpty()) {
				
				
			}
			else {
				
				//upload the file to folder and update the name ot date
			contact.setImage(file.getOriginalFilename());
	File savedFile = new ClassPathResource("static/img").getFile();
	
Path path = 	Paths.get(savedFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
	
	
Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);	
System.out.println("Image is uploaded");
			}
			
			
			


			contact.setUser(user);
			contactRepository.save(contact);

			
			userRepository.save(user);
			System.out.println("Data" + contact);
			
			
		}
		
		
		catch (Exception e) {
System.out.println(e.getMessage() + "error");
		}
		
		return "normal/add_contact_form";

		
		
	}
	
	//show contatcs handler
	// per page = 5[n]
	//current page = 0 [page]
	@GetMapping("/show-contacts/{page}")
	public String showContacts(@PathVariable("page")Integer page,Model model,Principal principal) {
		
		model.addAttribute("Title", "Show User Contacts");
		
		//list of Contacts
		
		String username = principal.getName();
		
	//User user =	userRepository.findByEmail(username);
	//List<Contact> contacts =	user.getContact();
		
	User user1 =	userRepository.findByEmail(username);
		
Pageable pageRequest =	PageRequest.of(	page, 5);
		
Page<Contact> contact =	this.contactRepository.findContactsByUser(user1.getId(),pageRequest);	
	
model.addAttribute("contacts", contact);
model.addAttribute("currentpage",page);
model.addAttribute("totalpages",contact.getTotalPages());

	
		return "normal/show_contacts";
	}
	
}
