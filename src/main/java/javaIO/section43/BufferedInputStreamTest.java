package javaIO.section43;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class BufferedInputStreamTest {
    public static void main(String[] args) {
        try {
            FileInputStream fin = new FileInputStream("a.txt"); // 节点流
            BufferedInputStream bfin = new BufferedInputStream(fin); // 处理流
            while (bfin.available() > 0) {
                int read = bfin.read();
                System.out.println(read + "");
            }
            bfin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
