package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
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

    protected void createButtonsPanel() {
	JButton backButton = FrameUtils.cuteButton("Back");
	backButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		userInterface.goToPreviousPage();
	    }
	});
	buttonsPanel.add(backButton);

	buttonsPanel.add(new JLabel()); // to add space between the two buttons

	JButton cancelButton = FrameUtils.cuteButton("Cancel");
	cancelButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		System.exit(0);
	    }
	});
	buttonsPanel.add(cancelButton);

	buttonsPanel.add(new JLabel()); // to add space between the two buttons

	JButton nextButton = FrameUtils.cuteButton("Next");
	nextButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		userInterface.goToNextPage();
	    }
	});
	buttonsPanel.add(nextButton);
    }

    protected abstract void onTop();

    protected abstract boolean areAllDataWellFilled();

    protected abstract void saveToProblem();

    protected abstract void getFromProblem();

}
