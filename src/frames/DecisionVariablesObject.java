package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import objects.DataType;

/**
 * This object was created to aid the construction of the Decision Variables
 * Page and later to convert to the object Decision Variable
 */

public class DecisionVariablesObject {

    private DecisionVariablesPage pageAssociated;
    private JPanel variablesPanel;
    private JTextField varName;
    private final static String[] dataTypes = { "Integer", "Double" };
    private JComboBox<String> dataType;
    private JTextField lowerBound;
    private JTextField upperBound;
    private JTextField invalidValues;
    private JLabel deleteIcon;

    /**
     * This object receives a page that contains the frame and has a name, a
     * dataType, a lower and upper bound.
     * 
     * @param page
     */

    public DecisionVariablesObject(final DecisionVariablesPage page, String variableName, String variableDataType,
	    String lowerLimit, String upperLimit, String[] values) {
	this.pageAssociated = page;
	variablesPanel = new JPanel();
	final DecisionVariablesObject tmp = this;
	varName = new JTextField(variableName, 6);
	varName.addKeyListener(new KeyListener() {
	    @Override
	    public void keyTyped(KeyEvent e) {
	    }

	    @Override
	    public void keyReleased(KeyEvent e) {
		varName.setText(varName.getText().trim()); // remove spaces
	    }

	    @Override
	    public void keyPressed(KeyEvent e) {
	    }
	});
	dataType = FrameUtils.cuteComboBox(dataTypes);
	if(variableDataType != null) {
	    if(variableDataType.equals("INTEGER")) {
		dataType.setSelectedIndex(0);
	    } else {
		dataType.setSelectedIndex(1);
	    }
	}
	lowerBound = new JTextField(lowerLimit, 6);
	upperBound = new JTextField(upperLimit, 6);
	if(values != null) {
	    String tmp1 =  StringUtils.remove(Arrays.toString(values), '[');
	    String tmp2 = StringUtils.remove(tmp1, ']');
	    invalidValues = new JTextField(tmp2,6);
	} else {
	    invalidValues = new JTextField(6);
	}
	deleteIcon = new JLabel();
	deleteIcon.setIcon(new ImageIcon("./src/frames/images/delete_icon2.png"));
	deleteIcon.addMouseListener(new MouseListener() {
	    @Override
	    public void mouseClicked(MouseEvent arg0) {
		EventQueue.invokeLater(new Runnable() {
		    public void run() {
			page.removeDecisionVariableFromList(tmp);
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
    }

    public DecisionVariablesObject(final DecisionVariablesPage page) {
	this(page, null, null, null, null, null);
    }

    /**
     * Transforms the object in a JPanel that will be added to the frame later.
     * 
     * @return JPanel
     */
    public JPanel transformIntoAPanel() {
	variablesPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	variablesPanel.setBackground(Color.WHITE);
	varName.setBorder(FrameUtils.cuteBorder());
	varName.setPreferredSize(new Dimension(10, 22));
	lowerBound.setBorder(FrameUtils.cuteBorder());
	lowerBound.setPreferredSize(new Dimension(10, 22));
	upperBound.setBorder(FrameUtils.cuteBorder());
	upperBound.setPreferredSize(new Dimension(10, 22));
	invalidValues.setBorder(FrameUtils.cuteBorder());
	invalidValues.setPreferredSize(new Dimension(10, 22));
	variablesPanel.add(varName);
	variablesPanel.add(dataType);
	variablesPanel.add(lowerBound);
	variablesPanel.add(upperBound);
	variablesPanel.add(invalidValues);
	variablesPanel.add(deleteIcon);
	return variablesPanel;
    }

    public DecisionVariablesPage getPageAssociated() {
	return pageAssociated;
    }

    public void setPageAssociated(DecisionVariablesPage pageAssociated) {
	this.pageAssociated = pageAssociated;
    }

    public void setVariableName(String name) {
	this.varName.setText(name);
    }

    public String getVariableName() {
	return varName.getText();
    }

    public String getDataType() {
	return dataType.getSelectedItem().toString();
    }

    public DataType getDataTypeToProblem() {
	return dataType.getSelectedItem().toString().equals("Integer") ? DataType.INTEGER : DataType.DOUBLE;
    }

    public String getLowerBound() {
	return lowerBound.getText();
    }

    public String getUpperBound() {
	return upperBound.getText();
    }

    public void setVariableDataType(String type) {
	dataType.setSelectedItem(type);
    }

    public void setLowerBound(String lower) {
	lowerBound.setText(lower);
    }

    public void setUpperBound(String upper) {
	upperBound.setText(upper);
    }

    /**
     * Get invalid values of the variable on a vector of strings
     * @return
     */
    public String[] getInvalidValues(){
	if(!invalidValues.getText().trim().isEmpty()) {
	    String[] v = invalidValues.getText().split(",");
	    return v;
	}
	return null;
    }

    /**
     * Returns {@code true} if {@linkplain DecisionVariablesObject} have a valid
     * name and the {@linkplain #dataType} is selected and have a valid bound and
     * valid inputs in the invalid values
     * otherwise {@code false}
     * 
     * @return {@code true} if {@linkplain DecisionVariablesObject} have a valid
     *         name and the {@linkplain #dataType} is selected and have a valid
     *         bound, otherwise {@code false}
     * @see #isValidName()
     * @see #isDataTypeSelected()
     * @see #isValidBound()
     * @see #isValidValues()
     */
    public boolean isWellFilled() {
	// methods separated to run all
	boolean isValidName = isValidName(), isDataTypeSelected = isDataTypeSelected(), isValidBound = isValidBound(), isValidValues = isValidValues();
	return isValidName && isDataTypeSelected && isValidBound && isValidValues;
    }

    /**
     * @return {@code true} if the {@link #varName} is not empty and is not
     *         repeated, otherwise {@code false} and evidence the error
     * @see DecisionVariablesPage#isNameRepeated(String)
     * @see FrameUtils#errorFormat(JComponent, String)
     */
    private boolean isValidName() {
	String text = varName.getText();
	String info = (text.equals("") ? "The variable's name field is mandatory and must be filled in. " : "")
		+ (pageAssociated.isNameRepeated(text) ? "The variable's name must be unique." : "");
	return !info.equals("") ? FrameUtils.errorFormat(varName, info) : FrameUtils.normalFormat(varName);
    }

    /**
     * @return {@code true} if the {@link #dataType} has an item selected,
     *         otherwise {@code false} and evidence the error
     * @see FrameUtils#errorFormat(JComponent, String)
     */
    private boolean isDataTypeSelected() {
	return dataType.getSelectedItem() == null
		? FrameUtils.errorFormat(dataType,
			"The decision variable's data type field is mandatory and must be filled in.")
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
		    "The " + boundStr + " bound field is mandatory and must be filled in.");

	try {
	    if (dataType.getSelectedItem().toString().equals("Integer"))
		Integer.parseInt(bound.getText());
	    else
		Double.parseDouble(bound.getText());
	} catch (NumberFormatException e) {
	    return FrameUtils.errorFormat(bound, "The " + boundStr + " bound provided is not a valid number.");
	} catch (NullPointerException e) {
	    return FrameUtils.errorFormat(bound,
		    "The decision variable's data type field is mandatory and must be filled in.");
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
		    FrameUtils.errorFormat(invalidValues, "The invalid values must in agreement with the data type selected");
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
	    FrameUtils.errorFormat(invalidValues, "The invalid values must contain only numbers or ','");
	    return false;
	}
	return tmp;
    }

}