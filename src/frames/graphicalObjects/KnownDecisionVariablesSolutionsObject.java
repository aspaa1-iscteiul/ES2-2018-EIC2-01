package frames.graphicalObjects;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import frames.KnownDecisionVariablesSolutionsPage;
import frames.frameUtils.FrameUtils;

/**
 * This object was created to aid the construction of the Known Solutions Page
 */

public class KnownDecisionVariablesSolutionsObject {

    private KnownDecisionVariablesSolutionsPage pageAssociated;
    private JTextField name;
    private ArrayList<JTextField> solutionsList;
    private JLabel addIcon;
    private JLabel newSolutionInfo;
    private String dataType;
    private String lowerBound;
    private String upperBound;
    private String[] invalidValues;

    /**
     * This object will have the solutions to a given variable that must agree
     * with the lower and upper bounds presented by the variable
     * 
     * @param page
     * @param name
     * @param type
     * @param lowerBound
     * @param upperBound
     * @param invalidValues
     */
    public KnownDecisionVariablesSolutionsObject(KnownDecisionVariablesSolutionsPage page, String name, String type, String lowerBound,
	    String upperBound, String[] invalidValues) {
	this.pageAssociated = page;
	this.name = new JTextField(name.length());
	this.name.setText(name);
	this.dataType = type;
	this.lowerBound = lowerBound;
	this.upperBound = upperBound;
	this.setInvalidValues(invalidValues);
	JTextField solution1 = new JTextField(2);
	JTextField solution2 = new JTextField(2);
	this.solutionsList = new ArrayList<JTextField>();
	this.solutionsList.add(solution1);
	this.solutionsList.add(solution2);
	this.addIcon = new JLabel();
	this.newSolutionInfo = new JLabel("Add new solutions");
    }

    /**
     * This object will have the solutions to a given variable that must agree
     * with the lower and upper bounds presented by the variable and it's created with the data
     * read from a XML file
     * @param page
     * @param name
     * @param variableDataType
     * @param lowerBound
     * @param upperBound
     * @param invalidValues
     * @param solutions
     */
    public KnownDecisionVariablesSolutionsObject(KnownDecisionVariablesSolutionsPage page, String name, String variableDataType, String lowerBound,
	    String upperBound, String[] invalidValues, ArrayList<String> solutions) {
	this.pageAssociated = page;
	this.name = new JTextField(name, name.length());

	if(variableDataType != null) {
	    if(variableDataType.equals("INTEGER")) {
		dataType = "Integer";
	    } else if(variableDataType.equals("DOUBLE")) {
		dataType = "Double";
	    } else {
		dataType = "Binary";
	    }
	}
	this.lowerBound = lowerBound;
	this.upperBound = upperBound;
	this.setInvalidValues(invalidValues);
	this.setTextfieldList(getSolutionListInTextField(solutions));
	this.addIcon = new JLabel();
	this.newSolutionInfo = new JLabel("Add new solutions");
    }

