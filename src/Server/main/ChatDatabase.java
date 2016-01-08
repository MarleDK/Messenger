package Server.main;


import universalClasses.Message;
import universalClasses.TimeStamp;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ChatDatabase {

    public static boolean isChat(String ID){
        return false;
    }

    public static ArrayList<String> getClients(String ID){
        return null;
    }


    public static String makeChat(ArrayList<String> clients){

        // Lave ny fil med ChatID som filnavn
        // Navn: TimeStamp + ClientID

        try {
            File chatFile = new File("Server/database/chat/" + new TimeStamp().toString() + clients.toString());
            FileWriter outFile = new FileWriter(chatFile, true);
            PrintWriter out = new PrintWriter(outFile);

            for (int i = 0; i < clients.size();i++) {
                if (clients.size() == i-1) {
                    out.print(clients.get(i));
                }
                else {
                    out.print(clients.get(i) + "§");
                }
            }
            out.close();

        }
        catch (Exception ex) {
            System.out.println("Failed creating file:" + "Server/database/chat/" + new TimeStamp().toString() + clients.toString());
        }
        // Filen indeholde Navne på Clienter
        // Bob§James§Kagemand

        //Lav ny chat, med unikt chatID og returner
        //HUSK at at fortælle de forbundet klienter (både aktive og inaktive), at de er med i en ny chat.
        //HUSK!! Check om det er oprettede clienter
        return null;
    }

    public static void logMessage(Message message){

    }

    public static String GetChatIDs(String Client) {
        // Listen af samtaler i en form for String
        return null;
    }


}
