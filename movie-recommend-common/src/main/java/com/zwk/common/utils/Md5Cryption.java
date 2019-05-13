package com.zwk.common.utils;

import com.zwk.common.constant.Final;
/*import com.zwk.movie_recommend.exception.MyException;*/

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * MD5加密
 * 
 * @author chillming
 */
public class Md5Cryption {
    /**
     * MD5加密
     * 
     * @param apcIn
     *            明文
     * @return 十六进制的MD5密文串
     */
    public static String encrypt(String apcIn) {
        byte[] buff = md5Digest(apcIn.getBytes());
        return byte2hex(buff);
    }
    
    /**
     * MD5 摘要计算(byte[]).
     * 
     * @param src
     *            byte[]
     * @throws Exception
     * @return byte[] 16 bit digest
     */
    public static byte[] md5Digest(byte[] src) {
        try {
            MessageDigest alg = MessageDigest.getInstance("MD5");
            return alg.digest(src);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
            //throw new MyException("MD5加密失败", e.getCause(),Final.MD5_ERROE_CODE);
        }
    }

    /**
     *
     * 二进制转换为十六进制String
     *
     * @param buff
     *            二进制
     *
     * @return 十六进制String
     *
     */
    public static String byte2hex(byte[] buff) {

        if (buff == null || buff.length <= 0) {
            return Final.EMPTY;
        }

        String tmpStr = null;
        StringBuilder hexStr = new StringBuilder();

        for (int n = 0; n < buff.length; n++) {
            tmpStr = (Integer.toHexString(buff[n] & 0XFF));
            if (tmpStr.length() == 1) {
                hexStr.append("0");
            }
            hexStr.append(tmpStr);
        }
        return hexStr.toString().toUpperCase();
    }
}
