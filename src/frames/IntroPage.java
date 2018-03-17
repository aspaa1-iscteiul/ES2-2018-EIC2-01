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
	mainPanel.setBorder(new EmptyBorder(50, 0, 0, 0));

	JLabel messageLabel = new JLabel("<html>With the <font color=red><b>Problem Solving App</b></font> "
		+ "you can submit your optimization problems for<br><font color=green><u>AUTOMATIC</u></font> "
		+ "evalution! According to the characteristics of your problem you will<br>be returned the "
		+ "optimal solution found by our <font color=orange><b>Metaheuristics Algorithm</b></font>.</html>");
	messageLabel.setFont(FrameUtils.cuteFont(14));
	JLabel linkLabel = new JLabel("<html>Find out more about <a href=\"\">jMetal framework</a>.</html>");
	linkLabel.setFont(FrameUtils.cuteFont(14));

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
		if(userInterface.wasSomethingImported()==true){
		    userInterface.cleanData();
		}
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
	fileChooser.setCurrentDirectory(new File( "C:\\Users\\Rodrigo\\git\\ES2-2018-EIC2-01\\src\\jUnitTests\\testFiles\\userConfigTest2.xml"));
	fileChooser.setDialogTitle("Select a problem configuration file");
	// Prevents selection of multiple options
	fileChooser.setMultiSelectionEnabled(false);
	// Only files with the XML extension are visible
	fileChooser.setFileFilter(new FileNameExtensionFilter("XML File", "xml"));
	fileChooser.setAcceptAllFileFilterUsed(false);

	if (fileChooser.showOpenDialog(userInterface.getFrame()) == JFileChooser.APPROVE_OPTION) {
	    userInterface.setProblem(UserFileUtils.readFromXML(fileChooser.getSelectedFile().getAbsolutePath()));
	    userInterface.createProblemId();
	    userInterface.createDecisionVariableFromProblem(null);
	    userInterface.createFitnessFunctionFromProblem(null);
	    userInterface.createOptimizationCriteriaFromProblem(null);
	    userInterface.createKnownSolutionsFromProblem(null);
	    userInterface.createTimeConstraints();
	    userInterface.setWasSomethingImported(true);
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
    }

    @Override
    protected void clearDataFromPage() {
	// TODO Auto-generated method stub

    }

}
