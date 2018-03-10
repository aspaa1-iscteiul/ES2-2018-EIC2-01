package frames;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextField;

import objects.DecisionVariable;
import objects.FitnessFunction;
import objects.OptimizationCriteria;
import objects.Problem;

public class UserInterface {

    private JFrame frame;
    private List<SuperPage> pages;
    private SendEmailPage emailPage;
    private ArrayList<DecisionVariablesObject> decisionVariablesFromPage;
    private ArrayList<FitnessFunctionObject> fitnessFunctionFromPage;
    private ArrayList<KnownSolutionsObject> knownSolutionsFromDecisionVariables;
    private ArrayList<OptimizationCriteriaObject> optimizationCriteriaFromPage;
    private int actualPageIndex = 0;
    private Problem problem;
    private boolean isSingleobjective;

    public UserInterface() {
	frame = new JFrame("ES2-2018-EIC2-01");
	emailPage = new SendEmailPage(this);
	decisionVariablesFromPage = new ArrayList<DecisionVariablesObject>();
	fitnessFunctionFromPage = new ArrayList<FitnessFunctionObject>();
	knownSolutionsFromDecisionVariables = new ArrayList<KnownSolutionsObject>();
	optimizationCriteriaFromPage = new ArrayList<OptimizationCriteriaObject>();
	
	problem = new Problem();

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
//	pages.add(new HomePage(this));
//	pages.add(new IntroPage(this));
//	pages.add(new RegisterUserPage(this));
//	pages.add(new ProblemIdPage(this));
	pages.add(new DecisionVariablesPage(this));
	pages.add(new OptimizationCriteriaPage(this));
	pages.add(new FitnessFunctionPage(this));
	pages.add(new KnownSolutionsPage(this));
	// pages.add(new AlgorithmsPage(this));
	pages.add(new TimeConstraintsPage(this));
	pages.add(new HomeCenterPage(this));
	pages.add(new SaveProblemPage(this));
    }

    public JFrame getFrame() {
	return frame;
    }

    public void goToNextPage() {
	SuperPage actualPage = pages.get(actualPageIndex);
	if (!actualPage.areAllDataWellFilled())
	    return;
	actualPage.saveToProblem();
	frame.remove(actualPage);
	SuperPage nextPage = pages.get(++actualPageIndex);
	nextPage.onTop();
	frame.add(nextPage);
	frame.pack(); // XXX remove when width and height is set
    }

    public void goToPreviousPage() {
	frame.remove(pages.get(actualPageIndex));
	SuperPage backPage = pages.get(--actualPageIndex);
	backPage.onTop();
	frame.add(backPage);
	frame.pack(); // XXX remove when width and height is set
    }

    public void goToEmailPage() {
	// TODO Auto-generated method stub
	frame.remove(pages.get(actualPageIndex));
	SuperPage page = emailPage;
	page.onTop();
	frame.add(page);
	frame.pack(); // XXX remove when width and height is set
    }

    public void returnFromEmailPage() {
	frame.remove(emailPage);
	SuperPage page = pages.get(actualPageIndex);
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

    public ArrayList<DecisionVariablesObject> getDecisionVariablesFromPage() {
	return decisionVariablesFromPage;
    }

    public void setDecisionVariablesFromPage(ArrayList<DecisionVariablesObject> decisionVariablesFromPage) {
	this.decisionVariablesFromPage = decisionVariablesFromPage;
    }

    public ArrayList<FitnessFunctionObject> getFitnessFunctionFromPage() {
	return fitnessFunctionFromPage;
    }

    public void setFitnessFunctionFromPage(ArrayList<FitnessFunctionObject> fitnessFunctionFromPage) {
	this.fitnessFunctionFromPage = fitnessFunctionFromPage;
    }

    public ArrayList<KnownSolutionsObject> getKnownSolutionsList() {
	return this.knownSolutionsFromDecisionVariables;
    }

    public void setKnownSolutionsList(ArrayList<KnownSolutionsObject> list) {
	this.knownSolutionsFromDecisionVariables = list;
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

    public ArrayList<DecisionVariable> createDecisionVariableFinalList() {
	ArrayList<DecisionVariable> dvList = new ArrayList<DecisionVariable>();
	for (DecisionVariablesObject dvo : decisionVariablesFromPage) {
	    dvList.add(new DecisionVariable(dvo.getVariableName(), dvo.getDataTypeToProblem(), dvo.getLowerBound(),
		    dvo.getUpperBound(), "Dominio", getKnownSolutionsOfGivenVariable(dvo.getVariableName())));
	}
	return dvList;
    }

    public ArrayList<FitnessFunction> createFitnessFunctionFinalList() {
	ArrayList<FitnessFunction> ffList = new ArrayList<FitnessFunction>();
	for (FitnessFunctionObject ffo : fitnessFunctionFromPage) {
	    ffList.add(new FitnessFunction(ffo.getPath(), null));
	}
	return ffList;
    }

    public ArrayList<OptimizationCriteria> createOptimizationCriteriaFinalList() {
	ArrayList<OptimizationCriteria> ocList = new ArrayList<OptimizationCriteria>();
	for (OptimizationCriteriaObject oco : optimizationCriteriaFromPage) {
	    ocList.add(new OptimizationCriteria(oco.getVariableName(), oco.getDataTypeToProblem()));
	}
	return ocList;
    }

    public ArrayList<String> getKnownSolutionsOfGivenVariable(String varName) {
	ArrayList<String> solutions = new ArrayList<String>();
	for (KnownSolutionsObject kso : knownSolutionsFromDecisionVariables) {
	    if (kso.getName().getText().equals(varName)) {
		for (JTextField text : kso.getTextfieldList()) {
		    solutions.add(text.getText());
		}
	    }
	}
	return solutions;
    }

    public void setFinalProblem() {
	this.problem.setDecisionVariables(createDecisionVariableFinalList());
	this.problem.setFitnessFunctions(createFitnessFunctionFinalList());
	this.problem.setOptimizationAlgorithms(null);
    }

}