package universalClasses;


public class Message {
    public final String text;
    public final TimeStamp time;
    public final String samtaleID;
    public final String afsenderID;



    public Message(TimeStamp time, String samtaleID, String Afsender, String text){
        this.text = text;
        this.time = time;
        this.samtaleID = samtaleID;
        this.afsenderID = Afsender;

    }

    public Message(String s){
        //lav en konstruktør, der tager message.toString teksten, og laver det til en Message
        while ()
    }

    public Message(String samtaleID, String Afsender, String text){
        this.text = text;
        this.samtaleID = samtaleID;
        this.afsenderID = Afsender;
        this.time = new TimeStamp();

    }

    public String toString() {
        String s = this.text;

        for(int i = 0; i< s.length(); i++){
            if(s.charAt(i) == '§' ){
                String x;
                x = s.substring(0, i) + "§" + s.substring(i);
                s = x;
                i++;


            }

        }

        return("Message§" + this.time + "§" + this.samtaleID + "§" + this.afsenderID + "§" + this.text);

        // Den returnere flere linjer med brug af \n
        // Starte med en linje kaldt §


    }

    
    


}
