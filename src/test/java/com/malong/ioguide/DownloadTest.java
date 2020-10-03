package com.malong.ioguide;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class DownloadTest {
    //    public static final String
    public String mDownloadUrl;
    public String mDownloadFilePath;

    @Before
    public void prepare() throws UnsupportedEncodingException {
        mDownloadFilePath = "/Users/v_maqinglong/Documents/IdeaProjects/IoGuide/src/main/resources/诛仙.txt";
//        mDownloadUrl = "https://sofu.li/wp-content/uploads/2019/07/f9a220cf5718c0b.jpg";
//        mDownloadUrl = "http://downapp.baidu.com/baidusearch/AndroidPhone/11.24.5.11/1/757p/" +
//            "20200622000111/baidusearch_AndroidPhone_11-24-5-11_757p.apk?responseContentDisposition=attachment%3Bfil" +
//            "ename%3D%22baidusearch_AndroidPhone_757p.apk%22&responseContentType=application%2Fvnd.android.packag" +
//            "e-archive&request_id=1594039457_2883908916&type=static";
        mDownloadFilePath=URLEncoder.encode(mDownloadFilePath, "utf-8");// Springboot识别不了中文字符
//        mDownloadUrl = "http://localhost:8088/sample/downloadFile?filePath=" + mDownloadFilePath;
        mDownloadUrl = "https://maqinglong-1253423006.cos.ap-beijing-1.myqcloud.com/static/mini_video/mda-ji5vv1uph88b4nw8.mp4";
    }

    @Test
    public void test() {
        HttpURLConnection connection = null;
        int startIndex = 100;
        int endIndex = 200;
        try {

            URL url = new URL(mDownloadUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/octet-stream");// 表明是某种二进制数据
            connection.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);// 指定下载文件的指定位置
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.connect();

            int contentLength = connection.getContentLength();
            System.out.println("文件的大小是:" + contentLength);
            InputStream inputStream = connection.getInputStream();


            String fileName = "xxx.txt";
            String fileDir = FileIoTest.class.getResource("/").getPath()
                    + "temp/DownloadTest/";
            File file = new File(fileDir);
            //noinspection ResultOfMethodCallIgnored
            file.mkdirs();
            file = new File(fileDir + fileName);

            File destFile = file;// 输出文件
            FileOutputStream outputStream = new FileOutputStream(destFile);
            byte[] bytes = new byte[1024];
            while (inputStream.read(bytes) > 0) {
                outputStream.write(bytes);
            }







        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
