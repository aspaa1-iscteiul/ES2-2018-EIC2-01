package utils;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

import objects.DataType;
import objects.DecisionVariable;
import objects.FitnessFunction;
import objects.OptimizationCriteria;
import objects.Problem;

/**
 * This class defines methods for writing a Problem object to a XML file
 * (writeToXML) and constructing a Problem object from a XML file (readFromXML).
 * The XML file must obey a determined structure for these methods to apply.
 * 
 * @author Ana Pestana
 *
 */
public class UserFileUtils {

    /**
     * Tags for the XML File nodes
     */
    public final static String tagRoot = "Problem", tagName = "Name", tagDataType = "DataType",
	    tagLowerBound = "LowerBound", tagUpperBound = "UpperBound", tagInvalidValues = "InvalidValues",
	    tagProblemDescription = "Description", tagDecisionVariablesSetName = "SetName",
	    tagDecisionVariables = "DecisionVariables", tagDecisionVariable = "DecisionVariable",
	    tagKnownSolutions = "KnownSolutions", tagKnownSolution = "KnownSolution",
	    tagFitnessFunctions = "FitnessFunctions", tagFitnessFunction = "FitnessFunction",
	    tagJarFilePath = "JarFilePath", tagOptimizationCriteriaList = "OptimizationCriteria",
	    tagSingleOptimizationCriteria = "Criteria", tagOptimizationAlgorithms = "OptimizationAlgorithms",
	    tagOptimizationAlgorithm = "OptimizationAlgorithm", tagIdealTimeFrame = "IdealTimeFrame",
	    tagMaximumTimeFrame = "MaximumTimeFrame";

    /**
     * Method to write a given Problem object to a XML file according to a
     * determined hierarchical structure
     * 
     * @param problem
     *            object you want to write on the XML file
     * @param path
     *            for the directory in which to store the XML File
     */
    public static void writeToXML(Problem problem, String path) {
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder;
	try {
	    dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.newDocument();

	    doc.appendChild(createProblemNode(doc, problem));

	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    DOMSource source = new DOMSource(doc);

	    StreamResult file = new StreamResult(new File(path));

	    transformer.transform(source, file);
	    System.out.println("File was saved with sucess");

	} catch (Exception e) {
	    JOptionPane.showMessageDialog(new JFrame(), "A problem occurred while saving the problem's configurations",
		    "Error message", JOptionPane.ERROR_MESSAGE);
	}
    }

    /**
     * Method that creates the root and child nodes for storing the problem's
     * attributes
     * 
     * @param doc
     *            XML document
     * @param problem
     *            object you want to write on the XML file
     * @return problemNode element containing the root node and its child nodes
     *         necessary for storing a Problem Object according to a determined XML
     *         hierarchical structure
     */
    private static Node createProblemNode(Document doc, Problem problem) {
	Element problemNode = doc.createElement(tagRoot);

	// Create Name and Description Nodes
	createTextNode(doc, problemNode, tagName, problem.getProblemName());

	if (problem.getProblemDescription() != null)
	    createTextNode(doc, problemNode, tagProblemDescription, problem.getProblemDescription());

	// Create Decision Variables Node and Sub-nodes
	Element decisionVariblesNode = createEmptyNode(doc, problemNode, tagDecisionVariables);

	if (problem.getDecisionVariablesSetName() != null) {
	    createTextNode(doc, decisionVariblesNode, tagDecisionVariablesSetName,
		    problem.getDecisionVariablesSetName());
	}

	for (DecisionVariable d : problem.getDecisionVariables()) {
	    Element decisionVaribleNode = createEmptyNode(doc, decisionVariblesNode, tagDecisionVariable);

	    createTextNode(doc, decisionVaribleNode, tagName, d.getName());
	    createTextNode(doc, decisionVaribleNode, tagDataType, d.getDataType().name());
	    createTextNode(doc, decisionVaribleNode, tagLowerBound, d.getLowerBound());
	    createTextNode(doc, decisionVaribleNode, tagUpperBound, d.getUpperBound());

	    if (d.getInvalidValues() != null)
		createTextNode(doc, decisionVaribleNode, tagInvalidValues, d.getInvalidValues());

	    if (d.getKnownSolutions() != null) {
		Element knownSolutionsNode = createEmptyNode(doc, decisionVaribleNode, tagKnownSolutions);
		for (String solution : d.getKnownSolutions())
		    createTextNode(doc, knownSolutionsNode, tagKnownSolution, solution);
	    }
	}

	// Create Fitness Functions Node and Sub-nodes
	Element fitnessFunctionsNode = createEmptyNode(doc, problemNode, tagFitnessFunctions);

	for (FitnessFunction f : problem.getFitnessFunctions()) {
	    Element fitnessFunctionNode = createEmptyNode(doc, fitnessFunctionsNode, tagFitnessFunction);
	    createTextNode(doc, fitnessFunctionNode, tagJarFilePath, f.getJarFilePath());

	    Element optimizationCriteriaListNode = createEmptyNode(doc, fitnessFunctionNode,
		    tagOptimizationCriteriaList);

	    for (OptimizationCriteria criteria : f.getOptimizationCriteria()) {
		Element optimizationCriteriaNode = createEmptyNode(doc, optimizationCriteriaListNode,
			tagSingleOptimizationCriteria);

		createTextNode(doc, optimizationCriteriaNode, tagName, criteria.getName());
		createTextNode(doc, optimizationCriteriaNode, tagDataType, criteria.getDataType().name());
	    }

	}

	// Create Optimization Algorithms Node and Sub-nodes
	Element optimizationAlgorithmsListNode = createEmptyNode(doc, problemNode, tagOptimizationAlgorithms);

	for (String algorithm : problem.getOptimizationAlgorithms())
	    createTextNode(doc, optimizationAlgorithmsListNode, tagOptimizationAlgorithm, algorithm);

	// Create Ideal Time Frame Node
	createTextNode(doc, problemNode, tagIdealTimeFrame, "" + problem.getIdealTimeFrame());

	// Create Maximum Time Frame Node
	if (problem.getMaxTimeFrame() != null)
	    createTextNode(doc, problemNode, tagMaximumTimeFrame, "" + problem.getMaxTimeFrame());

	return problemNode;
    }

