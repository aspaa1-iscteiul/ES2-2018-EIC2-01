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

/**
 * Created with the purpose of testing the class UserFileUtils.java
 * 
 * @author Ana Pestana
 *
 */
public class TestUserFileUtils {

    /**
     * Utility method that initiates a Problem object to be used for testing
     * 
     * @return Object Problem for testing
     */
    public static Problem contructProblem(int scenario) {
	ArrayList<String> knownSolutionsInteger = new ArrayList<String>();
	knownSolutionsInteger.add("3");
	knownSolutionsInteger.add("4");
	ArrayList<String> knownSolutionsDouble = new ArrayList<String>();
	knownSolutionsDouble.add("3.5");
	knownSolutionsDouble.add("4.5");
	DecisionVariable dv1 = new DecisionVariable("var1", knownSolutionsInteger);
	DecisionVariable dv2 = new DecisionVariable("var2", null);
	DecisionVariable dv3 = new DecisionVariable("var3", knownSolutionsDouble);
	DecisionVariable dv4 = new DecisionVariable("var4", null);
	ArrayList<DecisionVariable> decisionVariables = new ArrayList<>();
	decisionVariables.add(dv1);
	decisionVariables.add(dv2);
	decisionVariables.add(dv3);
	decisionVariables.add(dv4);

	OptimizationCriteria oc1 = new OptimizationCriteria("oc1", null);
	OptimizationCriteria oc2 = new OptimizationCriteria("oc2", knownSolutionsDouble);
	ArrayList<OptimizationCriteria> optimizationCriteria = new ArrayList<>();
	optimizationCriteria.add(oc1);
	optimizationCriteria.add(oc2);
	FitnessFunction ff = new FitnessFunction("path", optimizationCriteria);
	ArrayList<FitnessFunction> fitnessFunctions = new ArrayList<>();
	fitnessFunctions.add(ff);

	ArrayList<String> optimizationAlgorithms = new ArrayList<>();
	optimizationAlgorithms.add("NSGA");
	optimizationAlgorithms.add("NSGAII");

	Problem problem;
	if (scenario == 1)
	    problem = new Problem("ProblemaTeste", null, null, DataType.INTEGER, null, null, null, decisionVariables,
		    DataType.BINARY, fitnessFunctions, optimizationAlgorithms, "2.0", null);
	else
	    problem = new Problem("ProblemaTeste", "Descrição do problema de teste", "Regras", DataType.DOUBLE, "-5.0",
		    "+5.0", "0.0", decisionVariables, DataType.DOUBLE, fitnessFunctions, optimizationAlgorithms, "2.0",
		    "4.0");
	return problem;
    }

    /**
     * Testing class initiation
     */
    @Test
    public final static void testClassUserFileUtils() {
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
    public final static void successfullyTestWriteToAndReadFromXML1() {
	Problem problemWrite = contructProblem(1);
	utils.UserFileUtils.writeToXML(problemWrite, "./src/jUnitTests/testFiles", "/userConfigTest1.xml");
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
    public final static void successfullyTestWriteToAndReadFromXML2() {
	Problem problemWrite = contructProblem(2);
	utils.UserFileUtils.writeToXML(problemWrite, "./src/jUnitTests/testFiles", "/userConfigTest2.xml");
	utils.UserFileUtils.writeToXML(problemWrite, "./src/jUnitTests/testFiles", "/");
	Problem problemRead = utils.UserFileUtils.readFromXML("./src/jUnitTests/testFiles/userConfigTest2.xml");

	assertEquals(problemWrite, problemRead);
    }

    /**
     * Testing a scenario in which the writeToXML and readFromXML functions fail
     * and, therefore, it is necessary to handle exceptions
     */
    @Test
    public final static void unsuccessfullyTestWriteToAndReadFromXML() {
	Problem problemWrite = contructProblem(1);
	// For testing purposes, the file path passed as an argument has a typographical
	// error
	utils.UserFileUtils.writeToXML(problemWrite, "./src/jUnitTests/testFikes", "/userConfigTest1.xml");
	utils.UserFileUtils.readFromXML("./src/jUnitTests/testFilis/userConfigTest1.xml");
    }

    /**
     * Testing the method getInvalidValuesInVector() from the DecisionVariable class
     */
    @Test
    public final static void testGetInvalidValuesInVector() {
	Problem problem1 = contructProblem(1);
	problem1.getInvalidValuesInVector();
	Problem problem2 = contructProblem(2);
	problem2.getInvalidValuesInVector();
    }

}
