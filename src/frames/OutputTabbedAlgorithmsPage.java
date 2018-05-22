package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import frames.frameUtils.FrameUtils;
import utils.FileReader;

public class OutputTabbedAlgorithmsPage extends SuperPage {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel subPanel, subPanel2, first_run_panel;

    public OutputTabbedAlgorithmsPage(UserInterface userInterface) {
	super(userInterface);
    }

    @Override
    protected void initialize() {
	userInterface.getFrame().setTitle("Algorithm");
    }

    @Override
    protected void createMainPanel() {
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	mainPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

	// TODO Added line:
	mainPanel.add(addJTabbedPane(), BorderLayout.CENTER);

	// JPanel infoPanel = new JPanel();
	// infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
	// infoPanel.setBackground(Color.white);
	// JLabel decisionVariableLabel = new JLabel("Instantiated decision variables
	// ");
	// decisionVariableLabel.setFont(FrameUtils.cuteFont(14));
	// infoPanel.add(decisionVariableLabel);
	// JLabel optimizationLabel = new JLabel("Resulting Optimization Criteria");
	// optimizationLabel.setFont(FrameUtils.cuteFont(14));
	// infoPanel.add(optimizationLabel);
	// mainPanel.add(infoPanel);
	//
	// FrameUtils.addEmptyLabels(mainPanel, 1);
	//
	// JPanel subMainPanel = new JPanel();
	// subMainPanel.setLayout(new BoxLayout(subMainPanel, BoxLayout.X_AXIS));
	// subMainPanel.setBackground(Color.white);
	//
	// subPanel = new JPanel();
	// subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
	// subPanel.setBackground(Color.white);
	// subPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK,
	// 2),
	// BorderFactory.createEmptyBorder(10, 10, 10, 10)));
	//
	// JScrollPane scrollPane = new JScrollPane(subPanel);
	// scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	// scrollPane.setPreferredSize(new Dimension(200, 260));
	// subMainPanel.add(scrollPane);
	//
	// JLabel whiteSpace = new JLabel(" ");
	// subMainPanel.add(whiteSpace);
	//
	// subPanel2 = new JPanel();
	// subPanel2.setLayout(new BoxLayout(subPanel2, BoxLayout.Y_AXIS));
	// subPanel2.setBackground(Color.white);
	// subPanel2.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK,
	// 2),
	// BorderFactory.createEmptyBorder(10, 10, 10, 10)));
	//
	// JScrollPane scrollPane2 = new JScrollPane(subPanel2);
	// scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	// scrollPane2.setPreferredSize(new Dimension(200, 260));
	// subMainPanel.add(scrollPane2);
	//
	// mainPanel.add(subMainPanel);

    }

    /**
     * Cria e adiciona os tabs ao JTabbedPane
     */
    private JTabbedPane addJTabbedPane() {
	JTabbedPane tabbedPane = new JTabbedPane();

	first_run_panel = new JPanel();
	first_run_panel.setPreferredSize(new Dimension(700, 500));
	addContentsFirstRunPanel();

	// Adiciona o 1º tab - index 0
	// Args: texto do tab, icon, painel do tab, texto ao passar o cursor
	tabbedPane.addTab("", null, first_run_panel, "Atalho: Alt + 1");

	// Cria a label o 1º tab, com um dado tamanho, cor de letra e fonte
	JLabel tab1 = new JLabel("1st Run");
	tab1.setPreferredSize(new Dimension(180, 30));
	tab1.setForeground(new Color(0, 0, 153));
	tab1.setFont(new Font("Cambria", Font.PLAIN, 14));

	// Adiciona a label ao tab respetivo (referenciado pelo index)
	tabbedPane.setTabComponentAt(0, tab1);
	// Define o atalho para mudar para este tab (neste caso, ALT+1)
	tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
	// Define a cor do background do tab
	tabbedPane.setBackgroundAt(0, Color.LIGHT_GRAY);

	// Add the tabbed pane to this panel.
	// mainPanel.add(tabbedPane, BorderLayout.CENTER);
	add(tabbedPane, BorderLayout.CENTER);

	// The following line enables to use scrolling tabs.
	tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	
	return tabbedPane;
    }

    /**
     * Adiciona ao painel dos dados os campos de idade e nacionalidade e ao painel
     * da descrição os campos de montante, prazo e finalidade ao TAB1
     */
    private void addContentsFirstRunPanel() {
	JPanel infoPanel = new JPanel();
	infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
	infoPanel.setBackground(Color.white);
	JLabel decisionVariableLabel = new JLabel("Instantiated decision variables     ");
	decisionVariableLabel.setFont(FrameUtils.cuteFont(14));
	infoPanel.add(decisionVariableLabel);
	JLabel optimizationLabel = new JLabel("Resulting Optimization Criteria");
	optimizationLabel.setFont(FrameUtils.cuteFont(14));
	infoPanel.add(optimizationLabel);

	JPanel outterPanel = new JPanel();
	outterPanel.add(infoPanel);

	FrameUtils.addEmptyLabels(outterPanel, 1);

	JPanel subMainPanel = new JPanel();
	subMainPanel.setLayout(new BoxLayout(subMainPanel, BoxLayout.X_AXIS));
	subMainPanel.setBackground(Color.white);

	subPanel = new JPanel();
	subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
	subPanel.setBackground(Color.white);
	subPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
		BorderFactory.createEmptyBorder(10, 10, 10, 10)));

	JScrollPane scrollPane = new JScrollPane(subPanel);
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	scrollPane.setPreferredSize(new Dimension(200, 260));
	subMainPanel.add(scrollPane);

	JLabel whiteSpace = new JLabel("       ");
	subMainPanel.add(whiteSpace);

	subPanel2 = new JPanel();
	subPanel2.setLayout(new BoxLayout(subPanel2, BoxLayout.Y_AXIS));
	subPanel2.setBackground(Color.white);
	subPanel2.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
		BorderFactory.createEmptyBorder(10, 10, 10, 10)));

	JScrollPane scrollPane2 = new JScrollPane(subPanel2);
	scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	scrollPane2.setPreferredSize(new Dimension(200, 260));
	subMainPanel.add(scrollPane2);

	outterPanel.add(subMainPanel);
	first_run_panel.add(outterPanel);

    }

    private void constructPage(int runNumber) {
	FileReader fileReader = new FileReader();
	ArrayList<Double> list = fileReader.readFileAndReturnListInRunPerspective(
		new File(System.getProperty("user.dir") + "/src/utils/valuesTest.txt")).get(runNumber);
	for (Double doub : list) {
	    String str = userInterface.getDecisionVariablesFromPage().get(list.indexOf(doub)).getVariableName()
		    + "        " + doub.toString();
	    subPanel.add(new JLabel(str));
	}
	ArrayList<Double> list2 = fileReader.readFileAndReturnListInRunPerspective(
		new File(System.getProperty("user.dir") + "/src/utils/valuesTest.txt")).get(runNumber);
	for (Double doub : list2) {
	    String str = userInterface.getDecisionVariablesFromPage().get(list2.indexOf(doub)).getVariableName()
		    + "        " + doub.toString();
	    subPanel2.add(new JLabel(str));
	}
    }

    @Override
    protected void createButtonsPanel() {
	JButton homeButton = FrameUtils.cuteButton("Home");
	homeButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		userInterface.returnFromOutputAlgorithmPage();
	    }
	});
	buttonsPanel.add(homeButton);
    }

    @Override
    protected void onTop() {
	userInterface.getFrame().setTitle("Algorithm");
	constructPage(0);
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
    protected void clearDataFromPage() {
	// TODO Auto-generated method stub

    }

}
