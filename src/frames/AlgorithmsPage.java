package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import frames.frameUtils.FrameUtils;

public class AlgorithmsPage extends SuperPage {

    /**
     * Default
     */
    private static final long serialVersionUID = 1L;

    private static final List<String> multiobjectiveAlgorithms = Arrays.asList("ABYSS", "CellDE45", "DMOPSO", "GDE3",
	    "GWASFGA", "IBEA", "MOCell", "MOCHC", "MOEAD", "MOMBI", "NSGAII", "NSGAIII", "OMOPSO", "PAES", "PESA2",
	    "Random Search", "RNSGAII", "SMPSO", "SMSEMOA", "SPEA2", "WASFGA"),
	    singleobjectiveAlgorithms = Arrays.asList("Coral Reefs Optimization", "Differential Evolution",
		    "Elitist Evolution Strategy", "Non Elitist Evolution Strategy", "Generational Genetic Algorithm",
		    "Steady StateGenetic Algorithm");
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

	title.setFont(FrameUtils.cuteFont(16));
	mainPanel.add(title);

	algorithmsListPanel.setLayout(new BoxLayout(algorithmsListPanel, BoxLayout.Y_AXIS));
	algorithmsListPanel.setBackground(Color.WHITE);
	algorithmsListPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
	JScrollPane scrollPane = new JScrollPane(algorithmsListPanel);
	scrollPane.setBackground(Color.WHITE);
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	// TODO remove when frame size is set
	scrollPane.setPreferredSize(new Dimension(200, 200));
	scrollPane.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(20, 40, 10, 10),
		BorderFactory.createLineBorder(Color.BLACK, 2)));

	mainPanel.add(scrollPane);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	JButton preselectionButton = FrameUtils.cuteButton("Preselection");
	mainPanel.add(preselectionButton);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	JButton automaticSelectionButton = FrameUtils.cuteButton("Automatic Selection");
	mainPanel.add(automaticSelectionButton);
    }

    @Override
    protected void onTop() {
	algorithmsList.clear();
	algorithmsListPanel.removeAll();

	title.setText((userInterface.getIsSingleobjective() ? "Single objective" : "Multi objective")
		+ " optimization algorithms available");
	List<String> list = userInterface.getIsSingleobjective() ? singleobjectiveAlgorithms : multiobjectiveAlgorithms;

	for (String algorithm : list)
	    algorithmsList.add(new JCheckBox(algorithm));

	for (JCheckBox checkBox : algorithmsList) {
	    checkBox.setBackground(Color.WHITE);
	    algorithmsListPanel.add(checkBox);
	}

	if( userInterface.getOptimizationAlgorithmsFromPage().size()>0) {
	    for (JCheckBox checkBox : algorithmsList) {
		for(String string : userInterface.getOptimizationAlgorithmsFromPage()) {
		    if(checkBox.getText().equals(string)) {
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
	for(JCheckBox checkbox : algorithmsList) {
	    if(checkbox.isSelected()==true) {
		count++;
	    }
	}
	if(count==0) {
	    for(JCheckBox checkbox : algorithmsList) {
		FrameUtils.errorFormat(checkbox, "You must chose at least one optimization algorithm.");
	    }
	    return false;
	} else {
	    for(JCheckBox checkbox : algorithmsList) {
		FrameUtils.normalFormat(checkbox);
	    }
	    return true;
	}
    }

    /**
     * Get the names of the checkboxes selected of a fitness function
     * @return
     */
    public ArrayList<String> getTheCheckboxesSelected() {
	ArrayList<String> tmp = new ArrayList<String>();
	for(JCheckBox checkbox : algorithmsList) {
	    if(checkbox.isSelected()==true) {
		if(userInterface.getIsSingleobjective()==true) {
		    tmp.add(singleobjectiveAlgorithms.get(algorithmsList.indexOf(checkbox)));
		} else {
		    tmp.add(multiobjectiveAlgorithms.get(algorithmsList.indexOf(checkbox)));
		}
	    }
	}
	return tmp;
    }

    @Override
    protected void saveToProblem() {
	userInterface.setOptimizationAlgorithmsFromPage(getTheCheckboxesSelected());
    }

    @Override
    protected void clearDataFromPage() {
	for(JCheckBox checkbox : algorithmsList) {
	    checkbox.setSelected(false);
	}
    }

}
