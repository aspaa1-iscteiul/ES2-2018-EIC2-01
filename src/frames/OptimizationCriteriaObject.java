package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import objects.DataType;

/**
 * This object was created to aid the construction of the Optimization Criteria Page and later to convert to the object Decision Variable
 */

public class OptimizationCriteriaObject {

    private JTextField name;
    private String[] dataTypes = { "Integer", "Double" };
    private JComboBox<String> dataType;
    private OptimizationCriteriaPage page;
    private JLabel deleteIcon;
    private JPanel variablesPanel;

    public OptimizationCriteriaObject(OptimizationCriteriaPage ocp) {
	page = ocp;
	variablesPanel = new JPanel();
	name = new JTextField(10);
	dataType = FrameUtils.cuteComboBox(dataTypes);
	dataType.setSelectedItem(null);
	deleteIcon = new JLabel();
	deleteIcon.setIcon(new ImageIcon("./src/frames/images/delete_icon2.png"));
	final OptimizationCriteriaObject tmp = this;
	deleteIcon.addMouseListener(new MouseListener() {
	    @Override
	    public void mouseClicked(MouseEvent arg0) {
		EventQueue.invokeLater(new Runnable() {
		    public void run() {
			page.removeOptimizationCriteriaFromList(tmp);
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
	name.setBorder(FrameUtils.cuteBorder());
	name.setPreferredSize(new Dimension(10, 22));
	variablesPanel.add(name);
	variablesPanel.add(dataType);
	variablesPanel.add(deleteIcon);
	return variablesPanel;
    }

    /**
     * @return {@code true} if {@linkplain OptimizationCriteriaObject} have a
     *         valid name and the data type is selected, otherwise {@code false}
     * 
     * @see #isValidName()
     * @see #isDataTypeSelected()
     */
    public boolean isWellFilled() {
	// methods separated to run all
	boolean isValidName = isValidName(), isDataTypeSelected = isDataTypeSelected();
	return isValidName && isDataTypeSelected;
    }

    /**
     * @return {@code true} if the name is not empty and is not repeated,
     *         otherwise {@code false} and evidence the error
     * 
     * @see OptimizationCriteriaPage#isNameRepeated(String)
     */
    private boolean isValidName() {
	String text = name.getText();
	String info = (text.equals("") ? "The optimization criteria name must be filled in. " : "")
		+ (page.isNameRepeated(text) ? "The optimization criteria name must be unique." : "");
	return !info.equals("") ? FrameUtils.errorFormat(name, info) : FrameUtils.normalFormat(name);
    }

    /**
     * @return {@code true} if the {@link #dataType} has an item selected,
     *         otherwise {@code false} and evidence the error
     * @see FrameUtils#errorFormat(JComponent, String)
     */
    private boolean isDataTypeSelected() {
	return dataType.getSelectedItem() == null ? FrameUtils.errorFormat(dataType, "The data type must be filled in.")
		: FrameUtils.normalFormat(dataType);

    }

    public String getVariableName() {
	return name.getText();
    }

    public DataType getDataTypeToProblem() {
	if(dataType.getSelectedItem().toString().equals("Integer")){
	    return DataType.INTEGER;
	} else {
	    return DataType.DOUBLE;
	}
    }


}
