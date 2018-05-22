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

import objects.Admin;
import objects.QABlock;

/**
 * This class defines methods for creating a template of the config.xml
 * (createTemplate) and constructing an Admin object from a XML file
 * (loadAdmin). The XML file must obey a determined structure for these methods
 * to apply.
 * 
 * @author Ana Pestana
 *
 */

public class AdminFileUtils {

    /**
     * Tags for the XML File nodes
     */
    public final static String tagRoot = "ConfigParameters", tagAdminEmail = "Admin_email", tagFAQ = "FAQ",
	    tagQABlock = "Q&A_Block", tagQuestion = "Question", tagAnswer = "Answer", tagScriptsPath = "Scripts_Path";

    /**
     * Method to create a template of the config.xml file according to a determined
     * hierarchical structure
     * 
     */
    public static void createTemplate() {
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder;
	try {
	    dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.newDocument();

	    Admin admin = objects.Admin.getDefaultAdmin();

	    doc.appendChild(createAdminNode(doc, admin));

	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    DOMSource source = new DOMSource(doc);

	    StreamResult file = new StreamResult(new File("C:\\config.xml"));

	    transformer.transform(source, file);
	    System.out.println("config.xml template was saved sucessfully");

	} catch (Exception e) {
	    JOptionPane.showMessageDialog(new JFrame(),
		    "A problem occurred while downloading the template for the config.xml file", "Error message",
		    JOptionPane.ERROR_MESSAGE);
	}
    }

    /**
     * Method that creates the root and child nodes for storing the admin's
     * attributes
     * 
     * @param doc
     *            XML document
     * @param defaultAdmin
     *            object you want to write on the XML file
     * @return adminNode element containing the root node and its child nodes
     *         necessary for storing an Admin Object according to a determined XML
     *         hierarchical structure
     */
    private static Node createAdminNode(Document doc, Admin defaultAdmin) {
	Element adminNode = doc.createElement(tagRoot);

	// Create Administrator's Email Node
	createTextNode(doc, adminNode, tagAdminEmail, defaultAdmin.getEmail());

	// Create FAQ Node and Sub-nodes:
	// Block, Question, Answer

	Element faqNode = createEmptyNode(doc, adminNode, tagFAQ);

	for (QABlock qaBlock : defaultAdmin.getFaq()) {
	    Element qaBlockNode = createEmptyNode(doc, faqNode, tagQABlock);

	    createTextNode(doc, qaBlockNode, tagQuestion, qaBlock.getQuestion());
	    createTextNode(doc, qaBlockNode, tagAnswer, qaBlock.getAnswer());

	}

	// Create scripts path Node
	createTextNode(doc, adminNode, tagScriptsPath, defaultAdmin.getScriptsPath());

	return adminNode;
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
     * Method to read and construct an Admin object from a XML file document
     * compliant with a determined hierarchical structure
     * 
     * @param filePath
     *            for the directory containing the XML file
     * 
     * @return resulting Problem object
     */
    public static Admin readFromXML(String filePath) {
	File xmlFile = new File(filePath);
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder;

	Admin admin = new Admin();
	try {
	    dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.parse(xmlFile);
	    doc.getDocumentElement().normalize();
	    NodeList adminList = doc.getElementsByTagName(tagRoot);

	    admin = getAdminFromXMLNode(doc, adminList.item(0));

	} catch (SAXException | ParserConfigurationException | IOException e1) {
	    JOptionPane.showMessageDialog(new JFrame(), "A problem occurred while reading the configurations file",
		    "Error message", JOptionPane.ERROR_MESSAGE);
	}

	return admin;

    }

    /**
     * This method constructs an Admin object based on the information of a XML
     * document compliant with a determined node structure
     * 
     * @param doc
     *            XML document
     * @param adminNode
     *            element containing the root node and its child nodes
     * 
     * @return Problem object constructed from the XML document information
     */
    private static Admin getAdminFromXMLNode(Document doc, Node adminNode) {
	Admin admin = new Admin();

	Element adminElement = (Element) adminNode;

	// Retrieve Administrator's Email
	admin.setEmail((getTagValue(tagAdminEmail, adminElement)));

	// Retrieve FAQ Section: Block, Question, Answer
	admin.setFaq(getFAQSection(doc));

	// Retrieve scripts path
	admin.setScriptsPath((getTagValue(tagScriptsPath, adminElement)));

	return admin;
    }

    /**
     * This method obtains the Admin's FAQ Section defined in the XML document
     * 
     * @param doc
     *            XML document
     * 
     * @return ArrayList of Q&A blocks
     */
    private static ArrayList<QABlock> getFAQSection(Document doc) {

	ArrayList<QABlock> faq = new ArrayList<>();

	NodeList blockList = doc.getElementsByTagName(tagQABlock);

	for (int index = 0; index < blockList.getLength(); index++) {

	    Node blockNode = blockList.item(index);
	    Element qaBlockElement = (Element) blockNode;

	    String question = getTagValue(tagQuestion, qaBlockElement);
	    String answer = getTagValue(tagAnswer, qaBlockElement);

	    QABlock qaBlock = new QABlock(question, answer);

	    faq.add(qaBlock);
	}
	return faq;
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
