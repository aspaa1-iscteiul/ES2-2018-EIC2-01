package jUnitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import objects.DataType;
import objects.DecisionVariable;
import objects.FitnessFunction;
import objects.OptimizationCriteria;
import objects.Problem;
import utils.UserFileUtils;

public class TestUserFileUtils {

    /**
     * Utility method that initiates a Problem object to be used for testing
     * 
     * @return Object Problem for testing
     */
    public Problem contructProblem(int scenario) {
	ArrayList<String> knownSolutionsDV1 = new ArrayList<String>();
	knownSolutionsDV1.add("3");
	knownSolutionsDV1.add("4");
	ArrayList<String> knownSolutionsDV2 = new ArrayList<String>();
	knownSolutionsDV2.add("3.5");
	knownSolutionsDV2.add("4.5");
	DecisionVariable dv1 = new DecisionVariable("var1", DataType.INTEGER, "-5", "+5", "0,1,2", knownSolutionsDV1);
	DecisionVariable dv2 = new DecisionVariable("var2", DataType.DOUBLE, "-4.9", "+4.8", "0.0", null);
	DecisionVariable dv3 = new DecisionVariable("var3", DataType.DOUBLE, "-5.1", "+5.1", null, knownSolutionsDV2);
	DecisionVariable dv4 = new DecisionVariable("var4", DataType.INTEGER, "-3", "+3", null, null);
	ArrayList<DecisionVariable> decisionVariables = new ArrayList<>();
	decisionVariables.add(dv1);
	decisionVariables.add(dv2);
	decisionVariables.add(dv3);
	decisionVariables.add(dv4);

	OptimizationCriteria oc1 = new OptimizationCriteria("oc1", DataType.INTEGER);
	OptimizationCriteria oc2 = new OptimizationCriteria("oc2", DataType.DOUBLE);
	ArrayList<OptimizationCriteria> optimizationCriteria = new ArrayList<>();
	optimizationCriteria.add(oc1);
	optimizationCriteria.add(oc2);
	FitnessFunction ff = new FitnessFunction("path", optimizationCriteria);
	ArrayList<FitnessFunction> fitnessFunctions = new ArrayList<>();
	fitnessFunctions.add(ff);

	ArrayList<String> optimizationAlgorithms = new ArrayList<>();
	optimizationAlgorithms.add("NSGA");
	optimizationAlgorithms.add("NSGA-2");

	Problem problem;
	if (scenario == 1)
	    problem = new Problem("ProblemaTeste", null, null, decisionVariables, fitnessFunctions,
		    optimizationAlgorithms, "2.0", "4.0");
	else
	    problem = new Problem("ProblemaTeste", "Descrição do problema de teste", "Regras", decisionVariables,
		    fitnessFunctions, optimizationAlgorithms, "2.0", null);

	return problem;
    }

    /**
     * Testing class initiation
     */
    @Test
    public final void testClassUserFileUtils() {
	new UserFileUtils();
    }

    /**
     * Testing a successful scenario of writing a Problem object to a XML document,
     * reading a Problem of that same file and confirming we are obtaining
     * equivalent objects
     * 
     * Scenario: null Problem's Description, null DecisionVariablesSetName, set
     * MaxTimeFrame
     */
    @Test
    public final void successfullyTestWriteToAndReadFromXML1() {
	Problem problemWrite = contructProblem(1);
	utils.UserFileUtils.writeToXML(problemWrite, "./src/jUnitTests/testFiles/userConfigTest1.xml");
	Problem problemRead = utils.UserFileUtils.readFromXML("./src/jUnitTests/testFiles/userConfigTest1.xml");

	assertEquals(problemWrite, problemRead);
    }

    /**
     * Testing a successful scenario of writing a Problem object to a XML document,
     * reading a Problem of that same file and confirming we are obtaining
     * equivalent objects
     * 
     * Scenario: set Problem's Description, set DecisionVariablesSetName, null
     * MaxTimeFrame
     */
    @Test
    public final void successfullyTestWriteToAndReadFromXML2() {
	Problem problemWrite = contructProblem(2);
	utils.UserFileUtils.writeToXML(problemWrite, "./src/jUnitTests/testFiles/userConfigTest2.xml");
	Problem problemRead = utils.UserFileUtils.readFromXML("./src/jUnitTests/testFiles/userConfigTest2.xml");

	assertEquals(problemWrite, problemRead);
    }

    /**
     * Testing a scenario in which the writeToXML and readFromXML functions fail
     * and, therefore, it is necessary to handle exceptions
     */
    @Test
    public final void unsuccessfullyTestWriteToAndReadFromXML() {
	Problem problemWrite = contructProblem(1);
	// For testing purposes, the file path passed as an argument has a typographical
	// error
	utils.UserFileUtils.writeToXML(problemWrite, "./src/jUnitTests/testFikes/userConfigTest1.xml");
	utils.UserFileUtils.readFromXML("./src/jUnitTests/testFilis/userConfigTest1.xml");
    }

    /**
     * Testing the method getInvalidValuesInVector() from the DecisionVariable class
     */
    @Test
    public final void testGetInvalidValuesInVector() {
	Problem problem = contructProblem(1);
	problem.getDecisionVariables().get(0).getInvalidValuesInVector();
	problem.getDecisionVariables().get(2).getInvalidValuesInVector();
    }

}
