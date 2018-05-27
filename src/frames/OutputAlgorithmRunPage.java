package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import frames.frameUtils.FrameUtils;
import utils.FileReader;

public class OutputAlgorithmRunPage extends SuperPage {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel subPanel;
    private JPanel subPanel2;
    private String algorithmName;
    private int runNumber;

    public OutputAlgorithmRunPage(UserInterface userInterface, String algorithmName, int runNumber) {
	super(userInterface);
	this.algorithmName = algorithmName;
	this.runNumber = runNumber;
    }

    @Override
    protected void initialize() {
	userInterface.getFrame().setTitle("Algorithm - Run " + runNumber);
    }

    @Override
    protected void createMainPanel() {
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	mainPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

	JPanel infoPanel = new JPanel();
	infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
	infoPanel.setBackground(Color.white);
	JLabel decisionVariableLabel = new JLabel("Instantiated decision variables     ");
	decisionVariableLabel.setFont(FrameUtils.cuteFont(14));
	infoPanel.add(decisionVariableLabel);
	JLabel optimizationLabel = new JLabel("Resulting Optimization Criteria");
	optimizationLabel.setFont(FrameUtils.cuteFont(14));
	infoPanel.add(optimizationLabel);
	mainPanel.add(infoPanel);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	JPanel subMainPanel = new JPanel();
	subMainPanel.setLayout(new BoxLayout(subMainPanel, BoxLayout.X_AXIS));
	subMainPanel.setBackground(Color.white);

	subPanel = new JPanel();
	subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
	subPanel.setBackground(Color.white);
	subPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
		BorderFactory.createEmptyBorder(10, 10, 10, 10)));

	JScrollPane scrollPane = new JScrollPane(subPanel);
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	scrollPane.setPreferredSize(new Dimension(200, 260));
	subMainPanel.add(scrollPane);

	JLabel whiteSpace = new JLabel("       ");
	subMainPanel.add(whiteSpace);

	subPanel2 = new JPanel();
	subPanel2.setLayout(new BoxLayout(subPanel2, BoxLayout.Y_AXIS));
	subPanel2.setBackground(Color.white);
	subPanel2.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
		BorderFactory.createEmptyBorder(10, 10, 10, 10)));

	JScrollPane scrollPane2 = new JScrollPane(subPanel2);
	scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	scrollPane2.setPreferredSize(new Dimension(200, 260));
	subMainPanel.add(scrollPane2);

	mainPanel.add(subMainPanel);

    }

    @SuppressWarnings("static-access")
    private void constructPage(int runNumber){
	FileReader fileReader = new FileReader();
	ArrayList<Double> list = fileReader.readFileAndReturnListInRunPerspective(new File(System.getProperty("user.dir")
		+ "/experimentBaseDirectory/referenceFronts/" + userInterface.getProblem().getProblemName() + "."
		+ algorithmName + ".rs")).get(runNumber);
	for(Double doub : list) {
	    String str = userInterface.getProblem().getDecisionVariables().get(list.indexOf(doub)).getName() + "        " 
		    + doub.toString();
	    subPanel.add(new JLabel(str));
	}
	ArrayList<Double> list2 = fileReader.readFileAndReturnListInRunPerspective(new File(System.getProperty("user.dir")
		+ "/experimentBaseDirectory/referenceFronts/" + userInterface.getProblem().getProblemName() + "."
		+ algorithmName + ".rf")).get(runNumber);
	for(Double doub2 : list2) {
	    String str = userInterface.getProblem().getOptimizationCriteria().get(list2.indexOf(doub2)).getName() + "        " 
		    + doub2.toString();
	    subPanel2.add(new JLabel(str));
	}
    }

    @Override
    protected void createButtonsPanel() {
	JButton homeButton = FrameUtils.cuteButton("Home");
	homeButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		userInterface.returnFromOutputAlgorithmRunPage();
	    }
	});
	buttonsPanel.add(homeButton);
    }

    @Override
    protected void onTop() {
	userInterface.getFrame().setTitle(algorithmName + " - Run " + runNumber);
	constructPage(this.runNumber);
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

    @Override
    protected void clearDataFromPage() {
	// TODO Auto-generated method stub

    }

}
