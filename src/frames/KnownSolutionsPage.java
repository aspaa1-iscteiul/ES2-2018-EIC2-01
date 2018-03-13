package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

/**
 * This class represents the Known Solutions Page
 */

public class KnownSolutionsPage extends SuperPage {

    private static final long serialVersionUID = 1L;
    private ArrayList<KnownSolutionsObject> knownSolutionsList;
    private JButton nextButton; 
    private JPanel subSubMainPanel;

    /**
     * 
     * @param userInterface
     */
    public KnownSolutionsPage(UserInterface userInterface) {
	super(userInterface);
    }

    @Override
    protected void initialize() {
	knownSolutionsList = new ArrayList<KnownSolutionsObject>();
	nextButton = FrameUtils.cuteButton("Next");
    }

    @Override
    protected void createMainPanel() {
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	// XXX change when frame size is set
	mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

	JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	titlePanel.setBackground(Color.WHITE);
	JLabel titleLabel = new JLabel("Known Solution(s)");
	titleLabel.setFont(FrameUtils.cuteFont(16));
	titlePanel.add(titleLabel);
	mainPanel.add(titlePanel);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	JPanel subMainPanel = new JPanel(new BorderLayout());
	subMainPanel.setBackground(Color.WHITE);
	subMainPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
		BorderFactory.createEmptyBorder(10, 10, 10, 10)));

	JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	infoPanel.setBackground(Color.WHITE);
	JLabel name = new JLabel("Name");
	name.setBorder(new EmptyBorder(0, 0, 0, 45)); // to add space between
	// the labels
	name.setFont(FrameUtils.cuteFont(12));
	infoPanel.add(name);
	JLabel label = new JLabel("Solution(s)");
	label.setFont(FrameUtils.cuteFont(12));
	infoPanel.add(label);

	subMainPanel.add(infoPanel, BorderLayout.NORTH);

	subSubMainPanel = new JPanel();
	subSubMainPanel.setBackground(Color.WHITE);
	subSubMainPanel.setLayout(new BoxLayout(subSubMainPanel, BoxLayout.Y_AXIS));

	subMainPanel.add(subSubMainPanel, BorderLayout.CENTER);

	JScrollPane scrollPane = new JScrollPane(subMainPanel);
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	scrollPane.setPreferredSize(new Dimension(450, 250));

	mainPanel.add(scrollPane);

	FrameUtils.addEmptyLabels(mainPanel, 1);
    }

    @Override
    protected void createButtonsPanel() {
	JButton backButton = FrameUtils.cuteButton("Back");
	backButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		userInterface.goToPreviousPage();
		saveToProblem();
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

	nextButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		userInterface.goToNextPage();
	    }
	});
	buttonsPanel.add(nextButton);
    }

    /**
     * This method allows the saving of the inputs and updates the frame if a variable is added or deleted, keeping the inputs 
     * of the remaining variables while refreshing the frame.
     */
    @Override
    protected void onTop() {
	userInterface.getFrame().setTitle("Problem Solving App");
	JPanel warning = warningPanel();
	subSubMainPanel.removeAll();
	if (knownSolutionsList.size() > 0) {
	    for (KnownSolutionsObject kso : knownSolutionsList) {
		subSubMainPanel.add(kso.transformIntoAPanel());
	    }
	    if (userInterface.getDecisionVariablesFromPage().size() > 0) {
		for (DecisionVariablesObject dvo : userInterface.getDecisionVariablesFromPage()) {
		    boolean nova = true;
		    for (KnownSolutionsObject kso : knownSolutionsList) {
			if (dvo.getVariableName().equals(kso.getName().getText())) {
			    nova = false;
			    break;
			}
		    }
		    if (nova == true) {
			KnownSolutionsObject ksoAux = new KnownSolutionsObject(this, dvo.getVariableName(),
				dvo.getDataType(), dvo.getLowerBound(), dvo.getUpperBound(), dvo.getInvalidValues());
			knownSolutionsList.add(ksoAux);
			subSubMainPanel.add(ksoAux.transformIntoAPanel());
		    }
		}
	    }
	    if (knownSolutionsList.size() != userInterface.getDecisionVariablesFromPage().size()) {
		for (KnownSolutionsObject kso : knownSolutionsList) {
		    boolean toDelete = false;
		    for (DecisionVariablesObject dvo : userInterface.getDecisionVariablesFromPage()) {
			if (kso.getName().getText().equals(dvo.getVariableName())) {
			    toDelete = true;
			    break;
			}
		    }
		    if (toDelete == false) {
			subSubMainPanel.remove(kso.transformIntoAPanel());
			KnownSolutionsObject ksoAux = kso;
			knownSolutionsList.remove(ksoAux);
		    }
		}
	    }
	} else {
	    if (userInterface.getDecisionVariablesFromPage().size() > 0) {
		for (DecisionVariablesObject dvo : userInterface.getDecisionVariablesFromPage()) {
		    KnownSolutionsObject kso = new KnownSolutionsObject(this, dvo.getVariableName(), dvo.getDataType(),
			    dvo.getLowerBound(), dvo.getUpperBound(), dvo.getInvalidValues());
		    knownSolutionsList.add(kso);
		    subSubMainPanel.add(kso.transformIntoAPanel());
		}
		userInterface.setKnownSolutionsList(knownSolutionsList);
	    } else {
		subSubMainPanel.add(warning);
	    }
	}
	userInterface.refreshPage();
    }

    /**
     * Creates a warning Panel annoucing that no variables were yet created
     * @return
     */
    private JPanel warningPanel() {
	JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	panel.setBackground(Color.WHITE);
	JLabel warning = new JLabel("No variables created");
	warning.setForeground(Color.red);
	panel.add(warning);
	return panel;
    }

    @Override
    protected boolean areAllDataWellFilled() {
	boolean tmp = true;
	for (KnownSolutionsObject kso : userInterface.getKnownSolutionsList()) {
	    for (JTextField textField : kso.getTextfieldList()) {
		if (kso.validateInputs(textField) == false) {
		    tmp = false;
		}
	    }
	}
	return tmp;
    }

    @Override
    protected void saveToProblem() {
	userInterface.setKnownSolutionsList(knownSolutionsList);
    }

}