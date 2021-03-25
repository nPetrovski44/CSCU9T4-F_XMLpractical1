import java.io.*;               // import input-output
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.XMLConstants;
import javax.xml.parsers.*;         // import parsers
import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.*;           // import XPath
import javax.xml.validation.*;      // import validators
import javax.xml.transform.*;       // import DOM source classes

//import com.sun.xml.internal.bind.marshaller.NioEscapeHandler;
import org.w3c.dom.*;               // import DOM
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


/**
  DOM handler to read XML information, to create this, and to print it.

  @author   CSCU9T4, University of Stirling
  @version  11/03/20
*/
public class DOMMenu {

  /** Document builder */
  private static DocumentBuilder builder = null;

  /** XML document */
  private static Document document = null;

  /** XPath expression */
  private static XPath path = null;

  /** XML Schema for validation */
  private static Schema schema = null;
  
  private static ArrayList<DOMMenuIterator> listOfElements;

  /*----------------------------- General Methods ----------------------------*/

  /**
    Main program to call DOM parser.

    @param args         command-line arguments
  */
  public static void main(String[] args)
  {
	String documentName, schemeName;
	try
	{
		documentName = args[0];
		schemeName = args[1];
	    loadDocument(documentName);
	    if(validateDocument(schemeName) == true)printNodes();
	}catch(Exception e)
	{
		System.out.println("Error");
	}
    // load XML file into "document"
    // print staff.xml using DOM methods and XPath queries
  
  }

  /**
    Set global document by reading the given file.

    @param filename     XML file to read
  */
  private static void loadDocument(String filename) {
    try {
      // create a document builder
      DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
      builder = builderFactory.newDocumentBuilder();

      // create an XPath expression
      XPathFactory xpathFactory = XPathFactory.newInstance();
      path = xpathFactory.newXPath();

      // parse the document for later searching
      document = builder.parse(new File(filename));
    }
    catch (Exception exception) {
      System.err.println("could not load document " + exception);
    }
  }

  /*-------------------------- DOM and XPath Methods -------------------------*/
  /**
   Validate the document given a schema file
   @param filename XSD file to read
  */
  private static Boolean validateDocument(String filename) 
  {
    try {
      String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
      SchemaFactory factory = SchemaFactory.newInstance(language);
      schema = factory.newSchema(new File(filename));
      Validator validator = schema.newValidator();
      validator.validate(new DOMSource(document));
      return true;
    }
    catch (SAXParseException e){
      System.out.println("Could not validate because of " + e.getMessage());
      return false;
    }
    catch(Exception e)
    {
        System.out.println("Could not validate because of " + e.getMessage());
        return false;
    }
  }
  /**
    Print nodes using DOM methods and XPath queries.
  */
  private static void printNodes()
  {
	listOfElements = new ArrayList<DOMMenuIterator>();
	NodeList nl = document.getElementsByTagName("item");
	Node baseElement = null, element1 = null, element2 = null, element3 = null;
	
	for (int i = 0; i < nl.getLength(); i++)
	{
		baseElement = nl.item(i);
		element1 = baseElement.getFirstChild().getNextSibling();
		element2 = element1.getNextSibling().getNextSibling();
		element3 = element2.getNextSibling().getNextSibling();

		DOMMenuIterator elements = new DOMMenuIterator(element1.getTextContent(), element2.getTextContent(), element3.getTextContent());
		listOfElements.add(elements);
		elements.printElement();
		System.out.println();
	}
  }
  /**
    Get result of XPath query.

    @param query        XPath query
    @return         result of query
  */
  private static String query(String query) {
    String result = "";
    try {
      result = path.evaluate(query, document);
    }
    catch (Exception exception) {
      System.err.println("could not perform query - " + exception);
    }
    return(result);
  }
}
