package frames;

import java.awt.Color;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

/**
 * This object was created to aid the construction of the Optimization Criteria
 * Page and later to convert to the object Optimization Criteria
 */

public class OptimizationCriteriaCheckbox {

    private JLabel optimizationCriteriaName;
    private JCheckBox checkBox;

    /**
     * 
     * @param string
     */
    public OptimizationCriteriaCheckbox(String string) {
	this.optimizationCriteriaName = new JLabel(string);
	this.checkBox = new JCheckBox();
	this.checkBox.setBackground(Color.white);
    }

    public JLabel getOptimizationCriteriaName() {
	return optimizationCriteriaName;
    }

    public void setOptimizationCriteriaName(JLabel optimizationCriteriaName) {
	this.optimizationCriteriaName = optimizationCriteriaName;
    }

    public JCheckBox getCheckBox() {
	return checkBox;
    }

    public void setCheckBox(JCheckBox checkBox) {
	this.checkBox = checkBox;
    }

}
