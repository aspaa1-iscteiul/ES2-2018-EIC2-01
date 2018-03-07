package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class HomePage extends SuperPage {

    /**
     * Default
     */
    private static final long serialVersionUID = 1L;

    public HomePage(UserInterface userInterface) {
	super(userInterface);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void createMainPanel() {
	JLabel cover = new JLabel();
	cover.setIcon(new ImageIcon("./src/frames/images/cover.jpg"));
	mainPanel.add(cover);
    }

    @Override
    public void createButtonsPanel() {
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
    public void onTop() {
	userInterface.getFrame().setTitle("ES2-2018-EIC2-01");
    }

}
