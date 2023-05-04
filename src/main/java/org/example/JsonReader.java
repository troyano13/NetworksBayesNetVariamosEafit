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
import java.util.ArrayList;


public class JsonReader {

   public static ArrayList<Object> nodesXml = new ArrayList<Object>();
    private ArrayList<Object> Objectov;


    public   static void main(String[] args) throws Exception {
        //  toRedJsonFeature();
        toRedXMLFeature2();
        createEditableBayesNet( nodesXml);
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

    public static ArrayList<Object> toRedXMLFeature2() throws IOException, SAXException, ParserConfigurationException {

        File inputFile = new File("src/resources/Models-testDaniel.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();

        NodeList nodeList = doc.getElementsByTagName("abstract");

        ArrayList<Object> nodeXml = new ArrayList<Object>();


        // Node list
        for (int temp = 0; temp < nodeList.getLength(); temp++) {
            Node node = nodeList.item(temp);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                String label = ((Element) node).getAttribute("label");
                String labelParent = String.valueOf(((Element) node).getParentNode().getNodeName());
                String type = ((Element) node).getAttribute("type");
                String id = ((Element) node).getAttribute("id");

                // "mxGeometry"
                Element mxGeometry = (Element) ((Element) node).getElementsByTagName("mxGeometry").item(0);
                String x = mxGeometry.getAttribute("x");
                String y = mxGeometry.getAttribute("y");
                String width = mxGeometry.getAttribute("width");
                String height = mxGeometry.getAttribute("height");

                // node valor of xml
                System.out.println("label: " + label);
                System.out.println("labelParent: " + labelParent);
                System.out.println("type: " + type);
                System.out.println("id: " + id);
                System.out.println("x: " + x);
                System.out.println("y: " + y);
                System.out.println("width: " + width);
                System.out.println("height: " + height);


                nodeXml.add("label: " + label);
                nodeXml.add("labelParent: " + labelParent);
                nodeXml.add("type: " + type);
                nodeXml.add("id: " + id);
                nodeXml.add("x: " + x);
                nodeXml.add("y: " + y);
                nodeXml.add("width: " + width);
                nodeXml.add("height: " + height);
                System.out.println("  " + nodeXml);

                return nodeXml;
            }
        }
        return nodeXml;
    }


    public static void generateRandonBayesNet() throws Exception {

        EditableBayesNet m_BayesNet = new EditableBayesNet(true);
        BayesNetGenerator generator = new BayesNetGenerator();
        m_BayesNet = generator;
        generator.generateRandomNetwork();
        BIFReader bifReader = new BIFReader();
        bifReader.processString(m_BayesNet.toXMLBIF03());
        System.out.println(" m_BayesNet3---->>>" + m_BayesNet.toXMLBIF03());

        // obtein nodes
        // String[] options = new String[m_BayesNet.getNrOfNodes()];
        //


    }



    public static void createEditableBayesNet(ArrayList<Object> nodesXml) throws Exception {


        System.out.println("***"+nodesXml);



        String tmpfilename = "src/resources/library3.xml";
        FileWriter outfile = new FileWriter(tmpfilename);

        EditableBayesNet m_BayesNet = new EditableBayesNet(true);
        BayesNetGenerator generator = new BayesNetGenerator();

        BIFReader bifReader = new BIFReader();
        bifReader.processString(m_BayesNet.toXMLBIF03());
        System.out.println(" m_BayesNet3---->>>" + m_BayesNet.toXMLBIF03());

        // m_BayesNet.addNode(nodesXml.get());

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
