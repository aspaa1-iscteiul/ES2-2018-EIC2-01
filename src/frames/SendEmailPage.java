package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class SendEmailPage extends SuperPage {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField name;
    private JTextField subject;
    private JTextArea message;
    private JButton submitButton;

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
		// TODO: fazer funcao que envia email
	    }
	});
	buttonsPanel.add(submitButton);
    }

    @Override
    protected void onTop() {
	userInterface.getFrame().setTitle("Problem Solving App");
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
