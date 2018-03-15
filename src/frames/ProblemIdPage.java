package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

public class ProblemIdPage extends SuperPage {

    private static final long serialVersionUID = 1L;

    private JTextField problemName;
    private JTextArea problemDescription;

    public ProblemIdPage(UserInterface userInterface) {
	super(userInterface);
    }

    @Override
    protected void initialize() {
	problemName = new JTextField(30); // XXX see if 30 is a good width
	problemDescription = new JTextArea();
    }

    @Override
    protected void createMainPanel() {
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	// XXX change when frame size is set
	mainPanel.setBorder(new EmptyBorder(50, 0, 0, 0));

	JPanel problemPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	problemPanel.setBackground(Color.WHITE);
	JLabel problemLabel = new JLabel("Problem name:");
	problemLabel.setFont(FrameUtils.cuteFont(14));
	problemPanel.add(problemLabel);
	problemName.setBorder(FrameUtils.cuteBorder());
	problemPanel.add(problemName);
	mainPanel.add(problemPanel);

	FrameUtils.addEmptyLabels(mainPanel, 2);

	JPanel infoPanel = new JPanel(new BorderLayout());
	infoPanel.setBackground(Color.WHITE);
	infoPanel.setBorder(new EmptyBorder(0, 30, 0, 0));
	JLabel infoIcon = new JLabel();
	infoIcon.setIcon(new ImageIcon("./src/frames/images/info_icon.png"));
	infoPanel.add(infoIcon, BorderLayout.WEST);
	JLabel infoLabel = new JLabel("<html>The name you choose must start with a capital letter and can "
		+ "only <br> contain numbers and letters (from A to Z).<br></html>");
	infoLabel.setFont(FrameUtils.cuteFont(12));
	infoLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
	infoPanel.add(infoLabel, BorderLayout.CENTER);
	mainPanel.add(infoPanel);

	FrameUtils.addEmptyLabels(mainPanel, 3);

	JPanel descriptionPanel = new JPanel(new BorderLayout());
	descriptionPanel.setBackground(Color.WHITE);
	JPanel auxPanel = new JPanel(new BorderLayout());
	auxPanel.setBackground(Color.WHITE);
	JLabel descriptionLabel = new JLabel("Problem's description:  ");
	descriptionLabel.setFont(FrameUtils.cuteFont(14));
	auxPanel.add(descriptionLabel, BorderLayout.NORTH);
	descriptionPanel.add(auxPanel, BorderLayout.WEST);

	problemDescription.setBackground(Color.WHITE);
	problemDescription.setLineWrap(true);
	problemDescription.setWrapStyleWord(true);
	problemDescription.setBorder(FrameUtils.cuteBorder());

	JScrollPane scrollPane = new JScrollPane(problemDescription);
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	scrollPane.setPreferredSize(new Dimension(0, 100));

	descriptionPanel.add(scrollPane, BorderLayout.CENTER);

	mainPanel.add(descriptionPanel);
    }

    @Override
    protected void onTop() {
	userInterface.getFrame().setTitle("Problem Solving App");
    }

    @Override
    protected boolean areAllDataWellFilled() {
	return Pattern.matches("[a-zA-Z0-9]+", problemName.getText())
		&& Character.isUpperCase(problemName.getText().charAt(0)) ? FrameUtils.normalFormat(problemName)
			: FrameUtils.errorFormat(problemName,
				"The problem's name field is mandatory and must respect the conditions specified in the info section below.");
    }

    @Override
    protected void saveToProblem() {
	userInterface.getProblem().setProblemName(problemName.getText());
	userInterface.getProblem().setProblemName(problemDescription.getText());
    }

}
