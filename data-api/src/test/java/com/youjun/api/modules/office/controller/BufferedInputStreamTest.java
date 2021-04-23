package com.youjun.api.modules.office.controller;

import java.io.*;

/**
 * <p>
 *
 * </p>
 *
 * @author kirk
 * @since 2021/4/15
 */
public class BufferedInputStreamTest {
    public static void test1() throws IOException {
        BufferedInputStream rawInputStream = new BufferedInputStream(new FileInputStream("D:\\template\\document.xml"));
        byte[] readArray = new byte[1024];
        rawInputStream.mark(0);
        int readCount1 = rawInputStream.read(readArray);
        System.out.println(new String(readArray));
        rawInputStream.reset();
        int readCount2 = rawInputStream.read(readArray);
        System.out.println(new String(readArray));
        rawInputStream.reset();
        int readCount3 = rawInputStream.read(readArray);
        rawInputStream.reset();
        System.out.println(new String(readArray));
        System.out.println("读取了" + readCount1 + "个字节");
        System.out.println("读取了" + readCount2 + "个字节");
        System.out.println("读取了" + readCount3 + "个字节");
    }

    public static void main(String[] args) throws IOException {
        byte[] byteArr = {1, 2, 3, 4, 5};
        InputStream in = new ByteArrayInputStream(byteArr);
        BufferedInputStream is = new BufferedInputStream(in, 2);
        is.mark(3);

        is.read();
        is.reset();
        System.out.println("reset success");

        is.read();
        is.read();
        is.reset();
        System.out.println("reset success");

        is.read();
        is.read();
        is.read();
        is.reset();
        System.out.println("reset success");

        is.read();
        is.read();
        is.read();
        is.read();
        is.reset();
        System.out.println("reset success");

    }
}
