package com.zd.dk_dbs.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2017/3/2 0002.
 */

public class MD5Utils {
    public static String ecoder(String password) throws NoSuchAlgorithmException {
        //信息摘要器
        MessageDigest digest = MessageDigest.getInstance("md5");
        byte[] bytes = digest.digest(password.getBytes());

        StringBuffer buffer = new StringBuffer();
        for (byte b : bytes){
            int number = b & 0xfff;//加盐操作
            String numberStr = Integer.toHexString(number);
            System.out.println("输出的结果是："+numberStr);
            if(numberStr.length()==1){
                buffer.append(0);
            }
            buffer.append(numberStr);
        }
//        System.out.print("输出的结果是："+buffer.toString());
        return buffer.toString();
    }
}
