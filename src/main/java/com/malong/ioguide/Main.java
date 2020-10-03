package com.malong.ioguide;

import java.io.File;
import java.net.URL;

public class Main {
    public static void main(String[] args) {

        URL url = Main.class.getResource("mapping.txt");

        File file = new File(url.getFile());
        System.out.println(file.length());





    }
}
