package action;

import java.util.Properties;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import by.bsu.mail.creator.SessionCreator;

public class MailThead extends Thread {
	private MimeMessage message;
	private String sendToEmail;
	private String mailSubject;
	private String mailText;
	private Properties properties;

	public MailThead(String sendToEmail, String mailSubject, String mailText, Properties properties) {
		this.sendToEmail = sendToEmail;
		this.mailSubject = mailSubject;
		this.mailText = mailText;
		this.properties = properties;
	}

	private void init() {
		Session mailSession = (new SessionCreator(properties)).createSession();
		mailSession.setDebug(true);
		message = new MimeMessage(mailSession);
		try {
			message.setSubject(mailSubject);
			message.setContent(mailText, "text/html");
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendToEmail));
		} catch (AddressException e) {
			System.err.print("Некоректный адрес: " + sendToEmail + " " + e);
		} catch (MessagingException e) {
			System.err.print("Ошибка формирования сообщения" + e);
		}
	}

	public void run() {
		init();
		try {
			Transport.send(message);
		} catch (Exception e) {
			System.err.print("Ошибка при отправлении сообщения" + e);
		}
	}
}
