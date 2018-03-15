package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This object was created to aid the construction of the Fitness Function Page
 * and later to convert to the object Fitness Function
 */

public class FitnessFunctionObject {

    private FitnessFunctionPage pageAssociated;
    private JPanel fieldsPanel;
    private JButton uploadButton;
    private ArrayList<OptimizationCriteriaCheckbox> checkboxList;
    private JLabel warning;

    /**
     * 
     * @param page
     */
    public FitnessFunctionObject(FitnessFunctionPage page) {

	this.pageAssociated = page;
	this.checkboxList = new ArrayList<OptimizationCriteriaCheckbox>();
	uploadButton = FrameUtils.cuteButton("Upload JAR file");
	uploadButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Upload fitness function");
		// Launches the JFileChooser on the Desktop directory
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
		// Prevents selection of multiple options
		fileChooser.setMultiSelectionEnabled(false);
		// Only files with the JAR extension are visible
		fileChooser.setFileFilter(new FileNameExtensionFilter("JAR File", "jar"));
		fileChooser.setAcceptAllFileFilterUsed(false);
		if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {
		    uploadButton.setText(fileChooser.getSelectedFile().getAbsolutePath());
		}
	    }
	});
	Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
	uploadButton
	.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(0, 10, 0, 10)));
	uploadButton.setPreferredSize(new Dimension(135, 22));
	warning = new JLabel("No optimization criterias available");
    }

    /**
     * Transforms the object in a JPanel that will be added to the frame later.
     * 
     * @return JPanel
     */
    public JPanel transformIntoAPanel() {
	JPanel overallPanel = new JPanel();
	overallPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	overallPanel.setBackground(Color.WHITE);

	fieldsPanel = new JPanel();
	fieldsPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	fieldsPanel.setBackground(Color.WHITE);
	createComponents();
	overallPanel.add(fieldsPanel);

	return overallPanel;
    }

    public String getPath() {
	return uploadButton.getText();
    }

    public ArrayList<OptimizationCriteriaCheckbox> getCheckboxList() {
	return checkboxList;
    }

    public void setCheckboxList(ArrayList<OptimizationCriteriaCheckbox> checkboxList) {
	this.checkboxList = checkboxList;
    }

    /**
     * Creates the checkboxes and adds them to the frame, analyzing if they should
     * be enabled or not
     */
    public void createComponents() {
	if (pageAssociated.userInterface.getOptimizationCriteriaFromPage().size() > 0) {
	    fieldsPanel.removeAll();
	    fieldsPanel.add(uploadButton);
	    for (OptimizationCriteriaObject oco : pageAssociated.userInterface.getOptimizationCriteriaFromPage()) {
		final OptimizationCriteriaCheckbox checkbox = new OptimizationCriteriaCheckbox(oco.getVariableName());
		checkbox.getCheckBox().addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent arg0) {
			if (checkbox.getCheckBox().isSelected() == true) {
			    for (FitnessFunctionObject ffo : pageAssociated.getFitnessFunctionList()) {
				for (OptimizationCriteriaCheckbox occ : ffo.getCheckboxList()) {
				    if (occ.getOptimizationCriteriaName().getText()
					    .equals(checkbox.getOptimizationCriteriaName().getText())
					    && !occ.equals(checkbox)) {
					occ.getCheckBox().setEnabled(false);
				    }
				}
			    }
			} else {
			    for (FitnessFunctionObject ffo : pageAssociated.getFitnessFunctionList()) {
				for (OptimizationCriteriaCheckbox occ : ffo.getCheckboxList()) {
				    if (occ.getOptimizationCriteriaName().getText()
					    .equals(checkbox.getOptimizationCriteriaName().getText())
					    && !occ.equals(checkbox)) {
					occ.getCheckBox().setEnabled(true);
				    }
				}
			    }
			}
		    }
		});
		checkboxList.add(checkbox);
		fieldsPanel.add(checkbox.getOptimizationCriteriaName());
		fieldsPanel.add(checkbox.getCheckBox());
		verifyIfTheCheckboxShouldBeDisabled(checkbox);
	    }
	} else {
	    fieldsPanel.removeAll();
	    fieldsPanel.add(uploadButton);
	    warning.setForeground(Color.red);
	    fieldsPanel.add(warning);
	}
	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		pageAssociated.refreshPage();
	    }
	});
    }

    /**
     * Verify if the checkbox shoud be disabled because the value was already
     * selected on another checkbox
     * 
     * @param checkbox
     */
    public void verifyIfTheCheckboxShouldBeDisabled(OptimizationCriteriaCheckbox checkbox) {
	for (FitnessFunctionObject ffo : pageAssociated.getFitnessFunctionList()) {
	    for (OptimizationCriteriaCheckbox occ : ffo.getCheckboxList()) {
		if (occ.getOptimizationCriteriaName().getText().equals(checkbox.getOptimizationCriteriaName().getText())
			&& !occ.equals(checkbox)) {
		    if (occ.getCheckBox().isSelected() == true) {
			checkbox.getCheckBox().setEnabled(false);

		    }
		}
	    }
	}
    }

    /**
     * Cleans the selection of all the checkboxes
     */
    public void cleanData() {
	for (OptimizationCriteriaCheckbox occ : checkboxList) {
	    occ.getCheckBox().setEnabled(true);
	}
    }

    /**
     * Get the names of the checkboxes selected of a fitness function
     * @return
     */
    public ArrayList<String> getTheCheckboxesSelected() {
	ArrayList<String> tmp = new ArrayList<String>();
	for(OptimizationCriteriaCheckbox occ : checkboxList) {
	    if(occ.getCheckBox().isSelected()==true) {
		tmp.add(occ.getOptimizationCriteriaName().getText());
	    }
	}
	return tmp;
    }

}