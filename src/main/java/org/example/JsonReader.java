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

    public static void main(String[] args) throws Exception {
        //  toRedJsonFeature();
        toRedXMLFeature3();
        createEditableBayesNet(toRedXMLFeature3());

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

    public static ArrayList<Feature> toRedXMLFeature3() throws IOException, SAXException, ParserConfigurationException {

        File inputFile = new File("src/resources/Models-ClothingStoreMinimize.xml");
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
                System.out.println("----------------------R------------------------------");

                Feature feature = new Feature(label, labelParent, type, id, x, y, width, height, null, null);
                features.add(feature);

            }
        }

        // Obtener una lista de todos los elementos 'abstract'
        NodeList abstractList = doc.getElementsByTagName("abstract");
        for (int i = 0; i < abstractList.getLength(); i++) {

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
                System.out.println("------------------------A2----------------------------");

                Feature feature = new Feature(label, labelParent, type, id, x, y, width, height, null, null);
                features.add(feature);

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
                System.out.println("------------------------C----------------------------");

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

        NodeList relationConcConcList = doc.getElementsByTagName("rel_concrete_concrete");
        System.out.println("Cantidad de elementos encontrados: " + relationConcConcList.getLength());

        for (int i = 0; i < relationConcConcList.getLength(); i++) {
            Node node = relationConcConcList.item(i);

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
        System.out.println("Fin del procesamiento leer xml!!!");


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

    }

    public static void createEditableBayesNet(ArrayList<Feature> features) throws Exception {


        String tmpfilename = "src/resources/library3.xml";
        FileWriter outfile = new FileWriter(tmpfilename);

        EditableBayesNet m_BayesNet = new EditableBayesNet(true);


        for (Feature feature : features) {
            System.out.println("Label======: " + feature.getLabel());
            System.out.println("LabelParent=: " + feature.getLabelParent());
            System.out.println("Type:========" + feature.getType());
            System.out.println("ID: =========" + feature.getId());
            System.out.println("X: =======" + feature.getX());
            System.out.println("Y:======= " + feature.getY());
            System.out.println("Width:===== " + feature.getWidth());
            System.out.println("Height: ==========" + feature.getHeight());
            System.out.println("source: ==========" + feature.getSource());
            System.out.println("Target: ==========" + feature.getTarget());

            //inicio creacion nodos...NODE->" + feature.getLabel());

            if (feature.getType() != "relation" && feature.getLabel() != null) {
                int nPosX = Integer.parseInt(feature.getX());
                int nPosY = Integer.parseInt(feature.getY());
                m_BayesNet.addNode(feature.getId(), 2, nPosX, nPosY);


            } else if (feature.getLabel() == null) {

                System.out.println("creacion arcos" + feature.getTarget());
                m_BayesNet.addArc(feature.getTarget(), feature.getSource());
                double[][] matrize = m_BayesNet.getDistribution(feature.getTarget());
                System.out.println("Es......." + matrize.length + matrize);

                for (int i = 0; i < matrize.length; i++) {

                    for (int j = 0; j < matrize[i].length; j++) {
                        System.out.print(matrize[i][j] + " ");
                    }
                    System.out.println();
                }
                m_BayesNet.setDistribution(feature.getTarget(), matrize);

            } else {
                break; // Finaliza el bucle cuando se encuentra un nodo nulo
            }

        }
        outfile.write(m_BayesNet.toXMLBIF03());
        System.out.println("Documento XML creado correctamente.");
        outfile.close();
    }

    public static void editCPT(String nTargetNode) throws Exception {
        int nTargetNodee = Integer.parseInt(nTargetNode);
        EditableBayesNet m_BayesNet = new EditableBayesNet(true);
        System.out.println("nTargetNode-->" + nTargetNode);

        // int nParents= m_BayesNet.getNrOfParents(Integer.parseInt(nTargetNode));

        m_BayesNet.estimateCPTs();


    }

}