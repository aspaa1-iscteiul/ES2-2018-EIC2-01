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

public class TimeConstraintsPage extends SuperPage {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField idealTime;
    private JTextField maxTime;
    private JButton nextButton;
    private boolean idealOk = false;
    private boolean maxOk = false;

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
    }

    @Override
    protected boolean areAllDataWellFilled() {
	Boolean tmp = false;
	if (idealTime.getText().trim().isEmpty()) {
	    FrameUtils.errorFormat(idealTime, "The ideal time must be filled in.");
	} else {
	    FrameUtils.normalFormat(idealTime);
	}
	try {
	    Integer.parseInt(idealTime.getText());
	    idealOk = true;
	    if (!maxTime.getText().trim().isEmpty()) {
		Integer.parseInt(maxTime.getText());
		maxOk = true;
		if (idealOk == true && maxOk == true
			&& (Integer.parseInt(idealTime.getText()) <= Integer.parseInt(maxTime.getText()))) {
		    tmp = true;
		    FrameUtils.normalFormat(idealTime);
		} else {
		    FrameUtils.errorFormat(idealTime, "The ideal time must be smaller than the max time.");
		}
	    } else {
		tmp = true;
	    }
	} catch (NumberFormatException e1) {
	    tmp = false;
	    try {
		Double.parseDouble(idealTime.getText());
		idealOk = true;
		if (!maxTime.getText().trim().isEmpty()) {
		    Double.parseDouble(maxTime.getText());
		    maxOk = true;
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
	    } catch (NumberFormatException e2) {
		tmp = false;
	    }
	}
	return tmp;
    }

    @Override
    protected void saveToProblem() {
	userInterface.getProblem().setIdealTimeFrame(Double.parseDouble(idealTime.getText()));
	try {
	    userInterface.getProblem().setMaxTimeFrame(Double.parseDouble(maxTime.getText()));
	} catch (NumberFormatException e) {
	    userInterface.getProblem().setMaxTimeFrame(Double.MAX_VALUE);
	}
    }

}
