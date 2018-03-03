package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class OptimizationCriteriaObject {

	private JTextField name;
	private final static String[] dataTypes = { "Byte", "Short", "Integer", "Long", "Float", "Double", "Boolean", "Char" };
	private JComboBox<String> dataType;

	public OptimizationCriteriaObject() {
		this.name = new JTextField(10);
		this.dataType = FrameUtils.cuteComboBox(dataTypes);
	}

	public JPanel transformIntoAPanel() {
		JPanel variablesPanel = new JPanel();
		variablesPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		variablesPanel.setBackground(Color.WHITE);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
		name.setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(0, 10, 0, 10)));
		name.setPreferredSize(new Dimension(10, 22));
		variablesPanel.add(name);
		variablesPanel.add(dataType);
		return variablesPanel;
	}

	public JTextField getName() {
		return name;
	}

	public void setName(JTextField name) {
		this.name = name;
	}

	public JComboBox<String> getDataType() {
		return dataType;
	}

	public void setDataType(JComboBox<String> dataType) {
		this.dataType = dataType;
	}



}
