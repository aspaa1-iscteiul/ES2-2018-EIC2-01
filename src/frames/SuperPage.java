package frames;

import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;

public abstract class SuperPage {

    public static final int WIDTH = 0, HEIGHT = 0;

    protected JFrame frame;

    public SuperPage(String frameTitle) {
	frame = new JFrame(frameTitle);
    }

    public void launch() {
	frame.pack();
	frame.setLocation(new Point((Toolkit.getDefaultToolkit().getScreenSize().width - frame.getWidth()) / 2,
		(Toolkit.getDefaultToolkit().getScreenSize().height - frame.getHeight()) / 2));
	frame.setVisible(true);
    }

}
