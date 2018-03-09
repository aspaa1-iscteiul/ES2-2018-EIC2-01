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
    public Problem contructProblem() {
	ArrayList<String> knownSolutions = new ArrayList<String>();
	knownSolutions.add("3");
	knownSolutions.add("4");
	DecisionVariable dv1 = new DecisionVariable("var1", DataType.INTEGER, "-5", "+5", "Z except 0", knownSolutions);
	DecisionVariable dv2 = new DecisionVariable("var2", DataType.DOUBLE, "-4.9", "+4.8", "Z except 0.0", null);
	ArrayList<DecisionVariable> decisionVariables = new ArrayList<>();
	decisionVariables.add(dv1);
	decisionVariables.add(dv2);

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

	return new Problem("ProblemaTeste", "Descrição do problema de teste", decisionVariables, fitnessFunctions,
		optimizationAlgorithms, 2.0, 4.0);
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
     */
    @Test
    public final void successfullyTestWriteToAndReadFromXML() {
	Problem problemWrite = contructProblem();
	utils.UserFileUtils.writeToXML(problemWrite, "./src/jUnitTests/testFiles/userConfigTest.xml");
	Problem problemRead = utils.UserFileUtils.readFromXML("./src/jUnitTests/testFiles/userConfigTest.xml");

	assertEquals(problemWrite, problemRead);
	;

    }

    /**
     * Testing a scenario in which the writeToXML and readFromXML functions fail
     * and, therefore, it is necessary to handle exceptions
     */
    @Test
    public final void unsuccessfullyTestWriteToAndReadFromXML() {
	Problem problemWrite = contructProblem();
	// For testing purposes, the file path passed as an argument has a typographical
	// error
	utils.UserFileUtils.writeToXML(problemWrite, "./src/jUnitTests/testFikes/userConfigTest.xml");
	utils.UserFileUtils.readFromXML("./src/jUnitTests/testFilis/userConfigTest.xml");
    }

}
