package javaIO.section42;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopyDemo {
    public static void main(String[] args) {
        try (FileInputStream fin = new FileInputStream("a.txt");
             FileOutputStream fout = new FileOutputStream("new.txt")) {
            byte[] buf = new byte[32];
            while (fin.available() > 0) {
                int len = fin.read(buf);
                fout.write(buf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
