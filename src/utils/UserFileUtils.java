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

public class UserFileUtils {

    public static void writeToXML(Problem problem) {
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder;
	try {
	    dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.newDocument();

	    doc.appendChild(getProblem(doc, problem));

	    // Output to file
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    DOMSource source = new DOMSource(doc);

	    // Write to File
	    StreamResult file = new StreamResult(new File("./configFiles/userConfig.xml"));

	    // Write data
	    transformer.transform(source, file);
	    System.out.println("DONE");

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private static Node getProblem(Document doc, Problem problem) {
	Element problemNode = doc.createElement("Problem");

	// create name element
	problemNode.appendChild(getProblemElements(doc, problemNode, "Name", problem.getProblemName()));

	// create description element
	problemNode.appendChild(getProblemElements(doc, problemNode, "Description", problem.getProblemDescription()));

	// create list decision variables element
	Element decisionVariblesNode = doc.createElement("DecisionVariablesList");
	problemNode.appendChild(decisionVariblesNode);
	for (DecisionVariable d : problem.getDecisionVariables()) {
	    Element decisionVaribleNode = doc.createElement("DecisionVariable");
	    decisionVariblesNode.appendChild(decisionVaribleNode);

	    decisionVaribleNode.appendChild(getProblemElements(doc, decisionVaribleNode, "Name", d.getName()));
	    decisionVaribleNode
		    .appendChild(getProblemElements(doc, decisionVaribleNode, "DataType", "" + d.getDataType()));
	    decisionVaribleNode
		    .appendChild(getProblemElements(doc, decisionVaribleNode, "LowerBound", d.getLowerBound()));
	    decisionVaribleNode
		    .appendChild(getProblemElements(doc, decisionVaribleNode, "UpperBound", d.getUpperBound()));
	    decisionVaribleNode.appendChild(getProblemElements(doc, decisionVaribleNode, "Domain", d.getDomain()));
	    if (d.getKnownSolutions() != null) {
		Element knownSolutionsNode = doc.createElement("KnownSolutionsList");
		decisionVaribleNode.appendChild(knownSolutionsNode);
		for (String solution : d.getKnownSolutions()) {
		    knownSolutionsNode
			    .appendChild(getProblemElements(doc, knownSolutionsNode, "KnownSolution", solution));
		}
	    }

	}

	// create list fitness function element
	Element fitnessFunctionsNode = doc.createElement("FitnessFunctionsList");
	problemNode.appendChild(fitnessFunctionsNode);
	for (FitnessFunction f : problem.getFitnessFunctions()) {
	    Element fitnessFunctionNode = doc.createElement("FitnessFunction");
	    fitnessFunctionsNode.appendChild(fitnessFunctionNode);

	    fitnessFunctionNode
		    .appendChild(getProblemElements(doc, fitnessFunctionNode, "JarFilePath", f.getJarFilePath()));
	    if (f.getOptimizationCriteria() != null) {
		Element optimizationCriteriaListNode = doc.createElement("OptimizationCriteriaList");
		fitnessFunctionNode.appendChild(optimizationCriteriaListNode);
		for (OptimizationCriteria criteria : f.getOptimizationCriteria()) {
		    Element optimizationCriteriaNode = doc.createElement("OptimizationCriteria");
		    optimizationCriteriaListNode.appendChild(optimizationCriteriaNode);

		    optimizationCriteriaNode
			    .appendChild(getProblemElements(doc, optimizationCriteriaNode, "Name", criteria.getName()));
		    optimizationCriteriaNode.appendChild(
			    getProblemElements(doc, optimizationCriteriaNode, "DataType", "" + criteria.getDataType()));

		}
	    }

	}

	// create list optimization algorithms element
	Element optimizationAlgorithmsListNode = doc.createElement("OptimizationAlgorithmsList");
	problemNode.appendChild(optimizationAlgorithmsListNode);
	for (String a : problem.getOptimizationAlgorithms())
	    optimizationAlgorithmsListNode
		    .appendChild(getProblemElements(doc, optimizationAlgorithmsListNode, "OptimizationAlgorithm", a));

	// create ideal time frame element
	problemNode
		.appendChild(getProblemElements(doc, problemNode, "IdealTimeFrame", "" + problem.getIdealTimeFrame()));

	// create maximum time frame element
	problemNode
		.appendChild(getProblemElements(doc, problemNode, "MaximumTimeFrame", "" + problem.getMaxTimeFrame()));

	return problemNode;
    }

    // utility method to create text node
    private static Node getProblemElements(Document doc, Element element, String name, String value) {
	Element node = doc.createElement(name);
	node.appendChild(doc.createTextNode(value));
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
	    NodeList problemList = doc.getElementsByTagName("Problem");

	    problem = getProblem(doc, problemList.item(0));

	} catch (SAXException | ParserConfigurationException | IOException e1) {
	    e1.printStackTrace();
	}

	return problem;

    }

    private static Problem getProblem(Document doc, Node problemNode) {
	Problem problem = new Problem();
	if (problemNode.getNodeType() == Node.ELEMENT_NODE) {
	    Element element = (Element) problemNode;
	    problem.setProblemName((getTagValue("Name", element)));
	    problem.setProblemDescription((getTagValue("Description", element)));
	    problem.setIdealTimeFrame((Double.parseDouble(getTagValue("IdealTimeFrame", element))));
	    problem.setMaxTimeFrame((Double.parseDouble(getTagValue("MaximumTimeFrame", element))));
	}

	problem.setDecisionVariables(getDecisionVariables(doc));
	problem.setFitnessFunctions(getFitnessFunctions(doc));
	problem.setOptimizationAlgorithms(getOptimizationAlgorithms(doc));

	return problem;
    }

    private static String getTagValue(String tag, Element element) {
	NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
	Node node = (Node) nodeList.item(0);
	return node.getNodeValue();
    }

    private static ArrayList<DecisionVariable> getDecisionVariables(Document doc) {
	ArrayList<DecisionVariable> decisionVariables = new ArrayList<>();

	NodeList decisionVariablesList = doc.getElementsByTagName("DecisionVariable");

	for (int index = 0; index < decisionVariablesList.getLength(); index++) {

	    DecisionVariable decisionVariable = null;

	    Node decisionVariableNode = decisionVariablesList.item(index);

	    if (decisionVariableNode.getNodeType() == Node.ELEMENT_NODE) {

		Element element = (Element) decisionVariableNode;

		String name = element.getElementsByTagName("Name").item(0).getTextContent();
		String dataType = element.getElementsByTagName("DataType").item(0).getTextContent();
		String lowerBound = element.getElementsByTagName("LowerBound").item(0).getTextContent();
		String upperBound = element.getElementsByTagName("UpperBound").item(0).getTextContent();
		String domain = element.getElementsByTagName("Domain").item(0).getTextContent();

		if (dataType.equalsIgnoreCase(DataType.INTEGER.name()))
		    decisionVariable = new DecisionVariable(name, DataType.INTEGER, lowerBound, upperBound, domain,
			    null);
		else
		    decisionVariable = new DecisionVariable(name, DataType.DOUBLE, lowerBound, upperBound, domain,
			    null);

		Node knownSolutionsNode = decisionVariableNode.getLastChild();
		if (knownSolutionsNode.getNodeName().equals("KnownSolutionsList")) {

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

	NodeList fitnessFunctionsList = doc.getElementsByTagName("FitnessFunction");

	for (int index = 0; index < fitnessFunctionsList.getLength(); index++) {

	    FitnessFunction fitnessFunction = null;
	    ArrayList<OptimizationCriteria> optimizationCriteria = new ArrayList<>();

	    Node fitnessFunctionNode = fitnessFunctionsList.item(index);

	    if (fitnessFunctionNode.getNodeType() == Node.ELEMENT_NODE) {

		Element element = (Element) fitnessFunctionNode;

		String jarFilePath = element.getElementsByTagName("JarFilePath").item(0).getTextContent();

		fitnessFunction = new FitnessFunction(jarFilePath, null);

		Node optimizationCriteriaListNode = fitnessFunctionNode.getLastChild();
		NodeList optimizationCriteriaNodeList = optimizationCriteriaListNode.getChildNodes();

		for (int index2 = 0; index2 < optimizationCriteriaNodeList.getLength(); index2++) {
		    OptimizationCriteria optCriteria = null;

		    Node optimizationCriteriaNode = optimizationCriteriaNodeList.item(index2);

		    if (optimizationCriteriaNode.getNodeType() == Node.ELEMENT_NODE) {
			Element optimizationCriteriaElement = (Element) optimizationCriteriaNode;
			String name = optimizationCriteriaElement.getElementsByTagName("Name").item(0).getTextContent();
			String dataType = optimizationCriteriaElement.getElementsByTagName("DataType").item(0)
				.getTextContent();

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

	NodeList optimizationAlgorithmsList = doc.getElementsByTagName("OptimizationAlgorithm");

	for (int index = 0; index < optimizationAlgorithmsList.getLength(); index++) {

	    Node optimizationAlgorithmNode = optimizationAlgorithmsList.item(index);

	    if (optimizationAlgorithmNode.getNodeType() == Node.ELEMENT_NODE) {

		String algorithm = optimizationAlgorithmNode.getTextContent();

		optimizationAlgorithms.add(algorithm);

	    }

	}
	return optimizationAlgorithms;
    }

    public static void main(String[] args) {
	ArrayList<String> knownSolutions = new ArrayList<String>();
	knownSolutions.add("3");
	knownSolutions.add("4");
	DecisionVariable dv1 = new DecisionVariable("var1", DataType.INTEGER, "-5", "+5", "Z except 0", knownSolutions);
	DecisionVariable dv2 = new DecisionVariable("var2", DataType.INTEGER, "-5", "+5", "Z except 0", null);
	ArrayList<DecisionVariable> decisionVariables = new ArrayList<>();
	decisionVariables.add(dv1);
	decisionVariables.add(dv2);

	OptimizationCriteria oc1 = new OptimizationCriteria("oc1", DataType.INTEGER);
	OptimizationCriteria oc2 = new OptimizationCriteria("oc2", DataType.INTEGER);
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
	writeToXML(p1);

	Problem p2 = readFromXML("./configFiles/userConfig.xml");
	System.out.println(p2.toString());

    }

}