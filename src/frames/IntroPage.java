package frames;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

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
		+ "optimal solution found by our <font color=orange><b>Metaheuristics Algorithm</b></font>.</html>");

	JLabel linkLabel = new JLabel(
		"<html>Find out more about <a href=\"http://jmetal.sourceforge.net/\">jMetal framework</a>.</html>");

	messageLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
	linkLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));

	if (isBrowsingSupported()) {
	    linkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    goToWebsite(linkLabel);
	}

	mainPanel.add(messageLabel);
	mainPanel.add(linkLabel);

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
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Select a problem configuration file");
		if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(null)) {
		    userInterface.goToNextPage();
		}
	    }
	});
	mainPanel.add(importButton);
    }

    private static boolean isBrowsingSupported() {
	if (!Desktop.isDesktopSupported()) {
	    return false;
	}
	boolean result = false;
	Desktop desktop = java.awt.Desktop.getDesktop();
	if (desktop.isSupported(Desktop.Action.BROWSE)) {
	    result = true;
	}
	return result;

    }

    private void goToWebsite(JLabel linkLabel) {
	linkLabel.addMouseListener(new MouseAdapter() {
	    public void mouseClicked(MouseEvent e) {
		try {
		    Desktop.getDesktop().browse(new URI("http://jmetal.sourceforge.net/"));
		} catch (URISyntaxException | IOException ex) {
		}
	    }
	});
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
