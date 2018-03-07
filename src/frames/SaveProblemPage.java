package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SaveProblemPage extends SuperPage {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField fileName;
    private JTextField filePath;
    private JButton saveButton;
    private JButton finishButton;

    public SaveProblemPage(UserInterface userInterface) {
	super(userInterface);
    }

    @Override
    public void initialize() {
	this.fileName = new JTextField(30);
	this.filePath = new JTextField(30);
	this.saveButton = FrameUtils.cuteButton("Save");
	this.finishButton = FrameUtils.cuteButton("Finish");
    }

    @Override
    public void createMainPanel() {
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	// XXX change when frame size is set
	mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
	mainPanel.setBackground(Color.white);

	JPanel infoPanel = new JPanel();
	infoPanel.setLayout(new BorderLayout());
	infoPanel.setBackground(Color.WHITE);
	infoPanel.add(new JLabel("Save this problem's configuration before submitting it to evaluation"), BorderLayout.WEST);
	mainPanel.add(infoPanel);
	
	FrameUtils.addEmptyLabels(mainPanel, 1);

	JPanel namePanel = new JPanel();
	namePanel.setLayout(new BorderLayout());
	namePanel.setBackground(Color.WHITE);
	namePanel.add(new JLabel("File name:   "), BorderLayout.WEST);
	fileName.setBorder(FrameUtils.cuteBorder());
	namePanel.add(fileName, BorderLayout.CENTER);
	mainPanel.add(namePanel);
	
	FrameUtils.addEmptyLabels(mainPanel, 1);

	JPanel pathPanel = new JPanel();
	pathPanel.setLayout(new BorderLayout());
	pathPanel.setBackground(Color.WHITE);
	pathPanel.add(new JLabel("Path:            "), BorderLayout.WEST);
	filePath.setBorder(FrameUtils.cuteBorder());
	pathPanel.add(filePath, BorderLayout.CENTER);
	mainPanel.add(pathPanel);
	
	FrameUtils.addEmptyLabels(mainPanel, 2);
	
	JPanel savePanel = new JPanel();
	savePanel.setLayout(new BorderLayout());
	savePanel.setBackground(Color.WHITE);
	savePanel.add(saveButton, BorderLayout.WEST);
	mainPanel.add(savePanel);

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

	finishButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		System.out.println("Everything concluded");
	    }
	});
	buttonsPanel.add(finishButton);
    }

    @Override
    public void onTop() {
	userInterface.getFrame().setTitle("Problem Solving App");
    }

}
