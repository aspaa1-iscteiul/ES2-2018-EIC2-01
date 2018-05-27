package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import frames.frameUtils.FrameUtils;

public class HomePage extends SuperPage {

    /**
     * Default
     */
    private static final long serialVersionUID = 1L;

    public HomePage(UserInterface userInterface) {
	super(userInterface);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void createMainPanel() {
	JLabel cover = new JLabel();
	cover.setIcon(new ImageIcon(getClass().getResource("/cover.jpg")));
	mainPanel.add(cover);
    }

    @Override
    protected void createButtonsPanel() {
	JButton adminButton = FrameUtils.cuteButton("Sign in as Admin");
	adminButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		userInterface.goToIntroPageAdmin();
	    }
	});
	buttonsPanel.add(adminButton);

	FrameUtils.addEmptyLabels(buttonsPanel, 35);

	JButton startButton = FrameUtils.cuteButton("Start");
	startButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		userInterface.goToNextPage();
	    }
	});
	buttonsPanel.add(startButton);

	// to add space between the two buttons
	buttonsPanel.add(new JLabel());

	JButton cancelButton = FrameUtils.cuteButton("Cancel");
	cancelButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		System.exit(0);
	    }
	});
	buttonsPanel.add(cancelButton);
    }

    @Override
    protected void onTop() {
	userInterface.getFrame().setTitle("ES2-2018-EIC2-01");
    }

    @Override
    protected boolean areAllDataWellFilled() {
	return true;
    }

    @Override
    protected void saveToProblem() {
	// TODO Auto-generated method stub
    }

    @Override
    protected void clearDataFromPage() {
	// TODO Auto-generated method stub
    }
}
