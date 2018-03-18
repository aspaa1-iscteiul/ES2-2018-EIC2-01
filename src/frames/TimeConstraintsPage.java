package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import frames.frameUtils.FrameUtils;

/**
 * This class represents the Time Constraints Page
 */

public class TimeConstraintsPage extends SuperPage {

    private static final long serialVersionUID = 1L;
    private JTextField idealTime;
    private JTextField maxTime;
    private JButton nextButton;
    private boolean idealOk = false;
    private boolean maxOk = false;

    /**
     * 
     * @param userInterface
     */
    public TimeConstraintsPage(UserInterface userInterface) {
	super(userInterface);
    }

    @Override
    protected void initialize() {
	// TODO Auto-generated method stub
	this.idealTime = new JTextField(5);
	this.maxTime = new JTextField(5);
	this.nextButton = FrameUtils.cuteButton("Next");
    }

    @Override
    protected void createMainPanel() {
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	// XXX change when frame size is set
	mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

	JPanel titlePanel = new JPanel(new BorderLayout());
	titlePanel.setBackground(Color.WHITE);
	JLabel titleLabel = new JLabel("Time constraints");
	titleLabel.setFont(FrameUtils.cuteFont(16));
	titlePanel.add(titleLabel, BorderLayout.WEST);
	mainPanel.add(titlePanel);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	JPanel infoPanel = new JPanel(new BorderLayout());
	infoPanel.setBackground(Color.WHITE);
	JPanel auxPanel = new JPanel(new BorderLayout());
	auxPanel.setBackground(Color.WHITE);
	JLabel infoIcon = new JLabel();
	infoIcon.setIcon(new ImageIcon("./src/frames/images/info_icon.png"));
	auxPanel.add(infoIcon, BorderLayout.NORTH);
	infoPanel.add(auxPanel, BorderLayout.WEST);
	JLabel infoLabel = new JLabel("<html>In this section you can set constraints for the algorithm's runtime."
		+ " To do so,<br>fill in the fields below with optimal and maximum time frames (in minutes) for"
		+ "<br> calculating an optimal solution.</html>");
	infoLabel.setFont(FrameUtils.cuteFont(12));
	infoLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
	infoPanel.add(infoLabel, BorderLayout.CENTER);
	mainPanel.add(infoPanel);

	FrameUtils.addEmptyLabels(mainPanel, 2);

	createTimesPanels();

	FrameUtils.addEmptyLabels(mainPanel, 1);
    }

    /**
     * Creates the panel with the fields to fill in the ideal time and max time
     */
    private void createTimesPanels() {
	JPanel auxPanel1 = new JPanel();
	auxPanel1.setBackground(Color.WHITE);
	JPanel idealPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	idealPanel.setBackground(Color.white);
	JLabel idealLabel = new JLabel("Ideal time frame:          ");
	idealLabel.setFont(FrameUtils.cuteFont(12));
	idealPanel.add(idealLabel);
	idealTime.setBorder(FrameUtils.cuteBorder());
	idealPanel.add(idealTime);
	JLabel minLabel1 = new JLabel("min");
	minLabel1.setFont(FrameUtils.cuteFont(12));
	idealPanel.add(minLabel1);
	auxPanel1.add(idealPanel);
	mainPanel.add(auxPanel1);

	JPanel auxPanel2 = new JPanel();
	auxPanel2.setBackground(Color.WHITE);
	JPanel maxPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	maxPanel.setBackground(Color.white);
	JLabel maxLabel = new JLabel("Maximum time frame: ");
	maxLabel.setFont(FrameUtils.cuteFont(12));
	maxPanel.add(maxLabel);
	maxTime.setBorder(FrameUtils.cuteBorder());
	maxPanel.add(maxTime);
	JLabel minLabel2 = new JLabel("min");
	minLabel2.setFont(FrameUtils.cuteFont(12));
	maxPanel.add(minLabel2);
	auxPanel2.add(maxPanel);
	mainPanel.add(auxPanel2);
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
	if(userInterface.isXmlFileWasImportedAtIndex(5)==true) {
	    if(userInterface.getProblem().getIdealTimeFrame()!=null) {
		idealTime.setText(userInterface.getProblem().getIdealTimeFrame());
	    }
	    if(userInterface.getProblem().getMaxTimeFrame()!=null) {
		maxTime.setText(userInterface.getProblem().getMaxTimeFrame());
	    }
	    userInterface.putXmlFileWasImportedFalseAtIndex(5);
	}
    }

