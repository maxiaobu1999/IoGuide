package com.malong.ioguide;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

/**
 * void seek(long pos) 设置到此文件开头测量到的文件指针偏移量，在该位置发生下一个读取或写入操作。
 */
public class RandomAccessFileTest {
    private File mFile;

    @Before
    public void prepare() {
        String filePath = FileIoTest.class.getResource("/").getPath() + "诛仙.txt";
        System.out.println(filePath);
        mFile = new File(filePath);
        System.out.println(mFile.length());
    }

    /** 一行一行的读 */
    @Test
    public void testReadLine() throws IOException {
        RandomAccessFile raf = new RandomAccessFile(mFile, "r");
        /*
         * 不管文件中保存的数据编码格式是什么
         * 使用 RandomAccessFile对象方法的 readLine() 都会将编码格式转换成 ISO-8859-1
         * 所以要把"ISO-8859-1"编码的字节数组再次转换成系统默认的编码才可以显示正常
         */
        String line = raf.readLine();
        while (line != null) {
            System.out.println(new String(line.getBytes("ISO-8859-1")));
            line = raf.readLine();
        }
        raf.close();
    }

    /** 读取指定位置 */
    @Test
    public void testRead() throws IOException {
        RandomAccessFile raf = new RandomAccessFile(mFile, "r");
        byte[] bytes = new byte[18];
        raf.seek(0);// 指定文件指针
        raf.read(bytes);
        String s1 = new String(bytes, "UTF-8");
        System.out.println(s1);
        raf.seek(18);// 指定文件指针
        raf.read(bytes);
        String s2 = new String(bytes, "UTF-8");
        System.out.println(s2);
        raf.close();
    }

    /** 写文件 */
    @Test
    public void testWrite() throws IOException {
        // 1、创建临时文件
        String content = "123456";
        String fileName = "testWrite.txt";
        String fileDir = FileIoTest.class.getResource("/").getPath()
                + "temp/RandomAccessFileTest/";
        File file = new File(fileDir);
        //noinspection ResultOfMethodCallIgnored
        file.mkdirs();
        // 2、向文件中写入内容
        RandomAccessFile raf = new RandomAccessFile(fileDir + fileName, "rw");
        raf.write(content.getBytes());
        // 3、向文件中追加内容
        raf.seek(content.length());
        raf.write("abcd".getBytes());
        long pointer = raf.getFilePointer();// 记录当前指针
        // 4、替换文件中的内容
        raf.seek(2);
        raf.write("0".getBytes());
        // 5、插入：需要创建一个临时文件来保存插入点后的数据

        // 6、由后向前写入
        raf.seek(pointer + 3);
        raf.write("3".getBytes());
        raf.seek(pointer + 2);
        raf.write("2".getBytes());
        raf.seek(pointer + 1);
        raf.write("1".getBytes());
        //noinspection PointlessArithmeticExpression
        raf.seek(pointer + 0);
        raf.write("0".getBytes());

        raf.close();
    }

    /** 复制文件 */
    @Test
    public void testCopy() {
        String fileName = "testCopy.txt";
        String fileDir = FileIoTest.class.getResource("/").getPath()
                + "temp/RandomAccessFileTest/";
        File file = new File(fileDir);
        //noinspection ResultOfMethodCallIgnored
        file.mkdirs();
        file = new File(fileDir + fileName);

        File destFile = file;// 输出文件

        RandomAccessFile inRaf = null;
        RandomAccessFile outRaf = null;
        try {
            inRaf = new RandomAccessFile(mFile, "r");
            outRaf = new RandomAccessFile(destFile, "rw");

            byte[] bytes = new byte[1024];
            while (inRaf.read(bytes) > 0) {
                outRaf.write(bytes);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inRaf != null)
                    inRaf.close();
                if (outRaf != null)
                    outRaf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 从输入流中读取字节写入输出流
     *
     * @param is 输入流
     * @param raf 输出流
     * @return 复制大字节数
     */
    @Test
    public void testCopyStream() {
        // 1、创建临时文件
        String fileName = "testCopyStream.txt";
        String fileDir = FileIoTest.class.getResource("/").getPath()
                + "temp/RandomAccessFileTest/";
        File file = new File(fileDir);
        //noinspection ResultOfMethodCallIgnored
        file.mkdirs();
        // 2、向文件中写入内容
        try {
            FileInputStream in = new FileInputStream(mFile);
            RandomAccessFile raf = new RandomAccessFile(fileDir + fileName, "rw");
            copyStream(in, raf, 18);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static long copyStream(InputStream is,RandomAccessFile raf,long index) {
        if (null == is || null == raf) {
            return 0;
        }
        try {
            raf.seek(index);
            final int defaultBufferSize = 1024 * 3;
            byte[] buf = new byte[defaultBufferSize];
            long size = 0;
            int len;
            while ((len = is.read(buf)) > 0) {
                raf.write(buf, 0, len);
                size += len;
            }
            return size;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
