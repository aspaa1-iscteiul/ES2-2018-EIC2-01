package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

public class UserFileUtils {

    public final static String tagRoot = "Problem";

    public final static String tagName = "Name", tagDataType = "DataType", tagLowerBound = "LowerBound",
	    tagUpperBound = "UpperBound", tagDomain = "Domain", tagProblemDescription = "Description",
	    tagDecisionVariables = "DecisionVariables", tagDecisionVariable = "DecisionVariable",
	    tagKnownSolutions = "KnownSolutions", tagKnownSolution = "KnownSolution",
	    tagFitnessFunctions = "FitnessFunctions", tagFitnessFunction = "FitnessFunction",
	    tagJarFilePath = "JarFilePath", tagOptimizationCriteriaList = "OptimizationCriteria",
	    tagSingleOptimizationCriteria = "Criteria", tagOptimizationAlgorithms = "OptimizationAlgorithms",
	    tagOptimizationAlgorithm = "OptimizationAlgorithm", tagIdealTimeFrame = "IdealTimeFrame",
	    tagMaximumTimeFrame = "MaximumTimeFrame";

    public static void writeToXML(Problem problem, String path) {
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder;
	try {
	    dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.newDocument();

	    doc.appendChild(createProblemNode(doc, problem));

	    // Output to file
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    DOMSource source = new DOMSource(doc);

	    // Write to File
	    StreamResult file = new StreamResult(new File(path));

	    // Write data
	    transformer.transform(source, file);

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private static Node createProblemNode(Document doc, Problem problem) {
	Element problemNode = doc.createElement(tagRoot);

	// Create Name and Description Nodes
	createTextNode(doc, problemNode, tagName, problem.getProblemName());
	createTextNode(doc, problemNode, tagProblemDescription, problem.getProblemDescription());

	// Create Decision Variables Node and Sub-nodes
	Element decisionVariblesNode = createEmptyNode(doc, problemNode, tagDecisionVariables);

	for (DecisionVariable d : problem.getDecisionVariables()) {
	    Element decisionVaribleNode = createEmptyNode(doc, decisionVariblesNode, tagDecisionVariable);

	    createTextNode(doc, decisionVaribleNode, tagName, d.getName());
	    createTextNode(doc, decisionVaribleNode, tagDataType, d.getDataType().name());
	    createTextNode(doc, decisionVaribleNode, tagLowerBound, d.getLowerBound());
	    createTextNode(doc, decisionVaribleNode, tagUpperBound, d.getUpperBound());
	    createTextNode(doc, decisionVaribleNode, tagDomain, d.getDomain());

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

	    if (f.getOptimizationCriteria() != null) {
		Element optimizationCriteriaListNode = createEmptyNode(doc, fitnessFunctionNode,
			tagOptimizationCriteriaList);

		for (OptimizationCriteria criteria : f.getOptimizationCriteria()) {
		    Element optimizationCriteriaNode = createEmptyNode(doc, optimizationCriteriaListNode,
			    tagSingleOptimizationCriteria);

		    createTextNode(doc, optimizationCriteriaNode, tagName, criteria.getName());
		    createTextNode(doc, optimizationCriteriaNode, tagDataType, criteria.getDataType().name());
		}
	    }
	}

	// Create Optimization Algorithms Node and Sub-nodes
	Element optimizationAlgorithmsListNode = createEmptyNode(doc, problemNode, tagOptimizationAlgorithms);

	for (String algorithm : problem.getOptimizationAlgorithms())
	    createTextNode(doc, optimizationAlgorithmsListNode, tagOptimizationAlgorithm, algorithm);

	// Create Ideal Time Frame Node
	createTextNode(doc, problemNode, tagIdealTimeFrame, "" + problem.getIdealTimeFrame());
	// Create Maximum Time Frame Node
	createTextNode(doc, problemNode, tagMaximumTimeFrame, "" + problem.getMaxTimeFrame());

	return problemNode;
    }

    // Utility method to create a text node
    private static void createTextNode(Document doc, Element rootNode, String tag, String value) {
	Element node = doc.createElement(tag);
	node.appendChild(doc.createTextNode(value));
	rootNode.appendChild(node);
    }

    // Utility method to create an empty node
    private static Element createEmptyNode(Document doc, Element rootNode, String tag) {
	Element node = doc.createElement(tag);
	rootNode.appendChild(node);
	return node;
    }

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
	    e1.printStackTrace();
	}

	return problem;

    }

    private static Problem getProblemFromXMLNode(Document doc, Node problemNode) {
	Problem problem = new Problem();
	if (problemNode.getNodeType() == Node.ELEMENT_NODE) {

	    Element problemElement = (Element) problemNode;

	    problem.setProblemName((getTagValue(tagName, problemElement)));
	    problem.setProblemDescription((getTagValue(tagProblemDescription, problemElement)));

	    problem.setDecisionVariables(getDecisionVariables(doc));
	    problem.setFitnessFunctions(getFitnessFunctions(doc));
	    problem.setOptimizationAlgorithms(getOptimizationAlgorithms(doc));

	    problem.setIdealTimeFrame((Double.parseDouble(getTagValue(tagIdealTimeFrame, problemElement))));
	    problem.setMaxTimeFrame((Double.parseDouble(getTagValue(tagMaximumTimeFrame, problemElement))));
	}

	return problem;
    }

