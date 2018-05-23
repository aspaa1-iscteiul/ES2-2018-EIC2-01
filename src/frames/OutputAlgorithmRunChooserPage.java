package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import frames.frameUtils.FrameUtils;
import utils.FileReader;

public class OutputAlgorithmRunChooserPage extends SuperPage{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ArrayList<String> testRunsForAlgorithm;
    private JPanel subPanel;
    private String algorithmName;

    public OutputAlgorithmRunChooserPage(UserInterface userInterface, String algorithmName) {
	super(userInterface);
	this.algorithmName = algorithmName;
    }

    @Override
    protected void initialize() {
	testRunsForAlgorithm = new ArrayList<String>();
    }

    @Override
    protected void createMainPanel() {
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	mainPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

	JPanel infoPanel = new JPanel(new FlowLayout());
	infoPanel.setBackground(Color.white);
	JLabel messageLabel = new JLabel("<html>The optimization process is <font color=green><b>complete.</b></font> "
		+ " Please <u>select one of the options</u> below to <br><font color=blue>view the results.</font></html>");
	messageLabel.setFont(FrameUtils.cuteFont(14));
	infoPanel.add(messageLabel);
	mainPanel.add(infoPanel);

	FrameUtils.addEmptyLabels(mainPanel, 2);

	subPanel = new JPanel();
	subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
	subPanel.setBackground(Color.white);
	subPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
		BorderFactory.createEmptyBorder(10, 10, 10, 10)));

	JScrollPane scrollPane = new JScrollPane(subPanel);
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	scrollPane.setPreferredSize(new Dimension(510, 240));
	mainPanel.add(scrollPane);
    }

    @Override
    protected void createButtonsPanel() {
	JButton cancelButton = FrameUtils.cuteButton("Cancel");
	cancelButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		System.exit(0);
	    }
	});
	buttonsPanel.add(cancelButton);
    }

    @Override
    protected void onTop() {
	userInterface.getFrame().setTitle(algorithmName);
	testRunsForAlgorithm.clear();
	FileReader fileReader = new FileReader();
	int numberOfRuns = fileReader.readFileAndReturnListInRunPerspective(new File(System.getProperty("user.dir")+"/src/utils/valuesTest.txt")).size();
	for(int i = 0; i != numberOfRuns; i++) {
	    testRunsForAlgorithm.add("Run " + i);
	}
	constructPage();
    }

    @Override
    protected boolean areAllDataWellFilled() {
	// TODO Auto-generated method stub
	return true;
    }

    @Override
    protected void saveToProblem() {
	// TODO Auto-generated method stub
    }

    @Override
    protected void clearDataFromPage() {
    }

    private void constructPage() {
	subPanel.removeAll();
	int numberOfLines = 0;
	if((testRunsForAlgorithm.size())%3==0){
	    numberOfLines = (testRunsForAlgorithm.size())/3;
	} else { 
	    numberOfLines = ((testRunsForAlgorithm.size())/3)+1;
	}

	for(int i = 0; i != numberOfLines; i++ ) {
	    int count = 3;
	    JPanel auxPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	    auxPanel.setBackground(Color.white);
	    while(count > 0) {
		if(testRunsForAlgorithm.size() > 0 ) {
		    String str = testRunsForAlgorithm.remove(0);
		    String[] splittedStr = str.split("Run ");
		    int runNumber = Integer.parseInt(splittedStr[1]);
		    JButton button = FrameUtils.cuteButton(str);
		    button.setPreferredSize(new Dimension(140,30));
		    button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			   userInterface.goToOutputAlgorithmRunPage(algorithmName, runNumber);
			}

		    });
		    auxPanel.add(button);
		    count--;
		} else {
		    break;
		}
	    }
	    subPanel.add(auxPanel);
	    FrameUtils.addEmptyLabels(subPanel, 1);
	}
    }

}
