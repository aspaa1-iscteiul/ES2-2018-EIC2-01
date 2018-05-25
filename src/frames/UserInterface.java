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

import frames.graphicalObjects.DecisionVariablesObject;
import frames.graphicalObjects.FitnessFunctionObject;
import frames.graphicalObjects.KnownDecisionVariablesSolutionsObject;
import frames.graphicalObjects.KnownOptimizationCriteriaSolutionsObject;
import frames.graphicalObjects.OptimizationCriteriaObject;
import jMetal.JMetalRun;
import objects.Admin;
import objects.DecisionVariable;
import objects.OptimizationCriteria;
import objects.Problem;

public class UserInterface {

    private JFrame frame;
    private List<SuperPage> pages;
    private boolean[] xmlFileWasImported = new boolean[7];
    private boolean wasSomethingImported = false;
    private IntroPageAdmin introPageAdmin;
    private SendEmailPage emailPage;
    private OutputAlgorithmRunChooserPage outputAlgorithmRunChooserPage;
    private OutputAlgorithmRunPage outputAlgorithmRunPage;
    private OutputKnownSolutionsPage outputKnownSolutionsPage;
    private String userEmail;
    private ArrayList<DecisionVariablesObject> decisionVariablesFromPage;
    private ArrayList<KnownDecisionVariablesSolutionsObject> knownSolutionsFromDecisionVariables;
    private ArrayList<KnownOptimizationCriteriaSolutionsObject> knownSolutionsFromOptimizationCriteria;
    private ArrayList<OptimizationCriteriaObject> optimizationCriteriaFromPage;
    private ArrayList<String> optimizationAlgorithmsFromPage;
    private FitnessFunctionObject fitnessFunctionObject;
    private int actualPageIndex = 0;
    private Problem problem;
    private Admin admin;
    private boolean isSingleobjective;

