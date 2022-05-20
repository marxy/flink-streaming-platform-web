package com.flink.streaming.web.common.util;

/**
 * @Author: quentin.zeng
 * @Date: 2022/5/20 18:57
 */
public class KeyUtil {

    public static byte[] getKeyBytes(String key) {
        byte[] bytes = key.getBytes();
        byte[] keyBytes = new byte[24];
        if (bytes.length >= 24) {
            System.arraycopy(bytes, 0, keyBytes, 0, 24);
        } else {
            System.arraycopy(bytes, 0, keyBytes, 0, bytes.length);
            for (int i = bytes.length; i < 24 ; i++) {
                keyBytes[i] = 0;
            }
        }
        return keyBytes;
    }
}
