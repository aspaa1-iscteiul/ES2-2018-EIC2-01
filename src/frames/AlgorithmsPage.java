package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import frames.frameUtils.FrameUtils;
import jMetal.binaryConfiguration.BinaryAlgorithms;
import jMetal.doubleConfiguration.DoubleAlgorithms;
import jMetal.integerConfiguration.IntegerAlgorithms;
import objects.DataType;

public class AlgorithmsPage extends SuperPage {

    /**
     * Default
     */
    private static final long serialVersionUID = 1L;

    private JLabel title;
    private JPanel algorithmsListPanel;
    private ArrayList<JCheckBox> algorithmsList;

    public AlgorithmsPage(UserInterface userInterface) {
	super(userInterface);
    }

    @Override
    protected void initialize() {
	title = new JLabel();
	algorithmsListPanel = new JPanel();
	algorithmsList = new ArrayList<>();
    }

    @Override
    protected void createMainPanel() {
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	mainPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

	JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	titlePanel.setBackground(Color.WHITE);
	title.setFont(FrameUtils.cuteFont(16));
	titlePanel.add(title);

	mainPanel.add(titlePanel);

	algorithmsListPanel.setLayout(new BoxLayout(algorithmsListPanel, BoxLayout.Y_AXIS));
	algorithmsListPanel.setBackground(Color.WHITE);
	algorithmsListPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
	JScrollPane scrollPane = new JScrollPane(algorithmsListPanel);
	scrollPane.setBackground(Color.WHITE);
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	// TODO remove when frame size is set
	scrollPane.setPreferredSize(new Dimension(200, 300));
	scrollPane.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(20, 40, 10, 10),
		BorderFactory.createLineBorder(Color.BLACK, 2)));

	mainPanel.add(scrollPane);

    }

    @Override
    protected void onTop() {
	algorithmsList.clear();
	algorithmsListPanel.removeAll();

	title.setText((userInterface.getIsSingleobjective() ? "Single objective" : "Multi objective")
		+ " optimization algorithms available");

	

	for (String algorithm : getAlgorithmsList())
	    algorithmsList.add(new JCheckBox(algorithm));

	for (JCheckBox checkBox : algorithmsList) {
	    checkBox.setBackground(Color.WHITE);
	    algorithmsListPanel.add(checkBox);
	}

	if (userInterface.getOptimizationAlgorithmsFromPage().size() > 0) {
	    for (JCheckBox checkBox : algorithmsList) {
		for (String string : userInterface.getOptimizationAlgorithmsFromPage()) {
		    if (checkBox.getText().equals(string)) {
			checkBox.setSelected(true);
		    }
		}
	    }
	}

	algorithmsListPanel.repaint();
	algorithmsListPanel.revalidate();
    }

    @Override
    protected boolean areAllDataWellFilled() {
	int count = 0;
	for (JCheckBox checkbox : algorithmsList) {
	    if (checkbox.isSelected() == true) {
		count++;
	    }
	}
	if (count == 0) {
	    for (JCheckBox checkbox : algorithmsList) {
		FrameUtils.errorFormat(checkbox, "You must chose at least one optimization algorithm.");
	    }
	    return false;
	} else {
	    for (JCheckBox checkbox : algorithmsList) {
		FrameUtils.normalFormat(checkbox);
	    }
	    return true;
	}
    }

    @Override
    protected void saveToProblem() {
	userInterface.setOptimizationAlgorithmsFromPage(getTheCheckboxesSelected());
    }

    @Override
    protected void clearDataFromPage() {
	for (JCheckBox checkbox : algorithmsList) {
	    checkbox.setSelected(false);
	}
    }

    /**
     * Get the names of the checkboxes of the algorithms selected
     * 
     * @return
     */
    public ArrayList<String> getTheCheckboxesSelected() {
	ArrayList<String> tmp = new ArrayList<String>();
	ArrayList<String> algorithms = getAlgorithmsList();
	for (JCheckBox checkbox : algorithmsList) {
	    if (checkbox.isSelected() == true) {
		// TODO
		tmp.add(algorithms.get(algorithmsList.indexOf(checkbox)));
//		if (userInterface.getIsSingleobjective() == true) {
//		    tmp.add(DoubleAlgorithms.SINGLE_OBJECTIVE.get(algorithmsList.indexOf(checkbox)));
//		} else {
//		    tmp.add(DoubleAlgorithms.MULTI_OBJECTIVE.get(algorithmsList.indexOf(checkbox)));
//		}
	    }
	}
	return tmp;
    }
    
    /**
     * Returns the list of algorithms that will be added to the GUI based on a SINGLE_OBJECTIVE or MULTI_OBJECTIVE perspective
     * @return
     */
    public ArrayList<String> getAlgorithmsList() {
	ArrayList<String> list;
	DataType problemType = userInterface.getDecisionVariablesFromPage().get(0).getDataTypeToProblem();
	if (problemType == DataType.DOUBLE)
	    list = userInterface.getIsSingleobjective() ? DoubleAlgorithms.SINGLE_OBJECTIVE
		    : DoubleAlgorithms.MULTI_OBJECTIVE;
	else if (problemType == DataType.INTEGER)
	    list = userInterface.getIsSingleobjective() ? IntegerAlgorithms.SINGLE_OBJECTIVE
		    : IntegerAlgorithms.MULTI_OBJECTIVE;
	else
	    list = userInterface.getIsSingleobjective() ? BinaryAlgorithms.SINGLE_OBJECTIVE
		    : BinaryAlgorithms.MULTI_OBJECTIVE;
	return list;
    }
}