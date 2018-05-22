package frames;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import frames.frameUtils.FrameUtils;
import utils.UserFileUtils;

public class IntroPageAdmin extends SuperPage {

    /**
     * Default
     */
    private static final long serialVersionUID = 1L;

    public IntroPageAdmin(UserInterface userInterface) {
	super(userInterface);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void createMainPanel() {
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	// XXX change when frame size is set
	mainPanel.setBorder(new EmptyBorder(25, 0, 0, 0));

	JLabel messageLabel = new JLabel("<html>Welcome to the <font color=red><b>Problem Solving App!</b></font>"
		+ "You have sign in as an Administrator of this platform. "
		+ "You can submit the <br><font color=green><u>configuration parameters</u></font> through a XML file. "
		+ "This document <br><font color=orange><u>Must</u></font> follow a pre-determined structure."
		+ "You can import a configuration file or download a template by interacting with the buttons bellow.");
	messageLabel.setFont(FrameUtils.cuteFont(14));
	mainPanel.add(messageLabel);

	FrameUtils.addEmptyLabels(mainPanel, 2);

	JButton submitButton = FrameUtils.cuteButton("Import a configuration file");
	submitButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {

	    }
	});
	mainPanel.add(submitButton);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	JButton importButton = FrameUtils.cuteButton("Download a template for the config.xml file");
	importButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {

	    }
	});
	mainPanel.add(importButton);

    }

    @Override
    protected void createButtonsPanel() {
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
    protected void onTop() {
	userInterface.getFrame().setTitle("Problem Solving App");
    }

    @Override
    protected boolean areAllDataWellFilled() {
	return true;
    }

    @Override
    protected void saveToProblem() {
    }

    @Override
    protected void clearDataFromPage() {
	// TODO Auto-generated method stub

    }

}
