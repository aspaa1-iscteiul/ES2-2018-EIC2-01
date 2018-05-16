package frames;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import frames.frameUtils.FrameUtils;

public class OutputAlgorithmsPage extends SuperPage {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public OutputAlgorithmsPage(UserInterface userInterface) {
	super(userInterface);
	// TODO Auto-generated constructor stub
    }

    @Override
    protected void initialize() {
	userInterface.getFrame().setTitle("Algorithm");
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
	
	JPanel subPanel = new JPanel();
	subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
	subPanel.setBackground(Color.white);
	subPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
		BorderFactory.createEmptyBorder(10, 10, 10, 10)));

	FrameUtils.addEmptyLabels(subPanel, 1);
	JScrollPane scrollPane = new JScrollPane(subPanel);
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	scrollPane.setPreferredSize(new Dimension(200, 260));
	subMainPanel.add(scrollPane);
	
	JLabel whiteSpace = new JLabel("       ");
	subMainPanel.add(whiteSpace);
	
	JPanel subPanel2 = new JPanel();
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

    @Override
    protected void onTop() {
	userInterface.getFrame().setTitle("Algorithm");
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
