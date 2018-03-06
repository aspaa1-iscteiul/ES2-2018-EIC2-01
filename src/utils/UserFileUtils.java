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
	Element decisionVariblesNode = doc.createElement("DecisionVariables");
	problemNode.appendChild(decisionVariblesNode);
	for (DecisionVariable d : problem.getDecisionVariables()) {
	    decisionVariblesNode.appendChild(getProblemElements(doc, decisionVariblesNode, "Name", d.getName()));
	    decisionVariblesNode
		    .appendChild(getProblemElements(doc, decisionVariblesNode, "DataType", "" + d.getDataType()));
	    decisionVariblesNode
		    .appendChild(getProblemElements(doc, decisionVariblesNode, "LowerBound", d.getLowerBound()));
	    decisionVariblesNode
		    .appendChild(getProblemElements(doc, decisionVariblesNode, "UpperBound", d.getUpperBound()));
	    decisionVariblesNode.appendChild(getProblemElements(doc, decisionVariblesNode, "Domain", d.getDomain()));
	    if (d.getKnownSolutions() != null) {
		Element knownSolutionsNode = doc.createElement("KnownSolutions");
		decisionVariblesNode.appendChild(knownSolutionsNode);
		for (String solution : d.getKnownSolutions()) {
		    knownSolutionsNode
			    .appendChild(getProblemElements(doc, knownSolutionsNode, "KnownSolution", solution));
		}
	    }

	}

	// create list fitness function element
	Element fitnessFunctionsNode = doc.createElement("FitnessFunctions");
	problemNode.appendChild(fitnessFunctionsNode);
	for (FitnessFunction f : problem.getFitnessFunctions()) {
	    fitnessFunctionsNode
		    .appendChild(getProblemElements(doc, fitnessFunctionsNode, "JarFilePath", f.getJarFilePath()));
	    if (f.getOptimizationCriteria() != null) {
		Element optimizationCriteriaNode = doc.createElement("OptimizationCriteria");
		fitnessFunctionsNode.appendChild(optimizationCriteriaNode);
		for (OptimizationCriteria criteria : f.getOptimizationCriteria()) {
		    optimizationCriteriaNode
			    .appendChild(getProblemElements(doc, optimizationCriteriaNode, "Name", criteria.getName()));
		    optimizationCriteriaNode.appendChild(
			    getProblemElements(doc, optimizationCriteriaNode, "DataType", "" + criteria.getDataType()));

		}
	    }

	}

	// create list optimization algorithms element
	Element optimizationAlgorithmsNode = doc.createElement("OptimizationAlgorithms");
	problemNode.appendChild(optimizationAlgorithmsNode);
	for (String a : problem.getOptimizationAlgorithms())
	    optimizationAlgorithmsNode.appendChild(getProblemElements(doc, optimizationAlgorithmsNode, "Algorithm", a));

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
	    NodeList nodeList = doc.getElementsByTagName("Problem");

	    problem = getProblem(nodeList.item(0));

	} catch (SAXException | ParserConfigurationException | IOException e1) {
	    e1.printStackTrace();
	}

	return problem;

    }

    private static Problem getProblem(Node node) {
	Problem problem = new Problem();
	if (node.getNodeType() == Node.ELEMENT_NODE) {
	    Element element = (Element) node;
	    problem.setProblemName((getTagValue("Name", element)));
	    problem.setProblemDescription((getTagValue("Description", element)));
	    problem.setIdealTimeFrame((Double.parseDouble(getTagValue("IdealTimeFrame", element))));
	    problem.setMaxTimeFrame((Double.parseDouble(getTagValue("MaximumTimeFrame", element))));
	}

	return problem;
    }

    private static String getTagValue(String tag, Element element) {
	NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
	Node node = (Node) nodeList.item(0);
	return node.getNodeValue();
    }

    public static void main(String[] args) {
	ArrayList<String> knownSolutions = new ArrayList<String>();
	knownSolutions.add("3");
	knownSolutions.add("4");
	DecisionVariable dv = new DecisionVariable("var1", DataType.INTEGER, "-5", "+5", "Z except 0", knownSolutions);
	ArrayList<DecisionVariable> decisionVariables = new ArrayList<>();
	decisionVariables.add(dv);

	OptimizationCriteria oc1 = new OptimizationCriteria("oc1", DataType.INTEGER);
	ArrayList<OptimizationCriteria> optimizationCriteria = new ArrayList<>();
	optimizationCriteria.add(oc1);
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
