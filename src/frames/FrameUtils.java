package frames;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class FrameUtils {

    public static JButton cuteButton(String textOnButton) {
	JButton button = new JButton(textOnButton);
	button.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
	button.setBackground(Color.WHITE);
	button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
		BorderFactory.createEmptyBorder(0, 10, 0, 10)));
	button.setFocusPainted(false); // disables highlight
	// button.setBorder(
	// BorderFactory.createCompoundBorder(
	// BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE,
	// Color.BLACK),
	// BorderFactory.createCompoundBorder(
	// BorderFactory.createLineBorder(Color.BLACK,3),
	// BorderFactory.createEmptyBorder(0, 10, 0, 10))));
	return button;
    }

}
