package frames;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class FrameUtils {

    public static JButton cuteButton(String textOnButton) {
	JButton button = new JButton(textOnButton);
	button.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
	button.setBackground(Color.WHITE);
	button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
		BorderFactory.createEmptyBorder(0, 10, 0, 10)));
	button.setFocusPainted(false); // disables highlight
	return button;
    }

    public static JComboBox<String> cuteComboBox(String[] content) {
	JComboBox<String> comboBox = new JComboBox<String>(content);
	comboBox.setBackground(Color.WHITE);
	comboBox.setUI(new BasicComboBoxUI());
	comboBox.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
		BorderFactory.createEmptyBorder(0, 0, 0, 0)));
	return comboBox;
    }

    public static void addEmptyLabels(JPanel panel, int times) {
	for (int i = 0; i < times; i++)
	    panel.add(new JLabel(" "));
    }

    public static Border cuteBorder() {
	return BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
		BorderFactory.createEmptyBorder(0, 10, 0, 10));
    }

}
