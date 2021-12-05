package org.xyafu.four_history.utils;

import java.util.Random;

final public class RandomCode {

    public static String createCode(){

        String str="0123456789";
        StringBuilder sb=new StringBuilder(6);
        for(int i=0;i<6;i++)
        {
            char ch=str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
        return sb.toString();
    }
}
