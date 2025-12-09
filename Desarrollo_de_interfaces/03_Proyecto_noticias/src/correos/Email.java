package correos;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/*
 * Trabajo realizado por Arnau Blanch Manero
 */

public class Email {
	// Enviar email
	private String correoDestino;
	private String mensaje;
	
	public Email(String correoDestino, String mensaje) {
		super();
		this.correoDestino = correoDestino;
		this.mensaje = mensaje;
	}

	public boolean enviar() {
		boolean enviadoCorrectamente = true;
		final String fromEmail = "arnau.blanch.dosa@gmail.com"; //EMAIL DE SALIDA
		final String password = "**** "; //TODO ELIMINAR CONTRASEÑA DEL EMAIL DE SALIDA (aplicaciones de 3ros) Contraseñas de aplicación -- Verificación en 2 pasos 
		// https://myaccount.google.com/apppasswords
		final String toEmail = correoDestino; // EMAIL DESTINATARIO
				
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP de GMAIL en este caso
		props.put("mail.smtp.socketFactory.port", "465"); //PUERTO SSL 
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
		props.put("mail.smtp.auth", "true"); //ACTIVAR SMTP AUTENTIFICACIÓN
		props.put("mail.smtp.port", "465"); //SMTP Port		
		Authenticator auth = new Authenticator() {		
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};		
		Session session = Session.getDefaultInstance(props, auth);//CREA UNA SESIÓN CON TODAS LAS PROPIEDADES Y EL "LOGIN"
		try{
		      MimeMessage msg = new MimeMessage(session);
		      //Configurar Cabeceras
		      msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
		      msg.addHeader("format", "flowed");
		      msg.addHeader("Content-Transfer-Encoding", "8bit");
		      msg.setFrom(new InternetAddress("arnau.blanch.dosa@gmail.com", "Arnau Blanch Manero"));    	      
		      msg.setReplyTo(InternetAddress.parse("arnau.blanch.dosa@gmail.com", false));	      
		      msg.setSubject("Noticias DAM", "UTF-8");
		      msg.setText(mensaje, "UTF-8");
		      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));	   
		      Transport.send(msg);
		    }
		    catch (Exception e) {
		    	enviadoCorrectamente = false;
		      e.printStackTrace();
		    }
		
		return enviadoCorrectamente;
	}
	
	
}
