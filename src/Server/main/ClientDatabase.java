package Server.main;


import universalClasses.Message;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientDatabase {

    public static boolean hasClient(String ID){
        ID += ".txt";
        File folder = new File("serverdatabase/client/");
        for(int i=0 ; i<folder.listFiles().length ; i++){
            if(folder.listFiles()[i].getName().equals(ID)){
                return true;
            }
        }
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
                BuildClients += file.getName().substring(0,file.getName().length()-4) + "§";

            }
        }
        return BuildClients;
    }

    public static void newClient(String ID, String password){
        File file = new File("serverdatabase/client/" + ID + ".txt");
        System.out.println("Creating new client");
        try{
            FileWriter outFile = new FileWriter(file, true);
            PrintWriter out = new PrintWriter(outFile);

            file.createNewFile();
            System.out.println("File has been created");
            out.println(password);
            out.flush();
            System.out.println("Should have written password:"+password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
