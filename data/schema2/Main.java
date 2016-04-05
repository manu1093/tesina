package progetto.data.schema2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Main {
	private static String list[]={"assTable","geoTable","relTable","refTable"};
	public static void main(String[] args) {
		File f=new File(System.getProperty("user.dir")+"/xml.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(f);
			/*
			//parte validazione 
			 // create a SchemaFactory capable of understanding WXS schemas
		    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		    // load a WXS schema, represented by a Schema instance
		    Source schemaFile = new StreamSource(new File("mySchema.xsd"));
		    Schema schema = factory.newSchema(schemaFile);
		    // create a Validator instance, which can be used to validate an instance document
		    Validator validator = schema.newValidator();
		    // validate the DOM tree
		    try {
		        validator.validate(new DOMSource(doc));
		    } catch (SAXException e) {
		        // instance document is invalid!
		    }
			*/
			doc.getDocumentElement().normalize();
			//String schema=doc.getDocumentElement().getAttribute("schema");
			String sch=doc.getDocumentElement().getAttribute("schema");
			DatabaseSchema db=new DatabaseSchema(sch);
			
			NodeList tables=doc.getDocumentElement().getElementsByTagName("AssTable");
			
			for(int i=0;i<tables.getLength();i++){
				Element e=(Element)tables.item(i);
				String tableName=e.getElementsByTagName("name").item(0).getNodeValue();
				AssociationTableSchema tab=new AssociationTableSchema(tableName);
				db.add(tab);
			}
			
			tables=doc.getDocumentElement().getElementsByTagName("GeoTable");			
			for(int i=0;i<tables.getLength();i++){
				Element e=(Element)tables.item(i);
				String tableName=e.getElementsByTagName("name").item(0).getNodeValue();
				String geometry=e.getElementsByTagName("geometry").item(0).getNodeValue();
				GeometryTableSchema tab=new GeometryTableSchema(tableName,geometry);
				db.add(tab);
			}
			
			
			tables=doc.getDocumentElement().getElementsByTagName("RelTable");			
			for(int i=0;i<tables.getLength();i++){
				Element e=(Element)tables.item(i);
				String tableName=e.getElementsByTagName("name").item(0).getNodeValue();
				RelationTableSchema tab=new RelationTableSchema(tableName);
				db.add(tab);
			}
			
			tables=doc.getDocumentElement().getElementsByTagName("RefTable");			
			for(int i=0;i<tables.getLength();i++){
				Element e=(Element)tables.item(i);
				String tableName=e.getElementsByTagName("name").item(0).getNodeValue();
				ReferenceTableSchema tab=new ReferenceTableSchema(tableName);				
				db.add(tab);
			}
			
			
			for(int i=0;i<list.length;i++){
				tables=doc.getDocumentElement().getElementsByTagName(list[i]);
				for(int j=0;j<tables.getLength();j++){
					Element e=(Element)tables.item(j);
					String tableName=e.getElementsByTagName("name").item(0).getNodeValue();
					TableSchema t=db.get(tableName);
					if(t instanceof NavigableTableSchema){
						NavigableTableSchema navT=(NavigableTableSchema)t;
						NodeList nl=e.getElementsByTagName("relation");
						for(int h=0;h<nl.getLength();h++){
							Element ee=(Element)nl.item(h);
							String bid=ee.getAttribute("bidirectional");
							String dest=ee.getElementsByTagName("destination").item(0).getNodeValue();
							VisibleTableSchema destTab=db.getVisTable(dest);
							String rel=ee.getElementsByTagName("relTable").item(0).getNodeValue();
							NavigableTableSchema relTable=db.getNavTable(rel);
							String myAttr=ee.getElementsByTagName("relAttribute").item(0).getNodeValue();
							String extAttr=ee.getElementsByTagName("relAttribute").item(0).getAttributes().getNamedItem("extAttribute").getNodeValue();
							NavRelSchema r=new NavRelSchema(relTable,new ArrayList<String>(Arrays.asList(myAttr)),new ArrayList<String>(Arrays.asList(extAttr)));
							
						}
					}
				}
			}
			
			
			
			
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
