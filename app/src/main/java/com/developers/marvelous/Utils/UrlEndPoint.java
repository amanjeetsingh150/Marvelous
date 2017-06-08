package com.developers.marvelous.Utils;

/**
 * Created by Amanjeet Singh on 07-Jun-17.
 */

public class UrlEndPoint {
    public static final String BASE_URL="http://gateway.marvel.com";

    public static String getCharecters(){
        return BASE_URL+ "/v1/public/characters";
    }

    public static String getComics(){
        return BASE_URL+"/v1/public/comics";
    }

    public static String getMD5(String timestamp){
        try{
            java.security.MessageDigest md=java.security.MessageDigest.getInstance("MD5");
            String input=timestamp+API_KEY.getPrivateKey()+API_KEY.getPublicKey();
            byte[] array=md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte anArray : array) {
                sb.append(Integer.toHexString((anArray & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
