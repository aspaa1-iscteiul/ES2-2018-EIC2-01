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

import frames.frameUtils.FrameUtils;
import frames.graphicalObjects.KnownOptimizationCriteriaSolutionsObject;
import frames.graphicalObjects.OptimizationCriteriaObject;

/**
 * This class represents the Known Solutions Page
 */

public class KnownOptimizationCriteriaSolutionsPage extends SuperPage {

    private static final long serialVersionUID = 1L;
    private ArrayList<KnownOptimizationCriteriaSolutionsObject> knownSolutionsList;
    private JButton nextButton; 
    private JPanel subSubMainPanel;

    /**
     * 
     * @param userInterface
     */
    public KnownOptimizationCriteriaSolutionsPage(UserInterface userInterface) {
	super(userInterface);
    }

    @Override
    protected void initialize() {
	knownSolutionsList = new ArrayList<KnownOptimizationCriteriaSolutionsObject>();
	nextButton = FrameUtils.cuteButton("Next");
    }

    @Override
    protected void createMainPanel() {
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	// XXX change when frame size is set
	mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

	JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	titlePanel.setBackground(Color.WHITE);
	JLabel titleLabel = new JLabel("Known Optimization Criteria Solution(s)");
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
	if(userInterface.isXmlFileWasImportedAtIndex(5)==true) {
	    setThisPage();
	    knownSolutionsList = userInterface.getKnownOptimizationCriteriaSolutionsList();
	    for(KnownOptimizationCriteriaSolutionsObject kso : knownSolutionsList) {
		subSubMainPanel.add(kso.transformIntoAPanel());
	    }
	    verifyIfAnyCriteriaWasAdded();
	    verifyIfAnyVariableWasDeleted();
	    reconstructToLookNice();
	    userInterface.putXmlFileWasImportedFalseAtIndex(5);
	} else {
	    if (knownSolutionsList.size() > 0) {
		for (KnownOptimizationCriteriaSolutionsObject kocso : knownSolutionsList) {
		    subSubMainPanel.add(kocso.transformIntoAPanel());
		}
		verifyIfAnyCriteriaWasAdded();
		verifyIfAnyVariableWasDeleted();
		reconstructToLookNice();
	    } else {
		if (userInterface.getDecisionVariablesFromPage().size() > 0) {
		    for (OptimizationCriteriaObject oc : userInterface.getOptimizationCriteriaFromPage()) {
			KnownOptimizationCriteriaSolutionsObject oc1 = new KnownOptimizationCriteriaSolutionsObject(this, oc.getVariableName(), 
				oc.getDataType());
			knownSolutionsList.add(oc1);
			subSubMainPanel.add(oc1.transformIntoAPanel());
		    }
		    userInterface.setKnownOptimizationCriteriaSolutionsList(knownSolutionsList);
		} else {
		    subSubMainPanel.add(warning);
		}
	    }
	    userInterface.refreshPage();
	}
    }

    /**
     * Verify if any decision variable was added to the decision variables list and if there's any need to update
     * the known solutions list 
     */
    private void verifyIfAnyCriteriaWasAdded() {
	if (userInterface.getOptimizationCriteriaFromPage().size() > 0) {
	    for (OptimizationCriteriaObject oco : userInterface.getOptimizationCriteriaFromPage()) {
		boolean nova = true;
		for (KnownOptimizationCriteriaSolutionsObject kocso : knownSolutionsList) {
		    if (oco.getVariableName().equals(kocso.getName().getText())) {
			nova = false;
			break;
		    }
		}
		if (nova == true) {
		    KnownOptimizationCriteriaSolutionsObject ksoAux = new KnownOptimizationCriteriaSolutionsObject(this, oco.getVariableName(),
			    oco.getDataType());
		    knownSolutionsList.add(ksoAux);
		    subSubMainPanel.add(ksoAux.transformIntoAPanel());
		}
	    }
	}
    }

    /**
     * Verify if any decision variable was deleted from the decision variables list and if there's any need to update
     * the known solutions list 
     */
    private void verifyIfAnyVariableWasDeleted() {
	ArrayList<KnownOptimizationCriteriaSolutionsObject>  knownSolutionsListAux = new ArrayList<KnownOptimizationCriteriaSolutionsObject>();
	for (KnownOptimizationCriteriaSolutionsObject kocso : knownSolutionsList) {
	    boolean toDelete = true;
	    for (OptimizationCriteriaObject oco : userInterface.getOptimizationCriteriaFromPage()) {
		if (kocso.getName().getText().equals(oco.getVariableName())) {
		    toDelete = false;
		    break;
		}
	    }
	    if (toDelete == true) {
		knownSolutionsListAux.add(kocso);
	    }
	}
	for(KnownOptimizationCriteriaSolutionsObject kocsoAux : knownSolutionsListAux) {
	    subSubMainPanel.remove(kocsoAux.transformIntoAPanel());
	    knownSolutionsList.remove(kocsoAux);
	}
    }

    /**Clean data and reconstruct to put everything in the right place
     * 
     */
    private void reconstructToLookNice() {
	subSubMainPanel.removeAll();
	for(KnownOptimizationCriteriaSolutionsObject kocso : knownSolutionsList) {
	    subSubMainPanel.add(kocso.transformIntoAPanel());
	}
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
	boolean[] tmp = new boolean[getOverallNumberOfKnownSolutionsNumber()];
	int count = 0;
	for (KnownOptimizationCriteriaSolutionsObject kso : knownSolutionsList) {
	    for (JTextField textField : kso.getTextfieldList()) {
		if (kso.validateInputs(textField) == false) {
		    tmp[count] = false;
		} else {
		    tmp[count] = true;
		}
		count++;
	    }
	}
	for(int i = 0; i != tmp.length; i++) {
	    if(tmp[i] == false) {
		return false;
	    }
	}
	return true;
    }

    /**
     * Get the total number of known solutions number
     * @return
     */
    private int getOverallNumberOfKnownSolutionsNumber() {
	int count = 0;
	for (KnownOptimizationCriteriaSolutionsObject kso : knownSolutionsList) {
	    count += kso.getTextfieldList().size();
	}
	return count;
    }

    @Override
    protected void saveToProblem() {
	organizeList();
	userInterface.setKnownOptimizationCriteriaSolutionsList(knownSolutionsList);
    }

    private void setThisPage() {
	for(KnownOptimizationCriteriaSolutionsObject kocso : userInterface.getKnownOptimizationCriteriaSolutionsList()) {
	    kocso.setPage(this);
	}
    }

    /**
     * Removes null data from KnownSolutionsList
     */
    public void organizeList() {
	for(KnownOptimizationCriteriaSolutionsObject kocso : knownSolutionsList) {
	    kocso.organizeSolutionsList();
	}
    }

    @Override
    protected void clearDataFromPage() {
	knownSolutionsList.clear();
    }

}