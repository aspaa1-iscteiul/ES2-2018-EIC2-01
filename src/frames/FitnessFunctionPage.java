package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

public class FitnessFunctionPage extends SuperPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int standardNumberOfVariables = 1;
	private ArrayList<FitnessFunctionObject> fitnessFunctionList;

	public FitnessFunctionPage(UserInterface userInterface) {
		super(userInterface);
		// TODO Auto-generated constructor stub
	}

	private JButton nextButton;

	@Override
	public void initialize() {
		nextButton = FrameUtils.cuteButton("Next");	
		fitnessFunctionList = new ArrayList<FitnessFunctionObject>();
	}

	@Override
	public void createMainPanel() {
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		// XXX change when frame size is set
		mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		titlePanel.setBackground(Color.WHITE);
		titlePanel.add(new JLabel("Fitness Function"));
		mainPanel.add(titlePanel);

		FrameUtils.addEmptyLabels(mainPanel, 1);

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BorderLayout());
		infoPanel.setBackground(Color.WHITE);
		JPanel iconPanel = new JPanel();
		iconPanel.setLayout(new BorderLayout());
		iconPanel.setBackground(Color.WHITE);
		JLabel infoIcon = new JLabel();
		infoIcon.setIcon(new ImageIcon("./src/frames/images/info_icon.png"));
		iconPanel.add(infoIcon, BorderLayout.NORTH);
		infoPanel.add(iconPanel, BorderLayout.WEST);
		JLabel infoLabel = new JLabel("<html>Be advised that the plataform assumes that in the optimization process, "
				+ "<br> optimization means minimizing the optimization criteria. Therefore, the <br>"
				+ "implementation(s) of the objective function(s) provided should be consistent<br>"
				+ "with this assumption.</html>");
		infoLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		infoLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
		infoPanel.add(infoLabel, BorderLayout.CENTER);

		mainPanel.add(infoPanel);

		FrameUtils.addEmptyLabels(mainPanel, 1);

		JPanel subMainPanel = new JPanel();
		subMainPanel.setBackground(Color.WHITE);
		subMainPanel.setLayout(new BoxLayout(subMainPanel, BoxLayout.Y_AXIS));
		subMainPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		JPanel infoFieldPanel = new JPanel();
		infoFieldPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		infoFieldPanel.setBackground(Color.WHITE);
		JLabel name = new JLabel("Fitness Function");
		name.setBorder(new EmptyBorder(0, 0, 0, 43)); //to add space between the labels
		infoFieldPanel.add(name);
		infoFieldPanel.add(new JLabel("Optimization Criteria"));

		subMainPanel.add(infoFieldPanel);


		JPanel subSubMainPanel = new JPanel();
		subSubMainPanel.setBackground(Color.WHITE);
		subSubMainPanel.setLayout(new BoxLayout(subSubMainPanel, BoxLayout.Y_AXIS));

		for(int i = 0; i != standardNumberOfVariables; i++) {
			FitnessFunctionObject fitnessFunctionObject = new FitnessFunctionObject(this);
			fitnessFunctionList.add(fitnessFunctionObject);
			subSubMainPanel.add(fitnessFunctionObject.transformIntoAPanel());
		}

		subMainPanel.add(subSubMainPanel);

		JPanel addOptionPanel = new JPanel();
		addOptionPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		addOptionPanel.setBackground(Color.WHITE);
		JLabel addIcon = new JLabel();
		addIcon.setIcon(new ImageIcon("./src/frames/images/add_icon.png"));

		FitnessFunctionPage tmp = this;

		addIcon.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				EventQueue.invokeLater(new Runnable()
				{
					public void run()
					{
						FitnessFunctionObject fitnessFunctionObject = new FitnessFunctionObject(tmp);
						fitnessFunctionList.add(fitnessFunctionObject);
						subSubMainPanel.add(fitnessFunctionObject.transformIntoAPanel());
						validate(); //to update window
						repaint(); //to update window
					}
				});
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

		});
		addOptionPanel.add(addIcon, BorderLayout.WEST);
		addOptionPanel.add(new JLabel("Add new fitness function"));

		subMainPanel.add(addOptionPanel);

		JScrollPane scrollPane = new JScrollPane(subMainPanel);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		mainPanel.add(scrollPane);

		FrameUtils.addEmptyLabels(mainPanel, 1);

	}

	@Override
	public void createButtonsPanel() {
		JButton backButton = FrameUtils.cuteButton("Back");
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				userInterface.goToPreviousPage();
			}
		});
		buttonsPanel.add(backButton);

		buttonsPanel.add(new JLabel()); // to add space between the two buttons

		JButton cancelButton = FrameUtils.cuteButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		buttonsPanel.add(cancelButton);

		buttonsPanel.add(new JLabel()); // to add space between the two buttons

		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				userInterface.goToNextPage();
			}
		});
		buttonsPanel.add(nextButton);
	}

	@Override
	public void onTop() {
		userInterface.getFrame().setTitle("Problem Solving App");
		for(FitnessFunctionObject ffo : fitnessFunctionList) {
					ffo.refreshAll();
		}
		refreshPage();
	}

	public void refreshPage() {
		userInterface.getFrame().validate();
		userInterface.getFrame().repaint();
	}


}
