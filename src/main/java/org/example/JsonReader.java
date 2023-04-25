package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import weka.classifiers.bayes.net.BIFReader;
import weka.classifiers.bayes.net.BayesNetGenerator;
import weka.classifiers.bayes.net.EditableBayesNet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class JsonReader {

    public static void main(String[] args) throws Exception {
        toRedJsonFeature();
        toRedXMLFeature();
        createEditableBayesNet();
    }

    public static void toRedJsonFeature() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader("src/resources/dosNodosJSON.json");
        JSONObject jsonObject = (JSONObject) parser.parse(reader);

        JSONArray arrayProducLine = (JSONArray) jsonObject.get("productLines");

        for (int i = 0; i < arrayProducLine.size(); i++) {
            JSONObject jsonObjetDominioEnginer = (JSONObject) arrayProducLine.get(i);
            String name = (String) jsonObjetDominioEnginer.get("arrayProducLine");

            JSONObject dominios = (JSONObject) jsonObjetDominioEnginer.get("domainEngineerin");

            System.out.println("DomainEngineering: " + jsonObjetDominioEnginer.get("domainEngineering"));

            for (int e = 0; e < jsonObjetDominioEnginer.size(); e++) {
                JSONArray arrayModel = (JSONArray) jsonObjetDominioEnginer.get("model");

                System.out.println("arrayModel->" + e + arrayModel);
                for (int x = 0; x < jsonObjetDominioEnginer.size(); x++) {

                    JSONArray arrayElements = (JSONArray) jsonObjetDominioEnginer.get("elements");
                    System.out.println(" Elements->" + arrayElements);
                }
            }
            System.out.println("elements: " + name + arrayProducLine.toString());
        }
    }

    public static void toRedXMLFeature() throws IOException, SAXException, ParserConfigurationException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        File inputFile = new File("src/resources/Models-testDaniel.xml");
        Document documentXML = builder.parse(inputFile);

        documentXML.getDocumentElement().normalize();
        System.out.println("documentXML-->" + documentXML.getDocumentElement().getNodeName());

        Element root2 = documentXML.getDocumentElement();

        NodeList childNodes = documentXML.getElementsByTagName("root");


        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            System.out.println("ChildNodes-->" + node.getNodeName());

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                NodeList childChildNodes = element.getChildNodes();

                for (int e = 0; e < childChildNodes.getLength(); e++) {
                    Node nodeHijo = childChildNodes.item(e);

                    if (nodeHijo.getNodeType() == Node.ELEMENT_NODE) {
                        System.out.println("Name->" + nodeHijo.getNodeName());
                        System.out.println("Attributes->" + nodeHijo.getAttributes().getNamedItem("label"));
                        System.out.println("NodeType-> " + nodeHijo.getNodeType());
                        System.out.println("NodeValue->" + nodeHijo.getNodeValue());
                        System.out.println("ParentNode->" + nodeHijo.getParentNode());
                    }
                }
            }
        }
    }


    public static void generateRandonBayesNet() throws Exception {

        EditableBayesNet m_BayesNet = new EditableBayesNet(true);
        BayesNetGenerator generator = new BayesNetGenerator();
        m_BayesNet = generator;
        generator.generateRandomNetwork();
        BIFReader bifReader = new BIFReader();
        bifReader.processString(m_BayesNet.toXMLBIF03());
        System.out.println(" m_BayesNet3---->>>" + m_BayesNet.toXMLBIF03());


    }


    public static void createEditableBayesNet() throws Exception {

        String tmpfilename = "src/resources/library3.xml";
        FileWriter outfile = new FileWriter(tmpfilename);

        EditableBayesNet m_BayesNet = new EditableBayesNet(true);
        BayesNetGenerator generator = new BayesNetGenerator();

        BIFReader bifReader = new BIFReader();
        bifReader.processString(m_BayesNet.toXMLBIF03());
        System.out.println(" m_BayesNet3---->>>" + m_BayesNet.toXMLBIF03());

        m_BayesNet.addNode("TestPruebaA", 1, 100, 100);
        m_BayesNet.addNode("TestPruebaB", 1, 100, 100);
        m_BayesNet.addArc("TestPruebaA", "TestPruebaB");
        // m_BayesNet.estimateCPTs();

        outfile.write(m_BayesNet.toXMLBIF03());
        outfile.close();
        System.out.println(" m_BayesNet4---->>>" + m_BayesNet.toXMLBIF03());
        System.out.println("Documento XML creado correctamente.");
    }


}