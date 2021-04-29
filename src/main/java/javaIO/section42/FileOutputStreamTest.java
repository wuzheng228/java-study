package javaIO.section42;

import java.io.FileOutputStream;
import java.io.IOException;

public class FileOutputStreamTest {
    public static void main(String[] args) {
        try {
            FileOutputStream fout = new FileOutputStream("a.txt");
            fout.write("helloworld".getBytes());
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
