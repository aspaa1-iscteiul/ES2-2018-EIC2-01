package utils;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class FileUtils {

    public static void main(String[] args) {
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder;
	try {
	    dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.newDocument();

	    // Create root element
	    doc.appendChild(getProblem(doc, "1", "Pankaj", "29", "Java Developer", "Male"));

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

    private static Node getProblem(Document doc, String id, String name, String age, String role, String gender) {
	Element employee = doc.createElement("Problem");

	// set id attribute
	employee.setAttribute("id", id);

	// create name element
	employee.appendChild(getEmployeeElements(doc, employee, "name", name));

	// create age element
	employee.appendChild(getEmployeeElements(doc, employee, "age", age));

	// create role element
	employee.appendChild(getEmployeeElements(doc, employee, "role", role));

	// create gender element
	employee.appendChild(getEmployeeElements(doc, employee, "gender", gender));

	return employee;
    }

    // utility method to create text node
    private static Node getEmployeeElements(Document doc, Element element, String name, String value) {
	Element node = doc.createElement(name);
	node.appendChild(doc.createTextNode(value));
	return node;
    }

}
