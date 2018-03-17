package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class RegisterUserPage extends SuperPage {

    /**
     * Default
     */
    private static final long serialVersionUID = 1L;

    private JTextField email;
    private JCheckBox checkBox;

    public RegisterUserPage(UserInterface userInterface) {
	super(userInterface);
    }

    @Override
    protected void initialize() {
	email = new JTextField(30); // XXX see if 30 is a good width
	checkBox = new JCheckBox("I understand and accept the terms and conditions");
    }

    @Override
    protected void createMainPanel() {
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	mainPanel.setBorder(new EmptyBorder(50, 55, 0, 0));

	JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	emailPanel.setBackground(Color.WHITE);
	JLabel emailLabel = new JLabel("Email address:");
	emailLabel.setFont(FrameUtils.cuteFont(14));
	emailPanel.add(emailLabel);
	email.setBorder(FrameUtils.cuteBorder());
	emailPanel.add(email);
	mainPanel.add(emailPanel);

	// to add space between the email text and info
	FrameUtils.addEmptyLabels(mainPanel, 2);

	JPanel infoPanel = new JPanel(new BorderLayout());
	infoPanel.setBackground(Color.WHITE);
	infoPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
	JPanel iconPanel = new JPanel(new BorderLayout());
	iconPanel.setBackground(Color.WHITE);
	JLabel infoIcon = new JLabel();
	infoIcon.setIcon(new ImageIcon("./src/frames/images/info_icon.png"));
	iconPanel.add(infoIcon, BorderLayout.NORTH);
	infoPanel.add(iconPanel, BorderLayout.WEST);
	JLabel infoLabel = new JLabel("<html>The e-mail provided will only be used to send help information "
		+ "and/or<br>indications about the progress of the optimization process requested<br>"
		+ "through the platform (e.g., occurrence of errors). This data will not be<br>disclosed"
		+ "to third parties nor will it have any use other than as set forth<br>herein.</html>");
	infoLabel.setFont(FrameUtils.cuteFont(13));
	infoLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
	infoPanel.add(infoLabel, BorderLayout.CENTER);
	mainPanel.add(infoPanel);

	// to add space between the info and check box
	FrameUtils.addEmptyLabels(mainPanel, 4);

	checkBox.setFont(FrameUtils.cuteFont(12));
	checkBox.setBackground(Color.WHITE);
	mainPanel.add(checkBox);
    }

    @Override
    protected boolean areAllDataWellFilled() {
	boolean isEmailWellFilled = isEmailWellFilled(), isCheckBoxSeleceted = isCheckBoxSeleceted();
	return isEmailWellFilled && isCheckBoxSeleceted;
    }

    /**
     * Verifys if the input is a valid email
     * 
     * @return
     */
    private boolean isEmailWellFilled() {
	try {
	    // validates the email
	    new InternetAddress(email.getText()).validate();
	    return FrameUtils.normalFormat(email);
	} catch (AddressException ee) {
	    return FrameUtils.errorFormat(email,
		    email.getText().equals("") ? "The e-mail address field is a mandatory entry field and therefore must be filled in."
			    : "The e-mail provided does not have a valid format.");
	}
    }

    /**
     * Verify if the terms and conditions checkbox is filled
     * 
     * @return
     */
    private boolean isCheckBoxSeleceted() {
	return checkBox.isSelected() ? FrameUtils.normalFormat(checkBox)
		: FrameUtils.errorFormat(checkBox,
			"In order to proceed, you have to agree on the terms and conditions.");
    }

    @Override
    protected void onTop() {
	userInterface.getFrame().setTitle("Problem Solving App");
    }

    @Override
    protected void saveToProblem() {
	userInterface.setUserEmail(email.getText());
    }

}
