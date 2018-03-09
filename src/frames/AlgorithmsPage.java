package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

public class AlgorithmsPage extends SuperPage {

    /**
     * Default
     */
    private static final long serialVersionUID = 1L;

    private static final File jMetalMultiobjectiveAlgorithmsDir = new File(
	    "./jMetal/jmetal-algorithm/src/main/java/org/uma/jmetal/algorithm/multiobjective"),
	    jMetalSingleobjectiveAlgorithmsDir = new File(
		    "./jMetal/jmetal-algorithm/src/main/java/org/uma/jmetal/algorithm/singleobjective");
    private JLabel title;
    private JPanel algorithmsListPanel;
    private JButton nextButton;
    private ArrayList<JCheckBox> algorithmsList;

    public AlgorithmsPage(UserInterface userInterface) {
	super(userInterface);
    }

    @Override
    protected void initialize() {
	title = new JLabel();
	algorithmsListPanel = new JPanel();
	nextButton = FrameUtils.cuteButton("Next");
	algorithmsList = new ArrayList<>();
    }

    @Override
    protected void createMainPanel() {
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

	title.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
	mainPanel.add(title);

	algorithmsListPanel.setLayout(new BoxLayout(algorithmsListPanel, BoxLayout.Y_AXIS));
	algorithmsListPanel.setBackground(Color.WHITE);
	algorithmsListPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
	JScrollPane scrollPane = new JScrollPane(algorithmsListPanel);
	scrollPane.setBackground(Color.WHITE);
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	// TODO remove when frame size is set
	scrollPane.setPreferredSize(new Dimension(200, 200));
	scrollPane.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(20, 40, 10, 10),
		BorderFactory.createLineBorder(Color.BLACK, 2)));

	mainPanel.add(scrollPane);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	JButton preselectionButton = FrameUtils.cuteButton("Preselection");
	mainPanel.add(preselectionButton);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	JButton automaticSelectionButton = FrameUtils.cuteButton("Automatic Selection");
	mainPanel.add(automaticSelectionButton);
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

	nextButton.setEnabled(false);
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
	title.setText((userInterface.getIsSingleobjective() ? "Single objective" : "Multi objective")
		+ " optimization algorithms available");
	File dir = userInterface.getIsSingleobjective() ? jMetalSingleobjectiveAlgorithmsDir
		: jMetalMultiobjectiveAlgorithmsDir;

	for (File file : dir.listFiles())
	    algorithmsList.add(new JCheckBox(file.getName()));

	for (JCheckBox checkBox : algorithmsList) {
	    checkBox.setBackground(Color.WHITE);
	    algorithmsListPanel.add(checkBox);
	}
	algorithmsListPanel.repaint();
	algorithmsListPanel.revalidate();
    }

    @Override
    protected boolean areAllDataWellFilled() {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    protected void saveToProblem() {
	// TODO Auto-generated method stub
	
    }

}
