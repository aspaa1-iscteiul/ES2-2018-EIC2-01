package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
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
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class OptimizationCriteriaPage extends SuperPage {

    /**
     * Default
     */
    private static final long serialVersionUID = 1L;

    private ArrayList<OptimizationCriteriaObject> optimizationCriteriaList;
    private JPanel warningPanel, subSubMainPanel;
    private JButton nextButton;

    public OptimizationCriteriaPage(UserInterface userInterface) {
	super(userInterface);
    }

    @Override
    public void initialize() {
	nextButton = FrameUtils.cuteButton("Next");
	blockNextButton(true);
	optimizationCriteriaList = new ArrayList<OptimizationCriteriaObject>();

	warningPanel = new JPanel();
	warningPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	warningPanel.setBackground(Color.WHITE);
	JLabel warningIcon = new JLabel();
	warningIcon.setIcon(new ImageIcon("./src/frames/images/warning_icon.png"));
	warningPanel.add(warningIcon);
	JLabel warning = new JLabel("Optimization criterias must have unique names");
	warning.setForeground(Color.red);
	warningPanel.add(warning);
    }

    @Override
    public void createMainPanel() {
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	// XXX change when frame size is set
	mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

	JPanel titlePanel = new JPanel();
	titlePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	titlePanel.setBackground(Color.WHITE);
	titlePanel.add(new JLabel("Optimization Criteria"));
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
	// to add space between the labels
	name.setBorder(new EmptyBorder(0, 0, 0, 100));

	infoPanel.add(name);
	infoPanel.add(new JLabel("Data Type"));

	subMainPanel.add(infoPanel);

	subSubMainPanel = new JPanel();
	subSubMainPanel.setBackground(Color.WHITE);
	subSubMainPanel.setLayout(new BoxLayout(subSubMainPanel, BoxLayout.Y_AXIS));

	OptimizationCriteriaObject optimizationCriteria = new OptimizationCriteriaObject(this);
	optimizationCriteriaList.add(optimizationCriteria);
	subSubMainPanel.add(optimizationCriteria.transformIntoAPanel());

	subMainPanel.add(subSubMainPanel);

	continuateConstructor(subMainPanel);
    }

    private void continuateConstructor(JPanel subMainPanel) {
	JPanel addOptionPanel = new JPanel();
	addOptionPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	addOptionPanel.setBackground(Color.WHITE);
	JLabel addIcon = new JLabel();
	addIcon.setIcon(new ImageIcon("./src/frames/images/add_icon.png"));

	final OptimizationCriteriaPage tmp = this;
	addIcon.addMouseListener(new MouseListener() {
	    @Override
	    public void mouseClicked(MouseEvent arg0) {
		EventQueue.invokeLater(new Runnable() {
		    public void run() {
			blockNextButton(true);
			OptimizationCriteriaObject optimizationCriteria = new OptimizationCriteriaObject(tmp);
			optimizationCriteriaList.add(optimizationCriteria);
			subSubMainPanel.add(optimizationCriteria.transformIntoAPanel());
			validate(); // to update window
			repaint(); // to update window
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
	addOptionPanel.add(new JLabel("Add new criteria"));

	subMainPanel.add(addOptionPanel);

	JScrollPane scrollPane = new JScrollPane(subMainPanel);
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

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
		userInterface.isSingleobjective(optimizationCriteriaList.size() == 1);
		userInterface.goToNextPage();
	    }
	});
	buttonsPanel.add(nextButton);
    }

    @Override
    public void onTop() {
	userInterface.getFrame().setTitle("Problem Solving App");
    }

    private void refreshPage() {
	userInterface.getFrame().validate();
	userInterface.getFrame().repaint();
	userInterface.getFrame().pack();
    }

    public void blockNextButton(boolean b) {
	nextButton.setEnabled(!b);
    }

    public boolean isNameRepeated(String varName) {
	int count = 0;
	for (OptimizationCriteriaObject oco : optimizationCriteriaList)
	    if (!oco.getVariableName().equals("") && oco.getVariableName().equals(varName))
		count++;
	return count >= 2;
    }

    public void showWarning(final boolean show) {
	SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		if (show)
		    mainPanel.add(warningPanel);
		else
		    mainPanel.remove(warningPanel);
		refreshPage();
	    }
	});
    }

    public void removeOptimizationCriteriaFromList(OptimizationCriteriaObject oco) {
	optimizationCriteriaList.remove(oco);
	subSubMainPanel.remove(oco.transformIntoAPanel());

	isAllOptimizationCriteriaWellFilled();

	refreshPage();
    }

    public void isAllOptimizationCriteriaWellFilled() {
	for (OptimizationCriteriaObject oco : optimizationCriteriaList)
	    if (!oco.isWellFilled())
		return;
    }

}