package com.Controlmatic.PoS_System.api;

import java.io.IOException;

public class JarHandler {

    //executes any terminal command put into function parameter
    //different return statements for ubuntu and windows users
    public static Process execCommand(String command) throws IOException {
        Runtime rt = Runtime.getRuntime();
        //for virgin windows users
        return rt.exec("cmd.exe /c start cmd /c"+ command); //windows
        //for chad ubuntu users
        //return rt.exec(command); //ubuntu
    }

}
