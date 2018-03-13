package frames;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This object was created to aid the construction of the Known Solutions Page
 */

public class KnownSolutionsObject {

    private KnownSolutionsPage pageAssociated;
    private JTextField name;
    private JTextField solution1;
    private JTextField solution2;
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
     */
    public KnownSolutionsObject(KnownSolutionsPage page, String name, String type, String lowerBound,
	    String upperBound, String[] invalidValues) {
	this.pageAssociated = page;
	this.name = new JTextField(name.length());
	this.name.setText(name);
	this.dataType = type;
	this.lowerBound = lowerBound;
	this.upperBound = upperBound;
	this.setInvalidValues(invalidValues);
	this.solution1 = new JTextField(3);
	this.solution2 = new JTextField(3);
	this.solutionsList = new ArrayList<JTextField>();
	this.solutionsList.add(solution1);
	this.solutionsList.add(solution2);
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
	addIcon.setIcon(new ImageIcon("./src/frames/images/add_icon.png"));
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
     * solution
     * 
     * @param textField
     */
    public boolean validateInputs(JTextField textField) {
	boolean tmp = false;
	if (textField.getText().isEmpty()) {
	    FrameUtils.normalFormat(textField);
	    return true;
	} else {
	    if (dataType.equals("Integer")) {
		try {
		    Integer.parseInt(textField.getText());
		    FrameUtils.normalFormat(textField);
		    if (!lowerBound.isEmpty() && !upperBound.isEmpty()) {
			if (Integer.parseInt(textField.getText()) > Integer.parseInt(upperBound)
				|| Integer.parseInt(textField.getText()) < Integer.parseInt(lowerBound)) {
			    FrameUtils.errorFormat(textField,
				    "Solutions must agree with the variable's lower and upper bound");
			} else {
			    FrameUtils.normalFormat(textField);
			    if(!invalidValues.equals(null)) {
				for(int i = 0; i!= invalidValues.length; i++) {
				    if(invalidValues[i].equals(textField.getText().trim())) {
					FrameUtils.errorFormat(textField,
						"Solutions must agree with the invalid values selected");
				    } else {
					FrameUtils.normalFormat(textField);
					tmp = true;
				    }
				}
			    }

			}
		    }
		} catch (NumberFormatException e1) {
		    FrameUtils.errorFormat(textField, "Solutions must have the same data type has the variable");
		}
	    }
	    if (dataType.equals("Double")) {
		try {
		    Double.parseDouble(textField.getText());
		    FrameUtils.normalFormat(textField);
		    if (!lowerBound.isEmpty() && !upperBound.isEmpty()) {
			if (Double.parseDouble(textField.getText()) > Double.parseDouble(upperBound)
				|| Double.parseDouble(textField.getText()) < Double.parseDouble(lowerBound)) {
			    FrameUtils.errorFormat(textField,
				    "Solutions must agree with the variable's lower and upper bound");
			} else {
			    FrameUtils.normalFormat(textField);
			    if(!invalidValues.equals(null)) {
				for(int i = 0; i!= invalidValues.length; i++) {
				    if(invalidValues[i].equals(textField.getText().trim())) {
					FrameUtils.errorFormat(textField,
						"Solutions must agree with the invalid values selected");
				    } else {
					FrameUtils.normalFormat(textField);
					tmp = true;
				    }
				}
			    }
			}
		    }
		} catch (NumberFormatException e1) {
		    FrameUtils.errorFormat(textField, "Solutions must have the same data type has the variable");
		}
	    }
	    return tmp;
	}
    }

    public JTextField getName() {
	return name;
    }

    public void setName(JTextField name) {
	this.name = name;
    }

    public JTextField getSolution1() {
	return solution1;
    }

    public void setSolution1(JTextField solution1) {
	this.solution1 = solution1;
    }

    public JTextField getSolution2() {
	return solution2;
    }

    public void setSolution2(JTextField solution2) {
	this.solution2 = solution2;
    }

    public KnownSolutionsPage getPage() {
	return pageAssociated;
    }

    public void setPage(KnownSolutionsPage page) {
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

}