    /**
     * Transforms the object in a JPanel that will be added to the frame later.
     * 
     * @return JPanel
     */
    public JPanel transformIntoAPanel() {
	JPanel overallPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	overallPanel.setBackground(Color.WHITE);

	final JPanel firstPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	firstPanel.setBackground(Color.WHITE);
	name.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
		BorderFactory.createEmptyBorder(0, 10, 0, 10)));
	name.setEditable(false);
	firstPanel.add(name);

	for (final JTextField textField : solutionsList) {
	    textField.setBorder(FrameUtils.cuteBorder());
	    firstPanel.add(textField);
	}

	overallPanel.add(firstPanel);

	JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	addPanel.setBackground(Color.WHITE);
	addIcon.setIcon(new ImageIcon(getClass().getResource("/add_icon.png")));
	addIcon.addMouseListener(new MouseListener() {
	    @Override
	    public void mouseClicked(MouseEvent arg0) {
		EventQueue.invokeLater(new Runnable() {
		    public void run() {
			final JTextField newSolution = new JTextField(3);
			newSolution.setBorder(FrameUtils.cuteBorder());
			firstPanel.add(newSolution);
			solutionsList.add(newSolution);
			pageAssociated.userInterface.refreshPage();
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
	addPanel.add(addIcon);
	addPanel.add(newSolutionInfo);
	overallPanel.add(addPanel);
	return overallPanel;
    }

    /**
     * Validates the input of a given solution, validating if it agrees with the
     * bounds and data type previously chosen on the variable associated to the
     * solution and if it agrees with the invalid values chosen
     * 
     * @param textField
     */
    public boolean validateInputs(JTextField textField) {
	if (textField.getText().isEmpty()) {
	    FrameUtils.normalFormat(textField);
	    return true;
	} else {
	    boolean tmp = false;
	    if (dataType.equals("Integer")) {
		try {
		    Integer.parseInt(textField.getText());
		    tmp = FrameUtils.normalFormat(textField);
		    if (!lowerBound.isEmpty() && !upperBound.isEmpty()) {
			if (Integer.parseInt(textField.getText()) > Integer.parseInt(upperBound)
				|| Integer.parseInt(textField.getText()) < Integer.parseInt(lowerBound)) {
			    tmp = FrameUtils.errorFormat(textField,
				    "Solutions must agree with the variable's lower and upper bound");
			    return tmp;
			} else {
			    tmp = FrameUtils.normalFormat(textField);
			}
		    }
		    if(invalidValues != null) {
			for(int i = 0; i!= invalidValues.length; i++) {
			    if(invalidValues[i].equals(textField.getText())) {
				tmp = FrameUtils.errorFormat(textField,
					"Solutions must agree with the invalid values selected");
				return tmp;
			    } else {
				tmp = FrameUtils.normalFormat(textField);
			    }
			}
		    }
		} catch (NumberFormatException e1) {
		    tmp = FrameUtils.errorFormat(textField, "Solutions must have the same data type has the variable");
		    return tmp;
		}
	    }
	    if (dataType.equals("Double")) {
		try {
		    Double.parseDouble(textField.getText());
		    tmp = FrameUtils.normalFormat(textField);
		    if (!lowerBound.isEmpty() && !upperBound.isEmpty()) {
			if (Double.parseDouble(textField.getText()) > Double.parseDouble(upperBound)
				|| Double.parseDouble(textField.getText()) < Double.parseDouble(lowerBound)) {
			    tmp = FrameUtils.errorFormat(textField,
				    "Solutions must agree with the variable's lower and upper bound");
			    return tmp;
			} else {
			    tmp = FrameUtils.normalFormat(textField);
			}
		    }
		    if(invalidValues != null) {
			for(int i = 0; i!= invalidValues.length; i++) {
			    if(invalidValues[i].equals(textField.getText())) {
				tmp = FrameUtils.errorFormat(textField,
					"Solutions must agree with the invalid values selected");
				return tmp;
			    } else {
				tmp = FrameUtils.normalFormat(textField);
			    }
			}
		    }
		} catch (NumberFormatException e1) {
		    tmp = FrameUtils.errorFormat(textField, "Solutions must have the same data type has the variable");
		    return tmp;
		}
	    }
	    return tmp;
	}
    }

    /**
     * Transforms the solutions textfield into strings
     * @return
     */
    private ArrayList<String> getSolutionListInString() {
	ArrayList<String> tmp = new ArrayList<String>();
	for(JTextField text : solutionsList) {
	    tmp.add(text.getText());
	}
	return tmp;
    }

    /**
     * Transforms the strings into textfields
     * @return
     */
    private ArrayList<JTextField> getSolutionListInTextField(ArrayList<String> strings) {
	ArrayList<JTextField> tmp = new ArrayList<JTextField>();
	if(strings != null) {
	    for(String str : strings) {
		tmp.add(new JTextField(str));
	    }
	}
	return tmp;
    }

    @Override
    public String toString() {
	return "KnownSolutionsObject [name=" + name.getText() + ", solutionsList=" + getSolutionListInString().toString() 
		+ ", dataType=" + dataType + ", lowerBound=" + lowerBound
		+ ", upperBound=" + upperBound + ", invalidValues=" + Arrays.toString(invalidValues) + "]";
    }

    public JTextField getName() {
	return name;
    }

    public void setName(JTextField name) {
	this.name = name;
    }
    
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(String lowerBound) {
        this.lowerBound = lowerBound;
    }

    public String getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(String upperBound) {
        this.upperBound = upperBound;
    }

    public KnownDecisionVariablesSolutionsPage getPage() {
	return pageAssociated;
    }

    public void setPage(KnownDecisionVariablesSolutionsPage page) {
	this.pageAssociated = page;
    }

    public ArrayList<JTextField> getTextfieldList() {
	return solutionsList;
    }

    public void setTextfieldList(ArrayList<JTextField> textfieldList) {
	this.solutionsList = textfieldList;
    }

    public String[] getInvalidValues() {
	return invalidValues;
    }

    public void setInvalidValues(String[] invalidValues) {
	this.invalidValues = invalidValues;
    }

    /**
     * Organizes the known solutions list and removes null data
     */
    public void organizeSolutionsList() {
	ArrayList<JTextField> solutionsListAux = new ArrayList<JTextField>();
	for(JTextField text : solutionsList) {
	    if(!text.getText().toString().isEmpty()) {
		solutionsListAux.add(text);
	    }
	}
	solutionsList = solutionsListAux;
    }

}