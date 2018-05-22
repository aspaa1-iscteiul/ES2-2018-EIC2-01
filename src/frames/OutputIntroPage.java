package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class OutputIntroPage extends SuperPage{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ArrayList<String> testAlgorithms;
    private JPanel subPanel;

    public OutputIntroPage(UserInterface userInterface) {
	super(userInterface);
    }

    @Override
    protected void initialize() {
	testAlgorithms = new ArrayList<String>();
    }

    @Override
    protected void createMainPanel() {
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	mainPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

	JPanel infoPanel = new JPanel(new FlowLayout());
	infoPanel.setBackground(Color.white);
	JLabel messageLabel = new JLabel("<html>The optimization process is <font color=green><b>complete.</b></font> "
		+ " Please <u>select one of the options</u> below to <br><font color=blue>view results.</font></html>");
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
	userInterface.getFrame().setTitle("Output Intro Page");
	testAlgorithms.clear();
	if(userInterface.getOptimizationAlgorithmsFromPage()!= null) {
	    for(String str : userInterface.getOptimizationAlgorithmsFromPage()) {
		testAlgorithms.add(str);
	    }
	}
	testAlgorithms.add("Known Solutions");
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
	if((testAlgorithms.size())%3==0){
	    numberOfLines = (testAlgorithms.size())/3;
	} else { 
	    numberOfLines = ((testAlgorithms.size())/3)+1;
	}

	for(int i = 0; i != numberOfLines; i++ ) {
	    int count = 3;
	    JPanel auxPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	    auxPanel.setBackground(Color.white);
	    while(count > 0) {
		if(testAlgorithms.size() > 0 ) {
		    JButton button = FrameUtils.cuteButton(testAlgorithms.remove(0));
		    button.setPreferredSize(new Dimension(140,30));
		    button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
			    userInterface.goToOutputAlgorithmPage();
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
