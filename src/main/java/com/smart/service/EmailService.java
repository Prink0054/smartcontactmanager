package com.smart.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

	public boolean sendEmail(String subject,String message,String to) throws MessagingException {
		
		boolean f = false;
		
		String from = "turna.prink54@gmail.com";
		
		String host = "smtp.gmail.com";

		// get system properties
		Properties properties = System.getProperties();
		System.out.println("Properties" + properties);

		// setting im information to property object

		// host
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		// Step 1 : tog et sesion object
		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication("turna.prink54@gmail.com", "Kewal.Prink54");
			}

		});

		session.setDebug(true);

//Step 2 :Compose the message

		MimeMessage message1 = new MimeMessage(session);

//from
		message1.setFrom(from);
//adding ecepite	nt to message
		message1.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//addig subject to message
		message1.setSubject(subject);
//adding text to message
		message1.setText(message);

//send Step : 3

		Transport.send(message1);

		System.out.println("sent");
		f = true;
		
		return true;
		
		
		
	}
	
}
