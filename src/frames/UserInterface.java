package frames;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import utils.Problem;

public class UserInterface {

    private JFrame frame;
    private List<SuperPage> pages;
    private ArrayList<KnownSolutionsObject> knownSolutionsFromDecisionVariables;
    private ArrayList<OptimizationCriteriaObject> optimizationCriteriaFromPage;
    private int actualPageIndex = 0;
    private Problem problem;
    private boolean isSingleobjective;

    public UserInterface() {
	frame = new JFrame("ES2-2018-EIC2-01");
	knownSolutionsFromDecisionVariables = new ArrayList<KnownSolutionsObject>();
	optimizationCriteriaFromPage = new ArrayList<OptimizationCriteriaObject>();

	frame.addWindowListener(new WindowListener() {

	    @Override
	    public void windowOpened(WindowEvent e) {
	    }

	    @Override
	    public void windowIconified(WindowEvent e) {
	    }

	    @Override
	    public void windowDeiconified(WindowEvent e) {
	    }

	    @Override
	    public void windowDeactivated(WindowEvent e) {
	    }

	    @Override
	    public void windowClosing(WindowEvent e) {
		System.exit(0);
	    }

	    @Override
	    public void windowClosed(WindowEvent e) {
	    }

	    @Override
	    public void windowActivated(WindowEvent e) {
	    }
	});

	pages = new ArrayList<>();
	pages.add(new SaveProblemPage(this));
	pages.add(new HomePage(this));
	pages.add(new IntroPage(this));
	pages.add(new RegisterUserPage(this));
	pages.add(new ProblemIdPage(this));
	pages.add(new DecisionVariablesPage(this));
	pages.add(new OptimizationCriteriaPage(this));
	pages.add(new FitnessFunctionPage(this));
	pages.add(new KnownSolutionsPage(this));
	pages.add(new AlgorithmsPage(this));
    }

    public JFrame getFrame() {
	return frame;
    }

    public void goToNextPage() {
	frame.remove(pages.get(actualPageIndex));
	SuperPage page = pages.get(++actualPageIndex);
	page.onTop();
	frame.add(page);
	frame.pack(); // XXX remove when width and height is set
    }

    public void goToPreviousPage() {
	frame.remove(pages.get(actualPageIndex));
	SuperPage page = pages.get(--actualPageIndex);
	page.onTop();
	frame.add(page);
	frame.pack(); // XXX remove when width and height is set
    }

    private void launch() {
	frame.add(pages.get(actualPageIndex));
	frame.pack(); // XXX change to frame.setSize(width, height);
	frame.setLocation(new Point((Toolkit.getDefaultToolkit().getScreenSize().width - frame.getWidth()) / 2,
		(Toolkit.getDefaultToolkit().getScreenSize().height - frame.getHeight()) / 2));
	frame.setVisible(true);
    }

    public void setKnownSolutionsList(ArrayList<KnownSolutionsObject> list) {
	this.knownSolutionsFromDecisionVariables = list;
    }

    public ArrayList<KnownSolutionsObject> getKnownSolutionsList() {
	return this.knownSolutionsFromDecisionVariables;
    }

    public ArrayList<OptimizationCriteriaObject> getOptimizationCriteriaFromPage() {
	return optimizationCriteriaFromPage;
    }

    public void setOptimizationCriteriaFromPage(ArrayList<OptimizationCriteriaObject> optimizationCriteriaFromPage) {
	this.optimizationCriteriaFromPage = optimizationCriteriaFromPage;
    }

    public void setProblem(Problem problem) {
	this.problem = problem;
    }

    public Problem getProblem() {
	return problem;
    }

    public void isSingleobjective(boolean b) {// XXX horrible name
	isSingleobjective = b;
    }

    public boolean getIsSingleobjective() { // XXX horrible name
	return isSingleobjective;
    }

    public static void main(String[] args) {
	UserInterface user = new UserInterface();
	user.launch();
    }

}