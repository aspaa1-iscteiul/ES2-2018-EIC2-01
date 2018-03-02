package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
	private JButton nextButton;
	private JCheckBox checkBox;
	private boolean validEmail;

	public RegisterUserPage(UserInterface userInterface) {
		super(userInterface);
	}

	@Override
	public void initialize() {
		email = new JTextField(30); // XXX see if 30 is a good width
		nextButton = FrameUtils.cuteButton("Next");
		checkBox = new JCheckBox("I understand and accept the terms and conditions");
		validEmail = false;
	}

	@Override
	public void createMainPanel() {
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		// XXX change when frame size is set
		mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

		JPanel emailPanel = new JPanel();
		emailPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		emailPanel.setBackground(Color.WHITE);
		emailPanel.add(new JLabel("E-mail address:"));
		email.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				try {
					// validates the email
					new InternetAddress(email.getText()).validate();
					validEmail = true;
				} catch (AddressException ee) {
					validEmail = false;
				}
				nextButton.setEnabled(checkBox.isSelected() && validEmail);
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		emailPanel.add(email);
		mainPanel.add(emailPanel);

		FrameUtils.addEmptyLabels(mainPanel, 2);

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BorderLayout());
		infoPanel.setBackground(Color.WHITE);
		JLabel infoIcon = new JLabel();
		infoIcon.setIcon(new ImageIcon("./src/frames/images/info_icon.png"));
		infoPanel.add(infoIcon, BorderLayout.WEST);
		JLabel infoLabel = new JLabel("<html>The e-mail provided will only be used to send help information "
				+ "and/or<br>indications about the progress of the optimization process requested<br>"
				+ "through the plantform (e.g., occurrence of errors). This data will not be<br>disclosed"
				+ "to third parties nor will it have any use other than as set forth<br>herein.</html>");
		infoLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		infoLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
		infoPanel.add(infoLabel, BorderLayout.CENTER);
		mainPanel.add(infoPanel);

		FrameUtils.addEmptyLabels(mainPanel, 4);

		checkBox.setBackground(Color.WHITE);
		checkBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nextButton.setEnabled(checkBox.isSelected() && validEmail);
			}
		});
		mainPanel.add(checkBox);
	}

	@Override
	public void createButtonsPanel() {
		JButton backButton = FrameUtils.cuteButton("Back");
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				userInterface.goToPreviousPage();
			}
		});
		buttonsPanel.add(backButton);

		buttonsPanel.add(new JLabel()); // to add space between the two buttons

		JButton cancelButton = FrameUtils.cuteButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		buttonsPanel.add(cancelButton);

		buttonsPanel.add(new JLabel()); // to add space between the two buttons

		nextButton.setEnabled(false);
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO go to ProblemIDPage
			}
		});
		buttonsPanel.add(nextButton);
	}

	@Override
	public void onTop() {
		userInterface.getFrame().setTitle("Problem Solving App");
	}

}
