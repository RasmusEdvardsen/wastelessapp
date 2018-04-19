package com.example.edvardsen.wastelessclient.data;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Codes {

    private static String barcode = "";
    private static Codes codes = null;
    private ArrayList<String> ListOfCodes = new ArrayList<String>();
    private static String ocr = "";

    public static String getBarcode(String barcode) {
        return Codes.barcode;
    }
    public static String getOcr() {return ocr;}

    public ArrayList<String> getListofCodes(String barcode, String ocr) {
        ListOfCodes.add(ocr);
        ListOfCodes.add(barcode);
        return ListOfCodes;
    }
}
