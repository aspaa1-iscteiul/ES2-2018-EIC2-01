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

import objects.Problem;

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

    /**
     * Method to initialize the attributes of the classes that extend this one
     */
    protected abstract void initialize();

    /**
     * Method to create the main part of the page to be viewed
     */
    protected abstract void createMainPanel();

    /**
     * Constructs the {@linkplain #buttonsPanel} with the back, cancel and next
     * buttons, if the class that extends this one wants different buttons
     * implements this method
     */
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

    /**
     * This method is called before the page is viewed which allows it to update
     * itself, for example if you need something that comes from a previous page
     */
    protected abstract void onTop();

    /**
     * This method is called before the
     * {@linkplain UserInterface#goToNextPage()} to prevent to go to the next
     * page without all the data are correct
     * 
     * @return {@code true} if all the data in the extended class are well
     *         filled, otherwise {@code false} and evidence the errors
     */
    protected abstract boolean areAllDataWellFilled();

    /**
     * Saves what needs to be saved in class {@link Problem}
     */
    protected abstract void saveToProblem();

    /**
     * 
     */
    protected abstract void getFromProblem();

}
