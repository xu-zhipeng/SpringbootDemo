package com.youjun.api.modules.file.util;

import com.youjun.common.util.FileUtils;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;

import java.io.*;

/**
 * <p>
 *
 * </p>
 *
 * @author kirk
 * @since 2021/3/3
 */

/**
 * ipfs 工具类
 */
public class IpfsUtils {

    private static final String IPFS_API = "/ip4/39.100.38.142/tcp/8090";

    /**
     * 上传文件
     *
     * @param filePathAndName 路径+文件名 例:  D:/1.png
     * @return hash字符串  例：Qmf1TC2ThF2QPTXSzTUq185sTyLKtMyQKxGmRzzYSjM2Ca
     * @throws IOException
     */
    public static String upload(String filePathAndName) throws IOException {
        FileInputStream inputStream = new FileInputStream(filePathAndName);
        return upload(inputStream);
    }

    /**
     * 上传文件
     *
     * @param source
     * @return  hash字符串  例：Qmf1TC2ThF2QPTXSzTUq185sTyLKtMyQKxGmRzzYSjM2Ca
     * @throws IOException
     */
    public static String upload(File source) throws IOException {
        FileInputStream inputStream = new FileInputStream(source);
        return upload(inputStream);
    }

    /**
     * 上传文件
     *
     * @param inputStream
     * @return  hash字符串  例：Qmf1TC2ThF2QPTXSzTUq185sTyLKtMyQKxGmRzzYSjM2Ca
     * @throws IOException
     */
    public static String upload(InputStream inputStream) throws IOException {
        IPFS ipfs = new IPFS(IPFS_API);
        NamedStreamable.InputStreamWrapper file = new NamedStreamable.InputStreamWrapper(inputStream);
        MerkleNode addResult = ipfs.add(file).get(0);
        return addResult.hash.toString();
    }

    /**
     * 下载文件
     * @param hash
     * @return  返回字节流
     * @throws IOException
     */
    public static byte[] download(String hash) throws IOException {
        //added QmRJAL6RDH2hYaUN2CdKdS2JGJ4Sn1KzxpTcyG94Y59vXL test.txt
        //added Qmf1TC2ThF2QPTXSzTUq185sTyLKtMyQKxGmRzzYSjM2Ca a.png
        IPFS ipfs = new IPFS(IPFS_API);
        Multihash multihash = Multihash.fromBase58(hash);
        //下载文件
        byte[] data = ipfs.cat(multihash);
        return data;
    }

    /**
     * 下载文件
     *
     * @param hash            hash字符串  例：Qmf1TC2ThF2QPTXSzTUq185sTyLKtMyQKxGmRzzYSjM2Ca
     * @param filePathAndName 路径+文件名 例:  D:/1.png
     * @throws IOException
     */
    public static void download(String hash, String filePathAndName) throws IOException {
        //下载文件
        byte[] data = download(hash);
        FileUtils.writeBytes(data,filePathAndName);
    }
}
