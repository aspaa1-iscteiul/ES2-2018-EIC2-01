package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import frames.frameUtils.FrameUtils;

public class Tabbed_Risk_Frame extends JPanel {

    private static final long serialVersionUID = 1L;
    private static JFrame frame;
    private JPanel first_run_panel, mainPanel, subPanel, subPanel2;
    private JTabbedPane tabbedPane;
    private Font font_tab = new Font("Cambria", Font.PLAIN, 14);

    public Tabbed_Risk_Frame() {
	super(new BorderLayout());
	addJTabbedPane();
    }

    /**
     * Cria e adiciona os tabs ao JTabbedPane
     */
    private void addJTabbedPane() {
	tabbedPane = new JTabbedPane();

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
	tab1.setFont(font_tab);

	// Adiciona a label ao tab respetivo (referenciado pelo index)
	tabbedPane.setTabComponentAt(0, tab1);
	// Define o atalho para mudar para este tab (neste caso, ALT+1)
	tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
	// Define a cor do background do tab
	tabbedPane.setBackgroundAt(0, Color.LIGHT_GRAY);

	// Add the tabbed pane to this panel.
//	mainPanel.add(tabbedPane, BorderLayout.CENTER);
	 add(tabbedPane, BorderLayout.CENTER);

	// The following line enables to use scrolling tabs.
	tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
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

    /**
     * Create the GUI and show it. For thread safety, this method should be invoked
     * from the event dispatch thread.
     */
    private static void createAndShowGUI() {
	// Create and set up the window.
	frame = new JFrame("Simulador de crédito pessoal");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	// Add content to the window.
	frame.add(new Tabbed_Risk_Frame(), BorderLayout.CENTER);

	// Display the window.
	frame.setSize(850, 600);
	frame.setLocation(new Point((Toolkit.getDefaultToolkit().getScreenSize().width - frame.getWidth()) / 2,
		(Toolkit.getDefaultToolkit().getScreenSize().height - frame.getHeight()) / 2));
	frame.setResizable(false);
	frame.setVisible(true);
    }

    public static void main(String[] args) {

	// Schedule a job for the event dispatch thread:
	// creating and showing this application's GUI.
	SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		// Turn off metal's use of bold fonts
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		createAndShowGUI();
	    }
	});
    }

}
