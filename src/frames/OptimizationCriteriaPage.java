package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import frames.frameUtils.FrameUtils;
import frames.graphicalObjects.OptimizationCriteriaObject;

/**
 * This class represents the Optimization Criteria Page
 */

public class OptimizationCriteriaPage extends SuperPage {

    private static final long serialVersionUID = 1L;
    private ArrayList<OptimizationCriteriaObject> optimizationCriteriaList;
    private final static String[] dataTypes = { "Integer", "Double", "Binary" };
    private JComboBox<String> dataType;
    private JPanel subSubMainPanel;

    /**
     * 
     * @param userInterface
     */
    public OptimizationCriteriaPage(UserInterface userInterface) {
	super(userInterface);
    }

    @Override
    protected void initialize() {
	optimizationCriteriaList = new ArrayList<OptimizationCriteriaObject>();
    }

    @Override
    protected void createMainPanel() {
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	// XXX change when frame size is set
	mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

	JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	titlePanel.setBackground(Color.WHITE);
	JLabel titleLabel = new JLabel("Optimization Criteria");
	titleLabel.setFont(FrameUtils.cuteFont(16));
	titlePanel.add(titleLabel);
	mainPanel.add(titlePanel);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	JPanel introPanel = new JPanel(new BorderLayout());
	introPanel.setBackground(Color.WHITE);
	introPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
		BorderFactory.createEmptyBorder(10, 10, 10, 10))); 

	introPanel.add(labelsPanel(), BorderLayout.CENTER);
	introPanel.add(valuesPanel(), BorderLayout.SOUTH);

	mainPanel.add(introPanel);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	JPanel subMainPanel = new JPanel(new BorderLayout());
	subMainPanel.setBackground(Color.WHITE);
	subMainPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
		BorderFactory.createEmptyBorder(10, 10, 10, 10)));

	JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	infoPanel.setBackground(Color.WHITE);
	JLabel name = new JLabel("<html><font color=red><b> *</b></font> Name</html>");
	// to add space between the labels
	name.setBorder(new EmptyBorder(0, 0, 0, 122));
	name.setFont(FrameUtils.cuteFont(12));
	infoPanel.add(name);

	subMainPanel.add(infoPanel, BorderLayout.NORTH);

	subSubMainPanel = new JPanel();
	subSubMainPanel.setBackground(Color.WHITE);
	subSubMainPanel.setLayout(new BoxLayout(subSubMainPanel, BoxLayout.Y_AXIS));

	OptimizationCriteriaObject optimizationCriteria1 = new OptimizationCriteriaObject(this);
	OptimizationCriteriaObject optimizationCriteria2 = new OptimizationCriteriaObject(this);
	optimizationCriteriaList.add(optimizationCriteria1);
	optimizationCriteriaList.add(optimizationCriteria2);
	subSubMainPanel.add(optimizationCriteria1.transformIntoAPanel());
	subSubMainPanel.add(optimizationCriteria2.transformIntoAPanel());

	subMainPanel.add(subSubMainPanel, BorderLayout.CENTER);

	continuateConstructor(subMainPanel);
    }

    /**
     * Method to create the main part of the page to be viewed
     * 
     * @param subMainPanel
     */
    private void continuateConstructor(JPanel subMainPanel) {
	JPanel addOptionPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	addOptionPanel.setBackground(Color.WHITE);
	JLabel addIcon = new JLabel();
	addIcon.setIcon(new ImageIcon(getClass().getResource("images/add_icon.png")));

	final OptimizationCriteriaPage tmp = this;
	addIcon.addMouseListener(new MouseListener() {
	    @Override
	    public void mouseClicked(MouseEvent arg0) {
		EventQueue.invokeLater(new Runnable() {
		    public void run() {
			OptimizationCriteriaObject optimizationCriteria = new OptimizationCriteriaObject(tmp);
			optimizationCriteriaList.add(optimizationCriteria);
			subSubMainPanel.add(optimizationCriteria.transformIntoAPanel());
			userInterface.refreshPage();
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
	addOptionPanel.add(addIcon, BorderLayout.WEST);
	addOptionPanel.add(new JLabel("Add new criteria"));

	subMainPanel.add(addOptionPanel, BorderLayout.SOUTH);

	JScrollPane scrollPane = new JScrollPane(subMainPanel);
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	scrollPane.setPreferredSize(new Dimension(450, 170));

	mainPanel.add(scrollPane);
	FrameUtils.addEmptyLabels(mainPanel, 1);
    }

    /**
     * Creates the panel with the labels that display information 
     * about the optimization criteria attributes
     * @return
     */
    private JPanel labelsPanel() {
	JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	panel.setBackground(Color.WHITE);
	JLabel dataType = new JLabel("<html><font color=red><b>*</b></font> Data Type");
	dataType.setBorder(new EmptyBorder(0, 0, 0, 15));
	dataType.setFont(FrameUtils.cuteFont(12));
	panel.add(dataType);
	return panel;
    }

    /**
     * Creates the panel with the inputs of the optimization criteria attributes
     * @return
     */
    private JPanel valuesPanel() {
	JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	panel.setBackground(Color.WHITE);
	dataType = FrameUtils.cuteComboBox(dataTypes);
	panel.add(dataType);
	return panel;
    }

    /**
     * Imports the data of the file selected to the panel
     */
    private void importFromFilePanel() {
	JButton importFromFile = FrameUtils.cuteButton("Import from file");
	importFromFile.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (JOptionPane.showConfirmDialog(userInterface.getFrame(),
			"The imported file must only have one optimization criteria name per line. "
				+ System.lineSeparator()
				+ "The optimization criteria name field does not support spaces between characters."
				+ System.lineSeparator()
				+ "Therefore, if spaces are found while reading the document they will be automatically removed.",
				"Import optimization criteria", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.WARNING_MESSAGE) != JOptionPane.OK_OPTION)
		    return;

		JFileChooser fileChooser = new JFileChooser();
		// Launches the JFileChooser on the Desktop directory
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
		fileChooser.setDialogTitle("Select a file with the optimization criteria names");
		// Prevents selection of multiple options
		fileChooser.setMultiSelectionEnabled(false);

		if (fileChooser.showOpenDialog(userInterface.getFrame()) == JFileChooser.APPROVE_OPTION)
		    readImportedFile(fileChooser.getSelectedFile(), askUserForVariableAttributes());
	    }
	});
	buttonsPanel.add(importFromFile);
	FrameUtils.addEmptyLabels(buttonsPanel, 21);
    }

    protected void createButtonsPanel() {
	importFromFilePanel();
	JButton backButton = FrameUtils.cuteButton("Back");
	backButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		userInterface.goToPreviousPage();
	    }
	});
	buttonsPanel.add(backButton);

	buttonsPanel.add(new JLabel()); // to add space between the two buttons

	JButton cancelButton = FrameUtils.cuteButton("Cancel");
	cancelButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		System.exit(0);
	    }
	});
	buttonsPanel.add(cancelButton);

	buttonsPanel.add(new JLabel()); // to add space between the two buttons

	JButton nextButton = FrameUtils.cuteButton("Next");
	nextButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		userInterface.goToNextPage();
	    }
	});
	buttonsPanel.add(nextButton);
    }

    /**
     * Ask user to input values on variables created
     * 
     * @return
     */
    private String[] askUserForVariableAttributes() {
	JPanel inputPanel = new JPanel(new GridLayout(3, 2));
	inputPanel.add(new JLabel("<html><font color=red><b>*</b></font> Data Type: "));
	JComboBox<String> dataType = new JComboBox<String>(new String[] { "Integer", "Double" });
	inputPanel.add(dataType);

	if (JOptionPane.showConfirmDialog(userInterface.getFrame(), inputPanel, "Enter the attributes to all variables",
		JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
	    String type = (String) dataType.getSelectedItem();
	    return new String[] { type };
	}
	return null;
    }

    /**
     * Reads an imported variable and creates variables with the read values and
     * places them on the frame
     * 
     * @param selectedFile
     * @param values
     */
    private void readImportedFile(File selectedFile, String[] values) {
	if (values == null)
	    return;
	try {
	    Scanner scn = new Scanner(selectedFile);
	    while (scn.hasNextLine()) {
		String line = scn.nextLine().replaceAll(" ", "");
		if (!line.equals("")) {
		    //TODO: Read invalid values from file
		    OptimizationCriteriaObject oco = new OptimizationCriteriaObject(this, line, values[0]);
		    optimizationCriteriaList.add(oco);
		    subSubMainPanel.add(oco.transformIntoAPanel());
		}
	    }
	    userInterface.refreshPage();
	    scn.close();
	} catch (FileNotFoundException e) {
	    JOptionPane.showMessageDialog(userInterface.getFrame(),
		    "The file " + selectedFile.getAbsolutePath() + " doesn't exists");
	}
    }

    @Override 
    protected void onTop() {
	userInterface.getFrame().setTitle("Problem Solving App");
	if(userInterface.isXmlFileWasImportedAtIndex(3)==true) {
	    if(userInterface.getProblem().getOptimizationCriteriaDataType() != null) {
		if(userInterface.getProblem().getOptimizationCriteriaDataType().name().equals("INTEGER")) {
		    dataType.setSelectedIndex(0);
		} else if(userInterface.getProblem().getOptimizationCriteriaDataType().name().equals("DOUBLE")) {
		    dataType.setSelectedIndex(1);
		} else {
		    dataType.setSelectedIndex(2);
		}
	    }
	    subSubMainPanel.removeAll();
	    setThisPage();
	    optimizationCriteriaList = userInterface.getOptimizationCriteriaFromPage();
	    for(OptimizationCriteriaObject oco : optimizationCriteriaList) {
		subSubMainPanel.add(oco.transformIntoAPanel());
	    }
	    userInterface.putXmlFileWasImportedFalseAtIndex(3);
	}
    }

    /**
     * Returns {@code true} if there is at least other optimization criteria with
     * the same name, otherwise {@code false}
     * 
     * @param varName
     *            the {@code String} to compare to the other optimization criteria
     *            names in the {@linkplain #decisionVariableList}
     * 
     * @return {@code true} if there is at least other optimization criteria with
     *         the same name, otherwise {@code false}
     */
    public boolean isNameRepeated(String varName) {
	int count = 0;
	for (OptimizationCriteriaObject oco : optimizationCriteriaList)
	    if (!oco.getVariableName().equals("") && oco.getVariableName().equals(varName))
		count++;
	return count >= 2;
    }

    /**
     * Removes an optimization criteria from the optimization criteria list and
     * updates the frame
     * 
     * @param oco
     */
    public void removeOptimizationCriteriaFromList(OptimizationCriteriaObject oco) {
	optimizationCriteriaList.remove(oco);
	subSubMainPanel.remove(oco.transformIntoAPanel());
	userInterface.refreshPage();
    }

    @Override
    protected boolean areAllDataWellFilled() {
	for(OptimizationCriteriaObject oco : optimizationCriteriaList) {
	    oco.setDataType(dataType.getSelectedItem().toString());
	}
	if (optimizationCriteriaList.size() < 2) {
	    // XXX change message
	    JOptionPane.showMessageDialog(userInterface.getFrame(),
		    "In order to proceed, you need to declare at least two optimization criteria.",
		    "Optimization criteria", JOptionPane.ERROR_MESSAGE);
	    return false;
	}
	boolean answer = true;
	for (OptimizationCriteriaObject oco : optimizationCriteriaList) {
	    // separated to run the method
	    boolean var = oco.isWellFilled();
	    answer &= var;
	}
	return answer;
    }

    @Override
    protected void saveToProblem() {
	userInterface.isSingleobjective(optimizationCriteriaList.size() == 1);
	userInterface.setOptimizationCriteriaFromPage(optimizationCriteriaList);
    }

    private void setThisPage() {
	for(OptimizationCriteriaObject oco : userInterface.getOptimizationCriteriaFromPage()) {
	    oco.setPageAssociated(this);
	}
    }

    @Override
    protected void clearDataFromPage() {
	optimizationCriteriaList.clear();
	mainPanel.removeAll();
	subSubMainPanel.removeAll();
	createMainPanel();
    }

}