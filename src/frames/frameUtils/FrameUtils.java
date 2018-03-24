package frames.frameUtils;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
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

    /**
     * Adds a red border and an <b>errorMessage</b> to the <b>jComponent</b>
     * 
     * @param jComponent
     *            The component where a red border and the <b>errorMessage</b>
     *            will be added
     * @param errorMessage
     *            The error message to display
     * @return {@code false} because the method is called when something is
     *         wrong
     * @see JComponent#setBorder(Border)
     * @see JComponent#setToolTipText(String)
     */
    // XXX change @return
    public static boolean errorFormat(JComponent jComponent, String errorMessage) {
	if (jComponent instanceof JCheckBox) {
	    jComponent.setForeground(Color.RED);
	} else {
	    int var = jComponent instanceof JComboBox ? 0 : 10;
	    jComponent.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.RED, 2),
		    BorderFactory.createEmptyBorder(0, var, 0, var)));
	}
	jComponent.setToolTipText(errorMessage);
	return false;
    }

    /**
     * Adds a black border to the <b>jComponent</b>, and removes an error
     * message if it has one
     * 
     * @param jComponent
     *            The component where a red border and the <b>errorMessage</b>
     *            will be added
     * @return {@code true} because the method is called when something is right
     * @see JComponent#setBorder(Border)
     * @see JComponent#setToolTipText(String)
     */
    // XXX change @return
    public static boolean normalFormat(JComponent jComponent) {
	if (jComponent instanceof JCheckBox) {
	    jComponent.setForeground(Color.BLACK);
	} else {
	    int var = jComponent instanceof JComboBox ? 0 : 10;
	    jComponent.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
		    BorderFactory.createEmptyBorder(0, var, 0, var)));
	}
	jComponent.setToolTipText(null);
	return true;
    }

    public static Font cuteFont(int size) {
	return new Font("Solid Edge ASNI Unicode", Font.PLAIN, size);
    }

    // XXX change @return
    public static boolean errorFormatOfString(JComponent jComponent, String errorMessage) {
	jComponent.setForeground(Color.RED);
	jComponent.setToolTipText(errorMessage);
	return false;
    }

    /**
     * Adds a black border to the <b>jComponent</b>, and removes an error
     * message if it has one
     * 
     * @param jComponent
     *            The component where a red border and the <b>errorMessage</b>
     *            will be added
     * @return {@code true} because the method is called when something is right
     * @see JComponent#setBorder(Border)
     * @see JComponent#setToolTipText(String)
     */
    // XXX change @return
    public static boolean normalFormatOfString(JComponent jComponent) {
	jComponent.setForeground(Color.BLACK);
	jComponent.setToolTipText(null);
	return true;
    }


}
