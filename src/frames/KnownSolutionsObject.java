package frames;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class KnownSolutionsObject {

	private JTextField name;
	private JTextField solution1;
	private JTextField solution2;
	
	public KnownSolutionsObject(String string) {
		this.name = new JTextField(string);
		this.solution1 =  new JTextField(3);
		this.solution2 =  new JTextField(3);
	}
	
	public JPanel transformIntoAPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEADING));
		panel.setBackground(Color.WHITE);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
		name.setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(0, 10, 0, 10)));
		name.setPreferredSize(null);
		name.setEditable(false);
		solution1.setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(0, 10, 0, 10)));
		solution2.setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(0, 10, 0, 10)));
		panel.add(name);
		panel.add(solution1);
		panel.add(solution2);
		return panel;
	}

	public JTextField getName() {
		return name;
	}

	public void setName(JTextField name) {
		this.name = name;
	}

	public JTextField getSolution1() {
		return solution1;
	}

	public void setSolution1(JTextField solution1) {
		this.solution1 = solution1;
	}

	public JTextField getSolution2() {
		return solution2;
	}

	public void setSolution2(JTextField solution2) {
		this.solution2 = solution2;
	}
		
}