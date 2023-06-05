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
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;

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
        String tmpfilename = "src/resources/TresNdWeka.xml";
        toRedJsonFeature();
        // toRedXMLFeature3();
        // createEditableBayesNet(toRedXMLFeature3());
        //__  createInstancesFromXML(tmpfilename);
        //posterioPro();
        XMLBIFLoader.loadInstancesFromXML(tmpfilename);
        // posterioREad();


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

    public static ArrayList<Feature> toRedXMLFeature() throws IOException, SAXException, ParserConfigurationException {

        File inputFile = new File("src/resources/Models-TresNodos.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();

        ArrayList<Feature> features = new ArrayList<Feature>();
        // Obtener una lista de todos los elementos 'root'
        NodeList rootList = doc.getElementsByTagName("root");
        for (int i = 1; i < rootList.getLength(); i++) {

            Node nodeC = rootList.item(i);
            String id = ((Element) nodeC).getAttribute("id");

            if (nodeC.getNodeType() == Node.ELEMENT_NODE && id instanceof String) ;
            {
                String label = ((Element) nodeC).getAttribute("label");
                String type = ((Element) nodeC).getAttribute("type");
                String labelParent = String.valueOf(((Element) nodeC).getParentNode().getNodeName());

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
//
              /*  System.out.println("DATOS NODO" + feature.getTarget());
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList = m_BayesNet.getChildren(Integer.parseInt(feature.getTarget()));
                // Crear una instancia de BayesNet (m_BayesNet)
                BayesNet m_BayesNete = m_BayesNet;
// Obtener el número total de nodos en el modelo Bayesiano
                int totalNodes = m_BayesNete.getNrOfNodes();
// Recorrer cada nodo para verificar si tiene hijos
                for (int node = 0; node < totalNodes; node++) {
                    // Obtener los padres del nodo actual
                    int[] parents = m_BayesNete.getParentSet(node).getParents();
                  //  System.out.println("parents va: " + parents);

                    for (int i = 0; i < parents.length; i++) {

                      //  System.out.println("parents"+ parents +i);
                    }
                    // Verificar si el nodo actual no tiene hijos
                    if (m_BayesNet.getChildren(node).size() == 0) {
                        System.out.println("Nodo sin hijos: " + node);
                    }
                }
                for (int i = 0; i < arrayList.size(); i++) {
                    int elemento = arrayList.get(i);
                    System.out.println(elemento + "oe!!");
                }*/

                //
                System.out.println("CPT");

                /*
                double[][] matrize = m_BayesNet.getDistribution(feature.getTarget());

                for (int i = 0; i < matrize.length; i++) {

                    for (int j = 0; j < matrize[i].length; j++) {
                        if (i == 0) {

                             double[][] distribution = {
                                    {0.2, 0.8},   // Probabilidades condicionales para A = 0
                                    {0.6, 0.4}    // Probabilidades condicionales para A = 1
                            };
                        }
                        matrize[i][j]=1.5;
                        System.out.print(matrize[i][j] + " ");
                        m_BayesNet.setDistribution(feature.getTarget(), matrize);

                    }
                    m_BayesNet.setDistribution(feature.getTarget(), matrize);
                    System.out.println("-------");
                }

*/
//
                System.out.println("----inicio---");
                final double[][] m_fProbs;
                System.out.println("----2---");
                double[][] probs = m_BayesNet.getDistribution(feature.getTarget());
                System.out.println("---get----");
                m_fProbs = new double[probs.length][probs[0].length];
                System.out.println("---3----");
                for (int i = 0; i < probs.length; i++) {
                    System.out.println("---f1----");
                    for (int j = 0; j < probs[0].length; j++) {
                        System.out.println("---f2----");
                        System.out.println("1-------->" + m_fProbs[i][j]);
                        probs[i][j] = 0.1;
                        m_fProbs[i][j] = probs[i][j];
                        m_BayesNet.setDistribution(feature.getTarget(), m_fProbs);

                        System.out.println("2-------->" + m_fProbs[i][j]);
                        probs[i][j] = 0.1;
                        m_fProbs[i][j] = probs[i][j];
                        System.out.println("settt");
                        m_BayesNet.setDistribution(feature.getTarget(), m_fProbs);
                        System.out.println("postsettt");
                    }
                }

                //
            } else {
                break; // Finaliza el bucle cuando se encuentra un nodo nulo
            }

        }


        outfile.write(m_BayesNet.toXMLBIF03());
        System.out.println("Documento XML creado correctamente.");
        outfile.close();
    }

    public static Instances createInstancesFromXML(String xmlFilePath) {


        try {
            // Crear un objeto DocumentBuilder para procesar el archivo XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            System.out.println("1");
            // Leer el archivo XML y convertirlo en un objeto Document
            Document doc = builder.parse(xmlFilePath);

            // Obtener el elemento raíz del documento XML
            Element root = doc.getDocumentElement();
            System.out.println("2");
            // Crear los atributos para las instancias
            FastVector attributes = new FastVector();


            // Recorrer los nodos de variables
            NodeList variableNodes = root.getElementsByTagName("VARIABLE");
            for (int i = 0; i < variableNodes.getLength(); i++) {
                Element variable = (Element) variableNodes.item(i);
                System.out.println("3");
                // Obtener el nombre de la variable
                String variableName = variable.getAttribute("NAME");

                // Obtener los posibles valores de la variable
                NodeList valueNodes = variable.getElementsByTagName("OUTCOME");
                FastVector attributeValues = new FastVector(valueNodes.getLength());
                for (int j = 0; j < valueNodes.getLength(); j++) {
                    Element value = (Element) valueNodes.item(j);
                    String valueName = value.getAttribute("NAME");
                    attributeValues.addElement(valueName);
                    System.out.println("posibles valores" + valueNodes.item(j));
                }
                System.out.println("4");
                // Crear el atributo y agregarlo a la lista de atributos
                Attribute attribute = new Attribute(variableName, attributeValues);
                System.out.println("4.5");
                attributes.addElement(attribute);
            }

            // Crear el objeto Instances con los atributos
            Instances instances = new Instances("BayesNetInstances", attributes, 0);

            // Recorrer los nodos de datos y agregar instancias
            NodeList dataNodes = root.getElementsByTagName("DEFINITION");
            for (int i = 0; i < dataNodes.getLength(); i++) {
                Element data = (Element) dataNodes.item(i);
                System.out.println("5" + dataNodes.item(i));
                // Crear una nueva instancia
                //Instance instance = new Instance(attributes.size());

                // Obtener los valores de los atributos para la instancia
                NodeList valueNodes = data.getElementsByTagName("VALUE");
                for (int j = 0; j < valueNodes.getLength(); j++) {
                    Element value = (Element) valueNodes.item(j);
                    int attributeIndex = j;
                    int attributeValueIndex = attributeIndex;
                    System.out.println("6");
                    // Establecer el valor del atributo
                }
                System.out.println("7");
            }
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
        System.out.println("instancia generadas");
        return null;
    }


    public static void posterioREad() throws Exception {
        System.out.println("a1");

        String tmpfilename = "src/resources/TresNdWeka.xml";
        System.out.println("a3");

//        Instances instances = new Instances(new FileReader(tmpfilename));

        // Crear un objeto EditableBayesNet
        ;
        Instances m_Instances = null;
        EditableBayesNet m_BayesNet = new EditableBayesNet(true);

        m_BayesNet.buildClassifier(m_Instances);

        System.out.println("HOLIS");
        double[][] posteriorProbabilities = m_BayesNet.distributionsForInstances(m_Instances);
        // Imprimir las probabilidades posteriores
        for (int i = 0; i < posteriorProbabilities.length; i++) {
            System.out.println("Probabilidad posterior para la clase " + i + ": " + posteriorProbabilities[i]);
        }
        m_BayesNet.clearUndoStack();


        // Ruta del archivo XML


// Cargar el archivo XML en un objeto EditableBayesNet
      /*  BIFLoader loader = new BIFLoader();
        loader.setSource(new DataSource(tmpfilename));
        EditableBayesNet bayesNet = loader.getBayesNet();

        // Agregar nodos y definir la estructura del grafo en m_BayesNet

        // Convertir a BayesNet
        BayesNet bayesNet = new BayesNet();

        bayesNet=m_BayesNet;
*/
        // Generar instancias a partir de BayesNet
        BayesNetGenerator generator = new BayesNetGenerator();
        // Instances instances = generator.set
        //generator.setData();
        // generator.setBayesNet(bayesNet);


        // Imprimir las instancias
        // System.out.println(instances);

        System.out.println("a4");
        //m_BayesNet = new EditableBayesNet(instances);
        System.out.println("a5");
/*
       // double[] posteriorProbabilities = m_BayesNet.distributionForInstance(instances);
        System.out.println("a6");
        double[][] posteriorProbabilities = m_BayesNet.distributionsForInstances(instances);
        System.out.println("a7");
// Imprimir las probabilidades posteriores
        for (int i = 0; i < posteriorProbabilities.length; i++) {

            System.out.println("Probabilidad posterior para la clase " + i + ": " + posteriorProbabilities[i]);
        }
*/

    }
    /*
    public static void posterioPro() throws Exception {
        System.out.println("1");
        // Crear una instancia de BayesNet
        BayesNet m_BayesNet = new BayesNet();
        System.out.println("2");
// Cargar el modelo de la red bayesiana desde un archivo, si es necesario
        //  m_BayesNet = (BayesNet) SerializationHelper.read("src/resources/library3.xml");
        System.out.println("3");
// Crear una instancia de tipo Instances para representar los datos
        Instances m_Instances = null;
        System.out.println("4");
        String sFileName = "src/resources/library3.xml";
        System.out.println("5");
        Instances instances = new Instances(new FileReader(sFileName));
        System.out.println("6");
        Instances ale = new Instances(new FileReader(sFileName));
        System.out.println("7");
        m_BayesNet = new EditableBayesNet(instances);
        System.out.println("8");
        m_Instances = instances;
        String archivoXML = "ruta_del_archivo.xml";


        System.out.println("9");
// Establecer los valores observados (evidencia) en la instancia de datos
        Instance instance = new DenseInstance(12);
        System.out.println("10");
        instance.setDataset(ale);
        //instance.setValue(attributeIndex, value);  // Establecer el valor para cada atributo en la instancia
        System.out.println("11");
// Calcular la probabilidad posterior
        double[] posteriorProbabilities = m_BayesNet.distributionForInstance(instance);
        System.out.println("12");
// Imprimir las probabilidades posteriores
        for (int i = 0; i < posteriorProbabilities.length; i++) {
            System.out.println("Probabilidad posterior para la clase " + i + ": " + posteriorProbabilities[i]);
        }


    }
*/
}