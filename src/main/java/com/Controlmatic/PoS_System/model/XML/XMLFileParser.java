package com.Controlmatic.PoS_System.model.XML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringBufferInputStream;
@Deprecated
public class XMLFileParser {

    private static String bonusCardNumber;
    private static String bonusState;
    private static String paymentCardNumber;
    private static String paymentState;
    private static String paymentCardType;
    private static String xmlString;
    private static String productName;
    private static String productBarcode;
    private static String productVat;
    private static String[] productKeywords;

    public static void parseString(String xmlString) {
        try {
            //File inputFile = new File("input.txt");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new StringBufferInputStream(xmlString));
            doc.getDocumentElement().normalize();
            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            //make conditional shit or smt
            if(doc.getElementsByTagName("result").getLength() > 0) {
                parseCardPaymentRequest(xmlString, doc);
            } else if(doc.getElementsByTagName("product").getLength() > 0) {
                parsePCRequest(xmlString, doc);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //gets the card properties from a parsed XML file
    private static void parseCardPaymentRequest(String xmlString, Document doc) {
        NodeList nList = doc.getElementsByTagName("result");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            //System.out.println("\nCurrent Element :" + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                bonusCardNumber = xmlString.contains("bonusCardNumber") ?
                        eElement.getElementsByTagName("bonusCardNumber").item(0).getTextContent() : "";
                bonusState = xmlString.contains("bonusState") ?
                        eElement.getElementsByTagName("bonusState").item(0).getTextContent() : "";
                paymentCardNumber = xmlString.contains("paymentCardNumber") ?
                        eElement.getElementsByTagName("paymentCardNumber").item(0).getTextContent() : "";
                paymentState = xmlString.contains("paymentState") ?
                        eElement.getElementsByTagName("paymentState").item(0).getTextContent() : "";
                paymentCardType = xmlString.contains("paymentCardType") ?
                        eElement.getElementsByTagName("paymentCardType").item(0).getTextContent() : "";
            }
        }
    }

    private static void parsePCRequest(String xmlString, Document doc) {
        NodeList nList = doc.getElementsByTagName("product");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                productBarcode = xmlString.contains("barCode") ?
                        eElement.getElementsByTagName("barCode").item(0).getTextContent() : "";
                productName = xmlString.contains("name") ?
                        eElement.getElementsByTagName("name").item(0).getTextContent() : "";
                productVat = xmlString.contains("vat") ?
                        eElement.getElementsByTagName("vat").item(0).getTextContent() : "";
                if(!xmlString.contains("keyword"))
                    return;

                productKeywords = new String[eElement.getElementsByTagName("keyword").getLength()];
                for (int j = 0; j < eElement.getElementsByTagName("keyword").getLength(); j++) {
                    productKeywords[j] = eElement.getElementsByTagName("keyword").item(j).getTextContent();
                }
            }
        }
    }

    public static String getCardNumber() {
        return paymentCardNumber;
    }

    public static String getBonusCardNumber() {
        return bonusCardNumber;
    }

    public static String getBonusState() {
        return bonusState;
    }

    public static String getPaymentState() {
        return paymentState;
    }

    public static String getPaymentCardType() {
        return paymentCardType;
    }

    public static String getProductName() {
        return productName;
    }

    public static String getProductBarcode() {
        return productBarcode;
    }

    public static String getProductVat() {
        return productVat;
    }

    public static String[] getProductKeywords() {
        return productKeywords;
    }
}
