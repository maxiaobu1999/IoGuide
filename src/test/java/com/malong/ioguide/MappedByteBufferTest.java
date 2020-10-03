package com.malong.ioguide;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;


/**
 * 内存映射文件
 *  内存映射文件非常特别，它允许Java程序直接从内存中读取文件内容，通过将整个或部分文件映射到内存，
 * 由操作系统来处理加载请求和写入文件，应用只需要和内存打交道，这使得IO操作非常快。
 * 加载内存映射文件所使用的内存在Java堆区之外。Java编程语言支持内存映射文件，
 * 通过java.nio包和MappedByteBuffer 可以从内存直接读写文件。
 */
public class MappedByteBufferTest {
    private File mFile;

    @Before
    public void propare() {
        String filePath = FileIoTest.class.getResource("/").getPath() + "诛仙.txt";
        System.out.println(filePath);
        mFile = new File(filePath);
        System.out.println(mFile.length());

    }


    /**
     * NIO 内存映射读大文件
     */
    @Test
    public void readTest() {
        long start = System.currentTimeMillis();//开始时间
        long fileLength = 0;
        final int BUFFER_SIZE = 0x300000;// 3M的缓冲
        fileLength = mFile.length();
        try {
            MappedByteBuffer inputBuffer = new RandomAccessFile(mFile, "r")
                    .getChannel().map(FileChannel.MapMode.READ_ONLY, 0, fileLength);// 读取大文件

            byte[] dst = new byte[BUFFER_SIZE];// 每次读出3M的内容

            for (int offset = 0; offset < fileLength; offset += BUFFER_SIZE) {
                if (fileLength - offset >= BUFFER_SIZE) {
                    for (int i = 0; i < BUFFER_SIZE; i++)
                        dst[i] = inputBuffer.get(offset + i);
                } else {
                    for (int i = 0; i < fileLength - offset; i++)
                        dst[i] = inputBuffer.get(offset + i);
                }
                // 将得到的3M内容给Scanner，这里的XXX是指Scanner解析的分隔符
                Scanner scan = new Scanner(new ByteArrayInputStream(dst)).useDelimiter(" ");
                while (scan.hasNext()) {
                    // 这里为对读取文本解析的方法
                    System.out.print(scan.next() + " ");
                }
                scan.close();
            }
            System.out.println();
            long end = System.currentTimeMillis();//结束时间
            System.out.println("NIO 内存映射读大文件，总共耗时：" + (end - start) + "ms");// 1866ms
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
