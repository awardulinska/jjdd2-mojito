package com.bike;

public class Test {

    public static void main(String[] args) {
        try {
            BikeParsing bikeParsing = new BikeParsing("C:\\JJavaDeveloper\\nextbike-live.xml");
            bikeParsing.parseData();
            bikeParsing.showData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
