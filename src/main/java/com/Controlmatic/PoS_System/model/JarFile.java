package com.Controlmatic.PoS_System.model;

import com.Controlmatic.PoS_System.api.JarHandler;
import org.apache.tomcat.jni.Proc;

import java.io.IOException;

public class JarFile {

    private static Process cardReaderJar;
    private static Process cashBoxJar;
    private static Process productCatalogJar;

    //Opens alls jars that the program needs
    public static void openJars() throws IOException {
        cardReaderJar = JarHandler.execCommand("java -Dserver.port=9002 -jar CardReader.jar");
        cashBoxJar = JarHandler.execCommand("java -Dserver.port=9001 -jar CashBox.jar");
        productCatalogJar = JarHandler.execCommand("java -Dserver.port=9003 -jar ProductCatalog.jar");
    }
    //TODO close jars
    //den här funkar int
    public static void closeJars() throws IOException {
        JarHandler.execCommand("java -Dserver.port=9002 -jar CardReader.jar stop");
        JarHandler.execCommand("java -Dserver.port=9002 -jar CashBox.jar stop");
    }

    //Getters for all the Jar files

    public static Process getCardReaderJar() {
        return cardReaderJar;
    }

    public static Process getCashBoxJar() {
        return cashBoxJar;
    }

    public static Process getProductCatalogJar() {
        return productCatalogJar;
    }
}
