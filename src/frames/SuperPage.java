package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public abstract class SuperPage extends JPanel {

    /**
     * Default
     */
    private static final long serialVersionUID = 1L;

    protected JPanel mainPanel, buttonsPanel;
    protected UserInterface userInterface;

    public SuperPage(UserInterface userInterface) {
	super();
	this.setBackground(Color.WHITE);
	this.setLayout(new BorderLayout());

	this.userInterface = userInterface;

	JPanel panel = new JPanel();
	panel.setLayout(new FlowLayout(FlowLayout.CENTER));
	panel.setBackground(Color.WHITE);
	mainPanel = new JPanel();
	mainPanel.setBackground(Color.WHITE);
	panel.add(mainPanel);
	this.add(panel, BorderLayout.CENTER);

	buttonsPanel = new JPanel();
	buttonsPanel.setBackground(Color.WHITE);
	buttonsPanel.setBorder(new EmptyBorder(0, 0, 5, 5));
	buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
	this.add(buttonsPanel, BorderLayout.SOUTH);

	initialize();
	createMainPanel();
	createButtonsPanel();
    }

    protected abstract void initialize();

    protected abstract void createMainPanel();

    protected abstract void createButtonsPanel();

    protected abstract void onTop();

    protected abstract boolean areAllDataWellFilled();

    protected abstract void saveToProblem();

    protected abstract void getFromProblem();

}
