package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.border.EmptyBorder;

import frames.frameUtils.FrameUtils;
import frames.graphicalObjects.DecisionVariablesObject;

/**
 * This class represents the Decision Variables Page
 */

public class DecisionVariablesPage extends SuperPage {

    private static final long serialVersionUID = 1L;
    private ArrayList<DecisionVariablesObject> decisionVariableList;
    private JPanel subSubMainPanel;
    private JTextField decisionVariablesSetName;

    /**
     * 
     * @param userInterface
     */
    public DecisionVariablesPage(UserInterface userInterface) {
	super(userInterface);
    }

    @Override
    protected void initialize() {
	decisionVariableList = new ArrayList<DecisionVariablesObject>();
	subSubMainPanel = new JPanel();
    }

    @Override
    protected void createMainPanel() {
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	mainPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

	JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	titlePanel.setBackground(Color.WHITE);
	JLabel titleLabel = new JLabel("Decision Variables");
	titleLabel.setFont(FrameUtils.cuteFont(16));
	titlePanel.add(titleLabel);

	mainPanel.add(titlePanel);

	JPanel decisionVariablesSetPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	decisionVariablesSetPanel.setBackground(Color.WHITE);
	JLabel setNameLabel = new JLabel("Decision variables' set name: ");
	setNameLabel.setFont(FrameUtils.cuteFont(12));
	decisionVariablesSetName = new JTextField(5);
	decisionVariablesSetName.setBorder(FrameUtils.cuteBorder());
	decisionVariablesSetPanel.add(setNameLabel);
	decisionVariablesSetPanel.add(decisionVariablesSetName);

	mainPanel.add(decisionVariablesSetPanel);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	JPanel subMainPanel = new JPanel(new BorderLayout());
	subMainPanel.setBackground(Color.WHITE);
	subMainPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
		BorderFactory.createEmptyBorder(10, 10, 10, 10)));

	subMainPanel.add(labelsPanel(), BorderLayout.NORTH);

	subSubMainPanel.setBackground(Color.WHITE);
	subSubMainPanel.setLayout(new BoxLayout(subSubMainPanel, BoxLayout.Y_AXIS));

	DecisionVariablesObject decisionVariable = new DecisionVariablesObject(this);
	decisionVariableList.add(decisionVariable);
	subSubMainPanel.add(decisionVariable.transformIntoAPanel());

	subMainPanel.add(subSubMainPanel, BorderLayout.CENTER);

	subMainPanel.add(addOptionPanel(), BorderLayout.SOUTH);

	JScrollPane scrollPane = new JScrollPane(subMainPanel);
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	scrollPane.setPreferredSize(new Dimension(510, 200));
	mainPanel.add(scrollPane);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	importFromFilePanel();
    }

    /**
     * Creates the panel with the labels that display information 
     * about the decision variables attributes
     * @return
     */
    private JPanel labelsPanel() {
	JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	panel.setBackground(Color.WHITE);
	JLabel name = new JLabel(" Name");
	// to add space between the labels
	name.setBorder(new EmptyBorder(0, 0, 0, 52));
	name.setFont(FrameUtils.cuteFont(12));
	panel.add(name);
	JLabel dataType = new JLabel("Data Type");
	dataType.setBorder(new EmptyBorder(0, 0, 0, 15));
	dataType.setFont(FrameUtils.cuteFont(12));
	panel.add(dataType);
	JLabel lowerLabel = new JLabel("Lower Bound     ");
	lowerLabel.setFont(FrameUtils.cuteFont(12));
	panel.add(lowerLabel);
	JLabel upperLabel = new JLabel("Upper Bound");
	upperLabel.setBorder(new EmptyBorder(0, 0, 0, 16));
	upperLabel.setFont(FrameUtils.cuteFont(12));
	panel.add(upperLabel);
	JLabel invalidValuesLabel = new JLabel("Invalid values");
	invalidValuesLabel.setFont(FrameUtils.cuteFont(12));
	panel.add(invalidValuesLabel);
	return panel;
    }

    @Override
    protected void onTop() {
	userInterface.getFrame().setTitle("Problem Solving App");
	if(userInterface.isXmlFileWasImportedAtIndex(1)==true) {
	    subSubMainPanel.removeAll();
	    setThisPage();
	    decisionVariableList = userInterface.getDecisionVariablesFromPage();
	    for(DecisionVariablesObject dvo : decisionVariableList) {
		subSubMainPanel.add(dvo.transformIntoAPanel());
	    }
	    if(userInterface.getProblem().getDecisionVariablesSetName()!=null) {
		decisionVariablesSetName.setText(userInterface.getProblem().getDecisionVariablesSetName());
	    }
	}
	userInterface.putXmlFileWasImportedFalseAtIndex(1);
    }

    @Override
    protected boolean areAllDataWellFilled() {
	if (decisionVariableList.isEmpty()) {
	    JOptionPane.showMessageDialog(userInterface.getFrame(),
		    "In order to proceed, you need to declare at least one decision variable.", "Decision variables",
		    JOptionPane.ERROR_MESSAGE);
	    return false;
	}
	boolean answer = true;
	for (DecisionVariablesObject dvo : decisionVariableList) {
	    // separated to run the method
	    boolean var = dvo.isWellFilled();
	    answer &= var;
	}
	return answer;
    }

    @Override
    protected void saveToProblem() {
	userInterface.setDecisionVariablesFromPage(decisionVariableList);
	userInterface.getProblem().setDecisionVariablesSetName(decisionVariablesSetName.getText());
    }

    /**
     * Creates the panel that allows to add new decision variables to the interface
     * @return
     */
    private JPanel addOptionPanel() {
	JPanel addOptionPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	addOptionPanel.setBackground(Color.WHITE);
	JLabel addIcon = new JLabel();
	addIcon.setIcon(new ImageIcon("./src/frames/images/add_icon.png"));
	final DecisionVariablesPage tmp = this;
	addIcon.addMouseListener(new MouseListener() {
	    @Override
	    public void mouseClicked(MouseEvent arg0) {
		EventQueue.invokeLater(new Runnable() {
		    @Override
		    public void run() {
			DecisionVariablesObject decisionVariable = new DecisionVariablesObject(tmp);
			decisionVariableList.add(decisionVariable);
			subSubMainPanel.add(decisionVariable.transformIntoAPanel());
			userInterface.refreshPage();
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
	addOptionPanel.add(addIcon, BorderLayout.SOUTH);
	addOptionPanel.add(new JLabel("Add new variable"));
	return addOptionPanel;
    }

    /**
     * Imports the data of the file selected to the panel
     */
    private void importFromFilePanel() {
	JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	panel.setBackground(Color.WHITE);
	JButton importFromFile = FrameUtils.cuteButton("Import from file");
	importFromFile.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (JOptionPane.showConfirmDialog(userInterface.getFrame(),
			"The imported file must only have one decision variable name per line. "
				+ System.lineSeparator()
				+ "The decision variables name field does not support spaces between characters."
				+ System.lineSeparator()
				+ "Therefore, if spaces are found while reading the document they will be automatically removed.",
				"Import decision variables", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.WARNING_MESSAGE) != JOptionPane.OK_OPTION)
		    return;

		JFileChooser fileChooser = new JFileChooser();
		// Launches the JFileChooser on the Desktop directory
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
		fileChooser.setDialogTitle("Select a file with the variables names");
		// Prevents selection of multiple options
		fileChooser.setMultiSelectionEnabled(false);

		if (fileChooser.showOpenDialog(userInterface.getFrame()) == JFileChooser.APPROVE_OPTION)
		    readImportedFile(fileChooser.getSelectedFile(), askUserForVariableAttributes());
	    }
	});
	panel.add(importFromFile);
	mainPanel.add(panel);
    }

    /**
     * Ask user to input values on variables created
     * 
     * @return
     */
    private String[] askUserForVariableAttributes() {
	JPanel inputPanel = new JPanel(new GridLayout(3, 2));
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
		JOptionPane.showMessageDialog(userInterface.getFrame(),
			!lowerBound.getText().equals("") ? "The lower bound provided was not a valid number."
				: "The lower bound field must be filled in.");
		return askUserForVariableAttributes();
	    }
	    try {
		upper = type.equals("Integer") ? Integer.parseInt(upperBound.getText())
			: Double.parseDouble(upperBound.getText());
	    } catch (NumberFormatException e) {
		JOptionPane.showMessageDialog(userInterface.getFrame(),
			!upperBound.getText().equals("") ? "The upper bound provided was not a valid number."
				: "The upper bound field must be filled in.");
		return askUserForVariableAttributes();
	    }
	    if (upper > lower)
		return new String[] { type, lowerBound.getText(), upperBound.getText() };
	    else {
		JOptionPane.showMessageDialog(userInterface.getFrame(),
			"The upper bound must be bigger then the lower bound");
		return askUserForVariableAttributes();
	    }
	}
	return null;
    }

    /**
     * Reads an imported variable and creates variables with the read values and
     * places them on the frame
     * 
     * @param selectedFile
     * @param values
     */
    private void readImportedFile(File selectedFile, String[] values) {
	if (values == null)
	    return;
	try {
	    Scanner scn = new Scanner(selectedFile);
	    while (scn.hasNextLine()) {
		String line = scn.nextLine().replaceAll(" ", "");
		if (!line.equals("")) {
		    //TODO: Read invalid values from file
		    DecisionVariablesObject decisionVariable = new DecisionVariablesObject(this, line, values[0],
			    values[1], values[2], null);
		    decisionVariableList.add(decisionVariable);
		    subSubMainPanel.add(decisionVariable.transformIntoAPanel());
		}
	    }
	    userInterface.refreshPage();
	    scn.close();
	} catch (FileNotFoundException e) {
	    JOptionPane.showMessageDialog(userInterface.getFrame(),
		    "The file " + selectedFile.getAbsolutePath() + " doesn't exists");
	}
    }

    /**
     * Removes a variable from the list of decision variables and 
     * updates the interface
     * @param dvo
     */
    public void removeDecisionVariableFromList(DecisionVariablesObject dvo) {
	decisionVariableList.remove(dvo);
	subSubMainPanel.remove(dvo.transformIntoAPanel());
	userInterface.refreshPage();
    }

    /**
     * @param varName
     *            the {@code String} to compare to the other variable names in
     *            the {@linkplain #decisionVariableList}
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

    private void setThisPage() {
	for(DecisionVariablesObject dvo : userInterface.getDecisionVariablesFromPage()) {
	    dvo.setPageAssociated(this);
	}
    }

    @Override
    protected void clearDataFromPage() {
	decisionVariableList.clear();
	decisionVariablesSetName.setText(null);
	mainPanel.removeAll();
	subSubMainPanel.removeAll();
	createMainPanel();
    }

}