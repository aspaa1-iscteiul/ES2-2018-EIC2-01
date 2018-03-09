package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SaveProblemPage extends SuperPage {

    private static final long serialVersionUID = 1L;
    private JTextField fileName;
    private JTextField filePath;
    private JButton saveButton;
    private JButton finishButton;
    private boolean nameOk = false;
    private boolean pathOk = false;
    
    public SaveProblemPage(UserInterface userInterface) {
	super(userInterface);
    }

    @Override
    protected void initialize() {
	this.fileName = new JTextField(30);
	this.filePath = new JTextField(30);
	this.saveButton = FrameUtils.cuteButton("Save");
	this.saveButton.setEnabled(false);
	this.finishButton = FrameUtils.cuteButton("Finish");
    }

    @Override
    protected void createMainPanel() {
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
	
	fileName.addKeyListener(new KeyListener() {
	    @Override
	    public void keyPressed(KeyEvent arg0) {
	    }

	    @Override
	    public void keyReleased(KeyEvent arg0) {
		if(!fileName.getText().trim().isEmpty()) {
		    nameOk = true;
		    if(nameOk==true && pathOk==true) {
			saveButton.setEnabled(true);
		    }
		}
		
	    }

	    @Override
	    public void keyTyped(KeyEvent arg0) {
	    }
	});
	
	namePanel.add(fileName, BorderLayout.CENTER);
	mainPanel.add(namePanel);
	
	FrameUtils.addEmptyLabels(mainPanel, 1);

	JPanel pathPanel = new JPanel();
	pathPanel.setLayout(new BorderLayout());
	pathPanel.setBackground(Color.WHITE);
	pathPanel.add(new JLabel("Path:            "), BorderLayout.WEST);
	filePath.setBorder(FrameUtils.cuteBorder());
	
	filePath.addKeyListener(new KeyListener() {
	    @Override
	    public void keyPressed(KeyEvent arg0) {
	    }

	    @Override
	    public void keyReleased(KeyEvent arg0) {
		if(!filePath.getText().trim().isEmpty()) {
		    //TODO: Rever se isto funciona, acho que não
	            try {
	                Paths.get(filePath.getText());
	                pathOk = true;
	            } catch (InvalidPathException | NullPointerException ex1) {
	                pathOk = false;
	            }
		    if(nameOk==true && pathOk==true) {
			saveButton.setEnabled(true);
		    }
		}
		
	    }

	    @Override
	    public void keyTyped(KeyEvent arg0) {
	    }
	});
	
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

	finishButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		System.out.println("Everything concluded");
	    }
	});
	buttonsPanel.add(finishButton);
    }

    @Override
    protected void onTop() {
	userInterface.getFrame().setTitle("Problem Solving App");
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

    @Override
    protected void getFromProblem() {
	// TODO Auto-generated method stub
	
    }

}
