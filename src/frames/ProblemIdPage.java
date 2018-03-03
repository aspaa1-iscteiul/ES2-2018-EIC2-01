package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class ProblemIdPage extends SuperPage {

	private static final long serialVersionUID = 1L;

	private JTextField problemName;
	private JTextArea problemDescription;
	private JButton nextButton;

	// Validar se começa com uma letra grande e se só tem letras e números

	public ProblemIdPage(UserInterface userInterface) {
		super(userInterface);
	}

	@Override
	public void initialize() {
		problemName = new JTextField(30); // XXX see if 30 is a good width
		problemDescription = new JTextArea();
		nextButton = FrameUtils.cuteButton("Next");
	}

	@Override
	public void createMainPanel() {
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		// XXX change when frame size is set
		mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);

		JPanel problemPanel = new JPanel();
		problemPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		problemPanel.setBackground(Color.WHITE);
		problemPanel.add(new JLabel("Problem name:"));
		problemName
				.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(0, 10, 0, 10)));
		problemName.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				nextButton.setEnabled(Pattern.matches("[a-zA-Z0-9]+", problemName.getText())
						&& Character.isUpperCase(problemName.getText().charAt(0)));
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		problemPanel.add(problemName);
		mainPanel.add(problemPanel);

		FrameUtils.addEmptyLabels(mainPanel, 2);

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BorderLayout());
		infoPanel.setBackground(Color.WHITE);
		JLabel infoIcon = new JLabel();
		infoIcon.setIcon(new ImageIcon("./src/frames/images/info_icon.png"));
		infoPanel.add(infoIcon, BorderLayout.WEST);
		JLabel infoLabel = new JLabel("<html>The name you choose must start with a capital letter and"
				+ " can only <br> contain numbers and letters (from A to Z).<br></html>");
		infoLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		infoLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
		infoPanel.add(infoLabel, BorderLayout.CENTER);
		mainPanel.add(infoPanel);

		FrameUtils.addEmptyLabels(mainPanel, 2);

		JPanel descriptionPanel = new JPanel();
		descriptionPanel.setLayout(new BorderLayout());
		descriptionPanel.setBackground(Color.WHITE);
		descriptionPanel.add(new JLabel("Problem description:  "), BorderLayout.WEST);

		problemDescription.setBackground(Color.WHITE);
		problemDescription.setLineWrap(true);
		problemDescription.setWrapStyleWord(true);
		problemDescription.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(0, 10, 0, 10)));

		JScrollPane scrollPane = new JScrollPane(problemDescription);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		// A dimensão tem que ser posta no scrollPane e não no results
		scrollPane.setPreferredSize(new Dimension(50, 70));

		descriptionPanel.add(scrollPane, BorderLayout.CENTER);

		mainPanel.add(descriptionPanel);

		FrameUtils.addEmptyLabels(mainPanel, 1);
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
