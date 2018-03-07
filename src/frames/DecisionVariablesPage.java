package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class DecisionVariablesPage extends SuperPage {

    /**
     * Default
     */
    private static final long serialVersionUID = 1L;

    private ArrayList<DecisionVariablesObject> decisionVariableList;
    private JPanel subSubMainPanel, warningPanel, warningPanel2, warningPanel3;

    public DecisionVariablesPage(UserInterface userInterface) {
	super(userInterface);
    }

    private JButton nextButton;

    @Override
    public void initialize() {
	nextButton = FrameUtils.cuteButton("Next");
	decisionVariableList = new ArrayList<DecisionVariablesObject>();

	warningPanel = createWarningPanel("Optimization criterias must have unique names");
	warningPanel2 = createWarningPanel("Lower bound and upper bound must agree with the data type selected");
	warningPanel3 = createWarningPanel("Lower bound must be lower then upper bound");
    }

    private JPanel createWarningPanel(String message) {
	JPanel warningPanel = new JPanel();
	warningPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	warningPanel.setBackground(Color.WHITE);
	JLabel warningIcon = new JLabel();
	warningIcon.setIcon(new ImageIcon("./src/frames/images/warning_icon.png"));
	warningPanel.add(warningIcon);
	JLabel warning = new JLabel(message);
	warning.setForeground(Color.red);
	warningPanel.add(warning);
	return warningPanel;
    }

    @Override
    public void createMainPanel() {
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	// XXX change when frame size is set
	mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

	JPanel titlePanel = new JPanel();
	titlePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	titlePanel.setBackground(Color.WHITE);
	titlePanel.add(new JLabel("Decision Variables"));
	mainPanel.add(titlePanel);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	JPanel subMainPanel = new JPanel();
	subMainPanel.setBackground(Color.WHITE);
	subMainPanel.setLayout(new BoxLayout(subMainPanel, BoxLayout.Y_AXIS));
	subMainPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
		BorderFactory.createEmptyBorder(10, 10, 10, 10)));

	JPanel infoPanel = new JPanel();
	infoPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	infoPanel.setBackground(Color.WHITE);
	JLabel name = new JLabel("Name");
	// to add space between the labels
	name.setBorder(new EmptyBorder(0, 0, 0, 45));
	infoPanel.add(name);
	JLabel dataType = new JLabel("Data Type");
	dataType.setBorder(new EmptyBorder(0, 0, 0, 15));
	infoPanel.add(dataType);
	infoPanel.add(new JLabel("Lower Bound  "));
	infoPanel.add(new JLabel("Upper Bound  "));
	// infoPanel.add(new JLabel("Domain"));

	subMainPanel.add(infoPanel);

	subSubMainPanel = new JPanel();
	subSubMainPanel.setBackground(Color.WHITE);
	subSubMainPanel.setLayout(new BoxLayout(subSubMainPanel, BoxLayout.Y_AXIS));

	DecisionVariablesObject decisionVariable = new DecisionVariablesObject(this);
	decisionVariableList.add(decisionVariable);
	subSubMainPanel.add(decisionVariable.transformIntoAPanel());

	subMainPanel.add(subSubMainPanel);

	JPanel addOptionPanel = new JPanel();
	addOptionPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	addOptionPanel.setBackground(Color.WHITE);
	JLabel addIcon = new JLabel();
	addIcon.setIcon(new ImageIcon("./src/frames/images/add_icon.png"));

	DecisionVariablesPage tmp = this;

	addIcon.addMouseListener(new MouseListener() {

	    @Override
	    public void mouseClicked(MouseEvent arg0) {
		EventQueue.invokeLater(new Runnable() {
		    public void run() {
			nextButton.setEnabled(false);
			DecisionVariablesObject decisionVariable = new DecisionVariablesObject(tmp);
			decisionVariableList.add(decisionVariable);
			subSubMainPanel.add(decisionVariable.transformIntoAPanel());
			validate(); // to update window
			repaint(); // to update window
		    }
		});
	    }

	    @Override
	    public void mouseEntered(MouseEvent arg0) {
	    }

	    @Override
	    public void mouseExited(MouseEvent arg0) {
	    }

	    @Override
	    public void mousePressed(MouseEvent arg0) {
	    }

	    @Override
	    public void mouseReleased(MouseEvent arg0) {
	    }

	});
	addOptionPanel.add(addIcon, BorderLayout.WEST);
	addOptionPanel.add(new JLabel("Add new variable"));

	subMainPanel.add(addOptionPanel);

	JScrollPane scrollPane = new JScrollPane(subMainPanel);
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	mainPanel.add(scrollPane);

	FrameUtils.addEmptyLabels(mainPanel, 1);

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

	nextButton.setEnabled(false);
	nextButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		// if (verifyIfNamesAreUnique() == true && validateAllData() ==
		// true) {
		userInterface.goToNextPage();
		userInterface.setKnownSolutionsList(getKnownSolutionsFromDecisionVariables());
		// }
	    }
	});
	buttonsPanel.add(nextButton);
    }

    @Override
    public void onTop() {
	userInterface.getFrame().setTitle("Problem Solving App");
    }

    public void removeDecisionVariableFromList(DecisionVariablesObject dvo) {
	decisionVariableList.remove(dvo);
	subSubMainPanel.remove(dvo.transformIntoAPanel());

	validateData();

	refreshPage();
    }

    private ArrayList<KnownSolutionsObject> getKnownSolutionsFromDecisionVariables() {
	ArrayList<KnownSolutionsObject> knownSolutions = new ArrayList<KnownSolutionsObject>();
	for (DecisionVariablesObject dvo : decisionVariableList) {
	    if (!dvo.getName().getText().trim().isEmpty()) {
		knownSolutions.add(new KnownSolutionsObject(null, dvo.getName().getText()));
	    }
	}
	return knownSolutions;
    }

    public void refreshPage() {
	userInterface.getFrame().validate();
	userInterface.getFrame().repaint();
	userInterface.getFrame().pack();
    }

    public boolean isNameRepeated(String varName) {
	int count = 0;
	for (DecisionVariablesObject dvoList : decisionVariableList)
	    if (dvoList.getName().getText().equals(varName))
		count++;
	return count >= 2;
    }

    public void blockNextButton(boolean b) {
	nextButton.setEnabled(!b);
    }

    public void showWarning(boolean show) {
	SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		if (show)
		    mainPanel.add(warningPanel);
		else
		    mainPanel.remove(warningPanel);
		refreshPage();
	    }
	});
    }

    public void showWarning2(boolean show) {
	SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		if (show)
		    mainPanel.add(warningPanel2);
		else
		    mainPanel.remove(warningPanel2);
		refreshPage();
	    }
	});
    }

    public void showWarning3(boolean show) {
	SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		if (show)
		    mainPanel.add(warningPanel3);
		else
		    mainPanel.remove(warningPanel3);
		refreshPage();
	    }
	});
    }

    private void validateData() {
	for (DecisionVariablesObject dvo : decisionVariableList)
	    if (!dvo.isWellFilled()) {
		nextButton.setEnabled(false);
		return;
	    }
    }

}
