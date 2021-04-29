package javaIO.section52;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class OutputStreamWriterTest {
    public static void main(String[] args) {
        try {
            FileOutputStream fin = new FileOutputStream("a.txt");
            OutputStreamWriter writer = new OutputStreamWriter(fin, "gbk");
            writer.write("hello writer");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
