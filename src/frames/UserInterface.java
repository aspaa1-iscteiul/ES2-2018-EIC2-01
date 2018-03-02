package frames;

import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class UserInterface {

    private JFrame frame;
    private List<SuperPage> pages;
    private int actualPageIndex = 0;

    public UserInterface() {
	frame = new JFrame();

	pages = new ArrayList<>();
	pages.add(new HomePage(this));
//	pages.add(new IntroPage(this));
    }
    
    public JFrame getFrame() {
	return frame;
    }

    public void nextPage() {
	frame.remove(pages.get(actualPageIndex));
	frame.add(pages.get(++actualPageIndex));
	frame.pack(); // XXX remove when width and height is set
    }

    public void backPage() {
	frame.remove(pages.get(actualPageIndex));
	frame.add(pages.get(--actualPageIndex));
    }

    private void launch() {
	frame.pack(); // XXX change to frame.setSize(width, height);
	frame.setLocation(new Point((Toolkit.getDefaultToolkit().getScreenSize().width - frame.getWidth()) / 2,
		(Toolkit.getDefaultToolkit().getScreenSize().height - frame.getHeight()) / 2));
	frame.setVisible(true);
    }

    public static void main(String[] args) {
	UserInterface user = new UserInterface();
	user.launch();
    }

}
