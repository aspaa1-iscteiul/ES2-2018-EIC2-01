package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import frames.frameUtils.FrameUtils;
import frames.graphicalObjects.DecisionVariablesObject;

public class OutputKnownSolutionsPage extends SuperPage {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel subPanel;

    public OutputKnownSolutionsPage(UserInterface userInterface) {
	super(userInterface);
    }

    @Override
    protected void initialize() {
	userInterface.getFrame().setTitle("Known Solutions");	
    }

    @Override
    protected void createMainPanel() {
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	mainPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

	JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	infoPanel.setBackground(Color.white);
	JLabel decisionVariableLabel = new JLabel("Decision Variables     ");
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
	scrollPane.setPreferredSize(new Dimension(140, 260));
	subMainPanel.add(scrollPane);

	JLabel whiteSpace = new JLabel("       ");
	subMainPanel.add(whiteSpace);

	JPanel subPanel2 = new JPanel();
	subPanel2.setLayout(new BoxLayout(subPanel2, BoxLayout.Y_AXIS));
	subPanel2.setBackground(Color.white);
	subPanel2.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
		BorderFactory.createEmptyBorder(10, 10, 10, 10)));
	subPanel2.setPreferredSize(new Dimension(300, 260));
	subPanel2.setMaximumSize(new Dimension(300, 260));
	subPanel2.setMinimumSize(new Dimension(300, 260));
	subMainPanel.add(subPanel2);

	mainPanel.add(subMainPanel);

    }
    
    private void constructPanel() {
	for(DecisionVariablesObject dvo : userInterface.getDecisionVariablesFromPage()) {
	    JButton button = new JButton(dvo.getVariableName());
	    subPanel.add(button);
	}
    }

    @Override
    protected void onTop() {
	userInterface.getFrame().setTitle("Known Solutions");
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