    @Override
    protected boolean areAllDataWellFilled() {
	Boolean tmp = false;
	if (idealTime.getText().trim().isEmpty()) {
	    FrameUtils.errorFormat(idealTime, "The ideal time field is a mandatory entry field and therefore must be filled in.");
	} else {
	    FrameUtils.normalFormat(idealTime);
	}
	try {
	    Integer.parseInt(idealTime.getText());
	    if(Integer.parseInt(idealTime.getText()) > 0) {
		idealOk = true;  
		FrameUtils.normalFormat(idealTime);

		if (!maxTime.getText().trim().isEmpty()) {
		    Integer.parseInt(maxTime.getText());
		    if(Integer.parseInt(maxTime.getText()) > 0) {
			maxOk = true;  
			FrameUtils.normalFormat(maxTime);
		    } else {
			FrameUtils.errorFormat(maxTime,
				"The max time frame must be bigger than zero");
		    }
		    if (idealOk == true && maxOk == true
			    && (Integer.parseInt(idealTime.getText()) <= Integer.parseInt(maxTime.getText()))) {
			tmp = true;
			FrameUtils.normalFormat(idealTime);
		    } else {
			FrameUtils.errorFormat(idealTime,
				"The ideal time frame must be smaller than the maximum time frame.");
		    }
		} else {
		    tmp = true;
		}

	    } else {
		FrameUtils.errorFormat(idealTime,
			"The ideal time frame must be bigger than zero");
	    }
	} catch (NumberFormatException e1) {
	    tmp = false;
	    try {
		Double.parseDouble(idealTime.getText());
		if(Double.parseDouble(idealTime.getText()) > 0) {
		    idealOk = true;  
		    FrameUtils.normalFormat(idealTime);

		    if (!maxTime.getText().trim().isEmpty()) {
			Double.parseDouble(maxTime.getText());
			if( Double.parseDouble(maxTime.getText()) > 0) {
			    maxOk = true;  
			    FrameUtils.normalFormat(maxTime);
			} else {
			    FrameUtils.errorFormat(maxTime,
				    "The max time frame must be bigger than zero");
			}
			if (idealOk == true && maxOk == true
				&& (Double.parseDouble(idealTime.getText()) <= Double.parseDouble(maxTime.getText()))) {
			    tmp = true;
			    FrameUtils.normalFormat(idealTime);
			} else {
			    FrameUtils.errorFormat(idealTime, "The ideal time must be smaller than the max time.");
			}
		    } else {
			tmp = true;
		    }

		} else {
		    FrameUtils.errorFormat(idealTime,
			    "The ideal time frame must be bigger than zero");
		}
	    } catch (NumberFormatException e2) {
		tmp = false;
	    }
	}
	return tmp;
    }

    @Override
    protected void saveToProblem() {
	userInterface.getProblem().setIdealTimeFrame(idealTime.getText());
	try {
	    userInterface.getProblem().setMaxTimeFrame(maxTime.getText());
	} catch (NumberFormatException e) {
	    Double tmp = Double.MAX_VALUE;
	    userInterface.getProblem().setMaxTimeFrame(tmp.toString());
	}
    }
    
    @Override
    protected void clearDataFromPage() {
	idealTime.setText(null);
	maxTime.setText(null);
    }

}
