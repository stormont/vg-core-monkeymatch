package com.voyagegames.monkeymatch.helpers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LevelLoader {
	
	public final String background;
	public final float tokenScale;
	public final float spawnTime;
	public final int numRows;
	public final int numCols;
	public final float tokenX;
	public final float tokenY;
	public final List<String> tokens = new ArrayList<String>();
	public final List<GridElement> grids = new ArrayList<GridElement>();
	
	public LevelLoader(final String path) throws SAXException, IOException, ParserConfigurationException {
		final File xml = new File(path);
		final Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xml);
		
		doc.getDocumentElement().normalize();
		
		final NodeList globals = doc.getElementsByTagName("global");
		final NodeList tokens = doc.getElementsByTagName("tokens");
		final NodeList gridElements = doc.getElementsByTagName("gridelement");

		this.background = getTagValue("background", (Element)globals.item(0));
		this.tokenScale = Float.parseFloat(getTagValue("tokenscale", (Element)globals.item(0)));
		this.spawnTime = Float.parseFloat(getTagValue("spawntime", (Element)globals.item(0)));
		this.numRows = Integer.parseInt(getTagValue("numrows", (Element)globals.item(0)));
		this.numCols = Integer.parseInt(getTagValue("numcols", (Element)globals.item(0)));
		this.tokenX = Float.parseFloat(getTagValue("tokenx", (Element)globals.item(0)));
		this.tokenY = Float.parseFloat(getTagValue("tokeny", (Element)globals.item(0)));
 
		for (int i = 0; i < tokens.getLength(); ++i) {
			this.tokens.add(getTagValue("asset", (Element)tokens.item(i)));
		}
 
		for (int i = 0; i < gridElements.getLength(); ++i) {
			final Node node = gridElements.item(i);
		   
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				final Element e = (Element)node;
				final float xOffset = Float.parseFloat(getTagValue("xoffset", e));
				final float yOffset = Float.parseFloat(getTagValue("yoffset", e));
				this.grids.add(new GridElement(getTagValue("asset", e), xOffset, yOffset));
			}
		}
	}
	 
	private String getTagValue(final String tag, final Element element) {
		final NodeList children = element.getElementsByTagName(tag).item(0).getChildNodes();
		return ((Node)children.item(0)).getNodeValue();
	}

}
