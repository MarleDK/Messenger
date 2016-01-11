package Server.main;


import universalClasses.Message;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientDatabase {

    private static String BuildClients;
    public static boolean hasClient(String ID){
        return false;
    }

    public static String getClientsOnline() {
        BuildClients = "GetUsers";
        File folder = new File("serverdatabase/client/");
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> ChatIds = new ArrayList<>();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                //System.out.println(file.getName());
                BuildClients += "$" + file.toString();

            }
        }
        return BuildClients;
    }





}
