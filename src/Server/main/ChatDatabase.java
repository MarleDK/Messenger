package Server.main;


import universalClasses.Message;

import java.util.ArrayList;

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
