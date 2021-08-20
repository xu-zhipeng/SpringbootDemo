package com.youjun.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * <p>
 * 文件工具类
 * </p>
 *
 * @author kirk
 * @since 2021/5/18
 */
public class FileUtils {
    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

    public static void del(File file) {
        delDir(file);
    }

    public static void delDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            throw new RuntimeException("文件不存在");
        }
        delDir(file);
    }

    public static void delDir(File file) {
        if (!file.exists()) {
            throw new RuntimeException("文件不存在");
        }
        File[] files = file.listFiles();
        if (files != null) {
            for (File subFile : files) {
                if (subFile.isDirectory()) {
                    //递归直到目录下没有文件
                    delDir(subFile);
                } else {
                    //删除
                    if (!subFile.delete()) {
                        throw new RuntimeException(String.format("删除文件[%s]失败", subFile.getAbsolutePath()));
                    }
                }
            }
        }
        //删除
        if (!file.delete()) {
            throw new RuntimeException(String.format("删除文件[%s]失败", file.getAbsolutePath()));
        }
    }

    public static File createFile(String path) {
        File file = new File(path);
        if (file.exists() && !file.delete()) {
            throw new RuntimeException("file is exists and delete fail");
        }
        if (path.endsWith(File.separator) && !file.mkdirs()) {
            throw new RuntimeException("upper folder create fail");
        }
        //判断目标文件所在的目录是否存在 如果目标文件所在的目录不存在，则创建父目录
        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            throw new RuntimeException("upper folder create fail");
        }
        //创建目标文件
        try {
            if (!file.createNewFile()) {
                throw new RuntimeException("file is create fail");
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return file;
    }

    public static File mkdir(String path) {
        if (path == null) {
            return null;
        } else {
            File dir = new File(path);
            return mkdir(dir);
        }
    }

    public static File mkdir(File file) {
        if (file == null) {
            return null;
        } else {
            if (!file.exists()) {
                boolean mkdirs = file.mkdirs();
                if (!mkdirs) {
                    log.error("FileUtils mkdir error");
                }
            }
            return file;
        }
    }

    public static String readString(String path, Charset charset) {
        StringBuilder stringBuilder = new StringBuilder();
        try (
                FileInputStream in = new FileInputStream(path);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, charset));
        ) {
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                stringBuilder.append(str);
            }
        } catch (Exception e) {
            log.error("读取文件错误");
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static byte[] readBytes(String path) {
        File file = new File(path);
        if (!file.exists() || file.isDirectory()) {
            log.error("file is not exists or directorty.");
            throw new RuntimeException("file is not exists or directorty.");
        }
        return readBytes(file);
    }

    public static byte[] readBytes(File file) {
        try (
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = in.read(bytes)) != -1) {
                out.write(bytes, 0, len);
            }
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("FileUtils readBytes error");
        }
        return new byte[]{};
    }

    public static byte[] readBytes(InputStream in) {
        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            if (Objects.isNull(in)) {
                log.error("inputStream is null.");
                throw new RuntimeException("inputStream is null.");
            }
            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = in.read(bytes)) != -1) {
                out.write(bytes, 0, len);
            }
            in.close();
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("FileUtils readBytes error,message:{}", e.getMessage());
        }
        return new byte[]{};
    }

    public static File writeBytes(byte[] bytes, String path) {
        return writeBytes(bytes, createFile(path));
    }

    public static File writeBytes(byte[] bytes, File file) {
        try (
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        ) {
            if (Objects.isNull(file) || file.isDirectory()) {
                log.error("file is null or directorty.");
                throw new RuntimeException("file is null or directorty.");
            }
            bos.write(bytes);
            bos.flush();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return file;
    }

    public static File writeBytes(InputStream in, String path) {
        return writeBytes(in, createFile(path));
    }

    public static File writeBytes(InputStream in, File file) {
        try (
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        ) {
            if (Objects.isNull(in)) {
                log.error("inputStream is null.");
                throw new RuntimeException("inputStream is null.");
            }
            if (Objects.isNull(file) || file.isDirectory()) {
                log.error("file is null or directorty.");
                throw new RuntimeException("file is null or directorty.");
            }
            bos.write(readBytes(in));
            bos.flush();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return file;
    }

    public static void copyFile(String sourcePath, String destPath) {
        try (
                FileChannel inputChannel = new FileInputStream(sourcePath).getChannel();
                FileChannel outputChannel = new FileOutputStream(destPath).getChannel();
        ) {
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("找不到文件" + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("IO问题" + e.getMessage());
        }
    }
}
