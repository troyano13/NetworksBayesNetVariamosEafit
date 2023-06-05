package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import weka.classifiers.bayes.net.EditableBayesNet;
import weka.core.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLBIFLoader {
    public static Instances loadInstancesFromXML(String xmlFilePath) throws Exception {
        // Crear un objeto Instances
        Instances instances = null;
        System.out.println("1w");
        // Crear el parser del documento XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        System.out.println("2w");
        // Leer el archivo XML y parsearlo
        Document document = builder.parse(xmlFilePath);

        // Obtener la raíz del documento
        Element root = document.getDocumentElement();
        System.out.println("3w");
        // Obtener todos los elementos "VARIABLE"
        NodeList variableNodes = root.getElementsByTagName("VARIABLE");

        // Crear los atributos en base a los elementos "VARIABLE"
        for (int i = 0; i < variableNodes.getLength(); i++) {
            Node variableNode = variableNodes.item(i);
            if (variableNode.getNodeType() == Node.ELEMENT_NODE) {
                Element variableElement = (Element) variableNode;
                String variableName = variableElement.getElementsByTagName("NAME").item(0).getTextContent();
                NodeList outcomeNodes = variableElement.getElementsByTagName("OUTCOME");
                System.out.println("4w");
                // Crear los posibles valores (outcomes) del atributo
                FastVector attributeValues = new FastVector();
                for (int j = 0; j < outcomeNodes.getLength(); j++) {
                    String outcomeValue = outcomeNodes.item(j).getTextContent();
                    attributeValues.addElement(outcomeValue);
                }

                // Crear el atributo y agregarlo a Instances si es el primero
                if (instances == null) {
                    instances = new Instances("dataset", new FastVector(), 0);
                }
                Attribute attribute = new Attribute(variableName, attributeValues);
                instances.insertAttributeAt(attribute, instances.numAttributes());
            }
        }
        System.out.println("5w");
        // Obtener todos los elementos "DEFINITION"
        NodeList definitionNodes = root.getElementsByTagName("DEFINITION");

        // Agregar las instancias en base a los elementos "DEFINITION"
        Instance instance = null;
        for (int i = 0; i < definitionNodes.getLength(); i++) {
            Node definitionNode = definitionNodes.item(i);
            if (definitionNode.getNodeType() == Node.ELEMENT_NODE) {
                Element definitionElement = (Element) definitionNode;
                NodeList forNodes = definitionElement.getElementsByTagName("FOR");
                NodeList tableNodes = definitionElement.getElementsByTagName("TABLE");
                System.out.println("6w" + i);
                // Obtener el nombre del atributo
                String attributeName = forNodes.item(0).getTextContent();
                System.out.println("6w" + i + attributeName);
                // Obtener el índice del atributo en Instances
                int attributeIndex = instances.attribute(attributeName).index();

                // Obtener la tabla de probabilidades
                String[] tableValues = tableNodes.item(0).getTextContent().split("\\s+");

                // Crear la instancia y establecer los valores
                instance = new DenseInstance(instances.numAttributes());
                instance.setDataset(instances);
                instance.setMissing(attributeIndex);
                System.out.println("7w");
                for (int j = 0; j < tableValues.length; j++) {
                    System.out.println("7aaw");
                    // double probability = Double.parseDouble(tableValues[j]);
                    System.out.println("7aw");
                    instance.setValue(attributeIndex, j);
                    System.out.println("7bw");
                }

                // Agregar la instancia a Instances
                instances.add(instance);
            }
            System.out.println("8w");
        }

//qqui probando
        EditableBayesNet m_BayesNet = new EditableBayesNet(true);

        instances = m_BayesNet.m_Instances;

        if (instances != null) {
            System.out.println("8aw");
// Asignar las instancias y el atributo de clase
            for (int i = 0; i < instances.size(); i++) {
                for (int j = 0; j < instances.size(); j++) {
                    Instance posterior = instances.get(i);
                    System.out.println("instancia " + i + " y la clase " + j + ": " + posterior);
                }}
           // m_BayesNet.setData(instances);


            int classIndex = instances.numAttributes() - 1; // El índice del último atributo es el atributo de clase
            System.out.println("8bw");
// Construir el clasificador
            m_BayesNet.buildClassifier(instances);
            System.out.println("8fw");
// Calcular la probabilidad posterior
            double[][] posteriorProbabilities = m_BayesNet.distributionsForInstances(instances);
            System.out.println("8fw");
// Imprimir las probabilidades posteriores
            for (int i = 0; i < posteriorProbabilities.length; i++) {
                for (int j = 0; j < posteriorProbabilities[i].length; j++) {
                    double posterior = posteriorProbabilities[i][j];
                    System.out.println("Probabilidad posterior para la instancia " + i + " y la clase " + j + ": " + posterior);
                }}

            // Resto del código
        } else {
            // Manejo de la situación cuando las instancias son nulas
            System.out.println("Las instancias son nulas. Verifica la carga de datos.");
        }


        return instances;
    }
}
