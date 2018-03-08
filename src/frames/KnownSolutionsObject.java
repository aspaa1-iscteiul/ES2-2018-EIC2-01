package frames;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * This object was created to aid the construction of the Known Solutions Page
 */

public class KnownSolutionsObject {

    private KnownSolutionsPage page;
    private JTextField name;
    private JTextField solution1;
    private JTextField solution2;
    private ArrayList<JTextField> textfieldList;
    private JLabel addIcon;
    private String type;
    private String lowerBound;
    private String upperBound;

    /**
     * This object will have the solutions to a given variable that must agree with the lower and upper bounds presented by the variable
     * @param page
     * @param name
     * @param type
     * @param lowerBound
     * @param upperBound
     */
    public KnownSolutionsObject(KnownSolutionsPage page, String name, String type, String lowerBound, String upperBound) {
	this.page = page;
	this.name = new JTextField(name);
	this.type = type;
	this.lowerBound = lowerBound;
	this.upperBound = upperBound;
	this.solution1 = new JTextField(3);
	this.solution2 = new JTextField(3);
	this.textfieldList = new ArrayList<JTextField>();
	this.textfieldList.add(solution1);
	this.textfieldList.add(solution2);
    }

    public JPanel transformIntoAPanel() {
	JPanel overallPanel = new JPanel();
	overallPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	overallPanel.setBackground(Color.WHITE);

	final JPanel firstPanel = new JPanel();
	firstPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	firstPanel.setBackground(Color.WHITE);
	final Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
	name.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(0, 10, 0, 10)));
	name.setPreferredSize(null);
	name.setEditable(false);
	firstPanel.add(name);

	for (JTextField textField : textfieldList) {
	    textField.setBorder(
		    BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(0, 10, 0, 10)));

	    textField.addKeyListener(new KeyListener() {
		@Override
		public void keyPressed(KeyEvent arg0) {
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
		    keyListenerContent(textField);
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
		}
	    });

	    firstPanel.add(textField);
	}

	overallPanel.add(firstPanel);

	JPanel addPanel = new JPanel();
	addPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	addPanel.setBackground(Color.WHITE);

	this.addIcon = new JLabel();
	this.addIcon.setIcon(new ImageIcon("./src/frames/images/add_icon.png"));
	this.addIcon.addMouseListener(new MouseListener() {
	    @Override
	    public void mouseClicked(MouseEvent arg0) {
		EventQueue.invokeLater(new Runnable() {
		    public void run() {
			JTextField newSolution = new JTextField(3);
			newSolution.setBorder(BorderFactory.createCompoundBorder(border,
				BorderFactory.createEmptyBorder(0, 10, 0, 10)));
			
			newSolution.addKeyListener(new KeyListener() {
				@Override
				public void keyPressed(KeyEvent arg0) {
				}

				@Override
				public void keyReleased(KeyEvent arg0) {
				    keyListenerContent(newSolution);
				}

				@Override
				public void keyTyped(KeyEvent arg0) {
				}
			    });

			firstPanel.add(newSolution);
			textfieldList.add(newSolution);
			page.refreshPage();
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
	addPanel.add(new JLabel("Add new solutions"));
	overallPanel.add(addPanel);

	return overallPanel;
    }

    /**
     * Validates if all solutions meet the variable's data type and if so removes the warning panel 
     * @return
     */
    private boolean validateIfItsOkToRemoveDataTypeWarning() {
	boolean tmp = true;
	for (JTextField textField : textfieldList) {
	    if(type.equals("Integer") && !textField.getText().isEmpty()) {
		try {
		    Integer.parseInt(textField.getText());
		} catch(NumberFormatException e1) {
		    tmp = false;
		    break;
		}
	    }
	    if(type.equals("Double") && !textField.getText().isEmpty()) {
		try {
		    Double.parseDouble(textField.getText());
		} catch(NumberFormatException e1) {
		    tmp = false;
		    break;
		}
	    }
	}
	if(tmp == true){
	    page.setWarningAboutDataType(false);
	}
	return tmp;
    }

    /**
     * Validates if all solutions agree with the variable's lower and upper bound and if so removes the warning panel 
     * @return
     */
    private boolean validateIfItsOkToRemoveBoundsWarning() {
	boolean tmp = true;
	for (JTextField textField : textfieldList) {
	    if(type.equals("Integer") && !textField.getText().isEmpty()) {
		if( Integer.parseInt(textField.getText()) >  Integer.parseInt(upperBound) || Integer.parseInt(textField.getText()) <  Integer.parseInt(lowerBound) ) {
		    tmp = false;
		    break;
		}
	    }
	    if(type.equals("Double") && !textField.getText().isEmpty()) {
		if( Double.parseDouble(textField.getText()) >  Double.parseDouble(upperBound) || Double.parseDouble(textField.getText()) <  Double.parseDouble(lowerBound) ) {
		    tmp = false;
		    break;
		}
	    }
	}
	if(tmp == true){
	    page.setWarningAboutBounds(false);
	}
	return tmp;
    }

    /**
     * Validates if all solutions meet the data type and the bounds and if so enables the button that allows to move to the next page
     */
    private void validateIfItsOkToEnableButton() {
	if(validateIfItsOkToRemoveBoundsWarning()==true && validateIfItsOkToRemoveDataTypeWarning()==true){
	    page.enableNextButton();
	}
    }

    /**
     * Creates the key listeners of the variables that validates the input
     * @param textField
     */
    private void keyListenerContent(JTextField textField) {
	if(type.equals("Integer") && !textField.getText().isEmpty()) {
	    try {
		Integer.parseInt(textField.getText());
		validateIfItsOkToRemoveDataTypeWarning();
		if(!lowerBound.isEmpty() && !upperBound.isEmpty()) {
		    if( Integer.parseInt(textField.getText()) >  Integer.parseInt(upperBound) || Integer.parseInt(textField.getText()) <  Integer.parseInt(lowerBound) ) {
			page.setWarningAboutBounds(true);
		    } else {
			validateIfItsOkToRemoveBoundsWarning();
		    }
		}
		validateIfItsOkToEnableButton();
	    } catch(NumberFormatException e1) {
		page.setWarningAboutDataType(true);
	    }
	}
	if(type.equals("Double") && !textField.getText().isEmpty()) {
	    try {
		Double.parseDouble(textField.getText());
		validateIfItsOkToRemoveDataTypeWarning();
		if(!lowerBound.isEmpty() && !upperBound.isEmpty()) {
		    if( Double.parseDouble(textField.getText()) >  Double.parseDouble(upperBound) || Double.parseDouble(textField.getText()) <  Double.parseDouble(lowerBound) ) {
			page.setWarningAboutBounds(true);
		    } else {
			validateIfItsOkToRemoveBoundsWarning();
		    }
		}
		validateIfItsOkToEnableButton();
	    } catch(NumberFormatException e1) {
		page.setWarningAboutDataType(true);
	    }
	}
	if(textField.getText().isEmpty()) {
	    validateIfItsOkToEnableButton();
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
	return page;
    }

    public void setPage(KnownSolutionsPage page) {
	this.page = page;
    }

    public ArrayList<JTextField> getTextfieldList() {
	return textfieldList;
    }

    public void setTextfieldList(ArrayList<JTextField> textfieldList) {
	this.textfieldList = textfieldList;
    }

}