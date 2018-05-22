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
import java.util.regex.Pattern;

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
    private final static String[] dataTypes = { "Integer", "Double", "Binary" };
    private JComboBox<String> dataType;
    private JTextField lowerBound;
    private JTextField upperBound;
    private JTextField invalidValues;
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

	JPanel introPanel = new JPanel(new BorderLayout());
	introPanel.setBackground(Color.WHITE);
	introPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
		BorderFactory.createEmptyBorder(10, 10, 10, 10))); 

	JPanel decisionVariablesSetPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	decisionVariablesSetPanel.setBackground(Color.WHITE);
	JLabel setNameLabel = new JLabel("Decision variables' set name: ");
	setNameLabel.setFont(FrameUtils.cuteFont(12));
	decisionVariablesSetName = new JTextField(5);
	decisionVariablesSetName.setBorder(FrameUtils.cuteBorder());
	decisionVariablesSetPanel.add(setNameLabel);
	decisionVariablesSetPanel.add(decisionVariablesSetName);

	introPanel.add(decisionVariablesSetPanel, BorderLayout.NORTH);

	introPanel.add(labelsPanel2(), BorderLayout.CENTER);
	introPanel.add(valuesPanel(), BorderLayout.SOUTH);

	mainPanel.add(introPanel);

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
	scrollPane.setPreferredSize(new Dimension(510, 170));
	mainPanel.add(scrollPane);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	importFromFilePanel();
	
	FrameUtils.addEmptyLabels(buttonsPanel, 1);
    }

    /**
     * Creates the panel with the labels that display information 
     * about the decision variables attributes
     * @return
     */
    private JPanel labelsPanel() {
	JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	panel.setBackground(Color.WHITE);
	FrameUtils.addEmptyLabels(panel, 1);
	JLabel name = new JLabel("<html><font color=red><b> *</b></font> Name</html>");
	// to add space between the labels
	name.setBorder(new EmptyBorder(0, 0, 0, 52));
	name.setFont(FrameUtils.cuteFont(12));
	panel.add(name);
	FrameUtils.addEmptyLabels(panel, 1);
	return panel;
    }

    /**
     * Creates the panel with the labels that display information 
     * about the decision variables attributes
     * @return
     */
    private JPanel labelsPanel2() {
	JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	panel.setBackground(Color.WHITE);
	JLabel dataType = new JLabel("<html><font color=red><b>*</b></font> Data Type");
	dataType.setBorder(new EmptyBorder(0, 0, 0, 15));
	dataType.setFont(FrameUtils.cuteFont(12));
	panel.add(dataType);
	FrameUtils.addEmptyLabels(panel, 1);
	JLabel lowerLabel = new JLabel("<html><font color=red><b>*</b></font> Lower Bound     ");
	lowerLabel.setFont(FrameUtils.cuteFont(12));
	panel.add(lowerLabel);
	FrameUtils.addEmptyLabels(panel, 3);
	JLabel upperLabel = new JLabel("<html><font color=red><b>*</b></font> Upper Bound");
	upperLabel.setBorder(new EmptyBorder(0, 0, 0, 16));
	upperLabel.setFont(FrameUtils.cuteFont(12));
	panel.add(upperLabel);
	FrameUtils.addEmptyLabels(panel, 2);
	JLabel invalidValuesLabel = new JLabel("Invalid values");
	invalidValuesLabel.setFont(FrameUtils.cuteFont(12));
	panel.add(invalidValuesLabel);
	return panel;
    }

    /**
     * Creates the panel with the inputs of the decision variables attributes
     * @return
     */
    private JPanel valuesPanel() {
	JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	panel.setBackground(Color.WHITE);
	dataType = FrameUtils.cuteComboBox(dataTypes);
	lowerBound = new JTextField(6);
	upperBound = new JTextField(6);
	invalidValues = new JTextField(6);
	lowerBound.setBorder(FrameUtils.cuteBorder());
	lowerBound.setPreferredSize(new Dimension(8, 22));
	upperBound.setBorder(FrameUtils.cuteBorder());
	upperBound.setPreferredSize(new Dimension(8, 22));
	invalidValues.setBorder(FrameUtils.cuteBorder());
	invalidValues.setPreferredSize(new Dimension(10, 22));
	FrameUtils.addEmptyLabels(panel, 1);
	panel.add(dataType);
	FrameUtils.addEmptyLabels(panel, 2);
	panel.add(lowerBound);
	FrameUtils.addEmptyLabels(panel, 2);
	panel.add(upperBound);
	FrameUtils.addEmptyLabels(panel, 2);
	panel.add(invalidValues);
	return panel;
    }

    @Override
    protected void onTop() {
	userInterface.getFrame().setTitle("Problem Solving App");
	if(userInterface.isXmlFileWasImportedAtIndex(1)==true) {
	    subSubMainPanel.removeAll();
	    decisionVariableList = userInterface.getDecisionVariablesFromPage();
	    if(userInterface.getProblem().getDecisionVariablesDataType() != null) {
		if(userInterface.getProblem().getDecisionVariablesDataType().name().equals("INTEGER")) {
		    dataType.setSelectedIndex(0);
		} else if(userInterface.getProblem().getDecisionVariablesDataType().name().equals("DOUBLE")) {
		    dataType.setSelectedIndex(1);
		} else {
		    dataType.setSelectedIndex(2);
		}
	    }
	    if(userInterface.getProblem().getDecisionVariablesLowerBound() != null) {
		lowerBound.setText(userInterface.getProblem().getDecisionVariablesLowerBound());
	    }
	    if(userInterface.getProblem().getDecisionVariablesUpperBound() != null) {
		upperBound.setText(userInterface.getProblem().getDecisionVariablesUpperBound());
	    }
	    if(!userInterface.getProblem().getDecisionVariablesInvalidValues().equals("[null]")) {
		invalidValues.setText(userInterface.getProblem().getDecisionVariablesInvalidValues());
	    }
	    for(DecisionVariablesObject dvo : decisionVariableList) {
		subSubMainPanel.add(dvo.transformIntoAPanel());
		dvo.setPageAssociated(this);
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
	answer &= isWellFilled();
	return answer;
    }

    @Override
    protected void saveToProblem() {
	for(DecisionVariablesObject dvo : decisionVariableList) {
	    dvo.setLowerBound(lowerBound.getText());
	    dvo.setUpperBound(upperBound.getText());
	    dvo.setVariableDataType(dataType.getSelectedItem().toString());
	    dvo.setInvalidValues(invalidValues.getText());
	}
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
	buttonsPanel.add(importFromFile);
	FrameUtils.addEmptyLabels(buttonsPanel, 25);
    }

    /**
     * Ask user to input values on variables created
     * 
     * @return
     */
    private String[] askUserForVariableAttributes() {
	JPanel inputPanel = new JPanel(new GridLayout(3, 2));
	inputPanel.add(new JLabel("<html><font color=red><b>*</b></font> Data Type: "));
	JComboBox<String> dataType = new JComboBox<String>(new String[] { "Integer", "Double" });
	inputPanel.add(dataType);
	inputPanel.add(new JLabel("<html><font color=red><b>*</b></font> Lower Bound: "));
	JTextField lowerBound = new JTextField();
	inputPanel.add(lowerBound);
	inputPanel.add(new JLabel("<html><font color=red><b>*</b></font> Upper Bound: "));
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
		    DecisionVariablesObject decisionVariable = new DecisionVariablesObject(this, line, values[0], values[1], values[2], null);
		    lowerBound.setText(values[0]);
		    upperBound.setText(values[1]);
		    invalidValues.setText(values[2]);
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
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		decisionVariableList.remove(dvo);
		subSubMainPanel.remove(dvo.transformIntoAPanel());
		userInterface.refreshPage();
	    }
	});
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

    @Override
    protected void clearDataFromPage() {
	decisionVariableList.clear();
	decisionVariablesSetName.setText(null);
	mainPanel.removeAll();
	subSubMainPanel.removeAll();
	createMainPanel();
    }

    /**
     * Get invalid values of the variable on a vector of strings
     * @return
     */
    public String[] getInvalidValuesOnVector(){
	if(!invalidValues.getText().trim().isEmpty()) {
	    String[] v = invalidValues.getText().split(",");
	    return v;
	}
	return null;
    }

    /**
     * Returns {@code true} if {@linkplain DecisionVariablesObject} the {@linkplain #dataType} 
     * is selected and have a valid bound and
     * valid inputs in the invalid values
     * otherwise {@code false}
     * 
     * @return {@code true} if {@linkplain DecisionVariablesObject} the {@linkplain #dataType} is 
     * selected and have a valid bound, otherwise {@code false}
     * @see #isDataTypeSelected()
     * @see #isValidBound()
     * @see #isValidValues()
     */
    public boolean isWellFilled() {
	// methods separated to run all
	boolean isDataTypeSelected = isDataTypeSelected(), isValidBound = isValidBound(), isValidValues = isValidValues();
	return isDataTypeSelected && isValidBound && isValidValues;
    }

    /**
     * @return {@code true} if the {@link #dataType} has an item selected,
     *         otherwise {@code false} and evidence the error
     * @see FrameUtils#errorFormat(JComponent, String)
     */
    private boolean isDataTypeSelected() {
	return dataType.getSelectedItem() == null
		? FrameUtils.errorFormat(dataType,
			"The decision variable's data type field is a mandatory entry field and therefore must be filled in.")
			: FrameUtils.normalFormat(dataType);

    }

    /**
     * @param lower
     *            {@code true} if it is to validate the number in
     *            {@linkplain #lowerBound}, otherwise it is to validate the
     *            number in {@linkplain #upperBound}
     * @return {@code true} if the {@code JTextField}, indicated by
     *         <b>lower</b>, contains a valid number, otherwise {@code false}
     *         and evidence the error
     * @see FrameUtils#errorFormat(JComponent, String)
     */
    private boolean isValidNumber(boolean lower) {
	JTextField bound = lower ? lowerBound : upperBound;
	String boundStr = lower ? "lower" : "upper";
	if (bound.getText().equals(""))
	    return FrameUtils.errorFormat(bound,
		    "The " + boundStr + " bound field is a mandatory entry field and therefore must be filled in.");
	if (dataType.getSelectedItem().toString().equals("Binary")) {
	    if(bound.getText().matches("[01]+")){
		return FrameUtils.normalFormat(bound);
	    } else {
		return FrameUtils.errorFormat(bound, "The " + boundStr + " bound provided is not a valid number.");
	    }
	}
	try {
	    if (dataType.getSelectedItem().toString().equals("Integer")) {
		Integer.parseInt(bound.getText());
	    } else if(dataType.getSelectedItem().toString().equals("Double")) {
		Double.parseDouble(bound.getText());
	    } 
	} catch (NumberFormatException e) {
	    return FrameUtils.errorFormat(bound, "The " + boundStr + " bound provided is not a valid number.");
	} catch (NullPointerException e) {
	    return FrameUtils.errorFormat(bound,
		    "The decision variable's data type field is a mandatory entry field and therefore must be filled in.");
	}
	return FrameUtils.normalFormat(bound);
    }

    /**
     * @return {@code true} if {@linkplain #lowerBound} and
     *         {@linkplain #upperBound} have valid numbers and if the lower
     *         limit is less than the upper limit, otherwise {@code false} and
     *         evidence the error
     * @see #isValidNumber(boolean)
     * @see FrameUtils#errorFormat(JComponent, String)
     */
    private boolean isValidBound() {
	// separated to run all methods
	boolean lowerBoundValid = isValidNumber(true), upperBoundValid = isValidNumber(false);
	if (lowerBoundValid && upperBoundValid) {
	    boolean isInteger = dataType.getSelectedItem().toString().equals("Integer");
	    double lower = isInteger ? Integer.parseInt(lowerBound.getText())
		    : Double.parseDouble(lowerBound.getText());
	    double upper = isInteger ? Integer.parseInt(upperBound.getText())
		    : Double.parseDouble(upperBound.getText());
	    return upper <= lower
		    ? FrameUtils.errorFormat(upperBound, "The upper bound must be bigger than the lower bound")
			    : FrameUtils.normalFormat(upperBound);
	}
	return false;
    }

    /**
     * @return {@code true} if {@linkplain #invalidValues}
     *         have valid numbers separated by ',' or is empty,
     *          otherwise {@code false} and evidence the error
     * @see FrameUtils#errorFormat(JComponent, String)
     */
    private boolean isValidValues() {
	boolean tmp = true;
	if(invalidValues.getText().isEmpty()) {
	    FrameUtils.normalFormat(invalidValues);
	} else if(Pattern.matches("[.,0-9]+", invalidValues.getText())) {
	    FrameUtils.normalFormat(invalidValues);
	    if(dataType.getSelectedItem().equals("Integer")) {
		String[] v = invalidValues.getText().split(",");
		try {
		    for(int i = 0; i != v.length; i++) {
			Integer.parseInt(v[i]);
		    }
		    FrameUtils.normalFormat(invalidValues);
		}catch(NumberFormatException e) {
		    FrameUtils.errorFormat(invalidValues, "The invalid values provided must be in agreement with the data type selected.");
		    return false;
		}
	    }else if(dataType.getSelectedItem().equals("Double")) {
		String[] v = invalidValues.getText().split(",");
		try {
		    for(int i = 0; i != v.length; i++) {
			Double.parseDouble(v[i]);
		    }
		    FrameUtils.normalFormat(invalidValues);
		}catch(NumberFormatException e) {
		    FrameUtils.errorFormat(invalidValues, "The invalid values must in agreement with the data type selected");
		    return false;
		}
	    }
	}else {
	    FrameUtils.errorFormat(invalidValues, "The invalid values provided can only contain numbers and/or commas.");
	    return false;
	}
	return tmp;
    }

    public JTextField getLowerBound() {
	return lowerBound;
    }


    public JTextField getUpperBound() {
	return upperBound;
    }

    public JTextField getInvalidValues() {
	return invalidValues;
    }


}