    /**
     * Utility method to create a text node
     * 
     * @param doc
     *            XML document
     * @param rootNode
     *            parent of the new node
     * @param tag
     *            of the new node
     * @param value
     *            of the new node
     */
    private static Element createTextNode(Document doc, Element rootNode, String tag, String value) {
	Element node = doc.createElement(tag);
	node.appendChild(doc.createTextNode(value));
	rootNode.appendChild(node);
	return node;
    }

    /**
     * Utility method to create an empty node
     * 
     * @param doc
     *            XML document
     * @param rootNode
     *            parent of the new node
     * 
     * @param tag
     *            of the new node
     * 
     * @return empty new node
     */
    private static Element createEmptyNode(Document doc, Element rootNode, String tag) {
	Element node = doc.createElement(tag);
	rootNode.appendChild(node);
	return node;
    }

    /**
     * Method to read and construct a Problem object from a XML file document
     * compliant with a determined hierarchical structure
     * 
     * @param filePath
     *            for the directory containing the XML file
     * 
     * @return resulting Problem object
     */
    public static Problem readFromXML(String filePath) {
	File xmlFile = new File(filePath);
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder;

	Problem problem = new Problem();
	try {
	    dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.parse(xmlFile);
	    doc.getDocumentElement().normalize();
	    NodeList problemList = doc.getElementsByTagName(tagRoot);

	    problem = getProblemFromXMLNode(doc, problemList.item(0));

	} catch (SAXException | ParserConfigurationException | IOException e1) {
	    JOptionPane.showMessageDialog(new JFrame(), "A problem occurred while reading the problem's configurations",
		    "Error message", JOptionPane.ERROR_MESSAGE);
	}

	return problem;

    }

    /**
     * This method constructs a Problem object based on the information of a XML
     * document compliant with a determined node structure
     * 
     * @param doc
     *            XML document
     * @param problemNode
     *            element containing the root node and its child nodes
     * 
     * @return Problem object constructed from the XML document information
     */
    private static Problem getProblemFromXMLNode(Document doc, Node problemNode) {
	Problem problem = new Problem();

	Element problemElement = (Element) problemNode;

	problem.setProblemName((getTagValue(tagName, problemElement)));

	if (problemElement.getElementsByTagName(tagProblemDescription).getLength() > 0)
	    problem.setProblemDescription(getTagValue(tagProblemDescription, problemElement));

	if (problemElement.getElementsByTagName(tagDecisionVariablesSetName).getLength() > 0)
	    problem.setDecisionVariablesSetName(getTagValue(tagDecisionVariablesSetName, problemElement));
	
	problem.setDecisionVariables(getDecisionVariables(doc));
	problem.setFitnessFunctions(getFitnessFunctions(doc));
	problem.setOptimizationAlgorithms(getOptimizationAlgorithms(doc));

	problem.setIdealTimeFrame((getTagValue(tagIdealTimeFrame, problemElement)));

	if (problemNode.getLastChild().getNodeName().equals(tagMaximumTimeFrame))
	    problem.setMaxTimeFrame((getTagValue(tagMaximumTimeFrame, problemElement)));

	return problem;
    }

