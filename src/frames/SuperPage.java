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

	mainPanel = new JPanel();
	mainPanel.setBackground(Color.WHITE);
	this.add(mainPanel, BorderLayout.CENTER);

	buttonsPanel = new JPanel();
	buttonsPanel.setBackground(Color.WHITE);
	buttonsPanel.setBorder(new EmptyBorder(0, 0, 5, 5));
	buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
	this.add(buttonsPanel, BorderLayout.SOUTH);
	
	createMainPanel();
	createButtonsPanel();
	this.userInterface.getFrame().add(this);
    }

    public abstract void createMainPanel();

    public abstract void createButtonsPanel();

}
