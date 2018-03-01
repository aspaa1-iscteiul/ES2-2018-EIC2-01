package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class HomePage extends SuperPage {

    public HomePage() {
	super("ES2-2018-EIC2-01");

	// add cover to main_panel
	JLabel cover = new JLabel();
	cover.setIcon(new ImageIcon("./src/frames/images/cover.jpg"));
	frame.add(cover, BorderLayout.CENTER);

	// create panel to add the buttons
	JPanel buttonsPanel = new JPanel();
	buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
	buttonsPanel.setBackground(Color.WHITE);
	buttonsPanel.setBorder(new EmptyBorder(0, 0, 5, 5));
	JButton startButton = FrameUtils.cuteButton("Start");
	startButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		// TODO go to IntroPage
	    }
	});
	buttonsPanel.add(startButton);
	buttonsPanel.add(new JLabel()); // to add space between the two buttons
	JButton cancelButton = FrameUtils.cuteButton("Cancel");
	cancelButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		frame.dispose();
	    }
	});
	buttonsPanel.add(cancelButton);
	frame.add(buttonsPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
	HomePage homePage = new HomePage();
	homePage.launch();
    }

}
