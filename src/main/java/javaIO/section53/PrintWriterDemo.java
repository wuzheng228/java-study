package javaIO.section53;

import java.io.*;

public class PrintWriterDemo {
    public static void main(String[] args) {
        try {
            FileOutputStream fos = new FileOutputStream("a.txt");
            OutputStreamWriter ow = new OutputStreamWriter(fos);
            PrintWriter printWriter = new PrintWriter(ow, true);
            printWriter.write("写入一行数据");
            printWriter.close();
            ow.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
