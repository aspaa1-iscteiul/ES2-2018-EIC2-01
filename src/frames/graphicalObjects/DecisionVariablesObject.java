package frames.graphicalObjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import frames.DecisionVariablesPage;
import frames.frameUtils.FrameUtils;
import objects.DataType;

/**
 * This object was created to aid the construction of the Decision Variables
 * Page and later to convert to the object Decision Variable
 */

public class DecisionVariablesObject {

    private DecisionVariablesPage pageAssociated;
    private JPanel variablesPanel;
    private JTextField varName;
    private String dataType;
    private JTextField lowerBound;
    private JTextField upperBound;
    private JTextField invalidValues;

    private JLabel deleteIcon;

    /**
     * This object receives a page that contains the frame and has a name, a
     * dataType, a lower and upper bound and invalid solutions.
     * @param page
     * @param variableName
     * @param variableDataType
     * @param lowerLimit
     * @param upperLimit
     * @param values
     */
    public DecisionVariablesObject(final DecisionVariablesPage page, String variableName, String variableDataType,
	    String lowerLimit, String upperLimit, String[] values) {
	this.pageAssociated = page;
	variablesPanel = new JPanel();
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
	if(variableDataType != null) {
	    if(variableDataType.equals("INTEGER")) {
		dataType = "Integer";
	    } else if(variableDataType.equals("DOUBLE")) {
		dataType = "Double";
	    } else {
		dataType = "Binary";
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
    }

    /**
     * 
     * @param page
     */
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

	deleteIcon.addMouseListener(new MouseListener() {
	    @Override
	    public void mouseClicked(MouseEvent arg0) {
		pageAssociated.removeDecisionVariableFromList(DecisionVariablesObject.this);
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


	variablesPanel.add(varName);
	variablesPanel.add(deleteIcon);
	return variablesPanel;
    }

    /**
     * Returns {@code true} if {@linkplain DecisionVariablesObject} have a valid
     * name otherwise {@code false}
     * 
     * @return {@code true} if {@linkplain DecisionVariablesObject} have a valid
     *         name and otherwise {@code false}
     * @see #isValidName()
     */
    public boolean isWellFilled() {
	// methods separated to run all
	boolean isValidName = isValidName();
	return isValidName;
    }

    /**
     * @return {@code true} if the {@link #varName} is not empty and is not
     *         repeated, otherwise {@code false} and evidence the error
     * @see DecisionVariablesPage#isNameRepeated(String)
     * @see FrameUtils#errorFormat(JComponent, String)
     */
    private boolean isValidName() {
	String text = varName.getText();
	String info = (text.equals("") ? "The variable's name field is a mandatory entry field and therefore must be filled in. " : "")
		+ (pageAssociated.isNameRepeated(text) ? "The variable's name must be unique." : "");
	return !info.equals("") ? FrameUtils.errorFormat(varName, info) : FrameUtils.normalFormat(varName);
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
	return dataType;
    }

    /**
     * Transforms the dataType selected to an enumerate
     * 
     * @return
     */
    public DataType getDataTypeToProblem() {
	if( dataType.equals("Integer")) {
	    return DataType.INTEGER;
	} else if( dataType.equals("Double")) {
	    return DataType.DOUBLE;
	} else {
	    return DataType.BINARY;
	}
    }

    public String getLowerBound() {
	return lowerBound.getText();
    }

    public String getUpperBound() {
	return upperBound.getText();
    }

    public void setVariableDataType(String variableDataType) {
	dataType = variableDataType;
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

    public void setInvalidValues(String text) {
	invalidValues.setText(text);
    }


}