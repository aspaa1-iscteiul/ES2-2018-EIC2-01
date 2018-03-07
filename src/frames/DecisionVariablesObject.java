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

	this.dataType = FrameUtils.cuteComboBox(dataTypes);
	dataType.setSelectedItem(null);
	dataType.setEnabled(false);
	dataType.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		lowerBound.setEnabled(true);
		upperBound.setEnabled(true);
		if (isValidBound())
		    page.blockNextButton(false);
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

    public JTextField getName() {
	return name;
    }

    public void setName(JTextField name) {
	this.name = name;
    }

    public JComboBox<String> getDataType() {
	return dataType;
    }

    public void setDataType(JComboBox<String> dataType) {
	this.dataType = dataType;
    }

    public JTextField getLowerBound() {
	return lowerBound;
    }

    public void setLowerBound(JTextField lowerBound) {
	this.lowerBound = lowerBound;
    }

    public JTextField getUpperBound() {
	return upperBound;
    }

    public void setUpperBound(JTextField upperBound) {
	this.upperBound = upperBound;
    }

    public DecisionVariablesPage getPage() {
	return page;
    }

    public void setPage(DecisionVariablesPage page) {
	this.page = page;
    }

    public boolean isWellFilled() {
	return isValidName() && dataType.getSelectedItem() != null && isValidBound();
    }

    private boolean isValidName() {
	String text = name.getText().trim();
	if (text.equals("") || page.isNameRepeated(text)) {
	    dataType.setEnabled(false);
	    lowerBound.setEnabled(false);
	    upperBound.setEnabled(false);
	    page.blockNextButton(true);

	    page.showWarning(page.isNameRepeated(text));

	    return false;
	}
	page.showWarning(false);
	dataType.setEnabled(true);
	if (dataType.getSelectedItem() != null) {
	    lowerBound.setEnabled(true);
	    upperBound.setEnabled(true);
	    isValidBound();
	}
	return true;
    }

    /**
     * 
     * @param lower
     *            true if is about {@link #lowerBound} otherwise is about
     *            {@link #upperBound}
     * @return
     */
    private boolean isValidBound(boolean lower) {
	JTextField bound = lower ? lowerBound : upperBound;
	String type = (String) dataType.getSelectedItem();
	if (type == null)
	    return false;
	else {
	    if (bound.getText().equals("")) {
		page.showWarning2(false);
		page.blockNextButton(false);
		return true;
	    }
	    try {
		if (type.equals("Integer"))
		    Integer.parseInt(bound.getText());
		else
		    Double.parseDouble(bound.getText());
	    } catch (NumberFormatException e) {
		page.showWarning2(true);
		page.blockNextButton(true);
		return false;
	    }
	}
	page.showWarning2(false);
	page.blockNextButton(false);
	return true;
    }

    private boolean isValidBound() {
	if (isValidBound(true) && isValidBound(false)) {
	    if (!lowerBound.getText().equals("") && !upperBound.getText().equals("")) {
		double lower, upper;
		if (((String) dataType.getSelectedItem()).equals("Integer")) {
		    lower = Integer.parseInt(lowerBound.getText());
		    upper = Integer.parseInt(upperBound.getText());
		} else {
		    lower = Double.parseDouble(lowerBound.getText());
		    upper = Double.parseDouble(upperBound.getText());
		}
		boolean awnser = lower >= upper;
		page.showWarning3(awnser);
		page.blockNextButton(awnser);
		return awnser;
	    } else {
		page.showWarning3(false);
		page.blockNextButton(false);
		if (!lowerBound.getText().equals("")) {
		    return isValidBound(true);
		} else {
		    return isValidBound(false);
		}
	    }
	}
	return false;
    }

}