package utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class HorizontalPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public HorizontalPanel() {
	setPreferredSize(new Dimension(0, 50));
    }

    @Override
    public void paintComponent(Graphics g) {

	super.paintComponent(g);

	Graphics2D gg = (Graphics2D) g;
	gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	Font font = new Font("Arial", Font.PLAIN, 10);
	Font fontBold = new Font("Arial", Font.BOLD, 10);

	String string = "Number of runs";
	String stringCaption = "Caption:";
	String string2 = "Known Solutions";

	FontMetrics metrics = g.getFontMetrics(font);
	int width = metrics.stringWidth(string);
	gg.setFont(font);

	gg.drawString(string, (getWidth() - width) / 2, 11);
	
	gg.setFont(fontBold);
	gg.drawString(stringCaption, (getWidth() - width) / 2 , 33);
	
	gg.setFont(font);
	gg.setColor(new Color(60,179,113));
	gg.drawString(string2, (getWidth() - width) / 2 , 44);
    }

}
