package com.smart.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "USER")
public class User {

	
//	@Override
//	public String toString() {
//		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
//				+ ", enabled=" + enabled + ", imageUrl=" + imageUrl + ", about=" + about + ", contact=" + contact
//				+ "]";
//	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotBlank(message = "Name should not be Blank")
	@Size(max = 20,min = 5,message = "Minimum 5 and max 20 char are allowed")
	private String name;
	@Column(unique = true)
	private String email;
	private String password;
	private String role;
	private boolean enabled;
	private String imageUrl;
	@Column(length = 50)
	private String about;
	//mappedBy = "user"
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
	private List<Contact> contact = new ArrayList<Contact>();
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public List<Contact> getContact() {
		return contact;
	}
	public void setContact(List<Contact> contacts) {
		this.contact = contact;
	}
	
	
	
	
}
