package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import objects.DataType;

/**
 * This object was created to aid the construction of the Decision Variables
 * Page and later to convert to the object Decision Variable
 */

public class DecisionVariablesObject {

    private DecisionVariablesPage page;
    private JPanel variablesPanel;
    private JTextField name;
    private final static String[] dataTypes = { "Integer", "Double" };
    private JComboBox<String> dataType;
    private JTextField lowerBound;
    private JTextField upperBound;
    private JLabel deleteIcon;

    /**
     * This object receives a page that contains the frame and has a name, a
     * dataType, a lower and upper bound.
     * 
     * @param page
     */

    public DecisionVariablesObject(final DecisionVariablesPage page, String variableName, String variableDataType,
	    String lowerLimit, String upperLimit) {
	this.page = page;
	variablesPanel = new JPanel();
	final DecisionVariablesObject tmp = this;
	name = new JTextField(variableName, 5);
	name.addKeyListener(new KeyListener() {
	    @Override
	    public void keyTyped(KeyEvent e) {
	    }

	    @Override
	    public void keyReleased(KeyEvent e) {
		name.setText(name.getText().trim()); // remove spaces
	    }

	    @Override
	    public void keyPressed(KeyEvent e) {
	    }
	});
	dataType = FrameUtils.cuteComboBox(dataTypes);
	dataType.setSelectedItem(variableDataType);
	lowerBound = new JTextField(lowerLimit, 5);
	upperBound = new JTextField(upperLimit, 5);
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
	this(page, null, null, null, null);
    }

    /**
     * Transforms the object in a JPanel that will be added to the frame later.
     * 
     * @return
     */
    public JPanel transformIntoAPanel() {
	variablesPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	variablesPanel.setBackground(Color.WHITE);
	name.setBorder(FrameUtils.cuteBorder());
	name.setPreferredSize(new Dimension(10, 22));
	lowerBound.setBorder(FrameUtils.cuteBorder());
	lowerBound.setPreferredSize(new Dimension(10, 22));
	upperBound.setBorder(FrameUtils.cuteBorder());
	upperBound.setPreferredSize(new Dimension(10, 22));
	variablesPanel.add(name);
	variablesPanel.add(dataType);
	variablesPanel.add(lowerBound);
	variablesPanel.add(upperBound);
	variablesPanel.add(deleteIcon);
	return variablesPanel;
    }

    public void setVariableName(String name) {
	this.name.setText(name);
    }

    public String getVariableName() {
	return name.getText();
    }

    public String getDataType() {
	return dataType.getSelectedItem().toString();
    }

    public DataType getDataTypeToProblem() {
	if (dataType.getSelectedItem().toString().equals("Integer")) {
	    return DataType.INTEGER;
	} else {
	    return DataType.DOUBLE;
	}
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
     * Returns {@code true} if {@linkplain DecisionVariablesObject} have a valid
     * name and the {@linkplain #dataType} is selected and have a valid bound,
     * otherwise {@code false}
     * 
     * @return {@code true} if {@linkplain DecisionVariablesObject} have a valid
     *         name and the {@linkplain #dataType} is selected and have a valid
     *         bound, otherwise {@code false}
     * @see #isValidName()
     * @see #isDataTypeSelected()
     * @see #isValidBound()
     */
    public boolean isWellFilled() {
	// methods separated to run all
	boolean isValidName = isValidName(), isDataTypeSelected = isDataTypeSelected(), isValidBound = isValidBound();
	return isValidName && isDataTypeSelected && isValidBound;
    }

    /**
     * @return {@code true} if the {@link #name} is not empty and is not repeated,
     *         otherwise {@code false} and evidence the error
     * @see DecisionVariablesPage#isNameRepeated(String)
     * @see FrameUtils#errorFormat(JComponent, String)
     */
    private boolean isValidName() {
	String text = name.getText();
	String info = (text.equals("") ? "The variable name must be filled in. " : "")
		+ (page.isNameRepeated(text) ? "The variable name must be unique." : "");
	return !info.equals("") ? FrameUtils.errorFormat(name, info) : FrameUtils.normalFormat(name);
    }

    /**
     * @return {@code true} if the {@link #dataType} has an item selected, otherwise
     *         {@code false} and evidence the error
     * @see FrameUtils#errorFormat(JComponent, String)
     */
    private boolean isDataTypeSelected() {
	return dataType.getSelectedItem() == null ? FrameUtils.errorFormat(dataType, "The data type must be filled in.")
		: FrameUtils.normalFormat(dataType);

    }

    /**
     * @param lower
     *            {@code true} if it is to validate the number in
     *            {@linkplain #lowerBound}, otherwise it is to validate the number
     *            in {@linkplain #upperBound}
     * @return {@code true} if the {@code JTextField}, indicated by <b>lower</b>,
     *         contains a valid number, otherwise {@code false} and evidence the
     *         error
     * @see FrameUtils#errorFormat(JComponent, String)
     */
    private boolean isValidNumber(boolean lower) {
	JTextField bound = lower ? lowerBound : upperBound;
	String boundStr = lower ? "lower" : "upper";
	if (bound.getText().equals(""))
	    return FrameUtils.errorFormat(bound, "The " + boundStr + " limit must be filled in.");

	try {
	    if (dataType.getSelectedItem().toString().equals("Integer"))
		Integer.parseInt(bound.getText());
	    else
		Double.parseDouble(bound.getText());
	} catch (NumberFormatException e) {
	    return FrameUtils.errorFormat(bound, "The " + boundStr + " limit is not a valid number.");
	} catch (NullPointerException e) {
	    return FrameUtils.errorFormat(bound, "The data type must be filled in, to validate this field.");
	}
	return FrameUtils.normalFormat(bound);
    }

    /**
     * @return {@code true} if {@linkplain #lowerBound} and {@linkplain #upperBound}
     *         have valid numbers and if the lower limit is less than the upper
     *         limit, otherwise {@code false} and evidence the error
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

}