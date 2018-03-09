package frames;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

public class KnownSolutionsPage extends SuperPage {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel subSubMainPanel;
    private JButton nextButton;

    public KnownSolutionsPage(UserInterface userInterface) {
	super(userInterface);
	// TODO Auto-generated constructor stub
    }

    @Override
    protected void initialize() {
	nextButton = FrameUtils.cuteButton("Next");
    }

    @Override
    protected void createMainPanel() {
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	// XXX change when frame size is set
	mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

	JPanel titlePanel = new JPanel();
	titlePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	titlePanel.setBackground(Color.WHITE);
	titlePanel.add(new JLabel("Known Solution(s)"));
	mainPanel.add(titlePanel);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	JPanel subMainPanel = new JPanel();
	subMainPanel.setBackground(Color.WHITE);
	subMainPanel.setLayout(new BoxLayout(subMainPanel, BoxLayout.Y_AXIS));
	subMainPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
		BorderFactory.createEmptyBorder(10, 10, 10, 10)));

	JPanel infoPanel = new JPanel();
	infoPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	infoPanel.setBackground(Color.WHITE);
	JLabel name = new JLabel("Name");
	name.setBorder(new EmptyBorder(0, 0, 0, 45)); // to add space between the labels
	infoPanel.add(name);
	infoPanel.add(new JLabel("Solution(s)"));

	subMainPanel.add(infoPanel);

	subSubMainPanel = new JPanel();
	subSubMainPanel.setBackground(Color.WHITE);
	subSubMainPanel.setLayout(new BoxLayout(subSubMainPanel, BoxLayout.Y_AXIS));

	subMainPanel.add(subSubMainPanel);

	JScrollPane scrollPane = new JScrollPane(subMainPanel);
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

	mainPanel.add(scrollPane);

	FrameUtils.addEmptyLabels(mainPanel, 1);

    }

    @Override
    protected void createButtonsPanel() {
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
    protected void onTop() {
	userInterface.getFrame().setTitle("Problem Solving App");
	JPanel warning = warningPanel();
	subSubMainPanel.removeAll();
	if (userInterface.getKnownSolutionsList().size() > 0) {
	    for (KnownSolutionsObject kso : userInterface.getKnownSolutionsList()) {
		kso.setPage(this);
		subSubMainPanel.add(kso.transformIntoAPanel());
	    }
	} else {
	    subSubMainPanel.add(warning);
	}
	validate();
	repaint();
    }

    /**
     * Creates a warning Panel annoucing that no variables were created
     * @return
     */
    private JPanel warningPanel() {
	JPanel panel = new JPanel();
	panel.setLayout(new FlowLayout(FlowLayout.LEADING));
	panel.setBackground(Color.WHITE);
	JLabel warning = new JLabel("No variables created");
	warning.setForeground(Color.red);
	panel.add(warning);
	return panel;
    }

    public void refreshPage() {
	userInterface.getFrame().validate();
	userInterface.getFrame().repaint();
    }

    @Override
    protected boolean areAllDataWellFilled() {
	boolean tmp = true;
	for (KnownSolutionsObject kso : userInterface.getKnownSolutionsList()) {
	    for(JTextField textField : kso.getTextfieldList()) {
		if(kso.validateInputs(textField) == false) {
		    tmp = false;
		}
	    }
	}
	return tmp;
    }

    @Override
    protected void saveToProblem() {
	// TODO Auto-generated method stub

    }

    @Override
    protected void getFromProblem() {
	// TODO Auto-generated method stub

    }

}