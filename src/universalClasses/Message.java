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

        return("Message§" + this.time + "§" + this.samtaleID + "§" + this.afsenderID + "§" + s);

        // Den returnere flere linjer med brug af \n
        // Starte med en linje kaldt §


    }

    public static Message toMessage(String s){
        if(!s.startsWith("Message§")){
            return null;
        }
        String data = s.substring(28);
        String[] txtData = new String[3];
        int charIndex = 0;
        for (int i = 0; i < 3; i++) {
            boolean isToken = true;
            while (charIndex < data.length() && isToken) {
                if (data.charAt(charIndex) == '§' && data.charAt(charIndex + 1) == '§') {
                    txtData[i] += data.charAt(charIndex);
                    charIndex += 2;
                } else if (data.charAt(charIndex) == '§') {
                    charIndex += 1;
                    isToken = false;
                } else {
                    txtData[i] += data.charAt(charIndex);
                    charIndex += 1;
                }

            }
        }
        TimeStamp time = new TimeStamp(s.substring(8, 27));
        return new Message(time, txtData[0].substring(4), txtData[1].substring(4), txtData[2].substring(4));

    }

    
    


}
