package com.client;
// File Name SendHTMLEmail.java

import java.net.URI;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.MessageBody;

public class SendEmailClient2 {

	public static void main(String[] args) {
		ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2007_SP1);
		ExchangeCredentials credentials = new WebCredentials("prashant.shandilya@capgemini.com", "Posi@678");
		service.setCredentials(credentials);
		try {
			service.setUrl(new URI("https://webmail.in.capgemini.com/ews/exchange.asmx"));

			service.setTraceEnabled(true);

			Folder inbox = Folder.bind(service, WellKnownFolderName.Inbox);

			EmailMessage msg = new EmailMessage(service);
			msg.setSubject("Hello world!");
			msg.setBody(MessageBody.getMessageBodyFromText("Sent using the EWS Java API."));
			msg.getToRecipients().add("mrigank.varma@capgemini.com");
			msg.getToRecipients().add("prashant.shandilya@capgemini.com");
			msg.send();

			System.out.println("messages: " + inbox.getTotalCount());

		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
	}

}