    private static ArrayList<DecisionVariable> getDecisionVariables(Document doc) {

	ArrayList<DecisionVariable> decisionVariables = new ArrayList<>();

	NodeList decisionVariablesList = doc.getElementsByTagName(tagDecisionVariable);

	for (int index = 0; index < decisionVariablesList.getLength(); index++) {

	    DecisionVariable decisionVariable = null;

	    Node decisionVariableNode = decisionVariablesList.item(index);

	    if (decisionVariableNode.getNodeType() == Node.ELEMENT_NODE) {

		Element element = (Element) decisionVariableNode;

		String name = getTagValue(tagName, element);
		String dataType = getTagValue(tagDataType, element);
		String lowerBound = getTagValue(tagLowerBound, element);
		String upperBound = getTagValue(tagUpperBound, element);
		String domain = getTagValue(tagDomain, element);

		if (dataType.equalsIgnoreCase(DataType.INTEGER.name()))
		    decisionVariable = new DecisionVariable(name, DataType.INTEGER, lowerBound, upperBound, domain,
			    null);
		else
		    decisionVariable = new DecisionVariable(name, DataType.DOUBLE, lowerBound, upperBound, domain,
			    null);

		Node knownSolutionsNode = decisionVariableNode.getLastChild();

		if (knownSolutionsNode.getNodeName().equals(tagKnownSolutions)) {

		    ArrayList<String> knownSolutions = new ArrayList<>();

		    NodeList knownSolutionsList = knownSolutionsNode.getChildNodes();

		    for (int index2 = 0; index2 < knownSolutionsList.getLength(); index2++) {
			Node knownSolutionNode = knownSolutionsList.item(index2);
			if (knownSolutionNode.getNodeType() == Node.ELEMENT_NODE) {
			    String knownSolution = knownSolutionNode.getTextContent();

			    knownSolutions.add(knownSolution);
			}

		    }
		    decisionVariable.setKnownSolutions(knownSolutions);
		}

	    }

	    decisionVariables.add(decisionVariable);

	}
	return decisionVariables;
    }

    private static ArrayList<FitnessFunction> getFitnessFunctions(Document doc) {

	ArrayList<FitnessFunction> fitnessFunctions = new ArrayList<>();

	NodeList fitnessFunctionsList = doc.getElementsByTagName(tagFitnessFunction);

	for (int index = 0; index < fitnessFunctionsList.getLength(); index++) {

	    FitnessFunction fitnessFunction = null;
	    ArrayList<OptimizationCriteria> optimizationCriteria = new ArrayList<>();

	    Node fitnessFunctionNode = fitnessFunctionsList.item(index);

	    if (fitnessFunctionNode.getNodeType() == Node.ELEMENT_NODE) {

		Element element = (Element) fitnessFunctionNode;

		String jarFilePath = getTagValue(tagJarFilePath, element);

		fitnessFunction = new FitnessFunction(jarFilePath, null);

		Node optimizationCriteriaListNode = fitnessFunctionNode.getLastChild();
		NodeList optimizationCriteriaNodeList = optimizationCriteriaListNode.getChildNodes();

		for (int index2 = 0; index2 < optimizationCriteriaNodeList.getLength(); index2++) {
		    OptimizationCriteria optCriteria = null;

		    Node optimizationCriteriaNode = optimizationCriteriaNodeList.item(index2);

		    if (optimizationCriteriaNode.getNodeType() == Node.ELEMENT_NODE) {
			Element optimizationCriteriaElement = (Element) optimizationCriteriaNode;
			String name = getTagValue(tagName, optimizationCriteriaElement);
			String dataType = getTagValue(tagDataType, optimizationCriteriaElement);

			if (dataType.equalsIgnoreCase(DataType.INTEGER.name()))
			    optCriteria = new OptimizationCriteria(name, DataType.INTEGER);
			else
			    optCriteria = new OptimizationCriteria(name, DataType.DOUBLE);

		    }
		    optimizationCriteria.add(optCriteria);

		}
		fitnessFunction.setOptimizationCriteria(optimizationCriteria);

	    }
	    fitnessFunctions.add(fitnessFunction);

	}
	return fitnessFunctions;
    }

    private static ArrayList<String> getOptimizationAlgorithms(Document doc) {
	ArrayList<String> optimizationAlgorithms = new ArrayList<>();

	NodeList optimizationAlgorithmsList = doc.getElementsByTagName(tagOptimizationAlgorithm);

	for (int index = 0; index < optimizationAlgorithmsList.getLength(); index++) {

	    Node optimizationAlgorithmNode = optimizationAlgorithmsList.item(index);

	    if (optimizationAlgorithmNode.getNodeType() == Node.ELEMENT_NODE) {

		String algorithm = optimizationAlgorithmNode.getTextContent();

		optimizationAlgorithms.add(algorithm);
	    }
	    
	}
	return optimizationAlgorithms;
    }

    private static String getTagValue(String tag, Element element) {
	return element.getElementsByTagName(tag).item(0).getTextContent();
    }

    public static void main(String[] args) {
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

	Problem p1 = new Problem("ProblemaTeste", "Descrição do problema de teste", decisionVariables, fitnessFunctions,
		optimizationAlgorithms, 2.0, 4.0);
	writeToXML(p1, "./configFiles/userConfig.xml");

	Problem p2 = readFromXML("./configFiles/userConfig.xml");
	System.out.println(p2.toString());

    }

}