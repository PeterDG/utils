package com.peter.util.data;

import com.google.common.base.CaseFormat;
import com.google.common.primitives.Booleans;

import java.util.ArrayList;

/**
 * Created by Peter on 3/13/2016.
 */

public class Strings {

    public static String toCamelCase(String text) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, text);
    }

    public static String capitalize(String text) {
        String result = "";
        if (validateString(text)) {
            String[] arr = text.toLowerCase().split(" ");
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < arr.length; i++) {
                if (arr[i].length() > 0) sb.append(Character.toUpperCase(arr[i].charAt(0)))
                        .append(arr[i].substring(1)).append(" ");
            }
            result = sb.toString().trim();
        }
        return result;
    }

    public static ArrayList<String> capitalize(ArrayList<String> textList) {
        ArrayList<String> outList = new ArrayList<>();
        for (String str : textList)
            outList.add(capitalize(str));
        return outList;
    }

    public static Boolean validateString(String text) {
        Boolean result = true;
        if (!(text.length() > 0)) result = false;
        return result;
    }

    public static String compare(String textA, String textB) {
        if (textA.length() > 1) {
            if (textA.equals(textB))
                return "";
            else {
                int supA = textA.length();
                int supB = textB.length();
                int midA = supA / 2;
                int midB = supB / 2;
                return compare(textA.substring(0,midA),textB.substring(0,midB)) + compare(textA.substring(midA,supA),textB.substring(midB,supB));
            }
        }
        if(!textA.equals(textB))
            return textA;
        return "";
    }

    public static String diffWords(String textA, String textB) {
        String[] wordsListA = textA.split(" ");
        String[] wordsListB = textB.split(" ");

        if (textA.length() > 1) {
            if (textA.equals(textB))
                return "";
            else {
                int supA = textA.length();
                int supB = textB.length();
                int midA = supA / 2;
                int midB = supB / 2;
                return compare(textA.substring(0,midA),textB.substring(0,midB)) + compare(textA.substring(midA,supA),textB.substring(midB,supB));
            }
        }
        if(!textA.equals(textB))
            return textA;
        return "";
    }
}
