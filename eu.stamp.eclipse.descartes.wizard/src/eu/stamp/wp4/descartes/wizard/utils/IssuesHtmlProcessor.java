package eu.stamp.wp4.descartes.wizard.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class IssuesHtmlProcessor {
	
	public String[] process(String issueHtml) {
		try {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		issueHtml = issueHtml
			.replaceAll("<link href=\"../style.css\" rel=\"stylesheet\">","");
		InputStream stream = new ByteArrayInputStream(issueHtml.getBytes());
	    Document document = builder.parse(stream);
	    Element element = document.getDocumentElement();
	    String[] result = new String[2];
	    result[0] = getTitle(element,issueHtml);
	    result[1] = getHtmlDescription(issueHtml);
	    return result;
		} catch(ParserConfigurationException | FactoryConfigurationError
			| SAXException | IOException | NullPointerException e) {
			e.printStackTrace();
			return new String[] {"",""};
		}
	}
	
	private String getTitle(Element documentElement,String issueHtml) {
	    Node headNode = documentElement.getElementsByTagName("head").item(0);
        Node titleNode = ((Element)headNode)
        		.getElementsByTagName("title").item(0);
        String methodString = titleNode.getFirstChild().getNodeValue()
        		.replaceAll("\\(.*?\\)","");
        NodeList nameList = documentElement.getElementsByTagName("dd");
        String classString = "";
        String packageString = "";
        String string;
        for(int i = 0; i < nameList.getLength(); i++) {
        	string = nameList.item(i).getFirstChild().getNodeValue();
        	if(string.contains("/")) packageString = string.replaceAll("/",".");
        	else classString = string;
        }
        StringBuilder titleBuilder = new StringBuilder();
        titleBuilder.append("Descartes issue: ");
        titleBuilder.append(packageString);
        titleBuilder.append('.');
        titleBuilder.append(classString);
        titleBuilder.append("::");
        titleBuilder.append(methodString);
        
        if(issueHtml.contains("<strong>partially-tested</strong>"))
        	titleBuilder.append(". This method is partially tested");
        else titleBuilder.append(". This method is pseudo-tested");
        return titleBuilder.toString();
	}
	
	private String getHtmlDescription(String issueHtml) {
		return issueHtml.replaceFirst("<head>.*?</head>","")
				.replaceAll("<a href=.*?</a>","");
	}
	
	public static String  h2mu(String html) {
	  String result = html.replaceAll("<strong>","*")
					.replaceAll("</strong>","*");
	  result = result.replaceAll("<h1>","\nh2.").replaceAll("</h1>","\n\n");
	  result = result.replaceAll("<h2>","\nh3.").replaceAll("</h2>","\n\n");
	  result = result.replaceAll("<p>","\n\n").replaceAll("</p>","\n\n");
	  result = result.replaceAll("</li>","\n").replaceAll("<li>","* ");	
	  result.replaceAll("<.*?l>","\n\n");
	  while(result.contains("\n\n\n")) result = result.replaceAll("\n\n\n","\n\n");
	  result = result.replaceAll("<.*?>"," ");
	  return result;
	}
}
