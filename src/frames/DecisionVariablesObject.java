package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class DecisionVariablesObject {

	private DecisionVariablesPage page;
	private JPanel variablesPanel;
	private JTextField name;
	private final static String[] dataTypes = {"Byte", "Short", "Integer", "Long", "Float", "Double", "Boolean"};
	private JComboBox<String> dataType;
	private JTextField lowerBound;
	private JTextField upperBound;
	private final static String[] numberSets = { "N (Natural)", "Z (Integers)", "Q (Rational)", "R (Real)"};
	private JComboBox<String> domain1;
	private final static String[] operations = {"  ", "U", "/"};
	private JComboBox<String> domain2;
	private JTextField domain3;
	private JLabel deleteIcon;

	public DecisionVariablesObject(DecisionVariablesPage page) {
		this.page = page;
		this.variablesPanel = new JPanel();
		this.name = new JTextField(5);
		this.dataType = FrameUtils.cuteComboBox(dataTypes);
		this.lowerBound = new JTextField(5);
		this.upperBound = new JTextField(5);
		this.domain1 = FrameUtils.cuteComboBox(numberSets);
		this.domain2 = FrameUtils.cuteComboBox(operations);
		this.domain3 = new JTextField(5);

		this.domain3.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				if(Pattern.matches("[,0-9]+", domain3.getText()) || domain3.getText().isEmpty()) {
				}else {
					domain3.setText(domain3.getText().substring(0, domain3.getText().length() - 1));
					JOptionPane.showMessageDialog(variablesPanel, "Only numbers or ','", "InvalidInput", JOptionPane.INFORMATION_MESSAGE);
				}
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});

		this.deleteIcon = new JLabel();
		this.deleteIcon.setIcon(new ImageIcon("./src/frames/images/delete_icon2.png"));

		DecisionVariablesObject tmp = this;

		this.deleteIcon.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						page.removeDecisionVariableFromList(tmp);
					}
				});
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
	}

	public JPanel transformIntoAPanel() {
		variablesPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		variablesPanel.setBackground(Color.WHITE);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
		name.setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(0, 10, 0, 10)));
		name.setPreferredSize(new Dimension(10, 22));
		lowerBound.setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(0, 10, 0, 10)));
		lowerBound.setPreferredSize(new Dimension(10, 22));
		upperBound.setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(0, 10, 0, 10)));
		upperBound.setPreferredSize(new Dimension(10, 22));
		domain3.setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(0, 10, 0, 10)));
		domain3.setPreferredSize(new Dimension(10, 22));
		variablesPanel.add(name);
		variablesPanel.add(dataType);
		variablesPanel.add(lowerBound);
		variablesPanel.add(upperBound);
		variablesPanel.add(domain1);
		variablesPanel.add(domain2);
		variablesPanel.add(domain3);
		variablesPanel.add(deleteIcon);
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

	public JTextField getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(JTextField lowerBound) {
		this.lowerBound = lowerBound;
	}

	public JTextField getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(JTextField upperBound) {
		this.upperBound = upperBound;
	}

	public JComboBox<String> getDomain1() {
		return domain1;
	}

	public void setDomain1(JComboBox<String> domain1) {
		this.domain1 = domain1;
	}

	public JComboBox<String> getDomain2() {
		return domain2;
	}

	public void setDomain2(JComboBox<String> domain2) {
		this.domain2 = domain2;
	}

	public JTextField getDomain3() {
		return domain3;
	}

	public void setDomain3(JTextField domain3) {
		this.domain3 = domain3;
	}

	public DecisionVariablesPage getPage() {
		return page;
	}

	public void setPage(DecisionVariablesPage page) {
		this.page = page;
	}	


}
