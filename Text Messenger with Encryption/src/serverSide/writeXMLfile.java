package serverSide;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class writeXMLfile{

	//public static String id ="0";
	public void writeXML(String username, String email, String password, String gender, String dob) throws Exception {
		
		
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		DocumentBuilder db=dbf.newDocumentBuilder();
		
		Document document=db.parse(new File("details.xml"));
		
		Element element=document.getDocumentElement();
		Element account=document.createElement("Account");
		//account.setAttribute("id", id);
		
		String[] elementValue= {username,email,password,gender,dob};
		String[] elementName= {"userName","Email","password","gender","dateOfBirth"};
		
		for(int i=0;i<elementValue.length;i++) {
			
			Element elmt=createNewElement(document,elementName[i],elementValue[i]);
			account.appendChild(elmt);
			
		}
		
		element.appendChild(account);
		addNewAttribute(document);
		
	}


	private Element createNewElement(Document document, String elementName, String elementValue) {
		Element element=document.createElement(elementName);
		
		element.setTextContent(elementValue);
		
		return element;
	}

		
	private void addNewAttribute(Document document){
		
		TransformerFactory tf=TransformerFactory.newInstance();
		
		Transformer transfer;
		try {
			transfer = tf.newTransformer();
			transfer.setOutputProperty(OutputKeys.INDENT, "yes");
			transfer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
			
			DOMSource source=new DOMSource(document);
			StreamResult sr=new StreamResult(new File("details.xml"));
			try {
				transfer.transform(source,sr);
			} catch (TransformerException e) {
				
				e.printStackTrace();
			}
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
}
