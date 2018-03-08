package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class DecisionVariablesPage extends SuperPage {

    /**
     * Default
     */
    private static final long serialVersionUID = 1L;

    private ArrayList<DecisionVariablesObject> decisionVariableList;
    private JPanel subSubMainPanel, warningPanel1, warningPanel2, warningPanel3;
    private JButton nextButton;

    public DecisionVariablesPage(UserInterface userInterface) {
	super(userInterface);
    }

    @Override
    public void initialize() {
	nextButton = FrameUtils.cuteButton("Next");
	decisionVariableList = new ArrayList<DecisionVariablesObject>();

	warningPanel1 = createWarningPanel("Optimization criterias must have unique names");
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

	subMainPanel.add(infoPanel);

	subSubMainPanel = new JPanel();
	subSubMainPanel.setBackground(Color.WHITE);
	subSubMainPanel.setLayout(new BoxLayout(subSubMainPanel, BoxLayout.Y_AXIS));

	DecisionVariablesObject decisionVariable = new DecisionVariablesObject(this);
	decisionVariableList.add(decisionVariable);
	subSubMainPanel.add(decisionVariable.transformIntoAPanel());

	subMainPanel.add(subSubMainPanel);

	subMainPanel.add(addOptionPanel());

	JScrollPane scrollPane = new JScrollPane(subMainPanel);
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	mainPanel.add(scrollPane);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	importFromFilePanel();
    }

    private JPanel addOptionPanel() {
	JPanel addOptionPanel = new JPanel();
	addOptionPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	addOptionPanel.setBackground(Color.WHITE);
	JLabel addIcon = new JLabel();
	addIcon.setIcon(new ImageIcon("./src/frames/images/add_icon.png"));

	final DecisionVariablesPage tmp = this;

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
	return addOptionPanel;
    }

    private void importFromFilePanel() {
	JPanel panel = new JPanel();
	panel.setLayout(new FlowLayout(FlowLayout.LEADING));
	panel.setBackground(Color.WHITE);
	JButton importFromFile = FrameUtils.cuteButton("Import from file");
	importFromFile.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(userInterface.getFrame(),
			"The file to import must have only one variable name per line, the variable name could "
				+ "not have spaces, if it has spaces the spaces will be removed");

		JFileChooser fileChooser = new JFileChooser();
		// Launches the JFileChooser on the Desktop directory
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
		fileChooser.setDialogTitle("Select a file with the variables names");
		// Prevents selection of multiple options
		fileChooser.setMultiSelectionEnabled(false);

		if (fileChooser.showOpenDialog(userInterface.getFrame()) == JFileChooser.APPROVE_OPTION) {
		    readImportedFile(fileChooser.getSelectedFile(), askUserForVariableAttributes());
		}
	    }
	});
	panel.add(importFromFile);
	mainPanel.add(panel);
    }

    private String[] askUserForVariableAttributes() {
	JPanel inputPanel = new JPanel();
	inputPanel.setLayout(new GridLayout(3, 2));
	inputPanel.add(new JLabel("Data Type: "));
	JComboBox<String> dataType = new JComboBox<String>(new String[] { "Integer", "Double" });
	inputPanel.add(dataType);
	inputPanel.add(new JLabel("Lower Bound: "));
	JTextField lowerBound = new JTextField();
	inputPanel.add(lowerBound);
	inputPanel.add(new JLabel("Upper Bound: "));
	JTextField upperBound = new JTextField();
	inputPanel.add(upperBound);

	if (JOptionPane.showConfirmDialog(userInterface.getFrame(), inputPanel, "Enter the attributes to all variables",
		JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
	    String type = (String) dataType.getSelectedItem();
	    double lower, upper;
	    try {
		lower = type.equals("Integer") ? Integer.parseInt(lowerBound.getText())
			: Double.parseDouble(lowerBound.getText());
	    } catch (NumberFormatException e) {
		if (!lowerBound.getText().equals("")) {
		    JOptionPane.showMessageDialog(userInterface.getFrame(),
			    "The lower bound given was not a valid number");
		    return askUserForVariableAttributes();
		}
		lower = Double.MIN_VALUE;
	    }
	    try {
		upper = type.equals("Integer") ? Integer.parseInt(upperBound.getText())
			: Double.parseDouble(upperBound.getText());
	    } catch (NumberFormatException e) {
		if (!upperBound.getText().equals("")) {
		    JOptionPane.showMessageDialog(userInterface.getFrame(),
			    "The upper bound given was not a valid number");
		    return askUserForVariableAttributes();
		}
		upper = Double.MAX_VALUE;
	    }
	    if (lower < upper)
		return new String[] { type, lowerBound.getText(), upperBound.getText() };
	    else {
		JOptionPane.showMessageDialog(userInterface.getFrame(),
			"The lower bound must be lower then the upper bound");
		return askUserForVariableAttributes();
	    }
	}
	return null;
    }

    private void readImportedFile(File selectedFile, String[] values) {
	if (values == null)
	    return;
	try {
	    Scanner scn = new Scanner(selectedFile);
	    while (scn.hasNextLine()) {
		String line = scn.nextLine().replaceAll(" ", "");
		if (!line.equals("")) {
		    DecisionVariablesObject decisionVariable = new DecisionVariablesObject(this);
		    decisionVariable.setVariableName(line);
		    decisionVariable.setVariableDataType(values[0]);
		    decisionVariable.setLowerBound(values[1]);
		    decisionVariable.setUpperBound(values[2]);
		    decisionVariableList.add(decisionVariable);
		    subSubMainPanel.add(decisionVariable.transformIntoAPanel());
		}
	    }
	    scn.close();

	    // removes variables with empty names
	    for (int index = 0; index < decisionVariableList.size(); index++)
		if (decisionVariableList.get(index).getVariableName().equals("")) {
		    subSubMainPanel.remove(decisionVariableList.get(index).transformIntoAPanel());
		    decisionVariableList.remove(index);
		    index--;
		}

	    isAllVariablesWellFilled();
	} catch (FileNotFoundException e) {
	    JOptionPane.showMessageDialog(userInterface.getFrame(),
		    "The file " + selectedFile.getAbsolutePath() + " doesn't exists");
	}
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
		userInterface.goToNextPage();
		userInterface.setKnownSolutionsList(getKnownSolutionsFromDecisionVariables());
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

	isAllVariablesWellFilled();

	refreshPage();
    }

    private ArrayList<KnownSolutionsObject> getKnownSolutionsFromDecisionVariables() {
	ArrayList<KnownSolutionsObject> knownSolutions = new ArrayList<KnownSolutionsObject>();
	for (DecisionVariablesObject dvo : decisionVariableList) {
	    if (!dvo.getVariableName().trim().isEmpty()) {
		knownSolutions.add(new KnownSolutionsObject(null, dvo.getVariableName(), dvo.getDataType(), dvo.getLowerBound(), dvo.getUpperBound()));
		System.out.println(dvo.getDataType());
	    }
	}
	return knownSolutions;
    }

    /**
     * Refreshes the frame
     * 
     * @see #validate()
     * @see #repaint()
     */
    public void refreshPage() {
	userInterface.getFrame().validate();
	userInterface.getFrame().repaint();
	userInterface.getFrame().pack();
    }

    /**
     * Returns {@code true} if there is at least other variable with the same
     * name, otherwise {@code false}
     * 
     * @param varName
     *            the {@code String} to compare to the other variable names in
     *            the {@linkplain #decisionVariableList}
     * 
     * @return {@code true} if there is at least other variable with the same
     *         name, otherwise {@code false}
     */
    public boolean isNameRepeated(String varName) {
	int count = 0;
	for (DecisionVariablesObject dvo : decisionVariableList)
	    if (!dvo.getVariableName().equals("") && dvo.getVariableName().equals(varName))
		count++;
	return count >= 2;
    }

    /**
     * Blocks the {@linkplain #nextButton} if <b>b</b> is {@code true},
     * otherwise unblocks
     * 
     * @param b
     *            {@code true} if it is to block {@linkplain #nextButton},
     *            otherwise if it is to unblock
     * 
     * @see #setEnabled(boolean)
     */
    public void blockNextButton(boolean b) {
	nextButton.setEnabled(!b);
    }

    /**
     * If <b>show</b> is {@code true} it adds the warning panel's <b>number</b>,
     * otherwise it removes the warning panel's <b>number</b>
     * 
     * @param show
     *            {@code true} if it is to add the warning panel, {@code false}
     *            if it is to remove
     * 
     * @param number
     *            points to the respective warning panel
     * 
     * @see #warningPanel1
     * @see #warningPanel2
     * @see #warningPanel3
     */
    public void showWarning(final boolean show, final int number) {
	SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		JPanel panel = number == 1 ? warningPanel1 : (number == 2 ? warningPanel2 : warningPanel3);
		if (show)
		    mainPanel.add(panel);
		else
		    mainPanel.remove(panel);
		refreshPage();
	    }
	});
    }

    /**
     * Verifies if that all {@linkplain DecisionVariablesObject} in the
     * {@linkplain #decisionVariableList} are well field
     * 
     * @see DecisionVariablesObject#isWellFilled()
     */
    public void isAllVariablesWellFilled() {
	if(decisionVariableList.size() == 0) {
	    nextButton.setEnabled(false);
	}
	for (DecisionVariablesObject dvo2 : decisionVariableList)
	    if (!dvo2.isWellFilled())
		return;
    }

}
