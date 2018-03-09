package frames;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
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

import utils.UserFileUtils;

public class IntroPage extends SuperPage {

    /**
     * Default
     */
    private static final long serialVersionUID = 1L;

    public IntroPage(UserInterface userInterface) {
	super(userInterface);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void createMainPanel() {
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	// XXX change when frame size is set
	mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

	JLabel messageLabel = new JLabel("<html>With the <font color=red><b>Problem Solving App</b></font> "
		+ "you can submit your optimization problems for<br><font color=green><u>AUTOMATIC</u></font> "
		+ "evalution! According to the characteristics of your problem you will<br>be returned the "
		+ "optimal solution found by our <font color=orange><b>Metaheuristics Algorithm</b></font>.</html>");

	JLabel linkLabel = new JLabel("<html>Find out more about <a href=\"\">jMetal framework</a>.</html>");

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
		if (importXMLFile())
		    userInterface.goToNextPage();
	    }
	});
	mainPanel.add(importButton);
    }

    private boolean importXMLFile() {
	JFileChooser fileChooser = new JFileChooser();
	// Launches the JFileChooser on the Desktop directory
	fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
	fileChooser.setDialogTitle("Select a problem configuration file");
	// Prevents selection of multiple options
	fileChooser.setMultiSelectionEnabled(false);
	// Only files with the XML extension are visible
	fileChooser.setFileFilter(new FileNameExtensionFilter("XML File", "xml"));
	fileChooser.setAcceptAllFileFilterUsed(false);

	if (fileChooser.showOpenDialog(userInterface.getFrame()) == JFileChooser.APPROVE_OPTION) {
	    userInterface.setProblem(UserFileUtils.readFromXML(fileChooser.getSelectedFile().getAbsolutePath()));
	    return true;
	}
	return false;
    }

    private static boolean isBrowsingSupported() {
	if (!Desktop.isDesktopSupported()) {
	    return false;
	}
	return Desktop.getDesktop().isSupported(Desktop.Action.BROWSE);

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
	// TODO Auto-generated method stub
	
    }

}