    public UserInterface() {
	frame = new JFrame("ES2-2018-EIC2-01");
	for (int i = 0; i != xmlFileWasImported.length; i++) {
	    xmlFileWasImported[i] = false;
	}
	emailPage = new SendEmailPage(this);
	fitnessFunctionObject = new FitnessFunctionObject(null);
	decisionVariablesFromPage = new ArrayList<DecisionVariablesObject>();
	knownSolutionsFromDecisionVariables = new ArrayList<KnownDecisionVariablesSolutionsObject>();
	knownSolutionsFromOptimizationCriteria = new ArrayList<KnownOptimizationCriteriaSolutionsObject>();
	optimizationCriteriaFromPage = new ArrayList<OptimizationCriteriaObject>();
	optimizationAlgorithmsFromPage = new ArrayList<String>();

	problem = new Problem();
	admin = Admin.getDefaultAdmin();

	pages = new ArrayList<>();
	pages.add(new HomePage(this));
	pages.add(new IntroPage(this));
	pages.add(new RegisterUserPage(this));
	pages.add(new HomeCenterPage(this));
	pages.add(new ProblemIdPage(this));
	pages.add(new DecisionVariablesPage(this));
	pages.add(new OptimizationCriteriaPage(this));
	pages.add(new FitnessFunctionPage(this));
	pages.add(new KnownDecisionVariablesSolutionsPage(this));
	pages.add(new KnownOptimizationCriteriaSolutionsPage(this));
	pages.add(new AlgorithmsPage(this));
	pages.add(new TimeConstraintsPage(this));
	pages.add(new SaveProblemPage(this));
	pages.add(new OutputIntroPage(this));
	pages.add(new OutputKnownSolutionsPage(this));
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
     * Allows to go the IntroPageAdmin
     */
    public void goToIntroPageAdmin() {
	frame.remove(pages.get(actualPageIndex));
	introPageAdmin = new IntroPageAdmin(this);
	SuperPage page = introPageAdmin;
	page.onTop();
	frame.add(page);
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
     * Allows to go the OutputAlgorithmRunChooserPage
     */
    public void goToOutputAlgorithmRunChooserPage(String algorithmName) {
	frame.remove(pages.get(actualPageIndex));
	outputAlgorithmRunChooserPage = new OutputAlgorithmRunChooserPage(this, algorithmName);
	SuperPage page = outputAlgorithmRunChooserPage;
	page.onTop();
	frame.add(page);
	refreshPage();
    }

    /**
     * Allows to go the OutputAlgorithmRunPage
     */
    public void goToOutputAlgorithmRunPage(String algorithmName, int runNumber) {
	frame.remove(outputAlgorithmRunChooserPage);
	outputAlgorithmRunPage = new OutputAlgorithmRunPage(this, algorithmName, runNumber);
	SuperPage page = outputAlgorithmRunPage;
	page.onTop();
	frame.add(page);
	refreshPage();
    }

    /**
     * Allows to go the OutputKnownSolutionsPage(
     */
    public void goToOutputKnownSolutionsPage() {
	frame.remove(pages.get(actualPageIndex));
	outputKnownSolutionsPage = new OutputKnownSolutionsPage(this);
	SuperPage page = outputKnownSolutionsPage;
	page.onTop();
	frame.add(page);
	refreshPage();
    }

    /**
     * Allows to go back from the IntroPageAdmin to the HomeCenterPage
     */
    public void returnFromIntroPageAdmin() {
	frame.remove(introPageAdmin);
	SuperPage page = pages.get(actualPageIndex);
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
     * Allows to go back from the SendEmailPage to the OutputAlgorithmPage
     */
    public void returnFromOutputKnownSolutionsPage() {
	frame.remove(outputKnownSolutionsPage);
	SuperPage page = pages.get(actualPageIndex);
	page.onTop();
	frame.add(page);
	refreshPage();
    }

    /**
     * Allows to go back from the SendEmailPage to the OutputAlgorithmRunChooserPage
     */
    public void returnFromOutputAlgorithmRunChooserPage() {
	frame.remove(outputAlgorithmRunChooserPage);
	SuperPage page = pages.get(actualPageIndex);
	page.onTop();
	frame.add(page);
	refreshPage();
    }

    /**
     * Allows to go back from the SendEmailPage to the OutputAlgorithmRunPage
     */
    public void returnFromOutputAlgorithmRunPage() {
	frame.remove(outputAlgorithmRunPage);
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
	pages.get(actualPageIndex).onTop();
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

    public static void main(String[] args) {
	UserInterface user = new UserInterface();
	user.launch();
    }

    /**
     * Allows to averiguate that a problem was imported from a xml file and that
     * fields problem name and description can be used
     */
    public void createProblemId() {
	xmlFileWasImported[0] = true;
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
	    dvList.add(new DecisionVariable(dvo.getVariableName(),
		    getKnownSolutionsOfGivenVariable(dvo.getVariableName())));
	}
	this.problem.setDecisionVariablesDataType(decisionVariablesFromPage.get(0).getDataTypeToProblem());
	this.problem.setDecisionVariablesLowerBound(decisionVariablesFromPage.get(0).getLowerBound());
	this.problem.setDecisionVariablesUpperBound(decisionVariablesFromPage.get(0).getUpperBound());
	this.problem.setDecisionVariablesInvalidValues(
		Arrays.toString(decisionVariablesFromPage.get(0).getInvalidValues()));
	return dvList;
    }

    /**
     * Transforms the data from problem read from a xml file to the data used in the
     * interface
     * 
     * @param page
     * @return
     */
    public void createDecisionVariableFromProblem(DecisionVariablesPage page) {
	ArrayList<DecisionVariablesObject> tmp = new ArrayList<DecisionVariablesObject>();
	for (DecisionVariable dv : problem.getDecisionVariables()) {
	    tmp.add(new DecisionVariablesObject(page, dv.getName(), problem.getDecisionVariablesDataType().name(),
		    problem.getDecisionVariablesLowerBound(), problem.getDecisionVariablesUpperBound(),
		    problem.getInvalidValuesInVector()));
	}
	xmlFileWasImported[1] = true;
	this.decisionVariablesFromPage = tmp;
    }

    /**
     * Transforms the data inputs of the interface to the object FitnessFunction
     * that will be used in the object Problem
     * 
     * @return
     */
    public void createFitnessFunctionFinal() {
	this.problem.setFitnessFunction(fitnessFunctionObject.getPath());
    }

    /**
     * Transforms the data from problem read from a xml file to the data used in the
     * interface
     * 
     * @param page
     * @return
     */
    public void createFitnessFunctionFromProblem(FitnessFunctionPage page) {
	fitnessFunctionObject = new FitnessFunctionObject(page, problem.getFitnessFunction());
	xmlFileWasImported[2] = true;
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
	    ArrayList<String> solutionsAux = new ArrayList<String>();
	    for (KnownOptimizationCriteriaSolutionsObject ksoco : knownSolutionsFromOptimizationCriteria) {
		if (ksoco.getName().getText().equals(oco.getVariableName())) {
		    solutionsAux = ksoco.getSolutionListInString();
		}
	    }
	    ocList.add(new OptimizationCriteria(oco.getVariableName(), solutionsAux));
	    this.problem.setOptimizationCriteriaDataType(optimizationCriteriaFromPage.get(0).getDataTypeToProblem());
	}
	return ocList;
    }

    /**
     * Transforms the data from problem read from a xml file to the data used in the
     * interface
     * 
     * @param page
     * @return
     */
    public void createOptimizationCriteriaFromProblem(OptimizationCriteriaPage page) {
	ArrayList<OptimizationCriteriaObject> tmp = new ArrayList<OptimizationCriteriaObject>();
	for (OptimizationCriteria oc : problem.getOptimizationCriteria()) {
	    tmp.add(new OptimizationCriteriaObject(page, oc.getName(),
		    problem.getOptimizationCriteriaDataType().toString()));
	}
	xmlFileWasImported[3] = true;
	this.optimizationCriteriaFromPage = tmp;
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
	for (KnownDecisionVariablesSolutionsObject kso : knownSolutionsFromDecisionVariables) {
	    if (kso.getName().getText().equals(varName)) {
		for (JTextField text : kso.getTextfieldList()) {
		    solutions.add(text.getText());
		}
	    }
	}
	return solutions;
    }

    /**
     * Transforms the data from problem read from a xml file to the data used in the
     * interface
     * 
     * @param page
     * @return
     */
    public void createKnownDecisionVariablesSolutionsFromProblem(KnownDecisionVariablesSolutionsPage page) {
	ArrayList<KnownDecisionVariablesSolutionsObject> tmp = new ArrayList<KnownDecisionVariablesSolutionsObject>();
	for (DecisionVariable dv : problem.getDecisionVariables()) {
	    tmp.add(new KnownDecisionVariablesSolutionsObject(page, dv.getName(),
		    problem.getDecisionVariablesDataType().name(), problem.getDecisionVariablesLowerBound(),
		    problem.getDecisionVariablesUpperBound(), problem.getInvalidValuesInVector(),
		    dv.getKnownSolutions()));
	}
	xmlFileWasImported[4] = true;
	this.knownSolutionsFromDecisionVariables = tmp;
    }

    /**
     * Transforms the data from problem read from a xml file to the data used in the
     * interface
     * 
     * @param page
     * @return
     */
    public void createKnownOptimizationCriteriaSolutionsFromProblem(KnownOptimizationCriteriaSolutionsPage page) {
	ArrayList<KnownOptimizationCriteriaSolutionsObject> tmp = new ArrayList<KnownOptimizationCriteriaSolutionsObject>();
	for (OptimizationCriteria oc : problem.getOptimizationCriteria()) {
	    tmp.add(new KnownOptimizationCriteriaSolutionsObject(page, oc.getName(),
		    problem.getOptimizationCriteriaDataType().toString(), oc.getKnownSolutions()));
	}
	xmlFileWasImported[5] = true;
	this.knownSolutionsFromOptimizationCriteria = tmp;
    }

    /**
     * Transforms the data from problem read from a xml file to the data used in the
     * interface
     * 
     * @return
     */
    public void createOptimizationAlgorithmsFromProblem() {
	ArrayList<String> tmp = new ArrayList<String>();
	for (String string : problem.getOptimizationAlgorithms()) {
	    tmp.add(string);
	}
	this.optimizationAlgorithmsFromPage = tmp;
    }

    /**
     * Allows to averiguate that a problem was imported from a xml file and that
     * fields ideal time and max time can be used to populate the gui
     */
    public void createTimeConstraints() {
	xmlFileWasImported[6] = true;
    }

    /**
     * Sets the data from the interface on the optimization problem's
     * characterization
     */
    public void setFinalProblem() {
	this.problem.setDecisionVariables(createDecisionVariableFinalList());
	this.problem.setOptimizationCriteria(createOptimizationCriteriaFinalList());
	this.problem.setOptimizationAlgorithms(optimizationAlgorithmsFromPage);
	createFitnessFunctionFinal();
    }

    /**
     * Clean all the data if the user decides to submit a new problem
     */
    public void cleanData() {
	problem = new Problem();
	userEmail = null;
	decisionVariablesFromPage.clear();
	fitnessFunctionObject = new FitnessFunctionObject(null);
	knownSolutionsFromDecisionVariables.clear();
	optimizationCriteriaFromPage.clear();
	optimizationAlgorithmsFromPage.clear();
	for (int i = 0; i != xmlFileWasImported.length; i++) {
	    xmlFileWasImported[i] = false;
	}
	for (SuperPage page : pages) {
	    page.clearDataFromPage();
	}
	wasSomethingImported = false;
    }

    public boolean[] isXmlFileWasImported() {
	return xmlFileWasImported;
    }

    public boolean isXmlFileWasImportedAtIndex(int index) {
	return xmlFileWasImported[index];
    }

    public void putXmlFileWasImportedFalseAtIndex(int index) {
	xmlFileWasImported[index] = false;
    }

    public ArrayList<DecisionVariablesObject> getDecisionVariablesFromPage() {
	return decisionVariablesFromPage;
    }

    public void setDecisionVariablesFromPage(ArrayList<DecisionVariablesObject> decisionVariablesFromPage) {
	this.decisionVariablesFromPage = decisionVariablesFromPage;
    }

    public ArrayList<KnownDecisionVariablesSolutionsObject> getKnownDecisionVariablesSolutionsList() {
	return this.knownSolutionsFromDecisionVariables;
    }

    public void setKnownDecisionVariablesSolutionsList(ArrayList<KnownDecisionVariablesSolutionsObject> list) {
	this.knownSolutionsFromDecisionVariables = list;
    }

    public ArrayList<KnownOptimizationCriteriaSolutionsObject> getKnownOptimizationCriteriaSolutionsList() {
	return this.knownSolutionsFromOptimizationCriteria;
    }

    public void setKnownOptimizationCriteriaSolutionsList(ArrayList<KnownOptimizationCriteriaSolutionsObject> list) {
	this.knownSolutionsFromOptimizationCriteria = list;
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

    public boolean wasSomethingImported() {
	return wasSomethingImported;
    }

    public void setWasSomethingImported(boolean wasSomethingImported) {
	this.wasSomethingImported = wasSomethingImported;
    }
    
    public FitnessFunctionObject getFitnessFunctionObject() {
        return fitnessFunctionObject;
    }

    public void setFitnessFunctionObject(FitnessFunctionObject fitnessFunctionObject) {
        this.fitnessFunctionObject = fitnessFunctionObject;
    }

    /**
     * @return the admin
     */
    public Admin getAdmin() {
	return admin;
    }

    /**
     * @param admin
     *            the admin to set
     */
    public void setAdmin(Admin admin) {
	this.admin = admin;
    }

    public void runProblem() {
	setFinalProblem();
	new JMetalRun(this, problem, getIsSingleobjective(), getUserEmail(), admin.getEmail()).run();
    }

    public void showFrame(boolean show) {
	frame.setVisible(show);
    }

}