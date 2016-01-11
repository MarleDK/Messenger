package Server.main;


import universalClasses.Message;
import universalClasses.TimeStamp;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatDatabase {

    public static boolean isChat(String ID){
        return false;
    }

    public static ArrayList<String> getClients(String ID){

        // Læs først linje i ID filen
        File chatFile = new File("serverdatabase/chat/" + ID + ".txt");
        String Line;
        try {
            Scanner sc = new Scanner(chatFile);
            Line = sc.nextLine();
            int lastChar;
            for (int i = 0; Line.length() > i; i++) {
                lastChar = 0;
                if (Line.charAt(i) == '§') {

                    lastChar = i+1;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toString(ArrayList<String> clients){
        String TheString = "";
        for (String client : clients) {
            TheString = TheString + client + "§";
        }
        return TheString;
    }

    public static String makeChat(String s){

        // Lave ny fil med ChatID som filnavn
        // Navn: TimeStamp + ClientID
        if(!s.startsWith("NewChat§")){
            return null;
        }
        ArrayList<String> clients = new ArrayList<>();
        int lastToken = 7;
        for(int i=8; i<s.length(); i++){
            if(s.charAt(i) == '§'){
                clients.add(s.substring(lastToken+1, i));
                lastToken = i;
            }
        }
        clients.add(s.substring(lastToken+1));

        for (int i = clients.size(); i < 0; i--) {
            if (!ClientDatabase.hasClient(clients.get(i))) {
                clients.remove(i);
            }
        }

        //String ID = new TimeStamp().toString() + toString(clients);
        // Dette gør så man kun kan lave en unik gruppe hvert sekundt.
        // det for at undgå § symbolet når man bruger GetChatIDs
        String ID = new TimeStamp().toString();
        File chatFile = new File("serverdatabase/chat/" + ID + ".txt");
        try {
            chatFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter outFile = new FileWriter(chatFile, true);
            PrintWriter out = new PrintWriter(outFile);

            out.println(toString(clients));

            out.close();

        }
        catch (Exception ex) {
            System.out.println("Failed creating file:" + "Server/database/chat/" + ID + ".txt");
            return null;
        }
        // Filen indeholde Navne på Clienter
        // Bob§James§Kagemand

        //Lav ny chat, med unikt chatID og returner
        //HUSK at at fortælle de forbundet klienter (både aktive og inaktive), at de er med i en ny chat.


        //HUSK!! Check om det er oprettede clienter
        return ID;
    }

    public static void logMessage(Message message){
        String ID = message.samtaleID;
        File chatFile = new File("serverdatabase/chat/" + ID + ".txt");
        try {
            FileWriter outFile = new FileWriter(chatFile, true);
            PrintWriter out = new PrintWriter(outFile);
            out.println(message.toString());
            out.close();
        }
        catch (Exception ex) {
            System.out.println("Failed logging file:" + "Server/database/chat/" + ID + ".txt");
        }
    }

    public static ArrayList<String> GetChatIDs(String Client) {
        // Listen af samtaler i en form for String
        File folder = new File("serverdatabase/chat/");
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> ChatIds = new ArrayList<>();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                //System.out.println(file.getName());
                String path = "serverdatabase/chat/";
                //File checkfile = new File(path);
                try {
                    Scanner sc = new Scanner(file);
                    if(sc.hasNextLine()) {
                        if (sc.nextLine().contains(Client)) {
                            // add file to client (String)
                            ChatIds.add(file.getName());
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return ChatIds;
    }


}
