package javaIO.section42;

import java.io.FileInputStream;

public class FileInputStreamTest {
    public static void main(String[] args) {
        try (FileInputStream fin = new FileInputStream("a.txt")) {
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fin.read(buffer)) != -1) {
                System.out.println(new String(buffer, 0, len, "gbk"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
