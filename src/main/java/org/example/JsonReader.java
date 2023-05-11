package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.*;
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


    public static void main(String[] args) throws Exception {
        //  toRedJsonFeature();
        //toRedXMLFeature2();

        toRedXMLFeature3();
        //  createEditableBayesNet(toRedXMLFeature3());
        //  createEditableBayesNet(toRedXMLFeature2());

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

    public static ArrayList<Feature> toRedXMLFeature2() throws IOException, SAXException, ParserConfigurationException {

        File inputFile = new File("src/resources/Models-Prueba.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        ArrayList<Feature> features = new ArrayList<Feature>();


        // Obtener el elemento raíz
        Element root = doc.getDocumentElement();

        // Obtener los elementos en el orden especificado
        NodeList nodeList = root.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            System.out.println("Cantidad de elementos encontrados: " + nodeList.getLength() + nodesXml.toString());

            // Verificar si el nodo es un elemento
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                // Obtener el nombre de la etiqueta
                String tagName = element.getTagName();

                // Realizar operaciones según el nombre de la etiqueta
                if (tagName.equals("root")) {
                    System.out.println("++++");
                    NodeList rootList = doc.getElementsByTagName("root");
                    for (int ix = 1; ix < rootList.getLength(); ix++) {
                        Node nodeC = rootList.item(ix);
                        String labelParent = String.valueOf(((Element) nodeC).getParentNode().getNodeName());
                        String id = ((Element) nodeC).getAttribute("id");

                        if (nodeC.getNodeType() == Node.ELEMENT_NODE && id instanceof String) ;
                        {
                            String label = ((Element) nodeC).getAttribute("label");
                            String labelParente = String.valueOf(((Element) nodeC).getParentNode().getNodeName());
                            String type = ((Element) nodeC).getAttribute("type");


                            // "mxGeometry"
                            Element mxGeometry = (Element) ((Element) nodeC).getElementsByTagName("mxGeometry").item(0);
                            String x = mxGeometry.getAttribute("x");
                            String y = mxGeometry.getAttribute("y");
                            String width = mxGeometry.getAttribute("width");
                            String height = mxGeometry.getAttribute("height");

                            // node valor of xml
                            System.out.println("label: " + label);
                            System.out.println("labelParent: " + labelParent);
                            System.out.println("type: " + type);
                            //  System.out.println("id: " + id);
                            System.out.println("x: " + x);
                            System.out.println("y: " + y);
                            System.out.println("width: " + width);
                            System.out.println("height: " + height);


                            Feature feature = new Feature(label, labelParent, type, id, x, y, width, height, null, null);
                            features.add(feature);
                            //
                        }
                    }
                } else if (tagName.equals("abstract")) {
                    System.out.println("abtra");
//
                    // Obtener una lista de todos los elementos 'abstract'
                    NodeList abstractList = doc.getElementsByTagName("abstract");
                    for (int ia = 0; ia < abstractList.getLength(); ia++) {

                        Node nodeA = abstractList.item(ia);

                        if (nodeA.getNodeType() == Node.ELEMENT_NODE) {

                            String label = ((Element) nodeA).getAttribute("label");
                            String labelParent = String.valueOf(((Element) nodeA).getParentNode().getNodeName());
                            String type = ((Element) nodeA).getAttribute("type");
                            String id = ((Element) nodeA).getAttribute("id");

                            // "mxGeometry"
                            Element mxGeometry = (Element) ((Element) nodeA).getElementsByTagName("mxGeometry").item(0);
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


                            Feature feature = new Feature(label, labelParent, type, id, x, y, width, height, null, null);
                            features.add(feature);
                        }
                    }
                    //

                } else if (tagName.equals("concrete")) {
//
                    //

                } else if (tagName.equals("rel_concrete_root")) {
//
                    //

                } else if (tagName.equals("rel_abstract_root")) {
//
                    //

                } else if (tagName.equals("rel_concrete_abstract")) {
//
                    //

                }

            }
        }

        ///
        Element node = doc.getDocumentElement();

        // Verificar si el nodo es un elemento
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;

            // Realizar operaciones con el elemento
            System.out.println("Elemento&&: " + element.getTagName());

            // Recorrer los atributos del elemento
            NamedNodeMap attributes = element.getAttributes();
            for (int i = 0; i < attributes.getLength(); i++) {
                Node attribute = attributes.item(i);
                System.out.println(attribute.getNodeName() + ": &&" + attribute.getNodeValue());
            }

            System.out.println("-------------------------");
        }

        // Recorrer los hijos del nodo
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            System.out.println("childs" + child.getNodeName());

            {
                NodeList childrene = node.getChildNodes();
                for (int ie = 0; ie < children.getLength(); ie++) {
                    Node childe = childrene.item(ie);
                    System.out.println("childs" + childe.getNodeValue());
                }
            }
        }


        ///
        return features;
    }


    public static ArrayList<Feature> toRedXMLFeature3() throws IOException, SAXException, ParserConfigurationException {

        File inputFile = new File("src/resources/Models-Prueba.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();


        ArrayList<Feature> features = new ArrayList<Feature>();


        // Obtener una lista de todos los elementos 'root'
        NodeList rootList = doc.getElementsByTagName("root");
        for (int i = 1; i < rootList.getLength(); i++) {
            Node nodeC = rootList.item(i);
            String labelParent = String.valueOf(((Element) nodeC).getParentNode().getNodeName());
            String id = ((Element) nodeC).getAttribute("id");

            if (nodeC.getNodeType() == Node.ELEMENT_NODE && id instanceof String) ;
            {
                String label = ((Element) nodeC).getAttribute("label");
                String labelParente = String.valueOf(((Element) nodeC).getParentNode().getNodeName());
                String type = ((Element) nodeC).getAttribute("type");


                // "mxGeometry"
                Element mxGeometry = (Element) ((Element) nodeC).getElementsByTagName("mxGeometry").item(0);
                String x = mxGeometry.getAttribute("x");
                String y = mxGeometry.getAttribute("y");
                String width = mxGeometry.getAttribute("width");
                String height = mxGeometry.getAttribute("height");

                // node valor of xml
                System.out.println("label: " + label);
                System.out.println("labelParent: " + labelParent);
                System.out.println("type: " + type);
                //  System.out.println("id: " + id);
                System.out.println("x: " + x);
                System.out.println("y: " + y);
                System.out.println("width: " + width);
                System.out.println("height: " + height);


                Feature feature = new Feature(label, labelParent, type, id, x, y, width, height, null, null);
                features.add(feature);
                //
            }
        }


        // Obtener una lista de todos los elementos 'abstract'
        NodeList abstractList = doc.getElementsByTagName("abstract");
        for (int i = 0; i < abstractList.getLength(); i++) {
            //
            Node nodeA = abstractList.item(i);

            if (nodeA.getNodeType() == Node.ELEMENT_NODE) {

                String label = ((Element) nodeA).getAttribute("label");
                String labelParent = String.valueOf(((Element) nodeA).getParentNode().getNodeName());
                String type = ((Element) nodeA).getAttribute("type");
                String id = ((Element) nodeA).getAttribute("id");

                // "mxGeometry"
                Element mxGeometry = (Element) ((Element) nodeA).getElementsByTagName("mxGeometry").item(0);
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


                Feature feature = new Feature(label, labelParent, type, id, x, y, width, height, null, null);
                features.add(feature);
                //
            }
        }
//

        // Obtener una lista de todos los elementos 'concrete'
        NodeList concreteList = doc.getElementsByTagName("concrete");
        for (int i = 0; i < concreteList.getLength(); i++) {
            Node nodeC = concreteList.item(i);

            String id = ((Element) nodeC).getAttribute("id");
            if (nodeC.getNodeType() == Node.ELEMENT_NODE && !id.contains("clon")) {

                String label = ((Element) nodeC).getAttribute("label");
                String labelParent = String.valueOf(((Element) nodeC).getParentNode().getNodeName());
                String type = ((Element) nodeC).getAttribute("type");
                // "mxGeometry"
                Element mxGeometry = (Element) ((Element) nodeC).getElementsByTagName("mxGeometry").item(0);
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


                Feature feature = new Feature(label, labelParent, type, id, x, y, width, height, null, null);
                features.add(feature);
                //
            }
        }


        NodeList relatioAbsList = doc.getElementsByTagName("rel_concrete_abstract");
        System.out.println("Cantidad de elementos encontrados: " + relatioAbsList.getLength());

        for (int i = 0; i < relatioAbsList.getLength(); i++) {
            Node node = relatioAbsList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String id = ((Element) node).getAttribute("id");
                String type = ((Element) node).getAttribute("type");

                Node mxCellNode = element.getElementsByTagName("mxCell").item(0);

                if (mxCellNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element mxCellElement = (Element) mxCellNode;
                    String source = mxCellElement.getAttribute("source");
                    String target = mxCellElement.getAttribute("target");

                    System.out.println("Source: " + source + " Target: " + target + "type" + type + "id" + id);
                    Feature feature = new Feature(null, null, type, id, null, null, null, null, source, target);
                    features.add(feature);
                }
            }
        }

//
        NodeList relationConcList = doc.getElementsByTagName("rel_concrete_root");
        System.out.println("Cantidad de elementos encontrados: " + relationConcList.getLength());

        for (int i = 0; i < relationConcList.getLength(); i++) {
            Node node = relationConcList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String id = ((Element) node).getAttribute("id");
                String type = ((Element) node).getAttribute("type");

                Node mxCellNode = element.getElementsByTagName("mxCell").item(0);

                if (mxCellNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element mxCellElement = (Element) mxCellNode;
                    String source = mxCellElement.getAttribute("source");
                    String target = mxCellElement.getAttribute("target");

                    System.out.println("Source: " + source + " Target: " + target + "type" + type + "id" + id);
                    Feature feature = new Feature(null, null, type, id, null, null, null, null, source, target);
                    features.add(feature);
                }
            }
        }
        //

        //
        NodeList relationRooList = doc.getElementsByTagName("rel_concrete_root");
        System.out.println("Cantidad de elementos encontrados: " + relationRooList.getLength());

        for (int i = 0; i < relationRooList.getLength(); i++) {
            Node node = relationRooList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String id = ((Element) node).getAttribute("id");
                String type = ((Element) node).getAttribute("type");

                Node mxCellNode = element.getElementsByTagName("mxCell").item(0);

                if (mxCellNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element mxCellElement = (Element) mxCellNode;
                    String source = mxCellElement.getAttribute("source");
                    String target = mxCellElement.getAttribute("target");

                    System.out.println("Source: " + source + " Target: " + target + "type" + type + "id" + id);
                    Feature feature = new Feature(null, null, type, id, null, null, null, null, source, target);
                    features.add(feature);
                }
            }
        }
        //

        System.out.println("Fin del procesamiento!!!");


        return features;
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


    public static void createEditableBayesNet(ArrayList<Feature> features) throws Exception {


        String tmpfilename = "src/resources/library3.xml";
        FileWriter outfile = new FileWriter(tmpfilename);

        EditableBayesNet m_BayesNet = new EditableBayesNet(true);
        // BayesNetGenerator generator = new BayesNetGenerator();
        //  BIFReader bifReader = new BIFReader();


        for (Feature feature : features) {

            //   bifReader.processString(m_BayesNet.toXMLBIF03());
            System.out.println("**************************************************");
            System.out.println("Label===========0: " + feature.getLabel());
            System.out.println("LabelParent===========: " + feature.getLabelParent());
            System.out.println("Type:============= " + feature.getType());
            System.out.println("ID: =========0" + feature.getId());
            System.out.println("X: =======0" + feature.getX());
            System.out.println("Y:=======00 " + feature.getY());
            System.out.println("Width:=====00 " + feature.getWidth());
            System.out.println("Height: ==========00" + feature.getHeight());
            System.out.println("**************************************************");

            if (feature.getLabelParent() != null) {
                int nCardinality = Integer.parseInt(feature.getId());
                m_BayesNet.addNode(feature.getLabelParent(), 1);

            }

          /*  if (feature.getLabelParent() == feature.getType().contains("")) {
                int nCardinality = Integer.parseInt(feature.getId());
                m_BayesNet.addNode(feature.getLabelParent(), nCardinality);

            }*/

            int nPosX = Integer.parseInt(feature.getX());
            int nPosY = Integer.parseInt(feature.getY());


            m_BayesNet.addNode(feature.getLabel(), 1, nPosX, nPosY);
            m_BayesNet.addArc(feature.getLabelParent(), feature.getLabel());
            // m_BayesNet.estimateCPTs();


            System.out.println(" m_BayesNet4---->>>" + m_BayesNet.toXMLBIF03());
            System.out.println("Documento XML creado correctamente.");

        }
        outfile.write(m_BayesNet.toXMLBIF03());

        outfile.close();
    }


}