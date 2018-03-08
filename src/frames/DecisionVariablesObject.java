package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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

    public DecisionVariablesObject(final DecisionVariablesPage page) {
	this.page = page;
	this.variablesPanel = new JPanel();
	DecisionVariablesObject tmp = this;
	this.name = new JTextField(5) {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void processKeyEvent(KeyEvent key) {
		super.processKeyEvent(key);
		isValidName();
	    }
	};

	dataType = FrameUtils.cuteComboBox(dataTypes);
	dataType.setSelectedItem(null);
	dataType.setEnabled(false);
	dataType.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		lowerBound.setEnabled(true);
		upperBound.setEnabled(true);
		if (isValidBound())
		    page.isAllVariablesWellFilled();
	    }
	});

	this.lowerBound = new JTextField(5) {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void processKeyEvent(KeyEvent key) {
		super.processKeyEvent(key);
		isValidBound();
	    }
	};
	lowerBound.setEnabled(false);

	this.upperBound = new JTextField(5) {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void processKeyEvent(KeyEvent key) {
		super.processKeyEvent(key);
		isValidBound();
	    }
	};
	upperBound.setEnabled(false);

	constructorContinuation(tmp);
    }

    private void constructorContinuation(final DecisionVariablesObject tmp) {
	this.deleteIcon = new JLabel();
	this.deleteIcon.setIcon(new ImageIcon("./src/frames/images/delete_icon2.png"));

	this.deleteIcon.addMouseListener(new MouseListener() {
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
	dataType.setEnabled(true);
	lowerBound.setEnabled(true);
	upperBound.setEnabled(true);
	dataType.setSelectedItem(type);
    }

    public void setLowerBound(String lower) {
	if (dataType.getSelectedItem() != null)
	    lowerBound.setText(lower);
    }

    public void setUpperBound(String upper) {
	if (dataType.getSelectedItem() != null)
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
	return isValidName() && dataType.getSelectedItem() != null && isValidBound();
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
	if (text.equals("") || page.isNameRepeated(text)) {
	    dataType.setEnabled(false);
	    lowerBound.setEnabled(false);
	    upperBound.setEnabled(false);
	    page.blockNextButton(true);

	    page.showWarning(page.isNameRepeated(text), 1);

	    return false;
	}
	page.showWarning(false, 1);
	dataType.setEnabled(true);
	if (dataType.getSelectedItem() != null) {
	    lowerBound.setEnabled(true);
	    upperBound.setEnabled(true);
	    isValidBound();
	}
	return true;
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
	String type = (String) dataType.getSelectedItem();
	if (type == null)
	    return false;
	else {
	    if (bound.getText().equals("")) {
		page.showWarning(false, 2);
		page.blockNextButton(false);
		return true;
	    }
	    try {
		if (type.equals("Integer"))
		    Integer.parseInt(bound.getText());
		else
		    Double.parseDouble(bound.getText());
	    } catch (NumberFormatException e) {
		page.showWarning(true, 2);
		page.blockNextButton(true);
		return false;
	    }
	}
	page.showWarning(false, 2);
	page.blockNextButton(false);
	return true;
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
	if (isValidNumber(true) && isValidNumber(false)) {
	    if (!lowerBound.getText().equals("") && !upperBound.getText().equals("")) {
		double lower, upper;
		if (((String) dataType.getSelectedItem()).equals("Integer")) {
		    lower = Integer.parseInt(lowerBound.getText());
		    upper = Integer.parseInt(upperBound.getText());
		} else {
		    lower = Double.parseDouble(lowerBound.getText());
		    upper = Double.parseDouble(upperBound.getText());
		}
		boolean awnser = lower < upper;
		page.showWarning(!awnser, 3);
		page.blockNextButton(!awnser);
		return awnser;
	    } else {
		page.showWarning(false, 3);
		page.blockNextButton(false);
		if (!lowerBound.getText().equals("")) {
		    return isValidNumber(true);
		} else {
		    return isValidNumber(false);
		}
	    }
	}
	return false;
    }

}