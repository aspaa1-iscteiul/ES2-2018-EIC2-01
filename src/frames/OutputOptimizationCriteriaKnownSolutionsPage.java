package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import frames.frameUtils.FrameUtils;
import frames.frameUtils.GraphGenerator;
import frames.graphicalObjects.OptimizationCriteriaObject;
import utils.FileReader;

public class OutputOptimizationCriteriaKnownSolutionsPage extends SuperPage {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel subPanel, subPanel2;
    private String algorithmName;

    public OutputOptimizationCriteriaKnownSolutionsPage(UserInterface userInterface, String algorithmName) {
	super(userInterface);
	this.algorithmName = algorithmName;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void createMainPanel() {
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	mainPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

	JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	infoPanel.setBackground(Color.white);
	JLabel decisionVariableLabel = new JLabel("Optimization Criteria     ");
	decisionVariableLabel.setFont(FrameUtils.cuteFont(14));
	infoPanel.add(decisionVariableLabel);
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
	scrollPane.setPreferredSize(new Dimension(140, 300));
	subMainPanel.add(scrollPane);

	JLabel whiteSpace = new JLabel("       ");
	subMainPanel.add(whiteSpace);

	subPanel2 = new JPanel();
	subPanel2.setLayout(new BoxLayout(subPanel2, BoxLayout.Y_AXIS));
	subPanel2.setBackground(Color.white);
	subPanel2.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
		BorderFactory.createEmptyBorder(2, 2, 2, 2)));
	subPanel2.setPreferredSize(new Dimension(350, 300));
	subPanel2.setMaximumSize(new Dimension(350, 300));
	subPanel2.setMinimumSize(new Dimension(350, 300));
	subMainPanel.add(subPanel2);

	mainPanel.add(subMainPanel);

    }
    
    private void constructPanel() {
	for(OptimizationCriteriaObject oco : userInterface.getOptimizationCriteriaFromPage()) {
	    JButton button = FrameUtils.cuteButton(oco.getVariableName());
	    button.setPreferredSize(new Dimension(50,30));
	    FrameUtils.addEmptyLabels(subPanel, 1);
	    button.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
		    subPanel2.removeAll();
		    FileReader fileReader = new FileReader();
		    subPanel2.add(GraphGenerator.createAndShowGui(fileReader.readFileAndReturnList(new File(System.getProperty("user.dir")
			    +"/experimentBaseDirectory/referenceFronts/" + userInterface.getProblem().getProblemName() + "."
			    + algorithmName + ".rf")).get(userInterface.getOptimizationCriteriaFromPage().indexOf(oco)),
			    userInterface.getProblem().getOptimizationCriteria().get(userInterface.getOptimizationCriteriaFromPage().indexOf(oco)).getKnownSolutions(),
			    oco.getVariableName()));
		    repaint();
		    updateUI();
		}
		
	    });
	    subPanel.add(button);
	}
    }
    
    @Override
    protected void createButtonsPanel() {
	JButton homeButton = FrameUtils.cuteButton("Home");
	homeButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		userInterface.returnFromOutputOptimizationCriteriaKnownSolutionsPage();
	    }
	});
	buttonsPanel.add(homeButton);
    }

    @Override
    protected void onTop() {
	userInterface.getFrame().setTitle("Known Solutions - Optimization Criteria");
	constructPanel();
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
