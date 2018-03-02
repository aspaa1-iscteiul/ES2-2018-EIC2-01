package frames;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class IntroPage extends SuperPage {

	/**
	 * Default
	 */
	private static final long serialVersionUID = 1L;

	public IntroPage(UserInterface userInterface) {
		super(userInterface);
	}

	@Override
	public void initialize() {
	}

	@Override
	public void createMainPanel() {
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		// XXX change when frame size is set
		mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

		JLabel messageLabel = new JLabel("<html>With the <font color=red><b>Problem Solving App</b></font> "
				+ "you can submit your optimization problems for<br><font color=green><u>AUTOMATIC</u></font> "
				+ "evalution! According to the characteristics of your problem you will<br>be returned the "
				+ "optimal solution found by our <font color=orange><b>Metaheuristics Algorithm</b></font>."
				+ " Find out<br>more about <a href=\"www.google.com\">jMetal framework</a></html>");
		messageLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		mainPanel.add(messageLabel);

		FrameUtils.addEmptyLabels(mainPanel, 5);

		JButton submitButton = FrameUtils.cuteButton("Submit a new problem for evalution");
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				userInterface.goToNextPage();
			}
		});
		mainPanel.add(submitButton);

		FrameUtils.addEmptyLabels(mainPanel, 1);

		JButton importButton = FrameUtils.cuteButton("Import a previously stored problem configuration");
		importButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO open JFileChooser and then go to RegisterUserPage
			}
		});
		mainPanel.add(importButton);
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

		// to add space between the two buttons
		buttonsPanel.add(new JLabel());

		JButton cancelButton = FrameUtils.cuteButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		buttonsPanel.add(cancelButton);
	}

	@Override
	public void onTop() {
		userInterface.getFrame().setTitle("Problem Solving App");
	}

}
