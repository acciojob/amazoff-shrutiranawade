package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id = id;
        String stringHours1 = String.valueOf(deliveryTime.charAt(0));
        String stringHours2 = String.valueOf(deliveryTime.charAt(1));
        String ans1 = stringHours1+stringHours2;
        int Hours = Integer.parseInt(ans1);
        Hours = Hours*60;
        //Minutes
        String stringMinute1 = String.valueOf(deliveryTime.charAt(3));
        String stringMinute2 = String.valueOf(deliveryTime.charAt(4));
        int minutes = Integer.parseInt(stringMinute1+stringMinute2);
        //Time
        int ans = Hours+minutes;
        this.deliveryTime = ans; //hh:mm format


    }

    public String getId() {
        return id;
    }

    public static int getDeliveryTimeAsInt(String deliveryTime) {
        return (Integer.parseInt(deliveryTime.substring(0,2))*60) + Integer.parseInt(deliveryTime.substring(3));
    }
    public static String getDeliveryTimeAsString(int deliveryTime){
        int hours = deliveryTime/60;
        int min = deliveryTime%60;
        String strHr ="";
        String stMin ="";
        if(hours<10){
            strHr ="0"+hours;
        }
        else {
            strHr = ""+hours;
        }
        if (min<10){
            stMin ="0"+min;
        }
        else {
            stMin =""+min;
        }
        return strHr+stMin;
    }
    public int getDeliveryTime(){
        return deliveryTime;
    }
}



