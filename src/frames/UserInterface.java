package frames;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;
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
    private String userEmail;
    private ArrayList<DecisionVariablesObject> decisionVariablesFromPage;
    private ArrayList<FitnessFunctionObject> fitnessFunctionFromPage;
    private ArrayList<KnownSolutionsObject> knownSolutionsFromDecisionVariables;
    private ArrayList<OptimizationCriteriaObject> optimizationCriteriaFromPage;
    private ArrayList<String> optimizationAlgorithmsFromPage;
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
	optimizationAlgorithmsFromPage = new ArrayList<String>();

	problem = new Problem();

	pages = new ArrayList<>();
	pages.add(new HomePage(this));
	pages.add(new IntroPage(this));
	pages.add(new RegisterUserPage(this));
	pages.add(new ProblemIdPage(this));
	pages.add(new DecisionVariablesPage(this));  
	pages.add(new OptimizationCriteriaPage(this));
	pages.add(new FitnessFunctionPage(this));
	pages.add(new KnownSolutionsPage(this));
	pages.add(new AlgorithmsPage(this));
	pages.add(new TimeConstraintsPage(this));
	pages.add(new HomeCenterPage(this));
	pages.add(new SaveProblemPage(this));
    }

    /**
     * @return {@link #frame}
     */
    public JFrame getFrame() {
	return frame;
    }

    /**
     * Changes to the next page if {@linkplain SuperPage#areAllDataWellFilled()}
     */
    public void goToNextPage() {
	SuperPage actualPage = pages.get(actualPageIndex);
	if (!actualPage.areAllDataWellFilled())
	    return;
	actualPage.saveToProblem();
	frame.remove(actualPage);
	SuperPage nextPage = pages.get(++actualPageIndex);
	nextPage.onTop();
	frame.add(nextPage);
	refreshPage();
    }

    /**
     * Allows to go back to the previous page
     */
    public void goToPreviousPage() {
	frame.remove(pages.get(actualPageIndex));
	SuperPage backPage = pages.get(--actualPageIndex);
	backPage.onTop();
	frame.add(backPage);
	refreshPage();
    }

    /**
     * Allows to go the SendEmailPage
     */
    public void goToEmailPage() {
	frame.remove(pages.get(actualPageIndex));
	SuperPage page = emailPage;
	page.onTop();
	frame.add(page);
	refreshPage();
    }

    /**
     * Allows to go back from the SendEmailPage to the HomeCenterPage
     */
    public void returnFromEmailPage() {
	frame.remove(emailPage);
	SuperPage page = pages.get(actualPageIndex);
	page.onTop();
	frame.add(page);
	refreshPage();
    }

    /**
     * Makes the adjustments to the {@linkplain #frame} and the displays it
     * 
     * @see JFrame#setVisible(boolean)
     */
    private void launch() {
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
	frame.add(pages.get(actualPageIndex));
	frame.setSize(600, 440);
	frame.setResizable(false);
	frame.setLocation(new Point((Toolkit.getDefaultToolkit().getScreenSize().width - frame.getWidth()) / 2,
		(Toolkit.getDefaultToolkit().getScreenSize().height - frame.getHeight()) / 2));
	frame.setVisible(true);
    }

    /**
     * Refreshes the {@linkplain #frame}
     */
    public void refreshPage() {
	frame.validate();
	frame.repaint();
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

    public ArrayList<String> getOptimizationAlgorithmsFromPage() {
	return optimizationAlgorithmsFromPage;
    }

    public void setOptimizationAlgorithmsFromPage(ArrayList<String> optimizationAlgorithmsFromPage) {
	this.optimizationAlgorithmsFromPage = optimizationAlgorithmsFromPage;
    }

    public void setProblem(Problem problem) {
	this.problem = problem;
    }

    public Problem getProblem() {
	return problem;
    }

    public String getUserEmail() {
	return userEmail;
    }

    public void setUserEmail(String userEmail) {
	this.userEmail = userEmail;
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

    /**
     * Transforms the data inputs of the interface to the object DecisionVariable
     * that will be used in the object Problem
     * 
     * @return
     */
    public ArrayList<DecisionVariable> createDecisionVariableFinalList() {
	ArrayList<DecisionVariable> dvList = new ArrayList<DecisionVariable>();
	for (DecisionVariablesObject dvo : decisionVariablesFromPage) {
	    dvList.add(new DecisionVariable(dvo.getVariableName(), dvo.getDataTypeToProblem(), dvo.getLowerBound(),
		    dvo.getUpperBound(),  Arrays.toString(dvo.getInvalidValues()) , getKnownSolutionsOfGivenVariable(dvo.getVariableName())));
	}
	return dvList;
    }

    /**
     * Transforms the data inputs of the interface to the object FitnessFunction
     * that will be used in the object Problem
     * 
     * @return
     */
    public ArrayList<FitnessFunction> createFitnessFunctionFinalList() {
	ArrayList<FitnessFunction> ffList = new ArrayList<FitnessFunction>();
	for (FitnessFunctionObject ffo : fitnessFunctionFromPage) {
	    ffList.add(new FitnessFunction(ffo.getPath(), getOptimizationCriteriaObjectsFromGivenStrings(ffo.getTheCheckboxesSelected()) ));
	}
	return ffList;
    }

    /**
     * Transforms the data inputs of the interface to the object
     * OptimizationCriteria that will be used in the object Problem
     * 
     * @return
     */
    public ArrayList<OptimizationCriteria> createOptimizationCriteriaFinalList() {
	ArrayList<OptimizationCriteria> ocList = new ArrayList<OptimizationCriteria>();
	for (OptimizationCriteriaObject oco : optimizationCriteriaFromPage) {
	    ocList.add(new OptimizationCriteria(oco.getVariableName(), oco.getDataTypeToProblem()));
	}
	return ocList;
    }

    /**
     * Transforms the data inputs of the interface to the object DecisionVariable
     * attribute ArrayList<String> knownSolutions that will be used in the object
     * Problem
     * 
     * @return
     */
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

    /**
     * Converts a arraylist of strings with the name of the checkboxes selected into an arraylist of optimization criteria
     * to be used in the problem saving
     * @param strings
     * @return
     */
    private ArrayList<OptimizationCriteria> getOptimizationCriteriaObjectsFromGivenStrings(ArrayList<String> strings){
	ArrayList<OptimizationCriteria> tmp = new  ArrayList<OptimizationCriteria>();
	for(String str : strings) {
	    for(OptimizationCriteria oco : createOptimizationCriteriaFinalList()) {
		if(oco.getName().equals(str)) {
		    tmp.add(oco);
		}
	    }
	}
	return tmp;
    }


    /**
     * Sets the data from the interface on the optimization problem's
     * characterization
     */
    public void setFinalProblem() {
	this.problem.setDecisionVariables(createDecisionVariableFinalList());
	this.problem.setFitnessFunctions(createFitnessFunctionFinalList());
	this.problem.setOptimizationAlgorithms(optimizationAlgorithmsFromPage);
    }

}