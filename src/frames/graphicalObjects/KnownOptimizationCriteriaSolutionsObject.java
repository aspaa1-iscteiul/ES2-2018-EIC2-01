package frames.graphicalObjects;

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
import frames.KnownOptimizationCriteriaSolutionsPage;
import frames.frameUtils.FrameUtils;

public class KnownOptimizationCriteriaSolutionsObject {

    private KnownOptimizationCriteriaSolutionsPage pageAssociated;
    private JTextField name;
    private String dataType;
    private ArrayList<JTextField> solutionsList;
    private JLabel addIcon;
    private JLabel newSolutionInfo;

    public KnownOptimizationCriteriaSolutionsObject(KnownOptimizationCriteriaSolutionsPage page, String name, String type) {
	this.pageAssociated = page;
	this.name = new JTextField(name.length());
	this.name.setText(name);
	this.dataType = type;
	JTextField solution1 = new JTextField(2);
	JTextField solution2 = new JTextField(2);
	this.solutionsList = new ArrayList<JTextField>();
	this.solutionsList.add(solution1);
	this.solutionsList.add(solution2);
	this.addIcon = new JLabel();
	this.newSolutionInfo = new JLabel("Add new solutions");
    }

    public  KnownOptimizationCriteriaSolutionsObject(KnownOptimizationCriteriaSolutionsPage page, String name, String variableDataType,
	    ArrayList<String> solutions) {
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
     * Transforms the solutions textfield into strings
     * @return
     */
    public ArrayList<String> getSolutionListInString() {
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
		+ "]";
    }

    public JTextField getName() {
	return name;
    }

    public void setName(JTextField name) {
	this.name = name;
    }

    public KnownOptimizationCriteriaSolutionsPage getPage() {
	return pageAssociated;
    }

    public void setPage(KnownOptimizationCriteriaSolutionsPage page) {
	this.pageAssociated = page;
    }

    public ArrayList<JTextField> getTextfieldList() {
	return solutionsList;
    }

    public void setTextfieldList(ArrayList<JTextField> textfieldList) {
	this.solutionsList = textfieldList;
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
		} catch (NumberFormatException e1) {
		    tmp = FrameUtils.errorFormat(textField, "Solutions must have the same data type has the variable");
		    return tmp;
		}
	    }
	    if (dataType.equals("Double")) {
		try {
		    Double.parseDouble(textField.getText());
		    tmp = FrameUtils.normalFormat(textField);
		} catch (NumberFormatException e1) {
		    tmp = FrameUtils.errorFormat(textField, "Solutions must have the same data type has the variable");
		    return tmp;
		}
	    }
	    return tmp;
	}
    }

}