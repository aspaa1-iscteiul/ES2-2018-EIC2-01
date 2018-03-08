package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class DecisionVariablesObject {

    private DecisionVariablesPage page;
    private JPanel variablesPanel;
    private JTextField name;
    private final static String[] dataTypes = { "Integer", "Double" };
    private JComboBox<String> dataType;
    private JTextField lowerBound;
    private JTextField upperBound;
    private JLabel deleteIcon;

    public DecisionVariablesObject(final DecisionVariablesPage page, String name, String dataType, String lowerBound,
	    String upperBound) {
	this.page = page;
	variablesPanel = new JPanel();
	final DecisionVariablesObject tmp = this;
	this.name = new JTextField(name, 5);
	this.dataType = FrameUtils.cuteComboBox(dataTypes);
	this.dataType.setSelectedItem(dataType);
	this.lowerBound = new JTextField(lowerBound, 5);
	this.upperBound = new JTextField(upperBound, 5);
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

    public JPanel transformIntoAPanel() {
	variablesPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	variablesPanel.setBackground(Color.WHITE);
	Border border = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
		BorderFactory.createEmptyBorder(0, 10, 0, 10));
	name.setBorder(border);
	name.setPreferredSize(new Dimension(10, 22));
	lowerBound.setBorder(border);
	lowerBound.setPreferredSize(new Dimension(10, 22));
	upperBound.setBorder(border);
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
     * 
     * @see #isValidName()
     * @see #isValidBound()
     */
    public boolean isWellFilled() {
	// separated to run all methods
	boolean isValidName = isValidName(), isDataTypeSelected = isDataTypeSelected(), isValidBound = isValidBound();
	return isValidName && isDataTypeSelected && isValidBound;
    }

    /**
     * Returns {@code true} if the name is not empty and is not repeated, also
     * unlocks the {@linkplain #dataType}, otherwise places warnings and locks
     * the next button in the {@linkplain DecisionVariablesPage}
     * 
     * @return {@code true} if the name is not empty and is not repeated,
     *         otherwise {@code false}
     * 
     * @see DecisionVariablesPage#isNameRepeated(String)
     */
    private boolean isValidName() {
	String text = name.getText().trim();
	name.setText(text);
	String info = (text.equals("") ? "The variable name must be filled in. " : "")
		+ (page.isNameRepeated(text) ? "The variable name must be unique." : "");
	return !info.equals("") ? FrameUtils.errorFormat(name, info, false) : FrameUtils.normalFormat(name, false);
    }

    private boolean isDataTypeSelected() {
	return dataType.getSelectedItem() == null
		? FrameUtils.errorFormat(dataType, "The data type must be filled in.", true)
		: FrameUtils.normalFormat(dataType, true);

    }

    /**
     * Returns {@code true} if the {@code JTextField}, indicated by
     * <b>lower</b>, contains a valid number or is empty, otherwise places
     * warnings and locks the next button in the
     * {@linkplain DecisionVariablesPage}
     * 
     * @param lower
     *            {@code true} if it is to validate the number in
     *            {@linkplain #lowerBound}, otherwise it is to validate the
     *            number in {@linkplain #upperBound}
     * 
     * @return {@code true} if the JTextField contains a valid number or is
     *         empty, otherwise {@code false}
     */
    private boolean isValidNumber(boolean lower) {
	JTextField bound = lower ? lowerBound : upperBound;
	if (bound.getText().equals(""))
	    return FrameUtils.errorFormat(bound, "The " + (lower ? "lower" : "upper") + " limit must be filled in.",
		    false);

	try {
	    if (dataType.getSelectedItem().toString().equals("Integer"))
		Integer.parseInt(bound.getText());
	    else
		Double.parseDouble(bound.getText());
	} catch (NumberFormatException e) {
	    return FrameUtils.errorFormat(bound, "The " + (lower ? "lower" : "upper") + " limit is not a valid number.",
		    false);
	}
	return FrameUtils.normalFormat(bound, false);
    }

    /**
     * Returns {@code true} if {@linkplain #lowerBound} and
     * {@linkplain #upperBound} have valid numbers and if the lower limit is
     * less than the upper limit, otherwise places warnings and locks the next
     * button in the {@linkplain DecisionVariablesPage}
     * 
     * @return {@code true} if the {@linkplain #lowerBound} and
     *         {@linkplain #upperBound} have valid numbers and if the lower
     *         limit is less than the upper limit, otherwise {@code false}
     * 
     * @see #isValidNumber(boolean)
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
		    ? FrameUtils.errorFormat(upperBound, "The upper bound must be bigger than the lower bound", false)
		    : FrameUtils.normalFormat(upperBound, false);

	}
	return false;
    }

}