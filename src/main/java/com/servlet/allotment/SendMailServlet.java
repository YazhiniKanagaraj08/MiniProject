package com.servlet.allotment;

import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.annotation.WebServlet;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SendMailServlet")
public class SendMailServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Sending mail..");
        String message = "Respected Sir/Madam, This is message for allotment.";
        String subject = "Regarding Question paper Preparation";
        String to = "yazhinikanagaraj2002@gmail.com";
        String from = "yazhinikanagaraj2002@gmail.com";
        
        sendEmail(message, subject, to, from);
        
        response.setContentType("text/html");
        response.getWriter().println("<html><body><h2>Email Sent Successfully!</h2></body></html>");
    }
    
    private static void sendEmail(String message, String subject, String to, String from) {
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				
				return new PasswordAuthentication("yazhinikanagaraj2002@gmail.com","lamgfmmvkreljynl");
			}			
		});
        session.setDebug(true);
		//Step 2: Compose the message
		MimeMessage m = new MimeMessage(session);
		try {
			m.setFrom(from);
			
			//Adding Recipient
			m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			
			//Adding Subject
			m.setSubject(subject);
			
			//Adding text 
			m.setText(message);
			
			//Sending the message using Transport class
			Transport.send(m);
			System.out.println("Sent Successfully..");
			
		}  catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}