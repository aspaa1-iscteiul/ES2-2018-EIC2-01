package frames.frameUtils;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Group's email account:<br>
 * Email: ES22018EIC201@gmail.com<br>
 * Password: ES2-2018-EIC2-01AGRS<br>
 * Default Admin's email: problemsolving.group01@gmail.com<br>
 * Password: ES2-2018-EIC2-01AGRS
 * 
 * 
 * @author Rodrigo
 *
 */
public class Email {

    private String to;
    private String toCC;
    private String from;
    private String password;
    private String host;

    /**
     * Creates an email given the recipient mail adress
     * 
     * @param to
     */
    public Email(String to) {
	this.to = to;
	this.toCC = "ProblemSolving.Group01@gmail.com";
	this.from = "ES22018EIC201@gmail.com";
	this.password = "ES2-2018-EIC2-01AGRS";
	this.host = "localhost";
    }

    /**
     * Sends an email with the subject and message filled
     * 
     * @param subject
     * @param messageText
     */
    public void sendEmail(boolean wait, String subject, String messageText) {
	if (wait) {
	    sendEmail(subject, messageText);
	} else
	    new Thread() {
		@Override
		public void run() {
		    sendEmail(subject, messageText);
		}
	    }.start();
    }

    private void sendEmail(String subject, String messageText) {
	// Get system properties
	Properties properties = System.getProperties();

	// Setup mail server
	properties.setProperty("mail.smtp.host", host);
	properties.put("mail.smtp.starttls.enable", "true");
	properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	properties.put("mail.smtp.auth", "true");
	properties.put("mail.smtp.host", "smtp.gmail.com");
	properties.put("mail.smtp.port", "587");

	// Get the default Session object.
	Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
	    protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(from, password);
	    }
	});

	try {
	    Message message = new MimeMessage(session);
	    message.setFrom(new InternetAddress(from));
	    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
	    message.addRecipient(RecipientType.CC, new InternetAddress(toCC));
	    message.setSubject(subject);
	    message.setText(messageText);

	    Transport.send(message);

	} catch (MessagingException e) {
	    throw new RuntimeException(e);
	}
    }

    /**
     * Sends an email with the subject, message and attachment
     * 
     * @param subject
     * @param messageText
     */
    public void sendEmailWithAttachment(String subject, String messageText, String attachmentPath,
	    String attachmentName) {
	new Thread() {
	    @Override
	    public void run() {
		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		// Get the default Session object.
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
		    protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(from, password);
		    }
		});

		try {
		    Message message = new MimeMessage(session);
		    message.setFrom(new InternetAddress(from));
		    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		    message.addRecipient(RecipientType.CC, new InternetAddress(toCC));
		    message.setSubject(subject);
		    message.setText(messageText);

		    MimeBodyPart messageBodyPart = new MimeBodyPart();
		    Multipart multipart = new MimeMultipart();

		    messageBodyPart = new MimeBodyPart();
		    String file = attachmentPath + "\\" + attachmentName;
		    System.out.println(file);

		    BodyPart textPart = new MimeBodyPart();
		    textPart.setText(messageText);

		    String fileName = attachmentName;
		    DataSource source = new FileDataSource(file);
		    messageBodyPart.setDataHandler(new DataHandler(source));
		    messageBodyPart.setFileName(fileName);
		    multipart.addBodyPart(textPart);
		    multipart.addBodyPart(messageBodyPart);

		    message.setContent(multipart);

		    Transport.send(message);

		} catch (MessagingException e) {
		    throw new RuntimeException(e);
		}
	    }
	}.start();
    }

    /**
     * 
     * @return the receiver
     */
    public String getTo() {
	return to;
    }

    /**
     * 
     * @param to
     *            the receiver to set
     */
    public void setTo(String to) {
	this.to = to;
    }

    /**
     * 
     * @return the receiver of a copy of the email
     */
    public String getToCC() {
	return toCC;
    }

    /**
     * 
     * @param toCC
     *            the receiver of a copy to set
     */
    public void setToCC(String toCC) {
	this.toCC = toCC;
    }

    /**
     * 
     * @return the sender
     */
    public String getFrom() {
	return from;
    }

    /**
     * 
     * @param from
     *            the sender to set
     */
    public void setFrom(String from) {
	this.from = from;
    }

    /**
     * 
     * @return the password
     */
    public String getPassword() {
	return password;
    }

    /**
     * 
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
	this.password = password;
    }

}
