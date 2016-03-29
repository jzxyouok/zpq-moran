package com.cat14.moran.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 流处理工具类
 */
public class StreamUtil {

    /**
     * 读取接收的流
     *
     * @param inStream InputStream
     * @return  byte[]
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inStream) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;

        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0 , len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}
