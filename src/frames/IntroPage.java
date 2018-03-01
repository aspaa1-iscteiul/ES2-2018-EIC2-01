package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class IntroPage extends SuperPage {

    public IntroPage(JFrame backPage) {
	super(backPage, "Problem Solving App");
	JPanel centerPanel = new JPanel();
	centerPanel.setBackground(Color.WHITE);
	centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
	// XXX change when frame size is set
	centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

	JLabel messageLabel = new JLabel("<html>With the <font color=red><b>Pobrlem Solving App</b></font> "
		+ "you can submit your optimization problems for<br><font color=green><u>AUTOMATIC</u></font> "
		+ "evalution! According to the characteristics of your problem you will<br>be returned the "
		+ "optimal solution found by our <font color=orange><b>Metaheuristics Algorithm</b></font>."
		+ " Find out<br>more about <a href=\"www.google.com\">jMetal framework</a></html>");
	messageLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
	centerPanel.add(messageLabel);

	FrameUtils.addEmptyLabels(centerPanel, 5);

	JButton submitButton = FrameUtils.cuteButton("Submit a new problem for evalution");
	submitButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		// TODO go to RegisterUserPage
	    }
	});
	centerPanel.add(submitButton);

	FrameUtils.addEmptyLabels(centerPanel, 1);

	JButton importButton = FrameUtils.cuteButton("Import a previously stored problem configuration");
	importButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		// TODO open JFileChooser and then go to ???
	    }
	});
	centerPanel.add(importButton);

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

	frame.add(buttonsPanel, BorderLayout.SOUTH);
    }

}
