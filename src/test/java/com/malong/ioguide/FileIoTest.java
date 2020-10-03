package com.malong.ioguide;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

public class FileIoTest {
    private File mFile;

    @Before
    public void prepare() {
        String filePath = FileIoTest.class.getResource("/").getPath() + "诛仙.txt";
        System.out.println(filePath);
        mFile = new File(filePath);
        System.out.println(mFile.length());
    }

    @Test
    public void testFileRead() throws IOException {
        long start = System.currentTimeMillis();//开始时间
        FileInputStream fileInputStream = new FileInputStream(mFile);
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(fileInputStream));
        String line = reader.readLine();
        while (line != null) {
            System.out.println(line);
            line = reader.readLine();
        }
        reader.close();
        fileInputStream.close();
        long end = System.currentTimeMillis();//结束时间
        System.out.println("NIO 内存映射读大文件，总共耗时：" + (end - start) + "ms");// 1134ms

    }

    @Test
    public void testByteRead() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(mFile);
        byte[] bytes = new byte[9];
        // 从此输入流中读取最多len个字节的数据到一个字节数组，从目标数组b中的offset off开始。
        fileInputStream.read(bytes, 2, 5);
        String s = new String(bytes, "UTF-8");
        System.out.println(s);
//        byte tByte = bytes[0];
//        int i = tByte & 0xFF;
//        String string = Integer.toHexString(tByte);
//        String tString = Integer.toBinaryString((tByte & 0xFF) + 0x100).substring(1);
//        System.out.println(i);
//        String s = Utils.byteArrayToHexStr(bytes);


        fileInputStream.close();

    }

    /** 写文件 */
    @Test
    public void testFilWrite() throws IOException {
        String content = "123456";
        String fileName = "testFilWrite.txt";
        String fileDir = FileIoTest.class.getResource("/").getPath()
                + "temp/FileIoTest/";
        File file = new File(fileDir);
        //noinspection ResultOfMethodCallIgnored
        file.mkdirs();
        file = new File(fileDir + fileName);

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        bufferedWriter.write(content);
        bufferedWriter.close();
        outputStreamWriter.close();
        fileOutputStream.close();


    }

    /** 复制文件 */
    @Test
    public void testCopy() {
        String fileName = "testCopy.txt";
        String fileDir = FileIoTest.class.getResource("/").getPath()
                + "temp/FileIoTest/";
        File file = new File(fileDir);
        //noinspection ResultOfMethodCallIgnored
        file.mkdirs();
        file = new File(fileDir + fileName);

        File destFile = file;// 输出文件

        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(mFile);
            outputStream = new FileOutputStream(destFile);
            byte[] bytes = new byte[1024];
            while (inputStream.read(bytes) > 0) {
                outputStream.write(bytes);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


}
