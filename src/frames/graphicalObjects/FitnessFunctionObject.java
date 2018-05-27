package frames.graphicalObjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import frames.FitnessFunctionPage;
import frames.frameUtils.FrameUtils;

/**
 * This object was created to aid the construction of the Fitness Function Page
 * and later to convert to the object Fitness Function
 */

public class FitnessFunctionObject {

    private FitnessFunctionPage pageAssociated;
    private JPanel fieldsPanel;
    private boolean jarFileUploaded;
    private JButton uploadButton;

    public static String newLine = System.getProperty("line.separator");

    /**
     * 
     * @param page
     */
    public FitnessFunctionObject(FitnessFunctionPage page) {
	this.pageAssociated = page;
	this.setJarFileUploaded(false);
	uploadButton = FrameUtils.cuteButton("Upload JAR file");
	uploadButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		if ((JOptionPane.showConfirmDialog(pageAssociated,
			"The JAR file chosen should behave according to the established evaluation protocol. " + newLine
				+ "That is, it should receive as input the name of the optimization criteria to be "
				+ newLine
				+ "calculated as well as the solutions vector and it should return as an output the "
				+ newLine + "value of the optimization criterion for this solution." + newLine,
			"Exchange protocol", JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.INFORMATION_MESSAGE) != JOptionPane.OK_OPTION))
		    return;

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Upload fitness function");
		// Launches the JFileChooser on the Desktop directory
		String user = System.getProperty("user.name"); // platform independent
		fileChooser.setCurrentDirectory(new File("C:\\Users\\" + user + "\\Desktop"));
		// Prevents selection of multiple options
		fileChooser.setMultiSelectionEnabled(false);
		// Only files with the JAR extension are visible
		fileChooser.setFileFilter(new FileNameExtensionFilter("JAR File", "jar"));
		fileChooser.setAcceptAllFileFilterUsed(false);
		if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {
		    uploadButton.setText(fileChooser.getSelectedFile().getAbsolutePath());
		    setJarFileUploaded(true);
		} else {
		    setJarFileUploaded(false);
		}
	    }
	});
	Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
	uploadButton
		.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(0, 10, 0, 10)));
	uploadButton.setPreferredSize(new Dimension(135, 22));
    }

    /**
     * Creates a Fitness Function Object with the data read from a XML file
     * 
     * @param page
     * @param filePath
     */
    public FitnessFunctionObject(FitnessFunctionPage page, String filePath) {
	this.pageAssociated = page;
	uploadButton = FrameUtils.cuteButton(filePath);
	uploadButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Upload fitness function");
		// Launches the JFileChooser on the Desktop directory
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
		// Prevents selection of multiple options
		fileChooser.setMultiSelectionEnabled(false);
		// Only files with the JAR extension are visible
		fileChooser.setFileFilter(new FileNameExtensionFilter("JAR File", "jar"));
		fileChooser.setAcceptAllFileFilterUsed(false);
		if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {
		    uploadButton.setText(fileChooser.getSelectedFile().getAbsolutePath());
		}
	    }
	});
	Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
	uploadButton
		.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(0, 10, 0, 10)));
	uploadButton.setPreferredSize(new Dimension(135, 22));

    }

    /**
     * Transforms the object in a JPanel that will be added to the frame later.
     * 
     * @return JPanel
     */
    public JPanel transformIntoAPanel() {
	JPanel overallPanel = new JPanel();
	overallPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	overallPanel.setBackground(Color.WHITE);

	fieldsPanel = new JPanel();
	fieldsPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	fieldsPanel.setBackground(Color.WHITE);
	createComponents();
	overallPanel.add(fieldsPanel);

	return overallPanel;
    }

    /**
     * Transforms the object in a JPanel that will be added to the frame later given
     * the data from the xml file. Also verifies if any optimization criteria from
     * the xml file was deleted of another optimization criteria was added
     * 
     * @return JPanel
     */
    public JPanel transformIntoAPanelWhenReadFromXML() {
	JPanel overallPanel = new JPanel();
	overallPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	overallPanel.setBackground(Color.WHITE);

	fieldsPanel = new JPanel();
	fieldsPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	fieldsPanel.setBackground(Color.WHITE);
	fieldsPanel.add(uploadButton);
	uploadButton.setText(getPath());
	jarFileUploaded = true;
	overallPanel.add(fieldsPanel);
	return overallPanel;
    }

    public FitnessFunctionPage getPageAssociated() {
	return pageAssociated;
    }

    public void setPageAssociated(FitnessFunctionPage pageAssociated) {
	this.pageAssociated = pageAssociated;
    }

    public String getPath() {
	return uploadButton.getText();
    }

    public JButton getUploadButton() {
	return uploadButton;
    }

    public boolean isJarFileUploaded() {
	return jarFileUploaded;
    }

    public void setJarFileUploaded(boolean jarFileUploaded) {
	this.jarFileUploaded = jarFileUploaded;
    }

    /**
     * Creates the checkboxes and adds them to the frame, analyzing if they should
     * be enabled or not
     */
    public void createComponents() {
	fieldsPanel.removeAll();
	fieldsPanel.add(uploadButton);
    }

}