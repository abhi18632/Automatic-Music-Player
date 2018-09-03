package com.songspk.global;

import java.util.Date;

public class Randomizer {

    public static String getRandomString(int length) {
        StringBuffer buffer = new StringBuffer();
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int charactersLength = characters.length();
        for (int i = 0; i < length; i++) {
            double index = Math.random() * charactersLength;
            buffer.append(characters.charAt((int) index));
        }
        return buffer.toString();
    }

    public static String getDatedString(String token, int len) {
        String data = new Date().toString();
        String arr[] = data.split(" ");
        String date = arr[0] + "-" + arr[1] + "-" + arr[2] + "-" + arr[3];
        return date + "|" + token + "|" + getRandomString(len);
    }
}