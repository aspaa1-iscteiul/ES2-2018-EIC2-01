package frames;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class UserInterface {

	private JFrame frame;
	private List<SuperPage> pages;
	private int actualPageIndex = 0;

	public UserInterface() {
		frame = new JFrame("ES2-2018-EIC2-01");
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}
		});

		pages = new ArrayList<>();
		pages.add(new HomePage(this));
		pages.add(new IntroPage(this));
		pages.add(new RegisterUserPage(this));
	}

	public JFrame getFrame() {
		return frame;
	}

	public void goToNextPage() {
		frame.remove(pages.get(actualPageIndex));
		SuperPage page = pages.get(++actualPageIndex);
		page.onTop();
		frame.add(page);
		frame.pack(); // XXX remove when width and height is set
	}

	public void goToPreviousPage() {
		frame.remove(pages.get(actualPageIndex));
		SuperPage page = pages.get(--actualPageIndex);
		page.onTop();
		frame.add(page);
		frame.pack(); // XXX remove when width and height is set
	}

	private void launch() {
		frame.add(pages.get(actualPageIndex));

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
