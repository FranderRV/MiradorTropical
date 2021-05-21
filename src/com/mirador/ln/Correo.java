package com.mirador.ln;


import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.MessagingException;
import javax.mail.internet.*;
import javax.swing.JOptionPane;


public class Correo {

    private Properties propiedades;
    private Session sesion;
    private MimeMessage mensaje;
    private Transport transporte;
    private String cuenta;
    private String contrasena;

    public Correo(String cuenta, String contrasena) { 
        this.cuenta = cuenta;
        this.contrasena = contrasena;
        propiedades = new Properties();
        propiedades.setProperty("mail.smtp.host", "smtp.gmail.com");// Nombre del host de correo, es smtp.gmail.com
        propiedades.setProperty("mail.smtp.starttls.enable", "true");// TLS si está disponible
        propiedades.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");//Evita caida de Servidor TLS
        propiedades.setProperty("mail.smtp.port", "587");// Puerto de gmail para envio de correos
        propiedades.setProperty("mail.smtp.user", cuenta);// Nombre del usuario
        propiedades.setProperty("mail.smtp.auth", "true");// Si requiere o no usuario y password para conectarse.
        sesion = Session.getDefaultInstance(propiedades);//Genera la sesion con las propiedades determinadas
        mensaje = new MimeMessage(sesion);//El mensaje recibe la sesion
    }

    public void agregarDestinatario(String destinatario) {
        try {
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));// A quien va dirigido
        } catch (MessagingException ex) {
            System.err.println("Error agregando destinatarios: " + ex.getMessage());
        }
    }

    public void adjunto(String ruta, String nombreArchivo) {
        try {
            BodyPart adjunto = new MimeBodyPart();
            MimeMultipart multiParte = new MimeMultipart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource(ruta)));
            adjunto.setFileName(nombreArchivo);
            multiParte.addBodyPart(adjunto);
            mensaje.setContent(multiParte);
        } catch (MessagingException ex) {
            System.err.println("Error adjuntando archivo: " + ex.getMessage());
        }
    }

    public void crearMensaje(String asunto, String texto) {
        try {
            mensaje.setFrom(new InternetAddress(cuenta));// Quien envia el correo
            mensaje.setSubject(asunto);
            mensaje.setText(texto, "ISO-8859-1", "html");
        } catch (MessagingException ex) {
        	   JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void enviarCorreo() {
        try {
            transporte = sesion.getTransport("smtp");
            transporte.connect(cuenta, contrasena);
            transporte.sendMessage(mensaje, mensaje.getAllRecipients());
            transporte.close(); 
        } catch (MessagingException ex) {
         JOptionPane.showMessageDialog(null,ex.getMessage());
        }
    }

}