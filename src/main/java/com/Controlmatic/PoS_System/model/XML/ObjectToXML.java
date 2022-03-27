package com.Controlmatic.PoS_System.model.XML;

import javax.xml.bind.*;
import java.io.StringBufferInputStream;
import java.io.StringWriter;


/**
 * Responsible for mapping an Object to and from XML-schemas
 */
public class ObjectToXML {

    /**
     * For an object to be marshalled it needs to declare (at least) an @XMLRootElement
     * and its fields need to correspond to the XML output you want.
     * Default @XMLAccessorType is XmlAccessType.PUBLIC_MEMBER, another reasonable choice would be XmlAccessType.FIELD
     * @param tClass The Class of your object
     * @param object The instance of your object
     * @return your object marshalled to an XML-string
     */
    public static <T> String marshal(Class<T> tClass, T object) {
        Marshaller m;
        StringWriter sw = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(tClass);
            m = jaxbContext.createMarshaller();
            m.marshal(object, sw);
        } catch (JAXBException e) {
            System.out.println(e.getLocalizedMessage());
            return null;
        }

        if(!sw.toString().contains("?>")) {
            return sw.toString();
        }
        return sw.toString().substring(sw.toString().indexOf("?>")+2);
    }

    /**
     * For an object to be unmarshalled it needs to declare (at least) an @XMLRootElement
     * and its fields need to correspond to the XML input you give
     * @param tClass The Class which you want to instantiate using the xmlString
     * @param xmlString The XML-String containing information relevant to your Class
     * @return an instance of the Class with information taken from the XML-String
     */
    public static <T> T unmarshal(Class<T> tClass, String xmlString) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(tClass);
            Unmarshaller m = jaxbContext.createUnmarshaller();
            return tClass.cast(m.unmarshal(new StringBufferInputStream(xmlString)));
        } catch (ClassCastException | JAXBException e) {
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }

}
