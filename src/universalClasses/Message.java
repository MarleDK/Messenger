package universalClasses;


public class Message {
    private String text;
    private TimeStamp time;
    private String samtaleID;
    private String AfsenderID;


    public Message(TimeStamp time, String text, String samtaleID, String Afsender){


    }

    public Message(String text, String samtaleID, String Afsender){
        this.text = text;
        this.samtaleID = samtaleID;
        this.AfsenderID = Afsender;

    }

    public String toString() {
        String s = this.text;

        for(int i = 0; i< text.length(); i++){
            if(this.text ){

            }

        }

        return("Message\n" + this.samtaleID + "\n" + this.AfsenderID + "\n" + time);

        // Den returnere flere linjer med brug af \n
        // Starte med en linje kaldt §


    }

    public String getText(){
        return null;
    }


}
