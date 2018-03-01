package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class RegisterUserPage extends SuperPage {

    public RegisterUserPage(JFrame backPage) {
	super(backPage, "Problem Solving App");

	JPanel centerPanel = new JPanel();
	centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
	centerPanel.setBackground(Color.WHITE);
	// XXX change when frame size is set
	centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

	JPanel emailPanel = new JPanel();
	emailPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	emailPanel.setBackground(Color.WHITE);
	emailPanel.add(new JLabel("E-mail address:"));
	JTextField email = new JTextField(30);
	emailPanel.add(email);
	centerPanel.add(emailPanel);

	FrameUtils.addEmptyLabels(centerPanel, 2);

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
	centerPanel.add(infoPanel);

	FrameUtils.addEmptyLabels(centerPanel, 4);

	JButton nextButton = FrameUtils.cuteButton("Next");

	JCheckBox checkBox = new JCheckBox("I understand and accept the terms and conditions");
	checkBox.setBackground(Color.WHITE);
	checkBox.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		nextButton.setEnabled(checkBox.isSelected());
	    }
	});
	centerPanel.add(checkBox);

	frame.add(centerPanel, BorderLayout.CENTER);

	JPanel buttonsPanel = new JPanel();
	buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
	buttonsPanel.setBackground(Color.WHITE);
	buttonsPanel.setBorder(new EmptyBorder(0, 0, 5, 5));

	JButton backButton = FrameUtils.cuteButton("Back");
	backButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		frame.setVisible(false);
		backPage.setVisible(true);
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

	nextButton.setEnabled(checkBox.isSelected());
	nextButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		// TODO go to ProblemIDPage
	    }
	});
	buttonsPanel.add(nextButton);

	frame.add(buttonsPanel, BorderLayout.SOUTH);
    }

}
