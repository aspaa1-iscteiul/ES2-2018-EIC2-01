package frames;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class KnownSolutionsObject {

    private KnownSolutionsPage page;
    private JTextField name;
    private JTextField solution1;
    private JTextField solution2;
    private ArrayList<JTextField> textfieldList;
    private JLabel addIcon;

    public KnownSolutionsObject(KnownSolutionsPage page, String string) {
	this.page = page;
	this.name = new JTextField(string);
	this.solution1 = new JTextField(3);
	this.solution2 = new JTextField(3);
	this.textfieldList = new ArrayList<JTextField>();
	this.textfieldList.add(solution1);
	this.textfieldList.add(solution2);
    }

    public JPanel transformIntoAPanel() {
	JPanel overallPanel = new JPanel();
	overallPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	overallPanel.setBackground(Color.WHITE);

	final JPanel firstPanel = new JPanel();
	firstPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	firstPanel.setBackground(Color.WHITE);
	final Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
	name.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(0, 10, 0, 10)));
	name.setPreferredSize(null);
	name.setEditable(false);
	firstPanel.add(name);

	for (JTextField textField : textfieldList) {
	    textField.setBorder(
		    BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(0, 10, 0, 10)));
	    firstPanel.add(textField);
	}

	overallPanel.add(firstPanel);

	JPanel addPanel = new JPanel();
	addPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	addPanel.setBackground(Color.WHITE);

	this.addIcon = new JLabel();
	this.addIcon.setIcon(new ImageIcon("./src/frames/images/add_icon.png"));
	this.addIcon.addMouseListener(new MouseListener() {
	    @Override
	    public void mouseClicked(MouseEvent arg0) {
		EventQueue.invokeLater(new Runnable() {
		    public void run() {
			JTextField newSolution = new JTextField(3);
			newSolution.setBorder(BorderFactory.createCompoundBorder(border,
				BorderFactory.createEmptyBorder(0, 10, 0, 10)));
			firstPanel.add(newSolution);
			textfieldList.add(newSolution);
			page.refreshPage();
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

	addPanel.add(addIcon);
	addPanel.add(new JLabel("Add new solutions"));
	overallPanel.add(addPanel);

	return overallPanel;
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

    public KnownSolutionsPage getPage() {
	return page;
    }

    public void setPage(KnownSolutionsPage page) {
	this.page = page;
    }

    public ArrayList<JTextField> getTextfieldList() {
	return textfieldList;
    }

    public void setTextfieldList(ArrayList<JTextField> textfieldList) {
	this.textfieldList = textfieldList;
    }

}