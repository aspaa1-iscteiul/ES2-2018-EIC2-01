package utils;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class VerticalPanel extends JPanel {

    public VerticalPanel() {
	setPreferredSize(new Dimension(25, 0));
    }

    @Override
    public void paintComponent(Graphics g) {

	super.paintComponent(g);

	Graphics2D gg = (Graphics2D) g;
	gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	Font font = new Font("Arial", Font.PLAIN, 10);

	String string = "Value";

	FontMetrics metrics = g.getFontMetrics(font);
	int width = metrics.stringWidth(string);
	int height = metrics.getHeight();

	gg.setFont(font);

	drawRotate(gg, getWidth(), (getHeight() + width) / 2, 270, string);
    }

    public void drawRotate(Graphics2D gg, double x, double y, int angle, String text) {
	gg.translate((float) x, (float) y);
	gg.rotate(Math.toRadians(angle));
	gg.drawString(text, 0, 0);
	gg.rotate(-Math.toRadians(angle));
	gg.translate(-(float) x, -(float) y);
    }

}
