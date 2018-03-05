package utils;

import java.io.File;
import java.io.IOException;
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

	    // Create root element
	    // doc.appendChild(getProblem(doc, "1", "Pankaj", "29", "Java
	    // Developer", "Male"));
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

	// create age element
	problemNode.appendChild(getProblemElements(doc, problemNode, "Description", problem.getProblemDescription()));

	// create role element
	problemNode
		.appendChild(getProblemElements(doc, problemNode, "IdealTimeFrame", "" + problem.getIdealTimeFrame()));

	// create gender element
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
	// XMLReaderDOM domReader = new XMLReaderDOM();
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
	Problem p = new Problem("ProblemaTeste", "Descrição do problema de teste", 2.0, 4.0);
	writeToXML(p);
	Problem p2 = readFromXML("./configFiles/userConfig.xml");
	System.out.println(p2.toString());

    }

}
