package com.mirador.ln;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Email {
	private String receptor, mensaje, asunto;
	public Email(String receptor, String asunto, String mensaje) {

		
		try {

			String remitente = "miradorPHR@gmail.com";
			String contrasena = "miraDOOR2019";
			this.receptor = receptor;
			this.asunto = asunto;
			this.mensaje= mensaje;
			System.out.println(remitente);
			
			Properties props = System.getProperties();
		    props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
		    props.put("mail.smtp.user", remitente);
		    props.put("mail.smtp.clave", "miraDOOR2019");    //La clave de la cuenta
		    props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
		    props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
		    props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

		    Session session = Session.getDefaultInstance(props,null); 
		    
		    MimeMessage mail = new MimeMessage(session);

			mail.setFrom(new InternetAddress (remitente));
			mail.addRecipient(Message.RecipientType.TO, new InternetAddress (receptor));
			mail.setSubject(asunto);
			mail.setText(mensaje);

			Transport transportar = session.getTransport("smtp");
			transportar.connect(remitente,contrasena);
			transportar.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));          
			transportar.close();


		} catch (AddressException ex) {
			System.out.println("Erroer 1 "+ex.getMessage());
		} catch (MessagingException ex) {
			System.out.println("Erroer 1 "+ex.getMessage());
		}
	}
}
