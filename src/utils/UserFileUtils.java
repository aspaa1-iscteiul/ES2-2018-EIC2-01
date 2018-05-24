package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

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
    public final static String tagRoot = "Problem", tagProblemName = "Problem_Name",
	    tagProblemDescription = "Description", tagDecisionVariablesSetName = "DecisionVariables_SetName",
	    tagDecisionVariablesDataType = "DecisionVariables_DataType",
	    tagDecisionVariablesLowerBound = "DecisionVariables_LowerBound",
	    tagDecisionVariablesUpperBound = "DecisionVariables_UpperBound",
	    tagDecisionVariablesInvalidValues = "DecisionVariables_InvalidValues",
	    tagDecisionVariablesList = "DecisionVariables_List", tagDecisionVariable = "DecisionVariable",
	    tagName = "Name", tagKnownSolutionsList = "KnownSolutions_List", tagValue = "Value",
	    tagOptimizationCriteriaDataType = "OptimizationCriteria_DataType", tagFitnessFunction = "FitnessFunction",
	    tagOptimizationCriteriaList = "OptimizationCriteria_List", tagOptimizationCriteria = "OptimizationCriteria",
	    tagOptimizationAlgorithmsList = "OptimizationAlgorithms_List", tagAlgorithmsName = "Algorithm_Name",
	    tagIdealTimeFrame = "IdealTimeFrame", tagMaximumTimeFrame = "MaximumTimeFrame";

    /**
     * Method to write a given Problem object to a XML file according to a
     * determined hierarchical structure
     * 
     * @param problem
     *            object you want to write on the XML file
     * @param path
     *            for the directory in which to store the XML File
     */
    public static void writeToXML(Problem problem, String path, String fileName) {
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder;
	try {
	    dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.newDocument();

	    doc.appendChild(createProblemNode(doc, problem));

	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    DOMSource source = new DOMSource(doc);

	    if (fileName.trim().equals("/"))
		fileName = getDefaultName(problem.getProblemName());

	    StreamResult file = new StreamResult(new File(path + fileName));
	    System.out.println("Nome do ficheiro: " + path + fileName);

	    transformer.transform(source, file);
	    System.out.println("File was saved sucessfully");

	} catch (Exception e) {
	    JOptionPane.showMessageDialog(new JFrame(), "A problem occurred while saving the problem's configurations",
		    "Error message", JOptionPane.ERROR_MESSAGE);
	}
    }

    /**
     * This method returns the default file name:
     * "ProblemName_2018.05.25_23.59.00.xml"
     * 
     * @param problemName
     *            name of the problem configured in the file
     * @return Default name for this problem
     */
    private static String getDefaultName(String problemName) {
	Calendar now = Calendar.getInstance();
	int year = now.get(Calendar.YEAR);
	int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
	int day = now.get(Calendar.DAY_OF_MONTH);
	int hour = now.get(Calendar.HOUR_OF_DAY);
	int minute = now.get(Calendar.MINUTE);
	int second = now.get(Calendar.SECOND);
	return "/" + problemName + "_" + year + "." + month + "." + day + "_" + hour + "." + minute + "." + second
		+ ".xml";
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

	// Create Problem Name and Description Nodes
	createTextNode(doc, problemNode, tagProblemName, problem.getProblemName());

	if (problem.getProblemDescription() != null)
	    createTextNode(doc, problemNode, tagProblemDescription, problem.getProblemDescription());

	// Create Decision Variables related Nodes:
	// Set Name, Data Type, Lower and Upper Bound and Invalid Values
	if (problem.getDecisionVariablesSetName() != null)
	    createTextNode(doc, problemNode, tagDecisionVariablesSetName, problem.getDecisionVariablesSetName());

	createTextNode(doc, problemNode, tagDecisionVariablesDataType, problem.getDecisionVariablesDataType().name());

	if (problem.getDecisionVariablesLowerBound() != null)
	    createTextNode(doc, problemNode, tagDecisionVariablesLowerBound, problem.getDecisionVariablesLowerBound());

	if (problem.getDecisionVariablesUpperBound() != null)
	    createTextNode(doc, problemNode, tagDecisionVariablesUpperBound, problem.getDecisionVariablesUpperBound());

	if (problem.getDecisionVariablesInvalidValues() != null)
	    createTextNode(doc, problemNode, tagDecisionVariablesInvalidValues,
		    problem.getDecisionVariablesInvalidValues());

	// Create Decision Variables Node and Sub-nodes
	Element decisionVariblesNode = createEmptyNode(doc, problemNode, tagDecisionVariablesList);

	for (DecisionVariable d : problem.getDecisionVariables()) {
	    Element decisionVaribleNode = createEmptyNode(doc, decisionVariblesNode, tagDecisionVariable);

	    createTextNode(doc, decisionVaribleNode, tagName, d.getName());

	    if (d.getKnownSolutions() != null) {
		Element knownSolutionsNode = createEmptyNode(doc, decisionVaribleNode, tagKnownSolutionsList);
		for (String solution : d.getKnownSolutions())
		    createTextNode(doc, knownSolutionsNode, tagValue, solution);
	    }
	}

	// Create Optimization Criteria Data Type Node
	createTextNode(doc, problemNode, tagOptimizationCriteriaDataType,
		problem.getOptimizationCriteriaDataType().name());

	// Create Optimization Criteria Node and Sub-nodes
	Element optimizationCriteriaNode = createEmptyNode(doc, problemNode, tagOptimizationCriteriaList);

	for (OptimizationCriteria criteria : problem.getOptimizationCriteria()) {
	    Element optimizationCriteriaElement = createEmptyNode(doc, optimizationCriteriaNode,
		    tagOptimizationCriteria);

	    createTextNode(doc, optimizationCriteriaElement, tagName, criteria.getName());

	    if (criteria.getKnownSolutions() != null) {
		Element knownSolutionsNode = createEmptyNode(doc, optimizationCriteriaElement, tagKnownSolutionsList);
		for (String solution : criteria.getKnownSolutions())
		    createTextNode(doc, knownSolutionsNode, tagValue, solution);
	    }

	}

	// Create Fitness Function Node
	createTextNode(doc, problemNode, tagFitnessFunction, problem.getFitnessFunction());

	// Create Optimization Algorithms Node and Sub-nodes
	Element optimizationAlgorithmsListNode = createEmptyNode(doc, problemNode, tagOptimizationAlgorithmsList);

	for (String algorithm : problem.getOptimizationAlgorithms())
	    createTextNode(doc, optimizationAlgorithmsListNode, tagAlgorithmsName, algorithm);

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

	// Retrieve Problem Name and Description
	problem.setProblemName((getTagValue(tagProblemName, problemElement)));

	if (problemElement.getElementsByTagName(tagProblemDescription).getLength() > 0)
	    problem.setProblemDescription(getTagValue(tagProblemDescription, problemElement));

	// Retrieve Decision Variables:
	// Set Name, Data Type, Lower and Upper Bound and Invalid Values
	if (problemElement.getElementsByTagName(tagDecisionVariablesSetName).getLength() > 0)
	    problem.setDecisionVariablesSetName(getTagValue(tagDecisionVariablesSetName, problemElement));

	problem.setDecisionVariablesDataType(extractDataType(tagDecisionVariablesDataType, problemElement));

	if (problemElement.getElementsByTagName(tagDecisionVariablesLowerBound).getLength() > 0)
	    problem.setDecisionVariablesLowerBound(getTagValue(tagDecisionVariablesLowerBound, problemElement));

	if (problemElement.getElementsByTagName(tagDecisionVariablesUpperBound).getLength() > 0)
	    problem.setDecisionVariablesUpperBound(getTagValue(tagDecisionVariablesUpperBound, problemElement));

	if (problemElement.getElementsByTagName(tagDecisionVariablesInvalidValues).getLength() > 0)
	    problem.setDecisionVariablesInvalidValues(getTagValue(tagDecisionVariablesInvalidValues, problemElement));

	// Retrieve Decision Variables and Optimization Algorithms
	problem.setDecisionVariables(getDecisionVariables(doc));
	problem.setOptimizationAlgorithms(getOptimizationAlgorithms(doc));

	// Retrieve Optimization Criteria Data Type
	problem.setOptimizationCriteriaDataType(extractDataType(tagOptimizationCriteriaDataType, problemElement));

	// Retrieve OptimizationCriteria
	problem.setOptimizationCriteria(getOptimizationCriteria(doc));

	// Retrieve Fitness Function
	problem.setFitnessFunction(getTagValue(tagFitnessFunction, problemElement));

	// Retrieve Ideal and Maximum Time Frame
	problem.setIdealTimeFrame((getTagValue(tagIdealTimeFrame, problemElement)));

	if (problemNode.getLastChild().getNodeName().equals(tagMaximumTimeFrame))
	    problem.setMaxTimeFrame((getTagValue(tagMaximumTimeFrame, problemElement)));

	return problem;
    }

    /**
     * Returns the enum value of the dataType String retrieved from the
     * problemElement using the given tag
     * 
     * @param tag
     * @param problemElement
     * @return Enum value of dataType string
     */
    private static DataType extractDataType(String tag, Element problemElement) {
	String dataType = getTagValue(tag, problemElement);
	switch (dataType) {
	case "INTEGER":
	    return DataType.INTEGER;
	case "DOUBLE":
	    return DataType.DOUBLE;
	default:
	    return DataType.BINARY;
	}
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

	    Node decisionVariableNode = decisionVariablesList.item(index);
	    Element decisionVariableElement = (Element) decisionVariableNode;

	    String name = getTagValue(tagName, decisionVariableElement);
	    DecisionVariable decisionVariable = new DecisionVariable(name, null);

	    Node knownSolutionsNode = decisionVariableNode.getLastChild();
	    if (knownSolutionsNode.getNodeName().equals(tagKnownSolutionsList)) {

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
    private static ArrayList<OptimizationCriteria> getOptimizationCriteria(Document doc) {

	ArrayList<OptimizationCriteria> optimizationCriteria = new ArrayList<>();
	NodeList optimizationCriteriaNodeList = doc.getElementsByTagName(tagOptimizationCriteria);

	for (int index = 0; index < optimizationCriteriaNodeList.getLength(); index++) {

	    Node optimizationCriteriaNode = optimizationCriteriaNodeList.item(index);
	    Element optimizationCriteriaElement = (Element) optimizationCriteriaNode;

	    String name = getTagValue(tagName, optimizationCriteriaElement);
	    OptimizationCriteria optCriteria = new OptimizationCriteria(name, null);

	    Node knownSolutionsNode = optimizationCriteriaNode.getLastChild();
	    if (knownSolutionsNode.getNodeName().equals(tagKnownSolutionsList)) {

		ArrayList<String> knownSolutions = new ArrayList<>();
		NodeList knownSolutionsList = knownSolutionsNode.getChildNodes();

		for (int index3 = 0; index3 < knownSolutionsList.getLength(); index3++) {
		    Node knownSolutionNode = knownSolutionsList.item(index3);
		    String knownSolution = knownSolutionNode.getTextContent();

		    knownSolutions.add(knownSolution);
		}
		optCriteria.setKnownSolutions(knownSolutions);
	    }
	    optimizationCriteria.add(optCriteria);
	}
	return optimizationCriteria;
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
	NodeList optimizationAlgorithmsList = doc.getElementsByTagName(tagAlgorithmsName);

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