    /**
     * This method obtains the Problem's Decision Variables defined in the XML
     * document
     * 
     * @param doc
     *            XML document
     * 
     * @return ArrayList of the Problem's Decision Variables
     */
    private static ArrayList<DecisionVariable> getDecisionVariables(Document doc) {

	ArrayList<DecisionVariable> decisionVariables = new ArrayList<>();

	NodeList decisionVariablesList = doc.getElementsByTagName(tagDecisionVariable);

	for (int index = 0; index < decisionVariablesList.getLength(); index++) {

	    DecisionVariable decisionVariable = null;

	    Node decisionVariableNode = decisionVariablesList.item(index);

	    Element decisionVariableElement = (Element) decisionVariableNode;

	    String name = getTagValue(tagName, decisionVariableElement);
	    String dataType = getTagValue(tagDataType, decisionVariableElement);
	    String lowerBound = getTagValue(tagLowerBound, decisionVariableElement);
	    String upperBound = getTagValue(tagUpperBound, decisionVariableElement);

	    NodeList decisionVariableChildNodes = decisionVariableNode.getChildNodes();

	    String invalidValues = null;

	    if (decisionVariableChildNodes.item(4) != null
		    && decisionVariableChildNodes.item(4).getNodeName().equals(tagInvalidValues))
		invalidValues = getTagValue(tagInvalidValues, decisionVariableElement);

	    if (dataType.equalsIgnoreCase(DataType.INTEGER.name()))
		decisionVariable = new DecisionVariable(name, DataType.INTEGER, lowerBound, upperBound, invalidValues,
			null);
	    else
		decisionVariable = new DecisionVariable(name, DataType.DOUBLE, lowerBound, upperBound, invalidValues,
			null);

	    Node knownSolutionsNode = decisionVariableNode.getLastChild();

	    if (knownSolutionsNode.getNodeName().equals(tagKnownSolutions)) {

		ArrayList<String> knownSolutions = new ArrayList<>();

		NodeList knownSolutionsList = knownSolutionsNode.getChildNodes();

		for (int index2 = 0; index2 < knownSolutionsList.getLength(); index2++) {
		    Node knownSolutionNode = knownSolutionsList.item(index2);
		    String knownSolution = knownSolutionNode.getTextContent();

		    knownSolutions.add(knownSolution);

		}
		decisionVariable.setKnownSolutions(knownSolutions);
	    }

	    decisionVariables.add(decisionVariable);

	}
	return decisionVariables;
    }

    /**
     * This method obtains the Problem's Fitness Functions defined in the XML
     * document
     * 
     * @param doc
     *            XML document
     * 
     * @return ArrayList of the Problem's Fitness Functions
     */
    private static ArrayList<FitnessFunction> getFitnessFunctions(Document doc) {

	ArrayList<FitnessFunction> fitnessFunctions = new ArrayList<>();

	NodeList fitnessFunctionsList = doc.getElementsByTagName(tagFitnessFunction);

	for (int index = 0; index < fitnessFunctionsList.getLength(); index++) {

	    FitnessFunction fitnessFunction = null;
	    ArrayList<OptimizationCriteria> optimizationCriteria = new ArrayList<>();

	    Node fitnessFunctionNode = fitnessFunctionsList.item(index);

	    Element fitnessFunctionElement = (Element) fitnessFunctionNode;

	    String jarFilePath = getTagValue(tagJarFilePath, fitnessFunctionElement);

	    fitnessFunction = new FitnessFunction(jarFilePath, null);

	    Node optimizationCriteriaListNode = fitnessFunctionNode.getLastChild();
	    NodeList optimizationCriteriaNodeList = optimizationCriteriaListNode.getChildNodes();

	    for (int index2 = 0; index2 < optimizationCriteriaNodeList.getLength(); index2++) {
		OptimizationCriteria optCriteria = null;

		Node optimizationCriteriaNode = optimizationCriteriaNodeList.item(index2);

		Element optimizationCriteriaElement = (Element) optimizationCriteriaNode;
		String name = getTagValue(tagName, optimizationCriteriaElement);
		String dataType = getTagValue(tagDataType, optimizationCriteriaElement);

		if (dataType.equalsIgnoreCase(DataType.INTEGER.name()))
		    optCriteria = new OptimizationCriteria(name, DataType.INTEGER);
		else
		    optCriteria = new OptimizationCriteria(name, DataType.DOUBLE);

		optimizationCriteria.add(optCriteria);

	    }
	    fitnessFunction.setOptimizationCriteria(optimizationCriteria);

	    fitnessFunctions.add(fitnessFunction);

	}
	return fitnessFunctions;
    }

    /**
     * This method obtains the Problem's OptimizationAlgorithms defined in the XML
     * document
     * 
     * @param doc
     *            XML document
     * 
     * @return ArrayList of the Problem's OptimizationAlgorithms
     */
    private static ArrayList<String> getOptimizationAlgorithms(Document doc) {
	ArrayList<String> optimizationAlgorithms = new ArrayList<>();

	NodeList optimizationAlgorithmsList = doc.getElementsByTagName(tagOptimizationAlgorithm);

	for (int index = 0; index < optimizationAlgorithmsList.getLength(); index++) {

	    Node optimizationAlgorithmNode = optimizationAlgorithmsList.item(index);

	    String algorithm = optimizationAlgorithmNode.getTextContent();

	    optimizationAlgorithms.add(algorithm);

	}
	return optimizationAlgorithms;
    }

    /**
     * Utility method for returning a node content
     * 
     * @param tag
     *            of the node
     * @param element
     *            parent element of the node
     * 
     * @return text content of the node
     */
    private static String getTagValue(String tag, Element element) {
	return element.getElementsByTagName(tag).item(0).getTextContent();
    }

}