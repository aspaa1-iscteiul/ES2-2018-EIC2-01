package frames;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import frames.frameUtils.FrameUtils;
import frames.graphicalObjects.FitnessFunctionObject;

public class FitnessFunctionPage extends SuperPage {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private FitnessFunctionObject fitnessFunction;
    private JPanel subMainPanel;

    public FitnessFunctionPage(UserInterface userInterface) {
	super(userInterface);
	// TODO Auto-generated constructor stub
    }

    @Override
    protected void initialize() {
	fitnessFunction = new FitnessFunctionObject(this);
	subMainPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));

    }

    @Override
    protected void createMainPanel() {
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	// XXX change when frame size is set
	mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

	JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	titlePanel.setBackground(Color.WHITE);
	JLabel titleLabel = new JLabel("Fitness Function");
	titleLabel.setFont(FrameUtils.cuteFont(16));
	titlePanel.add(titleLabel);
	mainPanel.add(titlePanel);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	JPanel infoPanel = new JPanel(new BorderLayout());
	infoPanel.setBackground(Color.WHITE);
	JPanel iconPanel = new JPanel(new BorderLayout());
	iconPanel.setBackground(Color.WHITE);
	JLabel infoIcon = new JLabel();
	infoIcon.setIcon(new ImageIcon("./src/frames/images/info_icon.png"));
	iconPanel.add(infoIcon, BorderLayout.NORTH);
	infoPanel.add(iconPanel, BorderLayout.WEST);
	JLabel infoLabel = new JLabel("<html>Be advised that the plataform assumes that in the optimization process, "
		+ "<br> optimization means minimizing the optimization criteria. Therefore, the <br>"
		+ "implementation(s) of the fitness function(s) provided should be consistent<br>"
		+ "with this assumption.</html>");
	infoLabel.setFont(FrameUtils.cuteFont(12));
	infoLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
	infoPanel.add(infoLabel, BorderLayout.CENTER);

	mainPanel.add(infoPanel);

	FrameUtils.addEmptyLabels(mainPanel, 1);
	subMainPanel.setBackground(Color.WHITE);
	subMainPanel.add(fitnessFunction.transformIntoAPanel());
	mainPanel.add(subMainPanel);

    }

    @Override
    protected void onTop() {
	userInterface.getFrame().setTitle("Problem Solving App");
	if(userInterface.isXmlFileWasImportedAtIndex(2)==true) {
	    subMainPanel.removeAll();
	    fitnessFunction = userInterface.getFitnessFunctionObject();
	    fitnessFunction.setPageAssociated(this);
	    subMainPanel.add(fitnessFunction.transformIntoAPanelWhenReadFromXML());
	}
	userInterface.putXmlFileWasImportedFalseAtIndex(2);
    }

    @Override
    protected boolean areAllDataWellFilled() {
	return verifyIfJarFilesWereUploaded();
    }

    /**
     * Goes over the fitness function objects from the page and verifys
     * if they have a jar file associated
     */
    public boolean verifyIfJarFilesWereUploaded() {	
	if(fitnessFunction.isJarFileUploaded()==false) {
	    FrameUtils.errorFormat(fitnessFunction.getUploadButton(), "Every fitness function must have a jar file uploaded");
	    return false;
	} else {
	    FrameUtils.normalFormat(fitnessFunction.getUploadButton());
	    return true;
	}
    }

    @Override
    protected void saveToProblem() {
	userInterface.setFitnessFunctionObject(fitnessFunction);
    }

    @Override
    protected void clearDataFromPage() {
	// TODO Auto-generated method stub
    }

}
