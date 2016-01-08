package universalClasses;


public class Message {
    public final String text;
    public final TimeStamp time;
    public final String samtaleID;
    public final String AfsenderID;


    //public Message(TimeStamp time, String samtaleID, String Afsender, String text){


   // }

    public Message(String samtaleID, String Afsender, String text){
        this.text = text;
        this.samtaleID = samtaleID;
        this.AfsenderID = Afsender;
        this.time = new TimeStamp();

    }

    public String toString() {
        String s = this.text;

        for(int i = 0; i< text.length(); i++){
            if(s.charAt(i) == '§' ){
                


            }

        }

        return("Message\n" + this.time + "\n" + this.samtaleID + "\n" + this.AfsenderID + "\n" + this.text);

        // Den returnere flere linjer med brug af \n
        // Starte med en linje kaldt §


    }

    public String getText(){
        return null;
    }
    
    


}
