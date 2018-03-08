package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

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

	name = new JTextField(10) {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void processKeyEvent(KeyEvent key) {
		super.processKeyEvent(key);
		isValidName();
	    }
	};

	dataType = FrameUtils.cuteComboBox(dataTypes);
	dataType.setSelectedItem(null);
	dataType.setEnabled(false);
	dataType.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		page.isAllOptimizationCriteriaWellFilled();
	    }
	});

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
	Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
	name.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(0, 10, 0, 10)));
	name.setPreferredSize(new Dimension(10, 22));
	variablesPanel.add(name);
	variablesPanel.add(dataType);
	variablesPanel.add(deleteIcon);
	return variablesPanel;
    }

    /**
     * Returns {@code true} if {@linkplain OptimizationCriteriaObject} have a
     * valid name and the {@linkplain #dataType} is selected, otherwise
     * {@code false}
     * 
     * @return {@code true} if {@linkplain OptimizationCriteriaObject} have a
     *         valid name and the {@linkplain #dataType} is selected,
     *         otherwise {@code false}
     * 
     * @see #isValidName()
     */
    public boolean isWellFilled() {
	return isValidName() && dataType.getSelectedIndex() >= 0;
    }

    /**
     * Returns {@code true} if the name is not empty and is not repeated, also
     * unlocks the {@linkplain #dataType}, otherwise places warnings and locks
     * the next button in the {@linkplain OptimizationCriteriaPage}
     * 
     * @return {@code true} if the name is not empty and is not repeated,
     *         otherwise {@code false}
     * 
     * @see OptimizationCriteriaPage#isNameRepeated(String)
     */
    private boolean isValidName() {
	String text = name.getText().trim();
	name.setText(text);
	if (text.equals("") || page.isNameRepeated(text)) {
	    dataType.setEnabled(false);
	    page.blockNextButton(true);
	    page.showWarning(page.isNameRepeated(text));
	    return false;
	}
	page.showWarning(false);
	dataType.setEnabled(true);
	if (dataType.getSelectedIndex() >= 0)
	    page.blockNextButton(false);
	return true;
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
