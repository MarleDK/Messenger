package Server.main;


import universalClasses.Message;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientDatabase {

    public static boolean hasClient(String ID){
        return false;
    }

    public static String getClients() {
        String BuildClients = "GetUsers§";
        File folder = new File("serverdatabase/client/");
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> ChatIds = new ArrayList<>();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                //System.out.println(file.getName());
                BuildClients += file.getName().substring(0,file.getName().length()-4) + "$";

            }
        }
        return BuildClients;
    }





}
