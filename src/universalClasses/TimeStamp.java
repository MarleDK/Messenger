package universalClasses;


import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeStamp implements Comparable{
    private int sec;
    private int min;
    private int hour;
    private int day;
    private int month;
    private int year;
    private String formatted;
    
    public TimeStamp(){

        DateFormat Fsec = new SimpleDateFormat("ss");
        DateFormat Fmin = new SimpleDateFormat("mm");
        DateFormat Fhour = new SimpleDateFormat("HH");
        DateFormat Fday = new SimpleDateFormat("dd");
        DateFormat Fmonth = new SimpleDateFormat("MM");
        DateFormat Fyear = new SimpleDateFormat("yyyy");
        DateFormat F = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        Calendar calobj = Calendar.getInstance();

        this.sec = Integer.parseInt(Fsec.format(calobj.getTime()));
        this.min = Integer.parseInt(Fmin.format(calobj.getTime()));
        this.hour = Integer.parseInt(Fhour.format(calobj.getTime()));
        this.day = Integer.parseInt(Fday.format(calobj.getTime()));
        this.month = Integer.parseInt(Fmonth.format(calobj.getTime()));
        this.year = Integer.parseInt(Fyear.format(calobj.getTime()));
        this.formatted = F.format(calobj.getTime());
    }

    public TimeStamp(String time){
        this.sec = Integer.parseInt(time.substring(0,2));
        this.min = Integer.parseInt(time.substring(3,5));
        this.hour = Integer.parseInt(time.substring(6,10));
        this.day = Integer.parseInt(time.substring(11,13));
        this.month = Integer.parseInt(time.substring(14,16));
        this.year = Integer.parseInt(time.substring(17,19));
        this.formatted = time;

    }

    public String toString() {
        return this.formatted;
    }

    @Override
    public int compareTo(Object o) {
        //returnerer -1, 0 eller 1, alt efter hvilken "Time" object der er størst, læs lige op på hvordan
        return 0;
    }

}
