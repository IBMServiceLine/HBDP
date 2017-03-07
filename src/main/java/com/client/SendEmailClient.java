package com.client;
// File Name SendHTMLEmail.java

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmailClient {

	private static Properties properties = new Properties();

	private final static String username = "pshandil";
	private final static String password = "Posi@678";

	public static void main(String[] args) {

		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.host", "in-mum-mail3.corp.capgemini.com");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "2525");

		// Recipient's email ID needs to be mentioned.
		String to = "prashant.shandilya@capgemini.com";

		// Sender's email ID needs to be mentioned
		String from = "prashant.shandilya@capgemini.com";

		Authenticator authenticator = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		};

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties, authenticator);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("This is the Subject Line!");

			// Send the actual HTML message, as big as you like
			message.setContent("<h1>This is actual message</h1>", "text/html");

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

}