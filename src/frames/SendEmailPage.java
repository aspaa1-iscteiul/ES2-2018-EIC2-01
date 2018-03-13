package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

/**
 * This class represents the Send Email Page
 */

public class SendEmailPage extends SuperPage {

    private static final long serialVersionUID = 1L;
    private JTextField name;
    private JTextField subject;
    private JTextArea message;
    private JButton submitButton;

    /**
     * 
     * @param userInterface
     */
    public SendEmailPage(UserInterface userInterface) {
	super(userInterface);
	// TODO Auto-generated constructor stub
    }

    @Override
    protected void initialize() {
	this.name = new JTextField(30);
	this.subject = new JTextField(30);
	this.message = new JTextArea();
	this.submitButton = FrameUtils.cuteButton("Submit");
    }

    @Override
    protected void createMainPanel() {
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	// XXX change when frame size is set
	mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
	mainPanel.setBackground(Color.white);

	JPanel infoPanel = new JPanel();
	infoPanel.setLayout(new BorderLayout());
	infoPanel.setBackground(Color.WHITE);
	JLabel infoIcon = new JLabel();
	infoIcon.setIcon(new ImageIcon("./src/frames/images/email_icon.png"));
	infoPanel.add(infoIcon, BorderLayout.WEST);
	infoPanel.add(new JLabel("   We will answer your email within 2 working days."), BorderLayout.CENTER);
	mainPanel.add(infoPanel);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	JPanel namePanel = new JPanel();
	namePanel.setLayout(new BorderLayout());
	namePanel.setBackground(Color.WHITE);
	namePanel.add(new JLabel("Name:         "), BorderLayout.WEST);
	name.setBorder(FrameUtils.cuteBorder());
	namePanel.add(name, BorderLayout.CENTER);
	mainPanel.add(namePanel);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	JPanel subjectPanel = new JPanel();
	subjectPanel.setLayout(new BorderLayout());
	subjectPanel.setBackground(Color.WHITE);
	subjectPanel.add(new JLabel("Subject:      "), BorderLayout.WEST);
	subject.setBorder(FrameUtils.cuteBorder());
	subjectPanel.add(subject, BorderLayout.CENTER);
	mainPanel.add(subjectPanel);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	JPanel messagePanel = new JPanel();
	messagePanel.setLayout(new BorderLayout());
	messagePanel.setBackground(Color.WHITE);
	messagePanel.add(new JLabel("Message:   "), BorderLayout.WEST);
	message.setBackground(Color.WHITE);
	message.setLineWrap(true);
	message.setWrapStyleWord(true);
	message.setBorder(FrameUtils.cuteBorder());

	JScrollPane scrollPane = new JScrollPane(message);
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	// A dimensão tem que ser posta no scrollPane e não no results
	scrollPane.setPreferredSize(new Dimension(60, 100));
	messagePanel.add(scrollPane, BorderLayout.CENTER);

	mainPanel.add(messagePanel);
    }

    @Override
    protected void createButtonsPanel() {
	JButton cancelButton = FrameUtils.cuteButton("Cancel");
	cancelButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		userInterface.returnFromEmailPage();
	    }
	});
	buttonsPanel.add(cancelButton);

	buttonsPanel.add(new JLabel()); // to add space between the two buttons

	submitButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		sendEmail();
		userInterface.returnFromEmailPage();
	    }
	});
	buttonsPanel.add(submitButton);
    }

    @Override
    protected void onTop() {
	userInterface.getFrame().setTitle("Problem Solving App");
    }

    /**
     * Conta de email do grupo: Email: ES22018EIC201@gmail.com
     * Password: ES2-2018-EIC2-01AGRS
     * Sends an email to the admin email with the information filled by the user
     */
    private void sendEmail() {
	String to = "ES22018EIC201@gmail.com";

	// Sender's email ID needs to be mentioned
	String from = "ES22018EIC201@gmail.com";
	String password = "ES2-2018-EIC2-01AGRS";

	// Assuming you are sending email from localhost
	String host = "localhost";

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
	    message.setSubject("Email to answer:" + userInterface.getUserEmail() + " Name:" + name.getText() + " Subject:" + subject.getText());
	    message.setText("Email to answer:" + userInterface.getUserEmail() + " " + this.message.getText());

	    Transport.send(message);

	} catch (MessagingException e) {
	    throw new RuntimeException(e);
	}

    }

    @Override
    protected boolean areAllDataWellFilled() {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    protected void saveToProblem() {
	// TODO Auto-generated method stub
    }

}
