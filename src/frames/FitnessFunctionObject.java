package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class FitnessFunctionObject {

	private FitnessFunctionPage page;
	private JPanel fieldsPanel;
	private JButton uploadButton;
	private ArrayList<OptimizationCriteriaCheckbox> checkboxList;
	private JLabel warning;

	public FitnessFunctionObject(FitnessFunctionPage page){

		this.page = page;
		this.checkboxList = new ArrayList<OptimizationCriteriaCheckbox>();
		uploadButton = FrameUtils.cuteButton("Upload JAR file");
		uploadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("Upload fitness function");
				if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(null)) {
					uploadButton.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
		uploadButton.setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(0, 10, 0, 10)));
		uploadButton.setPreferredSize(new Dimension(135,22));
		warning = new JLabel("No optimization criterias available");
	}

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

	public ArrayList<OptimizationCriteriaCheckbox> getCheckboxList() {
		return checkboxList;
	}

	public void setCheckboxList(ArrayList<OptimizationCriteriaCheckbox> checkboxList) {
		this.checkboxList = checkboxList;
	}

	public void createComponents() {
		if(page.userInterface.getOptimizationCriteriaFromPage().size()>0) {
			fieldsPanel.removeAll();
			fieldsPanel.add(uploadButton);
			for(OptimizationCriteriaObject oco : page.userInterface.getOptimizationCriteriaFromPage()) {
				OptimizationCriteriaCheckbox checkbox = new OptimizationCriteriaCheckbox(oco.getName().getText());
				checkbox.getCheckBox().addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if(checkbox.getCheckBox().isSelected()==true) {
							for(FitnessFunctionObject ffo : page.getFitnessFunctionList()) {
								for(OptimizationCriteriaCheckbox occ : ffo.getCheckboxList()) {
									if(occ.getOptimizationCriteriaName().getText().equals(checkbox.getOptimizationCriteriaName().getText()) &&  !occ.equals(checkbox)) {
										occ.getCheckBox().setEnabled(false);
									}
								}
							}
						} else {
							for(FitnessFunctionObject ffo : page.getFitnessFunctionList()) {
								for(OptimizationCriteriaCheckbox occ : ffo.getCheckboxList()) {
									if(occ.getOptimizationCriteriaName().getText().equals(checkbox.getOptimizationCriteriaName().getText()) &&  !occ.equals(checkbox)) {
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
				page.refreshPage();				
			}
		});
	}

	public void verifyIfTheCheckboxShouldBeDisabled(OptimizationCriteriaCheckbox checkbox) {
		for(FitnessFunctionObject ffo : page.getFitnessFunctionList()) {
			for(OptimizationCriteriaCheckbox occ : ffo.getCheckboxList()) {
				if(occ.getOptimizationCriteriaName().getText().equals(checkbox.getOptimizationCriteriaName().getText()) &&  !occ.equals(checkbox)) {
					if(occ.getCheckBox().isSelected()==true) {
						checkbox.getCheckBox().setEnabled(false);
					
					}
				}
			}
		}
	}

	public void cleanData(){
		for(FitnessFunctionObject ffo : page.getFitnessFunctionList()) {
			for(OptimizationCriteriaCheckbox occ : ffo.getCheckboxList()) {
				occ.getCheckBox().setEnabled(true);
			}
		}
	}

}