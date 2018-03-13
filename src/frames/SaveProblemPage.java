package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * This class represents the Save Problem Page
 */

public class SaveProblemPage extends SuperPage {

    private static final long serialVersionUID = 1L;
    private JPanel savePanel;
    private JTextField fileName;
    private JTextField filePath;
    private JButton saveButton;
    private JButton finishButton;

    /**
     * 
     * @param userInterface
     */
    public SaveProblemPage(UserInterface userInterface) {
	super(userInterface);
    }

    @Override
    protected void initialize() {
	this.fileName = new JTextField(30);
	this.filePath = new JTextField(30);
	this.saveButton = FrameUtils.cuteButton("Save");
	this.saveButton.setEnabled(false);
	this.finishButton = FrameUtils.cuteButton("Finish");
    }

    @Override
    protected void createMainPanel() {
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	// XXX change when frame size is set
	mainPanel.setBorder(new EmptyBorder(50, 0, 0, 0));
	mainPanel.setBackground(Color.white);

	JPanel infoPanel = new JPanel(new BorderLayout());
	infoPanel.setBackground(Color.WHITE);
	JLabel infoLabel = new JLabel("Save this problem's configuration before submitting it to evaluation");
	infoLabel.setFont(FrameUtils.cuteFont(14));
	infoPanel.add(infoLabel, BorderLayout.WEST);
	mainPanel.add(infoPanel);

	FrameUtils.addEmptyLabels(mainPanel, 2);

	JPanel namePanel = new JPanel(new BorderLayout());
	namePanel.setBackground(Color.WHITE);
	JLabel nameLabel = new JLabel("File name:   ");
	nameLabel.setFont(FrameUtils.cuteFont(12));
	namePanel.add(nameLabel, BorderLayout.WEST);
	fileName.setBorder(FrameUtils.cuteBorder());

	namePanel.add(fileName, BorderLayout.CENTER);
	mainPanel.add(namePanel);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	continueCreateMainPanel();
    }

    /**
     * Method to create the main part of the page to be viewed
     */
    private void continueCreateMainPanel() {
	JPanel pathPanel = new JPanel(new BorderLayout());
	pathPanel.setBackground(Color.WHITE);
	JLabel pathLabel = new JLabel("Path:            ");
	pathLabel.setFont(FrameUtils.cuteFont(12));
	pathPanel.add(pathLabel, BorderLayout.WEST);
	filePath.setBorder(FrameUtils.cuteBorder());

	filePath.addKeyListener(new KeyListener() {
	    @Override
	    public void keyPressed(KeyEvent arg0) {
	    }

	    @Override
	    public void keyReleased(KeyEvent arg0) {
		if (!filePath.getText().trim().isEmpty()) {
		    // TODO: Rever se isto funciona, acho que não
		    try {
			Paths.get(filePath.getText());
			saveButton.setEnabled(true);
		    } catch (InvalidPathException | NullPointerException ex1) {
			saveButton.setEnabled(false);
		    }
		}

	    }

	    @Override
	    public void keyTyped(KeyEvent arg0) {
	    }
	});

	pathPanel.add(filePath, BorderLayout.CENTER);
	mainPanel.add(pathPanel);

	FrameUtils.addEmptyLabels(mainPanel, 2);

	savePanel = new JPanel(new BorderLayout());
	savePanel.setBackground(Color.WHITE);
	savePanel.add(saveButton, BorderLayout.WEST);
	mainPanel.add(savePanel);

    }

    @Override
    protected void createButtonsPanel() {

	saveButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		userInterface.setFinalProblem();
		JLabel infoIcon = new JLabel();
		infoIcon.setIcon(new ImageIcon("./src/frames/images/saved_icon.png"));
		savePanel.add(infoIcon, BorderLayout.CENTER);
		savePanel.validate();
		savePanel.repaint();
	    }
	});

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

	finishButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		System.out.println("Everything concluded");
	    }
	});
	buttonsPanel.add(finishButton);
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
