/*******************************************************************************
 * Copyright (c) 2019 Atos
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 * 	Ricardo Jose Tejada Garcia (Atos) - main developer
 * 	Jesús Gorroñogoitia (Atos) - architect
 * Initially developed in the context of STAMP EU project https://www.stamp-project.eu
 *******************************************************************************/
package eu.stamp.eclipse.descartes.plugin.profile;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eu.stamp.eclipse.descartes.plugin.pom.AbstractDescartesPomParser;
/**
 * An instance of this class is responsible for keeping the information
 * in the template files/descartes_profile.xml, and generating a profile
 * node to append it to the pom document. 
 * @see eu.stamp.eclipse.descartes.plugin.profile.DescarteProfile
 * @see files/descartes_profile.xml
 */
public class DescartesInterDocumentNode {

	private final String name;
	
	private final boolean isText;
	
	private boolean isNewLine;
	
	private final String text;
	
	private final List<DescartesInterDocumentNode> childs;
	
	public DescartesInterDocumentNode(Node node) {
		
		isText = isTextNode(node);
		isNewLine = isNewLine(node);
		name = node.getNodeName();
		childs = new LinkedList<DescartesInterDocumentNode>();
		
		if(isText) text = AbstractDescartesPomParser.getTextContent(node);
		else {
			text = null;
			NodeList list = node.getChildNodes();
            for(int i = 0; i < list.getLength(); i++)
            	childs.add(new DescartesInterDocumentNode(list.item(i)));
		}
	}
	
	public Node conversion(Document document) {
		
		if(isNewLine) return document.createTextNode("\n");

		Node result = document.createElement(name);
		
		if(isText) {
		   result.appendChild(document.createTextNode(text));
           return result;
		}
		List<Node> childNodes = childrenConversion(document);
		for(Node childNode : childNodes)
			 result.appendChild(childNode);
		return result;
	}
	
	private boolean isTextNode(Node node) {
		
		NodeList list = node.getChildNodes();
		if(list == null) return false;
		if(list.getLength() != 1) return false;
		
		return (list.item(0).getNodeType() == Node.TEXT_NODE);
	}
	
	private boolean isNewLine(Node node) {
		
		if(node.getNodeType() != Node.TEXT_NODE) return false;
	    if(node.getNodeName().contains("#")) return true;
	    return false;
	}
	
	private List<Node> childrenConversion(Document document){
		
		List<Node> result = new LinkedList<Node>();
		for(DescartesInterDocumentNode child : childs)
			result.add(child.conversion(document));
		return result;
	}
}
